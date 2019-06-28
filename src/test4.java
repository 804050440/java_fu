
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;//insert 用到
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by fql on 2018/12/17.
 * 数据库连接-跨表传输数据
 */

public class test4 {
    public static void main(String[] args) {
     //   Runnable runnable = new Runnable() {

                //声明Connection对象
                Connection con;

                //声明Connection对象
                Connection con1;

                //遍历查询结果集
                try {
                    //加载驱动程序
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    //1.getConnection()方法，连接MySQL数据库！！
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mixcall?user=root&password=1&useUnicode=true&characterEncoding=UTF8&serverTimezone=GMT%2B8");
                    if(!con.isClosed())
                        System.out.println("成功连接到数据库!");

                    try {
                        //加载驱动程序
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        //1.getConnection()方法，连接MySQL数据库！！
                        con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/mixcall?user=root&password=1&useUnicode=true&characterEncoding=UTF8&serverTimezone=GMT%2B8");
                        if(!con1.isClosed())
                            System.out.println("成功连接到数据库!");


                        //2.创建statement类对象，用来执行SQL语句！！
                        //注意此处调用的还是“源”库的con，而非“目标”库的con1
                        Statement statement = con.createStatement();
                        //要执行的SQL语句
                        String sql = "select * from student";
                        //3.ResultSet类，用来存放获取的结果集！！
                        ResultSet rs = statement.executeQuery(sql);


                        //要执行的SQL语句
                        String sql1 = "insert into student2(NO,name,name2)VALUES(?,?,?)";
                        //3.ResultSet类，用来存放获取的结果集！！
                        //注意此处调用的还是“目标”库的con1，而非“源”库的con
                        PreparedStatement pst = con1.prepareStatement(sql1);


                        //输出表头
                        System.out.println ("NO"+"/t"+"name"+"/t"+"name1");
                        while(rs.next()){
                            //输出表中内容
                            System.out.print(rs.getString("NO"));
                            System.out.print(rs.getString("name"));
                            System.out.print(rs.getString("name2"));


                            //给insert语句中的第一个问号赋值
                            pst.setString(1,rs.getString(1));
                            //给insert语句中的第二个问号赋值
                            pst.setString(2,rs.getString(2));
                            //给insert语句中的第三个问号赋值
                            pst.setString(3,rs.getString(3));
                            pst.executeUpdate();

                        }
                        pst.close();
                        rs.close();
                        con1.close();
                        con.close();
                    } catch(ClassNotFoundException e) {
                        //数据库驱动类异常处理
                        System.out.println("对不起,找不到驱动程序!");
                        e.printStackTrace();
                    } catch(SQLException e) {
                        //数据库连接失败异常处理
                        e.printStackTrace();
                    }

                } catch(ClassNotFoundException e) {
                    //数据库驱动类异常处理
                    System.out.println("对不起,找不到驱动程序!");
                    e.printStackTrace();
                } catch(SQLException e) {
                    //数据库连接失败异常处理
                    e.printStackTrace();
                }

                }
            }