package dao.custom.impl;

import controller.CustomerServlet;
import controller.OrderServlet;
import dao.custom.OrderDAO;
import entity.Orders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public boolean addOrder(Orders order) throws SQLException {
        Connection connection = OrderServlet.dataSource.getConnection();
        PreparedStatement pstm = connection.prepareStatement("Insert into Orders values(?,?,?,?,?)");
        pstm.setObject(1,order.getOrderID());
        pstm.setObject(2,order.getCustomerID());
        pstm.setObject(3,order.getDate());
        pstm.setObject(4,order.getDiscount());
        pstm.setObject(5,order.getTotal());

        int executeUpdate = pstm.executeUpdate();
        connection.close();

        System.out.println("order eka database ekata avoo");

        return executeUpdate > 0;
    }

    @Override
    public String getLastID() throws SQLException {
        Connection connection = OrderServlet.dataSource.getConnection();
        ResultSet resultSet = connection.prepareStatement("SELECT OrderID FROM Orders ORDER BY OrderID DESC LIMIT 1").executeQuery();

        String string=null;

        if (resultSet.next()) {
            string = resultSet.getString(1);
        }
        connection.close();

        return string;
    }
}
