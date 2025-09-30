package br.com.agendacultural.api.repositories;

import br.com.agendacultural.api.models.Evento;
import br.com.agendacultural.api.models.Produtor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByProdutor(Produtor produtor);
}