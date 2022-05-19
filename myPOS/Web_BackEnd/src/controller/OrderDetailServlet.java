package controller;

import bo.BOFactory;
import bo.custom.impl.OrderBOImpl;
import bo.custom.impl.OrderDetailBOImpl;
import dto.OrderDTO;
import dto.OrderDetailDTO;

import javax.annotation.Resource;
import javax.json.*;
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
import java.util.List;

@WebServlet(urlPatterns = "/placedorders")
public class OrderDetailServlet  extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    public static DataSource dataSource;

    OrderDetailBOImpl orderDetailBO = (OrderDetailBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.ORDERDETAIL);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();

        String orderId = jsonObject.getString("orderId");
        String itemCode = jsonObject.getString("itemCode");
        String quantity = jsonObject.getString("quantity");
        String uPrice = jsonObject.getString("uPrice");
        int total = jsonObject.getInt("total");


        OrderDetailDTO orderDetailDTO = new OrderDetailDTO(orderId,itemCode,Integer.parseInt(quantity),
                Double.parseDouble(uPrice),Double.parseDouble(String.valueOf(total)));

        PrintWriter writer = resp.getWriter();

        resp.setContentType("application/json");
        resp.setStatus(HttpServletResponse.SC_CREATED);

        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

        try {
            boolean added = orderDetailBO.addOrder(orderDetailDTO);

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
            objectBuilder.add("status", 500);
            objectBuilder.add("message", "Error");
            objectBuilder.add("data", throwables.getLocalizedMessage());
            writer.print(objectBuilder.build());
            resp.setStatus(HttpServletResponse.SC_OK);
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cusID = req.getParameter("cusID");
        PrintWriter writer = resp.getWriter();

        resp.setContentType("application/json");
        resp.setStatus(200);

        try {

            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

            List<OrderDetailDTO> allOrders = orderDetailBO.getOrders(cusID);

            for (OrderDetailDTO ac : allOrders) {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

                objectBuilder.add("orderId", ac.getOrderID());
                objectBuilder.add("itemCode", ac.getItemCode());
                objectBuilder.add("qty", ac.getQty());
                objectBuilder.add("unitPrice", ac.getUnitPrice());
                objectBuilder.add("total", ac.getTotal());

                arrayBuilder.add(objectBuilder.build());
            }

            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status", 200);
            response.add("message", "Done");
            response.add("data", arrayBuilder.build());
            writer.print(response.build());

        } catch (SQLException throwables) {
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status", 400);
            response.add("message", "Error");
            response.add("data", throwables.getLocalizedMessage());
            writer.print(response.build());
            throwables.printStackTrace();
        }
    }
}
