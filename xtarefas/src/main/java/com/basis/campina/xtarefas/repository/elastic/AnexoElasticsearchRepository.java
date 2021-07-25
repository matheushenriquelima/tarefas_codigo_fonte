package com.basis.campina.xtarefas.repository.elastic;

import com.basis.campina.xtarefas.domain.document.AnexoDocument;

public interface AnexoElasticsearchRepository extends ElasticEntity<AnexoDocument, Long> {

    default Class<AnexoDocument> getEntityClass(){
        return AnexoDocument.class;
    }
}
