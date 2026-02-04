package com.deliverytech.delivery.repository;

import com.deliverytech.delivery.entity.Produto;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByRestauranteId(Long restauranteId);

    List<Produto> findByDisponivelTrue();

    List<Produto> findByCategoria(String categoria);

    List<Produto> findByPrecoLessThanEqual(BigDecimal preco);
}
