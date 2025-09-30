package br.com.agendacultural.api.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "produtores")
@Data
public class Produtor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeFantasia;

    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;
}