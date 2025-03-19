package com.br.controle_contas.modules.controllers;

import com.br.controle_contas.modules.dtos.DtoConverter;
import com.br.controle_contas.modules.dtos.LoginDTO;
import com.br.controle_contas.modules.dtos.UsuarioDTO;
import com.br.controle_contas.modules.entities.Usuarios;
import com.br.controle_contas.modules.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> findAll() {
        List<UsuarioDTO> usuarios = usuarioService.findAllDTO();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable Integer id) {
        UsuarioDTO usuario = usuarioService.findByIdDTO(id);
        return usuario != null ? ResponseEntity.ok(usuario) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Usuarios usuario) {
        try {
            Usuarios novoUsuario = usuarioService.save(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(DtoConverter.toUsuarioDTO(novoUsuario));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> update(@PathVariable Integer id, @Valid @RequestBody Usuarios usuario) {
        Usuarios usuarioExistente = usuarioService.findById(id);
        if (usuarioExistente != null) {
            usuario.setId(id);
            Usuarios usuarioAtualizado = usuarioService.save(usuario);
            return ResponseEntity.ok(DtoConverter.toUsuarioDTO(usuarioAtualizado));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioDTO> login(@RequestBody LoginDTO loginDTO) {
        return usuarioService.login(loginDTO.getLogin(), loginDTO.getSenha())
                .map(usuario -> ResponseEntity.ok(DtoConverter.toUsuarioDTO(usuario)))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

}
