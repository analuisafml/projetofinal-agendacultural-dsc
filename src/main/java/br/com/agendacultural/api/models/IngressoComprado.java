package br.com.agendacultural.api.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ingressos_comprados")
@Data
public class IngressoComprado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "tipo_ingresso_id", nullable = false)
    private TipoIngresso tipoIngresso;

    @Column(unique = true)
    private String qrCodeUnico;
}