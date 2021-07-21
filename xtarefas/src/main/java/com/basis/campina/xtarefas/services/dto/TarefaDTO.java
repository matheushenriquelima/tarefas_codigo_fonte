package com.basis.campina.xtarefas.services.dto;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TarefaDTO {

    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private LocalDate dataConclusao;

    @NotNull
    private LocalDate dataInicio;

    @NotNull
    private String status;

    private List<AnexoDTO> anexos = new ArrayList<>();

    private ResponsavelDTO responsavel;
}
