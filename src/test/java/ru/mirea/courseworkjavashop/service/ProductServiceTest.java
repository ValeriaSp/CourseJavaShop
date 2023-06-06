package ru.mirea.courseworkjavashop.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import ru.mirea.courseworkjavashop.domain.model.Product;
import ru.mirea.courseworkjavashop.repository.ProductRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductService(productRepository);
    }

    @Test
    public void testGetAllProducts() {
        // Arrange
        List<Product> expectedProducts = new ArrayList<>();

        var product1 = new Product();
        product1.setName("Test Product 1");

        var product2 = new Product();
        product2.setName("Test Product 2");

        when(productRepository.findAll()).thenReturn(expectedProducts);

        // Act
        List<Product> actualProducts = productService.getAllProducts();

        // Assert
        verify(productRepository, times(1)).findAll();
        Assertions.assertEquals(expectedProducts, actualProducts);
    }

    @Test
    public void testFindById() {
        // Arrange
        int productId = 1;
        var product = new Product();
        product.setName("Test Product");
        Optional<Product> expectedProduct = Optional.of(product);
        when(productRepository.findById(productId)).thenReturn(expectedProduct);

        // Act
        Optional<Product> actualProduct = productService.findById(productId);

        // Assert
        verify(productRepository, times(1)).findById(productId);
        Assertions.assertEquals(expectedProduct, actualProduct);
    }

    @Test
    public void testGetById() {
        // Arrange
        int productId = 1;

        var expectedProduct = new Product();
        expectedProduct.setName("Test Product");


        when(productRepository.findById(productId)).thenReturn(Optional.of(expectedProduct));

        // Act
        Product actualProduct = productService.getById(productId);

        // Assert
        verify(productRepository, times(1)).findById(productId);
        Assertions.assertEquals(expectedProduct, actualProduct);
    }

    @Test
    public void testGetCartTotal() {
        Set<Product> products = new HashSet<>();

        var product1 = new Product();
        product1.setPrice(20.0);
        product1.setDiscount(true);
        product1.setDiscountPrice(10.0);
        products.add(product1);

        var product2 = new Product();

        product2.setPrice(10.0);
        product2.setDiscount(false);
        product2.setDiscountPrice(5.0);
        products.add(product2);


        double cartTotal = ProductService.getCartTotal(products);

        Assertions.assertEquals(20.0, cartTotal);
    }

    @Test
    public void testGetAll() {
        List<Product> expectedProducts = new ArrayList<>();
        var product1 = new Product();
        var product2 = new Product();
        product1.setName("Test Product 1");
        product2.setName("Test Product 2");
        expectedProducts.add(product1);
        expectedProducts.add(product2);
        when(productRepository.findAll()).thenReturn(expectedProducts);

        List<Product> actualProducts = productService.getAll();

        verify(productRepository, times(1)).findAll();
        Assertions.assertEquals(expectedProducts, actualProducts);
    }

    @Test
    public void testSave() {

        var productToSave = new Product();
        productToSave.setName("Test Product");
        productToSave.setPrice(10.0);

        productService.save(productToSave);

        verify(productRepository, times(1)).save(productToSave);
    }

}

