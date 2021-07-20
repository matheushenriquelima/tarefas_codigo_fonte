package com.basis.campina.xtarefas.services;


import com.basis.campina.xtarefas.repository.TarefaRepository;
import com.basis.campina.xtarefas.services.dto.TarefaDTO;
import com.basis.campina.xtarefas.services.exceptions.RegraNegocioException;
import com.basis.campina.xtarefas.services.mapper.TarefaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TarefaService {

    private final TarefaRepository repository;

    private final TarefaMapper mapper;

    @Transactional(readOnly = true)
    public TarefaDTO obterPorId(Long id) {
        return mapper.toDto(repository.findById(id).orElseThrow(()-> new RegraNegocioException("Tarefa não existe")));
    }

    public TarefaDTO salvar(TarefaDTO dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    public void deletar(Long id){
        obterPorId(id);
        repository.deleteById(id);
    }

}
