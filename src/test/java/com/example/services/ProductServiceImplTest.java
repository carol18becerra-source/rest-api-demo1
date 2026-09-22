package com.example.services;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.dao.PresentationDao;
import com.example.dao.ProductDao;
import com.example.entities.Presentation;
import com.example.entities.Product;

/*Los test hay que realizarlos en aislamiento. Los test a la capa de servicio ya se consideran
 *  test de integracion porque 
 * dependen de al capa de repositorio, pero dichas dependencias se simulan (mock)
 *  en lugar de inyectarlas realmente y aqui 
 * entra el framework Mockito */
@ExtendWith(MockitoExtension.class)

class ProductServiceImplTest {

	@Mock
	/*
	 * la dependecia @Mock simula las dependencias en lugar de inyectarlas
	 * realmente, para aislar todo lo posible el test que se esta implementando
	 */
	private ProductDao productDao;

	@Mock
	private PresentationDao presentationDao;

	@InjectMocks
	private ProductServiceImpl productServiceImpl;
	Product product1, product2;

	List<Product> productsList = new ArrayList<>();

	@BeforeEach
	void setUp() {
		Presentation presentation = Presentation.builder().name("unidades").description("por unidades").build();

		product1 = Product.builder().name("Google Pixel 7").description("Telefono de google").price(new BigDecimal(400))
				.stock(1000).productImage(null).presentation(presentation).build();

		product2 = Product.builder().name("IPhone 17 Pro").description("Telefono de Apple").price(new BigDecimal(1300))
				.stock(1500).productImage(null).presentation(presentation).build();

		productsList.add(product1);
		productsList.add(product2);
	}

	@Test
	void testFindAllPageable() {
		fail("Not yet implemented");
	}

	@Test
	void testFindAllSort() {
		fail("Not yet implemented");
	}

	@Test
	void testFindById() {
		fail("Not yet implemented");
	}

	@Test
	@DisplayName("Test del servicio para persistir un producto")
	void testSave() {

		/* necesitamos crear productos para todos los test de la capa de servicios */

		// given. Dado que se persiste un producto, es el caso de prueba
		given(productDao.save(product1)).willReturn(product1);

		// when. cuando se guarde el producto, utilizando el servicio
		Product productoGuardado = productServiceImpl.save(product1);

		// then
		assertThat(productoGuardado).isNotNull();

	}

	@Test

	void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	@DisplayName("Test para recuperar los dos productos productos creados")
	void testFindAll() {

		given(productDao.findAll()).willReturn(productsList);
		List<Product> products = productServiceImpl.findAll();
		assertEquals(2, products.size());

		// otras formas de hacerlo con assertThat
//		when(productServiceImpl.findAll()).thenReturn(productsList);
//		List<Product> products = productServiceImpl.findAll();
//		assertThat(products).hasSize(2);

//		when(productServiceImpl.findAll()).thenReturn(productsList);
//		List<Product> result = productServiceImpl.findAll();
//		assertThat(result).hasSize(2);
	}

	@Test
	@DisplayName("Test para recuperar una lista vacia de productos")
	void testEmptyProducList() {
		// given
		given(productDao.findAll()).willReturn(Collections.emptyList());

		// when
		List<Product> products = productServiceImpl.findAll();

		// then
		assertThat(products).isEmpty();

	}

}
