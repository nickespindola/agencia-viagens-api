package com.agencia.viagens.service;

import com.agencia.viagens.model.Destino;
import com.agencia.viagens.repository.DestinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DestinoService {

    @Autowired
    private DestinoRepository destinoRepository;

    /**
     * cadastro de novo destino de viagem
     */
    public Destino cadastrarDestino(Destino destino) {
        if (destino.getAvaliacaoMedia() == null) {
            destino.setAvaliacaoMedia(0.0);
        }
        if (destino.getNumeroAvaliacoes() == null) {
            destino.setNumeroAvaliacoes(0);
        }
        return destinoRepository.save(destino);
    }

    /**
     * lista de todos os destinos disponíveis
     */
    public List<Destino> listarTodosDestinos() {
        return destinoRepository.findAll();
    }

    /**
     * busca por ID
     */
    public Optional<Destino> buscarDestinoPorId(Long id) {
        return destinoRepository.findById(id);
    }

    /**
     * pesquisa por nome ou localização
     */
    public List<Destino> pesquisarDestinos(String termo) {
        if (termo == null || termo.trim().isEmpty()) {
            return listarTodosDestinos();
        }
        return destinoRepository.pesquisarPorNomeOuLocalizacao(termo);
    }

    /**
     * avalia destino com uma nota de 1 a 10 e recalcula a média
     */
    public Optional<Destino> avaliarDestino(Long id, Integer nota) {
        if (nota < 1 || nota > 10) {
            throw new IllegalArgumentException("A nota deve estar entre 1 e 10");
        }

        return destinoRepository.findById(id).map(destino -> {
            // calcula a nova média ponderada
            Double somaAvaliacoes = destino.getAvaliacaoMedia() * destino.getNumeroAvaliacoes();
            somaAvaliacoes += nota;
            
            destino.setNumeroAvaliacoes(destino.getNumeroAvaliacoes() + 1);
            destino.setAvaliacaoMedia(somaAvaliacoes / destino.getNumeroAvaliacoes());
            
            return destinoRepository.save(destino);
        });
    }

    /**
     * deleta destino de viagem
     */
    public boolean excluirDestino(Long id) {
        if (destinoRepository.existsById(id)) {
            destinoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * verifica se destino existe
     */
    public boolean destinoExiste(Long id) {
        return destinoRepository.existsById(id);
    }

    /**
     * atualiza os dados de um destino existente
     */
    public Optional<Destino> atualizarDestino(Long id, Destino dadosAtualizados) {
        return destinoRepository.findById(id).map(destinoExistente -> {
            // Atualiza apenas os campos editáveis
            destinoExistente.setNome(dadosAtualizados.getNome());
            destinoExistente.setLocalizacao(dadosAtualizados.getLocalizacao());
            destinoExistente.setDescricao(dadosAtualizados.getDescricao());

            // Mantém os dados de avaliação (não reinicia)
            return destinoRepository.save(destinoExistente);
        });
    }
}
