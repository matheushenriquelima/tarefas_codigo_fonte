package com.basis.campina.xtarefas.repository;


import com.basis.campina.xtarefas.domain.Responsavel;
import com.basis.campina.xtarefas.domain.document.ResponsavelDocument;
import com.basis.campina.xtarefas.services.dto.DominioFixoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ResponsavelRepository extends JpaRepository<Responsavel, Long> {

    @Query(value="SELECT new com.basis.campina.xtarefas.domain.document.ResponsavelDocument(r.id,r.nome,r.dataNasc,r.email) FROM Responsavel r"
            + " WHERE r.id = :id")
    ResponsavelDocument getDocument(@Param("id") Long id);

    @Query(value="SELECT new com.basis.campina.xtarefas.domain.document.ResponsavelDocument(r.id,r.nome,r.dataNasc,r.email) FROM Responsavel r")
    Page<ResponsavelDocument> reindexPage(Pageable pageable);

}
