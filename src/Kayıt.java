import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Kayıt extends JFrame {

	private JPanel contentPane;
	private JTextField tf1;
	private JTextField tf2;
	private JTextField tf3;
    private int b_yemek=-1,b_para=-1,b_esya=-1;
	
	static int  yönetici_id=0;
	
	private String saat="";
	public String dateAyar(int gun)
    {
    	 // Güncel tarihi alma
        LocalDate currentDate = LocalDate.now();
        
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
	    
	
	public void setvis()
	{
		this.setVisible(false);
	}
	public void LabelRes()
	{
		tf1.setText("");
		tf2.setText("");
		tf3.setText("");
	}
	public Kayıt() {
		Database db = new Database();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0,0,1530, 719);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(173, 216, 230));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel l1 = new JLabel("KAYIT ISLEMLERI");
		l1.setFont(new Font("Rockwell", Font.ITALIC, 17));
		l1.setHorizontalAlignment(SwingConstants.CENTER);
		l1.setBounds(454, 10, 625, 85);
		contentPane.add(l1);
		
		JLabel l2 = new JLabel("Adınız->");
		l2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		l2.setHorizontalAlignment(SwingConstants.RIGHT);
		l2.setBounds(10, 208, 145, 32);
		contentPane.add(l2);
		
		JLabel l3 = new JLabel("Soyadınız->");
		l3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		l3.setHorizontalAlignment(SwingConstants.RIGHT);
		l3.setBounds(10, 271, 145, 32);
		contentPane.add(l3);
		
		JLabel l4 = new JLabel("Sifreniz->");
		l4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		l4.setHorizontalAlignment(SwingConstants.RIGHT);
		l4.setBounds(10, 330, 145, 32);
		contentPane.add(l4);
		
		tf1 = new JTextField();
		tf1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		tf1.setBounds(165, 208, 244, 31);
		contentPane.add(tf1);
		tf1.setColumns(10);
		
		tf2 = new JTextField();
		tf2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		tf2.setColumns(10);
		tf2.setBounds(165, 272, 244, 31);
		contentPane.add(tf2);
		
		JButton b1 = new JButton("Yönetici Kayıt");
		b1.setBackground(new Color(176, 196, 222));
		b1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		b1.setBounds(10, 442, 261, 85);
		Image i1 = new ImageIcon(this.getClass().getResource("/kayıt2.png")).getImage();
		b1.setIcon(new ImageIcon(i1));
		b1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
			String ad,soyad,sifre,date=null,sql1,sql2=null;
			int yönetici_say=-1;
			sql2="Select count(kullanıcı_no) as Sayı FROM Kullanıcılar WHERE kullanıcı_türü = 'yönetici' ;";
			ResultSet rs = db.Sorgu(sql2);
			try {
				while(rs.next())
				{
					yönetici_say=rs.getInt(1);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(yönetici_say==0)
			{
				ad=tf1.getText();
				soyad=tf2.getText();
				sifre=tf3.getText();
				date=db.dAte();
				String up_date=dateAyar(1);
				getsaat();
sql1="INSERT INTO Kullanıcılar(kullanıcı_ad,kullanıcı_soyad,kullanıcı_sifre,kullanıcı_türü,baslangıc_tarihi,baslangıc_saat,update_tarihi,update_saati) VALUES (" +				
		"'"+ ad +"','" +soyad +"'," + Integer.parseInt(sifre)+",'yönetici','" + date +"','"+saat+"','"+up_date+"','"+saat+"');";
          db.add(sql1);        
sql1="SELECT kullanıcı_no FROM Kullanıcılar WHERE kullanıcı_türü='yönetici';";
       rs=db.Sorgu(sql1);
     try {
		while(rs.next())
		 {
			 yönetici_id=rs.getInt(1);
			 System.out.println("Yönetici_id=>"+yönetici_id);
		 }
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
   
		    LabelRes();
		    JOptionPane.showMessageDialog(contentPane,"META-LAND'e  Yönetici Olarak Kaydınız Basarılı Bir Şekilde Gerçeklestirildi...");
		    JOptionPane.showMessageDialog(contentPane,"Yönetici Oldugunuz İcin Oyunun Belli Parametreleri Belirlemeniz Gereklidir.Bunun İcin Yeni Bir Sayfaya Gönderileceksiniz...");
			setvis();
		    YöneticiSettings ys = new YöneticiSettings();
			}
			else {
				JOptionPane.showMessageDialog(contentPane,"Uyarı! META-LAND'in Bir Yöneticsi Vardır.İsterseniz Oyuncu Olarak Kayıt Olabilirsiniz.");
			}
			
			
			}

		});
		contentPane.add(b1);
		
		JButton b2 = new JButton("Oyuncu Kayıt");
		b2.setBackground(new Color(176, 196, 222));
		b2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		b2.setBounds(310, 442, 244, 85);
		Image i2 = new ImageIcon(this.getClass().getResource("/kayıt2.png")).getImage();
		b2.setIcon(new ImageIcon(i2));
		b2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
		     String ad,soyad,sifre, date=null ,sql,sql2=null;
		     int yonetici_count=-1,oyuncu_id=-1,syc=1,kayıt_sayısı=-1;
		     sql2="Select count(kullanıcı_no) as Sayı FROM Kullanıcılar WHERE kullanıcı_türü = 'yönetici' ;";
		    ResultSet sr=db.Sorgu(sql2); 
		    try {
				while(sr.next())
				{
					yonetici_count=sr.getInt(1);
					System.out.println("yonetici_count="+yonetici_count);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    if(yonetici_count==1) {
		    int os_id=-1;
		    sql="SELECT os_id FRom Oyuncusistem;";
		    sr=Database.Sorgu(sql);
		    try {
				while(sr.next())
				{
					os_id=sr.getInt(1);
				}
			} catch (SQLException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
		    ad=tf1.getText();
			soyad=tf2.getText();
		    sifre=tf3.getText();
		    date=db.dAte();
		    sql="SELECT COUNT(kullanıcı_no) as kayıt_sayısı FROM Kullanıcılar WHERE kullanıcı_ad='"+ad+"' AND kullanıcı_soyad='"
		    		+ soyad +"' AND kullanıcı_sifre="+sifre+";";
		    sr=db.Sorgu(sql);
		    try {
				while(sr.next())
				{
					kayıt_sayısı=sr.getInt("kayıt_sayısı");
					System.out.println("kayıt sayısı="+kayıt_sayısı);
				}
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} 
		    if(kayıt_sayısı==0) {
		    getsaat();	
		    String up_date=dateAyar(1);
		    System.out.println("class.Kayıt.saat=>"+saat);
		    sql="INSERT INTO Kullanıcılar (kullanıcı_ad,kullanıcı_soyad,kullanıcı_sifre,kullanıcı_türü,baslangıc_tarihi,baslangıc_saat,update_tarihi,update_saati) VALUES ('"+
		    ad +"','"+soyad+"',"+sifre+",'oyuncu','"+date+"','"+saat+"','"+up_date+"','"+saat+"');";  
		    db.add(sql);
		    sql="SELECT COUNT(kullanıcı_no) as oyuncu_id FROM Kullanıcılar;";
		    sr=db.Sorgu(sql);
		    try {
				while(sr.next())
				{
					oyuncu_id=sr.getInt(1);
				     System.out.println(oyuncu_id);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    
		    sql="SELECT kullanıcı_no FROM Kullanıcılar;";
		    sr=db.Sorgu(sql);
		    try {
				while(sr.next())
				{
					if(syc==oyuncu_id) {
					oyuncu_id=sr.getInt("kullanıcı_no");
				     System.out.println(oyuncu_id);
				     break;
					}
					syc++;
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    sql="SELECT baslangıc_yemek,baslangıc_esya,baslangıc_para FROM OyuncuSistem;";
		    sr=Database.Sorgu(sql);
		    try {
				while(sr.next())
				{
					b_yemek=sr.getInt("baslangıc_yemek");
					b_esya=sr.getInt("baslangıc_esya");
					b_para=sr.getInt("baslangıc_para");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    sql = "INSERT INTO Oyuncular (kullanıcı_id,oyuncu_sistem_id,kullanıcı_yemek,kullanıcı_esya,kullanıcı_para) VALUES("
		    	+ oyuncu_id + ","+os_id+","+b_yemek+","+b_esya+","+b_para+");";	
		    db.add(sql);
		    LabelRes();
		    JOptionPane.showMessageDialog(contentPane,"Oyuncu Olarak Kayıt İşleminiz Basarıyla Gercekleşmiştir...");
		    }
		    else {
			    JOptionPane.showMessageDialog(contentPane,"Bu Kullanıcı Mevcuttur Bu Yüzden Kayıt Gerceklesmedi...");
			    LabelRes();
		    }
		    
		    }
		    else {
		    JOptionPane.showMessageDialog(contentPane,"META-LAND'ın Bir Yöneticisi Yoktur.Lütfen Yönetici Kayıtını Bekleyin.İyi Gunler....");
		    LabelRes();
		    }
		    
			}			
		});
		contentPane.add(b2);
		
		JButton b3 = new JButton("");
		b3.setBackground(new Color(176, 196, 222));
		b3.setBounds(1444, 10, 62, 73);
		Image i3 = new ImageIcon(this.getClass().getResource("/door.png")).getImage();
		b3.setIcon(new ImageIcon(i3));
		b3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
	        setvis();
		    Main m = new Main();
			}
		});
		contentPane.add(b3);
		
		tf3 = new JTextField();
		tf3.setFont(new Font("Webdings", Font.PLAIN, 15));
		tf3.setBounds(165, 330, 244, 32);
		contentPane.add(tf3);
		tf3.setColumns(10);
		
		JLabel img = new JLabel("");
		img.setBounds(620, 129, 781, 444);
		Image i7 = new ImageIcon(this.getClass().getResource("/kapak5.png")).getImage();
		img.setIcon(new ImageIcon(i7));
		contentPane.add(img);
		this.setVisible(true);
	}
}
