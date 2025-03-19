package com.br.controle_contas.modules.repo;

import com.br.controle_contas.modules.entities.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuarios, Integer> {

    Optional<Usuarios> findByLogin(String login);
    Optional<Usuarios> findByCpf(String cpf);
    Optional<Usuarios> findByEmail(String email);

    List<Usuarios> findByNomeContaining(String nome);
}
