package com.br.controle_contas.modules.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParcelaDTO {
    private Integer id;
    private LocalDate dataVencimento;
    private Integer numeroParcela;
    private Double valorParcela;
    private String status;
    private Integer contaId;
}
