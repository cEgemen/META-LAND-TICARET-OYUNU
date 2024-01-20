import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mysql.cj.xdevapi.Result;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;

public class İsletmeciSettings extends JFrame {

	private JPanel contentPane;
	private JTextField tf3 = new JTextField(),tf4 = new JTextField(),tf5 = new JTextField(),tf6 = new JTextField();
    private JLabel l1,l2,l3=new JLabel(),l4=new JLabel(),l5=new JLabel(),l6=new JLabel(); 
	private JButton b1,b2,b4,b5;
	private int komisyon=-1,esya_ücret=-1,yemek_ücret=-1,kullanıcı_ücret=-1,s_ücret=-1,k_ücret=-1,kiralık_ücret=-1,satılık_ücret=-1;
	private String sql1,sql2,sql3,sql4;
	private ResultSet rs1,rs2,rs3,rs4;
	boolean ctrl1=false,ctrl2=false;
	int isletme_alan_id=-1;
	  private String saat="";
	    public void getsaat()
	    {
	 	    Date d = new Date();
	 	    SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss");
	         saat=format2.format(d);
	         System.out.println("saat->"+saat);
	    }
	public void setVis()
	{
		this.setVisible(false);
	}
	public void begin(int belirtec,int isletme_id)
	{
		System.out.println("isletme_id=>"+isletme_id);
	  if(!(belirtec==4))
	  {
		sql1="SELECT alan_id FROM İsletmeler WHERE isletme_id="+isletme_id+";";
		rs1=Database.Sorgu(sql1);
		try {
			while(rs1.next())
			{
				isletme_alan_id=rs1.getInt(1);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	  }
		if(belirtec==4)
		{
			tf3.setText("Mahsul Yok");
			tf4.setText("Maas Yok");
			  tf3.setEnabled(false);
			  tf4.setEnabled(false);

		}
		if(belirtec==3)
		{
		sql1="SELECT komisyon FROM Emlak WHERE isletmeci_id="+isletme_id+";";	
	rs1=Database.Sorgu(sql1);
	     try {
			while(rs1.next())
			 {
				 komisyon=rs1.getInt(1);
				 System.out.println("komisyon=>"+komisyon);
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sql2="SELECT kullanıcı_ücret FROM İsletmeler WHERE isletme_id="+isletme_id+";";
	   rs2=Database.Sorgu(sql2);  
	     try {
			while(rs2.next())
			 {
				 kullanıcı_ücret=rs2.getInt(1);
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 tf4.setText(Integer.toString(kullanıcı_ücret));
		 tf3.setText(Integer.toString(komisyon));
		}
		
		if(belirtec==2)
		{
		sql1="SELECT esya_ücreti FROM Magaza WHERE isletmeci_id="+isletme_id+";";	
	rs1=Database.Sorgu(sql1);
	     try {
			while(rs1.next())
			 {
				 esya_ücret=rs1.getInt(1);
				 System.out.println("esya_ücret=>"+esya_ücret);
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sql2="SELECT kullanıcı_ücret FROM İsletmeler WHERE isletme_id="+isletme_id+";";
	   rs2=Database.Sorgu(sql2);  
	     try {
			while(rs2.next())
			 {
				 kullanıcı_ücret=rs2.getInt(1);
				 System.out.println("kullanıcı_ücret=>"+kullanıcı_ücret);
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     tf4.setText(Integer.toString(kullanıcı_ücret));
		 tf3.setText(Integer.toString(esya_ücret));
		}
		
		if(belirtec==1)
		{
		sql3="SELECT yiyecek_ücreti FROM Market WHERE isletmeci_id="+isletme_id+";";	
		 System.out.println("dasssssssssssssssssssss");
	rs3=Database.Sorgu(sql3);
	     try {
			while(rs3.next())
			 {
				 yemek_ücret=rs3.getInt(1);
				 System.out.println("yiyecek_ücret=>"+yemek_ücret);
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sql2="SELECT kullanıcı_ücret FROM İsletmeler WHERE isletme_id="+isletme_id+";";
	   rs2=Database.Sorgu(sql2);  
	     try {
			while(rs2.next())
			 {
				 kullanıcı_ücret=rs2.getInt(1);
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     tf4.setText(Integer.toString(kullanıcı_ücret));
		 tf3.setText(Integer.toString(yemek_ücret));
		}
	
if(!(belirtec==4))
		{	
	int ctrl=-1;	
	sql1="SELECT COUNT(kl_id) as X FROM KiralıkList WHERE isletme_id="+isletme_id+";";	
	rs1=Database.Sorgu(sql1);
	 try {
		while(rs1.next())
		 {
			 ctrl=rs1.getInt("X");
			 if(!(ctrl==0))
			 {
				 ctrl1=true;
			 }
		 }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	ctrl=-1;
	sql1="SELECT COUNT(sl_id) as X FROM SatısList WHERE satılık_yer_alan_id="+isletme_alan_id+";";	
	rs1=Database.Sorgu(sql1);
	 try {
		while(rs1.next())
		 {
			 ctrl=rs1.getInt("X");
			 if(!(ctrl==0))
			 {
				 ctrl2=true;
			 }
		 }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	if(ctrl1==true) { 
  sql1="SELECT kiralama_ücret FROM KiralıkList WHERE isletme_id="+isletme_id+";";
  rs1=Database.Sorgu(sql1);
  try {
	while(rs1.next())
	  {
		  k_ücret=rs1.getInt(1);
	  }
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
  tf5.setText(Integer.toString(k_ücret));
	}
	else {
	  tf5.setText("X");
	  tf5.setEnabled(false);
	}
	if(ctrl2==true) { 
		  sql1="SELECT satıs_fiyat FROM SatısList WHERE satılık_yer_alan_id="+isletme_alan_id+";";
		  rs1=Database.Sorgu(sql1);
		  try {
			while(rs1.next())
			  {
				  s_ücret=rs1.getInt(1);
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 tf6.setText(Integer.toString(s_ücret)); 
			}
			else {
			  tf6.setText("0");
			  tf6.setEnabled(false);
			}
	}		 
else {
	tf5.setText("Kiralık Fiyatı Yok");
	tf5.setEnabled(false);
	int ctrl=-1;	
	sql1="SELECT COUNT(sl_id) as X FROM SatısList WHERE satılık_yer_alan_id="+isletme_id+";";	
	rs1=Database.Sorgu(sql1);
	 try {
		while(rs1.next())
		 {
			 ctrl=rs1.getInt("X");
			 if(!(ctrl==0))
			 {
				 ctrl2=true;
			 }
		 }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	if(ctrl2==true) { 
		  sql1="SELECT satıs_fiyat FROM SatısList WHERE satılık_yer_alan_id="+isletme_id+";";
		  rs1=Database.Sorgu(sql1);
		  try {
			while(rs1.next())
			  {
				  s_ücret=rs1.getInt(1);
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 tf6.setText(Integer.toString(s_ücret)); 
			}
			else {
			  tf6.setText("0");
			  tf6.setEnabled(false);
			}
}	
	}
	public void setLabel(int belirtec)
	{
		if(belirtec==4)
		{
			l3.setText("Mahsul Degeri=> ");
			l4.setText("Calısan Ücret=> ");
		    l5.setText("Kıralık Ücreti=> "); 
		    l6.setText("Satılık Ücreti=> "); 
		}
		if(belirtec==3)
		{
			l3.setText("Komisyon Degeri=> ");
			l4.setText("Calısan Ücret=> ");
		    l5.setText("Kıralık Ücreti=> "); 
		    l6.setText("Satılık Ücreti=> "); 

		}
		if(belirtec==2)
		{
			l3.setText("Esya Fiyat Degeri=> ");
			l4.setText("Calısan Ücret=> ");
		    l5.setText("Kıralık Ücreti=> "); 
		    l6.setText("Satılık Ücreti=> "); 

		}
		if(belirtec==1)
		{
			l3.setText("Yiyecek Fiyat Degeri=> ");
			l4.setText("Calısan Ücret=> ");
		    l5.setText("Kıralık Ücreti=> "); 
		    l6.setText("Satılık Ücreti=> "); 

		}
		
		
	}
	public İsletmeciSettings(String isletme_tür,int isletme_id,int belirtec,int alan_no) {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(0,0,1000,600);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(221, 160, 221));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel header = new JLabel("SETTİNGS");
		header.setFont(new Font("Tahoma", Font.PLAIN, 20));
		header.setHorizontalAlignment(SwingConstants.CENTER);
		header.setBounds(218, 10, 654, 50);
		contentPane.add(header);
		
		begin(belirtec,isletme_id);
		setLabel(belirtec);
		 l1 = new JLabel();
		if(isletme_tür.equals("arsa")) {
			l1.setText("Arsa Sahibi=>"+Giris.kullanıcı_ad+" "+Giris.kullanıcı_soyad);
		}
		else {
			l1.setText("İsletme Sahibi=>"+Giris.kullanıcı_ad+" "+Giris.kullanıcı_soyad);
		}
		l1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		l1.setBounds(10, 70, 380, 50);
		contentPane.add(l1);
		l2 = new JLabel();
		if(isletme_tür.equals("arsa")) {
			l2.setText("Alan Türü=> "+isletme_tür+" ve Alan İD=>"+isletme_id);
		}
		else {
			l2.setText("İsletmenin Türü=> "+isletme_tür+" ve İsletme İD=>"+isletme_id);
		}
		l2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		l2.setBounds(10, 123, 380, 50);
		contentPane.add(l2);
		
		l3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		l3.setBounds(10, 219, 168, 43);
		contentPane.add(l3);
		
		
		tf3.setBounds(178, 219, 201, 43);
		contentPane.add(tf3);
		tf3.setColumns(10);
		
		
		l4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		l4.setBounds(10, 272, 168, 43);
		contentPane.add(l4);
		
		tf4.setColumns(10);
		tf4.setBounds(178, 272, 201, 43);
		contentPane.add(tf4);
		
		l5.setFont(new Font("Tahoma", Font.PLAIN, 14));
		l5.setBounds(10, 325, 168, 43);
		contentPane.add(l5);
		
		
		tf5.setColumns(10);
		tf5.setBounds(178, 325, 201, 43);
		contentPane.add(tf5);
		
		
		l6.setFont(new Font("Tahoma", Font.PLAIN, 14));
		l6.setBounds(10, 378, 168, 43);
		contentPane.add(l6);
		
		
		tf6.setColumns(10);
		tf6.setBounds(178, 378, 201, 43);
		contentPane.add(tf6);
		setVisible(true);
		
		JButton back = new JButton("");
		back.setBackground(new Color(176, 196, 222));
		back.setBounds(934, 10, 52, 77);
		Image x1 = new ImageIcon(this.getClass().getResource("/door.png")).getImage();
		back.setIcon(new ImageIcon(x1));
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
                
				setVis();
				İslemPanel is = new İslemPanel(alan_no,"");
			}
		});
		contentPane.add(back);
		
		 b1 = new JButton("Kiralık Listeden Cıkar");
		b1.setBounds(32, 513, 156, 50);
		b1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(isletme_tür.equals("arsa"))
				{
					JOptionPane.showMessageDialog(contentPane,"Arsalar Kiralanamaz...");				
				}	
			else
				{
				if(ctrl1==true)
				{
        	  String s="",t=Database.dAte();
        	  getsaat();
        	  s="INSERT INTO İslemler(islem_sahibiı_id,islem_türü,islem_yer_tür,islem_tarihi,islem_saat) VALUES("+Giris.kullanıcı_id+",'listeden cıkarma','"+isletme_tür+"','"+t+"','"+saat+"');";		
        		Database.add(s);	  
			sql1="DELETE  FROM  KiralıkList WHERE isletme_id="+isletme_id+";";
            Database.update(sql1);
    		ctrl1=false;
            begin(belirtec,isletme_id);
    		contentPane.updateUI();
            JOptionPane.showMessageDialog(contentPane,"İşletme Kiralık Listesinden Basarıyla Kaldırıldı...");
				}
          else {
        	  JOptionPane.showMessageDialog(contentPane,"İşletme Kiralık Listesinde Yok...");
          }
			}
        			}

		});
		contentPane.add(b1);
		
	    b2 = new JButton("Satıs Listeden Cıkar");
		b2.setBounds(230, 513, 156, 50);
		b2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			if(isletme_tür.equals("arsa"))
			{
			   int syc=-1;
				sql1="SELECT COUNT(sl_id) FROM SatısList WHERE satılık_yer_alan_id="+isletme_id+";";	
			rs1=Database.Sorgu(sql1);
			try {
				while(rs1.next())
				{
					syc=rs1.getInt(1);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(syc>0)
			{
				String s="",t=Database.dAte();
				getsaat();
				s="INSERT INTO İslemler(islem_sahibiı_id,islem_türü,islem_yer_tür,islem_tarihi,islem_saat) VALUES("+Giris.kullanıcı_id+",'listeden cıkarma','"+isletme_tür+"','"+t+"','"+saat+"');";		
					Database.add(s);	 	
				 System.out.println("isletme_id=>"+isletme_id);
					sql1="DELETE  FROM SatısList WHERE satılık_yer_alan_id="+isletme_id+";";
		            Database.update(sql1);
		    	   ctrl2=false;
		            begin(belirtec,isletme_id);
		    		contentPane.updateUI();
		    		JOptionPane.showMessageDialog(contentPane,"Satılık Listesinden Basarıyla Kaldırıldı...");

			}
			else
			{
				  JOptionPane.showMessageDialog(contentPane,"Bu Yer Satıs Listesinde Bulunmuyor...");	
			}
			}
			else	
				{	
				if(ctrl2==true) {	
				String s="",t=Database.dAte();
				getsaat();
				s="INSERT INTO İslemler(islem_sahibiı_id,islem_türü,islem_yer_tür,islem_tarihi,islem_saat) VALUES("+Giris.kullanıcı_id+",'listeden cıkarma','"+isletme_tür+"','"+t+"','"+saat+"');";		
					Database.add(s);	 	
				  sql1="DELETE  FROM SatısList WHERE satılık_yer_alan_id="+isletme_alan_id+";";
		            Database.update(sql1);
		    		ctrl2=false;
		            begin(belirtec,isletme_id);
		    		JOptionPane.showMessageDialog(contentPane,"Satılık Listesinden Basarıyla Kaldırıldı...");

				}
			else {
			  JOptionPane.showMessageDialog(contentPane,"Bu Yer Satıs Listesinde Bulunmuyor...");	
			}
			}
			}
			
		});
		contentPane.add(b2);
		
		JButton b3 = new JButton("Kaydet");
		b3.setBounds(455, 295, 133, 50);
		b3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			  String a1,a2,a3,a4;
			  if(belirtec==4) {
				   a4=tf6.getText();
				   if(!(a4==null))
				   {
					 satılık_ücret=Integer.parseInt(a4);  
				   }
			  }
			  else {
				  a1=tf3.getText();
				   if(!(a1==null))
				   {
					   komisyon=Integer.parseInt(a1);
				   }
				   a2=tf4.getText();
				   if(!(a2==null))
				   {
					 kullanıcı_ücret=Integer.parseInt(a2);  
				   }
					a3=tf5.getText();
					   if(!(a3==null))
					   {
						  kiralık_ücret=Integer.parseInt(a3);
					   }
					   a4=tf6.getText();
					   if(!(a4==null))
					   {
						 satılık_ücret=Integer.parseInt(a4);  
					   }
			  }
			   if(belirtec==4)
			   {
				   if(ctrl2==true)
				    {
				    	 sql4="UPDATE SatısList SET satıs_fiyat="+satılık_ücret+" WHERE satılık_yer_alan_id="+isletme_id+";";
						    Database.update(sql4);
				    }
				    JOptionPane.showMessageDialog(contentPane,"Güncelleme Basarılı...");
			   }
				if(belirtec==3)
				{	
					sql1="UPDATE İsletmeler SET kullanıcı_ücret="+kullanıcı_ücret+" WHERE isletme_id="+isletme_id+";";
				    Database.update(sql1);
				    sql2="UPDATE Emlak SET komisyon="+komisyon+" WHERE isletmeci_id="+isletme_id+";";
				    Database.update(sql2);
				    if(ctrl1==true)
				    {
				    	 sql3="UPDATE KiralıkList SET kiralama_ücret="+kiralık_ücret+" WHERE isletme_id="+isletme_id+";";
						    Database.update(sql3);
				    }
				    if(ctrl2==true)
				    {
				    	 sql4="UPDATE SatısList SET satıs_fiyat="+satılık_ücret+" WHERE satılık_yer_alan_id="+isletme_alan_id+";";
						    Database.update(sql4);
				    }
				    JOptionPane.showMessageDialog(contentPane,"Güncelleme Basarılı...");
				}
				if(belirtec==2)
				{
					sql1="UPDATE İsletmeler SET kullanıcı_ücret="+kullanıcı_ücret+" WHERE isletme_id="+isletme_id+";";
				    Database.update(sql1);
				    sql2="UPDATE Emlak SET komisyon="+komisyon+" WHERE isletmeci_id="+isletme_id+";";
				    Database.update(sql2);
				    if(ctrl1==true)
				    {
				    	 sql3="UPDATE KiralıkList SET kiralama_ücret="+kiralık_ücret+" WHERE isletme_id="+isletme_id+";";
						    Database.update(sql3);
				    }
				    if(ctrl2==true)
				    {
				    	 sql4="UPDATE SatısList SET satıs_fiyat="+satılık_ücret+" WHERE satılık_yer_alan_id="+isletme_alan_id+";";
						    Database.update(sql4);
				    }
				    JOptionPane.showMessageDialog(contentPane,"Güncelleme Basarılı...");
				}
				if(belirtec==1)
				{
					sql1="UPDATE İsletmeler SET kullanıcı_ücret="+kullanıcı_ücret+" WHERE isletme_id="+isletme_id+";";
				    Database.update(sql1);
				    sql2="UPDATE Emlak SET komisyon="+komisyon+" WHERE isletmeci_id="+isletme_id+";";
				    Database.update(sql2);
				    if(ctrl1==true)
				    {
				    	 sql3="UPDATE KiralıkList SET kiralama_ücret="+kiralık_ücret+" WHERE isletme_id="+isletme_id+";";
						    Database.update(sql3);
				    }
				    if(ctrl2==true)
				    {
				    	 sql4="UPDATE SatısList SET satıs_fiyat="+satılık_ücret+" WHERE satılık_yer_alan_id="+isletme_alan_id+";";
						    Database.update(sql4);
				    }
				    JOptionPane.showMessageDialog(contentPane,"Güncelleme Basarılı...");
				}            
    
				
				
			}
		});
		contentPane.add(b3);
		
		 b4 = new JButton("Satılık Ekle");
		b4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ctrl2==false) {
					
					JOptionPane.showMessageDialog(contentPane,"Bir Emlakcı Seciniz Ve Satısa Veriniz...");
				if(isletme_tür.equals("arsa"))
				{
				setVis();	
TabloPane2 tp2 = new TabloPane2(isletme_id,Giris.kullanıcı_id,0,4,isletme_tür,alan_no,"",belirtec);	
				}
				else {
					setVis();
TabloPane2 tp2 = new TabloPane2(isletme_id,Giris.kullanıcı_id,0,4,isletme_tür,alan_no,"",belirtec);	
				}
				
				}
				else {
				  JOptionPane.showMessageDialog(contentPane,"Bu Yer Satıs Listesinde Bulunuyor...");	
				}
			}
		});
		b4.setBounds(430, 512, 168, 52);
		contentPane.add(b4);
		
		 b5 = new JButton("Kiralık Ekle");
		b5.setBounds(645, 513, 133, 50);
		b5.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			if(isletme_tür.equals("arsa"))
			{
				JOptionPane.showMessageDialog(contentPane,"Arsalar Kiraya Verilemez...");				
			}	
			else {		
				if(ctrl1==false) {
				JOptionPane.showMessageDialog(contentPane,"Bir Emlakcı Seciniz Ve İsletmenizi Kiraya Veriniz...");
				setVis();
				TabloPane2 tp2 = new TabloPane2(isletme_id,Giris.kullanıcı_id,0,3,isletme_tür,alan_no,"",belirtec);	
				}
				else {
				  JOptionPane.showMessageDialog(contentPane,"Bu İsletme Kiralık Listesinde Bulunuyor...");	
				}
				
			}
				
			}
			
		});
		contentPane.add(b5);
		
		JButton b6 = new JButton("Yükseltme");
		b6.setBounds(815, 513, 145, 51);
		b6.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
if(belirtec==4)
{
JOptionPane.showMessageDialog(contentPane,"Arsalar Yükseltilemez...");
}
			else		
			{				
  	int seviye=-1,ibilgi_id=-1,seviye_yükseltme_ücreti=-1,sev,is_id=-1,kullanıcı_para=-1;			
    String tür="";
  	sql1="SELECT isletme_bilgi_id,isletme_sistem_id,isletme_tür FROM İsletmeler WHERE isletme_id="+isletme_id+";";
	rs1=Database.Sorgu(sql1);  			 
	 try {
		while(rs1.next()) 		
		 {
			 ibilgi_id=rs1.getInt("isletme_bilgi_id");
			 is_id=rs1.getInt("isletme_sistem_id");
		     tür=rs1.getString("isletme_tür");
		 }
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	 sql1="SELECT kullanıcı_para FROM Oyuncular WHERE kullanıcı_id="+Giris.kullanıcı_id+";";
		rs1=Database.Sorgu(sql1);  			 
		 try {
			while(rs1.next()) 		
			 {
				 kullanıcı_para=rs1.getInt(1);
			 }
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	 
	  sql1="SELECT seviye_yükseltme_ücreti FROM İsletmeSistem WHERE is_id="+is_id+";";
		rs1=Database.Sorgu(sql1);  			 
		 try {
			while(rs1.next()) 		
			 {
				 seviye_yükseltme_ücreti=rs1.getInt(1);
			 }
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	 
		  sql1="SELECT seviye FROM iBilgileri WHERE islem_bilgileri_id="+ibilgi_id+";";
			rs1=Database.Sorgu(sql1);  			 
			 try {
				while(rs1.next()) 		
				 {
					 seviye=rs1.getInt(1);
				 }
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		if(!(seviye==1000)) {	 
int ücret=seviye_yükseltme_ücreti+((seviye-1)*2000);			 
int cevap=JOptionPane.showConfirmDialog(contentPane,"Seviye Yükseltmek İcin "+ücret+" Ödemelisiniz.Onaylıyor Musunu?");	
	 if(cevap==JOptionPane.YES_OPTION)
	 {
		 if(ücret<=kullanıcı_para)
		 {
			 seviye=seviye+1;
	         kullanıcı_para=kullanıcı_para-ücret;
			 sql1="SELECT islem_bilgileri_id FROM iBilgileri WHERE seviye="+seviye+";";		 
			 rs1=Database.Sorgu(sql1);
			 try {
				while(rs1.next())
				 {
					 ibilgi_id=rs1.getInt(1);
				 }
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	 sql2="UPDATE Oyuncular SET kullanıcı_para="+kullanıcı_para+" WHERE kullanıcı_id="+Giris.kullanıcı_id+";";        
	  Database.update(sql2);
	  String date=Database.dAte();
	  getsaat();
	  sql3="UPDATE İsletmeler SET isletme_bilgi_id="+ibilgi_id+", seviye_tarihi='"+date+"' WHERE isletme_id="+isletme_id+";";
	  Database.update(sql3);
	   sql4="INSERT INTO İslemler(islem_sahibiı_id,islem_türü,islem_yer_tür,islem_tarihi,islem_saat) VALUES("+Giris.kullanıcı_id+",'yükseltme','"+tür+"','"+date+"','"+saat+"');";	
		Database.add(sql4);
		 }
		 else {
			 JOptionPane.showMessageDialog(contentPane,"Paranız Yükseltme Ücretine Yetmedi ve İslem İptal Edildi");
		 }
	 }
	 else {
		 JOptionPane.showMessageDialog(contentPane,"Yükseltme İptal Edildi");
	 }
		}
		else {
			JOptionPane.showMessageDialog(contentPane,"İsletmeniz Son Seviyedir Bu Yüzden Yükseltme Yapamazsınız");
		}
			}
			}
		});
		contentPane.add(b6);
		
		
		
	}
}
