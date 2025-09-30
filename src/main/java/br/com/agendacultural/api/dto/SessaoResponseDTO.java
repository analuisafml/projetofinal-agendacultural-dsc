package br.com.agendacultural.api.dto;

import java.time.LocalDateTime;

public record SessaoResponseDTO(
        Long id,
        Long eventoId,
        LocalDateTime dataHoraInicio,
        LocalDateTime dataHoraFim,
        String status
) {}