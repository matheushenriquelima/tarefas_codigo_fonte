package com.basis.campina.xtarefas.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "ANEXO")
public class Anexo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ANEXO")
    @SequenceGenerator(name = "SEQ_ANEXO", allocationSize = 1, sequenceName = "SEQ_ANEXO")
    @Column(name = "ID_ANEXO")
    private Long id;

    @Column(name = "FILENAME")
    private String filename;

    @Column(name = "FILE")
    private String file;

    @Column(name = "CHAVE")
    private String chave;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TAREFA", referencedColumnName = "ID_TAREFA")
    private Tarefa tarefa;
}
