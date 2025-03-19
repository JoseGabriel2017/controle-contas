package com.br.controle_contas.modules.services;

import com.br.controle_contas.exceptions.NotFoundException;
import com.br.controle_contas.modules.dtos.CategoriaDTO;
import com.br.controle_contas.modules.entities.Categoria;
import com.br.controle_contas.modules.repo.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<CategoriaDTO> findAllDTO() {
        return categoriaRepository.findAll().stream()
                .map(this::toCategoriaDTO)
                .collect(Collectors.toList());
    }

    public CategoriaDTO findByIdDTO(Integer id) {
        Categoria categoria = findById(id);
        return toCategoriaDTO(categoria);
    }

    public Categoria findById(Integer id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada"));
    }

    public CategoriaDTO createCategoria(Categoria categoria) {
        Categoria savedCategoria = categoriaRepository.save(categoria);
        return toCategoriaDTO(savedCategoria);
    }

    public CategoriaDTO updateCategoria(Integer id, Categoria categoria) {
        Categoria existingCategoria = findById(id);
        existingCategoria.setDescricao(categoria.getDescricao());
        Categoria updatedCategoria = categoriaRepository.save(existingCategoria);
        return toCategoriaDTO(updatedCategoria);
    }

    public void deleteCategoria(Integer id) {
        Categoria categoria = findById(id);
        categoriaRepository.delete(categoria);
    }

    private CategoriaDTO toCategoriaDTO(Categoria categoria) {
        return new CategoriaDTO(
                categoria.getId(),
                categoria.getDescricao()
        );
    }
}

