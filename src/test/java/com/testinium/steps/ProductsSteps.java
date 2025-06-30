// 3. ProductsSteps.java (Step Definitions)
package com.testinium.steps;

import com.thoughtworks.gauge.Step;
import com.testinium.pages.ProductsPage;

import static org.junit.Assert.assertTrue;

public class ProductsSteps extends BaseSteps {
    ProductsPage productsPage = new ProductsPage();

    @Step("Click on 'Products' button")
    public void clickProductsButton() {
        productsPage.clickProductsButton();
    }

    @Step("Verify user is navigated to ALL PRODUCTS page successfully")
    public void verifyAllProductsPage() {
        assertTrue("All Products page is not visible", productsPage.isAllProductsPageVisible());
    }

    @Step("The products list is visible")
    public void verifyProductsListVisible() {
        assertTrue("Products list is not visible", productsPage.isProductsListVisible());
    }

    @Step("Click on 'View Product' of first product")
    public void clickViewFirstProduct() {
        productsPage.clickViewFirstProduct();
    }

    @Step("User is landed to product detail page")
    public void verifyProductDetailPage() {
        assertTrue("Product detail page is not visible", productsPage.isProductDetailPageVisible());
    }

    @Step("Verify that detail is visible: product name, category, price, availability, condition, brand")
    public void verifyProductDetails() {
        assertTrue("Product details are not fully visible", productsPage.isProductDetailsVisible());
    }
}
