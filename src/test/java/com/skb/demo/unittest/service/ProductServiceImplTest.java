package com.skb.demo.unittest.service;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.skb.demo.BaseServiceTest;
import com.skb.demo.domain.dto.ProductDto;
import com.skb.demo.domain.service.ProductServiceImpl;
import com.skb.demo.storeLogic.redis.repo.ProductRepositoryImpl;
import com.skb.demo.storeLogic.redis.repository.exception.InsufficientProductsException;

public class ProductServiceImplTest extends BaseServiceTest {

		
		private ProductServiceImpl productServiceImpl;
		
		@MockBean
		private ProductRepositoryImpl productRepository;
		@MockBean
		private ProductDto productDto;
		
	    private int purchaseQuantity = 15;
		
		@Before
		public void setUp() {
			productServiceImpl = new ProductServiceImpl();
			productServiceImpl.setProductRepository(productRepository);
		}
		
		@Test
		public void getProductById_normal_success() throws Exception {
			when(productRepository.findOne(5)).thenReturn(productDto);
			ProductDto findProductDto = productServiceImpl.getProductById(5);
			assertThat(findProductDto,is(equalTo(productDto)));
		}
		
		
		@Test
		public void getProductById_normal_fail() throws Exception {
			when(productRepository.findOne(5)).thenReturn(productDto);
			ProductDto findProductDto = productServiceImpl.getProductById(3);
			assertThat(findProductDto,is(not(equalTo(productDto))));
		}
		
		@Test
		public void saveProduct_normal_success() throws Exception {
			when(productRepository.save(productDto)).thenReturn(productDto);
			ProductDto saveProductDto = productServiceImpl.saveProduct(productDto);
			assertThat(saveProductDto, is(equalTo(productDto)));
		}
		
		@Test
		public void deleteProduct_normal_success() throws Exception {
			doNothing().when(productRepository).delete(5);
			productServiceImpl.deleteProduct(5);
			verify(productRepository, times(1)).delete(5);
		}
		
		
		@Test
		public void deleteProduct_normal_fail() throws Exception {
			doNothing().when(productRepository).delete(5);
			productServiceImpl.deleteProduct(3);
			verify(productRepository, times(0)).delete(5);
		}
		
	    @Test
	    public void buy_normal_success() throws InsufficientProductsException {
	        int availableQuantity = 30;
	        when(productRepository.getAvailableProducts(productDto)).thenReturn(availableQuantity);
	        productServiceImpl.buy(productDto, purchaseQuantity);
	        verify(productRepository).orderProduct(productDto, purchaseQuantity);
	        verify(productRepository, atLeastOnce()).getAvailableProducts(productDto);
	        InOrder order = inOrder(productRepository);
	        order.verify(productRepository).getAvailableProducts(productDto);
	        order.verify(productRepository).orderProduct(productDto, purchaseQuantity);



	    }

	    @Test(expected = InsufficientProductsException.class)
	    public void buy_normal_exception() throws InsufficientProductsException {
	        int availableQuantity = 3;
	        when(productRepository.getAvailableProducts(productDto)).thenReturn(availableQuantity);
	        try {
	        	productServiceImpl.buy(productDto, purchaseQuantity);
	        } catch (InsufficientProductsException e) {
	            verify(productRepository, times(0)).orderProduct(productDto, purchaseQuantity);
	            throw e;
	        }
	    }
		
}
