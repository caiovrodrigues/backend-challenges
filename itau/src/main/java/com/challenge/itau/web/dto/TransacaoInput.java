package com.challenge.itau.web.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record TransacaoInput(BigDecimal valor, OffsetDateTime dataHora) {
}
