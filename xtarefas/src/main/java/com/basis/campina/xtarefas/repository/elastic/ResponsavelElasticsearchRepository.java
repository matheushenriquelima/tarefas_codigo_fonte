package com.basis.campina.xtarefas.repository.elastic;

import com.basis.campina.xtarefas.domain.document.ResponsavelDocument;

public interface ResponsavelElasticsearchRepository extends ElasticEntity<ResponsavelDocument, Long> {

    default Class<ResponsavelDocument> getEntityClass(){
        return ResponsavelDocument.class;
    }
}
