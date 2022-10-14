package ru.netology.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.*;

public class MoneyTransferTests {

    String firstAmount = "500";
    String secondAmount = "1000";
    ElementsCollection cardsInfo = $$(".list__item div");
    String firstId = "92df3f1c-a033-48e6-8390-206f6b1f56c0";
    String secondId = "0f3f5c2a-249e-4c3d-8287-09f7a039391d";

    @Test
    void transferMoneyFromFirstCardToSecond() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var cardNumberInfo = DataHelper.getCardNumberInfoFor(authInfo);
        var rechargeCardPage = dashboardPage.RechargeSecondCard(cardNumberInfo);
        var dashboardPageAfterTransfer = rechargeCardPage.transferMoneyToSecondCard(firstAmount, cardNumberInfo);
        dashboardPageAfterTransfer.UpdateInfo();
        var actualFirstCardBalance = dashboardPageAfterTransfer.getCardBalance(firstId);
        var actualSecondCardBalance = dashboardPageAfterTransfer.getCardBalance(secondId);
        cardsInfo.first().shouldHave(Condition.text(String.valueOf(actualFirstCardBalance)));
        cardsInfo.last().shouldHave(Condition.text(String.valueOf(actualSecondCardBalance)));
    }

    @Test
    void transferMoneyFromSecondCardToFirst() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var cardNumberInfo = DataHelper.getCardNumberInfoFor(authInfo);
        var rechargeCardPage = dashboardPage.RechargeFirstCard(cardNumberInfo);
        var dashboardPageAfterTransfer = rechargeCardPage.transferMoneyToFirstCard(secondAmount, cardNumberInfo);
        dashboardPageAfterTransfer.UpdateInfo();
        var actualFirstCardBalance = dashboardPageAfterTransfer.getCardBalance(firstId);
        var actualSecondCardBalance = dashboardPageAfterTransfer.getCardBalance(secondId);
        cardsInfo.first().shouldHave(Condition.text(String.valueOf(actualFirstCardBalance)));
        cardsInfo.last().shouldHave(Condition.text(String.valueOf(actualSecondCardBalance)));
    }
}
