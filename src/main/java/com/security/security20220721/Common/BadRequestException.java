package com.security.security20220721.Common;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
public class BadRequestException  extends RuntimeException{

    private Integer status = BAD_REQUEST.value();
    public BadRequestException(String msg){super(msg);}

    public BadRequestException(HttpStatus status, String msg){
        super(msg);
        this.status=status.value();
    }
}
