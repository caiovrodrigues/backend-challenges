package com.challenge.itau.service;

import com.challenge.itau.domain.Transacao;
import com.challenge.itau.web.dto.TransacaoInput;
import com.challenge.itau.web.repository.TransacaoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.DoubleSummaryStatistics;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;

    public TransacaoService(TransacaoRepository transacaoRepository){
        this.transacaoRepository = transacaoRepository;
    }

    public DoubleSummaryStatistics getEstatisticasLastSeconds(Integer seconds) {
        var allTransactions = transacaoRepository.findAllLastSeconds(seconds);
        return allTransactions.map(Transacao::getValor).mapToDouble(BigDecimal::doubleValue).summaryStatistics();
    }

    public void create(TransacaoInput input){
        var transacao = Transacao.Factories.create(input.valor(), input.dataHora());
        transacaoRepository.save(transacao);
    }

    public void deleteAll() {
        transacaoRepository.deleteAll();
    }
}
