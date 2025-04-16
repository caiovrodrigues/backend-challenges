package com.challenge.itau.web.repository.impl;

import com.challenge.itau.domain.Transacao;
import com.challenge.itau.web.repository.TransacaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Repository
public class TransacaoInMemoryRepository implements TransacaoRepository {

    private final List<Transacao> transacoes = new ArrayList<>();
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public Stream<Transacao> findAllLastSeconds(Integer seconds) {
        var dateTime = OffsetDateTime.now().minusSeconds(seconds);
        return transacoes.stream().filter(transacao -> transacao.getDataHora().isAfter(dateTime));
    }

    @Override
    public void save(Transacao transacao) {
        transacoes.add(transacao);
        log.info("New transaction have been created; Id: {}; Value: {}; dateHour: {}",
                transacao.getId(), transacao.getValor(), transacao.getDataHora());
    }

    @Override
    public void deleteAll() {
        log.info("Starting deleting all transactions... Size: {}", transacoes.size());
        var iterator = transacoes.iterator();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
        log.info("Finished deleting all transactions... Size: {}", transacoes.size());
    }
}
