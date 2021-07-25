package com.basis.campina.xtarefas.services.filtro;

import lombok.Getter;
import lombok.Setter;
import org.elasticsearch.index.query.BoolQueryBuilder;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class TarefaFiltro implements Serializable, BaseFilter {

    private static final long serialVersionUID = 4980515323702820328L;

    private String nome;

    private String status;

    private LocalDate dataInicio;

    private LocalDate dataConclusao;

    private String responsavel;

    private String anexos;

    @Override
    public BoolQueryBuilder getFilter() {
        return null;
    }
}
