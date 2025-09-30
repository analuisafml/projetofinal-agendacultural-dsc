package br.com.agendacultural.api.dto;

import br.com.agendacultural.api.models.TipoIngresso;
import java.math.BigDecimal;

public record TipoIngressoDetalheDTO(
        Long id,
        String tipo,
        BigDecimal preco,
        int quantidadeDisponivel
) {
    public TipoIngressoDetalheDTO(TipoIngresso tipoIngresso) {
        this(
                tipoIngresso.getId(),
                tipoIngresso.getTipo(),
                tipoIngresso.getPreco(),
                tipoIngresso.getQuantidadeTotal() - tipoIngresso.getQuantidadeVendida()
        );
    }
}