package com.basis.campina.xtarefas.test.builder;


import com.basis.campina.xtarefas.domain.Tarefa;
import com.basis.campina.xtarefas.repository.TarefaRepository;
import com.basis.campina.xtarefas.services.dto.TarefaDTO;
import com.basis.campina.xtarefas.services.mapper.TarefaMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Collection;

@Component
public class TarefaBuilder extends ConstrutorEntidade<Tarefa>{

    @Autowired
    private TarefaRepository repository;

    @Autowired
    private TarefaMapper mapper;

    @Autowired
    private AnexoBuilder anexoBuilder;

    @Override
    public Tarefa construirEntidade() throws ParseException {
        Tarefa tarefa  =  new Tarefa();
        tarefa.setDataInicio(LocalDate.now());
        tarefa.setDataConclusao(LocalDate.now());
        tarefa.setStatus("Concluido");
        tarefa.setNome("Elaborar documento");
        return tarefa;
    }

    @SneakyThrows
    public TarefaDTO construirDTO()  {
        return this.mapper.toDto(this.construir());
    }

    @SneakyThrows
    public TarefaDTO construirObjetoDTO(){
        return mapper.toDto(construirEntidade());
    }

    @Override
    protected Tarefa persistir(Tarefa entidade) {
        return repository.save(entidade);
    }

    @Override
    protected Collection<Tarefa> obterTodos() {
        return null;
    }

    @Override
    protected Tarefa obterPorId(Integer id) {
        return null;
    }

}
