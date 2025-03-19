package com.br.controle_contas.modules.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Integer id;
    private String nome;
    private String cpf;
    private String telefone;
    private String endereco;
    private String email;
    private String login;
    // Não incluímos a senha nem a lista de contas no DTO
}

