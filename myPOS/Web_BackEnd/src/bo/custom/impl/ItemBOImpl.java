package bo.custom.impl;

import bo.custom.ItemBO;
import dao.DAOFactory;
import dao.custom.impl.CustomerDAOImpl;
import dao.custom.impl.ItemDAOImpl;
import dto.CustomerDTO;
import dto.ItemDTO;
import entity.Customer;
import entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemBOImpl implements ItemBO {

    ItemDAOImpl itemDAO = (ItemDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ITEM);

    @Override
    public List<ItemDTO> getAllItems() throws SQLException {
        List<Item> allCustomers = itemDAO.getAllItems();

        ArrayList<ItemDTO> itemDTOS = new ArrayList<>();

        for (Item item :  allCustomers) {

            System.out.println(item.getItemCode()+item.getItemName()+item.getUnitPrice()+item.getQty());

            itemDTOS.add(new ItemDTO(
                    item.getItemCode(),item.getItemName(),item.getUnitPrice(),item.getQty()
            ));
        }
        return itemDTOS;
    }

    @Override
    public boolean addItem(ItemDTO itemDTO) throws SQLException {
        Item customer = new Item(itemDTO.getItemCode(),itemDTO.getItemName(),itemDTO.getUnitPrice(),itemDTO.getQty());
        return itemDAO.addItem(customer);
    }

    @Override
    public boolean deleteItem(ItemDTO itemDTO) throws SQLException {
        Item customer = new Item(itemDTO.getItemCode(),itemDTO.getItemName(),itemDTO.getUnitPrice(),itemDTO.getQty());
        return itemDAO.deleteItem(customer);
    }

    @Override
    public boolean updateItem(ItemDTO itemDTO) throws SQLException {
        Item customer = new Item(itemDTO.getItemCode(),itemDTO.getItemName(),itemDTO.getUnitPrice(),itemDTO.getQty());
        return itemDAO.updateItem(customer);
    }
}
