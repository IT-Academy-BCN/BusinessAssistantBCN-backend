package com.businessassistantbcn.opendata.exception;

public class OpendataUnavailableServiceException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    /**
     * Create an exception without a message.
     */
    public OpendataUnavailableServiceException(){}


    /**
     * Create an exception with a message
     *
     * @param s the message
     */
    public OpendataUnavailableServiceException(String s){
        super(s);
    }

}
