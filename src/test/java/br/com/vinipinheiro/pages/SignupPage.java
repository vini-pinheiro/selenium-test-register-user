package br.com.vinipinheiro.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Página de cadastro detalhado (/signup - "ENTER ACCOUNT INFORMATION").
 */
public class SignupPage extends BasePage {
    private final By enterAccountInformationTitle = By.xpath("//h2[contains(., 'Enter Account Information') or contains(., 'ENTER ACCOUNT INFORMATION')]");
    private final By titleMrRadio = By.id("id_gender1");
    private final By titleMrsRadio = By.id("id_gender2");
    private final By nameInput = By.id("name");
    private final By passwordInput = By.id("password");
    private final By daysSelect = By.id("days");
    private final By monthsSelect = By.id("months");
    private final By yearsSelect = By.id("years");
    private final By newsletterCheckbox = By.id("newsletter");
    private final By optinCheckbox = By.id("optin");

    // Endereço e Dados Pessoais
    private final By firstNameInput = By.id("first_name");
    private final By lastNameInput = By.id("last_name");
    private final By companyInput = By.id("company");
    private final By address1Input = By.id("address1");
    private final By address2Input = By.id("address2");
    private final By countrySelect = By.id("country");
    private final By stateInput = By.id("state");
    private final By cityInput = By.id("city");
    private final By zipcodeInput = By.id("zipcode");
    private final By mobileNumberInput = By.id("mobile_number");

    private final By createAccountButton = By.cssSelector("button[data-qa='create-account']");

    public SignupPage(WebDriver driver) {
        super(driver);
    }

    public boolean isEnterAccountInformationVisible() {
        return isElementVisible(enterAccountInformationTitle);
    }

    public SignupPage selectTitleMr() {
        click(titleMrRadio);
        return this;
    }

    public SignupPage selectTitleMrs() {
        click(titleMrsRadio);
        return this;
    }

    public SignupPage fillPassword(String password) {
        type(passwordInput, password);
        return this;
    }

    public SignupPage selectDateOfBirth(String day, String month, String year) {
        selectDropdownByText(daysSelect, day);
        selectDropdownByText(monthsSelect, month);
        selectDropdownByText(yearsSelect, year);
        return this;
    }

    public SignupPage checkNewsletter() {
        click(newsletterCheckbox);
        return this;
    }

    public SignupPage checkSpecialOffers() {
        click(optinCheckbox);
        return this;
    }

    public SignupPage fillAddressDetails(String firstName, String lastName, String company,
                                         String address1, String address2, String country,
                                         String state, String city, String zipcode, String mobileNumber) {
        type(firstNameInput, firstName);
        type(lastNameInput, lastName);
        type(companyInput, company);
        type(address1Input, address1);
        type(address2Input, address2);
        selectDropdownByText(countrySelect, country);
        type(stateInput, state);
        type(cityInput, city);
        type(zipcodeInput, zipcode);
        type(mobileNumberInput, mobileNumber);
        return this;
    }

    public AccountCreatedPage clickCreateAccount() {
        click(createAccountButton);
        return new AccountCreatedPage(driver);
    }
}
