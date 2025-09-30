package br.com.agendacultural.api.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record SessaoRequestDTO(
        @NotNull
        @Future
        LocalDateTime dataHoraInicio,

        @NotNull
        @Future
        LocalDateTime dataHoraFim
) {}