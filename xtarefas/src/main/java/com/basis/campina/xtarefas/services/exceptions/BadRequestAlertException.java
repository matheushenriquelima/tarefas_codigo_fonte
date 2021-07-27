package com.basis.campina.xtarefas.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestAlertException extends RuntimeException {

    private static final long serialVersionUID = 3147472506819859422L;

    public BadRequestAlertException(final String mensagem) {
        this(mensagem, null);
    }

    public BadRequestAlertException(final String message, final Throwable motivo) {
        super(message, motivo);
    }
}