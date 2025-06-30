package com.testinium.steps;

import com.thoughtworks.gauge.Step;
import com.testinium.pages.RegisterPage;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

public class RegisterSteps extends BaseSteps {
    RegisterPage registerPage = new RegisterPage();
    String email;

    @Step("Launch browser")
    public void launchBrowser() {
        driver.manage().window().maximize();
    }

    @Step("Navigate to url 'https://automationexercise.com'")
    public void navigateToURL() {
        driver.get("https://automationexercise.com");
    }

    @Step("Verify that home page is visible successfully")
    public void verifyHomePage() {
        if (!registerPage.isHomePageVisible()) {
            throw new AssertionError("Home page not visible");
        }
    }

    @Step("Click on 'Signup / Login' button")
    public void clickSignupLogin() {
        registerPage.clickSignupLogin();
    }

    @Step("Verify 'New User Signup!' is visible")
    public void verifyNewUserSignup() {
        if (!registerPage.isNewUserSignupVisible()) {
            throw new AssertionError("'New User Signup!' not found");
        }
    }

    @Step("Enter name and email address")
    public void enterNameAndEmail() {
        registerPage.enterName("Test User");
        email = "test" + System.currentTimeMillis() + "@mail.com";
        registerPage.enterEmail(email);
    }

    @Step("Click 'Signup' button")
    public void clickSignupButton() {
        registerPage.clickSignupButton();
    }

    @Step("Verify that 'ENTER ACCOUNT INFORMATION' is visible")
    public void verifyEnterAccountInfo() {
        if (!registerPage.isEnterAccountInfoVisible()) {
            throw new AssertionError("'Enter Account Information' not found");
        }
    }

    @Step("Fill details: Title, Name, Email, Password, Date of birth")
    public void fillAccountDetails() {
        registerPage.selectTitleMr();
        registerPage.enterPassword("Test@123");
        registerPage.selectDateOfBirth("10", "6", "1990");
    }

    @Step("Select checkbox 'Sign up for our newsletter!'")
    public void selectNewsletterCheckbox() {
        registerPage.selectNewsletterCheckbox();
    }

    @Step("Select checkbox 'Receive special offers from our partners!'")
    public void selectOffersCheckbox() {
        registerPage.selectOffersCheckbox();
    }

    @Step("Fill details: First name, Last name, Company, Address, Address2, Country, State, City, Zipcode, Mobile Number")
    public void fillAddressDetails() {
        registerPage.fillAddressInfo(
                "Test", "User", "TestCompany",
                "Test Address Line 1", "Test Address Line 2",
                "India", "StateTest", "CityTest",
                "12345", "0123456789"
        );
    }

    @Step("Click 'Create Account button'")
    public void clickCreateAccount() {
        registerPage.clickCreateAccountButton();
    }

    @Step("Verify that 'ACCOUNT CREATED!' is visible")
    public void verifyAccountCreated() {
        if (!registerPage.isAccountCreatedVisible()) {
            throw new AssertionError("'Account Created!' not found");
        }
    }

    @Step("Click 'Continue' button")
    public void clickContinue() {
        try {
            registerPage.clickContinueButton();
        } catch (Exception e) {
            new Actions(driver).sendKeys(Keys.ENTER).perform();
        }
    }

    @Step("Click 'Delete Account' button")
    public void clickDeleteAccount() {
        registerPage.clickDeleteAccountButton();
    }

    @Step("Verify that 'ACCOUNT DELETED!' is visible and click 'Continue' button")
    public void verifyAccountDeletedAndContinue() {
        if (!registerPage.isAccountDeletedVisible()) {
            throw new AssertionError("'Account Deleted!' not found");
        }
        registerPage.clickContinueButton();
    }
}
