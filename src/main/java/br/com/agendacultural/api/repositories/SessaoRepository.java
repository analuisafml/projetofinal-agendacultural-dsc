package br.com.agendacultural.api.repositories;

import br.com.agendacultural.api.models.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SessaoRepository extends JpaRepository<Sessao, Long> {

}