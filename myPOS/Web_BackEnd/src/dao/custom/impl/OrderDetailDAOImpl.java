package dao.custom.impl;

import controller.CustomerServlet;
import controller.OrderDetailServlet;
import controller.OrderServlet;
import dao.custom.OrderDetailDAO;
import entity.OrderDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderDetailDAOImpl implements OrderDetailDAO {
    @Override
    public boolean addOrder(OrderDetail orderDetail) throws SQLException {
        Connection connection = OrderServlet.dataSource.getConnection();
        PreparedStatement pstm = connection.prepareStatement("Insert into OrderDetails values(?,?,?,?,?)");
        pstm.setObject(1,orderDetail.getOrderID());
        pstm.setObject(2,orderDetail.getItemCode());
        pstm.setObject(3,orderDetail.getQty());
        pstm.setObject(4,orderDetail.getUnitPrice());
        pstm.setObject(5,orderDetail.getTotal());

        int executeUpdate = pstm.executeUpdate();
        connection.close();

        System.out.println("orderDetail eka db ekata avoo");

        return executeUpdate > 0;
    }
}
