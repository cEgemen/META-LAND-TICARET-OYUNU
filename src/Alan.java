import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Alan extends JButton{

  public int alan_no;
  public String alan_kullanıcı_ad;
  public String alan_tür;
  static Alan [][] alan;
  public Alan()
  {
	   
  }
  public Alan(String alan_kullanıcı_ad)
  {
	   this.alan_kullanıcı_ad=alan_kullanıcı_ad;   
  }
   public Alan(int alan_no,String alan_kullanıcı_ad)
   {
	   this.alan_no=alan_no;
	   this.alan_kullanıcı_ad=alan_kullanıcı_ad;   
   }
   public void build1(Alan a , JPanel p1 )
   {
		String sql,boyut=null;
    	ResultSet rs;
    	sql="SELECT alan_boyut FROM AlanSistem;";
    	rs=Database.Sorgu(sql);
    	try {
			while(rs.next())
			{
				boyut=rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println("boyut->"+boyut);
           	String[] alan_boyut_ayrımı = boyut.split("x");
       int   x=Integer.parseInt(alan_boyut_ayrımı[0]);
       int   y=Integer.parseInt(alan_boyut_ayrımı[1]);
   	int syc=1;
    a.alan = new Alan[x][y]; 
   	for(int i=0;i<x;i++)
   	{
   		for(int j=0;j<y;j++)
   		{
   			a.alan[i][j]=new Alan(syc,Giris.kullanıcı_ad);
   			 p1.add(a.alan[i][j]);
   			syc++;
   		}
   	}
   	p1.updateUI();
   }
  
   public void build2(Alan a,JPanel p1,int m , int n )
   {
   	for(int i=0;i<m;i++)
   	{
   		for(int j=0;j<n;j++)
   		{
   			 p1.add(a.alan[i][j]);
   		}
   	}
   	p1.updateUI();
   }
   
}
