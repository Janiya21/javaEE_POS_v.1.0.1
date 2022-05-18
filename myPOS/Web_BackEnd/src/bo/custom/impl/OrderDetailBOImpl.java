package bo.custom.impl;

import bo.custom.OrderDetailBO;
import dao.DAOFactory;
import dao.custom.OrderDetailDAO;
import dao.custom.impl.OrderDAOImpl;
import dto.OrderDetailDTO;
import entity.OrderDetail;
import entity.Orders;

import java.sql.SQLException;

public class OrderDetailBOImpl implements OrderDetailBO {
    OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDERDETAIL);

    @Override
    public boolean addOrder(OrderDetailDTO od) throws SQLException {
        OrderDetail orderDetail = new OrderDetail(od.getOrderID(), od.getItemCode(), od.getQty(), od.getUnitPrice(), od.getTotal());
        return orderDetailDAO.addOrder(orderDetail);
    }
}
