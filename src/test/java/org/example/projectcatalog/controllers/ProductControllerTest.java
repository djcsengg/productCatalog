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

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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

    @Captor
    private ArgumentCaptor<Product> productCaptor;

    @Test
    @DisplayName("Checking if the Product is correctly returned")
    public void TestGetProductDetailsById_WithValidProductId_RunSuccessfully()
    {
        //Arrange
        Long id  = 101L;
        Product product = new Product();
        product.setId(101L);
       product.setName("Iphone");
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
        product.setName("Macbook");
        when(productService.getProductById(productId)).thenReturn(product);

        //Act
        productController.getProductDetails(productId);

        //Assert
        verify(productService).getProductById(idCaptor.capture()); //It verifies if getProducyById was called or not .
                                                                  // If called then capture the argument passed in idCaptor
        assertEquals(productId,idCaptor.getValue());

    }

    @Test
    @DisplayName("Should return a list of ProductDtos when products exist")
    public void testGetAllProducts_ReturnsListSuccessfully() {
        // Arrange
        Product p1 = new Product();
        p1.setId(1L);
        p1.setName("Laptop");

        Product p2 = new Product();
        p2.setId(2L);
        p2.setName("Phone");

        List<Product> mockProducts = Arrays.asList(p1, p2);

        // Tell the mock service to return our list
        when(productService.getAllProducts()).thenReturn(mockProducts);

        // Act
        List<ProductDto> result = productController.getAllProducts();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size(), "The result list size should match the mock data size");

        // Verify specific data mapping for the first element
        assertEquals(1L, result.get(0).getId());
        assertEquals("Laptop", result.get(0).getName());

        // Verify the second element
        assertEquals("Phone", result.get(1).getName());
    }

    @Test
    @DisplayName("Should create and return a new product")
    void testAddProduct_ReturnsCreatedProduct() {
        // Arrange
        ProductDto inputDto = new ProductDto();
        inputDto.setName("Sony TV");
        inputDto.setPrice(50000.0);

        Product mockSavedProduct = new Product();
        mockSavedProduct.setId(1L);
        mockSavedProduct.setName("Sony TV");
        mockSavedProduct.setProductPrice(50000.0);

        // Mock the service behavior (using any() because 'to()' creates a new instance internally)
        when(productService.createProduct(any(Product.class))).thenReturn(mockSavedProduct);

        // Act
        ProductDto result = productController.addProduct(inputDto);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Sony TV", result.getName());
        verify(productService).createProduct(any(Product.class));
    }


    @Test
    @DisplayName("Should update existing product and return updated DTO")
    void testUpdateProduct_ReturnsUpdatedProduct() {
        // Arrange
        Long id = 1L;
        ProductDto updateRequest = new ProductDto();
        updateRequest.setName("Macbook Pro");

        Product updatedProduct = new Product();
        updatedProduct.setId(id);
        updatedProduct.setName("Macbook Pro");

        when(productService.replaceProduct(eq(id), any(Product.class))).thenReturn(updatedProduct);

        // Act
        ProductDto result = productController.updateProduct(id, updateRequest);

        // Assert
        assertEquals("Macbook Pro", result.getName());
        assertEquals(id, result.getId());
    }

    @Test
    @DisplayName("Should return deleted product details when delete is called")
    void testDeleteProduct_ReturnsDeletedProductDetails() {
        // Arrange
        Long id = 5L;
        Product deletedProduct = new Product();
        deletedProduct.setId(id);
        deletedProduct.setName("Old Phone");

        when(productService.deleteProduct(id)).thenReturn(deletedProduct);

        // Act
        ProductDto result = productController.deleteProduct(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Old Phone", result.getName());
        verify(productService).deleteProduct(id);
    }

    @Test
    void testInternalMapping() {


        // 1. Arrange: Create the "someDto" (the input)
        ProductDto someDto = new ProductDto();
        someDto.setName("Gaming Console");
        someDto.setPrice(45000.0);

        // Setup the mock to avoid NullPointerException
        when(productService.createProduct(any(Product.class))).thenReturn(new Product());

        // 2. Act: Pass "someDto" into the controller
        productController.addProduct(someDto);

        // 3. Assert: Capture the Product object that the Controller's to() method created
        verify(productService).createProduct(productCaptor.capture());
        Product capturedProduct = productCaptor.getValue();

        // This proves your private to() method is working correctly!
        assertEquals("Gaming Console", capturedProduct.getName());
    }

}