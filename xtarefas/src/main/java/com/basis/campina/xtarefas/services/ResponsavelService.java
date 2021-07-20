package com.basis.campina.xtarefas.services;


import com.basis.campina.xtarefas.repository.ResponsavelRepository;
import com.basis.campina.xtarefas.services.dto.ResponsavelDTO;
import com.basis.campina.xtarefas.services.exceptions.RegraNegocioException;
import com.basis.campina.xtarefas.services.mapper.ResponsavelMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ResponsavelService {

    private final ResponsavelRepository repository;

    private final ResponsavelMapper mapper;

    @Transactional(readOnly = true)
    public ResponsavelDTO obterPorId(Long id) {
        return mapper.toDto(repository.findById(id).orElseThrow(()-> new RegraNegocioException("Tarefa não existe")));
    }

    public ResponsavelDTO salvar(ResponsavelDTO dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    public void deletar(Long id){
        obterPorId(id);
        repository.deleteById(id);
    }

}
