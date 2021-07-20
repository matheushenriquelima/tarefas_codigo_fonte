package com.basis.campina.xtarefas.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

}
