package dao.custom.impl;

import controller.CustomerServlet;
import dao.custom.ItemDAO;
import entity.Customer;
import entity.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {

    @Override
    public List<Item> getAllItems() throws SQLException {
        Connection connection = CustomerServlet.dataSource.getConnection();
        ResultSet rst = connection.prepareStatement("select * from Item").executeQuery();

        List<Item> ItemDTOS = new ArrayList<>();

        while(rst.next()){
            String code = rst.getString(1);
            String name = rst.getString(2);
            double price = rst.getDouble(3);
            int qty = rst.getInt(4);

            Item item = new Item(code,name,price,qty);
            ItemDTOS.add(item);
        }

        connection.close();

        return ItemDTOS;
    }

    @Override
    public boolean addItem(Item item) throws SQLException {
        Connection connection = CustomerServlet.dataSource.getConnection();
        PreparedStatement pstm = connection.prepareStatement("Insert into Item values(?,?,?,?)");
        pstm.setObject(1,item.getItemCode());
        pstm.setObject(2,item.getItemName());
        pstm.setObject(3,item.getUnitPrice());
        pstm.setObject(4,item.getQty());

        int executeUpdate = pstm.executeUpdate();
        connection.close();

        return executeUpdate > 0;
    }

    @Override
    public boolean deleteItem(Item item) throws SQLException {
        Connection connection = CustomerServlet.dataSource.getConnection();
        PreparedStatement pstm = connection.prepareStatement("Delete from Item where id=?");
        pstm.setObject(1, item.getItemCode());

        int executeUpdate = pstm.executeUpdate();
        connection.close();

        return executeUpdate > 0;
    }

    @Override
    public boolean updateItem(Item item) throws SQLException {
        Connection connection = CustomerServlet.dataSource.getConnection();
        PreparedStatement pstm = connection.prepareStatement("Update Item set name=?,unitPrice=?,qty=? where itemCode=?");
        pstm.setObject(1, item.getItemCode());
        pstm.setObject(2, item.getItemName());
        pstm.setObject(3, item.getUnitPrice());
        pstm.setObject(4, item.getQty());

        int executeUpdate = pstm.executeUpdate();
        connection.close();

        return executeUpdate > 0;
    }
}
