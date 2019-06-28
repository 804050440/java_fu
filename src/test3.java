
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by fql on 2018/12/17.
 * 数据库连接-test4原版，不能执行
 */

public class  test3 {
    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            public void run() {
                //声明Connection对象
                Connection con;
                //驱动程序名
                String driver1 = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
                //URL指向要访问的数据库名
                String url1 = "jdbc:sqlserver://IP地址和端口号;DateBaseName=数据库名";
                //MySQL配置时的用户名
                String user1 = "user";
                //MySQL配置时的密码
                String password1 = "user";


                //声明Connection对象
                Connection con1;
                //驱动程序名
                String driver2 = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
                //URL指向要访问的数据库名
                String url2 = "jdbc:sqlserver://IP地址和端口号;DateBaseName=数据库名";
                //MySQL配置时的用户名
                String user2 = "user";
                //MySQL配置时的密码
                String password2 = "user";
                //遍历查询结果集
                try {
                    //加载驱动程序
                    Class.forName(driver1);
                    //1.getConnection()方法，连接MySQL数据库！！
                    con = DriverManager.getConnection(url1,user1,password1);
                    if(!con.isClosed())
                        System.out.println("成功连接到数据库!");


                    try {
                        //加载驱动程序
                        Class.forName(driver2);
                        //1.getConnection()方法，连接MySQL数据库！！
                        con1 = DriverManager.getConnection(url2,user2,password2);
                        if(!con1.isClosed())
                            System.out.println("成功连接到数据库!");


                        //2.创建statement类对象，用来执行SQL语句！！
                        //注意此处调用的还是“源”库的con，而非“目标”库的con1
                        Statement statement = con.createStatement();
                        //要执行的SQL语句
                        String sql = "use 数据库名 select * from 表名";
                        //3.ResultSet类，用来存放获取的结果集！！
                        ResultSet rs = statement.executeQuery(sql);

                        //要执行的SQL语句
                        String sql1 = "use tiantiana insert into Table_1(tiantian,qiqi,yuyu)VALUES(?,?,?)";
                        //3.ResultSet类，用来存放获取的结果集！！
                        //注意此处调用的还是“目标”库的con1，而非“源”库的con
                        PreparedStatement pst = con1.prepareStatement(sql1);

                        //输出表头
                        System.out.println ("tiantian"+"/t"+"qiqi"+"/t"+"yuyu");
                        while(rs.next()){
                            //输出表中内容
                            System.out.print(rs.getString(1));
                            System.out.print(rs.getString(2));
                            System.out.print(rs.getString(3));

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


                        //2.创建statement类对象，用来执行SQL语句！！
                        //注意此处调用的还是“源”库的con，而非“目标”库的con1
                        Statement statement1 = con.createStatement();
                        //要执行的SQL语句
                        String sql2 = "use 数据库名 select * from 表名";
                        //3.ResultSet类，用来存放获取的结果集！！
                        ResultSet rs1 = statement1.executeQuery(sql2);

                        //要执行的SQL语句
                        String sql3 = "use tiantiana insert into Table_2(tiantian1,qiqi1,yuyu1)VALUES(?,?,?)";
                        //3.ResultSet类，用来存放获取的结果集！！
                        //注意此处调用的还是“目标”库的con1，而非“源”库的con
                        PreparedStatement pst1 = con1.prepareStatement(sql3);

                        //输出表头
                        System.out.println ("tiantian1"+"/t"+"qiqi1"+"/t"+"yuyu1");
                        while(rs1.next()){
                            //输出表中内容
                            System.out.print(rs1.getString(1));
                            System.out.print(rs1.getString(2));
                            System.out.print(rs1.getString(3));
                            //给insert语句中的第一、二、三个问号赋值
                            pst1.setString(1,rs1.getString(1));
                            pst1.setString(2,rs1.getString(2));
                            pst1.setString(3,rs1.getString(3));
                            pst1.executeUpdate();

                        }

                        //关闭链接
                        rs1.close();
                        pst.close();
                        con1.close();
                        con.close();
                    } catch(ClassNotFoundException e) {
                        //数据库驱动类异常处理
                        System.out.println("对不起,找不到驱动程序!");
                        e.printStackTrace();
                    } catch(SQLException e) {
                        //数据库连接失败异常处理
                        e.printStackTrace();
                    }catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }finally{
                        System.out.println("数据库数据成功获取！！");
                    }

                } catch(ClassNotFoundException e) {
                    //数据库驱动类异常处理
                    System.out.println("对不起,找不到驱动程序!");
                    e.printStackTrace();
                } catch(SQLException e) {
                    //数据库连接失败异常处理
                    e.printStackTrace();
                }catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }finally{
                    System.out.println("数据库数据成功获取！！");
                }
            }
        };
        ScheduledExecutorService service = Executors
                .newSingleThreadScheduledExecutor();
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
        service.scheduleAtFixedRate(runnable, 10, 60*2, TimeUnit.SECONDS);

    }
}