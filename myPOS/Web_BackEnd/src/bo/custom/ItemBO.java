package bo.custom;

import bo.SuperBO;
import dto.CustomerDTO;
import dto.ItemDTO;

import java.sql.SQLException;
import java.util.List;

public interface ItemBO extends SuperBO {
    public List<ItemDTO> getAllItems() throws SQLException;
    public boolean addItem(ItemDTO itemDTO) throws SQLException;
    public boolean deleteItem(ItemDTO itemDTO) throws SQLException;
    public boolean updateItem(ItemDTO itemDTO) throws SQLException;
    public ItemDTO getItem(String id) throws SQLException;
    public String getID() throws SQLException;
}
