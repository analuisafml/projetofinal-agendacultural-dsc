package br.com.agendacultural.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CheckoutRequestDTO(
        @NotNull Long tipoIngressoId,
        @NotNull @Positive int quantidade
) {}