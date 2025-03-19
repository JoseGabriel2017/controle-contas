package com.br.controle_contas.modules.controllers;

import com.br.controle_contas.modules.dtos.ParcelaDTO;
import com.br.controle_contas.modules.services.ParcelaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parcelas")
public class ParcelaController {

    @Autowired
    private ParcelaService parcelaService;

    @GetMapping
    public ResponseEntity<List<ParcelaDTO>> findAllDTO() {
        List<ParcelaDTO> parcelas = parcelaService.findAllDTO();
        return ResponseEntity.ok(parcelas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParcelaDTO> findByIdDTO(@PathVariable Integer id) {
        ParcelaDTO parcela = parcelaService.findByIdDTO(id);
        return parcela != null ? ResponseEntity.ok(parcela) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ParcelaDTO> create(@RequestBody ParcelaDTO parcelaDTO) {
        try {
            ParcelaDTO novaParcela = parcelaService.save(parcelaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaParcela);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParcelaDTO> update(@PathVariable Integer id, @RequestBody ParcelaDTO parcelaDTO) {
        ParcelaDTO parcelaAtualizada = parcelaService.update(id, parcelaDTO);
        return ResponseEntity.ok(parcelaAtualizada);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable Integer id, @RequestParam String status) {
        parcelaService.updateStatus(id, status);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        parcelaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
