package edu.cmu.diksha;
//Diksha Baruah
//andrew ID: dboruah

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

//this is the servlet class that consists of the GET response
@WebServlet(name = "FoodAppServlet",urlPatterns = {"/getFoodAppServlet1"})
public class FoodAppServlet extends javax.servlet.http.HttpServlet {

    FoodSearchModel foodSearchModel = null;
    MongoDBModel mongoDBModel =new MongoDBModel();


    // Initiate this servlet by instantiating the model that it will use.
    @Override
    public void init() {
        foodSearchModel = new FoodSearchModel();
    }

    // This servlet will reply to HTTP GET requests via this doGet method
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // get the search parameter if it exists
        String search = request.getParameter("searchWord");
        System.out.println(search);
        if (search != null) {

            String startTime = new Timestamp(System.currentTimeMillis()).toString(); //timestamp consisting of time when search is made
            long stTime=System.currentTimeMillis();
            String jsonResult = foodSearchModel.doEdamamSearch(search); //makes the search by receiving json response from Edamam API
            long eTime=System.currentTimeMillis();
            String endTime = new Timestamp(System.currentTimeMillis()).toString(); //timestamp consisting of time when response is received and search request is completed
            String latency= String.valueOf(eTime-stTime);
            String date= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()); //consists of date and time when search is made

            JSONParser parser = new JSONParser();

            //insert data obtained from this web service to mongo DB
            try {
                JSONObject jobj = (JSONObject)parser.parse(jsonResult);
                String nameOfDish= (String) jobj.get("nameOfDish");
                String calories= (String) jobj.get("calories");
                String picURL= (String) jobj.get("image");
                mongoDBModel.insert(date,startTime,endTime,latency,nameOfDish,calories,picURL);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            System.out.println(jsonResult);

            PrintWriter out = response.getWriter();
            out.println(jsonResult);

        } else {
            System.out.println("No search word entered");

        }

    }
}