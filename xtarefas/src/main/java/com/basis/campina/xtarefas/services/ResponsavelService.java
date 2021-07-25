package com.basis.campina.xtarefas.services;


import com.basis.campina.xtarefas.domain.Responsavel;
import com.basis.campina.xtarefas.repository.ResponsavelRepository;
import com.basis.campina.xtarefas.services.dto.ResponsavelDTO;
import com.basis.campina.xtarefas.services.event.ResponsavelEvent;
import com.basis.campina.xtarefas.services.exceptions.RegraNegocioException;
import com.basis.campina.xtarefas.services.mapper.ResponsavelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ResponsavelService {

    private final ResponsavelRepository repository;

    private final ResponsavelMapper mapper;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional(readOnly = true)
    public ResponsavelDTO obterPorId(Long id) {
        return mapper.toDto(repository.findById(id).orElseThrow(()-> new RegraNegocioException("Tarefa não existe")));
    }

    public ResponsavelDTO salvar(ResponsavelDTO dto) {
        Responsavel responsavel = repository.save(mapper.toEntity(dto));
        applicationEventPublisher.publishEvent(new ResponsavelEvent(responsavel.getId()));
        return mapper.toDto(responsavel);
    }

    public void deletar(Long id){
        obterPorId(id);
        repository.deleteById(id);
    }

}
