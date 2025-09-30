package br.com.agendacultural.api.dto;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(@NotBlank String email, @NotBlank String senha) {}
