package br.com.bookstore.service;

import br.com.bookstore.model.*;
import br.com.bookstore.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class VendaService {

    private final VendaRepository vendas;
    private final ItemVendaRepository itens;
    private final LivroRepository livros;

    public VendaService(VendaRepository vendas, ItemVendaRepository itens, LivroRepository livros) {
        this.vendas = vendas;
        this.itens = itens;
        this.livros = livros;
    }

    @Transactional
    public Venda realizarVenda(Long usuarioId, List<Map<String, Object>> produtos) {
        if (produtos == null || produtos.isEmpty()) {
            throw new IllegalArgumentException("Adicione pelo menos um livro à venda.");
        }

        Venda venda = new Venda();
        venda.setData(LocalDateTime.now());
        venda.setUsuarioId(usuarioId);
        venda.setTotal(BigDecimal.ZERO);
        vendas.save(venda);

        BigDecimal total = BigDecimal.ZERO;
        List<ItemVenda> itensVenda = new ArrayList<>();

        for (Map<String, Object> produto : produtos) {
            Long livroId = Long.valueOf(produto.get("livroId").toString());
            int quantidade = Integer.parseInt(produto.get("quantidade").toString());

            if (quantidade <= 0) {
                throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
            }

            Livro livro = livros.findById(livroId)
                    .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado."));

            if (livro.getEstoque() == null || livro.getEstoque() < quantidade) {
                throw new IllegalArgumentException("Estoque insuficiente para: " + livro.getTitulo());
            }

            BigDecimal subtotal = livro.getPreco().multiply(BigDecimal.valueOf(quantidade));
            total = total.add(subtotal);

            livro.setEstoque(livro.getEstoque() - quantidade);
            livros.save(livro);

            ItemVenda item = new ItemVenda();
            item.setVenda(venda);
            item.setLivro(livro);
            item.setQuantidade(quantidade);
            item.setPrecoUnitario(livro.getPreco());
            item.setSubtotal(subtotal);
            itensVenda.add(item);
        }

        venda.setTotal(total);
        vendas.save(venda);
        itens.saveAll(itensVenda);

        return venda;
    }
}
