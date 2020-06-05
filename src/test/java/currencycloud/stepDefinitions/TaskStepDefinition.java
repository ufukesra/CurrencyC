package currencycloud.stepDefinitions;

import currencycloud.pages.CurrencyPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class TaskStepDefinition {

    CurrencyPage currencyPage = new CurrencyPage();


    @Given("login with id and api_key then create an authentication token")
    public void login_with_id_and_api_key_then_create_an_authentication_token() {

        currencyPage.loginWithApiKey();
    }

    @When("user create a GBPUSD pair quote and check all currency balances")
    public void user_create_a_GBPUSD_pair_quote_and_check_all_currency_balances() {
         currencyPage.findBalances();
        currencyPage.get_A_Quote();
    }

    @Then("user sell GBP and buy USD the sell side and verify the buy amount is correct to the rate")
    public void user_sell_GBP_and_buy_USD_the_sell_side_and_verify_the_buy_amount_is_correct_to_the_rate() {

        currencyPage.convert();

    }

    @Then("user create second Quote the buy amount is wrong")
    public void user_create_second_Quote_the_buy_amount_is_wrong() {

        currencyPage.getSecondQuote();
    }

    @Then("end the API session")
    public void end_the_API_session() {

        currencyPage.closeSession();
    }


}
