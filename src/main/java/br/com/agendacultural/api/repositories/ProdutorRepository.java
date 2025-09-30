package br.com.agendacultural.api.repositories;

import br.com.agendacultural.api.models.Produtor;
import br.com.agendacultural.api.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProdutorRepository extends JpaRepository<Produtor, Long> {
    Optional<Produtor> findByUsuario(Usuario usuario);
}