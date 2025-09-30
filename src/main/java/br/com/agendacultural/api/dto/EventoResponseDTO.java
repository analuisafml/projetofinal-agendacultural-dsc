package br.com.agendacultural.api.dto;

public record EventoResponseDTO(
        Long id,
        String titulo,
        String descricao,
        String imagemUrl,
        String nomeProdutor,
        String nomeLocal,
        String nomeCategoria
) {}