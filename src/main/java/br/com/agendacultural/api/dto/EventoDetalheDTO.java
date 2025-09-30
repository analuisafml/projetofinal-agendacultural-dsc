package br.com.agendacultural.api.dto;

import br.com.agendacultural.api.models.Evento;
import java.util.List;
import java.util.stream.Collectors;

public record EventoDetalheDTO(
        Long id,
        String titulo,
        String descricao,
        String local,
        String categoria,
        List<SessaoDetalheDTO> sessoes
) {
    public EventoDetalheDTO(Evento evento) {
        this(
                evento.getId(),
                evento.getTitulo(),
                evento.getDescricao(),
                evento.getLocal().getNome(),
                evento.getCategoria().getNome(),
                evento.getSessoes().stream()
                        .map(SessaoDetalheDTO::new)
                        .collect(Collectors.toList())
        );
    }
}