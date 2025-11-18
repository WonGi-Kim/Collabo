package com.example.collabo.global.exception.specific;

import com.example.collabo.global.exception.CustomException;
import com.example.collabo.global.exception.ErrorCode;

/**
 * 인증되지 않은 사용자 접근 시 발생하는 예외
 */
public class UnauthorizedException extends CustomException {

    public UnauthorizedException() {
        super(ErrorCode.UNAUTHORIZED);
    }

    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}

