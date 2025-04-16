package com.challenge.itau.web.repository.impl;

import com.challenge.itau.domain.Transacao;
import com.challenge.itau.web.repository.TransacaoRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Repository
public class TransacaoInMemoryRepository implements TransacaoRepository {

    private List<Transacao> transacoes = new ArrayList<>();

    @Override
    public Stream<Transacao> findAllLastSeconds(Integer seconds) {
        var dateTime = OffsetDateTime.now().minusSeconds(seconds);
        return transacoes.stream().filter(transacao -> transacao.getDataHora().isAfter(dateTime));
    }

    @Override
    public void save(Transacao transacao) {
        transacoes.add(transacao);
    }

    @Override
    public void deleteAll() {
        transacoes.iterator().forEachRemaining(transacao -> transacoes.remove(transacao));
    }
}
