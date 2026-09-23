package com.example.controllers;

import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.example.entities.Presentation;
import com.example.entities.Product;
import com.example.services.ProductService;
import com.example.utilities.FileDownloadUtil;
import com.example.utilities.FileUploadUtil;
import com.example.utilities.FileUtil;

import tools.jackson.databind.ObjectMapper;

@WebMvcTest(ProductController.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@AutoConfigureMockMvc
class ProductControllerTest {
	@Autowired
	MockMvc mockMvc;

	@MockitoBean
	ProductService productService;

	@MockitoBean
	FileUploadUtil fileUploadUtil;

	@MockitoBean
	FileDownloadUtil fileDownloadUtil;

	@MockitoBean
	FileUtil fileUtil;

	@Autowired
	ObjectMapper objectMapper;

	List<Product> products = new ArrayList<>();
	Presentation presentation1, presentation2;
	Product product1, product2;

	@BeforeEach
	void setUp() {
		presentation1 = Presentation.builder().name("Decenas").description("Decenas de unidades").build();
		presentation2 = Presentation.builder().name("Unidades").description("Por unidades").build();

		product1 = Product.builder().name("camara ").description("HP camara").price(new BigDecimal("500")).stock(1900)
				.productImage(null).presentation(presentation1).build();

		product2 = Product.builder().name("Frigorifico").description("General Electric").price(new BigDecimal("2500"))
				.stock(3900).productImage(null).presentation(presentation2).build();

		products.add(product1);
		products.add(product2);
	}

	@Test
	@DisplayName("Controller test que recupera todos los productos")
	void testFindAll() throws Exception {
		// given

		// El controlador ordena con Sort.by("nombre"), no "name"
		given(productService.findAll(Sort.by("nombre"))).willReturn(products);

		// when
		ResultActions response = mockMvc.perform(get("/products").accept(MediaType.APPLICATION_JSON));

		// then
		response.andExpect(status().isOk()).andDo(print())
				.andExpect(jsonPath("$.productos.size()", is(products.size())));
	}

	@Test
	@DisplayName("Controller test para persistir un producto")
	void testSaveProduct() {

		// given

	}
}
