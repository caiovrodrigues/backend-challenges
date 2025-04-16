package com.challenge.itau.domain;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class Transacao {

    private final UUID id = UUID.randomUUID();

    private BigDecimal valor;

    private OffsetDateTime dataHora;

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
            if(dataHora.isBefore(OffsetDateTime.now())){
                throw new RuntimeException();
            }

            if(valor.compareTo(BigDecimal.ZERO) < 0){
                throw new RuntimeException();
            }

            return new Transacao(valor, dataHora);
        }
    }
}