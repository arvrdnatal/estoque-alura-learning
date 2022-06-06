package br.com.alura.estoque.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Product {

    @PrimaryKey(autoGenerate = true)
    private final long id;
    private final String nome;
    private final BigDecimal preco;
    private final int quantidade;

    public Product(long id, String nome, BigDecimal preco, int quantidade) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getPreco() {
        return (preco != null) ? preco.setScale(2, RoundingMode.HALF_EVEN) : new BigDecimal(0);
    }

    public int getQuantidade() {
        return quantidade;
    }

}
