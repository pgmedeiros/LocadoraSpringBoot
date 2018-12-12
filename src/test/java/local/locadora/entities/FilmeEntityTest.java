package local.locadora.entities;

import java.util.Iterator;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import local.locadora.exceptions.FilmeException;
import local.locadora.exceptions.FilmeSemEstoqueException;
import static org.hamcrest.core.Is.is;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import static org.junit.Assert.assertThat;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FilmeEntityTest {
    private static Validator validator;
    
    @Rule
    public ExpectedException expected = ExpectedException.none();
    
    @Test
    public void naoDeveRegistrarFilmeComEspacosNoInicioFim(){
        Filme filme = new Filme();
        filme.setNome(" A fuga das galinhas ");
        assertThat(filme.getNome(),is("A fuga das galinhas"));
    }
    @Test
    public void nomeDeveConterEntre2e100Caracteres(){
        expected.expect(FilmeException.class);
        expected.expectMessage("O nome do filme deve possuir entre 2 e 100 caracteres");
        Filme filme = new Filme();
        filme.setNome("a");
    }
    @Test
    public void estoqueFilmeNaopodeSernegativo(){
        expected.expect(FilmeException.class);
        expected.expectMessage("O Estoque deve ser positivo");
        Filme filme = new Filme();
        filme.setEstoque(-1);
    }
    
    @Test
    public void precoLocacaoNaoDeveUltrapassarDoisDigitos(){
        expected.expect(FilmeException.class);
        expected.expectMessage("O Preço deve ter no máximo dois dígitos");
        Filme filme = new Filme();
        filme.setPrecoLocacao(100.991);
    }
    
    @Test
    public void valorDaLocacaoNaoPodeSerNegativo(){
        expected.expect(FilmeException.class);
        expected.expectMessage("O Valor da locação deve ser positivo");
        Filme filme = new Filme();
        filme.setPrecoLocacao(-2.00);
    }
}
