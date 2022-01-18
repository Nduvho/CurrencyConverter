import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.http.HttpRequest;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Converter {

    public static void main(String[] args) throws IOException {
        Boolean stillRunning = true;
        do {
        HashMap<Integer, String> codeForCurrency = new HashMap<Integer, String>();

        // add codes
        codeForCurrency.put(1,"USD");
        codeForCurrency.put(2,"ZAR");
        codeForCurrency.put(3,"BDT");
        codeForCurrency.put(4,"PKR");
        codeForCurrency.put(5,"INR");

        Integer from,to;

        String fromCurrency, toCurrency;
        double currAmount;

        Scanner sc = new Scanner(System.in);

        System.out.println("Currency Converter App\nThis program converts different currencies to USD\nFor the purpose of this program we are going to use codes 1-5 to specify the different currencies");
        System.out.println("\n");

        System.out.println("Please choose code 2-5 to specify currency which you want to convert from");
        System.out.println("2:ZAR (South African rand) \t 3:BDT (Bangladeshi taka) \t 4.PKR (Pakistani rupee) \t 5.INR (Indian rupee) ");
       // fromCurrency = codeForCurrency.get(sc.nextInt());
        from = sc.nextInt();
        while(from < 2 || from > 5){
            System.out.println("Please select a valid currency (2-5)");
            System.out.println("2:ZAR (South African rand) \t 3:BDT (Bangladeshi taka) \t 4.PKR (Pakistani rupee) \t 5.INR (Indian rupee) ");
            from =sc.nextInt();
        }
        fromCurrency = codeForCurrency.get(from);

        System.out.println("To Currency");
        System.out.println("Please enter code 1 for 1:USD (United States Dollar)");
        //toCurrency = codeForCurrency.get(sc.nextInt());
        to = sc.nextInt();
        while(to != 1){
            System.out.println("Please select a valid currency (1)");
            System.out.println("1.USD (United States Dollar)");
            to =sc.nextInt();
        }
        toCurrency = codeForCurrency.get(to);

        System.out.println("Enter amount: ");
        currAmount = sc.nextFloat();

        HttpRequest(fromCurrency,toCurrency,currAmount);

        System.out.println("Would you like to make another conversion?");
        System.out.println("1:(YES) \t Or any other integer value for :(N0)");
            if(sc.nextInt() != 1){
                stillRunning = false;
            }
        }while (stillRunning);
        System.out.println("You've successfully converted the currency");

    }

    private static void HttpRequest(String fromCurrency, String toCurrency, double currAmount) throws IOException {

        String pattern = "##.##";
        DecimalFormat f = new DecimalFormat(pattern);
        try {
            String apikey = "YOUR-APIKEY";
            String url = "https://freecurrencyapi.net/api/v2/latest";
            URL urlForGetRequest = new URL(url);
            String readLine = null;
            HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
            conection.setRequestMethod("GET");
            int responseCode = conection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conection.getInputStream()));
                StringBuffer response = new StringBuffer();
                while ((readLine = in.readLine()) != null) {
                    response.append(readLine);
                }
                in.close();
                //System.out.println(response.toString());
                System.out.println("\n");
                JSONObject object = new JSONObject(response.toString());
                Double exRate = object.getJSONObject("data").getDouble(fromCurrency);
                System.out.println(object.getJSONObject("data"));
                System.out.println(exRate); //keep for debugging
                System.out.println("\n");
                System.out.println(f.format(currAmount)  + fromCurrency + " = " + f.format(currAmount/exRate)  + toCurrency);
            } else {
                throw new Exception("Error in API Call");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }






    }

}
