package com.example.starBank.repositories;

import com.example.starBank.model.RuleRequirements;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author Yuri-73
 */
@Repository
public class RecommendationsRepository {
    private final JdbcTemplate jdbcTemplate;
    private final String debit = "DEBIT";
    private final String invest = "INVEST";
    private final String saving = "SAVING";
    private final String deposit = "DEPOSIT";
    private final String withdraw = "WITHDRAW";
    private final String credit = "CREDIT";


    public RecommendationsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Метод получения значения amount из одной транзакции, полученной случайным образом
     * @param id id клиента банка
     * @return Возвращает 4-значное число amount или 0
     */
    public int getRandomTransactionAmount(UUID id){
        Integer result = jdbcTemplate.queryForObject(
                "SELECT amount FROM transactions t WHERE t.user_id = ? LIMIT 1",
                Integer.class,
                id);
        return result != null ? result : 0;
    }

    /**
     * Метод получения общего количества продуктов типа DEBIT для выбранного клиента
     * @param id id клиента банка
     * @return Возвращает общее количество продуктов типа DEBIT (снижено до одного, что удовлетворяет условию)
     */
    public Integer getDebitCount(UUID id) {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM transactions t INNER JOIN products p ON t.product_id = p.id WHERE t.user_id = ? AND p.type = ? AND t.type = ? LIMIT 1",
                Integer.class, id, debit, deposit);
    }

    /**
     * Метод получения общего количества продуктов типа INVEST для выбранного клиента
     * @param id id клиента банка
     * @return Возвращает хотя бы 1 продукт типа INVEST
     */
    public Integer getInvestCount(UUID id) {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM transactions t INNER JOIN products p ON t.product_id = p.id WHERE t.user_id = ? AND p.type = ? LIMIT 1",
                Integer.class, id, invest);
    }

    /**
     * Метод получения общей суммы пополнений по продуктам типа SAVING для выбранного клиента
     * @param id id клиента банка
     * @return Возвращает общую сумму пополнений по продуктам типа SAVING
     */
    public Integer getSavingAmount(UUID id) {
        Integer savingAmount = jdbcTemplate.queryForObject(
                "SELECT SUM(t.amount) FROM transactions t INNER JOIN products p ON t.product_id = p.id WHERE t.user_id = ? AND p.type = ? AND t.type = ?",
                Integer.class, id, saving, deposit);
        if (savingAmount == null) {
            savingAmount = 0;
        }
        return savingAmount;
    }

    /**
     * Метод получения общей суммы пополнений по продуктам типа DEBIT для выбранного клиента
     * @param id id клиента банка
     * @return Возвращает общую сумму пополнений для продуктов типа DEBIT
     */
    public Integer getDebitDepositAmount(UUID id) {
        Integer depitDepositAmount = jdbcTemplate.queryForObject(
                "SELECT SUM(t.amount) FROM transactions t INNER JOIN products p ON t.product_id = p.id WHERE t.user_id = ? AND p.type = ? AND t.type = ?",
                Integer.class, id, debit, deposit);
        if (depitDepositAmount == null) {
            depitDepositAmount = 0;
        }
        return depitDepositAmount;
    }

    /**
     * Метод получения общей суммы трат по продуктам типа DEBIT для выбранного клиента
     * @param id id клиента банка
     * @return Возвращает общую сумму трат по продуктам типа DEBIT
     */
    public Integer getDebitWithdrawAmount(UUID id) {
        Integer depitWithdrawAmount = jdbcTemplate.queryForObject(
                "SELECT SUM(t.amount) FROM transactions t INNER JOIN products p ON t.product_id = p.id WHERE t.user_id = ? AND p.type = ? AND t.type = ?",
                Integer.class, id, debit, withdraw);
        if (depitWithdrawAmount == null) {
            depitWithdrawAmount = 0;
        }
        return depitWithdrawAmount;
    }

    /**
     * Метод получения общего количества продуктов типа CREDIT для выбранного клиента
     * @param id id клиента банка
     * @return Возвращает общее количество продуктов типа CREDIT (снижено до одного, что удовлетворяет условию)
     */
    public Integer getCreditCount(UUID id) {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM transactions t INNER JOIN products p ON t.product_id = p.id WHERE t.user_id = ? AND p.type = ? AND t.type = ? LIMIT 1",
                Integer.class, id, debit, credit);
    }

    /**
     * Метод получения определения минимум одного продукта
     * @param id id клиента банка
     * @return Возвращает true/false
     */
    public Boolean getUserOfResult(UUID id, RuleRequirements rule) {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) >=1  FROM transactions t INNER JOIN products p ON t.product_id = p.id WHERE t.user_id = ? AND p.type = ? LIMIT 1",
                Boolean.class, id, rule.getArguments());
    }

    /**
     * Метод получения не менее 5 строк продукта
     * @param id id клиента банка
     * @return Возвращает true/false
     */
    public Boolean getActiveUserOfResult(UUID id, RuleRequirements rule) {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) >=5  FROM transactions t INNER JOIN products p ON t.product_id = p.id WHERE t.user_id = ? AND p.type = ? LIMIT 1",
                Boolean.class, id, rule.getArguments());
    }

    /**
     * Метод определения прохождения заданной суммы определённой транзакции
     * @param id id клиента банка
     * @return Возвращает true/false
     */
    public Boolean getTransactionSumCompareResult(UUID id, RuleRequirements rule) {
        String[] arguments = rule.getArguments().split(",");
        System.out.println("arguments " +arguments[0] +arguments[1]+arguments[2]+arguments[3]);

        Boolean answer = jdbcTemplate.queryForObject(
                "SELECT SUM(t.AMOUNT)" +arguments[2]+ "? FROM transactions t INNER JOIN products p ON t.product_id = p.id WHERE t.user_id=? AND p.type=? AND t.type=?",
                Boolean.class, arguments[3], id, arguments[0], arguments[1]);
        if (answer == null) {
            return false;
        }
        return answer;
    }

    /**
     * Метод определения прохождения сравниваемых сумм определённых транзакций
     * @param id id клиента банка
     * @return Возвращает true/false
     */
    public Boolean getTransactionSumCompareDepositWithDrawResult(UUID id, RuleRequirements rule) {
        String[] arguments = rule.getArguments().split(",");
        Boolean answer = jdbcTemplate.queryForObject(
                "SELECT (SELECT SUM(t.AMOUNT) FROM TRANSACTIONS t INNER JOIN PRODUCTS p ON t.product_id = p.id WHERE" +
                        " t.user_id = ? AND p.type = ? AND t.TYPE = 'DEPOSIT')" + arguments[1] +
                        "(SELECT SUM(t.AMOUNT) FROM TRANSACTIONS t INNER JOIN PRODUCTS p ON t.product_id = p.id " +
                        "WHERE t.user_id = ? AND p.type = ? AND t.TYPE = 'WITHDRAW');",
                Boolean.class,
                id, arguments[0], id, arguments[0]);
        if (answer == null) {
            return false;
        }
        return answer;
    }

    /** Метод определения Id клиента по его имени в базе USERS
     * @param username имя клиента
     * @return Возвращает true/false
     */
    public Boolean getBooleanUserIdByUsername(String username) {
        Boolean answer = jdbcTemplate.queryForObject("SELECT COUNT(*)=1 FROM users " + "WHERE users.username = ?", Boolean.class, username);
        if (answer == null) {
            return false;
        }
        return answer;
    }

    /** Метод получения Id клиента по его имени в базе USERS
     * @param username имя клиента
     * @return Возвращает id клиента
     */
    public UUID getUserIdByUsername(String username) {
        return jdbcTemplate.queryForObject("SELECT users.id FROM users WHERE users.username = ?", UUID.class, username);
    }



}


