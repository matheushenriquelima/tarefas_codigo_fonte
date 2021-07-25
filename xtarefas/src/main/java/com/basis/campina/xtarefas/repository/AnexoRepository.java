package com.basis.campina.xtarefas.repository;


import com.basis.campina.xtarefas.domain.Anexo;
import com.basis.campina.xtarefas.domain.document.AnexoDocument;
import com.basis.campina.xtarefas.repository.elastic.Reindexer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AnexoRepository extends JpaRepository<Anexo, Long>, Reindexer {

    @Query(value="SELECT new com.basis.campina.xtarefas.domain.document.AnexoDocument(a.id,a.file,a.filename) FROM Anexo a"
            + " WHERE a.id = :id")
    AnexoDocument getDocument(@Param("id") Long id);

    @Override
    @Query(value = "SELECT new com.basis.campina.xtarefas.domain.document.AnexoDocument(a.id,a.file,a.filename) FROM Anexo a order by a.id")
    Page<AnexoDocument> reindexPage(Pageable pageable);

    @Override
    default String getEntity(){
        return "anexo";
    }

}
