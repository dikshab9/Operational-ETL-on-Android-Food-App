package edu.cmu.diksha;
//Diksha Baruah
//andrew ID: dboruah
import java.util.List;

//this is the schema class for my android app
public class FoodAppSchema {

    String nameOfDish;
    String source;
    List<String> ingredients;
    String image;
    String calories;
    String timeTaken;

    public FoodAppSchema(String nameOfDish, String source, List<String> ingredients, String calories, String timeTaken, String image) {
        this.nameOfDish = nameOfDish;
        this.source = source;
        this.ingredients = ingredients;
        this.calories = calories;
        this.timeTaken = timeTaken;
        this.image=image;
    }

}
