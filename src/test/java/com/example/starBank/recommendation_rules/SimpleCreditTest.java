package com.example.starBank.recommendation_rules;

import com.example.starBank.model.Recommendation;
import com.example.starBank.repositories.RecommendationsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.starBank.constants.Constants.SIMPLECREDIT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SimpleCreditTest {
    @Mock
    private RecommendationsRepository repository;
    @InjectMocks
    private SimpleCredit out;

    @Test
    void getRecommendationByRuleTest() {
        when(repository.getCreditCount(any())).thenReturn(0);
        when(repository.getDebitDepositAmount(any())).thenReturn(134000);
        when(repository.getDebitWithdrawAmount(any())).thenReturn(124000);

        Recommendation recommendation = out.getRecommendationByRule(any()).orElse(null);
        assertEquals(recommendation, SIMPLECREDIT);
    }

    @Test
    void getNullRecommendationByRuleTest() {
        when(repository.getCreditCount(any())).thenReturn(0);
        when(repository.getDebitDepositAmount(any())).thenReturn(134000);
        when(repository.getDebitWithdrawAmount(any())).thenReturn(64000);

        Recommendation recommendation = out.getRecommendationByRule(any()).orElse(null);
        assertNull(recommendation);
    }
}
