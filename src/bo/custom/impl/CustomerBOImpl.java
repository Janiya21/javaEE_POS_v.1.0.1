package bo.custom.impl;

import bo.custom.CustomerBO;
import dto.CustomerDTO;
import entity.Customer;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {

    @Override
    public List<CustomerDTO> getAllCustomers() throws SQLException {
        return null;
    }

    @Override
    public boolean addCustomer(CustomerDTO customerDTO) {
        return false;
    }

    @Override
    public boolean deleteCustomer(CustomerDTO customerDTO) {
        return false;
    }

    @Override
    public boolean updateCustomer(CustomerDTO customerDTO) {
        return false;
    }
}
