package com.br.controle_contas.modules.dtos;

import com.br.controle_contas.modules.entities.Contas;
import com.br.controle_contas.modules.entities.Parcela;
import com.br.controle_contas.modules.entities.Usuarios;
import com.br.controle_contas.modules.entities.Categoria;

public class DtoConverter {

    public static UsuarioDTO toUsuarioDTO(Usuarios usuario) {
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getCpf(),
                usuario.getTelefone(),
                usuario.getEndereco(),
                usuario.getEmail(),
                usuario.getLogin()
        );
    }

    public static ContaDTO toContaDTO(Contas conta) {
        return new ContaDTO(
                conta.getId(),
                conta.getDescricao(),
                conta.getValor(),
                conta.getDataVencimento(),
                conta.getDataPagamento(),
                conta.getTipoConta(),
                conta.getStatus(),
                conta.getUsuario().getId(),
                conta.getCategoria().getId()
        );
    }

    public static ContaDetalhadaDTO toContaDetalhadaDTO(Contas conta) {
        return new ContaDetalhadaDTO(
                conta.getId(),
                conta.getDescricao(),
                conta.getValor(),
                conta.getDataVencimento(),
                conta.getDataPagamento(),
                conta.getTipoConta(),
                conta.getStatus(),
                conta.getUsuario().getId(),
                toCategoriaDTO(conta.getCategoria())
        );
    }

    public static CategoriaDTO toCategoriaDTO(Categoria categoria) {
        return new CategoriaDTO(
                categoria.getId(),
                categoria.getDescricao()
        );
    }

    public static ParcelaDTO toParcelaDTO(Parcela parcela) {
        ParcelaDTO dto = new ParcelaDTO();
        dto.setId(parcela.getId());
        dto.setDataVencimento(parcela.getDataVencimento());
        dto.setNumeroParcela(parcela.getNumeroParcela());
        dto.setValorParcela(parcela.getValorParcela());
        dto.setStatus(parcela.getStatus());
        dto.setContaId(parcela.getConta().getId());
        return dto;
    }

    public static Parcela toParcelaEntity(ParcelaDTO dto) {
        Parcela parcela = new Parcela();
        parcela.setId(dto.getId());
        parcela.setDataVencimento(dto.getDataVencimento());
        parcela.setNumeroParcela(dto.getNumeroParcela());
        parcela.setValorParcela(dto.getValorParcela());
        parcela.setStatus(dto.getStatus());
        return parcela;
    }

    // Método para converter DTO para entidade
    public static Usuarios toUsuarioEntity(UsuarioDTO dto) {
        Usuarios usuario = new Usuarios();
        usuario.setId(dto.getId());
        usuario.setNome(dto.getNome());
        usuario.setCpf(dto.getCpf());
        usuario.setTelefone(dto.getTelefone());
        usuario.setEndereco(dto.getEndereco());
        usuario.setEmail(dto.getEmail());
        usuario.setLogin(dto.getLogin());
        // Você precisará de um serviço para buscar a senha ou definir uma senha padrão
        return usuario;
    }

    public static Contas toContaEntity(ContaDTO dto) {
        Contas conta = new Contas();
        conta.setId(dto.getId());
        conta.setDescricao(dto.getDescricao());
        conta.setValor(dto.getValor());
        conta.setDataVencimento(dto.getDataVencimento());
        conta.setDataPagamento(dto.getDataPagamento());
        conta.setTipoConta(dto.getTipoConta());
        conta.setStatus(dto.getStatus());
        // Você precisará de serviços para buscar o usuário e a categoria
        return conta;
    }
}
