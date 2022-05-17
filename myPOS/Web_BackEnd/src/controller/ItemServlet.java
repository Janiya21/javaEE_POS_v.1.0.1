package controller;

import bo.BOFactory;
import bo.custom.impl.CustomerBOImpl;
import bo.custom.impl.ItemBOImpl;
import dto.CustomerDTO;
import dto.ItemDTO;
import entity.Item;

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
    public static DataSource dataSource;

    ItemBOImpl itemBO = (ItemBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.ITEM);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String option = req.getParameter("option");

        resp.setContentType("application/json");

        PrintWriter writer = resp.getWriter();
        resp.setStatus(200);

        switch (option){
            case "GETIDS":
                try {
                    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

                    List<ItemDTO> allItems = itemBO.getAllItems();

                    for (ItemDTO ic : allItems) {
                        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

                        objectBuilder.add("code", ic.getItemCode());

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
                break;

            case "GETALL":
                try {
                    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

                    List<ItemDTO> allItems = itemBO.getAllItems();

                    for (ItemDTO ic : allItems) {
                        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

                        objectBuilder.add("code", ic.getItemCode());
                        objectBuilder.add("name", ic.getItemName());
                        objectBuilder.add("price", ic.getUnitPrice());
                        objectBuilder.add("qty", ic.getQty());

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
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String itemCode = req.getParameter("itemCode");
        String itemName = req.getParameter("itemName");
        String unitPrice = req.getParameter("unitPrice");
        String qty = req.getParameter("qty");

        ItemDTO itemDTO = new ItemDTO(itemCode,itemName,Double.parseDouble(unitPrice),Integer.parseInt(qty));

        PrintWriter writer = resp.getWriter();

        resp.setContentType("application/json");

        try {
            boolean added = itemBO.addItem(itemDTO);
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            resp.setStatus(HttpServletResponse.SC_CREATED);

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

        ItemDTO itemDTO = new ItemDTO(itemID,null,0.0,0);

        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        resp.setStatus(200);
        try {
            if (itemBO.deleteItem(itemDTO)){
                objectBuilder.add("status", 200);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Successfully Deleted");
            }else {
                objectBuilder.add("status", 400);
                objectBuilder.add("data", "Wrong Id Inserted");
                objectBuilder.add("message", "");
            }
            writer.print(objectBuilder.build());
        } catch (SQLException throwables) {
            objectBuilder.add("status", 500);
            objectBuilder.add("message", "Error");
            objectBuilder.add("data", throwables.getLocalizedMessage());
            writer.print(objectBuilder.build());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String itemCode = jsonObject.getString("id");
        String itemName = jsonObject.getString("name");
        String unitPrice = jsonObject.getString("price");
        String qty = jsonObject.getString("qty");

        System.out.println(itemCode + " " + itemName + " " + unitPrice + " " + qty + ".....");

        ItemDTO itemDTO = new ItemDTO(itemCode,itemName,Double.parseDouble(unitPrice),Integer.parseInt(qty));

        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        resp.setStatus(200);

        try {
            if (itemBO.updateItem(itemDTO)) {
                objectBuilder.add("status", 200);
                objectBuilder.add("message", "Successfully Updated");
                objectBuilder.add("data", "");
            } else {
                objectBuilder.add("status", 400);
                objectBuilder.add("message", "Update Failed");
                objectBuilder.add("data", "");
            }
            writer.print(objectBuilder.build());
        } catch (SQLException throwables) {
            objectBuilder.add("status", 500);
            objectBuilder.add("message", "Update Failed");
            objectBuilder.add("data", throwables.getLocalizedMessage());
            writer.print(objectBuilder.build());
        }
    }
}
