package com.challenge.itau.web.repository;

import com.challenge.itau.domain.Transacao;

import java.util.stream.Stream;

public interface TransacaoRepository {

    Stream<Transacao> findAllLastSeconds(Integer seconds);

    void save(Transacao input);

    void deleteAll();
}
