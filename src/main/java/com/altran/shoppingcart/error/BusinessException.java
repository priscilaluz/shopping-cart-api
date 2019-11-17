package com.altran.shoppingcart.error;

import org.springframework.stereotype.Component;

@Component
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -7117723184046845556L;

    public BusinessException() {
        super();
    }

    public BusinessException(String errorMessage) {
        super(errorMessage);
    }

    public BusinessException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
