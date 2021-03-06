package com.basis.campina.xtarefas.services.dto;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnexoDTO {

    private Long id;

    @NotNull
    private String filename;

    private Long idTarefa;

    private DocumentoDTO documento;

}
