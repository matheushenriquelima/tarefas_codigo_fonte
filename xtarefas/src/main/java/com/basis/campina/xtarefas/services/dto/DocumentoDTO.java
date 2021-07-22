package com.basis.campina.xtarefas.services.dto;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentoDTO {

    private String uuId;

    @NotNull
    private String file;
}
