// 1. RegisterPage.java (Page Object Class)
package com.testinium.pages;

import com.testinium.steps.BaseSteps;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class ProductsPage extends BaseSteps {

    public void launchBrowser() {
        driver.manage().window().maximize();
    }

    public void navigateToUrl(String url) {
        driver.get(url);
    }

    public boolean isHomePageVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by("homeHtml"))).isDisplayed();
    }

    public void clickProductsButton() {
        wait.until(ExpectedConditions.elementToBeClickable(by("productsBtn"))).click();
    }

    public boolean isAllProductsPageVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by("allProductsHeader"))).isDisplayed();
    }

    public boolean isProductsListVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by("productsList"))).isDisplayed();
    }

    public void clickViewFirstProduct() {
        wait.until(ExpectedConditions.elementToBeClickable(by("viewFirstProductBtn"))).click();
    }

    public boolean isProductDetailPageVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by("productDetailSection"))).isDisplayed();
    }

    public boolean isProductDetailsVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by("productName"))).isDisplayed()
                && wait.until(ExpectedConditions.visibilityOfElementLocated(by("productCategory"))).isDisplayed()
                && wait.until(ExpectedConditions.visibilityOfElementLocated(by("productPrice"))).isDisplayed()
                && wait.until(ExpectedConditions.visibilityOfElementLocated(by("productAvailability"))).isDisplayed()
                && wait.until(ExpectedConditions.visibilityOfElementLocated(by("productCondition"))).isDisplayed()
                && wait.until(ExpectedConditions.visibilityOfElementLocated(by("productBrand"))).isDisplayed();
    }
}
