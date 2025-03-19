package com.br.controle_contas.modules.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContaDTO {
    private Integer id;
    private String descricao;
    private Float valor;
    private LocalDate dataVencimento;
    private LocalDate dataPagamento;
    private String tipoConta;
    private Boolean status;
    private Integer usuarioId;
    private Integer categoriaId;
}
