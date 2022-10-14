package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private ElementsCollection cards = $$(".list__item div");
    private ElementsCollection buttons = $$(".list__item div button");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";
    private SelenideElement updateButton = $("[data-test-id=action-reload] .button__text");

    public DashboardPage() {
    }

    public int getCardBalance(String id) {
        val card = cards.findBy(Condition.attribute("data-test-id", id));
        val text = card.text();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public RechargeCardPage RechargeFirstCard(DataHelper.CardNumberInfo info) {
        var fourLastNumbers = info.getFirstCardNumber().substring(15);
        cards.first().shouldHave(Condition.text(fourLastNumbers));
        buttons.first().click();
        return new RechargeCardPage();
    }

    public RechargeCardPage RechargeSecondCard(DataHelper.CardNumberInfo info) {
        var fourLastNumbers = info.getSecondCardNumber().substring(15);
        cards.last().shouldHave(Condition.text(fourLastNumbers));
        buttons.last().click();
        return new RechargeCardPage();
    }

    public DashboardPage UpdateInfo() {
        updateButton.click();
        return new DashboardPage();
    }
}
