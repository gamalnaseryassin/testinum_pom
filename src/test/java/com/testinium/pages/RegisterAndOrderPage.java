// === Generated Register + Place Order Module Based on Use Case 15 ===

// 1. Page Object Class: RegisterAndOrderPage.java
package com.testinium.pages;

import com.testinium.steps.BaseSteps;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.junit.Assert;
import java.util.List;

public class RegisterAndOrderPage extends BaseSteps {

    public void clickSignupLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(by("signupLoginBtn"))).click();
    }

    public void enterNameAndEmail(String name, String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(by("nameField"))).sendKeys(name);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by("emailField"))).sendKeys(email);
    }

    public void clickSignupButton() {
        wait.until(ExpectedConditions.elementToBeClickable(by("signupBtn"))).click();
    }

    public void fillAccountInformation(String password, String day, String month, String year) {
        wait.until(ExpectedConditions.elementToBeClickable(by("titleMr"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(by("password"))).sendKeys(password);
        new Select(wait.until(ExpectedConditions.elementToBeClickable(by("days")))).selectByValue(day);
        new Select(wait.until(ExpectedConditions.elementToBeClickable(by("months")))).selectByValue(month);
        new Select(wait.until(ExpectedConditions.elementToBeClickable(by("years")))).selectByValue(year);
    }

    public void checkNewsletter() {
        wait.until(ExpectedConditions.elementToBeClickable(by("newsletterCheckbox"))).click();
    }

    public void checkOffers() {
        wait.until(ExpectedConditions.elementToBeClickable(by("offersCheckbox"))).click();
    }

    public void fillAddress(String firstName, String lastName, String company, String address1, String address2,
                            String country, String state, String city, String zip, String mobile) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(by("firstName"))).sendKeys(firstName);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by("lastName"))).sendKeys(lastName);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by("company"))).sendKeys(company);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by("address1"))).sendKeys(address1);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by("address2"))).sendKeys(address2);
        new Select(wait.until(ExpectedConditions.elementToBeClickable(by("country")))).selectByVisibleText(country);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by("state"))).sendKeys(state);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by("city"))).sendKeys(city);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by("zipcode"))).sendKeys(zip);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by("mobileNumber"))).sendKeys(mobile);
    }

    public void clickCreateAccount() {
        wait.until(ExpectedConditions.elementToBeClickable(by("createAccountBtn"))).click();
    }

    public void clickContinue() {
        wait.until(ExpectedConditions.elementToBeClickable(by("continueBtn"))).click();
    }

    public void verifyLoggedInAs() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(by("loggedInAs"))).isDisplayed();
    }

    public void addProductsToCart() {
        List<WebElement> addButtons = driver.findElements(by("addToCartBtn"));
        if (!addButtons.isEmpty()) {
            addButtons.get(0).click(); // you can iterate or adjust index as needed
        } else {
            throw new RuntimeException("No add to cart buttons found!");
        }
    }

    public void clickCartButton() {
        wait.until(ExpectedConditions.elementToBeClickable(by("cartBtn"))).click();
    }

    public void clickProceedToCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(by("proceedToCheckoutBtn"))).click();
    }

    public void verifyAddressDetails() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(by("addressDetailsSection"))).isDisplayed();
    }

    public void enterComment(String comment) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(by("commentTextArea"))).sendKeys(comment);
    }

    public void clickPlaceOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(by("placeOrderBtn"))).click();
    }

    public void enterPaymentDetails(String nameOnCard, String cardNumber, String cvc, String expMonth, String expYear) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(by("nameOnCard"))).sendKeys(nameOnCard);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by("cardNumber"))).sendKeys(cardNumber);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by("cvc"))).sendKeys(cvc);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by("expiryMonth"))).sendKeys(expMonth);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by("expiryYear"))).sendKeys(expYear);
    }

    public void clickPayAndConfirm() {
        wait.until(ExpectedConditions.elementToBeClickable(by("payAndConfirmBtn"))).click();
    }

    public void verifyOrderSuccessMessage() {
        boolean isDisplayed = wait.until(ExpectedConditions.visibilityOfElementLocated(by("orderSuccessMessage"))).isDisplayed();
        Assert.assertTrue("Order success message is not visible!", isDisplayed);
    }

    public void clickDeleteAccount() {
        wait.until(ExpectedConditions.elementToBeClickable(by("deleteAccountBtn"))).click();
    }
}
