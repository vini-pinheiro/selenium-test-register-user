package br.com.vinipinheiro.tests;

import br.com.vinipinheiro.pages.AccountCreatedPage;
import br.com.vinipinheiro.pages.DeleteAccountPage;
import br.com.vinipinheiro.pages.HomePage;
import br.com.vinipinheiro.pages.LoginPage;
import br.com.vinipinheiro.pages.SignupPage;
import br.com.vinipinheiro.utils.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test Case 1: Register User
 * Valida o fluxo completo de cadastro de um novo usuário, verificação de login ativo e exclusão de conta.
 */
public class RegisterUserTest extends BaseTest {

    @Test
    @DisplayName("Test Case 1: Register User - Fluxo completo com criação e deleção de conta")
    public void testRegisterUser() {
        // 1. Launch browser & 2. Navigate to url 'http://automationexercise.com' (feito no BaseTest @BeforeEach)
        HomePage homePage = new HomePage(driver);

        // 3. Verify that home page is visible successfully
        assertTrue(homePage.isHomePageVisible(), "A página inicial deveria estar visível com sucesso");

        // 4. Click on 'Signup / Login' button
        LoginPage loginPage = homePage.clickSignupLogin();

        // 5. Verify 'New User Signup!' is visible
        assertTrue(loginPage.getSignupForm().isTitleVisible(), "O cabeçalho 'New User Signup!' deveria estar visível");

        // 6. Enter name and email address
        // Dado de entrada aplicando AVL (comprimento mínimo de nome = 1 caractere) e PCE (email único válido)
        String userName = "A";
        String userEmail = DataGenerator.generateUniqueEmail();
        loginPage.getSignupForm().fillName(userName);
        loginPage.getSignupForm().fillEmail(userEmail);

        // 7. Click 'Signup' button
        SignupPage signupPage = loginPage.getSignupForm().clickSignup();

        // 8. Verify that 'ENTER ACCOUNT INFORMATION' is visible
        assertTrue(signupPage.isEnterAccountInformationVisible(), "O título 'ENTER ACCOUNT INFORMATION' deveria estar visível");

        // 9. Fill details: Title, Name, Email, Password, Date of birth
        // Dado de entrada aplicando AVL no dia (limite inferior = 1)
        signupPage.selectTitleMr()
                  .fillPassword("Password123!")
                  .selectDateOfBirth("1", "January", "2000");

        // 10. Select checkbox 'Sign up for our newsletter!'
        signupPage.checkNewsletter();

        // 11. Select checkbox 'Receive special offers from our partners!'
        signupPage.checkSpecialOffers();

        // 12. Fill details: First name, Last name, Company, Address, Address2, Country, State, City, Zipcode, Mobile Number
        signupPage.fillAddressDetails(
                "Carlos",
                "Silva",
                "QA Corp",
                "Rua das Flores, 123",
                "Apto 45",
                "United States",
                "California",
                "Los Angeles",
                "90001",
                "+5511987654321"
        );

        // 13. Click 'Create Account button'
        AccountCreatedPage accountCreatedPage = signupPage.clickCreateAccount();

        // 14. Verify that 'ACCOUNT CREATED!' is visible
        assertTrue(accountCreatedPage.isAccountCreatedVisible(), "O título 'ACCOUNT CREATED!' deveria estar visível");

        // 15. Click 'Continue' button
        homePage = accountCreatedPage.clickContinue();

        // 16. Verify that 'Logged in as username' is visible
        assertTrue(homePage.isLoggedInAs(userName), "Deveria exibir 'Logged in as " + userName + "' no topo da página");

        // 17. Click 'Delete Account' button
        DeleteAccountPage deleteAccountPage = homePage.clickDeleteAccount();

        // 18. Verify that 'ACCOUNT DELETED!' is visible and click 'Continue' button
        assertTrue(deleteAccountPage.isAccountDeletedVisible(), "O título 'ACCOUNT DELETED!' deveria estar visível");
        homePage = deleteAccountPage.clickContinue();
        assertTrue(homePage.isHomePageVisible(), "Deveria retornar para a HomePage após exclusão da conta");
    }
}
