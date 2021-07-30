package com.basis.campina.xtarefas.config;

import com.basis.campina.xtarefas.services.dto.DocumentoDTO;
import com.basis.campina.xtarefas.services.feign.DocumentoClient;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Configuration
public class MockConfigutarion {

    @MockBean
    private DocumentoClient documentClient;

    @PostConstruct
    public void mock(){
        Mockito.when(documentClient.salvar(Collections.singletonList(new DocumentoDTO()))).thenReturn(null);
    }

}
