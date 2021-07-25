package com.basis.campina.xtarefas.services.elastic;

import com.basis.campina.xtarefas.domain.document.ResponsavelDocument;
import com.basis.campina.xtarefas.repository.ResponsavelRepository;
import com.basis.campina.xtarefas.repository.elastic.ResponsavelElasticsearchRepository;
import com.basis.campina.xtarefas.services.event.ResponsavelEvent;
import com.basis.campina.xtarefas.services.filtro.ResponsavelFiltro;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ResponsavelElasticsearchService {

    private final ResponsavelRepository repository;
    private final ResponsavelElasticsearchRepository elasticsearchRepository;

    public Page<ResponsavelDocument> search(ResponsavelFiltro filtro, Pageable pageable) {
        return elasticsearchRepository.search(filtro.getFilter(), pageable);
    }

    @TransactionalEventListener(fallbackExecution = true)
    @Transactional(readOnly = true)
    public void indexar(ResponsavelEvent event) {
        log.info("[XTAREFAS] Indexando Responsavel: {}", event.getId());
        ResponsavelDocument document = repository.getDocument(event.getId());
        elasticsearchRepository.save(document);
    }

}
