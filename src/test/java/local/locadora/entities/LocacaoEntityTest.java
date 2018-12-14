package local.locadora.entities;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import local.locadora.exceptions.LocacaoException;
import local.locadora.utils.DataUtils;
import static org.hamcrest.core.Is.is;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.junit.Assert;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class LocacaoEntityTest {
    private List<Filme> filmes;
    
    private static Validator validator;
    
    @Rule
    public ExpectedException expected = ExpectedException.none();

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    @Test
    public void naoDeveAceitarUsuarioNulo(){
        expected.expect(LocacaoException.class);
        expected.expectMessage("Um cliente deve ser selecionado");
        Locacao locacao = new Locacao();
        locacao.setCliente(null);

    }
    @Test
    public void locacaoDevePossuirPeloMenosUmFilme(){
        Locacao locacao = new Locacao();
        
        locacao.setDataLocacao(DataUtils.obterData(13, 12, 2018));
        locacao.setDataRetorno(DataUtils.obterData(15, 12, 2018));
        locacao.setFilmes(null);

        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();

        String msg = x.getMessage();

        assertThat(msg, is("Pelo menos um filme deve ser selecionado"));    
    }
    
    @Test
    public void locacaoDeFilmeSemEstoque(){
        Locacao locacao = new Locacao();
        Filme filme = new Filme("A fuga das galinhas", 0, 3.0);
        
        try{
            locacao.addFilme(filme);
        }catch(Exception e){
            Object ExceptionLocadora = null;
            
            Assert.assertSame(ExceptionLocadora, e);
        }
    }
    
    @Test
    public void dataDeLocacaoNaoDeveSerNula(){
        Locacao locacao = new Locacao();
        
        locacao.setDataRetorno(DataUtils.obterData(13, 12, 2018));
        
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();

        String msg = x.getMessage();

        assertThat(msg, is("A data de locação não deve ser nula")); 
    }
    
    @Test
    public void dataDeRetornoNaoPodeSerNulo() {
        Locacao locacao = new Locacao();
        
        locacao.setDataLocacao(DataUtils.obterData(20, 12, 2018));
        locacao.setValor(12.00);
        
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();

        String msg = x.getMessage();

        assertThat(msg, is("A data de retorno não deve ser nula"));
    }
    
    @Test
    public void dataDeRetornoDeveSerFutura(){
        Locacao locacao = new Locacao();
        
        locacao.setDataLocacao(DataUtils.obterData(17, 12, 2018));
        locacao.setDataRetorno(DataUtils.obterData(15, 12, 2018));

        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();

        String msg = x.getMessage();

        assertThat(msg, is("A data deve retorno deve ser futura")); 
    }
    
    @Test
    public void precoDeveTerNoMaximoDoisDigitos(){
        Locacao locacao = new Locacao();
        
        locacao.setDataLocacao(DataUtils.obterData(15, 12, 2018));
        locacao.setDataRetorno(DataUtils.obterData(17, 12, 2018));
        
        locacao.setValor(123.123);
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();

        String msg = x.getMessage();

        assertThat(msg, is("O Preço deve ter no máximo dois dígitos"));
    }
    
    @Test
    public void valorDaLocacaoDeveSerSemprePositivo() {
        Locacao locacao = new Locacao();
        
        locacao.setDataLocacao(DataUtils.obterData(20, 12, 2018));
        locacao.setDataRetorno(DataUtils.obterData(28, 12, 2018));
        locacao.setValor(-1.00);
        
        Set<ConstraintViolation<Locacao>> violations = validator.validate(locacao);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();

        String msg = x.getMessage();

        assertThat(msg, is("O valor da locação deve ser positivo"));
    }
}