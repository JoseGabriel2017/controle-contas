package com.br.controle_contas.modules.repo;

import com.br.controle_contas.modules.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    // Métodos personalizados
    Optional<Categoria> findByDescricao(String descricao);
    List<Categoria> findByTipo(String tipo);
}
