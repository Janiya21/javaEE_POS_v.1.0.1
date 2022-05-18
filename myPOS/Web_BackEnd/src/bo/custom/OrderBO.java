package bo.custom;

import bo.SuperBO;
import dto.OrderDTO;
import entity.Orders;

import java.sql.SQLException;
import java.util.List;

public interface OrderBO extends SuperBO {
    public boolean addOrder(OrderDTO orderDTO) throws SQLException;
    public String getLastID() throws SQLException;
    public List<OrderDTO> getAllOrders() throws SQLException;
}
