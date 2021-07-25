package com.basis.campina.xtarefas.services;


import com.basis.campina.xtarefas.domain.Anexo;
import com.basis.campina.xtarefas.repository.AnexoRepository;
import com.basis.campina.xtarefas.services.dto.AnexoDTO;
import com.basis.campina.xtarefas.services.dto.DocumentoDTO;
import com.basis.campina.xtarefas.services.event.AnexoEvent;
import com.basis.campina.xtarefas.services.exceptions.RegraNegocioException;
import com.basis.campina.xtarefas.services.feign.DocumentoClient;
import com.basis.campina.xtarefas.services.mapper.AnexoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AnexoService {

    private final AnexoRepository repository;

    private final AnexoMapper mapper;

    private final DocumentoClient documentoClient;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional(readOnly = true)
    public AnexoDTO obterPorId(Long id) {
        return mapper.toDto(repository.findById(id).orElseThrow(()-> new RegraNegocioException("Tarefa não existe")));
    }

    public DocumentoDTO obterDocumento(String uuId){
        return documentoClient.obterDocumento(uuId).getBody();
    }

    public void salvarDocumentos(List<AnexoDTO> anexoDTOs){
        List<DocumentoDTO> documentoDTOS = anexoDTOs.stream().map(AnexoDTO::getDocumento).collect(Collectors.toList());
        documentoClient.salvar(documentoDTOS);
    }

    public void lancarEvento(List<AnexoDTO> anexos){
        anexos.forEach(anexo -> applicationEventPublisher.publishEvent(new AnexoEvent(anexo.getId())));
    }

}
