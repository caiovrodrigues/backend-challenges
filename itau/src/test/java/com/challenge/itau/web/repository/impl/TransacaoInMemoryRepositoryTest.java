package com.challenge.itau.web.repository.impl;

import com.challenge.itau.domain.Transacao;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransacaoInMemoryRepositoryTest {

    @InjectMocks
    TransacaoInMemoryRepository transacaoRepository;

    @Nested
    class findAllLastSeconds{

        @Test
        @DisplayName("Should return only transactions made in the last 60 seconds")
        void shouldReturnOnlyTransactionsMadeInTheLast60Seconds(){
            //Arrange
            var transacao_1 = Transacao.Factories.create(BigDecimal.valueOf(600), OffsetDateTime.now().minusSeconds(30));
            var transacao_2 = Transacao.Factories.create(BigDecimal.valueOf(400), OffsetDateTime.now().minusSeconds(40));
            var transacao_3 = Transacao.Factories.create(BigDecimal.valueOf(1_000), OffsetDateTime.now().minusSeconds(120));
            transacaoRepository.save(transacao_1);
            transacaoRepository.save(transacao_2);
            transacaoRepository.save(transacao_3);

            //Act
            var transacoesStream = transacaoRepository.findAllLastSeconds(60);

            //Assert
            var transacoes = transacoesStream.toList();
            assertEquals(2, transacoes.size());
            assertTrue(transacoes.contains(transacao_1));
            assertTrue(transacoes.contains(transacao_2));
            assertFalse(transacoes.contains(transacao_3));
        }

        @Test
        @DisplayName("Should return all transactions when all are within the last 60 seconds")
        void shouldReturnAllWhenAllAreWithinTheLast60Seconds(){
            //Arrange
            var now = OffsetDateTime.now();
            var transacao_1 = Transacao.Factories.create(BigDecimal.valueOf(600), now.minusSeconds(30));
            var transacao_2 = Transacao.Factories.create(BigDecimal.valueOf(600), now.minusSeconds(25));
            var transacao_3 = Transacao.Factories.create(BigDecimal.valueOf(600), now.minusSeconds(23));
            transacaoRepository.save(transacao_1);
            transacaoRepository.save(transacao_2);
            transacaoRepository.save(transacao_3);

            //Act
            var transacoesStream = transacaoRepository.findAllLastSeconds(60);

            //Assert
            var transacoes = transacoesStream.toList();
            Assertions.assertThat(transacoes).hasSize(3);
            Assertions.assertThat(transacoes).contains(transacao_1, transacao_2, transacao_3);
        }

        @Test
        @DisplayName("Should return empty stream when all transactions are older than 60 seconds")
        void shouldReturnEmptyStreamWhenAllTransactionsAreOlderThan60Seconds(){
            //Arrange
            var now = OffsetDateTime.now();
            var transacao_1 = Transacao.Factories.create(BigDecimal.valueOf(600), now.minusSeconds(90));
            var transacao_2 = Transacao.Factories.create(BigDecimal.valueOf(600), now.minusSeconds(120));
            transacaoRepository.save(transacao_1);
            transacaoRepository.save(transacao_2);

            //Act
            var transacoesStream = transacaoRepository.findAllLastSeconds(60);

            //Assert
            Assertions.assertThat(transacoesStream).isEmpty();
        }

        @Test
        @DisplayName("Should not return transaction exactly 60 seconds old")
        void shouldReturnTransactionExactly60SecondsOld(){
            //Arrange
            var transacao_1 = Transacao.Factories.create(BigDecimal.valueOf(600), OffsetDateTime.now().minusSeconds(60));
            transacaoRepository.save(transacao_1);

            //Act
            var transacoesStream = transacaoRepository.findAllLastSeconds(60);

            //Assert
            var transacoes = transacoesStream.toList();
            assertEquals(1, transacoes.size());
            Assertions.assertThat(transacoes.getFirst()).isEqualTo(transacao_1);
        }

        @Test
        @DisplayName("Should return an empty stream when there is not any transaction")
        void shouldReturnEmptyStreamWhenTransactionsIsEmpty(){
            //Act
            var result = transacaoRepository.findAllLastSeconds(60);

            //Assert
            assertEquals(0, result.count());
        }
    }

    @Nested
    class deleteAll{

        @Test
        @DisplayName("Should delete all transactions successfully")
        void shouldDeleteAllTransactions(){
            //Arrange
            var transacao_1 = Transacao.Factories.create(BigDecimal.valueOf(600), OffsetDateTime.now().minusSeconds(30));
            var transacao_2 = Transacao.Factories.create(BigDecimal.valueOf(400), OffsetDateTime.now().minusSeconds(40));
            transacaoRepository.save(transacao_1);
            transacaoRepository.save(transacao_2);

            //Act
            transacaoRepository.deleteAll();

            //Assert
            var transactionsAfterDelete = transacaoRepository.findAllLastSeconds(999_999).toList();
            assertEquals(0, transactionsAfterDelete.size());
        }
    }

}
