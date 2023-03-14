package testrunner;

import controller.Transaction;
import io.restassured.path.json.JsonPath;
import org.apache.commons.configuration.ConfigurationException;
import org.junit.Assert;
import org.testng.annotations.Test;

import setup.Setup;
import utils.Utils;

import java.io.IOException;

public class TransactionTestRunner extends Setup {
    Transaction transaction;

    @Test(priority = 1, description = "Deposit 0 tk to the Agent from System") // *** NEGATIVE TEST ***
    public void depositInsufficientBalance() throws IOException {

        transaction = new Transaction();
        String agentAccount = prop.getProperty("Agent_phone");
        JsonPath jsonResponse = transaction.depositMoney("SYSTEM", agentAccount, 0);
        System.out.println(jsonResponse.get().toString());

        String message = jsonResponse.get("message");
        Assert.assertTrue(message.contains("Minimum deposit amount 10 tk and maximum deposit amount 10000 tk"));
    }

    @Test(priority = 2, description = "Deposit tk to the Invalid Agent from System") // *** NEGATIVE TEST ***
    public void depositBalanceToInvalidAgent() throws IOException {

        transaction = new Transaction();
        JsonPath jsonResponse = transaction.depositMoney("SYSTEM", "01722023445", 2000);
        System.out.println(jsonResponse.get().toString());

        String message = jsonResponse.get("message");
        Assert.assertTrue(message.contains("Account does not exist"));
    }


    @Test(priority = 3, description = "Deposit 5000 tk to the Agent from System")  // *** POSITIVE TEST ***
    public void depositValidBalance() throws IOException, ConfigurationException {

        transaction = new Transaction();
        String agentAccount = prop.getProperty("Agent_phone");
        JsonPath jsonResponse = transaction.depositMoney("SYSTEM", agentAccount, 5000);
        System.out.println(jsonResponse.get().toString());

        Utils.setEnvVariable("Agent_Transaction_Id", jsonResponse.get("trnxId").toString());

        String message = jsonResponse.get("message");
        Assert.assertTrue(message.contains("Deposit successful"));
    }


    // ---------------------------------------------------------------------------------------------------------- //



    @Test(priority = 4, description = "Deposit tk by Agent to Invalid Customer") // *** NEGATIVE TEST ***
    public void depositToInvalidCus() throws IOException {

        transaction = new Transaction();
        String agentAccount = prop.getProperty("Agent_phone");
        JsonPath jsonResponse = transaction.depositMoney(agentAccount, "01722023445", 3000);
        System.out.println(jsonResponse.get().toString());

        String message = jsonResponse.get("message");
        Assert.assertTrue(message.contains("Account does not exist"));
    }


    @Test(priority = 5, description = "Deposit 2000 tk by Agent to Customer ") // *** POSITIVE TEST ***
    public void depositToCustomer() throws IOException, ConfigurationException {

        transaction = new Transaction();
        String agentAccount = prop.getProperty("Agent_phone");
        String customerAccount = prop.getProperty("Customer_phone");
        JsonPath jsonResponse = transaction.depositMoney(agentAccount, customerAccount, 2000);
        System.out.println(jsonResponse.get().toString());

        Utils.setEnvVariable("Customer_Transaction_Id", jsonResponse.get("trnxId").toString());

        String message = jsonResponse.get("message");
        Assert.assertTrue(message.contains("Deposit successful"));
    }




    // ---------------------------------------------------------------------------------------------------------- //


    @Test(priority = 6, description = "Check balance of Customer with invalid Phone Number")  // *** NEGATIVE TEST ***
    public void checkCusBalWithInvalidNumber() throws IOException {

        transaction = new Transaction();
        JsonPath jsonResponse = transaction.checkCustomerBalance("01722023445");
        System.out.println(jsonResponse.get().toString());

        String message = jsonResponse.get("message");
        Assert.assertTrue(message.contains("User not found"));
    }


    @Test(priority = 7, description = "Check balance of Customer with valid Phone number") // *** POSITIVE TEST ***
    public void checkCusBaleWithValidNumber() throws IOException {

        transaction = new Transaction();
        JsonPath jsonResponse = transaction.checkCustomerBalance(prop.getProperty("Customer_phone"));
        System.out.println(jsonResponse.get().toString());

        String message = jsonResponse.get("message");
        Assert.assertTrue(message.contains("User balance"));
    }



    // ---------------------------------------------------------------------------------------------------------- //



    @Test(priority = 8, description = "Check statement by invalid trnxId")   // *** NEGATIVE TEST ***
    public void checkStatementByInvalidTranId() throws IOException {

        transaction = new Transaction();
        JsonPath jsonResponse = transaction.checkStatementByTrnxId("TXN545747");
        System.out.println(jsonResponse.get().toString());

        String message = jsonResponse.get("message");
        Assert.assertTrue(message.contains("Transaction not found"));

    }


    @Test(priority = 9, description = "Check statement by Valid TrnxId")   // *** POSITIVE TEST ***
    public void checkStatementByValidTranId() throws IOException {

        transaction = new Transaction();
        JsonPath jsonResponse = transaction.checkStatementByTrnxId(prop.getProperty("Customer_Transaction_Id"));
        System.out.println(jsonResponse.get().toString());

        String message = jsonResponse.get("message");
        Assert.assertTrue(message.contains("Transaction list"));

    }


    // ---------------------------------------------------------------------------------------------------------- //



    @Test(priority = 10, description = " Withdraw tk by Customer to Invalid Agent")  // *** NEGATIVE TEST ***
    public void withdrawByCusToInvalidAgent() throws IOException {

        transaction = new Transaction();
        String customerAccount = prop.getProperty("Customer_phone");
        JsonPath jsonResponse = transaction.withdrawCustomerBalance(customerAccount, "01722023445", 1000);
        System.out.println(jsonResponse.get().toString());

        String message = jsonResponse.get("message");
        Assert.assertTrue(message.contains("Account does not exist"));
    }



    @Test(priority = 11, description = "Withdraw 1000 tk by Customer to Valid Agent") // *** POSITIVE TEST ***
    public void withdrawByCustomer() throws IOException {

        transaction = new Transaction();
        String agentAccount = prop.getProperty("Agent_phone");
        String customerAccount = prop.getProperty("Customer_phone");

        JsonPath jsonResponse = transaction.withdrawCustomerBalance(customerAccount, agentAccount, 1000);
        System.out.println(jsonResponse.get().toString());

        String message = jsonResponse.get("message");
        Assert.assertTrue(message.contains("Withdraw successful"));

    }


    // ---------------------------------------------------------------------------------------------------------- //


    @Test(priority = 12, description = " Send tk to another Invalid Customer") // *** NEGATIVE TEST ***
    public void sendToInvalidCus() throws IOException {

        transaction = new Transaction();
        String customerPhoneNumber = prop.getProperty("Customer_phone");
        JsonPath jsonResponse = transaction.sendBalanceToAnotherCustomer(customerPhoneNumber, "01722023445", 500);
        System.out.println(jsonResponse.get().toString());

        String message = jsonResponse.get("message");
        Assert.assertTrue(message.contains("From/To Account does not exist"));
    }


    @Test(priority = 13, description = "Send 500 tk to Another Customer") // *** POSITIVE TEST ***
    public void sendMoneyToAnotherCus() throws IOException {

        transaction = new Transaction();
        String customerPhoneNumber = prop.getProperty("Customer_phone");
        JsonPath jsonResponse = transaction.sendBalanceToAnotherCustomer(customerPhoneNumber, "01686606909", 500);
        System.out.println(jsonResponse.get().toString());

        String message = jsonResponse.get("message");
        Assert.assertTrue(message.contains("Send money successful"));
    }


    // ---------------------------------------------------------------------------------------------------------- //


    @Test(priority = 14, description = "Check Customer statement by Invalid Phone Number") // *** NEGATIVE TEST ***
    public void checkCusStatementWithInvalidNumber() throws IOException {

        transaction = new Transaction();
        JsonPath jsonResponse = transaction.CustomerStatement("01722023445");
        System.out.println(jsonResponse.get().toString());

        String message = jsonResponse.get("message");
        Assert.assertTrue(message.contains("User not found"));
    }


    @Test(priority = 15, description = "Check Customer statement by Valid Phone Number") // *** POSITIVE TEST ***
    public void checkCustomerStatement() throws IOException {

        transaction = new Transaction();
        JsonPath jsonResponse = transaction.CustomerStatement(prop.getProperty("Customer_phone"));
        System.out.println(jsonResponse.get().toString());

        String message = jsonResponse.get("message");
        Assert.assertTrue(message.contains("Transaction list"));
    }
}
