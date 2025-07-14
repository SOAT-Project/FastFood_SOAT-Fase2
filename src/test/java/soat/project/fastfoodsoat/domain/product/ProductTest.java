package soat.project.fastfoodsoat.domain.product;

import org.junit.jupiter.api.Test;
import soat.project.fastfoodsoat.domain.exception.NotificationException;
import soat.project.fastfoodsoat.domain.productcategory.ProductCategoryId;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void givenValidParams_whenCreateProduct_thenInstantiateProduct() {
        final var name = "podrao";
        final var description = "mal passado";
        final var value = BigDecimal.valueOf(25.00);
        final var imageURL = "img";
        final var categoryId = ProductCategoryId.of(1);

        final var product = Product.newProduct(name, description, value, imageURL, categoryId);

        assertNotNull(product);
        assertNull(product.getId());
        assertEquals(name, product.getName());
        assertEquals(description, product.getDescription());
        assertEquals(value, product.getValue());
        assertEquals(imageURL, product.getImageURL());
        assertEquals(categoryId, product.getProductCategoryId());
        assertNotNull(product.getCreatedAt());
        assertNotNull(product.getUpdatedAt());
        assertNull(product.getDeletedAt());
    }

    @Test
    void givenNullName_whenCreateProduct_thenThrowsNotificationException() {
        final String name = null;
        final var description = "mal passado";
        final var value = BigDecimal.TEN;
        final var imageURL = "img";
        final var categoryId = ProductCategoryId.of(1);

        final var exception = assertThrows(NotificationException.class,
                () -> Product.newProduct(name, description, value, imageURL, categoryId));

        assertEquals(1, exception.getErrors().size());
        assertEquals("'name' should not be null", exception.getErrors().get(0).message());
    }

    @Test
    void givenEmptyName_whenCreateProduct_thenThrowsNotificationException() {
        final var name = "   ";
        final var description = "mal passado";
        final var value = BigDecimal.TEN;
        final var imageURL = "img";
        final var categoryId = ProductCategoryId.of(1);

        final var exception = assertThrows(NotificationException.class,
                () -> Product.newProduct(name, description, value, imageURL, categoryId));

        assertEquals("'name' should not be empty", exception.getErrors().get(0).message());
    }

    @Test
    void givenShortName_whenCreateProduct_thenThrowsNotificationException() {
        final var name = "ab";
        final var description = "mal passado";
        final var value = BigDecimal.TEN;
        final var imageURL = "img";
        final var categoryId = ProductCategoryId.of(1);

        final var exception = assertThrows(NotificationException.class,
                () -> Product.newProduct(name, description, value, imageURL, categoryId));

        assertEquals("'name' must be between 3 and 100 characters", exception.getErrors().get(0).message());
    }

    @Test
    void givenLongName_whenCreateProduct_thenThrowsNotificationException() {
        final var name = "a".repeat(101);
        final var description = "mal passado";
        final var value = BigDecimal.TEN;
        final var imageURL = "img";
        final var categoryId = ProductCategoryId.of(1);

        final var exception = assertThrows(NotificationException.class,
                () -> Product.newProduct(name, description, value, imageURL, categoryId));

        assertEquals("'name' must be between 3 and 100 characters", exception.getErrors().get(0).message());
    }

    @Test
    void givenNullDescription_whenCreateProduct_thenThrowsNotificationException() {
        final String description = null;
        final var name = "podrao";
        final var value = BigDecimal.TEN;
        final var imageURL = "img";
        final var categoryId = ProductCategoryId.of(1);

        final var exception = assertThrows(NotificationException.class,
                () -> Product.newProduct(name, description, value, imageURL, categoryId));

        assertEquals("'description' should not be null", exception.getErrors().get(0).message());
    }

    @Test
    void givenEmptyDescription_whenCreateProduct_thenThrowsNotificationException() {
        final var description = " ";
        final var name = "podrao";
        final var value = BigDecimal.TEN;
        final var imageURL = "img";
        final var categoryId = ProductCategoryId.of(1);

        final var exception = assertThrows(NotificationException.class,
                () -> Product.newProduct(name, description, value, imageURL, categoryId));

        assertEquals("'description' should not be empty", exception.getErrors().get(0).message());
    }

    @Test
    void givenTooLongDescription_whenCreateProduct_thenThrowsNotificationException() {
        final var description = "a".repeat(256);
        final var name = "podrao";
        final var value = BigDecimal.TEN;
        final var imageURL = "img";
        final var categoryId = ProductCategoryId.of(1);

        final var exception = assertThrows(NotificationException.class,
                () -> Product.newProduct(name, description, value, imageURL, categoryId));

        assertEquals("'description' must not exceed 255 characters", exception.getErrors().get(0).message());
    }

    @Test
    void givenNullValue_whenCreateProduct_thenThrowsNotificationException() {
        final var name = "podrao";
        final var description = "mal passado";
        final BigDecimal value = null;
        final var imageURL = "img";
        final var categoryId = ProductCategoryId.of(1);

        final var exception = assertThrows(NotificationException.class,
                () -> Product.newProduct(name, description, value, imageURL, categoryId));

        assertEquals("'value' should not be null", exception.getErrors().get(0).message());
    }

    @Test
    void givenNegativeValue_whenCreateProduct_thenThrowsNotificationException() {
        final var name = "podrao";
        final var description = "mal passado";
        final var value = BigDecimal.valueOf(-1);
        final var imageURL = "img";
        final var categoryId = ProductCategoryId.of(1);

        final var exception = assertThrows(NotificationException.class,
                () -> Product.newProduct(name, description, value, imageURL, categoryId));

        assertEquals("'value' must be greater than zero", exception.getErrors().get(0).message());
    }

    @Test
    void givenNullImageURL_whenCreateProduct_thenThrowsNotificationException() {
        final var name = "podrao";
        final var description = "mal passado";
        final var value = BigDecimal.TEN;
        final String imageURL = null;
        final var categoryId = ProductCategoryId.of(1);

        final var exception = assertThrows(NotificationException.class,
                () -> Product.newProduct(name, description, value, imageURL, categoryId));

        assertEquals("'imageURL' should not be null", exception.getErrors().get(0).message());
    }

    @Test
    void givenEmptyImageURL_whenCreateProduct_thenThrowsNotificationException() {
        final var name = "podrao";
        final var description = "mal passado";
        final var value = BigDecimal.TEN;
        final var imageURL = " ";
        final var categoryId = ProductCategoryId.of(1);

        final var exception = assertThrows(NotificationException.class,
                () -> Product.newProduct(name, description, value, imageURL, categoryId));

        assertEquals("'imageURL' should not be empty", exception.getErrors().get(0).message());
    }

    @Test
    void givenNullCategory_whenCreateProduct_thenThrowsNotificationException() {
        final var name = "podrao";
        final var description = "mal passado";
        final var value = BigDecimal.TEN;
        final var imageURL = "img";
        final ProductCategoryId categoryId = null;

        final var exception = assertThrows(NotificationException.class,
                () -> Product.newProduct(name, description, value, imageURL, categoryId));

        assertEquals("'productCategoryId' should not be null", exception.getErrors().get(0).message());
    }

    @Test
    void givenValidProduct_whenUpdate_thenAttributesShouldChange() {
        final var product = Product.newProduct("podrao", "mal passado", BigDecimal.ONE, "img", ProductCategoryId.of(1));

        final var newName = "X-Tudo";
        final var newDesc = "Novo";
        final var newVal = BigDecimal.valueOf(99);
        final var newImg = "url.com";
        final var newCat = ProductCategoryId.of(2);

        product.update(newName, newDesc, newVal, newImg, newCat);

        assertEquals(newName, product.getName());
        assertEquals(newDesc, product.getDescription());
        assertEquals(newVal, product.getValue());
        assertEquals(newImg, product.getImageURL());
        assertEquals(newCat, product.getProductCategoryId());
    }
}