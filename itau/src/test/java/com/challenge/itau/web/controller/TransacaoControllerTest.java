package com.challenge.itau.web.controller;

import com.challenge.itau.service.TransacaoService;
import com.challenge.itau.web.dto.TransacaoInput;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@ExtendWith(MockitoExtension.class)
class TransacaoControllerTest {

    @Mock
    TransacaoService transacaoService;

    @InjectMocks
    TransacaoController transacaoController;

    @Nested
    class create{

        @Test
        @DisplayName("Should delegate create transaction responsibility to TransacaoService")
        void shouldDelegateCreateResponsibilityToTransacaoService(){
            //Arrange
            var input = new TransacaoInput(BigDecimal.valueOf(1_000), OffsetDateTime.now());

            //Act
            var response = transacaoController.create(input);

            //Assert
            Assertions.assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.CREATED.value());
            verify(transacaoService, times(1)).create(input);
            verifyNoMoreInteractions(transacaoService);
        }
    }

    @Nested
    class delete{

        @Test
        @DisplayName("Should delegate delete transaction responsibility to TransacaoService")
        void shouldDelegateDeleteResponsibilityToTransacaoService(){
            //Act
            transacaoController.delete();

            //Assert
            Mockito.verify(transacaoService, Mockito.times(1)).deleteAll();
            Mockito.verifyNoMoreInteractions(transacaoService);
        }

    }

}