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
import java.sql.ResultSet;
import java.sql.SQLException;


@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {

    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource dataSource;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter writer = resp.getWriter();

        try {
            resp.setContentType("application/json");
            Connection connection = dataSource.getConnection();

            ResultSet rst = connection.prepareStatement("select * from Customer").executeQuery();
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

            while (rst.next()){
                String id = rst.getString(1);
                String name = rst.getString(2);
                String email = rst.getString(3);
                int tel = rst.getInt(4);

                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

                objectBuilder.add("id", id);
                objectBuilder.add("name", name);
                objectBuilder.add("email", email);
                objectBuilder.add("telNo", tel);

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
}
