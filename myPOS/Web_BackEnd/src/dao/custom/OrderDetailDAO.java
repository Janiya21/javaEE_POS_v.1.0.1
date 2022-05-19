package dao.custom;

import dao.SuperDAO;
import entity.OrderDetail;
import entity.Orders;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailDAO extends SuperDAO {
    public boolean addOrder(OrderDetail orderDetail) throws SQLException;
    public List<OrderDetail> getOrders(String id) throws SQLException;
}
