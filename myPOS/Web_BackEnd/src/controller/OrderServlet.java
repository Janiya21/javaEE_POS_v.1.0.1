package controller;

import bo.BOFactory;
import bo.custom.impl.CustomerBOImpl;
import bo.custom.impl.OrderBOImpl;
import dto.CustomerDTO;
import dto.OrderDTO;

import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/order")
public class OrderServlet  extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    public static DataSource dataSource;

    OrderBOImpl orderBO = (OrderBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.ORDER);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();

        String orderId = jsonObject.getString("orderId");
        String cusId = jsonObject.getString("cusId");
        String date = jsonObject.getString("date");
        int dis = jsonObject.getInt("dis");
        int total = jsonObject.getInt("total");

        OrderDTO orderDTO = new OrderDTO(orderId,cusId, Date.valueOf(date),Double.parseDouble(String.valueOf(dis)),Double.parseDouble(String.valueOf(total)));

        PrintWriter writer = resp.getWriter();

        resp.setContentType("application/json");
        resp.setStatus(200);

        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

        try {
            boolean added = orderBO.addOrder(orderDTO);

            if(added){
                objectBuilder.add("status", 200);
                objectBuilder.add("message", "Successfully Added");
            }else{
                objectBuilder.add("status", 400);
                objectBuilder.add("message", "Added Not Successful !!");
            }
            objectBuilder.add("data", "");
            writer.print(objectBuilder.build());

        } catch (SQLException throwables) {
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status", 500);
            response.add("message", "Error");
            response.add("data", throwables.getLocalizedMessage());
            writer.print(response.build());
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();

        resp.setContentType("application/json");
        resp.setStatus(200);

        try {
            String lastID = orderBO.getLastID();

            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("lastId",lastID);
            writer.print(objectBuilder.build());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
