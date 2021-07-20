package com.basis.campina.xtarefas.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "TAREFA")
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TAREFA")
    @SequenceGenerator(name = "SEQ_TAREFA", allocationSize = 1, sequenceName = "SEQ_TAREFA")
    @Column(name = "ID_TAREFA")
    private Long id;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "DATA_CONCLUSAO")
    private LocalDate dataConclusao;

    @Column(name = "DATA_INICIO")
    private LocalDate dataInicio;

    @Column(name = "STATUS")
    private String status;

    @Fetch(value = FetchMode.SUBSELECT)
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Anexo> anexos = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "ID_RESPONSAVEL")
    private Responsavel responsavel;

}
