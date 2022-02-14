//Diksha Baruah
//andrew ID: dboruah
package edu.cmu.diksha;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//this class is used to connect to mongo DB database, insert data, read data from DB
public class MongoDBModel {

    List<MongoDBData> mongoData=new ArrayList<>(); //list of type MongoDBData



    //this method connects to MONGO DB database and returns a collection consisting of documents
    public static MongoCollection<Document> connectToDB(){
        MongoClientURI uri = new MongoClientURI(
                "mongodb+srv://diksha_bh:1234@cluster-diksha.hbwvb.mongodb.net/MongoDB-project4?retryWrites=true&w=majority");
        MongoClient mongoClient = new MongoClient(uri);
        MongoDatabase database = mongoClient.getDatabase("MongoDB-project4");
        MongoCollection<Document> collection = database.getCollection("LogData");
        return collection;

    }

    //this method inserts data into the mongo DB database
    public void insert(String date, String startTime, String endTime, String latency, String nameOfDish, String calories, String picURL){
        //create a map to store data
        Map<String, String> data= new HashMap<>();
        data.put("Date",date);
        data.put("Start Time",startTime);
        data.put("End Time", endTime);
        data.put("Latency", latency);
        data.put("Name of Dish",nameOfDish);
        data.put("Calories",calories);
        data.put("Picture URL", picURL);

        //store the collection data received from connectToDB method
        MongoCollection<Document> collection=connectToDB();
        Document document= new Document("name",data);
        collection.insertOne(document);

    }

    //get list
    public List<MongoDBData> getObjectList(){

        return mongoData;
    }

    //read data from collection in mongo DB
    public void readData(MongoCollection<Document> collection)  {
        MongoCursor<Document> cursor = collection.find().iterator();
        String data="";
        JSONParser parser = new JSONParser();

        while(cursor.hasNext()){
            data=cursor.next().toJson();
            try{
                JSONObject jobj = (JSONObject)parser.parse(data);
                JSONObject jobj1= (JSONObject) jobj.get("name");

                String date= (String) jobj1.get("Date");
                String start_time= (String) jobj1.get("Start Time");
                String end_time= (String) jobj1.get("End Time");
                String latency= (String) jobj1.get("Latency");
                String nameOfDish= (String) jobj1.get("Name of Dish");
                String calories= (String) jobj1.get("Calories");
                String picURL= (String) jobj1.get("Picture URL");
                MongoDBData mongoDBData= new MongoDBData(date,start_time,end_time,latency,nameOfDish,calories,picURL);
                mongoData.add(mongoDBData); //add object of type mongo DB to the list

            }
            catch (ParseException e) {
                e.printStackTrace();
            }

        }

    }
}
