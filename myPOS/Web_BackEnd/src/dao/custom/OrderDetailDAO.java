package dao.custom;

import dao.SuperDAO;
import entity.OrderDetail;
import entity.Orders;

import java.sql.SQLException;

public interface OrderDetailDAO extends SuperDAO {
    public boolean addOrder(OrderDetail orderDetail) throws SQLException;
}
