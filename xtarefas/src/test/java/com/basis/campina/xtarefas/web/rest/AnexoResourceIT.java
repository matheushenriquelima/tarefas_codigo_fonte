package com.basis.campina.xtarefas.web.rest;

import com.basis.campina.xtarefas.config.containers.ContainersFactory;
import com.basis.campina.xtarefas.domain.Anexo;
import com.basis.campina.xtarefas.services.dto.AnexoDTO;
import com.basis.campina.xtarefas.services.elastic.AnexoElasticsearchService;
import com.basis.campina.xtarefas.services.event.AnexoEvent;
import com.basis.campina.xtarefas.services.filtro.AnexoFiltro;
import com.basis.campina.xtarefas.services.mapper.AnexoMapper;
import com.basis.campina.xtarefas.test.IntTestComum;
import com.basis.campina.xtarefas.test.builder.AnexoBuilder;
import com.basis.campina.xtarefas.util.TesteUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@Transactional
@Slf4j
@Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AnexoResourceIT extends IntTestComum {

    private final String API_ANEXO = "/api/anexos";
    private final String API_ANEXO_ID = API_ANEXO + "/{id}";
    private final String API_SEARCH = API_ANEXO + "/_search";

    @Autowired
    private AnexoBuilder builder;

    @Autowired
    private AnexoElasticsearchService searchService;

    @Autowired
    private AnexoMapper mapper;

    @Container
    private ContainersFactory containersFactory = ContainersFactory.getInstance();

    @Test
    @SneakyThrows
    @DisplayName("Obter Anexo por id com sucesso")
    public void obterAnexoPorId() {
        Anexo anexo = builder.construir();

        ResultActions resultActions = getMockMvc().perform(get(API_ANEXO_ID, anexo.getId())
                        .contentType(TesteUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        String json = resultActions.andReturn().getResponse().getContentAsString();
        AnexoDTO responsavelEncontrado = getConverter().getObjectMapper().readValue(json,
                new TypeReference<AnexoDTO>() {
                });

        assertNotNull(responsavelEncontrado);
        assertEquals(anexo.getId(), responsavelEncontrado.getId());
        assertEquals(anexo.getFilename(), responsavelEncontrado.getFilename());
        assertEquals(anexo.getChave(), responsavelEncontrado.getDocumento().getUuId());
    }

    @Test
    @DisplayName("Buscar Anexo por identificador inexistente")
    @SneakyThrows
    public void buscarAnexoPorIdInexistente() {

        Long idAnexoInexistente = 1020L;

        getMockMvc().perform(get(API_ANEXO_ID, idAnexoInexistente)
                .contentType(TesteUtil.APPLICATION_JSON_UTF8)).andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    @DisplayName("Buscar todos Anexos")
    public void buscarTodosResponsaveis() {
        Anexo anexo = builder.construir();

        this.searchService.indexar(new AnexoEvent(anexo.getId()));

        AnexoFiltro filtro = new AnexoFiltro();
        filtro.setQuery(anexo.getFilename());

        getMockMvc().perform(post(API_SEARCH).contentType(TesteUtil.APPLICATION_JSON_UTF8)
                        .content(TesteUtil.convertObjectToJsonBytes(filtro)))
                .andExpect(status().isOk());
    }

}
