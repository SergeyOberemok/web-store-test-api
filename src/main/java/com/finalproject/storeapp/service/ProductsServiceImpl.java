package com.finalproject.storeapp.service;

import com.finalproject.storeapp.model.Product;
import com.finalproject.storeapp.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("productsService")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class ProductsServiceImpl implements ProductsService {

    private ProductsRepository productsRepository;

    @Override
    public List<Product> findAll() {
        return productsRepository.findAll();
    }

    @Override
    public Product show(int productId) { return productsRepository.show(productId); }

    @Autowired
    public void setProductsRepository(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }
}
