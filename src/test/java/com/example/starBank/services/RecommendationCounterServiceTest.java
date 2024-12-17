package com.example.starBank.services;

import com.example.starBank.model.CounterForShow;
import com.example.starBank.model.RecommendationCounter;
import com.example.starBank.model.RecommendationWithRules;
import com.example.starBank.repositories.RecommendationCounterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.example.starBank.constants.RecommendationRuleConstants.SIMPLECREDIT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RecommendationCounterServiceTest {
    @Mock
    RecommendationCounterRepository counterRepository;

    @InjectMocks
    RecommendationCounterService out;

    private static RecommendationWithRules recommendationWithRules = SIMPLECREDIT;
    private static RecommendationCounter recommendationCounter = new RecommendationCounter(recommendationWithRules);
    private static CounterForShow expetedCounter = new CounterForShow(1L, 0L);

    @BeforeEach
    void setup() throws Exception {
        recommendationCounter.setId(1L);
        recommendationWithRules.setId(1L);


    }

    @Test
    void getCounterListTest() {
        when(counterRepository.findAll()).thenReturn(List.of(recommendationCounter));

        assertEquals(List.of(expetedCounter), out.getCounterList());
    }

}