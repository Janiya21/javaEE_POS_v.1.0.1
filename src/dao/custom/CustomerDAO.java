package dao.custom;

import dao.SuperDAO;
import entity.Customer;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO extends SuperDAO {
    public List<Customer> getAllCustomers() throws SQLException;
    public boolean addCustomer(Customer customer) throws SQLException;
    public boolean deleteCustomer(Customer customer) throws SQLException;
    public boolean updateCustomer(Customer customer) throws SQLException;
}
