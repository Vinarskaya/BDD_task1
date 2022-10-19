package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class RechargeCardPage {
    private SelenideElement amountField = $("[data-test-id=amount] input");
    private SelenideElement fromField = $("[data-test-id=from] input");
    private SelenideElement actionTransferButton = $("[data-test-id=action-transfer] .button__content");
    private SelenideElement errorNotification = $("[data-test-id=error-notification]");

    public void transferMoneyToCard(String transferAmount, DataHelper.Card info, int cardBalanceBeforeTransaction ) {
        amountField.setValue(transferAmount);
        fromField.setValue(info.getCardNumber());
        actionTransferButton.click();
        possibilityOfPayment(cardBalanceBeforeTransaction, transferAmount);
    }

    public void possibilityOfPayment (int cardBalanceBeforeTransaction, String transferAmount) {
        if (cardBalanceBeforeTransaction < Integer.parseInt(transferAmount)) {
            errorNotification.shouldBe(Condition.visible);
        }
    }
}

