import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by fql on 2018/12/16.
 * 数据库连接-查询
 */

public class test2 {
    public static void main(String[] args) {
        Connection con;
        String driver="com.mysql.cj.jdbc.Driver";
        //这里我的数据库是qcl
        String url="jdbc:mysql://localhost:3306/mixcall?"
                + "user=root&password=1&useUnicode=true&characterEncoding=UTF8&serverTimezone=GMT%2B8";
            try {
            Class.forName(driver);
            con = DriverManager.getConnection(url);
            if (!con.isClosed()) {
                System.out.println("数据库连接成功");
            }
            Statement statement = con.createStatement();
            String sql = "select * from mix_admin_role;";//我的表格叫home
            ResultSet resultSet = statement.executeQuery(sql);
            String name;
            while (resultSet.next()) {
                name = resultSet.getString("rolename");
                System.out.println("姓名：" + name);
            }
            resultSet.close();
            con.close();
        } catch (ClassNotFoundException e) {
            System.out.println("数据库驱动没有安装");

        } catch (SQLException e) {
            System.out.println("数据库连接失败");
        }
    }
}