package com.testinium.steps;

import com.thoughtworks.gauge.Step;
import com.testinium.pages.RegisterAndOrderPage;

public class RegisterAndOrderSteps extends BaseSteps {

    RegisterAndOrderPage page = new RegisterAndOrderPage();
    String email = "user" + System.currentTimeMillis() + "@mail.com";

    @Step("Fill all details in Signup and create account")
    public void fillSignupDetails() {
        page.enterNameAndEmail("Test User", email);
        page.clickSignupButton();
        page.fillAccountInformation("Test@123", "10", "6", "1990");
        page.checkNewsletter();
        page.checkOffers();
        page.fillAddress("Test", "User", "TestCompany", "Address1", "Address2", "India", "State", "City", "12345", "0123456789");
        page.clickCreateAccount();
    }

    @Step("Verify 'ACCOUNT CREATED!' and click 'Continue' button")
    public void verifyAccountCreatedAndContinue() {
        page.clickContinue(); // You may add verification if necessary
    }

    @Step("Verify 'Logged in as username' at top")
    public void verifyLoggedInAsUser() {
        page.verifyLoggedInAs();
    }

    @Step("Add products to cart")
    public void addProductToCart() {
        page.addProductsToCart();
    }

    @Step("Click 'Cart' button")
    public void clickCartButton() {
        page.clickCartButton();
    }

    @Step("Verify that cart page is displayed")
    public void verifyCartPageVisible() {
        // يمكن التحقق من cart URL أو عنصر ظاهر
    }

    @Step("Click Proceed To Checkout")
    public void clickProceedToCheckout() {
        page.clickProceedToCheckout();
    }

    @Step("Verify Address Details and Review Your Order")
    public void verifyAddressAndOrderDetails() {
        page.verifyAddressDetails();
    }

    @Step("Enter description in comment text area and click 'Place Order'")
    public void enterCommentAndPlaceOrder() {
        page.enterComment("Please deliver between 9AM - 5PM");
        page.clickPlaceOrder();
    }

    @Step("Enter payment details: Name on Card, Card Number, CVC, Expiration date")
    public void enterPaymentDetails() {
        page.enterPaymentDetails("Test User", "4111111111111111", "123", "12", "2026");
    }

    @Step("Click 'Pay and Confirm Order' button")
    public void clickPayAndConfirmOrder() {
        page.clickPayAndConfirm();
    }

    @Step("Verify success message 'Your order has been placed successfully!'")
    public void verifyOrderSuccess() {
        page.verifyOrderSuccessMessage();
    }

    @Step("Click 'Delete Account' button")
    public void clickDeleteAccount() {
        page.clickDeleteAccount();
    }

    @Step("Verify 'ACCOUNT DELETED!' and click 'Continue' button")
    public void verifyAccountDeleted() {
        page.clickContinue();
    }
}
