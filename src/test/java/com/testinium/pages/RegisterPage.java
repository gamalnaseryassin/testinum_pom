package com.testinium.pages;

import com.testinium.steps.BaseSteps;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class RegisterPage extends BaseSteps {

    public boolean isHomePageVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by("homeHtml"))).isDisplayed();
    }

    public void clickSignupLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(by("signupLoginBtn"))).click();
    }

    public boolean isNewUserSignupVisible() {
        return driver.getPageSource().contains("New User Signup!");
    }

    public void enterName(String name) {
        WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(by("nameField")));
        nameField.clear();
        nameField.sendKeys(name);
    }

    public void enterEmail(String email) {
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(by("emailField")));
        emailField.clear();
        emailField.sendKeys(email);
    }

    public void clickSignupButton() {
        wait.until(ExpectedConditions.elementToBeClickable(by("signupBtn"))).click();
    }

    public boolean isEnterAccountInfoVisible() {
        return driver.getPageSource().contains("Enter Account Information");
    }

    public void selectTitleMr() {
        wait.until(ExpectedConditions.elementToBeClickable(by("titleMr"))).click();
    }

    public void enterPassword(String password) {
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(by("password")));
        passwordField.sendKeys(password);
    }

    public void selectDateOfBirth(String day, String month, String year) {
        new Select(wait.until(ExpectedConditions.elementToBeClickable(by("days")))).selectByValue(day);
        new Select(wait.until(ExpectedConditions.elementToBeClickable(by("months")))).selectByValue(month);
        new Select(wait.until(ExpectedConditions.elementToBeClickable(by("years")))).selectByValue(year);
    }

    public void selectNewsletterCheckbox() {
        wait.until(ExpectedConditions.elementToBeClickable(by("newsletterCheckbox"))).click();
    }

    public void selectOffersCheckbox() {
        wait.until(ExpectedConditions.elementToBeClickable(by("offersCheckbox"))).click();
    }

    public void fillAddressInfo(String firstName, String lastName, String company, String address1, String address2, String country, String state, String city, String zipcode, String mobile) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(by("firstName"))).sendKeys(firstName);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by("lastName"))).sendKeys(lastName);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by("company"))).sendKeys(company);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by("address1"))).sendKeys(address1);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by("address2"))).sendKeys(address2);
        new Select(wait.until(ExpectedConditions.elementToBeClickable(by("country")))).selectByVisibleText(country);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by("state"))).sendKeys(state);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by("city"))).sendKeys(city);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by("zipcode"))).sendKeys(zipcode);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by("mobileNumber"))).sendKeys(mobile);
    }

    public void clickCreateAccountButton() {
        wait.until(ExpectedConditions.elementToBeClickable(by("createAccountBtn"))).click();
    }

    public boolean isAccountCreatedVisible() {
        return driver.getPageSource().contains("Account Created!");
    }

    public void clickContinueButton() {
        wait.until(ExpectedConditions.elementToBeClickable(by("continueBtn"))).click();
    }

    public void clickDeleteAccountButton() {
        wait.until(ExpectedConditions.elementToBeClickable(by("deleteAccountBtn"))).click();
    }

    public boolean isAccountDeletedVisible() {
        return driver.getPageSource().contains("Account Deleted!");
    }
}
