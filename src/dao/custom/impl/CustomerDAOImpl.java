package dao.custom.impl;

import dao.custom.CustomerDAO;
import entity.Customer;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {

    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource dataSource;

    @Override
    public List<Customer> getAllCustomers() throws SQLException {
        Connection connection = dataSource.getConnection();
        ResultSet rst = connection.prepareStatement("select * from Customer").executeQuery();

        List<Customer> customerDTOS = null;

        while(rst.next()){
            String id = rst.getString(1);
            String name = rst.getString(2);
            String email = rst.getString(3);
            int tel = rst.getInt(4);

            Customer customer = new Customer(id,name,email,tel);

            assert false;
            customerDTOS.add(customer);
        }
    }
}
