package com.plim.plimserver.domain.user.exception;

public class WithdrawalAccountException extends IllegalArgumentException {

    public WithdrawalAccountException(UserExceptionMessage e) {
        super(e.getMessage());
    }

}
