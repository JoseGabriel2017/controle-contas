package com.br.controle_contas.modules.repo;

import com.br.controle_contas.modules.entities.Categoria;
import com.br.controle_contas.modules.entities.Contas;
import com.br.controle_contas.modules.entities.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ContasRepository extends JpaRepository<Contas, Integer> {

    List<Contas> findByDataVencimentoBetween(LocalDate dataInicio, LocalDate dataFim);
    List<Contas> findByTipoConta(String tipoConta);
    List<Contas> findByStatus(Boolean status);

    // Métodos para buscar contas relacionadas
    List<Contas> findByUsuario(Usuarios usuario);
    List<Contas> findByCategoria(Categoria categoria);
}
