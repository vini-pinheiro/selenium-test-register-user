package br.com.vinipinheiro.utils;

import java.util.UUID;

/**
 * Utilitário de geração de dados dinâmicos para testes,
 * prevenindo colisões de dados em execuções sucessivas.
 */
public class DataGenerator {

    /**
     * Gera um email único baseado em timestamp e UUID reduzido.
     */
    public static String generateUniqueEmail() {
        return "qa_user_" + System.currentTimeMillis() + "_" + UUID.randomUUID().toString().substring(0, 5) + "@teste.com";
    }

    /**
     * Gera um email com formato válido, porém garantidamente não cadastrado para testes negativos de login.
     */
    public static String getNonExistentEmail() {
        return "usuario_inexistente_" + System.currentTimeMillis() + "@exemplo.com";
    }
}

