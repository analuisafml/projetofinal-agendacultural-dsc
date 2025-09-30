package br.com.agendacultural.api.repositories;

import br.com.agendacultural.api.models.Local;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalRepository extends JpaRepository<Local, Long> {
}