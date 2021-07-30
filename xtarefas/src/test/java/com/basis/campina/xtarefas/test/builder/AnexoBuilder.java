package com.basis.campina.xtarefas.test.builder;


import com.basis.campina.xtarefas.domain.Anexo;
import com.basis.campina.xtarefas.repository.AnexoRepository;
import com.basis.campina.xtarefas.services.mapper.AnexoMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Collection;
import java.util.UUID;

@Component
public class AnexoBuilder extends ConstrutorEntidade<Anexo>{

    @Autowired
    private AnexoRepository repository;

    @Autowired
    private AnexoMapper mapper;

    @Override
    public Anexo construirEntidade() throws ParseException {
        Anexo anexo  =  new Anexo();
        anexo.setChave(UUID.randomUUID().toString());
        anexo.setFile("arquivo");
        anexo.setFilename("arquivo da tarefa");
        return anexo;
    }

    @SneakyThrows
    public Anexo construirObjeto(){
        return construirEntidade();
    }

    @Override
    protected Anexo persistir(Anexo entidade) {
        return repository.save(entidade);
    }

    @Override
    protected Collection<Anexo> obterTodos() {
        return null;
    }

    @Override
    protected Anexo obterPorId(Integer id) {
        return null;
    }

}
