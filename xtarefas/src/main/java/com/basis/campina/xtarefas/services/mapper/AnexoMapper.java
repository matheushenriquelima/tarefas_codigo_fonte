package com.basis.campina.xtarefas.services.mapper;


import com.basis.campina.xtarefas.domain.Anexo;
import com.basis.campina.xtarefas.services.dto.AnexoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AnexoMapper extends EntityMapper<AnexoDTO, Anexo> {

    @Override
    @Mapping(source = "tarefa.id", target = "idTarefa")
    AnexoDTO toDto(Anexo entity);

    @Override
    @Mapping(source = "idTarefa", target = "tarefa.id")
    Anexo toEntity(AnexoDTO dto);

}
