package controller;

import bo.BOFactory;
import bo.custom.impl.CustomerBOImpl;
import dao.custom.impl.CustomerDAOImpl;
import dto.CustomerDTO;
import entity.Customer;

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


@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    public static DataSource dataSource;

    CustomerBOImpl customerBO = (CustomerBOImpl) BOFactory.getInstance().getBO(BOFactory.BOType.CUSTOMER);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String option = req.getParameter("option");
        String id = req.getParameter("id");
        System.out.println(" id " + id);

        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        resp.setStatus(200);

        switch (option){
            case "GETIDS":
                try {

                    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

                    List<CustomerDTO> allCustomers = customerBO.getAllCustomers();

                    for (CustomerDTO ac : allCustomers) {
                        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

                        objectBuilder.add("id", ac.getCustomerId());

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
                break;

            case "GETCUSTOMER":
                try {

                    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

                    CustomerDTO ac = customerBO.getCustomer(id);

                    objectBuilder.add("id", ac.getCustomerId());
                    objectBuilder.add("name", ac.getCustomerName());
                    objectBuilder.add("email", ac.getEmail());
                    objectBuilder.add("telNo", ac.getTelNo());

                    JsonObjectBuilder response = Json.createObjectBuilder();
                    response.add("status", 200);
                    response.add("message", "Done");
                    response.add("data", objectBuilder.build());
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
        String customerId = req.getParameter("customerId");
        String customerName = req.getParameter("customerName");
        String customerEmail = req.getParameter("customerEmail");
        String customerTelNo = req.getParameter("customerTelNo");

        CustomerDTO customerDTO = new CustomerDTO(customerId,customerName,customerEmail,Integer.parseInt(customerTelNo));

        PrintWriter writer = resp.getWriter();

        resp.setContentType("application/json");

        try {
            boolean added = customerBO.addCustomer(customerDTO);
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
            response.add("status", 500);
            response.add("message", "Error");
            response.add("data", throwables.getLocalizedMessage());
            writer.print(response.build());
            resp.setStatus(HttpServletResponse.SC_OK);
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cusID = req.getParameter("CusID");
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        CustomerDTO customerDTO = new CustomerDTO(cusID,null,null,0);

        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        resp.setStatus(200);
        try {
            if (customerBO.deleteCustomer(customerDTO)){
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
        String customerID = jsonObject.getString("id");
        String customerName = jsonObject.getString("name");
        String customerAddress = jsonObject.getString("address");
        String customerSalary = jsonObject.getString("salary");

        CustomerDTO customerDTO = new CustomerDTO(customerID,customerName,customerAddress,Integer.parseInt(customerSalary));

        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        resp.setStatus(200);

        try {
            if (customerBO.updateCustomer(customerDTO)) {
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
