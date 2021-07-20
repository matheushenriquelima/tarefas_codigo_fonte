package com.basis.campina.xtarefas.web.rest;

import com.basis.campina.xtarefas.services.TarefaService;
import com.basis.campina.xtarefas.services.dto.TarefaDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @GetMapping("/{id}")
    public ResponseEntity<TarefaDTO> obterPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obterPorId(id));
    }

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody TarefaDTO tarefaDTO){
        service.salvar(tarefaDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> alterar(@RequestBody TarefaDTO tarefaDTO){
        service.salvar(tarefaDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        service.deletar(id);
        return ResponseEntity.ok().build();
    }

}
