package bo.custom.impl;

import bo.custom.CustomerBO;
import dao.DAOFactory;
import dao.custom.impl.CustomerDAOImpl;
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

    CustomerDAOImpl customerDAO = (CustomerDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CUSTOMER);

    @Override
    public List<CustomerDTO> getAllCustomers() throws SQLException {
        List<Customer> allCustomers = customerDAO.getAllCustomers();

        ArrayList<CustomerDTO> customerDTOS = new ArrayList<>();

        for (Customer customer :  allCustomers) {

            System.out.println(customer.getCustomerId()+ customer.getCustomerName()+ customer.getEmail()+ customer.getTelNo());

            customerDTOS.add(new CustomerDTO(
                    customer.getCustomerId(),
                    customer.getCustomerName(),
                    customer.getEmail(),
                    customer.getTelNo()
            ));
        }
        return customerDTOS;
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
