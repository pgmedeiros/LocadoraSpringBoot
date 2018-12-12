
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
    public void naoDeveValidarUmNomeComDoisCaracteres() {
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
    @Test
    public void nomeDeveSerCampoUnico(){
        Cliente cliente = new Cliente();
        cliente.setNome("Angelo");
        
        try {
            
        }catch (ClienteException ex){
        
    }
    
    
    }
    @Test
    public void naoDeveRegistrarNomeComEspacosNoInicioFim(){
        Cliente cliente = new Cliente();
        cliente.setNome(" Patrick Medeiros ");
        assertThat(cliente.getNome(),is("Patrick Medeiros"));
    }
    @Test
    public void nomeComPrimeiraLetraMaiuscula(){
        Cliente cliente = new Cliente();
        cliente.setNome("patrick medeiros");
        assertThat(cliente.getNome(),is("Patrick Medeiros"));
    }
    
}

