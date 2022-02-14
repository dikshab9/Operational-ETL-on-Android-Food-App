package edu.cmu.diksha;
//Diksha Baruah
//andrew ID: dboruah
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*this is the model class for my food recipe search app
Note: Parts of code taken from Interesting Picture lab
* */
public class FoodSearchModel {
    //this method calls the fetch method to receive json response from EDAMAM food recipe URL
    public String doEdamamSearch(String searchTag)
            throws UnsupportedEncodingException {

        searchTag = URLEncoder.encode(searchTag, "UTF-8");
        String response = "";

        //the url consists of app_id, api key and search tag specified by user
        String edamamURL =
                "https://api.edamam.com/search?q="+searchTag.strip()+"&app_id=c1b6ade8&app_key=bd82130b2e3d41a6f8cbab1e151c90f6";

        // Fetch the page
        response = fetch(edamamURL); //gets the json response
        System.out.println(response);

        Map<String, Object> responseMap = new Gson().fromJson(
                response, new TypeToken<HashMap<String, Object>>() {}.getType()
        ); //convert the response in map for evaluation

        ArrayList<Object> hits= (ArrayList<Object>) responseMap.get("hits"); //get hits object from the map- hits consists of useful data like recipe, image, calories etc
        if(hits.size()!=0){
            LinkedTreeMap<String, Object> recipe = (LinkedTreeMap<String, Object>) hits.get((0)); //get the first element from a list of hits
            LinkedTreeMap<String, Object> recipeCharacteristics = (LinkedTreeMap<String, Object>) recipe.get(("recipe")); //extract recipe characteristics like cooking time, ingredients, calories etc
            System.out.println(response);
            System.out.println("\n");
            //placeholder for storing only the important results
            String nameOfDish=recipeCharacteristics.get("label").toString() ; //consists of dish name
            String source = recipeCharacteristics.get("source").toString(); //consists of source from where the recipe was obtained
            List<String> ingredients= (List<String>) recipeCharacteristics.get("ingredientLines"); //a list of ingredients that can be used to make the specified item
            String calories= recipeCharacteristics.get("calories").toString(); //consists of calories of the food item displayed
            String timeTaken = recipeCharacteristics.get("totalTime").toString(); //cooking time for the item
            String image= recipeCharacteristics.get("image").toString(); //consists of image URL
            FoodAppSchema schema= new FoodAppSchema(nameOfDish,source,ingredients,calories,timeTaken,image); //stores results in food schema
            String jsonResult = new Gson().toJson(schema); //converts the result to JSON format to be used by the client calling this web application
            System.out.println(jsonResult);
            return jsonResult;
        }
        else{
            return "Please enter valid input"; //if hits size=0, it means that it is an invalid search since there is no recipe present
        }

    }

    /*
     * This class makes an HTTP request to a given URL
     * @param urlString The URL of the request
     * @return A string of the response from the HTTP GET.
     */
    private String fetch(String urlString) {
        String response = "";
        try {
            URL url = new URL(urlString);
            /*
             * Create an HttpURLConnection.
             */
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Read all the text returned by the server
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String str;
            // Read each line of "in" until done, adding each to "response"
            while ((str = in.readLine()) != null) {
                // str is one line of text readLine() strips newline characters
                response += str;
            }
            in.close();
        } catch (IOException e) {
            System.out.println("Unable to connect to URL!!");
        }
        return response;
    }
}
