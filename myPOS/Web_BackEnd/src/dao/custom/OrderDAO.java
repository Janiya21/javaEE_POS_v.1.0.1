package dao.custom;

import dao.SuperDAO;
import entity.Orders;

import java.sql.SQLException;

public interface OrderDAO extends SuperDAO {
    public boolean addOrder(Orders order) throws SQLException;
    public String getLastID() throws SQLException;
}
