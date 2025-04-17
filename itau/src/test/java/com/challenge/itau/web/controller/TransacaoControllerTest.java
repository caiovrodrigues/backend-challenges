package com.challenge.itau.web.controller;

import com.challenge.itau.service.TransacaoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TransacaoControllerTest {

    @Mock
    TransacaoService transacaoService;

    @InjectMocks
    TransacaoController transacaoController;

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