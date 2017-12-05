package com.skb.demo.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skb.demo.domain.dto.ProductDto;
import com.skb.demo.storeLogic.redis.repo.ProductRepositoryImpl;
import com.skb.demo.storeLogic.redis.repository.exception.InsufficientProductsException;

@Service
public class ProductServiceImpl implements ProductService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private ProductRepositoryImpl productRepository;

    @Autowired
    public void setProductRepository(ProductRepositoryImpl productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public ProductDto getProductById(Integer id) {
        logger.debug("getProductById called");
        return productRepository.findOne(id);
    }

    @Override
    public ProductDto saveProduct(ProductDto productDto) {
        logger.debug("saveProduct called");
        return productRepository.save(productDto);
    }

    @Override
    public void deleteProduct(Integer id) {
        logger.debug("deleteProduct called");
        productRepository.delete(id);
    }
    
    @Override
	public boolean buy(ProductDto productDto, int orderedQuantity) throws InsufficientProductsException {
		boolean transactionStatus=false;
		int availableQuantity = productRepository.getAvailableProducts(productDto);
		if (orderedQuantity > availableQuantity) {
			throw new InsufficientProductsException();
		}
		productRepository.orderProduct(productDto, orderedQuantity);
		transactionStatus=true;
		return transactionStatus;
	}
}
