package com.example.starBank.recommendation_rules;

import com.example.starBank.model.Recommendation;
import com.example.starBank.repositories.RecommendationsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static com.example.starBank.constants.Constants.INVEST500;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Chowo
 */
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
        recommendation.setId(UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"));
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
