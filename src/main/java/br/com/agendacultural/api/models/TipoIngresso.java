package br.com.agendacultural.api.models;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "tipos_ingresso")
@Data
public class TipoIngresso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sessao_id", nullable = false)
    private Sessao sessao;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private BigDecimal preco;

    @Column(nullable = false)
    private int quantidadeTotal;

    private int quantidadeVendida = 0;
}