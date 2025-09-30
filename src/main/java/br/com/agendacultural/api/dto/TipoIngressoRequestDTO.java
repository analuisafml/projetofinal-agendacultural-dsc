package br.com.agendacultural.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record TipoIngressoRequestDTO(
        @NotBlank String tipo,
        @NotNull @PositiveOrZero BigDecimal preco,
        @NotNull @Positive int quantidadeTotal
) {}