<%@ page import="java.util.List" %>
<%@ page import="edu.cmu.diksha.MongoDBData" %><%--
  Created by IntelliJ IDEA.
  User: dikshabaruah
  Date: 11/13/20
  Time: 6:41 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Recipe Search App Dashboard</title>
<%--    Code taken from: https://www.w3schools.com/html/tryit.asp?filename=tryhtml_table_intro--%>
    <style>
      table {
        font-family: arial, sans-serif;
        border-collapse: collapse;
        width: 100%;
      }

      td, th {
        border: 1px solid #dddddd;
        text-align: left;
        padding: 8px;
      }

      tr:nth-child(even) {
        background-color: #dddddd;
      }
    </style>
  </head>
  <body>
  <h1>RECIPE SEARCH APP DASHBOARD AND ANALYTICS</h1>
  <p>Most popular recipe: <%=request.getAttribute("mostPopular")%></p>
  <p>Number of times <%=request.getAttribute("mostPopular")%> was searched: <%=request.getAttribute("popularityCount")%> times!!</p>
  <p> Average time taken to fetch result: <%=request.getAttribute("avgSearchTime")%> seconds</p>

<%--  Form the tables as per entries in the arraylist defined in MongoDBFoodSearch--%>
  <table>
    <tr>
      <th>Date</th>
      <th>Start Time</th>
      <th>End Time</th>
      <th>Latency</th>
      <th>Name Of Dish</th>
      <th>Calories</th>
      <th>Picture URL</th>
    </tr>
    <%
      List<MongoDBData> objList= (List<MongoDBData>) request.getAttribute("objList");
    %>
    <%
      for(MongoDBData obj: objList) {
    %>
    <tr>
      <td><%= obj.getDate()%>
      </td>
      <td><%= obj.getStartTime()%>
      </td>
      <td><%= obj.getEndTime()%>
      </td>
      <td><%= obj.getLatency()%>
      </td>
      <td><%= obj.getNameOfDish()%>
      </td>
      <td><%= obj.getCalories()%>
      </td>
      <td><%= obj.getPicURL()%>
      </td>
    </tr>
    <%
      }
    %>

  </table>
  </body>
</html>
