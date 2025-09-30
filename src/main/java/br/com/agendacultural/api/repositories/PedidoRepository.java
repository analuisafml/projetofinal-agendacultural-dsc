package br.com.agendacultural.api.repositories;

import br.com.agendacultural.api.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}
