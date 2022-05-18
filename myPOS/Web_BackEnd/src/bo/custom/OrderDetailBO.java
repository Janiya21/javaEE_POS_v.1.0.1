package bo.custom;

import bo.SuperBO;
import dto.OrderDTO;
import dto.OrderDetailDTO;

import java.sql.SQLException;

public interface OrderDetailBO extends SuperBO {
    public boolean addOrder(OrderDetailDTO orderDetailDTO) throws SQLException;
}
