package com.basis.campina.xtarefas.services.mapper;


import com.basis.campina.xtarefas.domain.Tarefa;
import com.basis.campina.xtarefas.services.dto.TarefaDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ResponsavelMapper.class, AnexoMapper.class})
public interface TarefaMapper extends EntityMapper<TarefaDTO, Tarefa> {

}
