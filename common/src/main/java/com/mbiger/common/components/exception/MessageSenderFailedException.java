package com.mbiger.common.components.exception;


/**
           * @author cyp
           * @time : 2019-01-17 14:23
           * @version V1.0.0
           * @description  消息发送失败
           */


public class MessageSenderFailedException extends BaseRuntimeException {

 private static final long serialVersionUID = 6282589858600134736L;

 public MessageSenderFailedException(String message) {
     super(message);
 }
}