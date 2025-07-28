package org.chungnamthon.flowmate.global.exception;

import org.chungnamthon.flowmate.global.exception.dto.ErrorStatus;

public class NotFoundException extends RootException {

    public NotFoundException(ErrorStatus status) {
        super(status);
    }

    public NotFoundException(ErrorStatus status, Throwable cause) {
        super(status, cause);
    }

}