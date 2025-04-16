package com.challenge.itau.domain;

import com.challenge.itau.exception.RequestParamException;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class Transacao {

    private final UUID id;

    private BigDecimal valor;

    private OffsetDateTime dataHora;

    {
        id = UUID.randomUUID();
    }

    private Transacao(BigDecimal valor, OffsetDateTime dataHora){
        this.valor = valor;
        this.dataHora = dataHora;
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public OffsetDateTime getDataHora() {
        return dataHora;
    }

    public static class Factories{
        public static Transacao create(BigDecimal valor, OffsetDateTime dataHora){
            if(dataHora.isAfter(OffsetDateTime.now())){
                throw new RequestParamException("dataHora must not be in the future");
            }

            if(valor.compareTo(BigDecimal.ZERO) < 0){
                throw new RequestParamException("value must not be less than zero");
            }

            return new Transacao(valor, dataHora);
        }
    }
}