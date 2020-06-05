package currencycloud.pages;

import currencycloud.utilities.ConfigurationReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import static io.restassured.RestAssured.baseURI;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static  org.junit.Assert.assertEquals;

public class CurrencyPage  {

    Response response;

    String token="";
    double sellAmount = Double.parseDouble(ConfigurationReader.getProperty("sell_amount"));
    double rate;


    public void setURL(){
        baseURI= ConfigurationReader.getProperty("baseURI");
        //port=Integer.parseInt(ConfigurationReader.getProperty("port"));
        Map<String,Object> credentials= new HashMap<>();
        credentials.put("api_key",ConfigurationReader.getProperty("api_key"));
        credentials.put("login_id",ConfigurationReader.getProperty("login_id"));
        Response response =
                given()
                        .contentType(ContentType.JSON).body(credentials).
                        when()
                        .post("/authenticate/api").prettyPeek();



        token=response.path("auth_token").toString();
        System.out.println(token);




    }



    public  void loginWithApiKey(){
        setURL();
        Response response=
                given()
                        .contentType(ContentType.JSON).header("X-Auth-Token",token).
                        when()
                        .post("/authenticate/close_session").prettyPeek();


        assertEquals(200,response.getStatusCode());
        assertEquals("application/json;charset=utf-8",response.contentType());

    }




    public void checkBalance(){
        setURL();

        Response response=
                given()
                        .header("X-Auth-Token",token).contentType(ContentType.JSON).
                        when()
                        .get("/balances/GBP").prettyPeek();

        assertEquals(200,response.getStatusCode());
        assertEquals("application/json;charset=utf-8",response.contentType());






        String balance=response.path("amount").toString();
        assertEquals("GBP",response.path("currency").toString());
        assertEquals("100000.00",balance);

    }


    public void findBalances(){

        setURL();
        Response response=
                given()
                        .header("X-Auth-Token",token).contentType(ContentType.JSON).
                        when()
                        .get("/balances/find").prettyPeek();

        assertEquals(200,response.getStatusCode());

        List<String> currencies=response.jsonPath().getList("balances.currency");
        List<Object> balances  =  response.jsonPath().getList("balances.amount");

        Map<String, Object > currencybalances=new HashMap<>();

        for (int i =0; i<balances.size(); i++){
            currencybalances.put(currencies.get(i),balances.get(i));
        }


        System.out.println("Currencies:"+currencybalances.toString());



    }


    public void get_A_Quote(){
        setURL();
        Response response=
                given().contentType(ContentType.JSON)
                        .queryParam("sell_currency","GBP")
                        .queryParam("buy_currency","USD")
                        .queryParam("amount",ConfigurationReader.getProperty("sell_amount"))
                        .queryParam("fixed_side","sell")
                        .header("X-Auth-Token",token).
                        when()
                        .get("/rates/detailed").prettyPeek();

        assertEquals(200,response.getStatusCode());
        assertEquals("application/json;charset=utf-8",response.contentType());




        rate= Double.parseDouble(response.path("client_rate").toString());
        System.out.println("Your rate: "+rate);
    }


    public void convert(){
        setURL();
        Map<String,Object> allPayload = new HashMap<>();
        allPayload.put("sell_currency","GBP");
        allPayload.put("buy_currency","USD");  //maybe I will be needed to change
        allPayload.put("amount", sellAmount);
        allPayload.put("fixed_side","sell");  ///Maybe I will be needed to change buy to sell
        allPayload.put("reason","USD topup"); ///Maybe I will be needed to change buy to sell depends to
        allPayload.put("term_agreement","true");



        Response response=
                given().contentType(ContentType.JSON)
                        .body(allPayload)
                        .header("X-Auth-Token",token).
                        when()
                        .post("/conversions/create").prettyPeek();

        assertEquals(200,response.getStatusCode());

        assertEquals("application/json;charset=utf-8",response.contentType());



        ///ASSERTION PART

        rate= response.jsonPath().getDouble("client_rate");
        //rate =Integer.parseInt(response.path("client_rate").toString());
        //int buyAmount =Integer.parseInt(response.path("client_buy_amount").toString());
        double actualbuyAmount =  response.jsonPath().getDouble("client_buy_amount");

        double expectedBuyAmount=sellAmount*rate;


        assertEquals((long)expectedBuyAmount,(long)actualbuyAmount);





        System.out.println("Sell amount: "+sellAmount);
        System.out.println("Rate : "+rate);
        System.out.println("Buying amount amount: " +expectedBuyAmount);


    }



    public void getSecondQuote(){
        setURL();
        Response response=
                given().contentType(ContentType.JSON)
                        .queryParam("sell_currency","GBP")
                        .queryParam("buy_currency","USD")
                        .queryParam("amount",ConfigurationReader.getProperty("sell_amount"))
                        .queryParam("fixed_side","sell")
                        .header("X-Auth-Toke",token). // should be Token not Toke
                        when()
                        .get("/rates/detailed").prettyPeek();

        assertEquals(200,response.getStatusCode());
        assertEquals("application/json;charset=utf-8",response.contentType());


        rate= response.jsonPath().getDouble("client_rate");
        System.out.println("Rate: "+rate);
    }









    public void closeSession(){
        RestAssured.reset();
    }



}
