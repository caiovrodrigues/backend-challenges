package com.challenge.itau.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class ItauException extends RuntimeException {

    public ProblemDetail toProblemDetail() {
        ProblemDetail pb = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        pb.setTitle("Internal Server Error");

        return pb;
    }
}
