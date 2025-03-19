package com.br.controle_contas.modules.services;

import com.br.controle_contas.modules.dtos.DtoConverter;
import com.br.controle_contas.modules.dtos.UsuarioDTO;
import com.br.controle_contas.modules.entities.Usuarios;
import com.br.controle_contas.modules.repo.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuarios> findAll() {
        return usuarioRepository.findAll();
    }

    public List<UsuarioDTO> findAllDTO() {
        List<Usuarios> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(DtoConverter::toUsuarioDTO)
                .collect(Collectors.toList());
    }

    public Usuarios findById(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public UsuarioDTO findByIdDTO(Integer id) {
        Usuarios usuario = findById(id);
        if (usuario != null) {
            return DtoConverter.toUsuarioDTO(usuario);
        } else {
            return null;
        }
    }

    public Usuarios save(Usuarios usuario) {
        // Verifica se o CPF já existe
        if (usuarioRepository.findByCpf(usuario.getCpf()).isPresent()) {
            throw new IllegalArgumentException("CPF já cadastrado: " + usuario.getCpf());
        }
        return usuarioRepository.save(usuario);
    }

    public void delete(Integer id) {
        usuarioRepository.deleteById(id);
    }

    public Optional<Usuarios> login(String login, String senha) {
        return usuarioRepository.findByLogin(login).filter(usuario -> usuario.getSenha().equals(senha));
    }
}
