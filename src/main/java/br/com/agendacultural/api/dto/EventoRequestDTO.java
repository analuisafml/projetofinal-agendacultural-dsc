package br.com.agendacultural.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EventoRequestDTO(
        @NotBlank String titulo,
        @NotBlank String descricao,
        String imagemUrl,
        @NotNull Long localId,
        @NotNull Long categoriaId
) {}