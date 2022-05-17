package dao.custom;

import dao.SuperDAO;
import entity.Customer;
import entity.Item;

import java.sql.SQLException;
import java.util.List;

public interface ItemDAO extends SuperDAO {
    public List<Item> getAllItems() throws SQLException;
    public boolean addItem(Item item) throws SQLException;
    public boolean deleteItem(Item item) throws SQLException;
    public boolean updateItem(Item item) throws SQLException;
}
