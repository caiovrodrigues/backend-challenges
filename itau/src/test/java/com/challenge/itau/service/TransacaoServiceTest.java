package com.challenge.itau.service;

import com.challenge.itau.domain.Transacao;
import com.challenge.itau.exception.RequestParamException;
import com.challenge.itau.web.dto.TransacaoInput;
import com.challenge.itau.web.repository.TransacaoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransacaoServiceTest {

    @Mock
    TransacaoRepository transacaoRepository;

    @InjectMocks
    TransacaoService transacaoService;

    @Captor
    ArgumentCaptor<Transacao> transacaoArgumentCaptor;

    @Nested
    class getEstatisticasLastSeconds{

        @Test
        @DisplayName("Should return a summary statistics of a single transaction")
        void shouldReturnASummaryStatistics(){
            //Arrange
            var value = BigDecimal.valueOf(1_200);
            var transacao = Transacao.Factories.create(value, OffsetDateTime.now());
            doReturn(Stream.of(transacao)).when(transacaoRepository).findAllLastSeconds(anyInt());

            //Act
            var result = transacaoService.getEstatisticasLastSeconds(60);

            //Assert
            assertNotNull(result);
            assertEquals(1, result.getCount());
            assertEquals(value.longValue(), result.getMax());
        }

        @Test
        @DisplayName("Should return correct statistics for transactions in the last 60 seconds")
        void shouldReturnCorrectStatisticsForTransactionsInTheLast60Seconds(){
            //Arrange
            var valor_1 = BigDecimal.valueOf(600.25);
            var valor_2 = BigDecimal.valueOf(400.50);
            var transacao_1 = Transacao.Factories.create(valor_1, OffsetDateTime.now().minusSeconds(30));
            var transacao_2 = Transacao.Factories.create(valor_2, OffsetDateTime.now().minusSeconds(40));

            doReturn(Stream.of(transacao_1, transacao_2)).when(transacaoRepository).findAllLastSeconds(anyInt());
            //Act
            var result = transacaoService.getEstatisticasLastSeconds(60);

            //Assert
            verify(transacaoRepository, times(1)).findAllLastSeconds(60);
            assertEquals(2, result.getCount());
            assertEquals(transacao_1.getValor().doubleValue(), result.getMax());
            assertEquals(transacao_2.getValor().doubleValue(), result.getMin());
            assertEquals(valor_1.doubleValue() + valor_2.doubleValue(), result.getSum());
            assertEquals((valor_1.doubleValue() + valor_2.doubleValue()) / 2.0, result.getAverage());
        }

        @Test
        @DisplayName("Should return correct statistics when there is not any transaction")
        void shouldReturnCorrectStatisticsWhenTransactionSizeIs0(){
            //Arrange
            doReturn(Stream.of()).when(transacaoRepository).findAllLastSeconds(60);

            //Act
            var result = transacaoService.getEstatisticasLastSeconds(60);

            //Assert
            assertEquals(0, result.getCount());
            assertEquals(0, result.getSum());

            assertEquals(Double.POSITIVE_INFINITY, result.getMin());
            assertEquals(Double.NEGATIVE_INFINITY, result.getMax());
            assertEquals(0, result.getAverage());
        }
    }

    @Nested
    class create{

        @Test
        @DisplayName("Should create a transaction successfully")
        void shouldCreateATransactionSuccessfully() {
            //Arrange
            var valor = BigDecimal.valueOf(1_000);
            var data = OffsetDateTime.now();
            TransacaoInput input = new TransacaoInput(valor, data);

            //Act
            transacaoService.create(input);

            //Assert
            verify(transacaoRepository).save(transacaoArgumentCaptor.capture());

            Transacao transacaoArgument = transacaoArgumentCaptor.getValue();

            assertNotNull(transacaoArgument);
            assertNotNull(transacaoArgument.getId());
            assertEquals(valor, transacaoArgument.getValor());
            assertEquals(data, transacaoArgument.getDataHora());
        }

        @Test
        @DisplayName("Should throw an error when value is negative")
        void shouldThrowAnErrorWhenValueIsNegative(){
            //Arrange
            var valor = BigDecimal.valueOf(-1_000);
            var data = OffsetDateTime.now();
            TransacaoInput input = new TransacaoInput(valor, data);

            //Act & Assert
            assertThrows(RequestParamException.class, () -> transacaoService.create(input));
        }

        @Test
        @DisplayName("Should throw an error when date is in the future")
        void shouldThrowAnErrorWhenDateIsInTheFuture(){
            //Arrange
            var valor = BigDecimal.valueOf(1_000);
            var data = OffsetDateTime.now().plusDays(1);
            TransacaoInput input = new TransacaoInput(valor, data);

            //Act & Assert
            assertThrows(RequestParamException.class, () -> transacaoService.create(input));
        }
    }

    @Nested
    class deleteAll{

        @Test
        @DisplayName("Should call TransacaoRepository deleteAll method")
        void shouldCallTransacaoRepositoryDeleteAllMethod(){
            //Act
            transacaoService.deleteAll();

            //Assert
            verify(transacaoRepository, times(1)).deleteAll();
            verifyNoMoreInteractions(transacaoRepository);
        }

    }
}