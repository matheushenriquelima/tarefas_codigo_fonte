package com.basis.campina.xtarefas.services.feign;

import com.basis.campina.xtarefas.services.dto.AnexoDTO;
import com.basis.campina.xtarefas.services.dto.DocumentoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "xdocs",url = "${application.feign.url-documents}")
public interface DocumentoClient {

    @PostMapping("/api/documentos")
    ResponseEntity<Void> salvar(List<DocumentoDTO> documentoDTOS);

    @GetMapping("/api/documentos/{uuId}")
    ResponseEntity<DocumentoDTO> obterDocumento(@PathVariable("uuId") String uuId);
}
