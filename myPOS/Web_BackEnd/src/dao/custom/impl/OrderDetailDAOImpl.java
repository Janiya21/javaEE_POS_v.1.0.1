package dao.custom.impl;

import controller.CustomerServlet;
import controller.OrderDetailServlet;
import controller.OrderServlet;
import dao.custom.OrderDetailDAO;
import entity.OrderDetail;
import entity.Orders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Override
    public List<OrderDetail> getOrders(String id) throws SQLException {
        Connection connection = CustomerServlet.dataSource.getConnection();
        ResultSet rst = connection.prepareStatement("select * from OrderDetails where OrderID='"+id+"'").executeQuery();

        List<OrderDetail> ordersList = new ArrayList<>();

        while(rst.next()){
            String orderID = rst.getString(1);
            String itemCode = rst.getString(2);
            int qty = rst.getInt(3);
            double uPrice = rst.getDouble(4);
            double tot = rst.getDouble(5);

            OrderDetail orderDetail = new OrderDetail(orderID, itemCode, qty, uPrice, tot);
            ordersList.add(orderDetail);
        }

        connection.close();

        return ordersList;
    }
}
