package com.basis.campina.xtarefas.services.elastic;

import com.basis.campina.xtarefas.domain.document.TarefaDocument;
import com.basis.campina.xtarefas.repository.AnexoRepository;
import com.basis.campina.xtarefas.repository.TarefaRepository;
import com.basis.campina.xtarefas.repository.elastic.Reindexer;
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

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class TarefaElasticsearchService implements Reindexer {

    private final TarefaRepository repository;
    private final TarefaElasticsearchRepository elasticsearchRepository;

    private final AnexoRepository anexoRepository;

    public Page<TarefaDocument> search(TarefaFiltro filtro, Pageable pageable) {
        return elasticsearchRepository.search(filtro.getFilter(), pageable);
    }

    @TransactionalEventListener(fallbackExecution = true)
    @Transactional(readOnly = true)
    public void indexar(TarefaEvent event) {
        log.info("[XTAREFAS] Indexando Tarefa: {}", event.getId());
        TarefaDocument document = repository.getDocument(event.getId());
        processarAnexoDocument(document);
        elasticsearchRepository.save(document);
    }

    private void processarAnexoDocument(TarefaDocument document){
        List<String> nomeAnexos = anexoRepository.buscarNomeAnexos(document.getId());
        document.setAnexos(Objects.isNull(nomeAnexos) ? "" : String.join(", ", nomeAnexos));
    }

    @Override
    public String getEntity() {
        return "tarefa";
    }

    @Override
    public Page<TarefaDocument> reindexPage(Pageable pageable) throws IllegalAccessException {
        Page<TarefaDocument> documentsPage = repository.reindexPage(pageable);
        documentsPage.getContent().forEach(this::processarAnexoDocument);
        return documentsPage;
    }

}
