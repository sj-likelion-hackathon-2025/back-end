package org.chungnamthon.flowmate.global.exception;

import org.chungnamthon.flowmate.global.exception.dto.ErrorStatus;

public class ForbiddenException extends RootException {

    public ForbiddenException(ErrorStatus status) {
        super(status);
    }

}