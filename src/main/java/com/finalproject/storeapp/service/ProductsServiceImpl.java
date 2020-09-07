package com.finalproject.storeapp.service;

import com.finalproject.storeapp.model.Product;
import com.finalproject.storeapp.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("productsService")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductsServiceImpl implements ProductsService {

    private final ProductsRepository productsRepository;

    @Override
    public List<Product> findAll() {
        return productsRepository.findAll();
    }

    @Override
    public Product show(long productId) { return productsRepository.findById(productId).orElse(null); }

    @Override
    public Product save(Product product) throws Exception {
        return productsRepository.save(product);
    }

    @Override
    public void delete(long productId) {
        productsRepository.deleteById(productId);
    }
}
