package br.com.agendacultural.api.services;

import br.com.agendacultural.api.dto.*;
import br.com.agendacultural.api.exceptions.ResourceNotFoundException;
import br.com.agendacultural.api.models.*;
import br.com.agendacultural.api.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventoService {

    @Autowired
    private SessaoRepository sessaoRepository;
    @Autowired
    private EventoRepository eventoRepository;
    @Autowired
    private ProdutorRepository produtorRepository;
    @Autowired
    private LocalRepository localRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;

    public EventoResponseDTO criarEvento(EventoRequestDTO dto, Usuario usuarioLogado) {
        Produtor produtor = produtorRepository.findByUsuario(usuarioLogado)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil de produtor não encontrado para este usuário."));
        Local local = localRepository.findById(dto.localId())
                .orElseThrow(() -> new ResourceNotFoundException("Local não encontrado."));
        Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada."));

        Evento novoEvento = new Evento();
        novoEvento.setTitulo(dto.titulo());
        novoEvento.setDescricao(dto.descricao());
        novoEvento.setImagemUrl(dto.imagemUrl());
        novoEvento.setProdutor(produtor);
        novoEvento.setLocal(local);
        novoEvento.setCategoria(categoria);

        Evento eventoSalvo = eventoRepository.save(novoEvento);

        return new EventoResponseDTO(
                eventoSalvo.getId(),
                eventoSalvo.getTitulo(),
                eventoSalvo.getDescricao(),
                eventoSalvo.getImagemUrl(),
                eventoSalvo.getProdutor().getNomeFantasia(),
                eventoSalvo.getLocal().getNome(),
                eventoSalvo.getCategoria().getNome()
        );
    }


    public SessaoResponseDTO adicionarSessao(Long eventoId, SessaoRequestDTO dto, Usuario usuarioLogado) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado com o ID: " + eventoId));

        Produtor produtor = produtorRepository.findByUsuario(usuarioLogado)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil de produtor não encontrado para este usuário."));

        if (!evento.getProdutor().equals(produtor)) {
            throw new SecurityException("Acesso negado: Você não é o proprietário deste evento.");
        }

        Sessao novaSessao = new Sessao();
        novaSessao.setEvento(evento);
        novaSessao.setDataHoraInicio(dto.dataHoraInicio());
        novaSessao.setDataHoraFim(dto.dataHoraFim());
        novaSessao.setStatus("AGENDADA");

        Sessao sessaoSalva = sessaoRepository.save(novaSessao);

        return new SessaoResponseDTO(
                sessaoSalva.getId(),
                sessaoSalva.getEvento().getId(),
                sessaoSalva.getDataHoraInicio(),
                sessaoSalva.getDataHoraFim(),
                sessaoSalva.getStatus()
        );
    }

    public List<EventoResumoDTO> listarEventos() {
        List<Evento> eventos = eventoRepository.findAll();

        return eventos.stream()
                .map(EventoResumoDTO::new)
                .collect(Collectors.toList());
    }

    public EventoDetalheDTO buscarPorId(Long id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado com o ID: " + id));

        return new EventoDetalheDTO(evento);
    }

    public List<EventoResumoDTO> listarEventosPorProdutor(Usuario usuarioLogado) {
        Produtor produtor = produtorRepository.findByUsuario(usuarioLogado)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil de produtor não encontrado para este usuário."));

        List<Evento> eventos = eventoRepository.findByProdutor(produtor);

        return eventos.stream()
                .map(EventoResumoDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public EventoResponseDTO atualizarEvento(Long eventoId, EventoRequestDTO dto, Usuario usuarioLogado) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado com o ID: " + eventoId));

        Produtor produtor = produtorRepository.findByUsuario(usuarioLogado)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil de produtor não encontrado."));

        if (!evento.getProdutor().equals(produtor)) {
            throw new SecurityException("Acesso negado: Você não é o proprietário deste evento.");
        }

        Local local = localRepository.findById(dto.localId())
                .orElseThrow(() -> new ResourceNotFoundException("Local não encontrado."));
        Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada."));

        evento.setTitulo(dto.titulo());
        evento.setDescricao(dto.descricao());
        evento.setImagemUrl(dto.imagemUrl());
        evento.setLocal(local);
        evento.setCategoria(categoria);

        return new EventoResponseDTO(
                evento.getId(), evento.getTitulo(), evento.getDescricao(), evento.getImagemUrl(),
                evento.getProdutor().getNomeFantasia(), evento.getLocal().getNome(), evento.getCategoria().getNome()
        );
    }
}