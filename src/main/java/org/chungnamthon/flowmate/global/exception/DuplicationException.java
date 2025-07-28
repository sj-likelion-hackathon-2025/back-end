package org.chungnamthon.flowmate.global.exception;

import org.chungnamthon.flowmate.global.exception.dto.ErrorStatus;

public class DuplicationException extends RootException {

    public DuplicationException(ErrorStatus status) {
        super(status);
    }

}