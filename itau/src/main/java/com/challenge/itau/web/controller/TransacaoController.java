package com.challenge.itau.web.controller;

import com.challenge.itau.service.TransacaoService;
import com.challenge.itau.web.dto.TransacaoInput;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.DoubleSummaryStatistics;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @GetMapping("/estatistica")
    public ResponseEntity<DoubleSummaryStatistics> estatisticasLastSeconds(
            @RequestParam(defaultValue = "60", required = false) Integer seconds) {
        return ResponseEntity.ok(transacaoService.getEstatisticasLastSeconds(seconds));
    }


    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody TransacaoInput input) {
        transacaoService.create(input);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete() {
        transacaoService.deleteAll();
        return ResponseEntity.ok().build();
    }

}
