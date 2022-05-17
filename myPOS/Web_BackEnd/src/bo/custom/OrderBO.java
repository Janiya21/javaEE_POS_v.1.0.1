package bo.custom;

import bo.SuperBO;
import dto.OrderDTO;
import entity.Orders;

import java.sql.SQLException;

public interface OrderBO extends SuperBO {
    public boolean addOrder(OrderDTO orderDTO) throws SQLException;
}
