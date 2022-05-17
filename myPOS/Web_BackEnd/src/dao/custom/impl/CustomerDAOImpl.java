package dao.custom.impl;

import controller.CustomerServlet;
import dao.custom.CustomerDAO;
import entity.Customer;

import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public List<Customer> getAllCustomers() throws SQLException {

        Connection connection = CustomerServlet.dataSource.getConnection();
        ResultSet rst = connection.prepareStatement("select * from Customer").executeQuery();

        List<Customer> customerDTOS = new ArrayList<>();

        while(rst.next()){
            String id = rst.getString(1);
            String name = rst.getString(2);
            String email = rst.getString(3);
            int tel = rst.getInt(4);

            Customer customer = new Customer(id,name,email,tel);
            customerDTOS.add(customer);
        }

        connection.close();

        return customerDTOS;
    }

    @Override
    public boolean addCustomer(Customer customer) throws SQLException {
        Connection connection = CustomerServlet.dataSource.getConnection();
        PreparedStatement pstm = connection.prepareStatement("Insert into Customer values(?,?,?,?)");
        pstm.setObject(1,customer.getCustomerId());
        pstm.setObject(2,customer.getCustomerName());
        pstm.setObject(3,customer.getEmail());
        pstm.setObject(4,customer.getTelNo());

        int executeUpdate = pstm.executeUpdate();
        connection.close();

        return executeUpdate > 0;
    }

    @Override
    public boolean deleteCustomer(Customer customer) throws SQLException {
        Connection connection = CustomerServlet.dataSource.getConnection();
        PreparedStatement pstm = connection.prepareStatement("Delete from Customer where id=?");
        pstm.setObject(1, customer.getCustomerId());

        int executeUpdate = pstm.executeUpdate();
        connection.close();

        return executeUpdate > 0;
    }

    @Override
    public boolean updateCustomer(Customer customer) throws SQLException {
        Connection connection = CustomerServlet.dataSource.getConnection();
        PreparedStatement pstm = connection.prepareStatement("Update Customer set name=?,email=?,telNo=? where id=?");
        pstm.setObject(1, customer.getCustomerName());
        pstm.setObject(2, customer.getEmail());
        pstm.setObject(3, customer.getTelNo());
        pstm.setObject(4, customer.getCustomerId());

        int executeUpdate = pstm.executeUpdate();
        connection.close();

        return executeUpdate > 0;
    }

    @Override
    public Customer getCustomer(String id) throws SQLException {
        Connection connection = CustomerServlet.dataSource.getConnection();
        PreparedStatement pstm = connection.prepareStatement("select * from Customer where id=?");
        pstm.setObject(1,id);

        ResultSet rst = pstm.executeQuery();

        Customer customer=null;

        if (rst.next()) {
            customer = new Customer(rst.getString(1),rst.getString(2),
                    rst.getString(3),Integer.parseInt(rst.getString(4)));
        }
        return customer;
    }
}
