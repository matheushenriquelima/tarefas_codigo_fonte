package com.basis.campina.xtarefas.services.mapper;


import com.basis.campina.xtarefas.domain.Anexo;
import com.basis.campina.xtarefas.services.dto.AnexoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnexoMapper extends EntityMapper<AnexoDTO, Anexo> {

}
