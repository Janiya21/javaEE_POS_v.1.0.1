package bo.custom.impl;

import bo.custom.OrderDetailBO;
import dao.DAOFactory;
import dao.custom.OrderDetailDAO;
import dao.custom.impl.OrderDAOImpl;
import dto.OrderDetailDTO;
import entity.OrderDetail;
import entity.Orders;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailBOImpl implements OrderDetailBO {
    OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDERDETAIL);

    @Override
    public boolean addOrder(OrderDetailDTO od) throws SQLException {
        OrderDetail orderDetail = new OrderDetail(od.getOrderID(), od.getItemCode(), od.getQty(), od.getUnitPrice(), od.getTotal());
        return orderDetailDAO.addOrder(orderDetail);
    }

    @Override
    public List<OrderDetailDTO> getOrders(String id) throws SQLException {
        List<OrderDetail> orders = orderDetailDAO.getOrders(id);

        ArrayList<OrderDetailDTO> orderDTO = new ArrayList<>();

        for (OrderDetail or : orders) {
            OrderDetailDTO orderDetailDTO = new OrderDetailDTO(or.getOrderID(), or.getItemCode(), or.getQty(), or.getUnitPrice(), or.getTotal());

            orderDTO.add(orderDetailDTO);
        }

        return orderDTO;
    }
}
