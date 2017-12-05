package com.skb.demo.domain.service;

import com.skb.demo.domain.dto.ProductDto;
import com.skb.demo.storeLogic.redis.repository.exception.InsufficientProductsException;

public interface ProductService {

    public ProductDto getProductById(Integer id);
    public ProductDto saveProduct(ProductDto product);
    public void deleteProduct(Integer id);
	public boolean buy(ProductDto productDto, int orderedQuantity) throws InsufficientProductsException;
}
