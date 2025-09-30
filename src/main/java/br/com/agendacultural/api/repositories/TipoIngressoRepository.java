package br.com.agendacultural.api.repositories;

import br.com.agendacultural.api.models.TipoIngresso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoIngressoRepository extends JpaRepository<TipoIngresso, Long> {
}