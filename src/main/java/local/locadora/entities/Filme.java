package local.locadora.entities;


import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Objects;
import local.locadora.exceptions.FilmeException;
import local.locadora.exceptions.FilmeSemEstoqueException;

@Entity
@Validated
public class Filme implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    @Size(min = 2, max = 100, message = "Um filme deve possuir entre 2 e 100 caracteres")
    private String nome;
    @Column
    @Min(value = 0, message = "O Estoque deve ser positivo")
    private Integer estoque;
    @Column
    @Digits(integer = 2, fraction = 2, message = "O Preço deve ter no máximo dois dígitos")
    @Positive(message = "O Valor da locação deve ser positivo")
    private Double precoLocacao;

    public Filme() {
    }

    public Filme(String nome, Integer estoque, Double precoLocacao) {
        this.nome = nome.trim();
        this.estoque = estoque;
        this.precoLocacao = precoLocacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if(nome.length() < 2 || nome.length() > 100 ){
            throw new FilmeException("O nome do filme deve possuir entre 2 e 100 caracteres");
        }
        this.nome = nome.trim();
    }

    public Integer getEstoque() {
        return estoque;
    }

    public void setEstoque(Integer estoque) {
        if(estoque < 0){
            throw new FilmeException("O Estoque deve ser positivo");
        }
        this.estoque = estoque;
    }

    public Double getPrecoLocacao() {
        return precoLocacao;
    }

    public void setPrecoLocacao(Double precoLocacao) {
        //DecimalFormat formato = new DecimalFormat("#.##");      
        //precoLocacao = Double.valueOf(formato.format(precoLocacao));
        
        if(precoLocacao > 99.99){
            throw new FilmeException("O Preço deve ter no máximo dois dígitos");
        }
        if(precoLocacao < 0){
            throw new FilmeException("O Valor da locação deve ser positivo");
        }
        this.precoLocacao = precoLocacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Filme other = (Filme) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nome + " - Estoque:" + estoque;
    }

}
