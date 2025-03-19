package com.br.controle_contas.modules.services;

import com.br.controle_contas.exceptions.NotFoundException;
import com.br.controle_contas.modules.dtos.ContaDTO;
import com.br.controle_contas.modules.dtos.ContaDetalhadaDTO;
import com.br.controle_contas.modules.dtos.ParcelaDTO;
import com.br.controle_contas.modules.dtos.DtoConverter;
import com.br.controle_contas.modules.entities.Categoria;
import com.br.controle_contas.modules.entities.Contas;
import com.br.controle_contas.modules.entities.Parcela;
import com.br.controle_contas.modules.entities.Usuarios;
import com.br.controle_contas.modules.repo.ContasRepository;
import com.br.controle_contas.modules.repo.ParcelasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContasService {

    @Autowired
    private ContasRepository contasRepository;

    @Autowired
    private ParcelasRepository parcelasRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CategoriaService categoriaService;

    public List<Contas> findAll() {
        return contasRepository.findAll();
    }

    public List<ContaDTO> findAllDTO() {
        List<Contas> contas = contasRepository.findAll();
        return contas.stream()
                .map(DtoConverter::toContaDTO)
                .collect(Collectors.toList());
    }

    public List<ContaDetalhadaDTO> findAllDetalhadasDTO() {
        List<Contas> contas = contasRepository.findAll();
        return contas.stream()
                .map(ContaDetalhadaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public Contas findById(Integer id) {
        return contasRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Conta não encontrada"));
    }

    public ContaDTO findByIdDTO(Integer id) {
        Contas conta = findById(id);
        return DtoConverter.toContaDTO(conta);
    }

    public ContaDetalhadaDTO findByIdDetalhadaDTO(Integer id) {
        Contas conta = findById(id);
        return ContaDetalhadaDTO.fromEntity(conta);
    }

    public ContaDTO createConta(ContaDTO contaDTO) {
        Contas conta = new Contas();
        convertDtoToEntity(contaDTO, conta);
        Contas savedConta = contasRepository.save(conta);
        return DtoConverter.toContaDTO(savedConta);
    }

    public ContaDTO updateConta(Integer id, ContaDTO contaDTO) {
        Contas conta = findById(id);
        convertDtoToEntity(contaDTO, conta);
        Contas updatedConta = contasRepository.save(conta);
        return DtoConverter.toContaDTO(updatedConta);
    }

    public void deleteConta(Integer id) {
        Contas conta = findById(id);
        contasRepository.delete(conta);
    }

    public ParcelaDTO createParcela(ParcelaDTO parcelaDTO) {
        Parcela novaParcela = new Parcela();
        novaParcela.setDataVencimento(parcelaDTO.getDataVencimento());
        novaParcela.setNumeroParcela(parcelaDTO.getNumeroParcela());
        novaParcela.setValorParcela(parcelaDTO.getValorParcela());
        novaParcela.setStatus(parcelaDTO.getStatus());

        Contas conta = findById(parcelaDTO.getContaId());
        novaParcela.setConta(conta);

        Parcela savedParcela = parcelasRepository.save(novaParcela);
        return DtoConverter.toParcelaDTO(savedParcela);
    }

    public ParcelaDTO updateParcelaStatus(Integer parcelaId, String status) {
        Parcela existingParcela = parcelasRepository.findById(parcelaId)
                .orElseThrow(() -> new NotFoundException("Parcela não encontrada"));
        existingParcela.setStatus(status);
        Parcela updatedParcela = parcelasRepository.save(existingParcela);
        return DtoConverter.toParcelaDTO(updatedParcela);
    }

    private void convertDtoToEntity(ContaDTO dto, Contas entity) {
        entity.setDescricao(dto.getDescricao());
        entity.setValor(dto.getValor());
        entity.setDataVencimento(dto.getDataVencimento());
        entity.setDataPagamento(dto.getDataPagamento());
        entity.setTipoConta(dto.getTipoConta());
        entity.setStatus(dto.getStatus());

        Usuarios usuario = usuarioService.findById(dto.getUsuarioId());
        entity.setUsuario(usuario);

        Categoria categoria = categoriaService.findById(dto.getCategoriaId());
        entity.setCategoria(categoria);
    }
}
