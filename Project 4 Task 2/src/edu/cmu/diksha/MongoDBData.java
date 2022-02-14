//Diksha Baruah
//andrew ID: dboruah
package edu.cmu.diksha;

//this class consists of schema for mongo db dashboard
public class MongoDBData {


    String date;
    String startTime;
    String endTime;
    String latency;
    String nameOfDish;
    String calories;
    String picURL;

    public MongoDBData(String date, String startTime, String endTime, String latency, String nameOfDish, String calories, String picURL) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.latency = latency;
        this.nameOfDish = nameOfDish;
        this.calories = calories;
        this.picURL = picURL;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLatency() {
        return latency;
    }

    public void setLatency(String latency) {
        this.latency = latency;
    }

    public String getNameOfDish() {
        return nameOfDish;
    }

    public void setNameOfDish(String nameOfDish) {
        this.nameOfDish = nameOfDish;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getPicURL() {
        return picURL;
    }

    public void setPicURL(String picURL) {
        this.picURL = picURL;
    }
}
