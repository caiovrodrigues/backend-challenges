package com.challenge.itau.web.repository;

import com.challenge.itau.domain.Transacao;

import java.util.List;

public interface TransacaoRepository {

    List<Transacao> findAllLastSeconds(Integer seconds);

    void save(Transacao input);

    void deleteAll();
}
