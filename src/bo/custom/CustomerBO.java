package bo.custom;

import bo.SuperBO;
import dto.CustomerDTO;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public interface CustomerBO extends SuperBO {
    public List<CustomerDTO> getAllCustomers() throws SQLException;
    public boolean addCustomer(CustomerDTO customerDTO) throws SQLException;
    public boolean deleteCustomer(CustomerDTO customerDTO) throws SQLException;
    public boolean updateCustomer(CustomerDTO customerDTO);
}
