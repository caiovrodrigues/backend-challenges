package com.challenge.itau.service;

import com.challenge.itau.domain.Transacao;
import com.challenge.itau.exception.RequestParamException;
import com.challenge.itau.web.dto.TransacaoInput;
import com.challenge.itau.web.repository.TransacaoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransacaoServiceTest {

    @Mock
    TransacaoRepository transacaoRepository;

    @InjectMocks
    TransacaoService transacaoService;

    @Captor
    ArgumentCaptor<Transacao> transacaoArgumentCaptor;

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
}