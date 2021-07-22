package com.basis.campina.xtarefas.services.mapper;


import com.basis.campina.xtarefas.domain.Anexo;
import com.basis.campina.xtarefas.services.dto.AnexoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AnexoMapper extends EntityMapper<AnexoDTO, Anexo> {

    @Override
    @Mapping(source = "tarefa.id", target = "idTarefa")
    @Mapping(source = "file", target = "documento.file")
    @Mapping(source = "chave", target = "documento.uuId")
    AnexoDTO toDto(Anexo entity);

    @Override
    @Mapping(source = "idTarefa", target = "tarefa.id")
    @Mapping(source = "documento.file", target = "file")
    @Mapping(source = "documento.uuId", target = "chave")
    Anexo toEntity(AnexoDTO dto);

}
