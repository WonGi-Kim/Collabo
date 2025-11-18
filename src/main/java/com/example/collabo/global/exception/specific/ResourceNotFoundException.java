package com.example.collabo.global.exception.specific;

import com.example.collabo.global.exception.CustomException;
import com.example.collabo.global.exception.ErrorCode;

/**
 * 리소스를 찾을 수 없을 때 발생하는 예외
 */
public class ResourceNotFoundException extends CustomException {

    public ResourceNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ResourceNotFoundException() {
        super(ErrorCode.NOT_FOUND);
    }
}

