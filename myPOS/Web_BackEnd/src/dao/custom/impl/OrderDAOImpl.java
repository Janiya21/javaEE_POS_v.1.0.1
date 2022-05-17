package dao.custom.impl;

import controller.CustomerServlet;
import dao.custom.OrderDAO;
import entity.Orders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public boolean addOrder(Orders order) throws SQLException {
        Connection connection = CustomerServlet.dataSource.getConnection();
        PreparedStatement pstm = connection.prepareStatement("Insert into Orders values(?,?,?,?,?)");
        pstm.setObject(1,order.getOrderID());
        pstm.setObject(2,order.getCustomerID());
        pstm.setObject(3,order.getDate());
        pstm.setObject(4,order.getDiscount());
        pstm.setObject(4,order.getTotal());

        int executeUpdate = pstm.executeUpdate();
        connection.close();

        return executeUpdate > 0;
    }
}
