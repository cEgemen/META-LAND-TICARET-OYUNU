import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Database {

static Connection con=null;
static Statement stat=null;
static ResultSet rs=null;

public Database()
{
	try {
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/proje6","root","kanarya1907=fener");
		stat=(Statement) con.createStatement();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
static void add(String sql)	
{
try {
	stat.executeUpdate(sql);
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
}
static void update(String sql)
{
	try {
		stat.executeUpdate(sql);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
static String dAte()
{
	Date simdikiZaman = new Date();
  //  System.out.println(simdikiZaman.toString());
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  //  System.out.println(df.format(simdikiZaman));
    return df.format(simdikiZaman);
}
static ResultSet Sorgu(String sql)
{
    try {
		rs=stat.executeQuery(sql);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}  
	return rs;
}
}
