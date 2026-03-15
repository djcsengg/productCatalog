package org.example.projectcatalog.controllers;

import org.example.projectcatalog.dtos.ProductDto;
import org.example.projectcatalog.models.Product;
import org.example.projectcatalog.services.IProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.util.Assert;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @MockitoBean
    @Qualifier("storageProductService") // As we have 2 implementation of Iproduct we need to specify which one to mock
    private IProductService productService;

    @Autowired
    private ProductController productController;

    @Captor
    private ArgumentCaptor<Long> idCaptor;

    @Test
    @DisplayName("Checking if the Product is correctly returned")
    public void TestGetProductDetailsById_WithValidProductId_RunSuccessfully()
    {
        //Arrange
        Long id  = 101L;
        Product product = new Product();
        product.setId(101L);
       product.setProductName("Iphone");
        product.setProductPrice(100000D);
        when(productService.getProductById(id)).thenReturn(product);

        //Act
        ProductDto productDto = productController.getProductDetails(id);

        //Assert
        assertNotNull(productDto); //Not using equal as we are not storing the data coming from controller
        //assertEquals(productDto.getName(),"Iphone"); Case 1
        assertEquals(productDto.getName(),"Iphone12"); //Case 2
        assertEquals(productDto.getId(),id);
        assertEquals(productDto.getPrice(),100000D);

    }

    @Test
    @DisplayName("Should throw exception when Product ID is negative")
    void testGetProductById_NegativeId_ThrowsException() {
        // Arrange
        Long negativeId = -1L;

        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> { //()-> Mean this is executables
            productController.getProductDetails(negativeId);
        });

        //Assert
        assertEquals("Please pass productId greater than 0", exception.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when Product ID is zero")
    void testGetProductById_ZeroId_ThrowsException() {
        // Arrange
        Long zeroId = 0L;

        // Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productController.getProductDetails (zeroId);
        });

        //Assert
        assertEquals("Please pass positive productId", exception.getMessage());
    }

    @Test
    public void TestGetProductDetailsById_ProductServiceCalledWithCorrectArguments_RunSuccessfully() {
        //Arrange
        Long productId = 5L;
        Product product = new Product();
        product.setId(productId);
        product.setProductName("Macbook");
        when(productService.getProductById(productId)).thenReturn(product);

        //Act
        productController.getProductDetails(productId);

        //Assert
        verify(productService).getProductById(idCaptor.capture()); //It verifies if getProducyById was called or not .
                                                                  // If called then capture the argument passed in idCaptor
        assertEquals(productId,idCaptor.getValue());

    }

}