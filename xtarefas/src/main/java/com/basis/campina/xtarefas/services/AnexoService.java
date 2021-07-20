package com.basis.campina.xtarefas.services;


import com.basis.campina.xtarefas.repository.AnexoRepository;
import com.basis.campina.xtarefas.services.dto.AnexoDTO;
import com.basis.campina.xtarefas.services.exceptions.RegraNegocioException;
import com.basis.campina.xtarefas.services.mapper.AnexoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AnexoService {

    private final AnexoRepository repository;

    private final AnexoMapper mapper;

    @Transactional(readOnly = true)
    public AnexoDTO obterPorId(Long id) {
        return mapper.toDto(repository.findById(id).orElseThrow(()-> new RegraNegocioException("Tarefa não existe")));
    }

}
