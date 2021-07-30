package com.basis.campina.xtarefas.test.builder;


import com.basis.campina.xtarefas.domain.Responsavel;
import com.basis.campina.xtarefas.repository.ResponsavelRepository;
import com.basis.campina.xtarefas.services.dto.ResponsavelDTO;
import com.basis.campina.xtarefas.services.mapper.ResponsavelMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Collection;

@Component
public class ResponsavelBuilder extends ConstrutorEntidade<Responsavel>{

    @Autowired
    private ResponsavelRepository repository;

    @Autowired
    private ResponsavelMapper mapper;

    @Override
    public Responsavel construirEntidade() throws ParseException {
        Responsavel responsavel = new Responsavel();
        responsavel.setNome("Responsavel");
        responsavel.setDataNasc(LocalDate.now());
        responsavel.setEmail("emailvalido@email.com");
        return responsavel;
    }
    @SneakyThrows
    public ResponsavelDTO construirDTO()  {
        return this.mapper.toDto(this.construir());
    }

    @SneakyThrows
    public ResponsavelDTO construirObjetoDTO() {
        return this.mapper.toDto(this.construirEntidade());
    }

    @Override
    protected Responsavel persistir(Responsavel entidade) {
        return repository.save(entidade);
    }

    @Override
    protected Collection<Responsavel> obterTodos() {
        return null;
    }

    @Override
    protected Responsavel obterPorId(Integer id) {
        return null;
    }

}
