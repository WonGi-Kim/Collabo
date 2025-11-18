package com.example.collabo.global.exception.specific;

import com.example.collabo.global.exception.CustomException;
import com.example.collabo.global.exception.ErrorCode;

/**
 * 잘못된 입력값이 들어왔을 때 발생하는 예외
 */
public class InvalidInputException extends CustomException {

    public InvalidInputException() {
        super(ErrorCode.BAD_REQUEST);
    }

    public InvalidInputException(ErrorCode errorCode) {
        super(errorCode);
    }
}

