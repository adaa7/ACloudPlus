package com.adaa7.common.exception;

/**
 * 账号不存在异常
 */
public class AccountNotFoundException extends BeanException{
    public AccountNotFoundException(){

    }
    public AccountNotFoundException(String msg){
        super(msg);
    }
}
