package com.challenge.itau.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record TransacaoInput(

        @NotNull
        @PositiveOrZero
        BigDecimal valor,

        @PastOrPresent
        OffsetDateTime dataHora
) {
}
