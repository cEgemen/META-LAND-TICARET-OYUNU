import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class YöneticiSettings2 extends JFrame {
	private JPanel contentPane;
	private JTextField tf1;
	private JTextField tf2;
	private JTextField tf3;
	private JTextField tf4;
	private JTextField tf6;
	private JTextField tf7;
	private JTextField tf8;
	private JTextField tf9;
	private JTextField tf5;
	private JTextField tf10;
	private JTextField tf11;
	private JTextField tf12;
	private JTextField tf13;
	private JTextField tf14;
	private JTextField tf15;
	private JTextField tf16;

	private String  oyun_boyut="3x3";

	private String  baslangıc_esya="1000";
	private String  baslangıc_yemek="1000";
	private String  baslangıc_para="500";

	private String  günlük_esya_gider="100";
	private String  günlük_yemek_gider="100";
	private String günlük_para_gider="50";

	private String  isletme_sabit_gelir="300";
	private String  isletme_sabit_gelir_oran="40";
	private String  yönetici_ücret="250";
	private String  seviye_yükseltme_ücreti="8000";
	
	private String  emlak_isletme_kurma_ücreti="1800";
	private String  magaza_isletme_kurma_ücreti="1600";
	private String  market_isletme_kurma_ücreti="1400";
	
	
	
	static int os_id=1,as_id=1,is_id=1,emlak_bilgi_id=-1,market_bilgi_id=-1,magaza_bilgi_id=-1;
	 	
	public String yönetici_yemek_ücret="20",yönetici_esya_ücreti="50",yönetici_komisyon="600"; 
	private JTextField tf17;
	
	public void setVis()
	{
		this.setVisible(false);
	}
	public void defualtSettings()
	{
		String sql="",sql2,sql3,sql4;
		int syc=-1;
	    ResultSet rs=null;
	  sql2="SELECT baslangıc_yemek,baslangıc_esya,baslangıc_para,gunluk_esya_gider,gunluk_yemek_gider,gunluk_para_gider FROM OyuncuSistem ;";
   rs=Database.Sorgu(sql2);
   try {
	while(rs.next())
	   {
		   baslangıc_yemek=Integer.toString(rs.getInt("baslangıc_yemek"));
		   baslangıc_esya=Integer.toString(rs.getInt("baslangıc_esya"));
		   baslangıc_para=Integer.toString(rs.getInt("baslangıc_para"));
		   günlük_esya_gider=Integer.toString(rs.getInt("gunluk_esya_gider"));
		   günlük_yemek_gider=Integer.toString(rs.getInt("gunluk_yemek_gider"));
		   günlük_para_gider=Integer.toString(rs.getInt("gunluk_para_gider"));
	   }
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

sql3="SELECT alan_boyut FROM AlanSistem;";
rs=Database.Sorgu(sql3);
try {
	while(rs.next())
	{
		oyun_boyut=rs.getString("alan_boyut");
	}
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

sql4="SELECT isletme_sabit_gelir,isletme_sabit_gelir_oran,yönetici_ücret,seviye_yükseltme_ücreti FROM İsletmeSistem;";  			
rs=Database.Sorgu(sql4);
try {
	while(rs.next())
	{
		isletme_sabit_gelir=Integer.toString(rs.getInt("isletme_sabit_gelir"));
		isletme_sabit_gelir_oran=Integer.toString(rs.getInt("isletme_sabit_gelir_oran"));
		yönetici_ücret=Integer.toString(rs.getInt("yönetici_ücret"));
		seviye_yükseltme_ücreti=Integer.toString(rs.getInt("seviye_yükseltme_ücreti"));
	}
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}	
sql4="SELECT isletme_kurma_fiyatı FROM EmlakBilgi;";  			
rs=Database.Sorgu(sql4);
try {
	while(rs.next())
	{
		emlak_isletme_kurma_ücreti=rs.getString(1);
		
	}
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}	

sql4="SELECT isletme_kurma_fiyatı FROM MagazaBilgi;";  			
rs=Database.Sorgu(sql4);
try {
	while(rs.next())
	{
		magaza_isletme_kurma_ücreti=rs.getString(1);
		
	}
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}	

sql4="SELECT isletme_kurma_fiyatı FROM MarketBilgi;";  			
rs=Database.Sorgu(sql4);
try {
	while(rs.next())
	{
		market_isletme_kurma_ücreti=rs.getString(1);
		
	}
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}	
		tf5.setText(oyun_boyut);
		 tf2.setText(baslangıc_esya);
		 tf1.setText( baslangıc_yemek);
		tf3.setText(baslangıc_para);
		 tf4.setText(günlük_esya_gider);
		 tf6.setText(günlük_yemek_gider);
	     tf7.setText(günlük_para_gider);
		 tf8.setText(isletme_sabit_gelir);
		  tf9.setText(isletme_sabit_gelir_oran);
		  tf10.setText(yönetici_ücret);
		  tf11.setText(yönetici_komisyon);
		  tf12.setText(yönetici_yemek_ücret);
		  tf13.setText(yönetici_esya_ücreti);
		  tf14.setText(emlak_isletme_kurma_ücreti);
		  tf15.setText(magaza_isletme_kurma_ücreti);
		  tf16.setText(market_isletme_kurma_ücreti);
	}
	
	public YöneticiSettings2() {
		setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
		setBounds(0,0, 1200, 759);
		contentPane =  new JPanel();
		contentPane.setBackground(new Color(135, 206, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel header = new JLabel("META-LAND AYARLAR");
		header.setFont(new Font("Tahoma", Font.PLAIN, 20));
		header.setBounds(435, 10, 251, 48);
		contentPane.add(header);
		
		JLabel l1 = new JLabel("Baslangıc Yemek->");
		l1.setHorizontalAlignment(SwingConstants.RIGHT);
		l1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		l1.setBounds(10, 86, 244, 25);
		contentPane.add(l1);
		
		JLabel l2 = new JLabel("Baslangıc Esya->");
		l2.setHorizontalAlignment(SwingConstants.RIGHT);
		l2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		l2.setBounds(10, 121, 244, 25);
		contentPane.add(l2);
		
		JLabel l3 = new JLabel("Baslangıc Para->");
		l3.setHorizontalAlignment(SwingConstants.RIGHT);
		l3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		l3.setBounds(10, 156, 244, 25);
		contentPane.add(l3);
		
		JLabel l4 = new JLabel("Günlük Esya Gider->");
		l4.setHorizontalAlignment(SwingConstants.RIGHT);
		l4.setFont(new Font("Tahoma", Font.PLAIN, 15));
		l4.setBounds(10, 191, 251, 25);
		contentPane.add(l4);
		
		tf1 = new JTextField();
		tf1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tf1.setBounds(256, 86, 136, 25);
		contentPane.add(tf1);
		tf1.setColumns(10);
		
		tf2 = new JTextField();
		tf2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tf2.setBounds(256, 121, 136, 25);
		contentPane.add(tf2);
		tf2.setColumns(10);
		
		tf3 = new JTextField();
		tf3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tf3.setBounds(256, 156, 136, 25);
		contentPane.add(tf3);
		tf3.setColumns(10);
		
		tf4 = new JTextField();
		tf4.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tf4.setBounds(256, 191, 136, 25);
		contentPane.add(tf4);
		tf4.setColumns(10);
		
		JLabel l6 = new JLabel("Günlük  Yemek Gider->");
		l6.setHorizontalAlignment(SwingConstants.RIGHT);
		l6.setFont(new Font("Tahoma", Font.PLAIN, 15));
		l6.setBounds(10, 226, 244, 25);
		contentPane.add(l6);
		
		JLabel l7 = new JLabel("Günlük Para Gider->");
		l7.setHorizontalAlignment(SwingConstants.RIGHT);
		l7.setFont(new Font("Tahoma", Font.PLAIN, 15));
		l7.setBounds(10, 261, 244, 25);
		contentPane.add(l7);
		
		JLabel l8 = new JLabel("İsletme Sabit Gelir->");
		l8.setHorizontalAlignment(SwingConstants.RIGHT);
		l8.setFont(new Font("Tahoma", Font.PLAIN, 15));
		l8.setBounds(10, 296, 236, 25);
		contentPane.add(l8);
		
		JLabel l9 = new JLabel("İsle. Sabit Gelir Oran->");
		l9.setHorizontalAlignment(SwingConstants.RIGHT);
		l9.setFont(new Font("Tahoma", Font.PLAIN, 15));
		l9.setBounds(10, 331, 244, 25);
		contentPane.add(l9);
		
		tf6 = new JTextField();
		tf6.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tf6.setColumns(10);
		tf6.setBounds(256, 226, 136, 25);
		contentPane.add(tf6);
		
		tf7 = new JTextField();
		tf7.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tf7.setColumns(10);
		tf7.setBounds(256, 261, 136, 25);
		contentPane.add(tf7);
		
		tf8 = new JTextField();
		tf8.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tf8.setColumns(10);
		tf8.setBounds(256, 296, 136, 25);
		contentPane.add(tf8);
		
		tf9 = new JTextField();
		tf9.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tf9.setColumns(10);
		tf9.setBounds(256, 331, 136, 25);
		contentPane.add(tf9);
		
		JLabel l5 = new JLabel("Oyun Boyut->");
		l5.setHorizontalAlignment(SwingConstants.RIGHT);
		l5.setFont(new Font("Tahoma", Font.PLAIN, 15));
		l5.setBounds(10, 51, 244, 25);
		contentPane.add(l5);
		
		tf5 = new JTextField();
		tf5.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tf5.setBounds(256, 51, 136, 25);
		contentPane.add(tf5);
		tf5.setColumns(10);
		
		
		JLabel l10 = new JLabel("Yönetici Ücret->");
		l10.setHorizontalAlignment(SwingConstants.RIGHT);
		l10.setFont(new Font("Tahoma", Font.PLAIN, 15));
		l10.setBounds(10, 366, 244, 25);
		contentPane.add(l10);
		
		tf10 = new JTextField();
		tf10.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tf10.setText("40");
		tf10.setColumns(10);
		tf10.setBounds(256, 366, 136, 25);
		contentPane.add(tf10);
		
		JLabel l11 = new JLabel("Y. Emlak Komisyon->");
		l11.setHorizontalAlignment(SwingConstants.RIGHT);
		l11.setFont(new Font("Tahoma", Font.PLAIN, 15));
		l11.setBounds(10, 401, 244, 25);
		contentPane.add(l11);
		
		tf11 = new JTextField();
		tf11.setText("40");
		tf11.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tf11.setColumns(10);
		tf11.setBounds(256, 401, 136, 25);
		contentPane.add(tf11);
		
		JLabel l12 = new JLabel("Y.  Yiyecek Ücret->");
		l12.setHorizontalAlignment(SwingConstants.RIGHT);
		l12.setFont(new Font("Tahoma", Font.PLAIN, 15));
		l12.setBounds(10, 436, 244, 25);
		contentPane.add(l12);
		
		tf12 = new JTextField();
		tf12.setText("40");
		tf12.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tf12.setColumns(10);
		tf12.setBounds(256, 436, 136, 25);
		contentPane.add(tf12);
		
		JLabel l13 = new JLabel("Y. Esya Ücret->");
		l13.setHorizontalAlignment(SwingConstants.RIGHT);
		l13.setFont(new Font("Tahoma", Font.PLAIN, 15));
		l13.setBounds(10, 471, 244, 25);
		contentPane.add(l13);
		
		tf13 = new JTextField();
		tf13.setText("40");
		tf13.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tf13.setColumns(10);
		tf13.setBounds(256, 471, 136, 25);
		contentPane.add(tf13);
		
		JLabel l14 = new JLabel("Emlak İsletme Kurulum Ücreti->");
		l14.setHorizontalAlignment(SwingConstants.RIGHT);
		l14.setFont(new Font("Tahoma", Font.PLAIN, 15));
		l14.setBounds(10, 506, 244, 25);
		contentPane.add(l14);
		
		tf14 = new JTextField();
		tf14.setText("40");
		tf14.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tf14.setColumns(10);
		tf14.setBounds(256, 506, 136, 25);
		contentPane.add(tf14);
		
		JLabel l15 = new JLabel("Magaza İsletme Kurulum Ücreti->");
		l15.setHorizontalAlignment(SwingConstants.RIGHT);
		l15.setFont(new Font("Tahoma", Font.PLAIN, 15));
		l15.setBounds(10, 541, 244, 25);
		contentPane.add(l15);
		
		tf15 = new JTextField();
		tf15.setText("40");
		tf15.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tf15.setColumns(10);
		tf15.setBounds(256, 541, 136, 25);
		contentPane.add(tf15);
		
		JLabel l16 = new JLabel("Market İsletme Kurulum Ücreti->");
		l16.setHorizontalAlignment(SwingConstants.RIGHT);
		l16.setFont(new Font("Tahoma", Font.PLAIN, 15));
		l16.setBounds(10, 576, 244, 25);
		contentPane.add(l16);
		
		tf16 = new JTextField();
		tf16.setText("40");
		tf16.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tf16.setColumns(10);
		tf16.setBounds(256, 576, 136, 25);
		contentPane.add(tf16);
		
		JLabel l17 = new JLabel("Seviye Yükseltme Ücreti->");
		l17.setHorizontalAlignment(SwingConstants.RIGHT);
		l17.setFont(new Font("Tahoma", Font.PLAIN, 15));
		l17.setBounds(10, 611, 244, 25);
		contentPane.add(l17);
		
		tf17 = new JTextField();
		tf17.setText("40");
		tf17.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tf17.setColumns(10);
		tf17.setBounds(256, 611, 136, 25);
		contentPane.add(tf17);
		
		this.defualtSettings();
		JButton b1 = new JButton("Kaydet");
		b1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		b1.setBounds(106, 657, 207, 55);
		b1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
		  String a1,a2,a3,a4,a5,a6,a7,a8,a9,a10,a11,a12,a13,a14,a15,a16,a17;
		  String sql=null;
		  ResultSet rs=null;
		  a1=tf1.getText();
			if(!(a1.isEmpty()))
			{
				baslangıc_yemek=a1;
			}
			a2=tf2.getText();
			if(!(a2.isEmpty()))
			{
				baslangıc_esya=a2;
			}
			a3=tf3.getText();
			if(!(a3.isEmpty()))
			{
				baslangıc_para=a3;
			}
			a4=tf4.getText();
			if(!(a4.isEmpty()))
			{
				günlük_yemek_gider=a4;
			}
			a5=tf5.getText();
			String [] tmp = a5.split("x");
			int top=Integer.parseInt(tmp[0]) + Integer.parseInt(tmp[1]);
			if(top<4)
			{
			JOptionPane.showMessageDialog(contentPane,"Alan Boyutu Degerinin Her İki Tarafındaki Rakamlar Toplamı En Az 4 Olmalıdır");	
			}
			else {
			if(!a5.isEmpty())
			{
				oyun_boyut=a5;
			}
			}
			a6=tf6.getText();
			if(!(a6.isEmpty()))
			{
				günlük_esya_gider=a6;
			}
			a7=tf7.getText();
			if(!(a7.isEmpty()))
			{
				günlük_para_gider=a7;
			}
			a8=tf8.getText();
			if(!(a8.isEmpty()))
			{
				isletme_sabit_gelir=a8;
			}
			a9=tf9.getText();
			if(!(a9.isEmpty()))
			{
				isletme_sabit_gelir_oran=a9;
			}
			a10=tf10.getText();
			if(!(a10.isEmpty()))
			{
				yönetici_ücret=a10;
	 		}
			a11=tf11.getText();
			if(!(a11.isEmpty()))
			{
				yönetici_komisyon=a11;
	 		}
			a12=tf12.getText();
			if(!(a12.isEmpty()))
			{
				yönetici_yemek_ücret=a12;
	 		}
			a13=tf13.getText();
			if(!(a13.isEmpty()))
			{
				yönetici_esya_ücreti=a13;
	 		}
			a14=tf14.getText();
			if(!(a14.isEmpty()))
			{
				emlak_isletme_kurma_ücreti=a14;
	 		}
			a15=tf15.getText();
			if(!(a15.isEmpty()))
			{
				magaza_isletme_kurma_ücreti=a15;
	 		}
			a16=tf16.getText();
			if(!(a16.isEmpty()))
			{
				market_isletme_kurma_ücreti=a16;
	 		}
			a17=tf17.getText();
			if(!(a17.isEmpty()))
			{
				seviye_yükseltme_ücreti=a17;
	 		}
sql="UPDATE OyuncuSistem SET baslangıc_yemek="+baslangıc_yemek+","+"baslangıc_esya="+baslangıc_esya+","+"baslangıc_para="+baslangıc_para+",gunluk_esya_gider="+günlük_esya_gider+",gunluk_yemek_gider="+günlük_yemek_gider+",gunluk_para_gider="+günlük_para_gider+";";
Database.update(sql);  
System.out.println("sq1="+sql);
sql="UPDATE AlanSistem SET alan_boyut='"+oyun_boyut+"';";
Database.update(sql);  				
System.out.println("sq2="+sql);
sql="UPDATE İsletmeSistem SET isletme_sabit_gelir="+isletme_sabit_gelir+",isletme_sabit_gelir_oran="+isletme_sabit_gelir_oran+",seviye_yükseltme_ücreti="+seviye_yükseltme_ücreti+",yönetici_ücret="+yönetici_ücret+";";
Database.update(sql);  			
System.out.println("sq3="+sql);

sql="UPDATE EmlakBilgi SET isletme_kurma_fiyatı="+emlak_isletme_kurma_ücreti+";";
Database.update(sql);  			
System.out.println("sq3="+sql);

sql="UPDATE MagazaBilgi SET isletme_kurma_fiyatı="+magaza_isletme_kurma_ücreti+";";
Database.update(sql);  			
System.out.println("sq3="+sql);

sql="UPDATE MarketBilgi SET isletme_kurma_fiyatı="+market_isletme_kurma_ücreti+";";
Database.update(sql);  			
System.out.println("sq3="+sql);
defualtSettings();
JOptionPane.showMessageDialog(contentPane,"Ayarlarınız Kaydedilmiştir...");			
			}
		});
		contentPane.add(b1);
		
		JLabel img = new JLabel("");
		img.setBounds(402, 101, 751, 464);
		Image i1 = new ImageIcon(this.getClass().getResource("/kapak1.png")).getImage();
		img.setIcon(new ImageIcon(i1));
		contentPane.add(img);
		
		JButton b2 = new JButton("");
		b2.setBackground(new Color(173, 216, 230));
		b2.setBounds(1125, 10, 51, 73);
		Image i2 = new ImageIcon(this.getClass().getResource("/door.png")).getImage();
		b2.setIcon(new ImageIcon(i2));
		b2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				YöneticiOyun yo = new YöneticiOyun();
				setVis();
			}
			
		});
		contentPane.add(b2);
		this.setVisible(true);
		defualtSettings();
		JOptionPane.showMessageDialog(contentPane,"Bos Bırakılan Ayarlara Default Bir Deger Atanır.Bu Ayarlar Oyun İçinden Degiştirilebilir...");
		
		
	}

}
