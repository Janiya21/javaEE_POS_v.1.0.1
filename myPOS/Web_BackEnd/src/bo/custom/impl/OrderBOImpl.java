package bo.custom.impl;

import bo.custom.OrderBO;
import dao.DAOFactory;
import dao.custom.impl.ItemDAOImpl;
import dao.custom.impl.OrderDAOImpl;
import dto.ItemDTO;
import dto.OrderDTO;
import entity.Item;
import entity.Orders;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderBOImpl implements OrderBO {

    OrderDAOImpl orderDAO = (OrderDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDER);

    @Override
    public boolean addOrder(OrderDTO orderDTO) throws SQLException {
        Orders orders = new Orders(orderDTO.getOrderID(),orderDTO.getCustomerID(),orderDTO.getDate(),orderDTO.getDiscount(),orderDTO.getTotal());
        return orderDAO.addOrder(orders);
    }

    @Override
    public String getLastID() throws SQLException {
        return orderDAO.getLastID();
    }

    @Override
    public List<OrderDTO> getAllOrders() throws SQLException {
        List<Orders> allOrders = orderDAO.getAllOrders();

        ArrayList<OrderDTO> orderDTOS = new ArrayList<>();

        for (Orders os :  allOrders) {

            orderDTOS.add(new OrderDTO(
                    os.getOrderID(),os.getCustomerID(),os.getDate(),os.getDiscount(),os.getTotal()
            ));
        }
        return orderDTOS;
    }
}
