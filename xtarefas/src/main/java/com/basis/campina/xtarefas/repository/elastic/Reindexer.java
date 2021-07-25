package com.basis.campina.xtarefas.repository.elastic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface Reindexer {

    String getEntity();

    default <T>Page<T> reindexPage(Pageable pageable) throws IllegalAccessException {
        throw new IllegalAccessException("Método não implementado.");
    }


}
