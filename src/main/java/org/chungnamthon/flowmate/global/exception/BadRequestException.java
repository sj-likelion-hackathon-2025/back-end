package org.chungnamthon.flowmate.global.exception;

import org.chungnamthon.flowmate.global.exception.dto.ErrorStatus;

public class BadRequestException extends RootException {

    public BadRequestException(ErrorStatus status) {
        super(status);
    }

}