package soat.project.fastfoodsoat.domain.order.orderproduct;

import org.junit.jupiter.api.Test;
import soat.project.fastfoodsoat.domain.exception.NotificationException;
import soat.project.fastfoodsoat.domain.orderproduct.OrderProduct;
import soat.project.fastfoodsoat.domain.product.Product;
import soat.project.fastfoodsoat.domain.productcategory.ProductCategoryId;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class OrderProductTest {

    @Test
    void givenValidParams_whenCreateOrderProduct_thenInstantiateOrderProduct() {
        final var name = "podrao";
        final var description = "mal passado";
        final var value = BigDecimal.valueOf(25.00);
        final var imageURL = "img";
        final var categoryId = ProductCategoryId.of(1);
        final var quantity = 1;

        final var product = Product.newProduct(name, description, value, imageURL, categoryId);
        final var orderProduct = OrderProduct.newOrderProduct(value, quantity, product);

        assertNotNull(orderProduct);
        assertNull(orderProduct.getId());
        assertEquals(value, orderProduct.getValue());
        assertEquals(quantity, orderProduct.getQuantity());
        assertEquals(product, orderProduct.getProduct());
        assertNotNull(product.getCreatedAt());
        assertNotNull(product.getUpdatedAt());
        assertNull(product.getDeletedAt());
    }

    @Test
    void givenNullQuantity_whenCreateOrderProduct_thenThrowsNotificationException() {
        final var name = "podrao";
        final var description = "mal passado";
        final var value = BigDecimal.valueOf(25.00);
        final var imageURL = "img";
        final var categoryId = ProductCategoryId.of(1);

        final var product = Product.newProduct(name, description, value, imageURL, categoryId);

        final var exception = assertThrows(NotificationException.class,
                () -> OrderProduct.newOrderProduct(value, null, product));

        assertEquals(1, exception.getErrors().size());
        assertEquals("'quantity' should not be null", exception.getErrors().get(0).message());
    }

    @Test
    void givenZeroQuantity_whenCreateOrderProduct_thenThrowsNotificationException() {
        final var name = "podrao";
        final var description = "mal passado";
        final var value = BigDecimal.valueOf(25.00);
        final var imageURL = "img";
        final var categoryId = ProductCategoryId.of(1);
        final var quantity = 0;

        final var product = Product.newProduct(name, description, value, imageURL, categoryId);

        final var exception = assertThrows(NotificationException.class,
                () -> OrderProduct.newOrderProduct(value, quantity, product));

        assertEquals(1, exception.getErrors().size());
        assertEquals("'quantity' should be greater than zero", exception.getErrors().get(0).message());
    }

    @Test
    void givenNullValue_whenCreateOrderProduct_thenThrowsNotificationException() {
        final var name = "podrao";
        final var description = "mal passado";
        final var value = BigDecimal.valueOf(25.00);
        final var imageURL = "img";
        final var categoryId = ProductCategoryId.of(1);
        final var quantity = 1;

        final var product = Product.newProduct(name, description, value, imageURL, categoryId);

        final var exception = assertThrows(NotificationException.class,
                () -> OrderProduct.newOrderProduct(null, quantity, product));

        assertEquals(1, exception.getErrors().size());
        assertEquals("'value' should not be null", exception.getErrors().get(0).message());
    }

    @Test
    void givenZeroValue_whenCreateOrderProduct_thenThrowsNotificationException() {
        final var name = "podrao";
        final var description = "mal passado";
        final var value = BigDecimal.valueOf(25.00);
        final var imageURL = "img";
        final var categoryId = ProductCategoryId.of(1);
        final var quantity = 1;

        final var product = Product.newProduct(name, description, value, imageURL, categoryId);

        final var exception = assertThrows(NotificationException.class,
                () -> OrderProduct.newOrderProduct(BigDecimal.ZERO, quantity, product));

        assertEquals(1, exception.getErrors().size());
        assertEquals("'value' should be greater than zero", exception.getErrors().get(0).message());
    }

    @Test
    void givenNullProduct_whenCreateOrderProduct_thenThrowsNotificationException() {
        final var value = BigDecimal.valueOf(25.00);
        final var quantity = 1;

        final var exception = assertThrows(NotificationException.class,
                () -> OrderProduct.newOrderProduct(value, quantity, null));

        assertEquals(1, exception.getErrors().size());
        assertEquals("'product' should not be null", exception.getErrors().get(0).message());
    }

    @Test
    void givenValidOrderProduct_whenUpdate_thenAttributesShouldChange() {
        final var name = "podrao";
        final var description = "mal passado";
        final var value = BigDecimal.valueOf(25.00);
        final var imageURL = "img";
        final var categoryId = ProductCategoryId.of(1);
        final var quantity = 1;

        final var product = Product.newProduct(name, description, value, imageURL, categoryId);
        final var orderProduct = OrderProduct.newOrderProduct(value, quantity, product);

        final var newValue = BigDecimal.valueOf(30.00);
        final var newQuantity = 2;

        orderProduct.update(newValue, newQuantity);

        assertEquals(newValue, orderProduct.getValue());
        assertEquals(newQuantity, orderProduct.getQuantity());
        assertEquals(product, orderProduct.getProduct());
        assertNotNull(orderProduct.getCreatedAt());
        assertNotNull(orderProduct.getUpdatedAt());
        assertNull(orderProduct.getDeletedAt());
    }
}