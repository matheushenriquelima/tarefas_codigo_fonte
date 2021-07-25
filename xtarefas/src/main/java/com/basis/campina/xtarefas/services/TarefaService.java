package com.basis.campina.xtarefas.services;


import com.basis.campina.xtarefas.domain.Anexo;
import com.basis.campina.xtarefas.domain.Tarefa;
import com.basis.campina.xtarefas.repository.TarefaRepository;
import com.basis.campina.xtarefas.services.dto.AnexoDTO;
import com.basis.campina.xtarefas.services.dto.TarefaDTO;
import com.basis.campina.xtarefas.services.event.TarefaEvent;
import com.basis.campina.xtarefas.services.exceptions.RegraNegocioException;
import com.basis.campina.xtarefas.services.mapper.TarefaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TarefaService {

    private final TarefaRepository repository;

    private final TarefaMapper mapper;

    private final AnexoService anexoService;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional(readOnly = true)
    public TarefaDTO obterPorId(Long id) {
        return mapper.toDto(repository.findById(id).orElseThrow(()-> new RegraNegocioException("Tarefa não existe")));
    }

    public TarefaDTO salvar(TarefaDTO dto) {
        gerarChaveArquivo(dto.getAnexos());
        salvarDocumentos(dto.getAnexos());
        Tarefa tarefa = repository.save(mapper.toEntity(dto));
        applicationEventPublisher.publishEvent(new TarefaEvent(tarefa.getId()));

        TarefaDTO tarefaDTO = mapper.toDto(tarefa);
        lancarEventoAnexo(tarefaDTO.getAnexos());
        return tarefaDTO;
    }

    public void deletar(Long id){
        obterPorId(id);
        repository.deleteById(id);
    }

    private void gerarChaveArquivo(List<AnexoDTO> anexoDTOS){
        anexoDTOS.forEach(anexoDTO -> anexoDTO.getDocumento().setUuId(UUID.randomUUID().toString()));
    }

    private void salvarDocumentos(List<AnexoDTO> anexoDTOS){
        anexoService.salvarDocumentos(anexoDTOS);
    }

    private void lancarEventoAnexo(List<AnexoDTO> anexos){
        anexoService.lancarEvento(anexos);
    }

}
