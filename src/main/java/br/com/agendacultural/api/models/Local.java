package br.com.agendacultural.api.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "locais")
@Data
public class Local {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String endereco;
    private String cidade;
}