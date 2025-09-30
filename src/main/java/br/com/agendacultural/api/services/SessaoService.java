package br.com.agendacultural.api.services;

import br.com.agendacultural.api.dto.TipoIngressoRequestDTO;
import br.com.agendacultural.api.dto.TipoIngressoResponseDTO;
import br.com.agendacultural.api.exceptions.ResourceNotFoundException;
import br.com.agendacultural.api.models.*;
import br.com.agendacultural.api.repositories.ProdutorRepository;
import br.com.agendacultural.api.repositories.SessaoRepository;
import br.com.agendacultural.api.repositories.TipoIngressoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessaoService {

    @Autowired
    private SessaoRepository sessaoRepository;
    @Autowired
    private ProdutorRepository produtorRepository;
    @Autowired
    private TipoIngressoRepository tipoIngressoRepository;

    public TipoIngressoResponseDTO adicionarTipoIngresso(Long sessaoId, TipoIngressoRequestDTO dto, Usuario usuarioLogado) {
        Sessao sessao = sessaoRepository.findById(sessaoId)
                .orElseThrow(() -> new ResourceNotFoundException("Sessão não encontrada com o ID: " + sessaoId));

        Produtor produtor = produtorRepository.findByUsuario(usuarioLogado)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil de produtor não encontrado."));

        if (!sessao.getEvento().getProdutor().equals(produtor)) {
            throw new SecurityException("Acesso negado: Você não é o proprietário do evento desta sessão.");
        }

        TipoIngresso novoTipoIngresso = new TipoIngresso();
        novoTipoIngresso.setSessao(sessao);
        novoTipoIngresso.setTipo(dto.tipo());
        novoTipoIngresso.setPreco(dto.preco());
        novoTipoIngresso.setQuantidadeTotal(dto.quantidadeTotal());

        TipoIngresso tipoIngressoSalvo = tipoIngressoRepository.save(novoTipoIngresso);

        return new TipoIngressoResponseDTO(
                tipoIngressoSalvo.getId(),
                tipoIngressoSalvo.getSessao().getId(),
                tipoIngressoSalvo.getTipo(),
                tipoIngressoSalvo.getPreco(),
                tipoIngressoSalvo.getQuantidadeTotal(),
                tipoIngressoSalvo.getQuantidadeTotal()
        );
    }

    public void deletarSessao(Long sessaoId, Usuario usuarioLogado) {
        Sessao sessao = sessaoRepository.findById(sessaoId)
                .orElseThrow(() -> new ResourceNotFoundException("Sessão não encontrada com o ID: " + sessaoId));

        Produtor produtor = produtorRepository.findByUsuario(usuarioLogado)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil de produtor não encontrado."));

        if (!sessao.getEvento().getProdutor().equals(produtor)) {
            throw new SecurityException("Acesso negado: Você não pode deletar uma sessão de um evento que não é seu.");
        }

        sessaoRepository.delete(sessao);
    }
}