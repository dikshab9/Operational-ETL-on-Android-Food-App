//Diksha Baruah
//andrew ID: dboruah
package edu.cmu.diksha;

import org.bson.Document;
import com.mongodb.client.MongoCollection;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.*;

//this class is used to display the analytical insights and dashboard in the browser
@WebServlet(name = "MongoDBServlet",urlPatterns = {"/getDashboard"})
public class MongoDBServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {


        MongoDBModel mongoDBModel = new MongoDBModel();
        MongoCollection<Document> collection= mongoDBModel.connectToDB(); //connectToDB returns the collection present in db
        //read data from this collection
        mongoDBModel.readData(collection);
        List<MongoDBData> objList= mongoDBModel.getObjectList(); //get array list of object of type mongoDB data
        request.setAttribute("objList",objList);

        //analytics
        //find the most popular search
        //find average search time
        Map<String,Integer> popularitySearch= new HashMap<>();
        int totalLatency=0;
        for(int i=0; i<objList.size();i++){
            totalLatency+=Integer.parseInt(objList.get(i).latency); //sum of latency fr each mongoDB Data object
            String name=objList.get(i).getNameOfDish();
            if(popularitySearch.containsKey(name)){
                int count= popularitySearch.get(name);
                count++;
                popularitySearch.put(name,count);
            }else{
                popularitySearch.put(name,1);
            }
        }

        String popularWord=""; //food recipe keyword that is most searched for
        int popularityCount=0; //number of times most popular word is searched for
        String avgSearchTime; //average search time taken to make the search
        if(popularitySearch.size()!=0){
             popularityCount= Collections.max(popularitySearch.values()); //maximum value of count
            //traverse through the map to find most popular word with highest popularity count
            for(Map.Entry<String, Integer> entry:popularitySearch.entrySet()){
                if(entry.getValue().equals(popularityCount)) //if value in map equals highest count for a search word
                {
                    popularWord=entry.getKey();
                }
            }
             avgSearchTime= String.valueOf(totalLatency/objList.size());
            //set attributes in View.jsp
            request.setAttribute("mostPopular",popularWord);
            request.setAttribute("popularityCount",popularityCount);
            request.setAttribute("avgSearchTime",avgSearchTime);


            //dispatch results to next view
            String nextView="";
            if(request.getServletPath().equals("/getDashboard")){
                nextView = "view.jsp";
                RequestDispatcher requestDispatcher =  request.getRequestDispatcher(nextView);
                requestDispatcher.forward(request, response);
            }
        }

    }
}
