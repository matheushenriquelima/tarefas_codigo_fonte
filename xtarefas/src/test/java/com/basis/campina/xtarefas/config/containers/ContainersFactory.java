package com.basis.campina.xtarefas.config.containers;

import org.testcontainers.elasticsearch.ElasticsearchContainer;

import java.util.Objects;

public class ContainersFactory {
    
    private static ContainersFactory containersFactory;
    
    private static ElasticsearchContainer elasticSearchFactory;
    
    public static ContainersFactory getInstance(){
        if(Objects.isNull(elasticSearchFactory)){
            elasticSearchFactory = ElasticSearchFactory.getInstance();
        }
        if(Objects.isNull(containersFactory)){
            containersFactory = new ContainersFactory();
        }
        return containersFactory;
    }
    
}
