package com.example.collabo.global.exception.specific;

import com.example.collabo.global.exception.CustomException;
import com.example.collabo.global.exception.ErrorCode;

/**
 * 접근 권한이 없을 때 발생하는 예외
 */
public class ForbiddenException extends CustomException {

    public ForbiddenException() {
        super(ErrorCode.FORBIDDEN);
    }

    public ForbiddenException(ErrorCode errorCode) {
        super(errorCode);
    }
}

