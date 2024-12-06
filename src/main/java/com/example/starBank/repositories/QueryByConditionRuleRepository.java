package com.example.starBank.repositories;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class QueryByConditionRuleRepository {
    private final JdbcTemplate jdbcTemplate;

    public QueryByConditionRuleRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Boolean getTransactionSumCompareDepositWithDrawResult(UUID id, String[] arg) {

        return jdbcTemplate.queryForObject(
                "SELECT (SELECT SUM(t.AMOUNT) FROM TRANSACTIONS t INNER JOIN PRODUCTS p ON t.product_id = p.id WHERE" +
                        " t.user_id = ? AND p.type = ? AND t.TYPE = 'DEPOSIT')" + arg[1] +
                        "(SELECT SUM(t.AMOUNT) FROM TRANSACTIONS t INNER JOIN PRODUCTS p ON t.product_id = p.id " +
                        "WHERE t.user_id = ? AND p.type = ? AND t.TYPE = 'WITHDRAW')",
                Boolean.class, id, arg[0], id, arg[0]);
    }

    public Boolean getTransactionSumCompareResult(UUID id, String[] arg) {
        return jdbcTemplate.queryForObject(
                "SELECT SUM(t.amount)" + arg[2] + "? FROM transactions t INNER JOIN products p ON t.product_id = p.id WHERE t.user_id = ? AND p.type = ? AND t.type = ?",
                Boolean.class, arg[3], id, arg[0], arg[1]);
    }


    public Boolean getActiveUserOfResult(UUID id, String[] arg3) {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) >=5  FROM transactions t INNER JOIN products p ON t.product_id = p.id WHERE t.user_id = ? AND p.type = ? LIMIT 1",
                Boolean.class, id, arg3[0]);
    }

    public Boolean getInvest500Result(UUID id, String[] arg) {
        return jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM transactions t INNER JOIN products p ON t.product_id = p.id WHERE t.user_id = ? AND p.type = ? LIMIT 1",
                Boolean.class, id, arg[0]);
    }

}


//--Пользователь использует как минимум 1 продукт типа DEBIT (Invest 500 #1)
//        SELECT COUNT(*) FROM TRANSACTIONS t INNER JOIN PRODUCTS p ON t.PRODUCT_id = p.ID WHERE t.USER_ID='22490bf7-8d33-444e-afc3-fce8b4c2cbde' AND p.TYPE = 'DEBIT' LIMIT 1