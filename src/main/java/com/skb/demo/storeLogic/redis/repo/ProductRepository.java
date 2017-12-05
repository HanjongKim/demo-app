package com.skb.demo.storeLogic.redis.repo;

import com.skb.demo.domain.dto.ProductDto;

public interface ProductRepository {
	
	public ProductDto findOne(Integer id);
	public ProductDto save(ProductDto productDto);
	public void delete(Integer id);
	public int getAvailableProducts(ProductDto productDto);
	public void orderProduct(ProductDto productDto, int orderedQuantity);
}
