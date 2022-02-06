package com.example.demokafkacloudstreamer.order;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.I_AM_A_TEAPOT)
public class OrderFailedExcepion extends RuntimeException {
    
    public OrderFailedExcepion(String msg) {
        super(msg);
    }
}
