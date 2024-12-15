package com.example.starBank.services;

import com.example.starBank.exceptions.InvalidAmountOfArgumentsException;
import com.example.starBank.model.Recommendation;
import com.example.starBank.model.RecommendationWithRules;
import com.example.starBank.model.RuleRequirements;
import com.example.starBank.recommendation_rules.Invest500;
import com.example.starBank.recommendation_rules.SimpleCredit;
import com.example.starBank.recommendation_rules.TopSaving;
import com.example.starBank.repositories.RecommendationCounterRepository;
import com.example.starBank.repositories.RecommendationsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.example.starBank.constants.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class RecommendationServiceTest {
    @Mock
    private Invest500 product1;
    @Mock
    private TopSaving product2;
    @Mock
    private SimpleCredit product3;
    @Mock
    private RecommendationsRepository repository;

    @Mock
    private RecommendationCounterRepository counterRepository;

    private RecommendationService out;

    @BeforeEach
    public void setUp() {
        out = new RecommendationService(repository, product1, product2, product3,counterRepository);
    }

    @Test
    void getTest() {
        //test:
        Mockito.when(repository.getRandomTransactionAmount(any())).thenReturn(59818);
        //check:
        assertEquals(out.get(UUID.randomUUID()), 59818);
    }

    @Test
    void getRecommendationTest() {
        //input conditions
        Recommendation recommendation1 = INVEST500;
        Recommendation recommendation2 = SIMPLECREDIT;
        Recommendation recommendation3 = TOPSAVING;
        List<Recommendation> recommendations = List.of(recommendation1, recommendation2, recommendation3);

        Mockito.when(product1.getRecommendationByRule(any())).thenReturn(Optional.of(recommendation1));
        Mockito.when(product2.getRecommendationByRule(any())).thenReturn(Optional.of((recommendation2)));
        Mockito.when(product3.getRecommendationByRule(any())).thenReturn(Optional.of((recommendation3)));
        //test:
        List<Recommendation> recommendations1 = out.getRecommendation(UUID.randomUUID());
        //check:
        assertTrue(out.getRecommendation(UUID.randomUUID()).contains(recommendation1)); //Включение 1-го элемента
        assertTrue(out.getRecommendation(UUID.randomUUID()).contains(recommendation2));  //Включение 2-го элемента
        assertTrue(out.getRecommendation(UUID.randomUUID()).contains(recommendation3));  //Включение 3-го элемента

        org.assertj.core.api.Assertions.assertThat(recommendations).containsAll(recommendations1); //var1
        assertEquals(recommendations1, recommendations); //var2
        assertIterableEquals(recommendations1, recommendations); //var3
    }

    @Test
    void getItemRecommendationTest() {
        //input conditions
        Recommendation recommendation3 = INVEST500;
        List<Recommendation> recommendations = List.of(recommendation3);

        Mockito.when(product1.getRecommendationByRule(any())).thenReturn(Optional.ofNullable(null));
        Mockito.when(product2.getRecommendationByRule(any())).thenReturn(Optional.ofNullable(null));
        Mockito.when(product3.getRecommendationByRule(any())).thenReturn(Optional.of((recommendation3)));
        //test:
        List<Recommendation> recommendations1 = out.getRecommendation(UUID.randomUUID());
        //check:
        assertTrue(out.getRecommendation(UUID.randomUUID()).contains(recommendation3));  //Включение 3-го элемента
        assertEquals(recommendations1.size(), 1);
        assertEquals(recommendations1.get(0).getName(), "Invest 500");
        assertEquals(recommendations1.get(0).getText(), "Откройте свой путь к успеху с индивидуальным" +
                " инвестиционным счетом (ИИС) от нашего банка! Воспользуйтесь налоговыми льготами и начните" +
                " инвестировать с умом. Пополните счет до конца года и получите выгоду в виде вычета на взнос " +
                "в следующем налоговом периоде. Не упустите возможность разнообразить свой портфель, снизить риски" +
                " и следить за актуальными рыночными тенденциями. Откройте ИИС сегодня и станьте ближе к финансовой" +
                " независимости!");
    }

    @Test
    void getNullRecommendationTest() {
        //input conditions
        Mockito.when(product1.getRecommendationByRule(any())).thenReturn(Optional.ofNullable(null));
        Mockito.when(product2.getRecommendationByRule(any())).thenReturn(Optional.ofNullable(null));
        Mockito.when(product3.getRecommendationByRule(any())).thenReturn(Optional.ofNullable(null));
        //test:
        List<Recommendation> recommendations1 = out.getRecommendation(UUID.randomUUID());
        //check:
        assertEquals(recommendations1.size(), 1);
        assertEquals(recommendations1.get(0).getName(), "Рекомендуемых продуктов нет");
        assertEquals(recommendations1.get(0).getText(), "-");
    }

    @Test
    void getRecommendationDinamic_Result_Test() {
        //input conditions
        RecommendationWithRules recommendationWithRules = new RecommendationWithRules("name", "text", UUID.randomUUID());

        RuleRequirements ruleRequirements = new RuleRequirements();
        ruleRequirements.setId(1l);
        ruleRequirements.setArguments("argument");
        ruleRequirements.setNegate(true); //для true
        ruleRequirements.setQuery("USER_OF");

        RuleRequirements ruleRequirements2 = new RuleRequirements();
        ruleRequirements2.setId(2l);
        ruleRequirements2.setArguments("argument1,argument2");
        ruleRequirements2.setNegate(false);
        ruleRequirements2.setQuery("TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW");

        recommendationWithRules.setRuleRequirements(List.of(ruleRequirements, ruleRequirements2));
        List<RecommendationWithRules> recomList = List.of(recommendationWithRules);

        Mockito.when(repository.getUserOfResult(any(), any())).thenReturn(false);
        Mockito.when(repository.getTransactionSumCompareDepositWithDrawResult(any(), any())).thenReturn(true);
//        Mockito.when(counterRepository.findByRecommendationWithRulesIdAndIncrementCounter(any()));

        //test:
        List<Recommendation> recommendationsByRule = out.getRecommendation(UUID.randomUUID(), recomList);

        //check:
        assertEquals(recommendationsByRule.size(), 1);
        assertEquals(recommendationsByRule.get(0).getName(), "name");
        assertEquals(recommendationsByRule.get(0).getText(), "text");
    }

    @Test
    void getRecommendationDinamic_InvalidAmountOfArgumentsException_Test() {
        //input conditions
        RecommendationWithRules recommendationWithRules = new RecommendationWithRules("name", "text", UUID.randomUUID());

        RuleRequirements ruleRequirements = new RuleRequirements();
        ruleRequirements.setId(1l);
        ruleRequirements.setArguments("argument1, argument2"); //2 аргумента для имитации ошибки InvalidAmountOfArgumentsException
        ruleRequirements.setNegate(true);
        ruleRequirements.setQuery("USER_OF");

        RuleRequirements ruleRequirements2 = new RuleRequirements();
        ruleRequirements2.setId(2l);
        ruleRequirements2.setArguments("argument1,argument2");
        ruleRequirements2.setNegate(false);
        ruleRequirements2.setQuery("TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW");

        recommendationWithRules.setRuleRequirements(List.of(ruleRequirements, ruleRequirements2));
        List<RecommendationWithRules> recomList = List.of(recommendationWithRules);

        //test and check:
        Assertions.assertThrows(InvalidAmountOfArgumentsException.class, () -> out.getRecommendation(UUID.randomUUID(), recomList));
    }

    @Test
    void getRecommendationDinamic_EmptyList_Test() {
        //input conditions
        RecommendationWithRules recommendationWithRules = new RecommendationWithRules("name", "text", UUID.randomUUID());

        RuleRequirements ruleRequirements = new RuleRequirements();
        ruleRequirements.setId(1l);
        ruleRequirements.setArguments("argument");
        ruleRequirements.setNegate(false);  //для false
        ruleRequirements.setQuery("USER_OF");

        RuleRequirements ruleRequirements2 = new RuleRequirements();
        ruleRequirements2.setId(2l);
        ruleRequirements2.setArguments("argument2");
        ruleRequirements2.setNegate(true);
        ruleRequirements2.setQuery("TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW");
        System.out.println("ruleRequirements2 " +ruleRequirements2);

        recommendationWithRules.setRuleRequirements(List.of(ruleRequirements, ruleRequirements2));
        List<RecommendationWithRules> recomList = List.of(recommendationWithRules);

        //test:
        List<Recommendation> recommendationsByRule = out.getRecommendation(UUID.randomUUID(), recomList);

        //check:
        assertEquals(recommendationsByRule.size(), 1);
        assertEquals(recommendationsByRule.get(0).getName(), "Рекомендуемых продуктов нет");
        assertEquals(recommendationsByRule.get(0).getText(), "-");
    }
}

