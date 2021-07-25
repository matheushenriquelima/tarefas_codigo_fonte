package com.basis.campina.xtarefas.repository.elastic;

import com.basis.campina.xtarefas.domain.document.TarefaDocument;

public interface TarefaElasticsearchRepository extends ElasticEntity<TarefaDocument, Long> {

    default Class<TarefaDocument> getEntityClass(){
        return TarefaDocument.class;
    }
}
