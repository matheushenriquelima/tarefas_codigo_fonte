package com.basis.campina.xtarefas.services.elastic;

import com.basis.campina.xtarefas.domain.document.AnexoDocument;
import com.basis.campina.xtarefas.repository.AnexoRepository;
import com.basis.campina.xtarefas.repository.elastic.AnexoElasticsearchRepository;
import com.basis.campina.xtarefas.services.event.AnexoEvent;
import com.basis.campina.xtarefas.services.filtro.AnexoFiltro;
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
public class AnexoElasticsearchService {

    private final AnexoRepository repository;
    private final AnexoElasticsearchRepository elasticsearchRepository;

    public Page<AnexoDocument> search(AnexoFiltro filtro, Pageable pageable) {
        return elasticsearchRepository.search(filtro.getFilter(), pageable);
    }

    @TransactionalEventListener(fallbackExecution = true)
    @Transactional(readOnly = true)
    public void indexar(AnexoEvent event) {
        log.info("[XTAREFAS] Indexando anexo: {}", event.getId());
        AnexoDocument document = repository.getDocument(event.getId());
        elasticsearchRepository.save(document);
    }

}
