package com.challenge.itau.web.repository;

import com.challenge.itau.domain.Transacao;

public interface TransacaoRepository {

    void save(Transacao input);

    void deleteAll();
}
