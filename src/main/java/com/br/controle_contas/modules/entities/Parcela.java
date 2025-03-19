package com.br.controle_contas.modules.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "parcelas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parcela {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;

    @Column(name = "numero_parcela")
    @NotNull(message = "O número da parcela é obrigatório")
    private Integer numeroParcela;

    @Column(name = "valor_parcela")
    @NotNull(message = "O valor da parcela é obrigatório")
    private Double valorParcela;

    private String status; // Indica se a parcela está paga ou pendente

    @ManyToOne
    @JoinColumn(name = "conta_id")
    private Contas conta;
}

