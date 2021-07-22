package com.basis.campina.xtarefas.web.rest;

import com.basis.campina.xtarefas.services.AnexoService;
import com.basis.campina.xtarefas.services.dto.AnexoDTO;
import com.basis.campina.xtarefas.services.dto.DocumentoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/anexos")
@RequiredArgsConstructor
@Slf4j
public class AnexoResource {

    private final AnexoService service;

    @GetMapping("/{id}")
    public ResponseEntity<AnexoDTO> obterPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obterPorId(id));
    }

    @GetMapping("/documento/{uuId}")
    public ResponseEntity<DocumentoDTO> obterDocumento(@PathVariable String uuId) {
        return ResponseEntity.ok(service.obterDocumento(uuId));
    }

}
