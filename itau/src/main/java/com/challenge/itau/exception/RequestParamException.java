package com.challenge.itau.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class RequestParamException extends ItauException {

    private String detail;

    public RequestParamException(String detail){
        this.detail = detail;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        pb.setTitle("Your request parameters didn't validate.");
        pb.setDetail(detail);
        return pb;
    }
}
