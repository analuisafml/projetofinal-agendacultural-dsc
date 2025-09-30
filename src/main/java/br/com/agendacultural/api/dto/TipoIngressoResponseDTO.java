package br.com.agendacultural.api.dto;

import java.math.BigDecimal;

public record TipoIngressoResponseDTO(
        Long id,
        Long sessaoId,
        String tipo,
        BigDecimal preco,
        int quantidadeTotal,
        int quantidadeDisponivel
) {}