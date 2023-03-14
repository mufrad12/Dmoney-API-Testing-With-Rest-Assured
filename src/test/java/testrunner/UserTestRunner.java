package testrunner;

import controller.User;
import io.restassured.path.json.JsonPath;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.Assert;
import org.testng.annotations.Test;
import setup.Setup;
import utils.Utils;

import java.io.IOException;

public class UserTestRunner extends Setup {
    User user;


    @Test(priority = 1, description = "Login API with invalid Password")  // *** NEGATIVE TEST ***
    public void loginWithInvalidCred() throws IOException {
        user = new User();
        JsonPath jsonResponse = user.callLoginAPI("salman@roadtocareer.net", "0000");
        String message = jsonResponse.get("message");
        System.out.println(message);

        Assert.assertTrue(message.contains("Password incorrect"));
    }


    @Test(priority = 2, description = "Login API with valid Email & Password")  // *** POSITIVE TEST ***
    public void loginWithValidCred() throws ConfigurationException, IOException {
        user = new User();
        JsonPath jsonResponse = user.callLoginAPI("salman@roadtocareer.net", "1234");
        String token = jsonResponse.get("token");
        String message = jsonResponse.get("message");
        Utils.setEnvVariable("token", token);
        System.out.println(token);
        System.out.println(message);

        Assert.assertTrue(message.contains("Login successfully"));

    }

    // ---------------------------------------------------------------------------------------------------------- //


    @Test(priority = 3, description = "Create User with existing information") // *** NEGATIVE TEST ***
    public void createUserWithExistingInfo() throws IOException {
        user = new User();
        JsonPath jsonResponse = user.createUser("Test Customer 1", "user81765@test.com", "1234", "01508356907", "123456789", "Customer");
        System.out.println(jsonResponse.get().toString());
        String message = jsonResponse.get("message");

        Assert.assertTrue(message.contains("already exists"));
    }


    @Test(priority = 4, description = "Create User with valid information")  // *** POSITIVE TEST ***
    public void createUserWithValidInfo() throws ConfigurationException, IOException {
        user = new User();
        Utils utils = new Utils();

        // Customer
        utils.generateRandomUser();
        JsonPath jsonResponse = user.createUser(utils.getName(), utils.getEmail(), "1234", utils.generatePhoneNumber(), "1456237894", "Customer");
        System.out.println(jsonResponse.get().toString());
        String message = jsonResponse.get("message");
        Utils.setEnvVariable("Customer_id", jsonResponse.get("user.id").toString());
        Utils.setEnvVariable("Customer_name", jsonResponse.get("user.name"));
        Utils.setEnvVariable("Customer_email", jsonResponse.get("user.email"));
        Utils.setEnvVariable("Customer_phone", jsonResponse.get("user.phone_number"));

        Assert.assertTrue(message.contains("User created"));

        // Agent
        utils.generateRandomUser();
        JsonPath jsonResponse1 = user.createUser(utils.getName(), utils.getEmail(), "1234", utils.generatePhoneNumber(), "1450037894", "Agent");
        System.out.println(jsonResponse1.get().toString());
        String message1 = jsonResponse1.get("message");
        Utils.setEnvVariable("Agent_id", jsonResponse1.get("user.id").toString());
        Utils.setEnvVariable("Agent_name", jsonResponse1.get("user.name"));
        Utils.setEnvVariable("Agent_email", jsonResponse1.get("user.email"));
        Utils.setEnvVariable("Agent_phone", jsonResponse1.get("user.phone_number"));

        Assert.assertTrue(message1.contains("User created"));
    }

    // ---------------------------------------------------------------------------------------------------------- //



    @Test(priority = 5, description = "Searching Customer by invalid Phone Number") // *** NEGATIVE TEST ***
    public void searchCustomerByInValidPhoneNumber() throws IOException {
        user = new User();
        JsonPath jsonResponse = user.searchCustomerByPhoneNumber("01722023445");
        System.out.println(jsonResponse.get().toString());
        String message = jsonResponse.get("message");

        Assert.assertTrue(message.contains("User not found"));
    }


    @Test(priority = 6, description = "Searching Customer by valid Phone Number") // *** POSITIVE TEST ***
    public void searchCustomerByValidPhoneNumber() throws IOException {
        user = new User();
        JsonPath jsonResponse = user.searchCustomerByPhoneNumber(prop.getProperty("Customer_phone"));
        System.out.println(jsonResponse.get().toString());
        String message = jsonResponse.get("message");

        Assert.assertTrue(message.contains("User found"));
    }
}
