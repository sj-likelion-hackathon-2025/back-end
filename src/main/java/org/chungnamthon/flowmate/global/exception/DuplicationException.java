package org.chungnamthon.flowmate.global.exception;

import org.chungnamthon.flowmate.global.exception.dto.ErrorStatus;

public class DuplicationException extends RootException {

    public DuplicationException(ErrorStatus status) {
        super(status);
    }

    public DuplicationException(ErrorStatus status, Throwable cause) {
        super(status, cause);
    }

}