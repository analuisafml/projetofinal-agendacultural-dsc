package br.com.agendacultural.api.repositories;

import br.com.agendacultural.api.models.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}