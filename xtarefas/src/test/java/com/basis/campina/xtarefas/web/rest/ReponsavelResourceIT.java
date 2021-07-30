package com.basis.campina.xtarefas.web.rest;

import com.basis.campina.xtarefas.config.containers.ContainersFactory;
import com.basis.campina.xtarefas.domain.Responsavel;
import com.basis.campina.xtarefas.services.dto.ResponsavelDTO;
import com.basis.campina.xtarefas.services.elastic.ResponsavelElasticsearchService;
import com.basis.campina.xtarefas.services.event.ResponsavelEvent;
import com.basis.campina.xtarefas.services.filtro.ResponsavelFiltro;
import com.basis.campina.xtarefas.services.mapper.ResponsavelMapper;
import com.basis.campina.xtarefas.test.IntTestComum;
import com.basis.campina.xtarefas.test.builder.ResponsavelBuilder;
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
public class ReponsavelResourceIT extends IntTestComum {

    private final String API_RESPONSAVEL = "/api/responsaveis";
    private final String API_RESPONSAVEL_ID = API_RESPONSAVEL + "/{id}";
    private final String API_SEARCH = API_RESPONSAVEL + "/_search";

    @Autowired
    private ResponsavelBuilder builder;

    @Autowired
    private ResponsavelElasticsearchService searchService;

    @Autowired
    private ResponsavelMapper mapper;

    @Container
    private ContainersFactory containersFactory = ContainersFactory.getInstance();

    @Test
    @SneakyThrows
    @DisplayName("Salvar Responsável com sucesso")
    public void salvarResponsavelComSucesso() {
        ResponsavelDTO dto = builder.construirObjetoDTO();

        getMockMvc().perform(post(API_RESPONSAVEL)
                        .contentType(TesteUtil.APPLICATION_JSON_UTF8)
                        .content(TesteUtil.convertObjectToJsonBytes(dto)))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @DisplayName("Atualizar Responsável com sucesso")
    public void atualizarResponsavelComSucesso() {
        ResponsavelDTO dto = builder.construirDTO();
        dto.setId(null);

        getMockMvc().perform(put(API_RESPONSAVEL)
                        .contentType(TesteUtil.APPLICATION_JSON_UTF8)
                        .content(TesteUtil.convertObjectToJsonBytes(dto)))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @DisplayName("Obter Responsável por id com sucesso")
    public void obterResponsavelPorId() {
        Responsavel responsavel = builder.construir();

        ResultActions resultActions = getMockMvc().perform(get(API_RESPONSAVEL_ID, responsavel.getId())
                        .contentType(TesteUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        String json = resultActions.andReturn().getResponse().getContentAsString();
        ResponsavelDTO responsavelEncontrado = getConverter().getObjectMapper().readValue(json,
                new TypeReference<ResponsavelDTO>() {
                });

        assertNotNull(responsavelEncontrado);
        assertEquals(responsavel.getId(), responsavelEncontrado.getId());
        assertEquals(responsavel.getNome(), responsavelEncontrado.getNome());
        assertEquals(responsavel.getEmail(), responsavelEncontrado.getEmail());
        assertEquals(responsavel.getDataNasc(), responsavelEncontrado.getDataNasc());
    }

    @Test
    @DisplayName("Buscar Responsavel por identificador inexistente")
    @SneakyThrows
    public void buscarResponsavelPorIdInexistente() {

        Long idResponsavelInexistente = 1020L;

        getMockMvc().perform(get(API_RESPONSAVEL_ID, idResponsavelInexistente)
                .contentType(TesteUtil.APPLICATION_JSON_UTF8)).andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    @DisplayName("Deletar Responsável por id com sucesso")
    public void DeletarResponsavelPorId() {
        Responsavel responsavel = builder.construir();

        getMockMvc().perform(delete(API_RESPONSAVEL_ID, responsavel.getId())
                        .contentType(TesteUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        getMockMvc().perform(get(API_RESPONSAVEL_ID, responsavel.getId())
                        .contentType(TesteUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    @SneakyThrows
    @DisplayName("Buscar todos responsaveis")
    public void buscarTodosResponsaveis() {
        Responsavel responsavel = builder.construir();

        this.searchService.indexar(new ResponsavelEvent(responsavel.getId()));

        ResponsavelFiltro filtro = new ResponsavelFiltro();
        filtro.setQuery(responsavel.getNome());

        getMockMvc().perform(post(API_SEARCH).contentType(TesteUtil.APPLICATION_JSON_UTF8)
                        .content(TesteUtil.convertObjectToJsonBytes(filtro)))
                .andExpect(status().isOk());
    }

}
