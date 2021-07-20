package com.basis.campina.xtarefas.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "RESPONSAVEL")
public class Responsavel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_RESPONSAVEL")
    @SequenceGenerator(name = "SEQ_RESPONSAVEL", allocationSize = 1, sequenceName = "SEQ_RESPONSAVEL")
    @Column(name = "ID_RESPONSAVEL")
    private Long id;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "DATA_NASCIMENTO")
    private LocalDate dataNasc;

    @Column(name = "EMAIL")
    private String email;

    @OneToMany
    private List<Tarefa> tarefas = new ArrayList<>();

}
