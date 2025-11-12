package com.agencia.viagens.controller;

import com.agencia.viagens.model.AvaliacaoRequest;
import com.agencia.viagens.model.Destino;
import com.agencia.viagens.service.DestinoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/destinos")
public class DestinoController {

    @Autowired
    private DestinoService destinoService;

    /**
     * Endpoint 1: Cadastro de Destinos de Viagem
     * POST /destinos
     */
    @PostMapping
    public ResponseEntity<Destino> cadastrarDestino(@RequestBody Destino destino) {
        try {
            Destino novoDestino = destinoService.cadastrarDestino(destino);
            return new ResponseEntity<>(novoDestino, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint 2: Listagem de Destinos de Viagem
     * GET /destinos
     */
    @GetMapping
    public ResponseEntity<List<Destino>> listarDestinos() {
        List<Destino> destinos = destinoService.listarTodosDestinos();
        return new ResponseEntity<>(destinos, HttpStatus.OK);
    }

    /**
     * Endpoint 3: Pesquisa de Destinos
     * GET /destinos/pesquisar?termo={nome ou localização}
     */
    @GetMapping("/pesquisar")
    public ResponseEntity<List<Destino>> pesquisarDestinos(@RequestParam(required = false) String termo) {
        List<Destino> destinos = destinoService.pesquisarDestinos(termo);
        return new ResponseEntity<>(destinos, HttpStatus.OK);
    }

    /**
     * Endpoint 4: Visualização de Informações Detalhadas
     * GET /destinos/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Destino> buscarDestinoPorId(@PathVariable Long id) {
        Optional<Destino> destino = destinoService.buscarDestinoPorId(id);
        
        if (destino.isPresent()) {
            return new ResponseEntity<>(destino.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint 5: Avaliação de Destino de Viagens
     * PATCH /destinos/{id}/avaliar
     */
    @PatchMapping("/{id}/avaliar")
    public ResponseEntity<Destino> avaliarDestino(
            @PathVariable Long id, 
            @RequestBody AvaliacaoRequest avaliacaoRequest) {
        try {
            Optional<Destino> destinoAtualizado = destinoService.avaliarDestino(id, avaliacaoRequest.getNota());
            
            if (destinoAtualizado.isPresent()) {
                return new ResponseEntity<>(destinoAtualizado.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint 6: Exclusão de Destinos de Viagem
     * DELETE /destinos/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirDestino(@PathVariable Long id) {
        boolean excluido = destinoService.excluirDestino(id);
        
        if (excluido) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
    * Endpoint 7: Atualização de Destino de Viagem
    * PUT /destinos/{id}
    */
    @PutMapping("/{id}")
    public ResponseEntity<Destino> atualizarDestino(
            @PathVariable Long id,
            @RequestBody Destino destinoAtualizado) {

        try {
            Optional<Destino> destino = destinoService.atualizarDestino(id, destinoAtualizado);

            if (destino.isPresent()) {
                return new ResponseEntity<>(destino.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
