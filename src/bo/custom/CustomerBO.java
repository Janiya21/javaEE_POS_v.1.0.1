package bo.custom;

import bo.SuperBO;
import dto.CustomerDTO;

import java.sql.SQLException;
import java.util.List;

public interface CustomerBO extends SuperBO {
    public List<CustomerDTO> getAllCustomers() throws SQLException;
    public boolean addCustomer(CustomerDTO customerDTO);
    public boolean deleteCustomer(CustomerDTO customerDTO);
    public boolean updateCustomer(CustomerDTO customerDTO);
}
