package com.example.starBank.repositories;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class DinamicRuleRepository {
    private final JdbcTemplate jdbcTemplate;

    public DinamicRuleRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Запрос на правило Простой кредит #2 или Top Saving #3 (Сумма пополнений по всем продуктам типа DEBIT больше, чем сумма трат по всем продуктам типа DEBIT)
     */
    public Boolean getTransactionSumCompareDepositWithDrawResult(UUID id, String[] arg) {
        return jdbcTemplate.queryForObject(
                "SELECT (SELECT SUM(t.AMOUNT) FROM TRANSACTIONS t INNER JOIN PRODUCTS p ON t.product_id = p.id WHERE" +
                        " t.user_id = ? AND p.type = ? AND t.TYPE = 'DEPOSIT')" + arg[1] +
                        "(SELECT SUM(t.AMOUNT) FROM TRANSACTIONS t INNER JOIN PRODUCTS p ON t.product_id = p.id " +
                        "WHERE t.user_id = ? AND p.type = ? AND t.TYPE = 'WITHDRAW')",
                Boolean.class, id, arg[0], id, arg[0]);
    }

    /**
     * Запрос на правило Простой кредит #3 (Сумма трат по всем продуктам типа DEBIT больше, чем 100 000 ₽.)
     */
    public Boolean getTransactionSumCompareResult(UUID id, String[] arg) {
        return jdbcTemplate.queryForObject(
                "SELECT SUM(t.amount)" + arg[2] + "? FROM transactions t INNER JOIN products p ON t.product_id = p.id WHERE t.user_id = ? AND p.type = ? AND t.type = ?",
                Boolean.class, arg[3], id, arg[0], arg[1]);
    }

    /**
     * Запасной запрос
     */
    public Boolean getActiveUserOfResult(UUID id, String[] arg3) {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) >=5  FROM transactions t INNER JOIN products p ON t.product_id = p.id WHERE t.user_id = ? AND p.type = ? LIMIT 1",
                Boolean.class, id, arg3[0]);
    }

    /**
     * Запрос на правило Invest 500 #1 или Top Saving #1 (Пользователь использует как минимум 1 продукт типа DEBIT ())
     */
    public Boolean getInvest500Result(UUID id, String[] arg) {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM transactions t INNER JOIN products p ON t.product_id = p.id WHERE t.user_id = ? AND p.type = ? LIMIT 1",
                Boolean.class, id, arg[0]);
    }

    /**
     * Запрос на правило Invest 500 #3 (Сумма пополнений продуктов с типом SAVING больше 1000 ₽)
     */
    public Boolean getSavingAmountMore1000_Invest500_Result(UUID id, String[] arg) {
        return jdbcTemplate.queryForObject(
                "SELECT SUM(t.AMOUNT)" + arg[1] + "? FROM TRANSACTIONS t INNER JOIN PRODUCTS p ON t.PRODUCT_id = p.ID " +
                        "WHERE t.USER_ID=? AND p.\"TYPE\"= ? AND t.TYPE='DEPOSIT'",
                Boolean.class, arg[2], id, arg[0]);
    }

    /**
     * Запрос на правило Top Saving #2 (Сумма пополнений по всем продуктам типа DEBIT больше или равна 50 000 ₽
     * ИЛИ Сумма пополнений по всем продуктам типа SAVING больше или равна 50 000 ₽.
     */
    public Boolean getAmountDebitSavingCompare_TopSaving_Result(UUID id, String[] arg) {
        return jdbcTemplate.queryForObject(
                "SELECT (SUM(t.AMOUNT)" + arg[5] + "?) FROM TRANSACTIONS t INNER JOIN PRODUCTS p ON t.PRODUCT_id = p.ID\n" +
                        "WHERE t.USER_ID=? AND t.\"TYPE\"=? AND (p.TYPE =? OR p.TYPE =?)",
                Boolean.class, arg[3], id, arg[0]);
    }
}


