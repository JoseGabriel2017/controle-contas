package com.br.controle_contas.modules.controllers;

import com.br.controle_contas.exceptions.NotFoundException;
import com.br.controle_contas.modules.dtos.ContaDTO;
import com.br.controle_contas.modules.dtos.ContaDetalhadaDTO;
import com.br.controle_contas.modules.dtos.ParcelaDTO;
import com.br.controle_contas.modules.services.ContasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contas")
public class ContasController {

    @Autowired
    private ContasService contasService;

    @GetMapping
    public ResponseEntity<List<ContaDTO>> getAllContas() {
        List<ContaDTO> contas = contasService.findAllDTO();
        return ResponseEntity.ok(contas);
    }

    @GetMapping("/detalhadas")
    public ResponseEntity<List<ContaDetalhadaDTO>> getAllContasDetalhadas() {
        List<ContaDetalhadaDTO> contas = contasService.findAllDetalhadasDTO();
        return ResponseEntity.ok(contas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaDTO> getContaById(@PathVariable Integer id) {
        ContaDTO conta = contasService.findByIdDTO(id);
        return conta != null ? ResponseEntity.ok(conta) : ResponseEntity.notFound().build();
    }

    @GetMapping("/detalhadas/{id}")
    public ResponseEntity<ContaDetalhadaDTO> getContaDetalhadaById(@PathVariable Integer id) {
        ContaDetalhadaDTO conta = contasService.findByIdDetalhadaDTO(id);
        return conta != null ? ResponseEntity.ok(conta) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ContaDTO> createConta(@RequestBody ContaDTO contaDTO) {
        ContaDTO novaConta = contasService.createConta(contaDTO);
        return new ResponseEntity<>(novaConta, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContaDTO> updateConta(@PathVariable Integer id, @RequestBody ContaDTO contaDTO) {
        ContaDTO updatedConta = contasService.updateConta(id, contaDTO);
        return ResponseEntity.ok(updatedConta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConta(@PathVariable Integer id) {
        contasService.deleteConta(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/parcelas")
    public ResponseEntity<ParcelaDTO> createParcela(@RequestBody ParcelaDTO parcelaDTO) {
        ParcelaDTO novaParcela = contasService.createParcela(parcelaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaParcela);
    }

    @PutMapping("/parcelas/{id}/status")
    public ResponseEntity<Void> updateParcelaStatus(@PathVariable Integer id, @RequestParam String status) {
        contasService.updateParcelaStatus(id, status);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFound(NotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return new ResponseEntity<>("Ocorreu um erro no servidor", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
