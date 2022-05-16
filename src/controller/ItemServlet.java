package controller;

import bo.BOFactory;
import bo.custom.impl.CustomerBOImpl;
import dto.CustomerDTO;

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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@WebServlet(urlPatterns = "/item")
public class ItemServlet extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource dataSource;

    CustomerBOImpl customerBO = (CustomerBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.CUSTOMER);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter writer = resp.getWriter();
        resp.setStatus(200);

        try {
            resp.setContentType("application/json");

            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

            List<CustomerDTO> allCustomers = customerBO.getAllCustomers();

            for (CustomerDTO ac : allCustomers) {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

                objectBuilder.add("id", ac.getCustomerId());
                objectBuilder.add("name", ac.getCustomerName());
                objectBuilder.add("email", ac.getEmail());
                objectBuilder.add("telNo", ac.getTelNo());

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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String itemCode = req.getParameter("itemCode");
        String itemName = req.getParameter("itemName");
        String unitPrice = req.getParameter("unitPrice");
        String qty = req.getParameter("qty");

        PrintWriter writer = resp.getWriter();

        resp.setContentType("application/json");

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement pstm = connection.prepareStatement("Insert into Item values(?,?,?,?)");
            pstm.setObject(1,itemCode);
            pstm.setObject(2,itemName);
            pstm.setObject(3,unitPrice);
            pstm.setObject(4,qty);

            if(pstm.executeUpdate() > 0){
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                resp.setStatus(HttpServletResponse.SC_CREATED);
                objectBuilder.add("status", 200);
                objectBuilder.add("message", "Item Successfully Added");
                objectBuilder.add("data", "");
                writer.print(objectBuilder.build());
            }
            connection.close();

        } catch (SQLException throwables) {
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status", 400);
            response.add("message", "Error");
            response.add("data", throwables.getLocalizedMessage());
            writer.print(response.build());
            resp.setStatus(HttpServletResponse.SC_OK);
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String itemID = req.getParameter("itemID");
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement pstm = connection.prepareStatement("Delete from item where itemCode=?");
            pstm.setObject(1, itemID);

            if (pstm.executeUpdate() > 0) {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("status", 200);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Successfully Deleted");
                writer.print(objectBuilder.build());
            } else {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("status", 400);
                objectBuilder.add("data", "Wrong Id Inserted");
                objectBuilder.add("message", "");
                writer.print(objectBuilder.build());
            }
            connection.close();

        } catch (SQLException throwables) {
            resp.setStatus(200);
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("status", 500);
            objectBuilder.add("message", "Error");
            objectBuilder.add("data", throwables.getLocalizedMessage());
            writer.print(objectBuilder.build());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();

        String itemCode = jsonObject.getString("id");
        String itemName = jsonObject.getString("name");
        String unitPrice = jsonObject.getString("price");
        String qty = jsonObject.getString("qty");

        System.out.println(itemCode + " " + itemName + " " + unitPrice + " " + qty);

        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement pstm = connection.prepareStatement("Update Item set name=?,unitPrice=?,qty=? where itemCode=?");
            pstm.setObject(1, itemName);
            pstm.setObject(2, unitPrice);
            pstm.setObject(3, qty);
            pstm.setObject(4, itemCode);

            if (pstm.executeUpdate() > 0) {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("status", 200);
                objectBuilder.add("message", "Successfully Updated");
                objectBuilder.add("data", "");
                writer.print(objectBuilder.build());
            } else {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("status", 400);
                objectBuilder.add("message", "Update Failed");
                objectBuilder.add("data", "");
                writer.print(objectBuilder.build());
            }
            connection.close();
        } catch (SQLException throwables) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("status", 500);
            objectBuilder.add("message", "Update Failed");
            objectBuilder.add("data", throwables.getLocalizedMessage());
            writer.print(objectBuilder.build());
        }
    }
}
