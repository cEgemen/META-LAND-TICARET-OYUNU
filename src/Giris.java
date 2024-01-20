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
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;

public class Giris extends JFrame {

	private JPanel contentPane;
	private JTextField tf1;
	private JTextField tf2;
	private JTextField tf3;
	static  String kullanıcı_ad,kullanıcı_soyad,kullanıcı_sifre;
	static  int kullanıcı_id;
	private String sql3,date=Database.dAte();
	private ResultSet rs3;
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
	public Giris() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0,0,1530, 719);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(222, 184, 135));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel header = new JLabel("META-LAND GİRİS");
		header.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		header.setHorizontalAlignment(SwingConstants.CENTER);
		header.setBounds(431, 10, 575, 47);
		contentPane.add(header);
		
		JLabel l1 = new JLabel("Adınız Gİriniz->");
		l1.setHorizontalAlignment(SwingConstants.RIGHT);
		l1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		l1.setBounds(40, 159, 170, 31);
		contentPane.add(l1);
		
		JLabel l2 = new JLabel("Soyadınızı Giriniz->");
		l2.setHorizontalAlignment(SwingConstants.RIGHT);
		l2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		l2.setBounds(40, 220, 170, 31);
		contentPane.add(l2);
		
		JLabel l3 = new JLabel("Sifrenizi Giriniz->");
		l3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		l3.setHorizontalAlignment(SwingConstants.RIGHT);
		l3.setBounds(40, 285, 170, 31);
		contentPane.add(l3);
		
		tf1 = new JTextField();
		tf1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		tf1.setBounds(220, 156, 278, 41);
		contentPane.add(tf1);
		tf1.setColumns(10);
		
		tf2 = new JTextField();
		tf2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		tf2.setColumns(10);
		tf2.setBounds(220, 217, 278, 41);
		contentPane.add(tf2);
		
		tf3 = new JTextField();
		tf3.setFont(new Font("Wingdings 2", Font.PLAIN, 15));
		tf3.setColumns(10);
		tf3.setBounds(220, 282, 278, 41);
		contentPane.add(tf3);
		
		JButton b1 = new JButton("Giris");
		b1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		b1.setBackground(new Color(173, 216, 230));
		b1.setBounds(247, 401, 223, 70);
		Image i2= new ImageIcon(this.getClass().getResource("/giris.png")).getImage();
		b1.setIcon(new ImageIcon(i2));
		b1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			String kullanıcı_tür=null,sql=null,sql2=null;
			ResultSet rs=null,rs2=null;
			int kayıt_sayısı=-1;
			kullanıcı_ad=tf1.getText();
			kullanıcı_soyad=tf2.getText();
			kullanıcı_sifre=tf3.getText();
			sql="SELECT COUNT(kullanıcı_no) as kayıt_sayısı FROM Kullanıcılar WHERE kullanıcı_ad='"+kullanıcı_ad+"' AND kullanıcı_soyad='"
			+kullanıcı_soyad+"' AND kullanıcı_sifre="+kullanıcı_sifre+";";
			rs=Database.Sorgu(sql);
			try {
				while(rs.next())
				{
					kayıt_sayısı=rs.getInt(1);
					System.out.println("kayıt sayısı="+kayıt_sayısı+"sql="+sql);
				
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			sql="SELECT kullanıcı_no FROM Kullanıcılar WHERE kullanıcı_ad='"+kullanıcı_ad+"' AND kullanıcı_soyad='"
					+kullanıcı_soyad+"' AND kullanıcı_sifre="+kullanıcı_sifre+";";
					rs=Database.Sorgu(sql);
					try {
						while(rs.next())
						{
							kullanıcı_id=rs.getInt(1);
						
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			sql2="SELECT kullanıcı_türü FROM Kullanıcılar WHERE kullanıcı_ad='"+kullanıcı_ad+"' AND kullanıcı_soyad='"
					+kullanıcı_soyad+"' AND kullanıcı_sifre="+kullanıcı_sifre+";";
			rs2=Database.Sorgu(sql2);
			 	try {
					while (rs2.next())
					{
						kullanıcı_tür=rs2.getString(1);
						System.out.println("kullanıcı_türü="+kullanıcı_tür);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			 if(kayıt_sayısı==1)
			 {
			     JOptionPane.showMessageDialog(contentPane,"Kayıt Kontrol Basarılı.Meta-Land Dünyasına Geciliyor...");	
			     if(kullanıcı_tür.equals("yönetici")) {
			    	 getsaat();
			    	 sql3="INSERT INTO İslemler(islem_sahibiı_id,islem_türü,islem_yer_tür,islem_tarihi,islem_saat) VALUES("+kullanıcı_id+",'oyuna giris','sanal ortam','"+date+"','"+saat+"');";		
			 		Database.add(sql3);
			    	 YöneticiOyun yo = new YöneticiOyun();
			    	 setVis();
			     }
			     else {
			    	 getsaat();
			    	 sql3="INSERT INTO İslemler(islem_sahibiı_id,islem_türü,islem_yer_tür,islem_tarihi,islem_saat) VALUES("+kullanıcı_id+",'oyuna giris','sanal ortam','"+date+"','"+saat+"');";		
			 		Database.add(sql3);
			    	 OyuncuOyun  oo = new OyuncuOyun();
			    	 setVis();
			     }
			     
			 }
			 else {
			     JOptionPane.showMessageDialog(contentPane,"Kayıt Kontrol Basarısız.Lütfen Kayıt Olduktan Sonra Giris Yapınız...");	 
			     
			 }	 
			}
		});
		contentPane.add(b1);
		
		JButton b2 = new JButton("");
		b2.setBackground(new Color(173, 216, 230));
		b2.setBounds(1455, 10, 51, 73);
		Image i3 =new ImageIcon(this.getClass().getResource("/door.png")).getImage();
		b2.setIcon(new ImageIcon(i3));
		b2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
             setVis();
			 Main m = new Main();
			}
		});
		contentPane.add(b2);
		
		JLabel img = new JLabel("");
		img.setBounds(574, 105, 801, 501);
		Image i1 = new ImageIcon(this.getClass().getResource("/kapak6.png")).getImage();
		img.setIcon(new ImageIcon(i1));
		contentPane.add(img);
		this.setVisible(true);
	}
}
