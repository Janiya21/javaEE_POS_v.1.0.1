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

        return pstm.executeUpdate() > 0;
    }

    @Override
    public boolean deleteCustomer(Customer customer) {
        return false;
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        return false;
    }
}
