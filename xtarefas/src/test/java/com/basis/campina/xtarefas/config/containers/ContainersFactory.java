package com.basis.campina.xtarefas.config.containers;

import org.testcontainers.junit.jupiter.Container;

import java.util.Objects;

public class ContainersFactory {

    private static ContainersFactory containersFactory;

    @Container
    private static CustomElasticContainer customElasticContainer;
    
    public static ContainersFactory getInstance(){
        if(Objects.isNull(customElasticContainer)){
            customElasticContainer = ElasticSearchFactory.getInstance();
        }
        if(Objects.isNull(containersFactory)){
            containersFactory = new ContainersFactory();
        }
        return containersFactory;
    }
    
}
