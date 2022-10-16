package ru.netology.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.*;

public class MoneyTransferTests {

    String firstAmount = "500";
    String secondAmount = "1000";

    @Test
    void transferMoneyFromFirstCardToSecond() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardInfo = DataHelper.getFirstCardInfo(authInfo);
        var secondCardInfo = DataHelper.getSecondCardInfo(authInfo);
        var firstCardBalanceBeforeTransaction = dashboardPage.getCardBalance(firstCardInfo);
        var secondCardBalanceBeforeTransaction = dashboardPage.getCardBalance(secondCardInfo);
        var rechargeCardPage = dashboardPage.CardSelection(secondCardInfo);
        var dashboardPageAfterTransfer = rechargeCardPage.transferMoneyToCard(firstAmount, firstCardInfo);
        dashboardPageAfterTransfer.UpdateInfo();
        var expectedFirstCardBalance = firstCardBalanceBeforeTransaction - Integer.parseInt(firstAmount);
        var expectedSecondCardBalance = secondCardBalanceBeforeTransaction + Integer.parseInt(firstAmount);
        var actualFirstCardBalance = dashboardPageAfterTransfer.getCardBalance(firstCardInfo);
        var actualSecondCardBalance = dashboardPageAfterTransfer.getCardBalance(secondCardInfo);
        Assertions.assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        Assertions.assertEquals(expectedSecondCardBalance, actualSecondCardBalance);
    }

    @Test
    void transferMoneyFromSecondCardToFirst() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);
        var firstCardInfo = DataHelper.getFirstCardInfo(authInfo);
        var secondCardInfo = DataHelper.getSecondCardInfo(authInfo);
        var firstCardBalanceBeforeTransaction = dashboardPage.getCardBalance(firstCardInfo);
        var secondCardBalanceBeforeTransaction = dashboardPage.getCardBalance(secondCardInfo);
        var rechargeCardPage = dashboardPage.CardSelection(firstCardInfo);
        var dashboardPageAfterTransfer = rechargeCardPage.transferMoneyToCard(secondAmount, secondCardInfo);
        dashboardPageAfterTransfer.UpdateInfo();
        var expectedFirstCardBalance = firstCardBalanceBeforeTransaction + Integer.parseInt(secondAmount);
        var expectedSecondCardBalance = secondCardBalanceBeforeTransaction - Integer.parseInt(secondAmount);
        var actualFirstCardBalance = dashboardPageAfterTransfer.getCardBalance(firstCardInfo);
        var actualSecondCardBalance = dashboardPageAfterTransfer.getCardBalance(secondCardInfo);
        Assertions.assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        Assertions.assertEquals(expectedSecondCardBalance, actualSecondCardBalance);
    }
}
