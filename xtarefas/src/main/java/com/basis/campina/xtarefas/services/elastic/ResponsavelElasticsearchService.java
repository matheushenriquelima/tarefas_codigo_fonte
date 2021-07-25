package com.basis.campina.xtarefas.services.elastic;

import com.basis.campina.xtarefas.domain.document.ResponsavelDocument;
import com.basis.campina.xtarefas.repository.ResponsavelRepository;
import com.basis.campina.xtarefas.repository.TarefaRepository;
import com.basis.campina.xtarefas.repository.elastic.Reindexer;
import com.basis.campina.xtarefas.repository.elastic.ResponsavelElasticsearchRepository;
import com.basis.campina.xtarefas.services.dto.DominioFixoDTO;
import com.basis.campina.xtarefas.services.event.ResponsavelEvent;
import com.basis.campina.xtarefas.services.filtro.ResponsavelFiltro;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ResponsavelElasticsearchService implements Reindexer {

    private final ResponsavelRepository repository;
    private final ResponsavelElasticsearchRepository elasticsearchRepository;

    private final TarefaRepository tarefaRepository;

    public Page<ResponsavelDocument> search(ResponsavelFiltro filtro, Pageable pageable) {
        return elasticsearchRepository.search(filtro.getFilter(), pageable);
    }

    @TransactionalEventListener(fallbackExecution = true)
    @Transactional(readOnly = true)
    public void indexar(ResponsavelEvent event) {
        log.info("[XTAREFAS] Indexando Responsavel: {}", event.getId());
        ResponsavelDocument document = repository.getDocument(event.getId());
        processarResponsavelDocument(document, event.getId());
        elasticsearchRepository.save(document);
    }

    private void processarResponsavelDocument(ResponsavelDocument document, Long id){
        List<DominioFixoDTO> nomeTarefas = tarefaRepository.buscarNomesTarefas(id);
        document.setTarefas(Objects.isNull(nomeTarefas) ? "" : nomeTarefas.stream().map(DominioFixoDTO::getLabel).collect(Collectors.joining(", ")));
    }

    @Override
    public String getEntity() {
        return "responsaveis";
    }

    @Override
    public Page<ResponsavelDocument> reindexPage(Pageable pageable) throws IllegalAccessException {
        Page<ResponsavelDocument> documentsPage = repository.reindexPage(pageable);
        documentsPage.getContent().forEach(document -> {
            processarResponsavelDocument(document, document.getId());
        });
        return Reindexer.super.reindexPage(documentsPage.getPageable());
    }
}
