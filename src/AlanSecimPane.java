import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class AlanSecimPane extends JFrame {

	private JPanel contentPane;
	String sql1,sql2;
	ResultSet rs1,rs2;
    int isletme_kurma_fiyatı=-1;
    private Object [] kolon = {"Arsa İD","Alan Tür","Ürün Satıs Fiyatı","Sabit Geliri","Calısan Sayısı"};
    private Object[] satır=new Object[5];
	private  DefaultTableModel model = new DefaultTableModel();
	private JScrollPane scrollPane = new JScrollPane();
	private JTable table = new JTable();
	private Alan a = new Alan();  
    String date=Database.dAte();
    private String saat="";
    public void getsaat()
    {
 	    Date d = new Date();
 	    SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss");
         saat=format2.format(d);
         System.out.println("saat->"+saat);
    }
    public void creatTablo()
	{
    	
     ArrayList<Integer> alan_id = new ArrayList<Integer>();
     sql1="SELECT  alan_id FROM Alanlar WHERE kullanıcı_id="+Giris.kullanıcı_id+";";
     rs1=Database.Sorgu(sql1);
     try {
		while(rs1.next())
		 {
			 alan_id.add(rs1.getInt(1));
		 }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    if(!(alan_id.size()==0))
    {
    	String alan_tür="";
    	for(int i=0;i<alan_id.size();i++)
    	{
    	       sql1="SELECT alan_tür FROM Alanlar WHERE alan_id="+alan_id.get(i)+";";
    	       rs1=Database.Sorgu(sql1);
    	       try {
				while(rs1.next())
				   {
					   alan_tür=rs1.getString(1);
				   }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	   if(alan_tür.equals("arsa"))
    	   {
    		  satır[0]=alan_id.get(i);
    	      satır[1]="arsa";
    	      satır[2]="-----";
    	      satır[3]="-----";
    	      satır[4]="-----";
    	      model.addRow(satır);
    	   }      
    	}
    }
    
	}
	public void setVis()
	{
		this.setVisible(false);
	}
	public AlanSecimPane(int alan_no,String mesaj) {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(0,0,906,509);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(216, 191, 216));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel header = new JLabel("Alan Secim");
		header.setHorizontalAlignment(SwingConstants.CENTER);
		header.setFont(new Font("Tahoma", Font.ITALIC, 20));
		header.setBounds(92, 0, 777, 59);
		contentPane.add(header);

		JButton back = new JButton("");
		back.setBackground(new Color(176, 196, 222));
		back.setBounds(837, 0, 55, 84);
		Image i1 = new ImageIcon(this.getClass().getResource("/door.png")).getImage();
		back.setIcon(new ImageIcon(i1));
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setVis();
			İslemPanel ip = new İslemPanel(alan_no,mesaj);
			}
			
		});
		contentPane.add(back);
		
		model.setColumnIdentifiers(kolon);
		creatTablo();
		
		scrollPane.setBounds(63, 110, 755, 343);
		contentPane.add(scrollPane);
		
		table.setBounds(397, 252, 188, 175);
	  table.setModel(model);
		scrollPane.setViewportView(table);
		
	   
	   table.addMouseListener(new MouseAdapter() {
		   public void mouseClicked(MouseEvent e) {
				int  alan_no=-1 ,alan_id=-1,k_id=-1,count=-1,islem_bilgileri_id=-1,is_id=-1,emlak_bilgi_id=-1,market_bilgi_id=-1,magaza_bilgi_id=-1;
				alan_id=(int) table.getValueAt(table.getSelectedRow(),0); 
				sql1="SELECT alan_no FROM Alanlar WHERE alan_id="+alan_id+";";
				rs1=Database.Sorgu(sql1);
				try {
					while(rs1.next())
					{
						alan_no=rs1.getInt(1);
					}
				} catch (SQLException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
				
				sql1="SELECT islem_bilgileri_id FROM iBilgileri WHERE seviye=1;";
				rs1=Database.Sorgu(sql1);
				try {
					while(rs1.next())
					{
						islem_bilgileri_id=rs1.getInt(1);
					}
				} catch (SQLException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
				sql1="SELECT is_id FROM İsletmeSistem;";
				rs1=Database.Sorgu(sql1);
				try {
					while(rs1.next())
					{
						is_id=rs1.getInt(1);
					}
				} catch (SQLException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}	
					
				sql2="SELECT emlak_bilgi_id FROM EmlakBilgi;";
				rs2=Database.Sorgu(sql2);
				try {
					while(rs2.next())
					{
						emlak_bilgi_id=rs2.getInt(1);
					}
				} catch (SQLException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
				
				sql2="SELECT  market_bilgi_id FROM MarketBilgi;";
				rs2=Database.Sorgu(sql2);
				try {
					while(rs2.next())
					{
						market_bilgi_id=rs2.getInt(1);
					}
				} catch (SQLException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
				
				sql2="SELECT magaza_bilgi_id FROM MagazaBilgi;";
				rs2=Database.Sorgu(sql2);
				try {
					while(rs2.next())
					{
						magaza_bilgi_id=rs2.getInt(1);
					}
				} catch (SQLException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
				
				
				sql1="SELECT alan_id,kullanıcı_id FROM Alanlar WHERE alan_id="+alan_id+";";
				rs1=Database.Sorgu(sql1);
				try {
					while(rs1.next())
					{
					 k_id=rs1.getInt("kullanıcı_id");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
			 if(k_id==Giris.kullanıcı_id)	
			 {
				 sql2="SELECT COUNT(alan_id) FROM Alanlar WHERE kullanıcı_id="+k_id+" AND alan_tür='arsa';";
			     rs2=Database.Sorgu(sql2);
			     try {
					while(rs2.next())
					 {
						 count=rs2.getInt(1);
					 }
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				 
				if(count>0) 
				 {
				 String cevap = JOptionPane.showInputDialog(contentPane,"Lütfen Kurmak İstediginiz İsletmenini Adını Giriniz(emlak,magaza,market):");
				if(cevap.equals("emlak"))
				{
				sql2 ="SELECT isletme_kurma_fiyatı FROM EmlakBilgi;";
				rs2=Database.Sorgu(sql2);
				try {
					while(rs2.next())
					{
						isletme_kurma_fiyatı=rs2.getInt(1);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				int k_para=-1;
				String tür="";
				sql2="SELECT kullanıcı_türü FROM Kullanıcılar WHERE kullanıcı_no="+k_id+";";
				rs2=Database.Sorgu(sql2);
				try {
					while(rs2.next())
					{
						tür=rs2.getString(1);
					}
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			if(tür.equals("oyuncu"))
			{
				sql1 = "SELECT kullanıcı_para FROM Oyuncular WHERE kullanıcı_id="+Giris.kullanıcı_id+";";
				rs1=Database.Sorgu(sql1);
				try {
					while(rs1.next())
					{
						k_para=rs1.getInt(1);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(k_para>=isletme_kurma_fiyatı)
				{
				int cevap2 = JOptionPane.showConfirmDialog(contentPane,"Bu İsletmeyi Kurmak İcin "+isletme_kurma_fiyatı+" Ödemeniz Gereklidir.Onaylıyor Musunuz?");	
				 if(cevap2==JOptionPane.YES_OPTION)	
				 {
				  String maas  = JOptionPane.showInputDialog(contentPane,"Lütfen Sabit Maas Degerini Giriniz:");
				  String komisyon  = JOptionPane.showInputDialog(contentPane,"Lütfen Emlak komisyon Degerini Giriniz:");
				     k_para-=isletme_kurma_fiyatı;
				     sql2="UPDATE Oyuncular SET kullanıcı_para="+k_para+" WHERE kullanıcı_id="+Giris.kullanıcı_id+";";
				     Database.update(sql2);
				     sql2="UPDATE Alanlar SET alan_tür='isletme' WHERE alan_id="+alan_id+";";
				     Database.update(sql2);
				     sql2="INSERT INTO İsletmeler(alan_id,isletme_bilgi_id,isletme_sistem_id,isletme_tür,kullanıcı_ücret,calısan_sayısı,seviye_tarihi) VALUES("
				    		 +alan_id+","+islem_bilgileri_id+","+is_id+",'emlak',"+maas+",0,'"+date+"');";
				     Database.add(sql2);
				  int isletme_id=-1;  
				    sql1="SELECT isletme_id FROM İsletmeler WHERE alan_id="+alan_id+";"; 
				     rs1=Database.Sorgu(sql1);
				     try {
						while(rs1.next())
						 {
							 isletme_id=rs1.getInt(1);
						 }
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				sql1="INSERT INTO Emlak(isletmeci_id,emlak_bilgi_id,komisyon) VALUES("+isletme_id+","+emlak_bilgi_id+","+komisyon+");";
				Database.add(sql1);
				  String s="",t=Database.dAte();
		          getsaat();
		          s="INSERT INTO İslemler(islem_sahibiı_id,islem_türü,islem_yer_tür,islem_tarihi,islem_saat) VALUES("+Giris.kullanıcı_id+",'isletme kurma','emlak','"+t+"','"+saat+"');";		
		           	Database.add(s);   
			         for(int i=0;i<a.alan.length;i++)
			         {
			        	 for(int j=0;j<a.alan[0].length;j++)
			        	 {
			        		 if(a.alan[i][j].alan_no==alan_no)
			        		 {
			        	   			a.alan[i][j]=new Alan(Giris.kullanıcı_ad);
			        	   			a.alan[i][j].alan_tür="emlak";
			        	   			break;
			        		 }
			        	 }
			         }
			         model.setRowCount(0);
					 creatTablo();
			         JOptionPane.showMessageDialog(contentPane,"İsleminiz Basarıyla Gercekleştirilmistir..");
				 }
				 else {
					 JOptionPane.showMessageDialog(contentPane,"İslem İptal Edildi...");
				 }
				}
				else
				{
					JOptionPane.showMessageDialog(contentPane,"Mevcut Paranıc İsletme Kurmak İcin Gereken Ücretten Daha Az Oldugu İcin İslem İptal Edildi...");
				}
				}
			else {
				JOptionPane.showMessageDialog(contentPane,"YÖnetici İsletme Dikemez...");
			}
				}
				else if(cevap.equals("market"))
				{
					sql2 ="SELECT isletme_kurma_fiyatı FROM MarketBilgi;";
					rs2=Database.Sorgu(sql2);
					try {
						while(rs2.next())
						{
							isletme_kurma_fiyatı=rs2.getInt(1);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					int k_para=-1;
					String tür="";
					sql2="SELECT kullanıcı_türü FROM Kullanıcılar WHERE kullanıcı_no="+k_id+";";
					rs2=Database.Sorgu(sql2);
					try {
						while(rs2.next())
						{
							tür=rs2.getString(1);
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				if(tür.equals("oyuncu"))
				{
					sql1 = "SELECT kullanıcı_para FROM Oyuncular WHERE kullanıcı_id="+Giris.kullanıcı_id+";";
					rs1=Database.Sorgu(sql1);
					try {
						while(rs1.next())
						{
							k_para=rs1.getInt(1);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if(k_para>=isletme_kurma_fiyatı)
					{
					int cevap2 = JOptionPane.showConfirmDialog(contentPane,"Bu İsletmeyi Kurmak İcin "+isletme_kurma_fiyatı+" Ödemeniz Gereklidir.Onaylıyor Musunuz?");	
					 if(cevap2==JOptionPane.YES_OPTION)	
					 {
						 String maas  = JOptionPane.showInputDialog(contentPane,"Lütfen Sabit Maas Degerini Giriniz:");
						  String yiyecek_ücreti  = JOptionPane.showInputDialog(contentPane,"Lütfen Yemek Birim Fiyatını  Giriniz:");
						     k_para-=isletme_kurma_fiyatı;
						     sql2="UPDATE Oyuncular SET kullanıcı_para="+k_para+" WHERE kullanıcı_id="+Giris.kullanıcı_id+";";
						     Database.update(sql2);
						     sql2="UPDATE Alanlar SET alan_tür='isletme' WHERE alan_id="+alan_id+";";
						     Database.update(sql2);
						     sql2="INSERT INTO İsletmeler(alan_id,isletme_bilgi_id,isletme_sistem_id,isletme_tür,kullanıcı_ücret,calısan_sayısı,seviye_tarihi) VALUES("
						    		 +alan_id+","+islem_bilgileri_id+","+is_id+",'market',"+maas+",0,'"+date+"');";
						     Database.add(sql2);
						  int isletme_id=-1;  
						    sql1="SELECT isletme_id FROM İsletmeler WHERE alan_id="+alan_id+";"; 
						     rs1=Database.Sorgu(sql1);
						     try {
								while(rs1.next())
								 {
									 isletme_id=rs1.getInt(1);
								 }
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						sql1="INSERT INTO Market(isletmeci_id,market_bilgi_id,yiyecek_ücreti) VALUES("+isletme_id+","+market_bilgi_id+","+yiyecek_ücreti+");";
						Database.add(sql1);
						 String s="",t=Database.dAte();
				          getsaat();
				          s="INSERT INTO İslemler(islem_sahibiı_id,islem_türü,islem_yer_tür,islem_tarihi,islem_saat) VALUES("+Giris.kullanıcı_id+",'isletme kurma','market','"+t+"','"+saat+"');";		
				           	Database.add(s);      
				         for(int i=0;i<a.alan.length;i++)
				         {
				        	 for(int j=0;j<a.alan[0].length;j++)
				        	 {
				        		 if(a.alan[i][j].alan_no==alan_no)
				        		 {
				        	   			a.alan[i][j]=new Alan(Giris.kullanıcı_ad);
				        	   			a.alan[i][j].alan_tür="market";
				        	   			break;
				        		 }
				        	 }
				         }
				         model.setRowCount(0);
						 creatTablo();
				         JOptionPane.showMessageDialog(contentPane,"İsleminiz Basarıyla Gercekleştirilmistir..");
					 }
					 else {
						 JOptionPane.showMessageDialog(contentPane,"İslem İptal Edildi...");
					 }
					}
					else
					{
						JOptionPane.showMessageDialog(contentPane,"Mevcut Paranıc İsletme Kurmak İcin Gereken Ücretten Daha Az Oldugu İcin İslem İptal Edildi...");
					}
					}
				else {
					JOptionPane.showMessageDialog(contentPane,"YÖnetici İsletme Dikemez...");
				}
				}
				else if(cevap.equals("magaza"))
				{
					sql2 ="SELECT isletme_kurma_fiyatı FROM MagazaBilgi;";
					rs2=Database.Sorgu(sql2);
					try {
						while(rs2.next())
						{
							isletme_kurma_fiyatı=rs2.getInt(1);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					int k_para=-1;
					String tür="";
					sql2="SELECT kullanıcı_türü FROM Kullanıcılar WHERE kullanıcı_no="+k_id+";";
					rs2=Database.Sorgu(sql2);
					try {
						while(rs2.next())
						{
							tür=rs2.getString(1);
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				if(tür.equals("oyuncu"))
				{
					sql1 = "SELECT kullanıcı_para FROM Oyuncular WHERE kullanıcı_id="+Giris.kullanıcı_id+";";
					rs1=Database.Sorgu(sql1);
					try {
						while(rs1.next())
						{
							k_para=rs1.getInt(1);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if(k_para>=isletme_kurma_fiyatı)
					{
					int cevap2 = JOptionPane.showConfirmDialog(contentPane,"Bu İsletmeyi Kurmak İcin "+isletme_kurma_fiyatı+" Ödemeniz Gereklidir.Onaylıyor Musunuz?");	
					 if(cevap2==JOptionPane.YES_OPTION)	
					 {
						 String maas  = JOptionPane.showInputDialog(contentPane,"Lütfen Sabit Maas Degerini Giriniz:");
						  String esya_ücreti = JOptionPane.showInputDialog(contentPane,"Lütfen Magaza Esya Birim Fiyatını Giriniz:");
						     k_para-=isletme_kurma_fiyatı;
						     sql2="UPDATE Oyuncular SET kullanıcı_para="+k_para+" WHERE kullanıcı_id="+Giris.kullanıcı_id+";";
						     Database.update(sql2);
						     sql2="UPDATE Alanlar SET alan_tür='isletme' WHERE alan_id="+alan_id+";";
						     Database.update(sql2);
						     sql2="INSERT INTO İsletmeler(alan_id,isletme_bilgi_id,isletme_sistem_id,isletme_tür,kullanıcı_ücret,calısan_sayısı,seviye_tarihi) VALUES("
						    		 +alan_id+","+islem_bilgileri_id+","+is_id+",'magaza',"+maas+",0,'"+date+"');";
						     Database.add(sql2);
						  int isletme_id=-1;  
						    sql1="SELECT isletme_id FROM İsletmeler WHERE alan_id="+alan_id+";"; 
						     rs1=Database.Sorgu(sql1);
						     try {
								while(rs1.next())
								 {
									 isletme_id=rs1.getInt(1);
								 }
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						sql1="INSERT INTO Magaza(isletmeci_id,magaza_bilgi_id,esya_ücreti) VALUES("+isletme_id+","+market_bilgi_id+","+esya_ücreti+");";
						Database.add(sql1);
						 String s="",t=Database.dAte();
				          getsaat();
				          s="INSERT INTO İslemler(islem_sahibiı_id,islem_türü,islem_yer_tür,islem_tarihi,islem_saat) VALUES("+Giris.kullanıcı_id+",'isletme kurma','magaza','"+t+"','"+saat+"');";		
				           	Database.add(s);       
				         for(int i=0;i<a.alan.length;i++)
				         {
				        	 for(int j=0;j<a.alan[0].length;j++)
				        	 {
				        		 if(a.alan[i][j].alan_no==alan_no)
				        		 {
				        	   			a.alan[i][j]=new Alan(Giris.kullanıcı_ad);
				        	   			a.alan[i][j].alan_tür="magaza";
				        	   			break;
				        		 }
				        	 }
				         }
						 model.setRowCount(0);
						 creatTablo();
				         JOptionPane.showMessageDialog(contentPane,"İsleminiz Basarıyla Gercekleştirilmistir..");
					 }
					 else {
						 JOptionPane.showMessageDialog(contentPane,"İslem İptal Edildi...");
					 }
					}
					else
					{
						JOptionPane.showMessageDialog(contentPane,"Mevcut Paranıc İsletme Kurmak İcin Gereken Ücretten Daha Az Oldugu İcin İslem İptal Edildi...");
					}
					}
				else {
					JOptionPane.showMessageDialog(contentPane,"YÖnetici İsletme Dikemez...");
				}
				}
				
			 }
				else {
					JOptionPane.showMessageDialog(contentPane,"Bosta Arsanız Yoktur...");
				}
				}
			 else {
				 
				JOptionPane.showMessageDialog(contentPane,"Lütfen Kendi Arsanızı Seciniz..."); 
			 }
		   }
	   });
	   
		this.setVisible(true);	
	}
}
