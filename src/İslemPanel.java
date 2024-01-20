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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;

public class İslemPanel extends JFrame {

	private JPanel contentPane;
    private JButton b1,b2,b3,b4;
    private JLabel l2 = new JLabel("......"),l3 = new JLabel(""),l4 = new JLabel(),l1 = new JLabel();
    private JButton back;
    private int isletme_id;
    private String ad=null,soyad=null,sql;
	private int k_id=-1,a_id=-1,isletme_bilgi_id=-1,kapasite=-1,seviye=-1;
	private ResultSet rs;
	private String date1=Database.dAte(),date2,date3;
	private String cevap3=null;
	private String alan_tür=null,isletme_tür=null,mesaj=null;
	private JButton m;
	private boolean click2=false,sahip=false,yönetici=false,yönetici_emlak=false,giris=true;
	private JButton b5;
	private int kullanıcı_yemek=-1,kullanıcı_esya=-1,kullanıcı_para=-1,isletme_sahibi_para=-1;
	private String kullanıcı_tür="",isletme_sahibi_tür="";
	private String saat="";
	private int emlak_isletme_id=-1;	

	 public void getsaat()
	    {
	 	    Date d = new Date();
	 	    SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss");
	         saat=format2.format(d);
	         System.out.println("saat->"+saat);
	    }
	    
public String dateAyar(String gun)
{
	 // Güncel tarihi alma
    LocalDate currentDate = LocalDate.now();
    
    // Eklemek istediğiniz gün sayısı
    int daysToAdd = Integer.parseInt(gun);
    
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
public boolean gun_saat_farkı(String baslangıc_tarihi,String bitis_tarihi,String x)
{
	String dt_baslangıc=baslangıc_tarihi;
    String dt_bitis=bitis_tarihi;
 String [] tmp3=dt_baslangıc.split("-");
 String [] tmp4=dt_bitis.split("-");
 String []  tmp6=x.split("-");
	 int t=0,t1=0,t2=0,t3=0;
 LocalDate currentDate = LocalDate.of(Integer.parseInt(tmp6[0]),Integer.parseInt(tmp6[1]),Integer.parseInt(tmp6[2]));
	 System.out.println("currentDate=>"+currentDate);
  LocalDate date2 = LocalDate.of(Integer.parseInt(tmp3[0]),Integer.parseInt(tmp3[1]),Integer.parseInt(tmp3[2]));
  LocalDate date3 = LocalDate.of(Integer.parseInt(tmp4[0]),Integer.parseInt(tmp4[1]),Integer.parseInt(tmp4[2]));
  System.out.println("baslangıc_tarihi->"+date2);
  Period period =date2.until(currentDate);
  Period period2=date3.until(currentDate);
  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String cd = currentDate.format(formatter);
     String [] tmp2 = cd.split("-");      
  long fark =(Integer.parseInt(tmp3[0])-Integer.parseInt(tmp2[0]))*365 +period.toTotalMonths() * 30 + period.getDays();
  long fark2 =(Integer.parseInt(tmp4[0])-Integer.parseInt(tmp2[0]))*365 +period2.toTotalMonths() * 30 + period2.getDays();
  System.out.println("gun_saat_fark metod içi=>"+fark);
 if(fark>=0)
 {
   if(fark2<=0)
   {
	   return true;
   }
   else
   {
	   return false;
   }
 }
 else
 {
	 return false;
 }
 
}
public void setVis()
{
	this.setVisible(false);
}
public void dflt(int alan_no)
{
	int c=0;
	boolean fark=false;
	getsaat();
	String calısma_saatleri="",calısma_bitis="",calısma_baslangıc="";
	sql="SELECT  COUNT(calısanlar_id)  FROM Calısanlar WHERE kullanıcı_id="+Giris.kullanıcı_id+";";
	rs=Database.Sorgu(sql);
	try {
		while(rs.next())
		{
		c=rs.getInt(1);
		}
	} catch (SQLException e2) {
		// TODO Auto-generated catch block
		e2.printStackTrace();
	}
if(c>0)	
	{
	sql="SELECT  calısma_saatleri,calısma_baslangıc_tarihi,calısma_bitis_tarihi  FROM Calısanlar WHERE kullanıcı_id="+Giris.kullanıcı_id+";";
	rs=Database.Sorgu(sql);
	try {
		while(rs.next())
		{
		calısma_saatleri=rs.getString("calısma_saatleri");
		calısma_bitis=rs.getString("calısma_bitis_tarihi");
		calısma_baslangıc=rs.getString("calısma_baslangıc_tarihi");
		}
	} catch (SQLException e2) {
		// TODO Auto-generated catch block
		e2.printStackTrace();
	}
 fark=gun_saat_farkı(calısma_baslangıc,calısma_bitis,date1);
if(fark==true)
{
	if(!(calısma_saatleri.isEmpty()))
	{
	  boolean c1=false,c2=false;
	  String [] tmp = saat.split(":");//güncel saat
	  String [] t=calısma_saatleri.split("-");
	  String [] tmp2 =t[0].split(":");//calısma baslangıc saat
	  String [] tmp3 =t[1].split(":");//calısam bitis saat
	  
 	if(Integer.parseInt(tmp[0])>Integer.parseInt(tmp2[0]))    
 	{
 		c1=true;
 	}
 	else if((Integer.parseInt(tmp[0])== Integer.parseInt(tmp2[0])) && (Integer.parseInt(tmp[1])> Integer.parseInt(tmp2[1]))) 
 			{
 		c1=true;
 			}
	else if((Integer.parseInt(tmp[0])== Integer.parseInt(tmp2[0])) && (Integer.parseInt(tmp[1]) == Integer.parseInt(tmp2[1])) && (Integer.parseInt(tmp[2]) > Integer.parseInt(tmp2[2]))) 
		{
	c1=true;
		} 
 if(c1==true)	
 {
		if(Integer.parseInt(tmp[0])<Integer.parseInt(tmp3[0]))    
	 	{
	 		c2=true;
	 	}
	 	else if((Integer.parseInt(tmp[0])== Integer.parseInt(tmp3[0])) && (Integer.parseInt(tmp[1])< Integer.parseInt(tmp3[1]))) 
	 			{
	 		c2=true;
	 			}
		else if((Integer.parseInt(tmp[0])== Integer.parseInt(tmp3[0])) && (Integer.parseInt(tmp[1]) == Integer.parseInt(tmp3[1])) && (Integer.parseInt(tmp[2]) < Integer.parseInt(tmp3[2]))) 
			{
		c2=true;
			} 
 }
 if(c2==true)
 {
	 giris=false;
 }
	}
}
else {
	giris=true;
}
}
else
{
giris=true;
}
 System.out.println("giris->"+giris);
 System.out.println("fark->"+fark);	
	sql="SELECT kullanıcı_id,alan_id,alan_tür FROM Alanlar WHERE alan_no="+alan_no+";";
	rs=Database.Sorgu(sql);
	try {
		while(rs.next())
		{
			k_id=rs.getInt("kullanıcı_id");
			a_id=rs.getInt("alan_id");
			alan_tür=rs.getString("alan_tür");
			if(alan_tür.equals("isletme"))
			{
				click2=true;
			}
			if(k_id==Giris.kullanıcı_id)
			{
				sahip=true;
			}
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	sql ="SELECT kullanıcı_ad,kullanıcı_soyad,kullanıcı_türü FROM Kullanıcılar WHERE kullanıcı_no="+k_id+";";
	rs=Database.Sorgu(sql);
	try {
		while(rs.next())
		{
			ad=rs.getString("kullanıcı_ad");
			soyad=rs.getString("kullanıcı_soyad");
			isletme_sahibi_tür=rs.getString("kullanıcı_türü");
			if(isletme_sahibi_tür.equals("yönetici"))
			{
				yönetici=true;
			}
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	l2.setText(ad+" "+soyad);

	if(click2==true)
	{
	sql="SELECT isletme_id,isletme_tür FROM İsletmeler WHERE alan_id="+a_id+";";
	rs=Database.Sorgu(sql);
   try {
	while(rs.next())
	   {
		   isletme_id=rs.getInt("isletme_id");
		   isletme_tür=rs.getString("isletme_tür");
		   if(isletme_tür.equals("emlak") && yönetici==true)
		   {
			   yönetici_emlak=true;
		   }
	   }
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
   sql="SELECT isletme_bilgi_id FROM İsletmeler WHERE isletme_id="+isletme_id+";";	
   rs=Database.Sorgu(sql);
   try {
		while(rs.next())
		  {
			  isletme_bilgi_id=rs.getInt(1);
		  }
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
   sql="SELECT kapasite,seviye FROM iBilgileri WHERE islem_bilgileri_id="+isletme_bilgi_id+";";	
   rs=Database.Sorgu(sql);
   try {
		while(rs.next())
		  {
			 kapasite=rs.getInt("kapasite");
			 seviye=rs.getInt("seviye");
		  }
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
   l3.setText("İsletme Seviye=> "+seviye);
   l4.setText("İsletme İd'si=> "+isletme_id);
}
	else {
		 l3.setText("Alan Seviye=> "+0);
		   l4.setText("Alan ID'si=> "+a_id);
	}
   sql="SELECT kullanıcı_türü FROM Kullanıcılar WHERE kullanıcı_no="+Giris.kullanıcı_id+";";
   rs=Database.Sorgu(sql);
   try {
	while(rs.next())
	   {
		   kullanıcı_tür=rs.getString(1);
	   }
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
   if(kullanıcı_tür.equals("oyuncu"))
   {
	   sql="SELECT kullanıcı_para,kullanıcı_esya,kullanıcı_yemek FROM Oyuncular WHERE kullanıcı_id="+Giris.kullanıcı_id+";";
       rs=Database.Sorgu(sql);
       try {
		while(rs.next())
		   {
			   kullanıcı_esya=rs.getInt("kullanıcı_esya");
			   kullanıcı_yemek=rs.getInt("kullanıcı_yemek");
			   kullanıcı_para=rs.getInt("kullanıcı_para");
			   
		   }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
       
   }
  if(isletme_sahibi_tür.equals("oyuncu"))
  {

	   sql="SELECT kullanıcı_para FROM Oyuncular WHERE kullanıcı_id="+k_id+";";
      rs=Database.Sorgu(sql);
      try {
		while(rs.next())
		   {
			   isletme_sahibi_para=rs.getInt("kullanıcı_para");
		   }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
String s="",t=Database.dAte();
  s="INSERT INTO İslemler(islem_sahibiı_id,islem_türü,islem_yer_tür,islem_tarihi,islem_saat) VALUES("+Giris.kullanıcı_id+",'alana giris','sanal ortam','"+t+"','"+saat+"')";		
	Database.add(s);	 
   
}
	public İslemPanel(int alan_no,String mesaj) {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(500,0, 864, 417);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 250, 205));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel header = new JLabel("İSLEM EKRANI");
		header.setFont(new Font("Tahoma", Font.PLAIN, 18));
		header.setHorizontalAlignment(SwingConstants.CENTER);
		header.setBounds(312, 0, 369, 40);
		contentPane.add(header);
		
		dflt(alan_no);
		
		 if(alan_tür.equals("arsa"))
		 {
			 l1.setText("Arsa Sahibi=>");
		 }
		 else {
			 l1.setText("İsletme Sahibi=>");
		 }
		l1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		l1.setBounds(10, 35, 110, 40);
		contentPane.add(l1);
		
		l2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		l2.setBounds(117, 35, 306, 40);
		contentPane.add(l2);
		
		 l3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		l3.setBounds(10, 128, 413, 40);
		contentPane.add(l3);
		
		l4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		l4.setBounds(10, 85, 219, 33);
		contentPane.add(l4);
				
		 b1 = new JButton("Satın Alım");
		b1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		b1.setBounds(10, 308, 138, 62);
		b1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
	if(giris==false)	
	{
		JOptionPane.showMessageDialog(contentPane,"Calısma Saatlerinde Satın Alma İslemi Yapamazsınız");
	}
	else
	{
		if(click2==false && sahip==true)
        {
        	JOptionPane.showMessageDialog(contentPane,"Burası Bir Arsa Ve  Burayı Daha Önce Satın Almıssınız...");
        }
        else if(click2==false && sahip==false)
        {
        int tmp=-1,emlak_id=-1;  	
        sql="SELECT COUNT(sl_id) as X  FROM SatısList WHERE satılık_yer_alan_id="+a_id+";";
        rs=Database.Sorgu(sql);
        try {
			while(rs.next())
			{
				tmp=rs.getInt("X");
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        if(tmp==1)
        {
        	sql="SELECT emlak_id FROM SatısList WHERE satılık_yer_alan_id="+a_id+";";
            rs=Database.Sorgu(sql);
            try {
    			while(rs.next())
    			{
    				emlak_id=rs.getInt(1);
    			}
    		} catch (SQLException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
        }
        
         if(tmp==0)	
         {
        	 JOptionPane.showMessageDialog(contentPane,"Bu Arsa Satılık Degil Fakat Mesaj Atarak Sansınızı Deneyebilirsiniz...");
         }
         else {
          int emlak_isletme_id=-1;	 
        	sql="SELECT isletmeci_id FROM Emlak WHERE emlak_id="+emlak_id+";";   
        	rs=Database.Sorgu(sql);
        	try {
				while(rs.next())
				{
				   emlak_isletme_id=rs.getInt(1);	
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	JOptionPane.showMessageDialog(contentPane,"Bu Arsa Satıs Listesinde ve Bu Arsayı "+emlak_isletme_id+" ID'li Emlaktan Satın Alabilirsiniz...");
         }
        }
			else	
				{
			   	
                String cevap=null;
                if(isletme_tür.equals("market"))
                {
                int yiyecek_ücret=-1;
                sql="SELECT yiyecek_ücreti FROM Market WHERE isletmeci_id="+isletme_id+";";
                rs=Database.Sorgu(sql);
                try {
					while(rs.next())
					{
						yiyecek_ücret=rs.getInt(1);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                
                  cevap =JOptionPane.showInputDialog(contentPane,"Marketteki Yiyeceklerin Tane Fiyatı "+yiyecek_ücret+"'dir.Lütfen Almak İstediginiz Yiyecek Adetini Giriniz:");         
               
                  int p1=kullanıcı_para-(yiyecek_ücret*Integer.parseInt(cevap));
                  int y1=kullanıcı_yemek+Integer.parseInt(cevap);
                  int p2=isletme_sahibi_para+(yiyecek_ücret*Integer.parseInt(cevap));
           
           if ( sahip== true && yönetici==false )
          {
        	   sql="UPDATE Oyuncular SET kullanıcı_yemek="+y1+" WHERE kullanıcı_id="+Giris.kullanıcı_id+";";    
               Database.update(sql);   
               String s="",t=Database.dAte();
               getsaat();
               s="INSERT INTO İslemler(islem_sahibiı_id,islem_türü,islem_yer_tür,islem_tarihi,islem_saat) VALUES("+Giris.kullanıcı_id+",'satın alma','market','"+t+"','"+saat+"');";		
             	Database.add(s);
          }
          else if (sahip==false && yönetici==true)
          {
        	  sql="UPDATE Oyuncular SET kullanıcı_para="+p1+", kullanıcı_yemek="+y1+" WHERE kullanıcı_id="+Giris.kullanıcı_id+";";    
              Database.update(sql);  
              String s="",t=Database.dAte();
              getsaat();
              s="INSERT INTO İslemler(islem_sahibiı_id,islem_türü,islem_yer_tür,islem_tarihi,islem_saat) VALUES("+Giris.kullanıcı_id+",'satın alma','market','"+t+"','"+saat+"');";		
            	Database.add(s);
            	 s="INSERT INTO İslemler(islem_sahibiı_id,islem_türü,islem_yer_tür,islem_tarihi,islem_saat) VALUES("+k_id+",'satma','market','"+t+"','"+saat+"');";		
             	Database.add(s);
          }
          else if(sahip==false && yönetici==false)
          {
        	  if(kullanıcı_tür.equals("oyuncu"))
        	  {
        		  sql="UPDATE Oyuncular SET kullanıcı_para="+p1+", kullanıcı_yemek="+y1+" WHERE kullanıcı_id="+Giris.kullanıcı_id+";";    
                  Database.update(sql);
                  sql="UPDATE Oyuncular SET kullanıcı_para="+p2+" WHERE kullanıcı_id="+k_id+";";    
                  Database.update(sql);	   
                  String s="",t=Database.dAte();
                  getsaat();
                  s="INSERT INTO İslemler(islem_sahibiı_id,islem_türü,islem_yer_tür,islem_tarihi,islem_saat) VALUES("+Giris.kullanıcı_id+",'satın alma','market','"+t+"','"+saat+"');";		
                	Database.add(s);
                	 s="INSERT INTO İslemler(islem_sahibiı_id,islem_türü,islem_yer_tür,islem_tarihi,islem_saat) VALUES("+k_id+",'satma','market','"+t+"','"+saat+"');";		
                 	Database.add(s);
        	  }
          }
          
            int islem_id=-1,syc=0,syc2=1;
               sql="SELECT COUNT(islem_sahibiı_id) FROM İslemler WHERE islem_sahibiı_id="+Giris.kullanıcı_id+";";
               rs=Database.Sorgu(sql);
               try {
				while(rs.next())
				   {
					  syc=rs.getInt(1);   
				   }
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
               sql="SELECT islem_id FROM İslemler WHERE islem_sahibiı_id="+Giris.kullanıcı_id+";";
               rs=Database.Sorgu(sql);
               try {
				while(rs.next())
				   {
					 if(syc2==syc) {
					  islem_id=rs.getInt(1);
					  break;
					 }
					 syc2++;

					 }
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}              
               String s="",t=Database.dAte();
               getsaat();
               sql="INSERT INTO SatınAlımlar(islem_id,satan_id,satıs_yer_alan_id,satıs_fiyatı,satıs_tarihi,satıs_baslangıc_saati) VALUES("+islem_id+","+k_id+","+a_id+","+yiyecek_ücret*Integer.parseInt(cevap)+",'"+t+"','"+saat+"');";
           Database.add(sql);
           if ( sahip== true && yönetici==false ) {
         	 s="INSERT INTO İslemler(islem_sahibiı_id,islem_türü,islem_yer_tür,islem_tarihi,islem_saat) VALUES("+k_id+",'satma','market','"+t+"','"+saat+"');";		
          	Database.add(s);    
           }     
           
                }
                else if(isletme_tür.equals("magaza"))
                {
                	 cevap=null;
                    if(isletme_tür.equals("magaza"))
                    {
                    int esya_ücret=-1;
                    sql="SELECT esya_ücreti FROM Magaza WHERE isletmeci_id="+isletme_id+";";
                    rs=Database.Sorgu(sql);
                    try {
    					while(rs.next())
    					{
    						esya_ücret=rs.getInt(1);
    					}
    				} catch (SQLException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				}
                    
                      cevap =JOptionPane.showInputDialog(contentPane,"Magaza Esyaların Tane Fiyatı "+esya_ücret+"'dir.Lütfen Almak İstediginiz Esya Adetini Giriniz:");         
                      int p1=kullanıcı_para-(esya_ücret*Integer.parseInt(cevap));
                      int e1=kullanıcı_esya+Integer.parseInt(cevap);
                      int p2=isletme_sahibi_para+(esya_ücret*Integer.parseInt(cevap));
                      
                      if ( sahip== true && yönetici==false )
                     {
                   	   sql="UPDATE Oyuncular SET kullanıcı_yemek="+e1+" WHERE kullanıcı_id="+Giris.kullanıcı_id+";";    
                          Database.update(sql);   
                          String s="",t=Database.dAte();
                          getsaat();
                          s="INSERT INTO İslemler(islem_sahibiı_id,islem_türü,islem_yer_tür,islem_tarihi,islem_saat) VALUES("+Giris.kullanıcı_id+",'satın alma','market','"+t+"','"+saat+"');";		
                        	Database.add(s);
                     }
                     else if (sahip==false && yönetici==true)
                     {
                   	  sql="UPDATE Oyuncular SET kullanıcı_para="+p1+", kullanıcı_yemek="+e1+" WHERE kullanıcı_id="+Giris.kullanıcı_id+";";    
                         Database.update(sql);  
                         String s="",t=Database.dAte();
                         getsaat();
                         s="INSERT INTO İslemler(islem_sahibiı_id,islem_türü,islem_yer_tür,islem_tarihi,islem_saat) VALUES("+Giris.kullanıcı_id+",'satın alma','market','"+t+"','"+saat+"');";		
                       	Database.add(s);
                       	 s="INSERT INTO İslemler(islem_sahibiı_id,islem_türü,islem_yer_tür,islem_tarihi,islem_saat) VALUES("+k_id+",'satma','market','"+t+"','"+saat+"');";		
                        	Database.add(s);
                     }
                     else if(sahip==false && yönetici==false)
                     {
                   	  if(kullanıcı_tür.equals("oyuncu"))
                   	  {
                   		  sql="UPDATE Oyuncular SET kullanıcı_para="+p1+", kullanıcı_yemek="+e1+" WHERE kullanıcı_id="+Giris.kullanıcı_id+";";    
                             Database.update(sql);
                             sql="UPDATE Oyuncular SET kullanıcı_para="+p2+" WHERE kullanıcı_id="+k_id+";";    
                             Database.update(sql);	   
                             String s="",t=Database.dAte();
                             getsaat();
                             s="INSERT INTO İslemler(islem_sahibiı_id,islem_türü,islem_yer_tür,islem_tarihi,islem_saat) VALUES("+Giris.kullanıcı_id+",'satın alma','market','"+t+"','"+saat+"');";		
                           	Database.add(s);
                           	 s="INSERT INTO İslemler(islem_sahibiı_id,islem_türü,islem_yer_tür,islem_tarihi,islem_saat) VALUES("+k_id+",'satma','market','"+t+"','"+saat+"');";		
                            	Database.add(s);
                   	  }
                     }
                int islem_id=-1,syc=0,syc2=1;
                   sql="SELECT COUNT(islem_sahibiı_id) FROM İslemler WHERE islem_sahibiı_id="+Giris.kullanıcı_id+";";
                   rs=Database.Sorgu(sql);
                   try {
    				while(rs.next())
    				   {
    					  syc=rs.getInt(1);   
    				   }
    			} catch (SQLException e4) {
    				// TODO Auto-generated catch block
    				e4.printStackTrace();
    			}
                   sql="SELECT islem_id FROM İslemler WHERE islem_sahibiı_id="+Giris.kullanıcı_id+";";
                   rs=Database.Sorgu(sql);
                   try {
    				while(rs.next())
    				   {
    					 if(syc2==syc) {
    					  islem_id=rs.getInt(1);   
    					 }
    					 syc2++;
    					 }
    			} catch (SQLException e4) {
    				// TODO Auto-generated catch block
    				e4.printStackTrace();
    			}
                   String s="",t=Database.dAte();
                   getsaat();
                   sql="INSERT INTO SatınAlımlar(islem_id,satan_id,satıs_yer_alan_id,satıs_fiyatı,satıs_tarihi,satıs_baslangıc_saati) VALUES("+islem_id+","+k_id+","+a_id+","+esya_ücret*Integer.parseInt(cevap)+",'"+t+"','"+saat+"');";
                   Database.add(sql);
                   if ( sahip== true && yönetici==false ) {
                   	 s="INSERT INTO İslemler(islem_sahibiı_id,islem_türü,islem_yer_tür,islem_tarihi,islem_saat) VALUES("+k_id+",'satma','market','"+t+"','"+saat+"');";		
                    	Database.add(s);    
                     }      
  
                    }
                }
                else {
                	 cevap=null;
                     if(isletme_tür.equals("emlak"))
                     {
                     int emlak_id=-1;
                     sql="SELECT emlak_id FROM Emlak WHERE isletmeci_id="+isletme_id+";";
                     rs=Database.Sorgu(sql);
                     try {
     					while(rs.next())
     					{
     						emlak_id=rs.getInt(1);
     					}
     				} catch (SQLException e1) {
     					// TODO Auto-generated catch block
     					e1.printStackTrace();
     				}      
               
                   JOptionPane.showMessageDialog(contentPane,"Bu Emlak'a İlan Veren İsletmelerin Tablosu Üzerinden Satın Alma İsleminizi Yapabilirsiniz");         
                    setVis();
                   TabloPane2 tp2 = new TabloPane2(isletme_id,k_id,emlak_id,2,"emlak",alan_no,mesaj,-1);
                 }
                	
                	
                }
			}
			}
			}
		});
		contentPane.add(b1);
		
		m = new JButton("Mesaj Gönder");
		m.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		m.setBounds(10, 178, 120, 40);
		m.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
		if(sahip==true) {
		JOptionPane.showMessageDialog(contentPane,"Kendinize Mesaj Atamazsınız...");	
		}
		else {
			setVis();
            MesajPane mp = new MesajPane(alan_no,k_id);
		}	
		}
			
		});
		contentPane.add(m);
		
		 b2 = new JButton("Kiralama");
		b2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		b2.setBounds(158, 308, 140, 62);
		b2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
        if(giris==false)        
        {
        	JOptionPane.showMessageDialog(contentPane,"Calısama Saatlerinde Kiralama İslemi Yapamazsınız");
        }	
			else		
			{
				  if(click2==false )
			        {
			        
JOptionPane.showMessageDialog(contentPane,"Arsalar Kiralanamaz Fakat Satın Almayı Deneyebilirsin...");
			         
			        }
						else	
							{	
							int emlak_id=-1,onay=-1;
							sql="SELECT COUNT(kl_id) as X FROM KiralıkList WHERE isletme_id="+isletme_id+";";
			                rs=Database.Sorgu(sql);
							try {
								while(rs.next())
								{
								 	onay=rs.getInt("X");
 	
								}
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						if(onay==1) {	
							sql="SELECT emlak_id FROM KiralıkList WHERE isletme_id="+isletme_id+";";
			                rs=Database.Sorgu(sql);
							try {
								while(rs.next())
								{
								 		emlak_id=rs.getInt("emlak_id");
								}
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							sql="SELECT isletmeci_id FROM Emlak WHERE emlak_id="+emlak_id+";";
							rs=Database.Sorgu(sql);
							try {
								while(rs.next())
								{
								 		emlak_isletme_id=rs.getInt(1);
								}
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}		
							
						}
							String cevap=null;
			                if(isletme_tür.equals("market"))
			                {
			                if(onay==0) {	
			                JOptionPane.showMessageDialog(contentPane,"Bu Market Kiralık Listesinde Degildir...");
			                }
			                else {
				                JOptionPane.showMessageDialog(contentPane,"Bu Market "+emlak_isletme_id+" ID'li Emlaktan Kiralama İsleminizi Tamamlayabilirsiniz..."); 	
			                }
			                }
			                else if(isletme_tür.equals("magaza"))
			                {
			                	 cevap=null;
			                    if(isletme_tür.equals("magaza"))
			                    {
			                        if(onay==0) {	
						                JOptionPane.showMessageDialog(contentPane,"Bu Magaza Kiralık Listesinde Degildir...");
						                }
						                else {
							                JOptionPane.showMessageDialog(contentPane,"Bu Magaza "+emlak_isletme_id+" ID'li Emlaktan Kiralama İsleminizi Tamamlayabilirsiniz..."); 	
						                }
			                }
			                }
			                else {
			                	 cevap=null;
			                     if(isletme_tür.equals("emlak"))
			                    {
			                sql="SELECT emlak_id FROM Emlak WHERE isletmeci_id="+isletme_id+";";  
			                  rs=Database.Sorgu(sql) ;
			                  try {
								while(rs.next())
								  {
									  emlak_id=rs.getInt(1);
								  }
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
			                    	 if(onay==1) 
			                   {
			                	JOptionPane.showMessageDialog(contentPane,"Bu Emlagı Burada Kiralayabilirsiniz");   
			                   }
			 				  JOptionPane.showMessageDialog(contentPane,"Bu Emlak Üzeriden Kiralama Listesine Eklenmis İsletmeleri Kiralayabilirsiniz..."); 	
			                 setVis();
			 				  TabloPane2 tp2 = new TabloPane2(isletme_id,k_id,emlak_id,1,"emlak",alan_no,mesaj,-1);
			                    	 
			                    	 
			                    	 }
			                	
			                	
			                }
						}
				
			}	
			}	
		});
		contentPane.add(b2);
		
		 b3 = new JButton("Calısma");
		b3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		b3.setBounds(326, 308, 146, 62);
		b3.addActionListener(new ActionListener() {

			
			@Override
			public void actionPerformed(ActionEvent e) {
int calısan_say=-1,maas=-1,count=-1;
sql="SELECT COUNT(calısanlar_id)  FROM Calısanlar WHERE kullanıcı_id="+Giris.kullanıcı_id+";";            
rs=Database.Sorgu(sql);
try {
	while(rs.next())
	{
		count=rs.getInt(1);
	}
} catch (SQLException e2) {
	// TODO Auto-generated catch block
	e2.printStackTrace();
}
if(count==0)
{
sql="SELECT calısan_sayısı,kullanıcı_ücret FROM İsletmeler WHERE isletme_id="+isletme_id+";";	
rs=Database.Sorgu(sql);
try {
	while(rs.next())
	 {
		 calısan_say=rs.getInt("calısan_sayısı");
		 System.out.println("calısan_Sayısı=>"+calısan_say);
		 maas=rs.getInt("kullanıcı_ücret");
	 }
} catch (SQLException e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
}
 if(click2==false)
  {
  	JOptionPane.showMessageDialog(contentPane,"Bu Alan Arsa Oldugu İcin Calısma Birimi Yoktur..."); 	  
  }
  else if(sahip==true)
  {
	  JOptionPane.showMessageDialog(contentPane,"Calısan Kapasitemiz=>"+kapasite+" Calısan Sayımız=>"+calısan_say+" ve Calısan Ücretleri"+maas+"'dir");
  }
else 
{
    if(yönetici==true)
    {
    	JOptionPane.showMessageDialog(contentPane,"İsletme Uygundur.isletme Sartları Geliyor...");
        int cevap1 = JOptionPane.showConfirmDialog(contentPane,"Maasınız "+maas+" birimdir.Onaylıyor Musunuz?");
		if(cevap1==JOptionPane.YES_OPTION)
		{
	String cevap2;
	String [] x;
	String [] y1;
	String [] y2;
	 TabloPane tp = new TabloPane(alan_no,mesaj,isletme_id,3);
	 while(true)
	 {
		  
		  cevap2=JOptionPane.showInputDialog("Calışma Saatlerini Giriniz(09:00:00-14:00:00,vb):");
			 x = cevap2.split("-");
			 y1=x[0].split(":");
			 y2=x[1].split(":");
		if(!(x.length==2) || !(y1[0].length()==2) || !(y1[1].length()==2) || !(y1[2].length()==2) ||  !(y2[0].length()==2)  ||  !(y2[1].length()==2) || !(y2[2].length()==2))
		{ 
			JOptionPane.showMessageDialog(contentPane,"Saat Formatı Yanlıs Girildi...");
	         continue;	
		}
		break;
	 }
	 cevap3=JOptionPane.showInputDialog("Calısma Gün Sayınızı Giriniz(2,5,10,vb):");
	 int gun=Integer.parseInt(cevap3)+1;
	 date2=dateAyar("1");
	 date3=dateAyar(Integer.toString(gun));
	sql="INSERT INTO Calısanlar (kullanıcı_id,isletme_id,calısma_baslangıc_tarihi,calısma_bitis_tarihi,calısma_gun_sayısı,calısma_saatleri,calısma_basvuru_tarihi) VALUES("
	+ Giris.kullanıcı_id+","+isletme_id+",'"+date2+"','"+date3+"',"+cevap3+",'"+cevap2+"','"+date1+"');";
	Database.add(sql);
	int tmp=calısan_say+1;
	System.out.println("calısan_sayısı=>"+calısan_say);
	sql ="UPDATE İsletmeler SET calısan_sayısı="+tmp+" WHERE isletme_id="+isletme_id+";";
	Database.update(sql);
	String s="",t=Database.dAte();
	getsaat();
	s="INSERT INTO İslemler(islem_sahibiı_id,islem_türü,islem_yer_tür,islem_tarihi,islem_saat) VALUES("+Giris.kullanıcı_id+",'calısaya baslama','"+isletme_tür+"','"+t+"','"+saat+"');";		
		Database.add(s);	 
		}
		else {
			JOptionPane.showMessageDialog(contentPane,"Maası Kabul Ederseniz Tekrar Bekleriz...");
		}
    }
else
	{
				if(!(calısan_say<kapasite))
				{
					JOptionPane.showMessageDialog(contentPane,"Bu İsletmenin Kapasitesi Doludur...");
				}
				else {
					JOptionPane.showMessageDialog(contentPane,"İsletme Uygundur.isletme Sartları Geliyor...");
                    int cevap1 = JOptionPane.showConfirmDialog(contentPane,"Maasınız "+maas+" birimdir.Onaylıyor Musunuz?");
					if(cevap1==JOptionPane.YES_OPTION)
					{
						String cevap2;
						String [] x ;
						String [] y1;
						String [] y2;
						 TabloPane tp = new TabloPane(alan_no,mesaj,isletme_id,3);
						
						 while(true)
						 {
							 cevap2=JOptionPane.showInputDialog("Calışma Saatlerini Giriniz(09:00:00-14:00:00,vb):");
							 x = cevap2.split("-");
							 y1=x[0].split(":");
							 y2=x[1].split(":");
						if(!(x.length==2) || !(y1[0].length()==2) || !(y1[1].length()==2) || !(y1[2].length()==2) ||  !(y2[0].length()==2)  ||  !(y2[1].length()==2) || !(y2[2].length()==2))
						{ 
							JOptionPane.showMessageDialog(contentPane,"Saat Formatı Yanlıs Girildi...");
					         continue;	
						}
						break;
						 }
										
				 cevap3=JOptionPane.showInputDialog("Calısma Gün Sayınızı Giriniz(2,5,10,vb):");
				 int gun=Integer.parseInt(cevap3)+1;
				 date2=dateAyar("1");
				 date3=dateAyar(Integer.toString(gun));
				 System.out.println("date2=>"+date2);
				 System.out.println("date3=>"+date3);
				sql="INSERT INTO Calısanlar (kullanıcı_id,isletme_id,calısma_baslangıc_tarihi,calısma_bitis_tarihi,calısma_gun_sayısı,calısma_saatleri,calısma_basvuru_tarihi) VALUES("
				+ Giris.kullanıcı_id+","+isletme_id+",'"+date2+"','"+date3+"',"+cevap3+",'"+cevap2+"','"+date1+"');";
				Database.add(sql);
				int tmp=calısan_say+1;
				sql ="UPDATE İsletmeler SET calısan_sayısı="+tmp+" WHERE isletme_id="+isletme_id+";";
				Database.update(sql);
				String s="",t=Database.dAte();
				getsaat();
				s="INSERT INTO İslemler(islem_sahibiı_id,islem_türü,islem_yer_tür,islem_tarihi,islem_saat) VALUES("+Giris.kullanıcı_id+",'calısaya baslama','"+isletme_tür+"','"+t+"','"+saat+"');";		
					Database.add(s);	 
					
					}
					else {
						JOptionPane.showMessageDialog(contentPane,"Maası Kabul Ederseniz Tekrar Bekleriz...");
					}
					
				}
}
}
			}
else {
	JOptionPane.showMessageDialog(contentPane,"Mevcut Bir İşiniz Oldugu İçin Çalışamazsınız...");
}
			}
		});
		contentPane.add(b3);
		
		 b4 = new JButton();
		if(alan_tür.equals("arsa"))
		{
			b4.setText("Arsa İslemleri");
		}
		else {
			b4.setText("İsletme İslemleri");
		}
		b4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		b4.setBounds(502, 308, 165, 62);
		b4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
		if(giris==false)
		{
			JOptionPane.showMessageDialog(contentPane,"Calısma Saatinizde İsinize Odaklanınız");
		}
			else	
				{		
			if(sahip==false)
			{
			JOptionPane.showMessageDialog(contentPane,"Bu İsletmenin Sahibi Olmadıgınız İcin Bu Özelligi Kullanma Yetkiniz Yoktur...");	
			}
			else
				{	
             if(click2==false)
            {
            	 setVis();
            	 İsletmeciSettings is = new İsletmeciSettings("arsa",a_id,4,alan_no);
            }
             else {
                 if(isletme_tür.equals("emlak"))    
                 {
                	 setVis();
                	 İsletmeciSettings is = new İsletmeciSettings(isletme_tür,isletme_id,3,alan_no);
                 }
                 else if(isletme_tür.equals("market"))
                 {
                	 setVis();
                	 İsletmeciSettings is = new İsletmeciSettings(isletme_tür,isletme_id,1,alan_no);
                 }
                 else if(isletme_tür.equals("magaza"))
                 {
                	 setVis();
                	 İsletmeciSettings is = new İsletmeciSettings(isletme_tür,isletme_id,2,alan_no);
                 }
             }
			}
			} 
			}	
			
		});
		contentPane.add(b4);
		
		back = new JButton("");
		back.setBackground(new Color(176, 196, 222));
		back.setBounds(800, 0, 50, 75);
		Image i1 = new ImageIcon(this.getClass().getResource("/door.png")).getImage();
		back.setIcon(new ImageIcon(i1));
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String s="",t=Database.dAte(),tur="";
				getsaat();
				s="INSERT INTO İslemler(islem_sahibiı_id,islem_türü,islem_yer_tür,islem_tarihi,islem_saat) VALUES("+Giris.kullanıcı_id+",'alandan cıkıs','sanal ortam','"+t+"','"+saat+"');";		
					Database.add(s);	
				s="SELECT kullanıcı_türü FROM Kullanıcılar WHERE kullanıcı_no="+Giris.kullanıcı_id+";";	
				rs=Database.Sorgu(s);
				try {
					while(rs.next())
					{
						tur=rs.getString(1);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				setVis();
				if(tur.equals("yönetici"))
				{
					YöneticiOyun yo = new YöneticiOyun();
				}
				else {
                OyuncuOyun oo =new OyuncuOyun();
			}
			}
			
		});
		contentPane.add(back);
		
		b5 = new JButton("İsletme Kur");
		b5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	  if(giris==false)
	  {
		  JOptionPane.showMessageDialog(contentPane,"Calısma Saatlerinde İsletme Kuramazsınız");
	  }
			else		
				{		
		if(yönetici_emlak==true)
	      {	  
	    	  setVis();
	    	  AlanSecimPane asp = new AlanSecimPane(alan_no,mesaj);
	      }
			else if(click2==false && sahip==true && yönetici_emlak==false)   
				{					
			String ad="",soyad="";
				sql ="SELECT kullanıcı_ad,kullanıcı_soyad FROM Kullanıcılar WHERE kullanıcı_türü='yönetici';";	
				rs=Database.Sorgu(sql);	
				try {
					while(rs.next())	
					{
						ad=rs.getString("kullanıcı_ad");
						soyad=rs.getString("kullanıcı_soyad");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(contentPane,"İsletme Kurma İsleminizi "+ad+" "+soyad+" Kişinin Sahip Oldugu Emlaktan Gercekleştirebilirsiniz...");
				
				}
				else {
					
					JOptionPane.showMessageDialog(contentPane,"Burası Arsa Degil ya da Bu Arsanın Sahibi Siz Degilsiniz...");
				}
								
			}
		
			}
		});
		b5.setFont(new Font("Tahoma", Font.PLAIN, 14));
		b5.setBounds(702, 309, 138, 62);
		contentPane.add(b5);
		
		setVisible(true);
	}
}
