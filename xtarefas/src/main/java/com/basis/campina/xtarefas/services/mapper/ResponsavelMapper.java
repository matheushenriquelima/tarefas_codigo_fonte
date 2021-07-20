package com.basis.campina.xtarefas.services.mapper;


import com.basis.campina.xtarefas.domain.Responsavel;
import com.basis.campina.xtarefas.services.dto.ResponsavelDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResponsavelMapper extends EntityMapper<ResponsavelDTO, Responsavel> {

}
