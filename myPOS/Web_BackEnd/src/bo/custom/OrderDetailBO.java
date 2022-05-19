package bo.custom;

import bo.SuperBO;
import dto.OrderDTO;
import dto.OrderDetailDTO;
import entity.OrderDetail;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailBO extends SuperBO {
    public boolean addOrder(OrderDetailDTO orderDetailDTO) throws SQLException;
    public List<OrderDetailDTO> getOrders(String id) throws SQLException;
}
