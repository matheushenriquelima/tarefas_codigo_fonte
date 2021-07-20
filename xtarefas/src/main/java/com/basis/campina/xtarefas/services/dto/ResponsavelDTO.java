package com.basis.campina.xtarefas.services.dto;

import com.basis.campina.xtarefas.domain.Tarefa;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ResponsavelDTO {

    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private LocalDate dataNasc;

    @NotNull
    private String email;

    private List<Tarefa> tarefas = new ArrayList<>();
}
