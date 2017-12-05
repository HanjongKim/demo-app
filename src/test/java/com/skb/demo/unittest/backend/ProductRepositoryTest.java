package com.skb.demo.unittest.backend;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.skb.demo.domain.dto.ProductDto;
import com.skb.demo.storeLogic.redis.repo.ProductRepositoryImpl;

@RunWith(SpringRunner.class)
public class ProductRepositoryTest {
	
//    @MockBean
//    private  RedisConnection redisConnection;
//    @MockBean
//    private  RedisConnectionFactory redisConnectionFactory;
    @Spy
    private ProductRepositoryImpl productRepository;
//    private RedisTemplate<String, ProductDto> redisTemplate;
       
    private String KEY = "Product";
    private ProductDto productDto;
    private Integer id;
    
    @SuppressWarnings({ "rawtypes" })
    @Bean
    public RedisTemplate<?, ?> redisTemplate() {

      RedisConnection connectionMock = mock(RedisConnection.class);
      RedisConnectionFactory factoryMock = mock(RedisConnectionFactory.class);

      when(factoryMock.getConnection()).thenReturn(connectionMock);

      RedisTemplate template = new RedisTemplate<Object, Object>();
      template.setConnectionFactory(factoryMock);

      return template;
    }

    @SuppressWarnings({ "rawtypes" })
    @Bean
    public HashOperations opsForHash() {

      RedisConnection connectionMock = mock(RedisConnection.class);
      RedisConnectionFactory factoryMock = mock(RedisConnectionFactory.class);

      when(factoryMock.getConnection()).thenReturn(connectionMock);

      RedisTemplate template = new RedisTemplate<Object, Object>();
      template.setConnectionFactory(factoryMock);
      

      return template.opsForHash();
    }
    @Before
    public void setUp() {   
//    	when(redisConnectionFactory.getConnection()).thenReturn(redisConnection);
//    	redisTemplate = new RedisTemplate<String, Object>();
//    	redisTemplate.setConnectionFactory(redisConnectionFactory);
//    	redisTemplate.afterPropertiesSet();
//    	hashOps = redisTemplate.opsForHash();
 //   	redisTemplate = new RedisTemplate<String, Object>();
//    	RedisTemplate reisTemplate = new RedisTemplate<String, ProductDto>();
//    	productRepository = new ProductRepositoryImpl(reisTemplate);
    	productDto = new ProductDto();
    	id = new Integer(10);
    	
    	productDto.setId(id);
    	productDto.setPrice(new BigDecimal(5000000));
    	productDto.setProductId("P10");
    }
   
	@Test
    public void findOne_normal_success() {  
//		when(opsForHash().put(KEY, productDto.getId(),productDto)).thenReturn(null);
//		doNothing().when(productRepository).delete(5);
    	doNothing().when(spy(HashOperations.class)).put(KEY, productDto.getId(),productDto);
//     	doReturn(productDto).when(opsForHash()).put(KEY, productDto.getId(),productDto);
    	ProductDto returnProductDto = productRepository.findOne(productDto.getId());
    	if(null != returnProductDto){
    		System.out.println("returnProductDto");
    	}
    	if(null != productDto ){
    		System.out.println("productDto");
    	}
//    	assertThat(returnProductDto,is(equalTo(productDto)));
    } 
    
    @Ignore    
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
//        assertNotNull(product.getId()); //not null after save
//        //fetch from DB
//        Product fetchedProduct = productRepository.findOne(product.getId());
//        //should not be null
//        assertNotNull(fetchedProduct);
//        //should equal
//        assertEquals(product.getId(), fetchedProduct.getId());
//        assertEquals(product.getDescription(), fetchedProduct.getDescription());
//        //update description and save
//        fetchedProduct.setDescription("New Description");
//        productRepository.save(fetchedProduct);
//        //get from DB, should be updated
//        Product fetchedUpdatedProduct = productRepository.findOne(fetchedProduct.getId());
//        assertEquals(fetchedProduct.getDescription(), fetchedUpdatedProduct.getDescription());
//        //verify count of products in DB
//        long productCount = productRepository.count();
//        assertEquals(productCount, 1);
//        //get all products, list should only have one
//        Iterable<Product> products = productRepository.findAll();
//        int count = 0;
//        for(Product p : products){
//            count++;
//        }
//        assertEquals(count, 1);
    }

}
