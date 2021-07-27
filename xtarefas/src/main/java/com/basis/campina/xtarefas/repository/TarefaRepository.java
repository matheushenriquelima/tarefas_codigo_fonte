package com.basis.campina.xtarefas.repository;


import com.basis.campina.xtarefas.domain.Tarefa;
import com.basis.campina.xtarefas.domain.document.TarefaDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    @Query(value="SELECT new com.basis.campina.xtarefas.domain.document.TarefaDocument(t.id,t.nome,t.dataConclusao,t.status,t.dataInicio, t.responsavel.nome) FROM Tarefa t"
            + " WHERE t.id = :id")
    TarefaDocument getDocument(@Param("id") Long id);

    @Query(value = "SELECT t.nome FROM Tarefa t WHERE t.responsavel.id = :id")
    List<String> buscarNomesTarefas(@Param("id") Long id);

    @Query(value="SELECT new com.basis.campina.xtarefas.domain.document.TarefaDocument(t.id,t.nome,t.dataConclusao,t.status,t.dataInicio, t.responsavel.nome) FROM Tarefa t")
    Page<TarefaDocument> reindexPage(Pageable pageable);

}
