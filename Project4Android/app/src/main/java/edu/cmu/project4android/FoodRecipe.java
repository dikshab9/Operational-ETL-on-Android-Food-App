//Name: Diksha Baruah
//Andrew ID: dboruah
package edu.cmu.project4android;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import edu.cmu.project4android.R;

/*this class consists of event handler on clicking submit
and displays search result for recipe and image*/
public class FoodRecipe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final FoodRecipe foodRecipe = this;

        /*
         * Find the "submit" button, and add a listener to it
         */
        Button submitButton = (Button) findViewById(R.id.submit);
        // Add a listener to the send button
        submitButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View viewParam) {
                String searchTerm = ((EditText) findViewById(R.id.searchTerm)).getText().toString();
                GetResults getResults = new GetResults();
                getResults.search(searchTerm, foodRecipe); // Done asynchronously in another thread.  It calls recipeReady() in this thread when complete.

            }
        });
    }

    /*
     * This is called by the foodRecipe object in GetPicture class.  This allows for passing back the Bitmap picture for updating the ImageView
     */
    public void pictureReady(Bitmap picture) {
        ImageView pictureView = (ImageView) findViewById(R.id.foodPicture);
        pictureView.setImageBitmap(picture);
        pictureView.setVisibility(View.VISIBLE);
        pictureView.invalidate();
    }
    /*
     * This is called by the foodRecipe object in GetRecipe class. This method displays the recipe on the screen
     * And displays error message when invalid data is entered or when the app in unable to connect to the heroku url.
     */

    public void recipeReady(String display){
        //check condition for null display value or URL connection error
        if(!display.equals("") && !display.equals("Cannot connect to URL")){
            TextView searchView = (EditText)findViewById(R.id.searchTerm);
            TextView textView1=findViewById(R.id.textView);
            TextView textView2=findViewById(R.id.textView2);
            textView1.setText("\nHere is the result for "+searchView.getText().toString()+":\n");
            textView1.setVisibility(View.VISIBLE);
            String[] results=display.split(";"); //display consists of recipe data and image url separated by ';'
            textView2.setText(results[0]); //textview2 consists of text data regarding recipe: for eg: name of dish, ingredients etc
            textView2.setVisibility(View.VISIBLE);
            searchView.setText("");
            GetPicture gp= new GetPicture();
            gp.search(results[1],this); //results[1] consists of image url which is processed by the GetPicture class
        }

        //error handling for invalid searches or connection errors
        else{
            TextView searchView = (EditText)findViewById(R.id.searchTerm);
            TextView textView1=findViewById(R.id.textView);
            //if no json response is received for a given search word then its not a valid term
            if(display.equals("")){
                textView1.setText("\nNot a valid term! "+searchView.getText().toString()+" Could not be found.");
            }
            else if(display.equals("Cannot connect to URL")){
                textView1.setText("\n Cannot connect to URL, please try again later!");
            }
            textView1.setVisibility(View.VISIBLE);
            searchView.setText("");
            ImageView pictureView =  findViewById(R.id.foodPicture);
            pictureView.setVisibility(View.INVISIBLE);
            TextView textView2=findViewById(R.id.textView2);
            textView2.setVisibility(View.INVISIBLE);
        }


    }
}
