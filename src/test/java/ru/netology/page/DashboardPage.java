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

    public int getCardBalance(DataHelper.Card info) {
        val card = cards.findBy(Condition.attribute("data-test-id", info.getId()));
        val text = card.text();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public RechargeCardPage cardSelection(DataHelper.Card info) {
        val card = cards.findBy(Condition.attribute("data-test-id", info.getId()));
        val indexOfSelenideElement = cards.indexOf(card);
        val button = buttons.get(indexOfSelenideElement);
        button.click();
        return new RechargeCardPage();
    }

    public DashboardPage updateInfo() {
        updateButton.click();
        return new DashboardPage();
    }
}
