package com.basis.campina.xtarefas.web.rest;

import com.basis.campina.xtarefas.domain.document.ResponsavelDocument;
import com.basis.campina.xtarefas.services.ResponsavelService;
import com.basis.campina.xtarefas.services.dto.ResponsavelDTO;
import com.basis.campina.xtarefas.services.elastic.ResponsavelElasticsearchService;
import com.basis.campina.xtarefas.services.filtro.ResponsavelFiltro;
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
@RequestMapping("/api/responsaveis")
@RequiredArgsConstructor
@Slf4j
public class ResponsavelResource {

    private final ResponsavelService service;

    private final ResponsavelElasticsearchService elasticsearchService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponsavelDTO> obterPorId(@PathVariable Long id) {
        log.info("[XTAREFAS] Requisição REST para obter Responsavel por id {}", id);
        return ResponseEntity.ok(service.obterPorId(id));
    }

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody ResponsavelDTO responsavelDTO){
        log.info("[XTAREFAS] Requisição REST para salvar Responsavel {}", responsavelDTO);
        service.salvar(responsavelDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> alterar(@RequestBody ResponsavelDTO responsavelDTO){
        log.info("[XTAREFAS] Requisição REST para alterar Responsavel {}", responsavelDTO);
        service.salvar(responsavelDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        log.info("[XTAREFAS] Requisição REST para deletar Responsavel por id {}", id);
        service.deletar(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/_search")
    public ResponseEntity<Page<ResponsavelDocument>> search(@RequestBody ResponsavelFiltro filter, Pageable pageable) {
        Page<ResponsavelDocument> responsaveis = elasticsearchService.search(filter, pageable);
        return new ResponseEntity<>(responsaveis, HttpStatus.OK);
    }

}
