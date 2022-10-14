package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class RechargeCardPage {
    private SelenideElement amountField = $("[data-test-id=amount] input");
    private SelenideElement fromField = $("[data-test-id=from] input");
    private SelenideElement actionTransferButton = $("[data-test-id=action-transfer] .button__content");
    private SelenideElement actionCancelButton = $("[data-test-id=action-cancel] .button__content");

    public DashboardPage transferMoneyToFirstCard(String amount, DataHelper.CardNumberInfo info) {
        amountField.setValue(amount);
        fromField.setValue(info.getSecondCardNumber());
        actionTransferButton.click();
        return new DashboardPage();
    }

    public DashboardPage transferMoneyToSecondCard(String amount, DataHelper.CardNumberInfo info) {
        amountField.setValue(amount);
        fromField.setValue(info.getFirstCardNumber());
        actionTransferButton.click();
        return new DashboardPage();
    }

    public DashboardPage interruptTheOperation() {
        actionCancelButton.click();
        return new DashboardPage();
    }
}

