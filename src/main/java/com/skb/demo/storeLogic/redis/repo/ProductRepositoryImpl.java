package com.skb.demo.storeLogic.redis.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.skb.demo.domain.dto.ProductDto;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
	
    private static final String KEY = "Product";
    
    @Autowired    
    private RedisTemplate<String, ProductDto> redisTemplate;


	public ProductDto findOne(Integer id) {
		return (ProductDto) redisTemplate.opsForHash().get(KEY, id);
	}

	public ProductDto save(ProductDto productDto) {
		redisTemplate.opsForHash().put(KEY, productDto.getId(), productDto);
		return productDto;
	}

	public void delete(Integer id) {
		redisTemplate.opsForHash().delete(KEY, id);
		
	}

	public int getAvailableProducts(ProductDto productDto) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void orderProduct(ProductDto productDto, int orderedQuantity) {
		// TODO Auto-generated method stub
		
	}


}
