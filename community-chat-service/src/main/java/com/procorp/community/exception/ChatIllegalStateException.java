package com.procorp.community.exception;

import java.io.Serial;

public class ChatIllegalStateException extends IllegalStateException {
    @Serial
    private static final long serialVersionUID = 1L;

    public ChatIllegalStateException(String message) {
        super(message);
    }

    public ChatIllegalStateException(Throwable e) {
        super(e);
    }
}
