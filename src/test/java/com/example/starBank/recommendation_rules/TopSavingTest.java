package com.example.starBank.recommendation_rules;

import com.example.starBank.model.Recommendation;
import com.example.starBank.repositories.RecommendationsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static com.example.starBank.constants.Constants.TOPSAVING;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TopSavingTest {

    @Mock
    private RecommendationsRepository repository;
    @InjectMocks
    private TopSaving out;

    @Test
    void getRecommendationByRuleTest() {
        when(repository.getDebitCount(any())).thenReturn(1);
        when(repository.getSavingAmount(any())).thenReturn(55000);
        when(repository.getDebitDepositAmount(any())).thenReturn(34000);
        when(repository.getDebitWithdrawAmount(any())).thenReturn(24000);

        Recommendation recommendation = out.getRecommendationByRule(any()).orElse(null);
        recommendation.setId(UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925"));
        assertEquals(recommendation, TOPSAVING);
    }

    @Test
    void getNullRecommendationByRuleTest() {
        when(repository.getDebitCount(any())).thenReturn(1);
        when(repository.getSavingAmount(any())).thenReturn(55000);
        when(repository.getDebitDepositAmount(any())).thenReturn(34000);
        when(repository.getDebitWithdrawAmount(any())).thenReturn(44000);

        Recommendation recommendation = out.getRecommendationByRule(any()).orElse(null);
        assertNull(recommendation);
    }


}
