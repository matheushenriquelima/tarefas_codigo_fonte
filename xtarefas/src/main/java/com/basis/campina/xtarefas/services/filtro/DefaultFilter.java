package com.basis.campina.xtarefas.services.filtro;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class DefaultFilter implements Serializable {

    private static final long serialVersionUID = -822829513136329893L;

    protected String query;

    protected Boolean status;
}
