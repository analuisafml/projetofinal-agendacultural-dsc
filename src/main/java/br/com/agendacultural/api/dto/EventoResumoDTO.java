package br.com.agendacultural.api.dto;

import br.com.agendacultural.api.models.Evento;
import br.com.agendacultural.api.models.Sessao;

import java.time.LocalDateTime;

public record EventoResumoDTO(
        Long id,
        String titulo,
        String imagemUrl,
        String cidade,
        String nomeLocal,
        LocalDateTime proximaSessao
) {
    public EventoResumoDTO(Evento evento) {
        this(
                evento.getId(),
                evento.getTitulo(),
                evento.getImagemUrl(),
                evento.getLocal().getCidade(),
                evento.getLocal().getNome(),
                evento.getSessoes().stream()
                        .map(Sessao::getDataHoraInicio)
                        .filter(data -> data.isAfter(LocalDateTime.now()))
                        .min(LocalDateTime::compareTo)
                        .orElse(null)
        );
    }
}