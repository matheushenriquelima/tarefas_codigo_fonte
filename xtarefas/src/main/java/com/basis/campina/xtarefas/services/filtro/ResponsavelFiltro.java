package com.basis.campina.xtarefas.services.filtro;

import lombok.Getter;
import lombok.Setter;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ResponsavelFiltro extends DefaultFilter implements Serializable, BaseFilter {

    private static final long serialVersionUID = 4980515323702820328L;

    @Override
    public BoolQueryBuilder getFilter() {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        List<String> fields = new ArrayList<>();
        filterFields(fields, query, queryBuilder, "nome", "email", "tarefas");
        addMustTermQuery(queryBuilder, "dataNasc", queryDate);

        return  queryBuilder;
    }

}
