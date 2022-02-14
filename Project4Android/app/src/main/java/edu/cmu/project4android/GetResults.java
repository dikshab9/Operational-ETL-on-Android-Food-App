//Name: Diksha Baruah
//Andrew ID: dboruah
package edu.cmu.project4android;

import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

//this class gets recipe results to be displayed to the app
public class GetResults {
    FoodRecipe foodRecipe = null;

    /*
     * Arguments: search term, FoodRecipe object
     *
     */
    //the submit event calls on this method
    public void search(String searchTerm, FoodRecipe foodRecipe) {
        this.foodRecipe = foodRecipe;
        new AsyncEdamamSearch().execute(searchTerm);
    }

    /*
     * AsyncTask provides a simple way to use a thread separate from the UI thread in which to do network operations.
     * doInBackground is run in the helper thread.
     * onPostExecute is run in the UI thread, allowing for safe UI updates.
     */
    private class AsyncEdamamSearch extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            try {
                return search(urls[0]);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return "";
        }

        //this method is run in the UI thread
        protected void onPostExecute(String result)
        {
            System.out.println("RESULT: "+result);
            String display=""; //to store the result that needs to be displayed on the app
            String[] results=result.split(";"); //the parameter result contains seachTerm and response joined by ';'
            String searchTerm=results[0];
            String response=results[1];
            if(!response.equals("Please enter valid input" ) && !response.equals("Cannot connect to URL")){
                try{
                    JSONObject obj= new JSONObject(response); //json object of our response
                    String nameOfDish=obj.getString("nameOfDish");
                    //handle third party invalid data
                    //observation and assumption: In most items the item searched  is present in name of dish
                    if(nameOfDish.toLowerCase().contains(searchTerm.toLowerCase())){
                        //get all important data if this condition is satisfied
                        String source= obj.getString("source");
                        JSONArray ingredientsList= (JSONArray) obj.get("ingredients");
                        List<String> ingredients= new ArrayList<>();
                        for(int i=0;i<ingredientsList.length();i++){
                            ingredients.add(i,ingredientsList.getString(i));
                        }
                        String image= obj.getString("image");
                        String[] calories = obj.getString("calories").split("\\.");
                        String caloriesWhole=calories[0];
                        String timeTaken = obj.getString("timeTaken");

                        //construct a display string to be displayed in browser
                        display="\nName of dish: "+ nameOfDish +
                                "\nSource: "+source +
                                "\nCalories: "+caloriesWhole + " kCals"+
                                "\nCooking Time: "+ timeTaken+" mins";

                        if(ingredients.size()!=0){
                            display += "\nIngredients: ";
                            for(String str:ingredients){
                                display += str +", ";
                            }
                        }
                        display+=";"+image; //separates the image URL from rest of the data by a ';'
                        foodRecipe.recipeReady(display);
                    }
                    else{
                        display=""; //to handle conditions for which the API gives results but in actual such result is invalid
                        foodRecipe.recipeReady(display);
                    }
                }
                catch(JSONException e){
                    e.printStackTrace();
                }
            }
            else if(response.equals("Cannot connect to URL")){
                display="Cannot connect to URL";
                foodRecipe.recipeReady(display);
            }
            else{
                display="";
                foodRecipe.recipeReady(display);
            }

        }

        //fetches json data from the heroku app
        private String search(String searchTerm) throws UnsupportedEncodingException {
            String searchTag = URLEncoder.encode(searchTerm, "UTF-8");
            String response = "";

            String herokuUrl =
                    "https://whispering-badlands-89043.herokuapp.com/?searchWord="+searchTag; //uses the web servlet from project 1 task 1 deployed to heroku
            String herokuUrl2="https://cryptic-inlet-17544.herokuapp.com/getFoodAppServlet1?searchWord="+searchTag; //uses the web servlet from project 1 task 2 deployed to heroku

            //connect to URL
            try {
                URL url = new URL(herokuUrl2);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                // Read all the text returned by the server
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String str;
                // Read each line of "in" until done, adding each to "response"
                while ((str = in.readLine()) != null) {
                    // str is one line of text readLine() strips newline characters
                    response += str;
                    System.out.println(response);
                }
                in.close();
            } catch (IOException e) {
                response="Cannot connect to URL";
                System.out.println("Cannot connect to URL!");
            }
            return searchTerm+";"+response; //return the search term and response

        }

    }
}
