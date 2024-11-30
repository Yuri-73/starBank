package com.example.starBank.recommendation_rules;

import com.example.starBank.model.Recommendation;
import com.example.starBank.repositories.RecommendationsRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class TopSaving implements RecommendationRuleSet{

    private final RecommendationsRepository repository;
    private final Recommendation recommendation;

    public TopSaving(RecommendationsRepository repository) {
        recommendation = new Recommendation(2,"Top saving", "Откройте свою собственную «Копилку» с нашим банком! «Копилка» — " +
                "это уникальный банковский инструмент, который поможет вам легко и удобно накапливать деньги на важные цели. " +
                "Больше никаких забытых чеков и потерянных квитанций — всё под контролем!\n" +
                "\n" + "Преимущества «Копилки»:\n" + "\n" + "Накопление средств на конкретные цели. Установите лимит и срок" +
                " накопления, и банк будет автоматически переводить определенную сумму на ваш счет.\n" + "\n" + "Прозрачность и" +
                " контроль. Отслеживайте свои доходы и расходы, контролируйте процесс накопления и корректируйте стратегию при" +
                " необходимости.\n" + "\n" + "Безопасность и надежность. Ваши средства находятся под защитой банка, а доступ к ним" +
                " возможен только через мобильное приложение или интернет-банкинг.\n" + "\n" +
                "Начните использовать «Копилку» уже сегодня и станьте ближе к своим финансовым целям!");
        this.repository = repository;
    }

    /**
     * Метод формирования рекомендации по условию из полученных значений из БД
     * @param id для поиска по id клиента банка
     * @return Возвращает готовую рекомендацию
     */
    @Override
    public Optional<Recommendation> getRecommendationByRule(UUID id) {
        int debitCount = repository.getDebitCount(id);
        int savingAmount = repository.getSavingAmount(id);
        int debitDepositAmount = repository.getDebitDepositAmount(id);
        int debitWithdrawAmount = repository.getDebitWithdrawAmount(id);
        if (debitCount >= 1 && (savingAmount >= 50000 || debitDepositAmount >= 50000) && debitDepositAmount > debitWithdrawAmount) {
            return Optional.of(recommendation);
        }
        return Optional.empty();
    }

}
