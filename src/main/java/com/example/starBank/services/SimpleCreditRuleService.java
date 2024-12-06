package com.example.starBank.services;

import com.example.starBank.model.RecommendationWithRules;
import com.example.starBank.model.RuleRequirements;
import com.example.starBank.repositories.QueryByConditionRuleRepository;
import com.example.starBank.repositories.RecommendationsRepository;
import com.example.starBank.repositories.RecommendationsRuleRepository;
import com.example.starBank.repositories.RuleRequirementsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SimpleCreditRuleService {

    private QueryByConditionRuleRepository queryByConditionRuleRepository;
    private RuleRequirementsRepository ruleRequirementsRepository;
    private RecommendationsRuleRepository recommendationsRuleRepository;

    public SimpleCreditRuleService(QueryByConditionRuleRepository queryByConditionRuleRepository,
                                   RecommendationsRuleRepository recommendationsRuleRepository,
                                   RuleRequirementsRepository ruleRequirementsRepository) {
        this.queryByConditionRuleRepository = queryByConditionRuleRepository;
        this.recommendationsRuleRepository = recommendationsRuleRepository;
        this.ruleRequirementsRepository = ruleRequirementsRepository;
    }

    public boolean recommendationSimpleCredit(UUID id, RecommendationWithRules recom) {
        Optional<RuleRequirements> userOf = ruleRequirementsRepository.findByQuery("USER_OF");
        System.out.println("userOf: " + userOf);
        if (userOf.get().isNegate()) {
            Optional<RuleRequirements> transactionSum = ruleRequirementsRepository.findByQuery("TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW");
            String[] arg = transactionSum.get().getArguments().split(", ");
            Boolean queryTransWithdrad = queryByConditionRuleRepository.getTransactionSumCompareDepositWithDrawResult(id, arg);

            if (queryTransWithdrad && !transactionSum.get().isNegate()) {
                Optional<RuleRequirements> transactionSum2 = ruleRequirementsRepository.findByQuery("TRANSACTION_SUM_COMPARE");
                String[] arg2 = transactionSum2.get().getArguments().split(", ");
                Boolean queryTrans = queryByConditionRuleRepository.getTransactionSumCompareResult(id, arg2);
                if (queryTrans && !transactionSum2.get().isNegate()) {
                    return true;
                }
            }
        }
        return false;
    }
}
