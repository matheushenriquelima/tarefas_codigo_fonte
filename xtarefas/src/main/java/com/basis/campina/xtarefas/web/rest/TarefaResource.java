package com.basis.campina.xtarefas.web.rest;

import com.basis.campina.xtarefas.domain.document.TarefaDocument;
import com.basis.campina.xtarefas.services.TarefaService;
import com.basis.campina.xtarefas.services.dto.TarefaDTO;
import com.basis.campina.xtarefas.services.elastic.TarefaElasticsearchService;
import com.basis.campina.xtarefas.services.filtro.TarefaFiltro;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tarefas")
@RequiredArgsConstructor
@Slf4j
public class TarefaResource {

    private final TarefaService service;

    private final TarefaElasticsearchService elasticsearchService;

    @GetMapping("/{id}")
    public ResponseEntity<TarefaDTO> obterPorId(@PathVariable Long id) {
        log.info("[XTAREFAS] Requisição REST para obter Tarefa por id {}", id);
        return ResponseEntity.ok(service.obterPorId(id));
    }

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody TarefaDTO tarefaDTO){
        log.info("[XTAREFAS] Requisição REST para salvar Tarefa {}", tarefaDTO);
        service.salvar(tarefaDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> alterar(@RequestBody TarefaDTO tarefaDTO){
        log.info("[XTAREFAS] Requisição REST para alterar Tarefa {}", tarefaDTO);
        service.salvar(tarefaDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        log.info("[XTAREFAS] Requisição REST para deletar Tarefa por id {}", id);
        service.deletar(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/_search")
    public ResponseEntity<Page<TarefaDocument>> search(@RequestBody TarefaFiltro filter, Pageable pageable) {
        Page<TarefaDocument> tarefas = elasticsearchService.search(filter, pageable);
        return new ResponseEntity<>(tarefas, HttpStatus.OK);
    }

}
