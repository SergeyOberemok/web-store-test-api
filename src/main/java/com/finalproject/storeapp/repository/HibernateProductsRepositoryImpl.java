package com.finalproject.storeapp.repository;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("productsRepository")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public abstract class HibernateProductsRepositoryImpl implements ProductsRepository {
}
