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
import java.util.DoubleSummaryStatistics;

@ExtendWith(MockitoExtension.class)
class TransacaoControllerTest {

    @Mock
    TransacaoService transacaoService;

    @InjectMocks
    TransacaoController transacaoController;

    @Nested
    class estatisticasLastSeconds{

        @Test
        @DisplayName("Should return statistics from TransacaoService")
        void shouldReturnStatisticsFromService(){
            //Arrange
            var expectedSeconds = 60;
            var doubleSummaryStatistics = new DoubleSummaryStatistics(2, 100.0, 200.0, 300.0);
            doReturn(doubleSummaryStatistics).when(transacaoService).getEstatisticasLastSeconds(eq(expectedSeconds));

            //Act
            var response = transacaoController.estatisticasLastSeconds(expectedSeconds);

            //Assert
            verify(transacaoService, times(1)).getEstatisticasLastSeconds(expectedSeconds);
            verifyNoMoreInteractions(transacaoService);
            var responseBody = response.getBody();
            Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            Assertions.assertThat(responseBody).isNotNull();
            Assertions.assertThat(responseBody).isEqualTo(doubleSummaryStatistics);
        }
    }

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