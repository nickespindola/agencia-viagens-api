package com.agencia.viagens.service;

import com.agencia.viagens.model.Destino;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class DestinoService {

    private final List<Destino> destinos = new ArrayList<>();
    private final AtomicLong contadorId = new AtomicLong(1);

    /**
     * cadastro de novo destino de viagem
     */
    public Destino cadastrarDestino(Destino destino) {
        destino.setId(contadorId.getAndIncrement());
        if (destino.getAvaliacaoMedia() == null) {
            destino.setAvaliacaoMedia(0.0);
        }
        if (destino.getNumeroAvaliacoes() == null) {
            destino.setNumeroAvaliacoes(0);
        }
        destinos.add(destino);
        return destino;
    }

    /**
     * lista de todos os destinos disponíveis
     */
    public List<Destino> listarTodosDestinos() {
        return new ArrayList<>(destinos);
    }

    /**
     * busca por ID
     */
    public Optional<Destino> buscarDestinoPorId(Long id) {
        return destinos.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst();
    }

    /**
     * pesquisa por nome ou localização
     */
    public List<Destino> pesquisarDestinos(String termo) {
        if (termo == null || termo.trim().isEmpty()) {
            return listarTodosDestinos();
        }
        
        String termoLowerCase = termo.toLowerCase();
        return destinos.stream()
                .filter(d -> 
                    (d.getNome() != null && d.getNome().toLowerCase().contains(termoLowerCase)) ||
                    (d.getLocalizacao() != null && d.getLocalizacao().toLowerCase().contains(termoLowerCase))
                )
                .collect(Collectors.toList());
    }

    /**
     * avalia destino com uma nota de 1 a 10 e recalcula a média
     */
    public Optional<Destino> avaliarDestino(Long id, Integer nota) {
        if (nota < 1 || nota > 10) {
            throw new IllegalArgumentException("A nota deve estar entre 1 e 10");
        }

        Optional<Destino> destinoOpt = buscarDestinoPorId(id);
        
        if (destinoOpt.isPresent()) {
            Destino destino = destinoOpt.get();
            
            // calcula a nova média ponderada
            Double somaAvaliacoes = destino.getAvaliacaoMedia() * destino.getNumeroAvaliacoes();
            somaAvaliacoes += nota;
            
            destino.setNumeroAvaliacoes(destino.getNumeroAvaliacoes() + 1);
            destino.setAvaliacaoMedia(somaAvaliacoes / destino.getNumeroAvaliacoes());
            
            return Optional.of(destino);
        }
        
        return Optional.empty();
    }

    /**
     * deleta destino de viagem
     */
    public boolean excluirDestino(Long id) {
        return destinos.removeIf(d -> d.getId().equals(id));
    }

    /**
     * verifica se destino existe
     */
    public boolean destinoExiste(Long id) {
        return destinos.stream().anyMatch(d -> d.getId().equals(id));
    }

    /**
     * atualiza os dados de um destino existente
     */
    public Optional<Destino> atualizarDestino(Long id, Destino dadosAtualizados) {
        Optional<Destino> destinoOpt = buscarDestinoPorId(id);

        if (destinoOpt.isPresent()) {
            Destino destinoExistente = destinoOpt.get();

            // Atualiza apenas os campos editáveis
            destinoExistente.setNome(dadosAtualizados.getNome());
            destinoExistente.setLocalizacao(dadosAtualizados.getLocalizacao());
            destinoExistente.setDescricao(dadosAtualizados.getDescricao());

            // Mantém os dados de avaliação (não reinicia)
            return Optional.of(destinoExistente);
        }

        return Optional.empty();
    }
}
