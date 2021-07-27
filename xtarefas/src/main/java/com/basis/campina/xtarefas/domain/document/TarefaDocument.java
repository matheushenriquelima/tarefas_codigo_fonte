package com.basis.campina.xtarefas.domain.document;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.InnerField;
import org.springframework.data.elasticsearch.annotations.MultiField;

import java.time.LocalDate;

@Getter
@Setter
@Document(indexName = "tarefa")
@NoArgsConstructor
public class TarefaDocument extends BaseDocument {

    private static final long serialVersionUID = -1768915045323314380L;

    @MultiField(mainField = @Field(type = FieldType.Text, store = true, analyzer = TRIM_CASE_INSENSITIVE, fielddata = true), otherFields = {
            @InnerField(suffix = SORT, type = FieldType.Text, store = true, analyzer = TRIM_CASE_INSENSITIVE, fielddata = true)})
    private String nome;

    @Field(type = FieldType.Date, store = true, format = DateFormat.custom, pattern = DATE_PATTERN)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
    private LocalDate dataConclusao;

    @Field(type = FieldType.Date, store = true, format = DateFormat.custom, pattern = DATE_PATTERN)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN)
    private LocalDate dataInicio;

    @MultiField(mainField = @Field(type = FieldType.Text, store = true, analyzer = TRIM_CASE_INSENSITIVE, fielddata = true), otherFields = {
            @InnerField(suffix = SORT, type = FieldType.Text, store = true, analyzer = TRIM_CASE_INSENSITIVE, fielddata = true)})
    private String status;

    @MultiField(mainField = @Field(type = FieldType.Text, store = true, analyzer = TRIM_CASE_INSENSITIVE, fielddata = true), otherFields = {
            @InnerField(suffix = SORT, type = FieldType.Text, store = true, analyzer = TRIM_CASE_INSENSITIVE, fielddata = true)})
    private String responsavel;

    @MultiField(mainField = @Field(type = FieldType.Text, store = true, analyzer = TRIM_CASE_INSENSITIVE, fielddata = true), otherFields = {
            @InnerField(suffix = SORT, type = FieldType.Text, store = true, analyzer = TRIM_CASE_INSENSITIVE, fielddata = true)})
    private String anexos;

    public TarefaDocument(Long id, String nome, LocalDate dataConclusao, String status,LocalDate dataInicio, String responsavel){
        this.id = id;
        this.nome = nome;
        this.dataConclusao = dataConclusao;
        this.dataInicio = dataInicio;
        this.status = status;
        this.responsavel = responsavel;
    }

}

