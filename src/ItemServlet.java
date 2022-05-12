import javax.annotation.Resource;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@WebServlet(urlPatterns = "/item")
public class ItemServlet extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource dataSource;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter writer = resp.getWriter();

        try {
            resp.setContentType("application/json");
            Connection connection = dataSource.getConnection();

            ResultSet rst = connection.prepareStatement("select * from Item").executeQuery();
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

            while (rst.next()){
                String id = rst.getString(1);
                String name = rst.getString(2);
                Double price = rst.getDouble(3);
                int qty = rst.getInt(4);

                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

                objectBuilder.add("code", id);
                objectBuilder.add("name", name);
                objectBuilder.add("price", price);
                objectBuilder.add("qty", qty);

                arrayBuilder.add(objectBuilder.build());
            }

            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status", 200);
            response.add("message", "Done");
            response.add("data", arrayBuilder.build());
            writer.print(response.build());

            connection.close();

        } catch (SQLException throwables) {
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status", 400);
            response.add("message", "Error");
            response.add("data", throwables.getLocalizedMessage());
            writer.print(response.build());
            resp.setStatus(HttpServletResponse.SC_OK); //200
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String itemCode = req.getParameter("itemCode");
        String itemName = req.getParameter("itemName");
        String unitPrice = req.getParameter("unitPrice");
        String qty = req.getParameter("qty");

        PrintWriter writer = resp.getWriter();

        resp.setContentType("application/json");

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement pstm = connection.prepareStatement("Insert into Item values(?,?,?,?)");
            pstm.setObject(1,itemCode);
            pstm.setObject(2,itemName);
            pstm.setObject(3,unitPrice);
            pstm.setObject(4,qty);

            if(pstm.executeUpdate() > 0){
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                resp.setStatus(HttpServletResponse.SC_CREATED);
                objectBuilder.add("status", 200);
                objectBuilder.add("message", "Item Successfully Added");
                objectBuilder.add("data", "");
                writer.print(objectBuilder.build());
            }
            connection.close();

        } catch (SQLException throwables) {
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status", 400);
            response.add("message", "Error");
            response.add("data", throwables.getLocalizedMessage());
            writer.print(response.build());
            resp.setStatus(HttpServletResponse.SC_OK);
            throwables.printStackTrace();
        }
    }
}
