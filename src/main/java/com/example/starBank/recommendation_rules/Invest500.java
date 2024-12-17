package com.example.starBank.recommendation_rules;

import com.example.starBank.model.Recommendation;
import com.example.starBank.repositories.RecommendationsRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Chowo
 */
@Component
public class Invest500 implements RecommendationRuleSet{
    private final RecommendationsRepository repository;
    private final Recommendation recommendation;

    public Invest500(RecommendationsRepository repository) {
        recommendation = new Recommendation(UUID.randomUUID(),"Invest 500", "Откройте свой путь к успеху с индивидуальным" +
                " инвестиционным счетом (ИИС) от нашего банка! Воспользуйтесь налоговыми льготами и начните" +
                " инвестировать с умом. Пополните счет до конца года и получите выгоду в виде вычета на взнос " +
                "в следующем налоговом периоде. Не упустите возможность разнообразить свой портфель, снизить риски" +
                " и следить за актуальными рыночными тенденциями. Откройте ИИС сегодня и станьте ближе к финансовой" +
                " независимости!");

        this.repository = repository;
    }

    /**
     * Метод формирования рекомендации по условию полученных значений из БД Н2
     * @param id Идентификатор клиента банка
     * @return Возвращает штатную рекомендацию или пустое значение
     */
    @Override
    public Optional<Recommendation> getRecommendationByRule(UUID id) {
        int debitCount = repository.getDebitCount(id);
        int investCount = repository.getInvestCount(id);
        int savingAmount = repository.getSavingAmount(id);
        if (debitCount >= 1 && investCount < 1 && savingAmount > 1000) {
            return Optional.of(recommendation);
        }
        return Optional.empty();
    }

}
