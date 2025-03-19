package com.br.controle_contas.modules.services;

import com.br.controle_contas.modules.dtos.DtoConverter;
import com.br.controle_contas.modules.dtos.ParcelaDTO;
import com.br.controle_contas.modules.entities.Contas;
import com.br.controle_contas.modules.entities.Parcela;
import com.br.controle_contas.modules.repo.ContasRepository;
import com.br.controle_contas.modules.repo.ParcelasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParcelaService {

    @Autowired
    private ParcelasRepository parcelaRepository;

    @Autowired
    private ContasRepository contasRepository;

    public List<ParcelaDTO> findAllDTO() {
        List<Parcela> parcelas = parcelaRepository.findAll();
        return parcelas.stream()
                .map(DtoConverter::toParcelaDTO)
                .collect(Collectors.toList());
    }

    public ParcelaDTO findByIdDTO(Integer id) {
        Optional<Parcela> parcela = parcelaRepository.findById(id);
        return parcela.map(DtoConverter::toParcelaDTO).orElse(null);
    }

    public ParcelaDTO save(ParcelaDTO parcelaDTO) {
        if (parcelaDTO.getContaId() != null) {
            Optional<Contas> contaOptional = contasRepository.findById(parcelaDTO.getContaId());

            if (contaOptional.isPresent()) {
                Contas conta = contaOptional.get();
                Parcela parcela = DtoConverter.toParcelaEntity(parcelaDTO);
                parcela.setConta(conta);

                Parcela savedParcela = parcelaRepository.save(parcela);
                return DtoConverter.toParcelaDTO(savedParcela);
            } else {
                throw new IllegalArgumentException("Conta não encontrada com o ID: " + parcelaDTO.getContaId());
            }
        } else {
            // Se contaId for nulo, você pode criar a parcela sem conta ou lançar uma exceção
            Parcela parcela = DtoConverter.toParcelaEntity(parcelaDTO);
            Parcela savedParcela = parcelaRepository.save(parcela);
            return DtoConverter.toParcelaDTO(savedParcela);
        }
    }

    public ParcelaDTO update(Integer id, ParcelaDTO parcelaDTO) {
        Optional<Parcela> parcelaOptional = parcelaRepository.findById(id);

        if (parcelaOptional.isEmpty()) {
            throw new IllegalArgumentException("Parcela não encontrada com o ID: " + id);
        }

        Parcela parcela = parcelaOptional.get();
        parcela.setDataVencimento(parcelaDTO.getDataVencimento());
        parcela.setNumeroParcela(parcelaDTO.getNumeroParcela());
        parcela.setValorParcela(parcelaDTO.getValorParcela());

        if (parcelaDTO.getContaId() != null) {
            Optional<Contas> contaOptional = contasRepository.findById(parcelaDTO.getContaId());
            if (contaOptional.isPresent()) {
                parcela.setConta(contaOptional.get());
            }
        }

        Parcela updatedParcela = parcelaRepository.save(parcela);
        return DtoConverter.toParcelaDTO(updatedParcela);
    }

    public void updateStatus(Integer id, String status) {
        Optional<Parcela> parcelaOptional = parcelaRepository.findById(id);

        if (parcelaOptional.isPresent()) {
            Parcela parcela = parcelaOptional.get();
            parcela.setStatus(status);
            parcelaRepository.save(parcela);
        } else {
            throw new IllegalArgumentException("Parcela não encontrada com o ID: " + id);
        }
    }

    public void delete(Integer id) {
        parcelaRepository.deleteById(id);
    }
}
