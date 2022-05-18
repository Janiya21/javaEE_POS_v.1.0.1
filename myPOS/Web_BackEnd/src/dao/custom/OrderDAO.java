package dao.custom;

import dao.SuperDAO;
import entity.Orders;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface OrderDAO extends SuperDAO {
    public boolean addOrder(Orders order) throws SQLException;
    public String getLastID() throws SQLException;
    public List<Orders> getAllOrders() throws SQLException;
}
