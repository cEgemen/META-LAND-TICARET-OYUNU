import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class Main extends JFrame {

	private JPanel contentPane;
    private JLabel l1,l_d=new JLabel("TARİH->"),l_s=new JLabel("SAAT->");
    private JButton b1,b2;
    private JTextField tf_d=new JTextField(),tf_s=new JTextField();
	private String tarih= Database.dAte(),saat=null;
	private String sql1,sql2,sql3,sql4,sql5;
	private ResultSet rs1,rs2,rs3,rs4,rs5;
	private int g_esya_gider=-1,g_yemek_gider=-1,g_para_gider=-1;
	public int hesap(int sbt_ücret,int oran,int seviye)
	{
		int tmp=sbt_ücret;
		for(int i=1;i<=seviye;i++)
		{
			if(!(i-1==0))
			{
			tmp=tmp+((tmp*oran)/100);
			}
		}
		return tmp;
	}
	public String dateAyar(String date,int gun)
	    {
		 String [] tmp = date.split("-");
	    	 // Güncel tarihi alma
	        LocalDate currentDate = LocalDate.of(Integer.parseInt(tmp[0]),Integer.parseInt(tmp[1]),Integer.parseInt(tmp[2]));	        
	        // Eklemek istediğiniz gün sayısı
	        int daysToAdd = gun;
	        
	        // Belirli bir sayıda gün ekleyerek yeni tarihi hesaplama
	        LocalDate endDate = currentDate.plusDays(daysToAdd);
	        
	        // Sonucu String olarak almak için formatlama
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        String endDateString = endDate.format(formatter);
	        
	        // Sonuçları ekrana yazdırma
	        System.out.println("Güncel tarih: " + currentDate);
	        System.out.println("Eklenen gün sayısı: " + daysToAdd);
	        System.out.println("Sonuç tarihi: " + endDateString);
	    	return endDateString;
	    }
	
	public void getsaat()
    {
 	    Date d = new Date();
 	    SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss");
         saat=format2.format(d);
         System.out.println("saat->"+saat);
    }
	public long gun_saat_farkı(int kullanıcı_id,String x,String y)
	{
		String dt="";
	    String sa="";
	   String s = "SELECT update_tarihi,update_saati FROM Kullanıcılar WHERE kullanıcı_no="+kullanıcı_id+";";
	   ResultSet r = Database.Sorgu(s);
	   try {
		while(r.next())
		   {
			   dt=r.getString("update_tarihi"); //update zaman
			   sa=r.getString("update_saati");   // update saat
		   }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 String [] tmp1=dt.split("-"); // up_ta
	 String [] tmp2=x.split("-");  //current_ta
	 String [] tmp3=sa.split(":"); //up_sa
	 String [] tmp4=y.split(":");  //current_sa
 	 int t1=0,t2=0;
	 boolean onay=false;
	 LocalDate currentDate =LocalDate.of(Integer.parseInt(tmp2[0]),Integer.parseInt(tmp2[1]),Integer.parseInt(tmp2[2]));
	 System.out.println("şimdiki zaman=>"+currentDate);
     LocalDate date2 = LocalDate.of(Integer.parseInt(tmp1[0]),Integer.parseInt(tmp1[1]),Integer.parseInt(tmp1[2]));
     System.out.println("update_tarihi->"+date2);
     Period period = date2.until(currentDate); // update-simdiki
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	  String cd = currentDate.format(formatter);
         String [] tmp5 = cd.split("-");      
      long fark =(Integer.parseInt(tmp1[0])-Integer.parseInt(tmp5[0]))*365 +period.toTotalMonths() * 30 + period.getDays();
	 System.out.println("gun_saat_fark metod içi=>"+fark);
     if(Integer.parseInt(tmp4[0])>Integer.parseInt(tmp3[0]))
     {
    	 onay=true;
     }
     else if((Integer.parseInt(tmp4[0])==Integer.parseInt(tmp3[0])) && (Integer.parseInt(tmp4[1])>Integer.parseInt(tmp3[1])))
     {
    	 onay=true;
     }
     else if((Integer.parseInt(tmp4[0])==Integer.parseInt(tmp3[0])) && (Integer.parseInt(tmp4[1])==Integer.parseInt(tmp3[1])) && (Integer.parseInt(tmp4[2])>=Integer.parseInt(tmp3[2])))
     {
    	 onay=true;
     }
     if(fark>0 && onay==true)
     {
    	 return fark;
     }
     else if(fark>0 && onay==false)
     {
       return fark-1;	 
     }
     else if(fark==0 && onay==true)
     {
    	 return 0;
     }
     else {
    	 return -1;
     }
	}
	
	public long gun_saat_farkı(String baslangıc_tarihi,String bitis_tarihi,String baslangıc_saati,String x,String y)
	{
		String dt_baslangıc=baslangıc_tarihi;
	    String dt_bitis=bitis_tarihi;
		String sa=baslangıc_saati;
	 String [] tmp3=dt_bitis.split("-");
	 String [] tmp4=sa.split(":");
	 String [] tmp5=y.split(":");
	 String []  tmp6=x.split("-");
  	 int t=0,t1=0,t2=0,t3=0;
	 boolean onay=false;
	 LocalDate currentDate = LocalDate.of(Integer.parseInt(tmp6[0]),Integer.parseInt(tmp6[1]),Integer.parseInt(tmp6[2]));
	 System.out.println("currentDate=>"+currentDate);
      LocalDate date2 = LocalDate.of(Integer.parseInt(tmp3[0]),Integer.parseInt(tmp3[1]),Integer.parseInt(tmp3[2]));
     System.out.println("bitis_tarihi->"+date2);
      Period period =date2.until(currentDate);
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    String cd = currentDate.format(formatter);
         String [] tmp2 = cd.split("-");      
      long fark =(Integer.parseInt(tmp3[0])-Integer.parseInt(tmp2[0]))*365 +period.toTotalMonths() * 30 + period.getDays();
	 System.out.println("gun_saat_fark metod içi=>"+fark);
     if(Integer.parseInt(tmp5[0])>Integer.parseInt(tmp4[0]))
     {
    	 onay=true;
     }
     else if((Integer.parseInt(tmp5[0])==Integer.parseInt(tmp4[0])) && (Integer.parseInt(tmp5[1])>Integer.parseInt(tmp4[1])))
     {
    	 onay=true;
     }
     else if((Integer.parseInt(tmp5[0])==Integer.parseInt(tmp4[0])) && (Integer.parseInt(tmp5[1])==Integer.parseInt(tmp4[1])) && (Integer.parseInt(tmp5[2])>=Integer.parseInt(tmp4[2])))
     {
    	 onay=true;
     }
     if(fark>0 && onay==true)
     {
    	 return fark;
     }
     else if(fark>0 && onay==false)
     {
       return fark-1;	 
     }
     else if(fark==0 && onay==true)
     {
    	 return 0;
     }
     else if(fark<0 && onay == true ){
    	 return fark+1;
     }
     else {
    	return fark;
     }     
	}
	public long gun_saat_farkı(String baslangıc_tarihi,String bitis_tarihi,String x)
	{
		String dt_baslangıc=baslangıc_tarihi;
	    String dt_bitis=bitis_tarihi;
	 String [] tmp3=dt_bitis.split("-");
	 String []  tmp6=x.split("-");
  	 int t=0,t1=0,t2=0,t3=0;
	 LocalDate currentDate = LocalDate.of(Integer.parseInt(tmp6[0]),Integer.parseInt(tmp6[1]),Integer.parseInt(tmp6[2]));
  	 System.out.println("currentDate=>"+currentDate);
      LocalDate date2 = LocalDate.of(Integer.parseInt(tmp3[0]),Integer.parseInt(tmp3[1]),Integer.parseInt(tmp3[2]));
     System.out.println("bitis_tarihi->"+date2);
      Period period =date2.until(currentDate);
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	    String cd = currentDate.format(formatter);
         String [] tmp2 = cd.split("-");      
      long fark =(Integer.parseInt(tmp3[0])-Integer.parseInt(tmp2[0]))*365 +period.toTotalMonths() * 30 + period.getDays();
	 System.out.println("gun_saat_fark metod içi=>"+fark);
     if(fark>0)
     {
    	 return fark;
     }
     else if(fark==0)
     {
    	 return 0;
     }
     else {
    	return fark;
     }
     
	}
	
    public void setVis()
    {
    	this.setVisible(false);
    }
                       
    
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
   public void getDate()
   {
	    Date d = new Date();
	    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	    SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss");
	    tarih=format1.format(d);
        saat=format2.format(d);
        System.out.println("tarih->"+tarih);
        System.out.println("saat->"+saat);
   }
   public void duzen(String tarih,String saat)
   {
	   int kullanıcı_esya=-1,kullanıcı_yemek=-1,kullanıcı_para=-1,is_yeri_id=-1;
	   ArrayList<Integer> kullanıcı_id = new ArrayList<Integer>();
	  sql1="SELECT gunluk_esya_gider,gunluk_yemek_gider,gunluk_para_gider FROM OyuncuSistem;";
	  rs1=Database.Sorgu(sql1);
	  try {
		while(rs1.next())
		  {
			  g_esya_gider=rs1.getInt("gunluk_esya_gider");
			  g_yemek_gider=rs1.getInt("gunluk_yemek_gider");
			  g_para_gider=rs1.getInt("gunluk_para_gider");
		  }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	sql1="SELECT kullanıcı_id FROM Oyuncular;";
	  rs1=Database.Sorgu(sql1);
		try {
			while(rs1.next()) 
			 {
			  kullanıcı_id.add(rs1.getInt("kullanıcı_id"));     			 
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	if(kullanıcı_id.size()>0)
	{
		for(int k=0;k<kullanıcı_id.size();k++) {
		sql2="SELECT kullanıcı_yemek,kullanıcı_esya,kullanıcı_para FROM Oyuncular WHERE kullanıcı_id="+kullanıcı_id.get(k)+";";
		  rs2=Database.Sorgu(sql2);		  
			try {
				while(rs2.next()) 
				 { 
				  kullanıcı_yemek=rs2.getInt("kullanıcı_yemek");     			 
				  kullanıcı_esya=rs2.getInt("kullanıcı_esya");     			 
				  kullanıcı_para=rs2.getInt("kullanıcı_para");
				 }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
   long fark=gun_saat_farkı(kullanıcı_id.get(k),tarih,saat);  
      System.out.println("duzen->gun_saat_farkı(kullanıcı_id):"+fark);
        if((fark>=0))  
          {
        fark++;	
         int tmp=-1,tmp2=-1;
         ArrayList<Integer> isletme_alan_id=new ArrayList<Integer>();
         
         sql3="SELECT COUNT(calısanlar_id)  FROM Calısanlar WHERE kullanıcı_id="+kullanıcı_id.get(k)+";";
         rs3=Database.Sorgu(sql3);
         try {
			while(rs3.next())
			 {
				 tmp=rs3.getInt(1);	 
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         sql3="SELECT alan_id FROM Alanlar WHERE kullanıcı_id="+kullanıcı_id.get(k)+" AND alan_tür='isletme';";
         rs3=Database.Sorgu(sql3);
         try {
			while(rs3.next())
			 {
				 isletme_alan_id.add(rs3.getInt(1));
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
        if(tmp==0 && isletme_alan_id.size()==0) 
        { 
        	// işletmesi yok ve calısmıyor;
        int a1=(int) (kullanıcı_yemek-(g_yemek_gider*fark));
        int a2=(int) (kullanıcı_esya-(g_esya_gider*fark));
        int a3=(int) (kullanıcı_para-(g_para_gider*fark));
       
 	       sql2="UPDATE Oyuncular SET kullanıcı_yemek="+a1+","+"kullanıcı_esya="+a2+",kullanıcı_para="+a3+" WHERE kullanıcı_id="+kullanıcı_id.get(k)+";";
       Database.add(sql2);     
     String up_date=dateAyar(tarih,1);
       sql2="UPDATE Kullanıcılar SET update_tarihi='"+up_date+"', update_saati='"+saat+"' WHERE kullanıcı_no="+kullanıcı_id.get(k)+";";
       Database.add(sql2);  
        }
        else if(!(tmp==0) && isletme_alan_id.size()==0)
        {
        	// isletmesi yok ve calısıyo
        boolean emlak=false,magaza=false,market=false;
        int maas=-1,isveren_kullanıcı_id=-1,isveren_para=-1,calısan_sayısı=-1;
        String bitis_tarihi=null,baslangıc_tarihi=null,isletme_tür=null,kullanıcı_tür=null;
        	sql2="SELECT isletme_id,calısma_bitis_tarihi,calısma_baslangıc_tarihi FROM Calısanlar WHERE  kullanıcı_id="+kullanıcı_id.get(k)+";"; 
        	rs2=Database.Sorgu(sql2);
        	try {
				while(rs2.next())
				{
					bitis_tarihi=rs2.getString("calısma_bitis_tarihi");
				    is_yeri_id=rs2.getInt("isletme_id");
				    baslangıc_tarihi=rs2.getString("calısma_baslangıc_tarihi");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	int a_id=-1;
              sql3="SELECT isletme_tür,kullanıcı_ücret,alan_id,calısan_sayısı FROM İsletmeler WHERE isletme_id="+is_yeri_id+";"; 
               rs3=Database.Sorgu(sql3);
               try {
				while(rs3.next())
				   {
					   calısan_sayısı=rs3.getInt("calısan_sayısı");
					   maas=rs3.getInt("kullanıcı_ücret");
					   isletme_tür=rs3.getString("isletme_tür");
					   if(isletme_tür.equals("emlak"))
					   {
						   emlak=true;
					   }
					   else if(isletme_tür.equals("magaza"))
					   {
						   magaza=true;
					   }
					   else if(isletme_tür.equals("market"))
					   {
						   market=true;
					   }
					   a_id=rs3.getInt("alan_id");
				   }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
       sql1="SELECT kullanıcı_id FROM Alanlar WHERE alan_id="+a_id+";";
       rs1=Database.Sorgu(sql1);
       try {
		while(rs1.next())
		   {
			   isveren_kullanıcı_id=rs1.getInt("kullanıcı_id");
		   }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
       sql1="SELECT kullanıcı_türü FROM Kullanıcılar WHERE kullanıcı_no="+isveren_kullanıcı_id+";";
       rs1=Database.Sorgu(sql1);
       try {
		while(rs1.next())
		   {
			   kullanıcı_tür=rs1.getString(1);
		   }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
       if(!(kullanıcı_tür.equals("yönetici")))
       {
    	   sql2="SELECT kullanıcı_para FROM Oyuncular WHERE kullanıcı_id="+isveren_kullanıcı_id+";";
    	   rs2=Database.Sorgu(sql2);
    	   try {
			while(rs2.next())
			   {
				   isveren_para=rs2.getInt(1);
			   }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       }   
      if(emlak==true)  	
      {
    	 long f = gun_saat_farkı(baslangıc_tarihi,bitis_tarihi,tarih);
    	 System.out.println("emlak_calısma_gunFark(baslangıc,bitis)>"+f);
    	 if(f<=0)
    	 {
    		  int a1=(int) (kullanıcı_yemek-(g_yemek_gider*fark));
    	        int a2=(int) (kullanıcı_esya-(g_esya_gider*fark));
    	        int a3=(int) (kullanıcı_para+(maas*fark));
    	      
    	 	       sql2="UPDATE Oyuncular SET kullanıcı_yemek="+a1+","+"kullanıcı_esya="+a2+",kullanıcı_para="+a3+" WHERE kullanıcı_id="+kullanıcı_id.get(k)+";";
    	       Database.add(sql2);     
    	       String up_date=dateAyar(tarih,1);
    	       sql2="UPDATE Kullanıcılar SET update_tarihi='"+up_date+"', update_saati='"+saat+"' WHERE kullanıcı_no="+kullanıcı_id.get(k)+";";
    	       Database.add(sql2);  
    	        
    	   if(!(kullanıcı_tür.equals("yönetici")))
           {
    		 int g =(int) (isveren_para-(maas*fark));  
        	  sql3="UPDATE Oyuncular SET kullanıcı_para="+g+";";
   	       Database.add(sql3);
           }
    	 }
    	
    	 else {
    		 sql1="DELETE FROM Calısanlar WHERE kullanıcı_id="+kullanıcı_id.get(k)+";";
   		  Database.update(sql1);
    			calısan_sayısı=calısan_sayısı-1;
    			sql2="UPDATE İsletmeler SET calısan_sayısı="+calısan_sayısı+" WHERE isletme_id="+is_yeri_id+";";
    			Database.update(sql2);	
    			sql1="INSERT INTO İslemler(islem_sahibiı_id,islem_türü,islem_yer_tür,islem_tarihi,islem_saat) VALUES("+kullanıcı_id.get(k)+",'calısma bitis','"+isletme_tür+"','"+tarih+"','"+saat+"');";
    			Database.add(sql1);	 
    		  int a1=(int) (kullanıcı_yemek-(g_yemek_gider*fark));
  	        int a2=(int) (kullanıcı_esya-(g_esya_gider*fark));
  	        int a3=(int) (kullanıcı_para-(g_para_gider*f));
  	 	       sql2="UPDATE Oyuncular SET kullanıcı_yemek="+a1+","+"kullanıcı_esya="+a2+",kullanıcı_para="+a3+" WHERE kullanıcı_id="+kullanıcı_id.get(k)+";";
  	       Database.add(sql2);     
  	     String up_date=dateAyar(tarih,1);
  	       sql2="UPDATE Kullanıcılar SET update_tarihi='"+up_date+"', update_saati='"+saat+"' WHERE kullanıcı_no="+kullanıcı_id.get(k)+";";
  	       Database.add(sql2);  
    	 }
      }
      else if(magaza==true) 	
      {
    	 long f = gun_saat_farkı(baslangıc_tarihi,bitis_tarihi,tarih);
    	 System.out.println("magaza_calısma_gunFark>"+f);
    	 if((f<=0))
     	 {
     		  int a1=(int) (kullanıcı_yemek-(g_yemek_gider*fark));
     	        int a2=kullanıcı_esya;
     	        int a3=(int) (kullanıcı_para-(g_para_gider*fark)+(maas*fark));
     	      
         	       sql3="UPDATE Oyuncular SET kullanıcı_yemek="+a1+","+"kullanıcı_esya="+a2+",kullanıcı_para="+a3+" WHERE kullanıcı_id="+kullanıcı_id.get(k)+";";
     	       Database.add(sql3);
     	      String up_date=dateAyar(tarih,1);
     	       sql2="UPDATE Kullanıcılar SET update_tarihi='"+up_date+"', update_saati='"+saat+"' WHERE kullanıcı_no="+kullanıcı_id.get(k)+";";
     	       Database.add(sql2); 
     	   if(!(kullanıcı_tür.equals("yönetici")))
            {
     		 int g =(int) (isveren_para-(maas*fark));  
         	  sql3="UPDATE Oyuncular SET kullanıcı_para="+g+";";
         	  Database.update(sql3);
            }
     	 }
    	 else {
    		 sql1="DELETE FROM Calısanlar WHERE kullanıcı_id="+kullanıcı_id.get(k)+";";
   		  Database.update(sql1);
 			calısan_sayısı=calısan_sayısı-1;
 			sql2="UPDATE İsletmeler SET calısan_sayısı="+calısan_sayısı+" WHERE isletme_id="+is_yeri_id+";";
 			Database.update(sql2);	
 			sql1="INSERT INTO İslemler(islem_sahibiı_id,islem_türü,islem_yer_tür,islem_tarihi,islem_saat) VALUES("+kullanıcı_id.get(k)+",'calısma bitis','"+isletme_tür+"','"+tarih+"','"+saat+"');";
 			Database.add(sql1);	  
    		 int a1=(int) (kullanıcı_yemek-(g_yemek_gider*fark));
  	        int a2=(int) (kullanıcı_esya-(g_esya_gider*f));
  	        int a3=(int) (kullanıcı_para-(g_para_gider*fark));
  	      
      	       sql3="UPDATE Oyuncular SET kullanıcı_yemek="+a1+","+"kullanıcı_esya="+a2+",kullanıcı_para="+a3+" WHERE kullanıcı_id="+kullanıcı_id.get(k)+";";
  	       Database.add(sql3);
  	     String up_date=dateAyar(tarih,1);
  	       sql2="UPDATE Kullanıcılar SET update_tarihi='"+up_date+"', update_saati='"+saat+"' WHERE kullanıcı_no="+kullanıcı_id.get(k)+";";
  	       Database.add(sql2); 
    	 }
      }
      else if(market==true)
      {
    	  long f = gun_saat_farkı(baslangıc_tarihi,bitis_tarihi,tarih);
     	 System.out.println("market_calısma_gunFark>"+f);

    	  if((f<=0))
     	 {
     		  int a1=kullanıcı_yemek;
     	        int a2=(int) (kullanıcı_esya-(g_esya_gider*fark));
     	        int a3=(int) (kullanıcı_para-(g_para_gider*fark)+(maas*fark));
     	      
     	       sql3="UPDATE Oyuncular SET kullanıcı_yemek="+a1+","+"kullanıcı_esya="+a2+",kullanıcı_para="+a3+" WHERE kullanıcı_id="+kullanıcı_id.get(k)+";";
     	       Database.add(sql3);
     	      String up_date=dateAyar(tarih,1);
     	       sql2="UPDATE Kullanıcılar SET update_tarihi='"+up_date+"', update_saati='"+saat+"' WHERE kullanıcı_no="+kullanıcı_id.get(k)+";";
     	       Database.add(sql2); 
     	   if(!(kullanıcı_tür.equals("yönetici")))
            {
     		 int g =(int) (isveren_para-(maas*fark));  
         	  sql3="UPDATE Oyuncular SET kullanıcı_para="+g+";";
         	  Database.update(sql3);
            }
     	 }
    	  else {
    		  sql1="DELETE FROM Calısanlar WHERE kullanıcı_id="+kullanıcı_id.get(k)+";";
    		  Database.update(sql1);
    		  calısan_sayısı=calısan_sayısı-1;
   			sql2="UPDATE İsletmeler SET calısan_sayısı="+calısan_sayısı+" WHERE isletme_id="+is_yeri_id+";";
   			Database.update(sql2);	
   			sql1="INSERT INTO İslemler(islem_sahibiı_id,islem_türü,islem_yer_tür,islem_tarihi,islem_saat) VALUES("+kullanıcı_id.get(k)+",'calısma bitis','"+isletme_tür+"','"+tarih+"','"+saat+"');";
   			Database.add(sql1); 
    		  int a1=(int) (kullanıcı_yemek-(g_yemek_gider*f));
   	        int a2=(int) (kullanıcı_esya-(g_esya_gider*fark));
   	        int a3=(int) (kullanıcı_para-(g_para_gider*fark));
   	      
   	       sql3="UPDATE Oyuncular SET kullanıcı_yemek="+a1+","+"kullanıcı_esya="+a2+",kullanıcı_para="+a3+" WHERE kullanıcı_id="+kullanıcı_id.get(k)+";";
   	       Database.add(sql3);
   	     String up_date=dateAyar(tarih,1);
   	       sql2="UPDATE Kullanıcılar SET update_tarihi='"+up_date+"', update_saati='"+saat+"' WHERE kullanıcı_no="+kullanıcı_id.get(k)+";";
   	       Database.add(sql2); 
    	  }
      }
        }
        else {
        	int emlak_say=0,magaza_say=0,market_say=0,sbt_ücret=-1,k_emlak=0,k_magaza=0,k_market=0,oran=-1,ibilgileri_id=-1,seviye=-1;
        	ArrayList<String> isletme_tür = new ArrayList<String>();
        	ArrayList<Integer> islem_id=new ArrayList<Integer>();
        	ArrayList<Integer> i_id=new ArrayList<Integer>();
        	 sql2="SELECT isletme_sabit_gelir,isletme_sabit_gelir_oran FROM İsletmeSistem;";
        	   rs2=Database.Sorgu(sql2);
        	   try {
  			while(rs2.next())
  			   {
  				   sbt_ücret=rs2.getInt("isletme_sabit_gelir");
  				   oran=rs2.getInt("isletme_sabit_gelir_oran");
  			   }
  		} catch (SQLException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}      	
        	   
       	sql1="SELECT islem_id FROM İslemler WHERE islem_türü='kiralama' AND islem_sahibiı_id="+kullanıcı_id.get(k)+";";
        	rs1=Database.Sorgu(sql1);
        		try {
        			while(rs1.next()) 
        			{
        				islem_id.add(rs1.getInt(1));
        			}
        		} catch (SQLException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        	  if(!(islem_id.size()==0))	 
        	  {
        		  int kiralayan_id=-1,kiralatan_id=-1,kiralanan_isletme_id=-1,kiralanan_isletme_alan_id=-1;       
        		  String kira_baslangıc_tarihi="",kira_bitis_tarihi="",kira_baslangıc_saati="",kiralık_isletme_türü="";
        		  for(int i=0; i<islem_id.size();i++)
        		  {
        			int ctrl=-1;
        		     sql1="SELECT COUNT(islem_id) FROM Kiralamalar WHERE islem_id="+islem_id.get(i)+";";
        			rs1=Database.Sorgu(sql1);
        			try {
        				while(rs1.next())
        				{
        					ctrl=rs1.getInt(1);
        				}
        			} catch (SQLException e1) {
        				// TODO Auto-generated catch block
        				e1.printStackTrace();
        			}
        			if(ctrl>0)
        		      {
        		      sql1="SELECT kiralatan_id,kiralanan_isletme_id,kira_baslangıc_tarihi,kira_bitis_tarihi,kira_baslangıc_saati FROM Kiralamalar WHERE  islem_id="+islem_id.get(i)+";";
        		      rs1=Database.Sorgu(sql1);
        		      try {
        				while(rs1.next())
        				  {
        					  kiralatan_id=rs1.getInt("kiralatan_id");
        					  kira_baslangıc_tarihi=rs1.getString("kira_baslangıc_tarihi");
        					  kira_bitis_tarihi=rs1.getString("kira_bitis_tarihi");
        					  kira_baslangıc_saati=rs1.getString("kira_baslangıc_saati");
        				      kiralanan_isletme_id=rs1.getInt("kiralanan_isletme_id");
        				      i_id.add(kiralanan_isletme_id);
        				  }
        			} catch (SQLException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			}
        		      
        		  sql1="SELECT isletme_tür,isletme_bilgi_id FROM İsletmeler WHERE isletme_id="+kiralanan_isletme_id+";";    
        		  rs1=Database.Sorgu(sql1);  
        		  try {
					while(rs1.next())
					  {
						  kiralık_isletme_türü=rs1.getString("isletme_tür");
					     ibilgileri_id=rs1.getInt("isletme_bilgi_id");
					  }
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		  sql1="SELECT seviye FROM iBilgileri WHERE islem_bilgileri_id="+ibilgileri_id+";";
        		  rs1=Database.Sorgu(sql1);
        		  try {
					while(rs1.next())
					  {
						  seviye=rs1.getInt(1);
					  }
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	int sbt_ücret2=hesap(sbt_ücret,oran,seviye);  
        		 long f = gun_saat_farkı(kira_baslangıc_tarihi,kira_bitis_tarihi,kira_baslangıc_saati,tarih,saat);     
        		 System.out.println("kira_kontrol_gun_saat_fark->"+f);    
        		 if(f<=0)
        		       {  
                       if(kiralık_isletme_türü.equals("emlak"))
                       {
                    	int a1=(int) (kullanıcı_yemek-(g_yemek_gider*fark));
               	        int a2=(int) (kullanıcı_esya-(g_esya_gider*fark));
               	        int a3=(int) (kullanıcı_para+(sbt_ücret2*fark));              	      
               	       sql2="UPDATE Oyuncular SET kullanıcı_yemek="+a1+","+"kullanıcı_esya="+a2+",kullanıcı_para="+a3+" WHERE kullanıcı_id="+kullanıcı_id.get(k)+";";
               	       Database.add(sql2);
               	     String up_date=dateAyar(tarih,1);
               	       sql2="UPDATE Kullanıcılar SET update_tarihi='"+up_date+"', update_saati='"+saat+"' WHERE kullanıcı_no="+kullanıcı_id.get(k)+";";
               	       Database.add(sql2);   	  
                       k_emlak++;
                       }
                       else if(kiralık_isletme_türü.equals("market"))
                       {
                    		int a1=(int) (kullanıcı_yemek);
                   	        int a2=(int) (kullanıcı_esya-(g_esya_gider*fark));
                   	        int a3=(int) (kullanıcı_para+(sbt_ücret2*fark)-(g_para_gider*fark));              	      
                   	       sql2="UPDATE Oyuncular SET kullanıcı_yemek="+a1+","+"kullanıcı_esya="+a2+",kullanıcı_para="+a3+" WHERE kullanıcı_id="+kullanıcı_id.get(k)+";";
                   	       Database.add(sql2);
                   	     String up_date=dateAyar(tarih,1);
                   	       sql2="UPDATE Kullanıcılar SET update_tarihi='"+up_date+"', update_saati='"+saat+"' WHERE kullanıcı_no="+kullanıcı_id.get(k)+";";
                   	       Database.add(sql2);                      
                   	   k_market++;    
                       }
                       else if(kiralık_isletme_türü.equals("magaza"))
                       {
                    		int a1=(int) ((int) (kullanıcı_yemek)-(g_yemek_gider*fark));
                   	        int a2=(int) (kullanıcı_esya);
                   	        int a3=(int) (kullanıcı_para+(sbt_ücret2*fark)-(g_para_gider*fark));              	      
                   	       sql2="UPDATE Oyuncular SET kullanıcı_yemek="+a1+","+"kullanıcı_esya="+a2+",kullanıcı_para="+a3+" WHERE kullanıcı_id="+kullanıcı_id.get(k)+";";
                   	       Database.add(sql2);
                   	     String up_date=dateAyar(tarih,1);
                   	       sql2="UPDATE Kullanıcılar SET update_tarihi='"+up_date+"', update_saati='"+saat+"' WHERE kullanıcı_no="+kullanıcı_id.get(k)+";";
                   	       Database.add(sql2);
                          k_magaza++;
                       }
        		  }
        		 else
        		 {
        			 System.out.println("kiralama sonu ve f="+f);
        		     if(kiralık_isletme_türü.equals("emlak"))
                     {
                  	int a1=(int) (kullanıcı_yemek-(g_yemek_gider*fark));
             	        int a2=(int) (kullanıcı_esya-(g_esya_gider*fark));
             	        int a3=(int) ((kullanıcı_para)-(g_para_gider*f));              	      
             	       sql2="UPDATE Oyuncular SET kullanıcı_yemek="+a1+","+"kullanıcı_esya="+a2+",kullanıcı_para="+a3+" WHERE kullanıcı_id="+kullanıcı_id.get(k)+";";
             	       Database.add(sql2);
             	      String up_date=dateAyar(tarih,1);
             	       sql2="UPDATE Kullanıcılar SET update_tarihi='"+up_date+"', update_saati='"+saat+"' WHERE kullanıcı_no="+kullanıcı_id.get(k)+";";
             	       Database.add(sql2);   	  
                      k_emlak++;
                     }
                     else if(kiralık_isletme_türü.equals("market"))
                     {
                  		int a1=(int) (kullanıcı_yemek-(g_yemek_gider*f));
                 	        int a2=(int) (kullanıcı_esya-(g_esya_gider*fark));
                 	        int a3=(int) (kullanıcı_para-(g_para_gider*fark));              	      
                 	       sql2="UPDATE Oyuncular SET kullanıcı_yemek="+a1+","+"kullanıcı_esya="+a2+",kullanıcı_para="+a3+" WHERE kullanıcı_id="+kullanıcı_id.get(k)+";";
                 	       Database.add(sql2);
                 	      String up_date=dateAyar(tarih,1);
                 	       sql2="UPDATE Kullanıcılar SET update_tarihi='"+up_date+"', update_saati='"+saat+"' WHERE kullanıcı_no="+kullanıcı_id.get(k)+";";
                 	       Database.add(sql2);      
                 	      k_market++;      
                     }
                     else if(kiralık_isletme_türü.equals("magaza"))
                     {
                  		int a1=(int) ((int) (kullanıcı_yemek)-(g_yemek_gider*fark));
                 	        int a2=(int) (kullanıcı_esya-g_esya_gider*f);
                 	        int a3=(int) (kullanıcı_para+-(g_para_gider*fark));              	      
                 	       sql2="UPDATE Oyuncular SET kullanıcı_yemek="+a1+","+"kullanıcı_esya="+a2+",kullanıcı_para="+a3+" WHERE kullanıcı_id="+kullanıcı_id.get(k)+";";
                 	       Database.add(sql2);
                 	      String up_date=dateAyar(tarih,1);
                 	       sql2="UPDATE Kullanıcılar SET update_tarihi='"+up_date+"', update_saati='"+saat+"' WHERE kullanıcı_no="+kullanıcı_id.get(k)+";";
                 	       Database.add(sql2);
                     k_magaza++;
                     }
        		 }
        		  }
        	  }
      }
        System.out.println("isletme_alan_id_size()->"+isletme_alan_id.size());
          for (int i=0;i<isletme_alan_id.size();i++)
        	{
         	int isletme_id=-1,x=0;
        		sql2="SELECT isletme_tür,isletme_id FROM İsletmeler  WHERE alan_id="+isletme_alan_id.get(i)+";";
        		rs2=Database.Sorgu(sql2);
       		try {
					while(rs2.next())
					{
						isletme_tür.add(rs2.getString("isletme_tür"));
					    isletme_id=rs2.getInt("isletme_id");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
       	  sql1="SELECT isletme_bilgi_id FROM İsletmeler WHERE isletme_id="+isletme_id+";";    
		  rs1=Database.Sorgu(sql1);  
		  try {
			while(rs1.next())
			  {
			     ibilgileri_id=rs1.getInt("isletme_bilgi_id");
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  sql1="SELECT seviye FROM iBilgileri WHERE islem_bilgileri_id="+ibilgileri_id+";";
		  rs1=Database.Sorgu(sql1);
		  try {
			while(rs1.next())
			  {
				  seviye=rs1.getInt(1);
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	int sbt_ücret2=hesap(sbt_ücret,oran,seviye);  
    if(!(i_id.size()==0)) {
	for (int j=0;j<i_id.size();j++)
     {
    	 if(i_id.get(i)==isletme_id)
    	 {
    		 x++;
    	 }
     }
    }
	if(x==0)
	{

		if(isletme_tür.get(i).equals("emlak"))
		{
			 int a1=(int) (kullanıcı_yemek-(g_yemek_gider*fark));
		        int a2=(int) (kullanıcı_esya-(g_esya_gider*fark));
		        int a3=(int) (kullanıcı_para+(sbt_ücret2*fark));
		       
		       sql3="UPDATE Oyuncular SET kullanıcı_yemek="+a1+","+"kullanıcı_esya="+a2+",kullanıcı_para="+a3+" WHERE kullanıcı_id="+kullanıcı_id.get(k)+";";
		       Database.add(sql3);
		       String up_date=dateAyar(tarih,1);
		       sql2="UPDATE Kullanıcılar SET update_tarihi='"+up_date+"', update_saati='"+saat+"' WHERE kullanıcı_no="+kullanıcı_id.get(k)+";";
		       Database.add(sql2); 	  	
		}
		else if(isletme_tür.get(i).equals("market"))
		{
			int a1=kullanıcı_yemek;
	        int a2=(int) (kullanıcı_esya-(g_esya_gider*fark));
	        int a3=(int) (kullanıcı_para+(sbt_ücret2*fark)-(g_para_gider*fark));
	       
	       sql2="UPDATE Oyuncular SET kullanıcı_yemek="+a1+","+"kullanıcı_esya="+a2+",kullanıcı_para="+a3+" WHERE kullanıcı_id="+kullanıcı_id.get(k)+";";
	       Database.add(sql2);
	       String up_date=dateAyar(tarih,1);
	       sql2="UPDATE Kullanıcılar SET update_tarihi='"+up_date+"', update_saati='"+saat+"' WHERE kullanıcı_no="+kullanıcı_id.get(k)+";";
	       Database.add(sql2);   
		}
		else if(isletme_tür.get(i).equals("magaza"))
		{
			int a1=(int) (kullanıcı_yemek-(g_yemek_gider*fark));
	        int a2=kullanıcı_esya;
	        int a3=(int) (kullanıcı_para+(sbt_ücret2*fark)-(g_para_gider*fark));  
	        System.out.println("a1->"+a1+", a2->"+a2+", a3->"+a3);
	       sql2="UPDATE Oyuncular SET kullanıcı_yemek="+a1+","+"kullanıcı_esya="+a2+",kullanıcı_para="+a3+" WHERE kullanıcı_id="+kullanıcı_id.get(k)+";";
	       Database.add(sql2);
	       String up_date=dateAyar(tarih,1);
	       sql2="UPDATE Kullanıcılar SET update_tarihi='"+up_date+"', update_saati='"+saat+"' WHERE kullanıcı_no="+kullanıcı_id.get(k)+";";
	       Database.add(sql2);   
		}
	}
        }
        }
          } 
		 }
   }
   } 
  
   
public void duzen2(String tarih,String saat) {
ArrayList<Integer> islem_id=new ArrayList<Integer>();
ArrayList<Integer> calısma_id=new ArrayList<Integer>();
int calısan_id=-1,isletme_id=-1,calısma_gun_sayısı=-1,calısan_sayısı=-1;
String calısma_baslangıc_tarihi="",calısma_bitis_tarihi="",calısma_saatleri="",calısma_basvuru_tarihi="",isletme_tür="";
sql1="SELECT calısanlar_id FROM Calısanlar;";
rs1=Database.Sorgu(sql1);
try {
	while(rs1.next())
	{
		calısma_id.add(rs1.getInt(1));
	}
} catch (SQLException e2) {
	// TODO Auto-generated catch block
	e2.printStackTrace();
}
if(calısma_id.size()>0)
{
for(int i=0;i<calısma_id.size();i++)
{
	sql2="SELECT calısma_baslangıc_tarihi,calısma_bitis_tarihi,isletme_id,kullanıcı_id FROM Calısanlar WHERE calısanlar_id="+calısma_id.get(i)+";";
    rs2=Database.Sorgu(sql2);
    try {
		while(rs2.next())
		{
			calısma_baslangıc_tarihi=rs2.getString("calısma_baslangıc_tarihi");
			calısma_bitis_tarihi=rs2.getString("calısma_bitis_tarihi");
		    isletme_id=rs2.getInt("isletme_id");
		    calısan_id=rs2.getInt("kullanıcı_id");
		    
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   sql3="SELECT calısan_sayısı,isletme_tür FROM İsletmeler WHERE isletme_id="+isletme_id+";"; 
    rs3=Database.Sorgu(sql3);
    try {
		while(rs3.next())
		{
			calısan_sayısı=rs3.getInt("calısan_sayısı");
		    isletme_tür=rs3.getString("isletme_tür");
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    long f=gun_saat_farkı(calısma_baslangıc_tarihi,calısma_bitis_tarihi,tarih);
    if(f>0)
    {
  	  sql1="DELETE FROM Calısanlar WHERE kullanıcı_id="+calısan_id+";";	
		Database.update(sql1);
		calısan_sayısı=calısan_sayısı-1;
		sql2="UPDATE İsletmeler SET calısan_sayısı="+calısan_sayısı+" WHERE isletme_id="+isletme_id+";";
		Database.update(sql2);	
		sql1="INSERT INTO İslemler(islem_sahibiı_id,islem_türü,islem_yer_tür,islem_tarihi,islem_saat) VALUES("+calısan_id+",'calısma bitis','"+isletme_tür+"','"+tarih+"','"+saat+"');";
		Database.add(sql1);	 	
    }
}

}
sql1="SELECT islem_id FROM İslemler WHERE islem_türü='kiralama';";
rs1=Database.Sorgu(sql1);
	try {
		while(rs1.next()) 
		{
			islem_id.add(rs1.getInt(1));
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  if(!(islem_id.size()==0))	 
  {
	  int kiralayan_id=-1,kiralatan_id=-1,kiralanan_isletme_id=-1,kiralanan_isletme_alan_id=-1;        
	  String kira_baslangıc_tarihi="",kira_bitis_tarihi="",kira_baslangıc_saati="",kiralık_isletme_türü="";
	  for(int i=0; i<islem_id.size();i++)
	  {
		  sql1="SELECT islem_sahibiı_id FROM İslemler WHERE  islem_id="+islem_id.get(i)+";";
	      rs1=Database.Sorgu(sql1);
	      try {
			while(rs1.next())
			  {
				  kiralayan_id=rs1.getInt(1);
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int ctrl=-1;
	     sql1="SELECT COUNT(islem_id) FROM Kiralamalar WHERE islem_id="+islem_id.get(i)+";";
		rs1=Database.Sorgu(sql1);
		try {
			while(rs1.next())
			{
				ctrl=rs1.getInt(1);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(ctrl>0)
	      {
	      sql1="SELECT kiralatan_id,kiralanan_isletme_id,kira_baslangıc_tarihi,kira_bitis_tarihi,kira_baslangıc_saati FROM Kiralamalar WHERE  islem_id="+islem_id.get(i)+";";
	      rs1=Database.Sorgu(sql1);
	      try {
			while(rs1.next())
			  {
				  kiralatan_id=rs1.getInt("kiralatan_id");
				  kira_baslangıc_tarihi=rs1.getString("kira_baslangıc_tarihi");
				  kira_bitis_tarihi=rs1.getString("kira_bitis_tarihi");
				  kira_baslangıc_saati=rs1.getString("kira_baslangıc_saati");
			      kiralanan_isletme_id=rs1.getInt("kiralanan_isletme_id");
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    sql2="SELECT isletme_tür FROM İsletmeler WHERE isletme_id="+kiralanan_isletme_id+";";  
	    rs2=Database.Sorgu(sql2);
	    try {
			while(rs2.next())
			{
				kiralık_isletme_türü=rs2.getString(1);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	 long f = gun_saat_farkı(kira_baslangıc_tarihi,kira_bitis_tarihi,kira_baslangıc_saati,tarih,saat);     
	 System.out.println("kira_kontrol_gun_saat_fark->"+f);    
	 if(f>0)
	       {  

	    sql2="DELETE FROM Kiralamalar WHERE islem_id="+islem_id.get(i)+";"; 	  
	    Database.update(sql2);
	      sql1="SELECT alan_id FROM İsletmeler WHERE isletme_id="+kiralanan_isletme_id+";";
	    rs1=Database.Sorgu(sql1);
	    try {
			while(rs1.next())
			{
				kiralanan_isletme_alan_id=rs1.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    sql2="UPDATE Alanlar SET kullanıcı_id="+kiralatan_id+" WHERE alan_id="+kiralanan_isletme_alan_id+";";  
	    Database.update(sql2);
	    sql2="INSERT INTO İslemler (islem_sahibiı_id,islem_türü,islem_yer_tür,islem_tarihi,islem_saat) VALUES("+kiralayan_id+",'kiralama bitis','"+kiralık_isletme_türü+"','"+tarih+"','"+saat+"');";
	     Database.add(sql2);
	       }
	  }
	  
	  }
  }
  }

  public void end()
  {
	  ArrayList<Integer> kullanıcı_id= new ArrayList<Integer>();
	  int kullanıcı_yemek=-1,kullanıcı_esya=-1,kullanıcı_para=-1;
	sql1 = "SELECT kullanıcı_no FROM Kullanıcılar;";  
	  rs1=Database.Sorgu(sql1);
	 try {
		while(rs1.next()) 
		 {
			 kullanıcı_id.add(rs1.getInt(1));
		 }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	for(int i=0;i<kullanıcı_id.size();i++)	 
	{
		String k_tür="";
		sql1="SELECT kullanıcı_türü FROM Kullanıcılar WHERE kullanıcı_no="+kullanıcı_id.get(i)+";";
		rs1=Database.Sorgu(sql1);
		try {
			while(rs1.next())
			{
			          k_tür=rs1.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     if(k_tür.equals("oyuncu"))
	     {
	    	 sql2="SELECT kullanıcı_esya,kullanıcı_yemek,kullanıcı_para FROM Oyuncular WHERE kullanıcı_id="+kullanıcı_id.get(i)+";";
	          rs2=Database.Sorgu(sql2);
	          try {
				while(rs2.next())
				  {
					          kullanıcı_para=rs2.getInt("kullanıcı_para");
							  kullanıcı_yemek=rs2.getInt("kullanıcı_yemek");
							  kullanıcı_esya=rs2.getInt("kullanıcı_esya");
				  }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	       if(kullanıcı_yemek<=0 || kullanıcı_esya<=0)
	       {
	    	   System.out.println("silllllllllllll");
	    	   int y_id=-1;
	           ArrayList<Integer> alan_id = new ArrayList<Integer>();
	           sql1="SELECT kullanıcı_no FROM Kullanıcılar WHERE kullanıcı_türü='yönetici';";
	           rs1=Database.Sorgu(sql1);
	           try {
				while(rs1.next())
				   {
					   y_id=rs1.getInt(1);
				   }
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	           sql1="SELECT alan_id FROM Alanlar WHERE kullanıcı_id="+kullanıcı_id.get(i)+";";
	           rs1=Database.Sorgu(sql1);
	           try {
				while(rs1.next())
				   {
					   alan_id.add(rs1.getInt("alan_id"));
				   }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	           if(alan_id.size()>0)
	           {
	        	   for(int p=0;p<alan_id.size();p++) {
	        	   String t="";
	        	   sql1="SELECT alan_tür FROM Alanlar WHERE kullanıcı_id="+alan_id.get(p)+";";
		           rs1=Database.Sorgu(sql1);
		           try {
					while(rs1.next())
					   {
						   t=rs1.getString(1);
					   }
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		         if(t.equals("arsa"))
		         {
		        	 sql3="UPDATE Alanlar SET kullanıcı_id="+y_id+", alan_tür='arsa' WHERE alan_id="+alan_id.get(p)+";";
		        	 Database.update(sql3);
		         }
		         else {
		        	 sql3="DELETE FROM  İsletmeler WHERE alan_id="+alan_id.get(p)+";";
		        	 Database.update(sql3);
		        	 sql3="UPDATE Alanlar SET kullanıcı_id="+y_id+", alan_tür='arsa' WHERE alan_id="+alan_id.get(p)+";";
		        	 Database.update(sql3);
		         }
		           
	        	   }
	           }
	    	   sql3 ="DELETE FROM Kullanıcılar WHERE kullanıcı_no="+kullanıcı_id.get(i)+";";
	    	   Database.update(sql3);
	    	   i--;
	       }
	          
	     }
		
	}
	 
  }
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Database db = new Database();
		getDate();
		setBounds(0,0, 1300, 650);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(144, 238, 144));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		l_d.setHorizontalAlignment(SwingConstants.RIGHT);
		l_d.setFont(new Font("Tahoma", Font.PLAIN, 14));
		l_d.setBounds(1118, 10, 81, 41);
		contentPane.add(l_d);
		
		tf_d.setFont(new Font("Tahoma", Font.PLAIN, 15));
		 tf_d.setEditable(false);
	        tf_d.setText(tarih);
		tf_d.setBounds(1197, 10, 89, 41);
		contentPane.add(tf_d);
		tf_d.setColumns(10);
		
		 l1 = new JLabel("META-LAND'A HOŞ GELDİNİZ");
		l1.setFont(new Font("Ink Free", Font.ITALIC, 20));
		l1.setHorizontalAlignment(SwingConstants.CENTER);
		l1.setBounds(283, 10, 666, 116);
		contentPane.add(l1);
		
		 b1 = new JButton("GİRİS");
		b1.setBackground(new Color(147, 112, 219));
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		b1.setForeground(new Color(0, 0, 0));
		b1.setFont(new Font("Tahoma", Font.PLAIN, 17));
		b1.setBounds(307, 500, 309, 81);
		Image i1 = new ImageIcon(this.getClass().getResource("/giris.png")).getImage();
        b1.setIcon(new ImageIcon(i1));
        b1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
            Giris g = new Giris();      
			setVis();
			}}
        		);
		contentPane.add(b1);
		
	     b2 = new JButton("KAYIT");
		b2.setBackground(new Color(147, 112, 219));
		b2.setFont(new Font("Tahoma", Font.PLAIN, 17));
		b2.setBounds(764, 503, 309, 75);
		Image i2 = new ImageIcon(this.getClass().getResource("/kayıt.png")).getImage();
        b2.setIcon(new ImageIcon(i2));
        b2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Kayıt k = new Kayıt();
				setVis();
			}}
        
        		);
		contentPane.add(b2);
		
		JLabel img1 = new JLabel("");
		img1.setBounds(447, 177, 450, 271);
		Image i4= new ImageIcon(this.getClass().getResource("/kapak2.png")).getImage();
		img1.setIcon(new ImageIcon(i4));
		contentPane.add(img1);
		
		JLabel img2 = new JLabel("");
		img2.setBounds(10, 176, 416, 272);
		Image i5= new ImageIcon(this.getClass().getResource("/kapak3.png")).getImage();
		img2.setIcon(new ImageIcon(i5));
		contentPane.add(img2);
		
		JLabel img3 = new JLabel("");
		img3.setBounds(915, 177, 361, 271);
		Image i6= new ImageIcon(this.getClass().getResource("/kapak4.png")).getImage();
		img3.setIcon(new ImageIcon(i6));
		contentPane.add(img3);
		
		l_s.setHorizontalAlignment(SwingConstants.RIGHT);
		l_s.setFont(new Font("Tahoma", Font.PLAIN, 14));
		l_s.setBounds(1118, 57, 81, 41);
		contentPane.add(l_s);
		

	        tf_s.setEditable(false);
	        tf_s.setText(saat);
		tf_s.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tf_s.setColumns(10);
		tf_s.setBounds(1197, 57, 89, 41);
		contentPane.add(tf_s);
		
		JButton settings = new JButton("");
		settings.setBackground(new Color(144, 238, 144));
		settings.setBounds(1217, 108, 65, 59);
		Image i8 = new ImageIcon(this.getClass().getResource("/set.png")).getImage();
		settings.setIcon(new ImageIcon(i8));
		settings.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
		String tmp=tarih,tmp3="";
		tmp3= JOptionPane.showInputDialog(contentPane,"Lütfen Yeni Tarihi Giriniz:(xxxx-xx-xx)");
		     String []tmp2 = tmp3.split("-");
		     if(!(tmp2.length==3)) {
				   JOptionPane.showMessageDialog(contentPane,"Tarih Bos ya da Uygun Formatta Degil");	 
					 tf_s.setText(tmp);
		     }
		     else {   
		     int x1=tmp2[0].length();
		     int x2=tmp2[1].length();
		     int x3=tmp2[2].length();
	   if(tmp.isEmpty() || !(x1==4) || !(x2==2) ||  !(x3==2))
	   {
		   JOptionPane.showMessageDialog(contentPane,"Tarih Bos ya da Uygun Formatta Degil");
			 tf_s.setText(tmp);
	   }
	   else {
		   tf_d.setText(tmp3);
		   tarih=tmp3;
	   }
		     }
	 tmp=saat;tmp3="";	     
	   tmp3 = JOptionPane.showInputDialog(contentPane,"Lütfen Yeni Saati Giriniz:(xx:xx:xx)");
	     tmp2 = tmp3.split(":");
	     if(!(tmp2.length==3)) {
	  	   JOptionPane.showMessageDialog(contentPane,"Saat Bos ya da Uygun Formatta Degil");
	  	 tf_s.setText(tmp);
	     }
	     else {
	   int  x1=tmp2[0].length();
	    int  x2=tmp2[1].length();
	    int  x3=tmp2[2].length();
 if(tmp.isEmpty() || !(x1==2) || !(x2==2) ||  !(x3==2))
 {
	   JOptionPane.showMessageDialog(contentPane,"Saat Bos ya da Uygun Formatta Degil");
		 tf_s.setText(tmp);
 }
 else {
	 tf_s.setText(tmp3);
	 saat=tmp3;
 } 
 
	     }
System.out.println("güncel_saat->"+saat);
System.out.println("güncel_tarih->"+tarih);	     
 duzen(tarih,saat);
 duzen2(tarih,saat);
 end();
			}
		});
		contentPane.add(settings);
		duzen(tarih,saat);
		duzen2(tarih,saat);
		end();

		this.setVisible(true);

	}
}
