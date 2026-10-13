package br.com.vinipinheiro.tests;

import br.com.vinipinheiro.pages.HomePage;
import br.com.vinipinheiro.pages.LoginPage;
import br.com.vinipinheiro.utils.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test Case 3: Login User with incorrect email and password
 * Valida a rejeição de credenciais inválidas (PCE) e a exibição da mensagem de erro correspondente.
 */
public class LoginUserTest extends BaseTest {

    @Test
    @DisplayName("Test Case 3: Login User with incorrect email and password")
    public void testLoginWithIncorrectCredentials() {
        // 1. Launch browser & 2. Navigate to url 'http://automationexercise.com' (feito no BaseTest @BeforeEach)
        HomePage homePage = new HomePage(driver);

        // 3. Verify that home page is visible successfully
        assertTrue(homePage.isHomePageVisible(), "A página inicial deveria estar visível com sucesso");

        // 4. Click on 'Signup / Login' button
        LoginPage loginPage = homePage.clickSignupLogin();

        // 5. Verify 'Login to your account' is visible
        assertTrue(loginPage.getLoginForm().isTitleVisible(), "O cabeçalho 'Login to your account' deveria estar visível");

        // 6. Enter incorrect email address and password
        // Dados de entrada modelados via PCE (Classes Inválidas para autenticação)
        String incorrectEmail = DataGenerator.getNonExistentEmail();
        String incorrectPassword = "SenhaIncorreta#999";
        loginPage.getLoginForm().fillEmail(incorrectEmail);
        loginPage.getLoginForm().fillPassword(incorrectPassword);

        // 7. Click 'login' button
        loginPage.getLoginForm().clickLogin();

        // 8. Verify error 'Your email or password is incorrect!' is visible
        assertTrue(loginPage.getLoginForm().isErrorMessageVisible(), "A mensagem de erro de credenciais incorretas deveria estar visível");
        assertEquals("Your email or password is incorrect!", loginPage.getLoginForm().getErrorMessage().trim(), "O texto da mensagem de erro deve ser idêntico ao esperado");
    }
}

