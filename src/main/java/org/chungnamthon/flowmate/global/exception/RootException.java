package org.chungnamthon.flowmate.global.exception;

import lombok.Getter;
import org.chungnamthon.flowmate.global.exception.dto.ErrorStatus;

@Getter
public abstract class RootException extends RuntimeException {

    private final ErrorStatus errorStatus;

    protected RootException(ErrorStatus status) {
        super(status.getMessage());
        this.errorStatus = status;
    }

}