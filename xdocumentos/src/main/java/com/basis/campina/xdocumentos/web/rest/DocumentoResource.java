package com.basis.campina.xdocumentos.web.rest;

import com.basis.campina.xdocumentos.services.DocumentoService;
import com.basis.campina.xdocumentos.services.dto.DocumentoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/documentos")
@RequiredArgsConstructor
public class DocumentoResource {

    private final DocumentoService service;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody List<DocumentoDTO> documentoDTOs) {
        service.salvarDocumentos(documentoDTOs);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{uuId}")
    public ResponseEntity<DocumentoDTO> obterDocumento(@PathVariable("uuId") String uuId){
        DocumentoDTO documentoDTO = service.getDocument(uuId);
        return ResponseEntity.ok(documentoDTO);
    }

}
