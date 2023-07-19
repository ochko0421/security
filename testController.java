package camunda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.model.bpmn.instance.DataObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import camunda.config.OracleConfig;

@RestController
public class testController {

    @PostMapping(value = "/testuser")
    public @ResponseBody List<test> createUser(@RequestBody test test) throws Exception {
        List<test> _test = new ArrayList<test>();
        OracleConfig dbPool = new OracleConfig("CAMUNDA");
        Connection oraConn = null;
        PreparedStatement statement = null;

        try {
            oraConn = dbPool.getConnection();
            statement = oraConn.prepareStatement(
                    "insert into act_user (first_name, last_name, age, phonenumber, email) "
                            + "VALUES(?, ?, ?, ?, ? )");
            statement.setString(1, test.getFirstName());
            statement.setString(2, test.getLastName());
            statement.setInt(3, test.getAge());
            statement.setInt(4, test.getPhoneNumber());
            statement.setString(5, test.getEmail());

            int affectedRows = statement.executeUpdate();

            if (affectedRows < 1) {
                System.out.println(affectedRows);
                return null;
            }
            test test2 = new test();
            test2.setFirstName(test.getFirstName());
            test2.setLastName(test.getLastName());
            test2.setAge(test.getAge());
            test2.setPhoneNumber(test.getPhoneNumber());
            test2.setEmail(test.getEmail());
            _test.add(test2);

        } catch (Exception e) {
            throw new Exception("firstcatch" + e.getMessage());
        } finally {
            ((OracleConfig) dbPool).close((Connection) oraConn);
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    throw new Exception("second" + e.getMessage());
                }
            }
        }
        return _test;
    }

    @RequestMapping(value = "/testuser", method = RequestMethod.GET)
    public @ResponseBody List<test> getUser() throws Exception {
        List<test> testList = new ArrayList<test>();
        OracleConfig dbPool = new OracleConfig("camunda");
        Connection oraConn = null;
        PreparedStatement statement = null;

        System.out.println("hello world");
        try {
            oraConn = dbPool.getConnection();
            statement = oraConn.prepareStatement("SELECT * FROM ACT_USER");
            ResultSet rs = statement.executeQuery();

            if (!rs.next()) {
                return null;
            }

            do {
                test _test = new test();
                _test.setFirstName(rs.getString("first_name"));
                _test.setLastName(rs.getString("last_name"));
                _test.setAge(rs.getInt("age"));
                _test.setPhoneNumber(rs.getInt("phoneNumber"));
                _test.setEmail(rs.getString("email"));
                testList.add(_test);
            } while (rs.next());

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            ((OracleConfig) dbPool).close((Connection) oraConn);
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    throw new Exception(e.getMessage());
                }
            }
        }

        return testList;

    }
}
