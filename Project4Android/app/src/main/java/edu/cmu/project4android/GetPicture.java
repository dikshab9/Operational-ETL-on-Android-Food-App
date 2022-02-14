//Name: Diksha Baruah
//Andrew ID: dboruah

package edu.cmu.project4android;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

//this class obtains the picture from the picture URL provided by FoodRecipe.java
public class GetPicture {
    FoodRecipe foodRecipe = null;

    /*
     * search is the public GetPicture method.  Its arguments are the search term, and the FoodRecipe object that called it.
     */
    public void search(String searchTerm, FoodRecipe foodRecipe) {
        this.foodRecipe = foodRecipe;
        new AsyncFlickrSearch().execute(searchTerm);
    }

    /*
     * AsyncTask provides a simple way to use a thread separate from the UI thread in which to do network operations.
     * doInBackground is run in the helper thread.
     * onPostExecute is run in the UI thread, allowing for safe UI updates.
     */
    private class AsyncFlickrSearch extends AsyncTask<String, Void, Bitmap> {
        protected Bitmap doInBackground(String... urls) {
            return getImage(urls[0]);
        }

        protected void onPostExecute(Bitmap picture) {
            foodRecipe.pictureReady(picture);
        } //calls the pictureReady method in food recipe after obtaining the picture from the URL in Bitmap format

        /*
         * Given a URL referring to an image, return a bitmap of that image
         */
        private Bitmap getImage(String  pictureUrl) {
            try {
                URL url = new URL(pictureUrl);
                final URLConnection conn = url.openConnection();
                conn.connect();
                BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
                Bitmap bm = BitmapFactory.decodeStream(bis);
                bis.close();
                return bm;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
