package com.testinium.steps;

import com.testinium.pages.LoginPage;
import com.thoughtworks.gauge.Step;

public class LoginSteps extends BaseSteps {

    private final LoginPage loginPage;

    public LoginSteps() {
        this.loginPage = new LoginPage();
    }

    @Step("Verify 'Login to your account' is visible")
    public void verifyLoginToYourAccountVisible() {
        if (!loginPage.isLoginHeaderVisible()) {
            throw new AssertionError("'Login to your account' is not visible");
        }
    }

    @Step("Verify that 'Logged in as username' is visible")
    public void verifyLoggedInAsUsername() {
        if (!loginPage.isLoggedInAsVisible()) {
            throw new AssertionError("'Logged in as username' is not visible");
        }
    }

    @Step("Enter correct email address and password")
    public void enterCorrectEmailAndPassword() {
        loginPage.enterEmail("gamal@invita.com");
        loginPage.enterPassword("@6egksGyE5cPx");
    }

    @Step("Click 'login' button")
    public void clickLoginButton() {
        loginPage.clickLogin();
    }

    @Step("Verify that 'ACCOUNT DELETED!' is visible")
    public void verifyAccountDeletedVisible() {
        if (!loginPage.isAccountDeletedVisible()) {
            throw new AssertionError("'ACCOUNT DELETED!' is not visible");
        }
    }

    @Step("Enter incorrect email address and password")
    public void enterInvalidCredentials() {
        loginPage.enterEmail("invalid@example.com");
        loginPage.enterPassword("wrongpassword");
    }

    @Step("Verify error 'Your email or password is incorrect!' is visible")
    public void verifyInvalidLoginError() {
        String actual = loginPage.getLoginErrorMessage();
        String expected = "Your email or password is incorrect!";

        if (!actual.equalsIgnoreCase(expected)) {
            throw new AssertionError("Expected: '" + expected + "', but got: '" + actual + "'");
        }
    }
}
