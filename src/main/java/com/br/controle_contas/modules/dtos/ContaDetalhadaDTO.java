package com.br.controle_contas.modules.dtos;

import com.br.controle_contas.modules.entities.Contas;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContaDetalhadaDTO {
    private Integer id;
    private String descricao;
    private Float valor;
    private LocalDate dataVencimento;
    private LocalDate dataPagamento;
    private String tipoConta;
    private Boolean status;
    private Integer usuarioId;
    private CategoriaDTO categoria;

    public static ContaDetalhadaDTO fromEntity(Contas conta) {
        return new ContaDetalhadaDTO(
                conta.getId(),
                conta.getDescricao(),
                conta.getValor(),
                conta.getDataVencimento(),
                conta.getDataPagamento(),
                conta.getTipoConta(),
                conta.getStatus(),
                conta.getUsuario().getId(),
                DtoConverter.toCategoriaDTO(conta.getCategoria())
        );
    }
}
