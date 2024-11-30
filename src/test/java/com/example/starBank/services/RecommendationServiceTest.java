package com.example.starBank.services;

import com.example.starBank.model.Recommendation;
import com.example.starBank.recommendation_rules.Invest500;
import com.example.starBank.recommendation_rules.SimpleCredit;
import com.example.starBank.recommendation_rules.TopSaving;
import com.example.starBank.repositories.RecommendationsRepository;
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

    private RecommendationService out;

    @BeforeEach
    public void setUp() {
        out = new RecommendationService(repository, product1, product2, product3);
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
        assertEquals(recommendations1.get(0).getId(), 0);
        assertEquals(recommendations1.get(0).getName(), "Рекомендуемых продуктов нет");
        assertEquals(recommendations1.get(0).getText(), "-");
    }
}


//    List<Student> students = List.of(student1, student2, student3, student4);
//        Mockito.when(studentRepositoryMock.findByAgeBetween(anyInt(), anyInt())).thenReturn(students);
//                //check:
//                assertEquals(out.findByAgeBetween(2, 5), students);

//    public List<Recommendation> getRecommendation(UUID id) {
//        List<Recommendation> listOfRecommendation = new ArrayList<>();
//        Recommendation recommendation1 = product1.getRecommendationByRule(id).orElse(null);
//        Recommendation recommendation2 = product2.getRecommendationByRule(id).orElse(null);
//        Recommendation recommendation3 = product3.getRecommendationByRule(id).orElse(null);
//        if (recommendation1 != null) {
//            listOfRecommendation.add(recommendation1);
//        }
//        if (recommendation2 != null) {
//            listOfRecommendation.add(recommendation2);
//        }
//        if (recommendation3 != null) {
//            listOfRecommendation.add(recommendation3);
//        }
//        if (listOfRecommendation.size() == 0) {
//            listOfRecommendation.add(new Recommendation("Рекомендуемых продуктов нет", "-"));
//        }
//        return listOfRecommendation;
//    }
