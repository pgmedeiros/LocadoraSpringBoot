
package local.locadora.entities;

import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Iterator;
import java.util.Set;
import local.locadora.exceptions.ClienteException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class ClienteEntityTest {

    private static Validator validator;
    
    @Rule
    public ExpectedException expected = ExpectedException.none();

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     * Note que <b>validator</b> aplica a validação do bean validation
     * O Iterator é utilizado para pegar as violações ocorridas
     */
    
    @Test
    public void cpfNaoValido(){
        Cliente cliente = new Cliente();
        String cpf = "021.571.990-099";
       
        cliente.setCpf(cpf);
        cliente.setNome("Patrick Goncalves");
        
        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        
        assertThat(message, is("O CPF não é válido"));	
    }
    
    @Test
    public void cpfTemQueSerUmValorValido(){        
        Cliente cliente = new Cliente();
        cliente.setCpf("12312312312");
        
        assertThat(cliente.getCpf(), is("12312312312"));
    }
    
    @Test
    public void nomeDeveTerEntreQuatroACinquentaCaracteres() {
        Cliente cliente = new Cliente();
        cliente.setNome("An");

        Set<ConstraintViolation<Cliente>> violations = validator.validate(cliente);
        Iterator it = violations.iterator();
        //while(it.hasNext()){
        ConstraintViolationImpl x = (ConstraintViolationImpl) it.next();
        String message = x.getMessage();
        // }

        assertThat(message, is("Um nome deve possuir entre 4 e 50 caracteres"));
    }
    
    @Test
    public void naoDeveAceitarNomeComSimbolosENumeros(){
        expected.expect(ClienteException.class);
        expected.expectMessage("Nome não deve possuir simbolos ou números");
        Cliente cliente = new Cliente();
        cliente.setNome("J0rge Silva"); 
    }
    
    /*@Test
    public void nomeDeveSerCampoUnico(){
     
    }*/
    
    @Test
    public void naoDeveRegistrarNomeComEspacosNoInicioFim(){
        Cliente cliente = new Cliente();
        cliente.setNome(" Patrick Medeiros ");
        assertThat(cliente.getNome(),is("Patrick Medeiros"));
    }
    
    @Test
     public void primeiraLetraDoNomeESobrenomeDevemSerMaiusculas() {

        try {
            Cliente cliente = new Cliente();
            cliente.setNome("Patrick Gonçalves");
            assertThat(cliente.getNome(), is("Patrick Gonçalves"));
        } catch (Exception e) {
            e.getMessage();
            fail();
        }
    }
}

