package com.basis.campina.xtarefas.web.rest;

import com.basis.campina.xtarefas.config.containers.ContainersFactory;
import com.basis.campina.xtarefas.domain.Tarefa;
import com.basis.campina.xtarefas.services.dto.TarefaDTO;
import com.basis.campina.xtarefas.services.elastic.TarefaElasticsearchService;
import com.basis.campina.xtarefas.services.event.TarefaEvent;
import com.basis.campina.xtarefas.services.filtro.TarefaFiltro;
import com.basis.campina.xtarefas.services.mapper.TarefaMapper;
import com.basis.campina.xtarefas.test.IntTestComum;
import com.basis.campina.xtarefas.test.builder.TarefaBuilder;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@Transactional
@Slf4j
@Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TarefaResourceIT extends IntTestComum {

    private final String API_TAREFA = "/api/tarefas";
    private final String API_TAREFA_ID = API_TAREFA + "/{id}";
    private final String API_SEARCH = API_TAREFA + "/_search";

    @Autowired
    private TarefaBuilder builder;

    @Autowired
    private TarefaElasticsearchService searchService;

    @Autowired
    private TarefaMapper mapper;

    @Container
    private ContainersFactory containersFactory = ContainersFactory.getInstance();

    @Test
    @SneakyThrows
    @DisplayName("Salvar Tarefa com sucesso")
    public void salvarTarefaComSucesso() {
        TarefaDTO dto = builder.construirObjetoDTO();

        getMockMvc().perform(post(API_TAREFA)
                        .contentType(TesteUtil.APPLICATION_JSON_UTF8)
                        .content(TesteUtil.convertObjectToJsonBytes(dto)))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @DisplayName("Atualizar Tarefa com sucesso")
    public void atualizarTarefaComSucesso() {
        TarefaDTO dto = builder.construirDTO();
        dto.setId(null);

        getMockMvc().perform(put(API_TAREFA)
                        .contentType(TesteUtil.APPLICATION_JSON_UTF8)
                        .content(TesteUtil.convertObjectToJsonBytes(dto)))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @DisplayName("Obter Tarefa por id com sucesso")
    public void obterTarefaPorId() {
        Tarefa tarefa = builder.construir();

        ResultActions resultActions = getMockMvc().perform(get(API_TAREFA_ID, tarefa.getId())
                        .contentType(TesteUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        String json = resultActions.andReturn().getResponse().getContentAsString();
        TarefaDTO tarefaEncontrado = getConverter().getObjectMapper().readValue(json,
                new TypeReference<TarefaDTO>() {
                });

        assertNotNull(tarefaEncontrado);
        assertEquals(tarefa.getId(), tarefaEncontrado.getId());
        assertEquals(tarefa.getNome(), tarefaEncontrado.getNome());
        assertEquals(tarefa.getDataInicio(), tarefaEncontrado.getDataInicio());
        assertEquals(tarefa.getDataConclusao(), tarefaEncontrado.getDataConclusao());
        assertEquals(tarefa.getStatus(), tarefaEncontrado.getStatus());
    }

    @Test
    @DisplayName("Buscar Tarefa por identificador inexistente")
    @SneakyThrows
    public void buscarTarefaPorIdInexistente() {

        Long idTarefaInexistente = 1020L;

        getMockMvc().perform(get(API_TAREFA_ID, idTarefaInexistente)
                .contentType(TesteUtil.APPLICATION_JSON_UTF8)).andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    @DisplayName("Deletar Tarefa por id com sucesso")
    public void DeletarTarefaPorId() {
        Tarefa tarefa = builder.construir();

        getMockMvc().perform(delete(API_TAREFA_ID, tarefa.getId())
                        .contentType(TesteUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        getMockMvc().perform(get(API_TAREFA_ID, tarefa.getId())
                        .contentType(TesteUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    @DisplayName("Buscar todos Tarefas")
    public void buscarTodasTarefas() {
        Tarefa tarefa = builder.construir();

        this.searchService.indexar(new TarefaEvent(tarefa.getId()));

        TarefaFiltro filtro = new TarefaFiltro();
        filtro.setQuery(tarefa.getNome());

        getMockMvc().perform(post(API_SEARCH).contentType(TesteUtil.APPLICATION_JSON_UTF8)
                        .content(TesteUtil.convertObjectToJsonBytes(filtro)))
                .andExpect(status().isOk());
    }

}
