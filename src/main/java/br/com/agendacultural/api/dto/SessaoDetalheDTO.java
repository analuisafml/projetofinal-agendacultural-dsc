package br.com.agendacultural.api.dto;

import br.com.agendacultural.api.models.Sessao;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record SessaoDetalheDTO(
        Long id,
        LocalDateTime dataHoraInicio,
        String status,
        List<TipoIngressoDetalheDTO> tiposDeIngresso
) {
    public SessaoDetalheDTO(Sessao sessao) {
        this(
                sessao.getId(),
                sessao.getDataHoraInicio(),
                sessao.getStatus(),
                sessao.getTiposIngresso().stream()
                        .map(TipoIngressoDetalheDTO::new)
                        .collect(Collectors.toList())
        );
    }
}