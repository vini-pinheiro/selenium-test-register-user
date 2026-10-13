# Automação de Testes End-to-End (E2E) - Automation Exercise

Projeto acadêmico de automação de testes End-to-End (E2E) com **Java 17**, **Selenium WebDriver 4** e **JUnit 5**, aplicando os padrões de arquitetura **Page Object Model (POM)** e **Component Object Model (COM)**, fundamentado nas técnicas formais de **Particionamento de Classes de Equivalência (PCE)** e **Análise de Valor Limite (AVL)**.

Aplicação alvo: [Automation Exercise](https://automationexercise.com/)

---

## 1. Escopo dos Testes

O escopo do projeto cobre integralmente os seguintes cenários oficiais da plataforma:

### Test Case 1: Register User (Fluxo Positivo Completo)
1. Iniciar o navegador.
2. Navegar para a URL `http://automationexercise.com`.
3. Validar se a página inicial está visível com sucesso.
4. Clicar no botão 'Signup / Login'.
5. Validar se o texto 'New User Signup!' está visível.
6. Preencher os campos Name e Email Address.
7. Clicar no botão 'Signup'.
8. Validar se o título 'ENTER ACCOUNT INFORMATION' está visível.
9. Preencher os dados: Title, Name, Email, Password e Date of birth.
10. Marcar a opção 'Sign up for our newsletter!'.
11. Marcar a opção 'Receive special offers from our partners!'.
12. Preencher os dados: First name, Last name, Company, Address, Address2, Country, State, City, Zipcode e Mobile Number.
13. Clicar no botão 'Create Account'.
14. Validar se a mensagem 'ACCOUNT CREATED!' está visível.
15. Clicar no botão 'Continue'.
16. Validar se o indicador 'Logged in as username' está visível no topo da página.
17. Clicar no botão 'Delete Account'.
18. Validar se a mensagem 'ACCOUNT DELETED!' está visível e clicar no botão 'Continue'.

### Test Case 3: Login User with incorrect email and password (Fluxo Negativo)
1. Iniciar o navegador.
2. Navegar para a URL `http://automationexercise.com`.
3. Validar se a página inicial está visível com sucesso.
4. Clicar no botão 'Signup / Login'.
5. Validar se o texto 'Login to your account' está visível.
6. Preencher com email e senha incorretos.
7. Clicar no botão 'login'.
8. Validar se a mensagem de erro 'Your email or password is incorrect!' está visível.

---

## 2. Design de Testes e Engenharia de Dados (PCE e AVL)

O design dos dados de teste baseou-se nos princípios de caixa-preta de Engenharia de Software:
* **Particionamento de Classes de Equivalência (PCE):** Identificação de subconjuntos do domínio de entrada em que o sistema trata todos os dados de forma equivalente (classes válidas e inválidas).
* **Análise de Valor Limite (AVL):** Verificação das fronteiras extremas das partições de dados válidas e inválidas, local crítico onde ocorre a maior incidência de falhas lógicas e de validação.

### Matriz de Técnicas de Teste

| ID | Funcionalidade / Campo | Técnica Aplicada & Justificativa | Dado de Entrada | Classe / Fronteira | Resultado Esperado |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **ENT-01** | **Login**<br>`Email` (TC3) | **PCE (Classe Inválida de Credencial)**<br>Valida o comportamento de autenticação com formato sintático aceito, mas entidade inexistente na base de dados. | `usuario_inexistente_<ts>@exemplo.com` | Partição Inválida (Email não cadastrado) | Exibição da mensagem de erro: *"Your email or password is incorrect!"*. |
| **ENT-02** | **Login**<br>`Password` (TC3) | **PCE (Classe Inválida de Credencial)**<br>Valida a rejeição de acesso quando a senha não coincide com o registro cadastrado. | `SenhaIncorreta#999` | Partição Inválida (Senha incorreta) | O login é bloqueado; exibe *"Your email or password is incorrect!"*. |
| **ENT-03** | **Signup Inicial**<br>`Email Address` (TC1) | **PCE (Classe Válida Dinâmica)**<br>Exige endereço de email com sintaxe RFC válida e unicidade absoluta para evitar falso-positivo por colisão de chave única. | `qa_user_<ts>_<uuid>@teste.com` | Partição Válida (Sintaxe correta e único) | Submissão aceita; redireciona para a tela *"ENTER ACCOUNT INFORMATION"*. |
| **ENT-04** | **Signup Inicial**<br>`Name` (TC1) | **AVL (Fronteira Mínima)**<br>Testa a fronteira inferior de caracteres válidos permitidos para o nome de exibição ($L=1$). | `A` | Limite Inferior Válido ($L=1$) | Nome aceito e replicado na etapa cadastral seguinte. |
| **ENT-05** | **Account Info**<br>`Date of Birth - Day` (TC1) | **AVL (Fronteiras de Domínio Calendário)**<br>O seletor possui intervalo discreto $[1, 31]$. O valor 1 é a fronteira inicial permitida no componente dropdown. | Dia `1` (mínimo válido) | Limite Inferior Válido ($D=1$) | Dropdown selecionado com sucesso e submissão processada. |
| **ENT-06** *(Extra)* | **Account Info**<br>`Mobile Number` (TC1) | **PCE (Classe Válida de Telefone)**<br>Verifica aceitação de números telefônicos com prefixo internacional e dígitos sem máscara rígida. | `+5511987654321` | Partição Válida (Numérico internacional) | Cadastro concluído com sucesso (*"ACCOUNT CREATED!"*). |

---

## 3. Arquitetura da Solução

O projeto adota uma arquitetura em camadas focada em modularidade, alta coesão e baixo acoplamento:

```text
automation-exercise/
├── pom.xml                                  <- Gerenciador de dependências e plugins Maven
├── README.md                                <- Documentação técnica e acadêmica
└── src/
    └── test/
        └── java/
            └── br/com/vinipinheiro/
                ├── components/              <- Component Object Model (COM)
                │   ├── BaseComponent.java   <- Superclasse com busca dinâmica de root
                │   ├── Navbar.java          <- Barra de navegação global
                │   ├── LoginForm.java       <- Bloco "Login to your account"
                │   └── SignupForm.java      <- Bloco "New User Signup!"
                ├── pages/                   <- Page Object Model (POM)
                │   ├── BasePage.java        <- Superclasse com esperas explícitas e mitigação de UI
                │   ├── HomePage.java        <- Página inicial
                │   ├── LoginPage.java       <- Página de autenticação que agrega formulários
                │   ├── SignupPage.java      <- Página de dados cadastrais detalhados
                │   ├── AccountCreatedPage.java <- Confirmação de conta criada
                │   └── DeleteAccountPage.java  <- Confirmação de conta deletada
                ├── tests/                   <- Scripts de teste (JUnit 5)
                │   ├── BaseTest.java        <- Setup/teardown de ciclo de vida do WebDriver
                │   ├── RegisterUserTest.java <- TC1: Passos 1 a 18
                │   └── LoginUserTest.java    <- TC3: Passos 1 a 8
                └── utils/                   <- Utilitários e infraestrutura
                    ├── DriverFactory.java   <- Configuração resiliente do Chrome
                    └── DataGenerator.java   <- Geração dinâmica de emails e dados únicos
```

### Padrões de Projeto Aplicados
* **Page Object Model (POM):** Cada página da aplicação web é representada por uma classe Java responsável por fornecer métodos semânticos que refletem os objetivos do usuário.
* **Component Object Model (COM):** Subdivisões e componentes visuais reutilizáveis (`Navbar`, `LoginForm`, `SignupForm`) são isolados em classes de componentes, encapsulando seus seletores e evitando duplicidade.
* **Dynamic Root Resolution:** Os componentes utilizam resolução dinâmica de localizadores para suas raízes (`rootLocator`), evitando exceções de elementos desatualizados (`StaleElementReferenceException`) quando a página sofre recarregamento.

---

## 4. Mitigação de Riscos de UI e Resiliência

Durante a análise arquitetural do site `automationexercise.com`, foram mapeados riscos críticos de interface e implementadas contramedidas técnicas no código:

1. **Interferência de Anúncios (Google Ads / Vignettes):**
   * O site carrega scripts do Google AdSense que geram iframes sobrepostos e redirecionamentos para `#google_vignette`.
   * **Mitigação:** Configuração de `ChromeOptions` para bloqueio de popups e notificações; método `dismissAdsIfPresent()` na `BasePage` que remove elementos invasivos via injeção JavaScript; e tratamento automático de redirecionamento caso `#google_vignette` ocorra.
2. **Cliques Interceptados (`ElementClickInterceptedException`):**
   * Elementos cobertos por banners flutuantes recebem scroll automático via `scrollIntoView({block: 'center'})` antes da interação, com fallback via `JavascriptExecutor` caso o clique nativo seja interceptado.
3. **Flakiness e Assincronia de Rede:**
   * Banimento de pausas estáticas (`Thread.sleep`). Toda interação é governada por **Esperas Explícitas (`WebDriverWait`)** com condições de prontidão (`visibilityOfElementLocated` e `elementToBeClickable`).
4. **Colisão de Dados de Teste:**
   * Utilização da classe [`DataGenerator`](src/test/java/br/com/vinipinheiro/utils/DataGenerator.java) para gerar emails únicos por execução com base em `timestamp` e identificadores pseudoaleatórios, viabilizando execuções repetidas do TC1 sem falhas por duplicidade cadastral.

---

## 5. Instruções de Execução

### Pré-requisitos
* **Java Development Kit (JDK) 17+**
* **Apache Maven 3.8+**
* **Google Chrome** (o ChromeDriver é gerenciado automaticamente pelo Selenium Manager integrado ao Selenium 4)

### Executando os Testes via Terminal

* **Executar toda a suíte de testes (Modo Padrão):**
  ```bash
  mvn test
  ```

* **Executar toda a suíte em Modo Headless (ideal para CI/CD):**
  ```bash
  mvn test -Dheadless=true
  ```

* **Executar apenas o Test Case 1 (Register User):**
  ```bash
  mvn test -Dtest=RegisterUserTest -Dheadless=true
  ```

* **Executar apenas o Test Case 3 (Login com Credenciais Incorretas):**
  ```bash
  mvn test -Dtest=LoginUserTest -Dheadless=true
  ```

### Relatórios de Execução
Após a execução, os relatórios detalhados do JUnit 5 / Surefire são gerados em:
* `target/surefire-reports/`