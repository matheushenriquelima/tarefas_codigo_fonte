package com.basis.campina.xtarefas.repository;


import com.basis.campina.xtarefas.domain.Tarefa;
import com.basis.campina.xtarefas.domain.document.AnexoDocument;
import com.basis.campina.xtarefas.domain.document.TarefaDocument;
import com.basis.campina.xtarefas.services.dto.DominioFixoDTO;
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

    @Query(value = "SELECT new com.basis.campina.xtarefas.services.dto.DominioFixoDTO(t.id,t.nome) FROM Tarefa t WHERE t.responsavel.id = :id")
    List<DominioFixoDTO> buscarNomesTarefas(@Param("id") Long id);

}
