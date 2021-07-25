package com.basis.campina.xtarefas.services.elastic;

import com.basis.campina.xtarefas.domain.document.TarefaDocument;
import com.basis.campina.xtarefas.repository.TarefaRepository;
import com.basis.campina.xtarefas.repository.elastic.TarefaElasticsearchRepository;
import com.basis.campina.xtarefas.services.event.TarefaEvent;
import com.basis.campina.xtarefas.services.filtro.TarefaFiltro;
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
public class TarefaElasticsearchService {

    private final TarefaRepository repository;
    private final TarefaElasticsearchRepository elasticsearchRepository;

    public Page<TarefaDocument> search(TarefaFiltro filtro, Pageable pageable) {
        return elasticsearchRepository.search(filtro.getFilter(), pageable);
    }

    @TransactionalEventListener(fallbackExecution = true)
    @Transactional(readOnly = true)
    public void indexar(TarefaEvent event) {
        log.info("[XTAREFAS] Indexando Tarefa: {}", event.getId());
        TarefaDocument document = repository.getDocument(event.getId());
        elasticsearchRepository.save(document);
    }

}
