package com.example.starBank.recommendation_rules;

import com.example.starBank.model.Recommendation;
import com.example.starBank.repositories.RecommendationsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.starBank.constants.Constants.INVEST500;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class Invest500Test {

    @Mock
    private RecommendationsRepository repository;
    @InjectMocks
    private Invest500 out;


    @Test
    void getRecommendationByRuleTest() {
        when(repository.getDebitCount(any())).thenReturn(1);
        when(repository.getInvestCount(any())).thenReturn(0);
        when(repository.getSavingAmount(any())).thenReturn(55000);

        Recommendation recommendation = out.getRecommendationByRule(any()).orElse(null);
        assertEquals(recommendation, INVEST500);
    }

    @Test
    void getNullRecommendationByRuleTest() {
        when(repository.getDebitCount(any())).thenReturn(0);
        when(repository.getInvestCount(any())).thenReturn(0);
        when(repository.getSavingAmount(any())).thenReturn(55000);

        Recommendation recommendation = out.getRecommendationByRule(any()).orElse(null);
        assertNull(recommendation);
    }


}
