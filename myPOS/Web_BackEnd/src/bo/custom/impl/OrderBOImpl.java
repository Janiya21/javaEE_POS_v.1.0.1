package bo.custom.impl;

import bo.custom.OrderBO;
import dao.DAOFactory;
import dao.custom.impl.ItemDAOImpl;
import dao.custom.impl.OrderDAOImpl;
import dto.OrderDTO;
import entity.Item;
import entity.Orders;

import java.sql.SQLException;

public class OrderBOImpl implements OrderBO {

    OrderDAOImpl orderDAO = (OrderDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDER);

    @Override
    public boolean addOrder(OrderDTO orderDTO) throws SQLException {
        Orders orders = new Orders(orderDTO.getOrderID(),orderDTO.getCustomerID(),orderDTO.getDate(),orderDTO.getDiscount(),orderDTO.getTotal());
        return orderDAO.addOrder(orders);
    }
}
