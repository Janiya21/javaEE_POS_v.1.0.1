package dao.custom;

import entity.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO {
    public List<Customer> getAllCustomers() throws SQLException;
}
