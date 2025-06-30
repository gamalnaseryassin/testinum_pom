package com.testinium.pages;

import com.testinium.base.BaseTest;
import com.testinium.steps.BaseSteps;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends BaseSteps {

    public boolean isLoginHeaderVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by("loginPageHeader"))).isDisplayed();
    }

    public boolean isLoggedInAsVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by("loggedInAsLabel"))).isDisplayed();
    }

    public void enterEmail(String email) {
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(by("loginPageEmailInput")));
        emailField.clear();
        emailField.sendKeys(email);
    }

    public void enterPassword(String password) {
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(by("loginPagePasswordInput")));
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(by("loginPageLoginButton"))).click();
    }

    public boolean isAccountDeletedVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by("loginPageDeleteSuccess"))).isDisplayed();
    }

    public String getLoginErrorMessage() {
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(by("loginErrorMessageLabel")));
        return error.getText().trim();
    }

//    LoginPage.loginAs("admin@mail.com", "password");
    public void loginAs(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLogin();
    }
}
