import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.SwingConstants;
import java.awt.Color;

public class OyuncuOyun extends JFrame implements ActionListener{

	private JPanel contentPane,p1;
	private JLabel l1,l2=new JLabel("-1"),l3,l4=new JLabel("-1"),l5,l6=new JLabel("-1"), l7 = new JLabel("");
    private JButton m;
	private Alan alan = new Alan();
    private İslemPanel ip;
    private int mesaj_say=-1,x=3,y=3; 
    private String saat="";
    public void getsaat()
    {
 	    Date d = new Date();
 	    SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss");
         saat=format2.format(d);
         System.out.println("saat->"+saat);
    }
   
    public void alan_boyut_ayrımı() {
    	String sql,boyut="3x3";
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
           	String[] alan_boyut_ayrımı = boyut.split("x");
           	x=Integer.parseInt(alan_boyut_ayrımı[0]);
           	y=Integer.parseInt(alan_boyut_ayrımı[1]);
    }
    public void Action_and_Image()
    {
    	for(int i=0; i<x ; i++ )
    	{
    		for(int j=0; j<y ; j++)
    		{
    			alan.alan[i][j].addActionListener(this);
    			String tür=null,sql;
    			int id=-1;
    			Image x;
    			ResultSet rs=null;
    			sql="SELECT alan_id,alan_tür FROM Alanlar WHERE alan_no="+alan.alan[i][j].alan_no+";";
    			rs=Database.Sorgu(sql);
    			try {
					while(rs.next())
					{
						id=rs.getInt("alan_id");
						tür=rs.getString("alan_tür");
					 System.out.println("türrrr"+tür);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			if(tür.equals("arsa"))
    			{				
    	     			x = new ImageIcon(this.getClass().getResource("/zemin.png")).getImage();
    	     			alan.alan[i][j].setIcon(new ImageIcon(x));
    			}
    			else {
    			sql="SELECT isletme_tür FROM İsletmeler WHERE alan_id="+id+";";
    			rs=Database.Sorgu(sql);
    			try {
					while(rs.next())
					{
						tür=rs.getString(1);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
    			
    		 if(tür.equals("emlak"))
    		{
    			x = new ImageIcon(this.getClass().getResource("/emlak.png")).getImage();
    			alan.alan[i][j].setIcon(new ImageIcon(x));
    			alan.alan[i][j].setBackground(new Color(175,214,255));
    			alan.alan[i][j].alan_tür="emlak";
    		}
    		else if(tür.equals("magaza"))
    		{
    			x = new ImageIcon(this.getClass().getResource("/magaza.png")).getImage();
    			alan.alan[i][j].setIcon(new ImageIcon(x));
    			alan.alan[i][j].setBackground(new Color(175,214,255));
    			alan.alan[i][j].alan_tür="mazgaza";
    		}
    		else if(tür.equals("market"))
    		{
    			x = new ImageIcon(this.getClass().getResource("/market.png")).getImage();
    			alan.alan[i][j].setIcon(new ImageIcon(x));
    			alan.alan[i][j].setBackground(new Color(175,214,255));
    			alan.alan[i][j].alan_tür="market";
    		}
    		}
    	}
    		
    	}
    }
   
	public void kullanıcı_deger_ayar()
	{
		String sql=null;
			int	k_no=0,b_esya=-1,b_para=-1,b_yemek=-1;
		ResultSet rs=null;
		sql="SELECT kullanıcı_no FROM Kullanıcılar WHERE kullanıcı_ad='"+Giris.kullanıcı_ad+"' AND kullanıcı_soyad='"+Giris.kullanıcı_soyad
				+"' AND kullanıcı_sifre="+Giris.kullanıcı_sifre+";";
		 rs=Database.Sorgu(sql);
		 try {
			while(rs.next())
			 {
				 k_no=rs.getInt(1);
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	sql="SELECT kullanıcı_yemek,kullanıcı_esya,kullanıcı_para FROM Oyuncular WHERE kullanıcı_id="+k_no+";";	
	  rs=Database.Sorgu(sql); 
	    try {
			while(rs.next())
			{
				b_yemek=rs.getInt("kullanıcı_yemek");
				b_esya=rs.getInt("kullanıcı_esya");
				b_para=rs.getInt("kullanıcı_para");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   l2.setText(Integer.toString(b_esya));
	   l4.setText(Integer.toString(b_yemek));
	   l6.setText(Integer.toString(b_para));
	   l7.setText("Kullanıcı=>"+Giris.kullanıcı_ad+" "+Giris.kullanıcı_soyad);
  sql="SELECT COUNT(iletisim_id) as iletisim_say FROM İletisimler WHERE alıcı_id="+Giris.kullanıcı_id+";";
	 rs=Database.Sorgu(sql); 
  try {
	while(rs.next())
	  {
		  mesaj_say=rs.getInt(1);
		  System.out.println("mesaj sayısı="+mesaj_say);
	  }
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
	}
  public void setVis()
  {
	  this.setVisible(false);
  }
	public OyuncuOyun() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0,0,1300,650);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(216, 191, 216));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		Image i1 = new ImageIcon(this.getClass().getResource("/esya.png")).getImage();
		Image i2 = new ImageIcon(this.getClass().getResource("/yemek.png")).getImage();
		Image i3 = new ImageIcon(this.getClass().getResource("/para.png")).getImage();

		 p1 = new JPanel();
		p1.setBounds(214, 41, 934, 572);
		alan_boyut_ayrımı();
		p1.setLayout(new GridLayout(x,y));
		contentPane.add(p1);
		alan.build1(alan,p1);
		
		JButton b1 = new JButton("Kiralık İlanlar");
		b1.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		b1.setBounds(1158, 576, 128, 37);
		b1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			 	
             JButton b= (JButton) e.getSource();
			  String label=b.getText();
			 if(label.contains("Kiralık"))
			 {
				 setVis();
				 Object [] kolon = {"İsletme Türü","Kiralama Ücreti","Calısan Sayısı","Kiralayan Kisi","Kiralam İcin Kullanılack İsletme ID"}; 
				 Kiralık_Satılık_TabloPane kstp = new Kiralık_Satılık_TabloPane(kolon,label,1,2);
			 }
			 else {
				setVis();
				 Object [] kolon = {"İsletme Türü","Satıs Ücreti","Calısan Sayısı","Satan Kisi","Satıs İcin Kullanılack İsletme ID"}; 
				 Kiralık_Satılık_TabloPane kstp = new Kiralık_Satılık_TabloPane(kolon,label,2,2);
				 
			 }
				
			}
			
			
		});
		contentPane.add(b1);
		
		JButton b2 = new JButton("Satılık İlanlar");
		b2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		b2.setBounds(1158, 529, 128, 37);
		b2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				 JButton b= (JButton) e.getSource();
				  String label=b.getText();
				 if(label.contains("Kiralık"))
				 {
					 setVis();
					 Object [] kolon = {"İsletme Türü","Kiralama Ücreti","Calısan Sayısı","Kiralayan Kisi","Kiralam İcin Kullanılack İsletme ID"}; 
					 Kiralık_Satılık_TabloPane kstp = new Kiralık_Satılık_TabloPane(kolon,label,1,2);
				 }
				 else {
					 setVis();
					 Object [] kolon = {"İsletme Türü","Satıs Ücreti","Calısan Sayısı","Satan Kisi","Satıs İcin Kullanılack İsletme ID"}; 
					 Kiralık_Satılık_TabloPane kstp = new Kiralık_Satılık_TabloPane(kolon,label,2,2);
					 
				 }
				
			}
			
		});
		contentPane.add(b2);
		
		JButton b4 = new JButton("Kisisel Bilgiler");
		b4.setFont(new Font("Tahoma", Font.PLAIN, 15));
		b4.setBounds(1158, 482, 128, 37);
		b4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			setVis();
			Kullanıcı_Bilgi_Pane kbp = new Kullanıcı_Bilgi_Pane(Giris.kullanıcı_id,1);	
			}
			
			
		});
		contentPane.add(b4);
		
		JLabel lblNewLabel = new JLabel("META-LAND");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(339, 0, 632, 31);
		contentPane.add(lblNewLabel);
		
		JLabel l1 = new JLabel("->");
		l1.setHorizontalAlignment(SwingConstants.CENTER);
		l1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		l1.setBounds(10, 86, 86, 27);
		l1.setIcon(new ImageIcon(i1));
		contentPane.add(l1);
		
	
		l2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		l2.setBounds(106, 85, 59, 28);
		contentPane.add(l2);
		
		JLabel l3 = new JLabel("->");
		l3.setHorizontalAlignment(SwingConstants.CENTER);
		l3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		l3.setBounds(10, 123, 86, 27);
		l3.setIcon(new ImageIcon(i2));
		contentPane.add(l3);
		
		
		l4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		l4.setBounds(106, 123, 59, 27);
		contentPane.add(l4);
		
		JLabel l5 = new JLabel("->");
		l5.setHorizontalAlignment(SwingConstants.CENTER);
		l5.setFont(new Font("Tahoma", Font.PLAIN, 15));
		l5.setBounds(10, 160, 86, 27);
		l5.setIcon(new ImageIcon(i3));
		contentPane.add(l5);
		
	
		l6.setFont(new Font("Tahoma", Font.PLAIN, 14));
		l6.setBounds(106, 160, 59, 27);
		contentPane.add(l6);
		
		l7.setFont(new Font("Tahoma", Font.PLAIN, 16));
		l7.setBounds(0, 39, 211, 37);
		contentPane.add(l7);
		
		JButton back = new JButton("");
		back.setBounds(1237, 0, 49, 68);
		Image ix = new ImageIcon(this.getClass().getResource("/door.png")).getImage();
    	back.setIcon(new ImageIcon(ix));
    	back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String s="",date=Database.dAte();
				getsaat();
				 s="INSERT INTO İslemler(islem_sahibiı_id,islem_türü,islem_yer_tür,islem_tarihi,islem_saat) VALUES("+Giris.kullanıcı_id+",'oyundan cıkıs','sanal ortam','"+date+"','"+saat+"');";		
					Database.add(s);	 	 
					setVis();
				Main m = new Main();
			}
    		
    	});
		contentPane.add(back);
	
	
		this.Action_and_Image();
		kullanıcı_deger_ayar();
		
		 m = new JButton("");
		 m.setBackground(new Color(255, 215, 0));
			m.setBounds(20, 197, 68, 45);
	       if (mesaj_say==0)
	        {
	        	Image i = new ImageIcon(this.getClass().getResource("/m1.png")).getImage();
	        	m.setIcon(new ImageIcon(i));
	        }
	        else {
	        	Image i = new ImageIcon(this.getClass().getResource("/m2.png")).getImage();
	        	m.setIcon(new ImageIcon(i));
	        }
	       
	        m.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
                  if(mesaj_say==0)     
                  {
                	  JOptionPane.showMessageDialog(contentPane,"Mesaj Kutunuz Boş...");
                  }
                  else {
                	  setVis();
                	  TabloPane tp = new TabloPane(0," ",0,1);
                  }
                		  
				}
	        	
	        });
	        
			contentPane.add(m);
			
		
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent gg) { 

	Alan a=(Alan)gg.getSource();
	int alan_no=a.alan_no;
	System.out.println("alan_no=>"+alan_no);
    setVis();
İslemPanel	ip = new İslemPanel(alan_no,"");
	}
}
