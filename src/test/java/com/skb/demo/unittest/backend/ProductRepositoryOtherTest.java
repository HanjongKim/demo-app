package com.skb.demo.unittest.backend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.skb.demo.BaseRedisTest;
import com.skb.demo.domain.dto.ProductDto;
import com.skb.demo.storeLogic.redis.repo.ProductRepositoryImpl;


public class ProductRepositoryOtherTest extends BaseRedisTest {	
	
    private ProductRepositoryImpl productRepository;
    @Autowired
    public void setProductRepository(ProductRepositoryImpl productRepository) {
        this.productRepository = productRepository;
    }
 
    @Test
    public void testSaveProduct(){
        //setup product
        ProductDto productDto = new ProductDto();
        productDto.setDescription("Spring Framework Guru Shirt");
        productDto.setPrice(new BigDecimal("18.95"));
        productDto.setProductId("1234");
        //save product, verify has ID value after save
        assertNull(productDto.getId()); //null before save
        productRepository.save(productDto);
        assertNotNull(productDto.getId()); //not null after save
        //fetch from DB
        ProductDto fetchedProductDto = productRepository.findOne(productDto.getId());
        //should not be null
        assertNotNull(fetchedProductDto);
        //should equal
        assertEquals(productDto.getId(), fetchedProductDto.getId());
        assertEquals(productDto.getDescription(), fetchedProductDto.getDescription());
        //update description and save
        fetchedProductDto.setDescription("New Description");
        productRepository.save(fetchedProductDto);
        //get from DB, should be updated
        ProductDto fetchedUpdatedProductDto = productRepository.findOne(fetchedProductDto.getId());
        assertEquals(fetchedProductDto.getDescription(), fetchedUpdatedProductDto.getDescription());

    }

}
