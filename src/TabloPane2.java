import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class TabloPane2 extends JFrame {

	private JPanel contentPane;
	private DefaultTableModel model=new DefaultTableModel();
	private Object[] kolonlar1={"Emlak ID","Kiralık İsletme ID","İsletme Tür","Günlük Kiralık Fiyat"};
	private Object[] kolonlar2={"Emlak ID","Satılık Yer Alan ID","Yer Tür","Satıs Fiyat"};
	private Object[] kolonlar3={"Emlak ID","Emlak Sahibi Ad","Emlak Sahibi Soyad","Komisyon"};
	private Object[] kolonlar4={"Emlak ID","Emlak Sahibi Ad","Emlak Sahibi Soyad","Komisyon"};
	private Object[] satırlar=new Object[4];
	private JTable table_1;
	private JScrollPane scrollPane;
    private String sql1,sql2,sql3,sql4;
    private ResultSet rs1,rs2,rs3,rs4;
    private String date = Database.dAte();
    private String saat="";
    private boolean sahip=false;
    public void getsaat()
    {
 	    Date d = new Date();
 	    SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss");
         saat=format2.format(d);
         System.out.println("saat->"+saat);
    }
    
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
    public void creatTablo(int isletme_id,int emlak_id,int belirtec,String isletme_tür)
    {
          	if(belirtec==1)
          	{
          		 int kiralama_isletme_id=-1; 
                ArrayList<Integer> kl_id = new ArrayList<Integer>();
                System.out.println("emlak_id->"+emlak_id);
                sql1="SELECT kl_id FROM KiralıkList WHERE emlak_id="+emlak_id+";";
             	rs1=Database.Sorgu(sql1);       
                try {
					while(rs1.next())
					{
						kl_id.add(rs1.getInt(1));
		                System.out.println("kl_id->"+rs1.getInt(1));
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                System.out.println("kl_id->"+kl_id.size());
             	if(kl_id.size()>0)
             	{
             		for(int i=0;i<kl_id.size();i++)
             		{
             			 sql1="SELECT emlak_id,isletme_id,kiralama_ücret FROM KiralıkList WHERE kl_id="+kl_id.get(i)+";";
                      	rs1=Database.Sorgu(sql1);
             			try {
							while(rs1.next())
							{
								satırlar[0]=rs1.getInt("emlak_id");
								satırlar[1]=rs1.getInt("isletme_id");
								satırlar[3]=rs1.getInt("kiralama_ücret");
								kiralama_isletme_id=rs1.getInt("isletme_id");
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
             			int alan_id=-1,kul_id=-1;
             			 sql1="SELECT isletme_tür,alan_id FROM İsletmeler WHERE isletme_id="+kiralama_isletme_id+";";
                       	rs1=Database.Sorgu(sql1);
              			try {
 							while(rs1.next())
 							{
 								satırlar[2]=rs1.getString("isletme_tür");
 							     alan_id=rs1.getInt("alan_id");
 							}
 						} catch (SQLException e) {
 							// TODO Auto-generated catch block
 							e.printStackTrace();
 												}
         				  model.addRow(satırlar);	
             		}
             	}
          	}
          	else if(belirtec==2){
         int satıs_isletme_alan_id=-1;
         String tür="";
        ArrayList <Integer> sl_id = new ArrayList<Integer>();
        sql1="SELECT sl_id FROM SatısList WHERE emlak_id="+emlak_id+";";
      	rs1=Database.Sorgu(sql1); 
         try {
			while(rs1.next())
			 {
				 sl_id.add(rs1.getInt(1));
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         if(sl_id.size()>0)
         {
        	 for(int i=0;i<sl_id.size();i++)
        	 {
        	 sql1="SELECT emlak_id,satılık_yer_alan_id,satıs_fiyat FROM SatısList WHERE sl_id="+sl_id.get(i)+";";
           	rs1=Database.Sorgu(sql1);
          	
 				try {
					while(rs1.next())
					{
						satırlar[0]=rs1.getInt("emlak_id");
						satırlar[1]=rs1.getInt("satılık_yer_alan_id");
						satırlar[3]=rs1.getInt("satıs_fiyat");			
						satıs_isletme_alan_id=rs1.getInt("satılık_yer_alan_id");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
 				int kul_id=-1;
 					sql2="SELECT alan_tür,kullanıcı_id FROM Alanlar WHERE alan_id="+satıs_isletme_alan_id+";";
 					rs2=Database.Sorgu(sql2);
 					try {
						while(rs2.next())
						{
							tür=rs2.getString("alan_tür");
						     kul_id=rs2.getInt("kullanıcı_id");
						   
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
 					if(tür.equals("arsa"))
 					{
 						satırlar[2]="arsa";
 					}
 					else {
 						sql2="SELECT isletme_tür FROM İsletmeler WHERE alan_id="+satıs_isletme_alan_id+";";
 						rs2=Database.Sorgu(sql2);
 						try {
							while(rs2.next())
							{
								tür=rs2.getString("isletme_tür");
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
 						satırlar[2]=tür;
 					}
 				  model.addRow(satırlar);	
 				}
         } 
        	 
         }  
          	else 
          	{ 		
          	  int  emlak_isletme_id=-1,emlak_alan_id=-1,emlak_sahibi_id=-1,komisyon=-1; 
          			String  emlak_sahibi_ad="",emlak_sahibi_soyad=" "; 		
              ArrayList<Integer> e_id = new ArrayList<Integer>();
          	  sql1="SELECT emlak_id FROM Emlak;";
              	rs1=Database.Sorgu(sql1);
              	try {
					while(rs1.next())
					{
						e_id.add(rs1.getInt(1));
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
             if(e_id.size()>0) 	
             {
               	System.out.println("e_id.size()->"+e_id.size());
            		 for(int i=0;i<e_id.size();i++)
            		 {
            			 sql1="SELECT isletmeci_id,komisyon FROM Emlak WHERE emlak_id="+e_id.get(i)+";";
                       	rs1=Database.Sorgu(sql1);
                       	try {
         					while(rs1.next())
         					{
         						emlak_isletme_id=rs1.getInt("isletmeci_id");
         						komisyon=rs1.getInt("komisyon");
         					}
         				} catch (SQLException e) {
         					// TODO Auto-generated catch block
         					e.printStackTrace();
         				}
                       	int emlak_sahibi_alan_id=-1;
                        sql3="SELECT alan_id FROM İsletmeler WHERE isletme_id="+emlak_isletme_id+";";
     					rs3=Database.Sorgu(sql3);
     					try {
							while(rs3.next())
							{
								emlak_sahibi_alan_id=rs3.getInt(1);
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}  	
                       	
                       	
            			 sql3="SELECT kullanıcı_id FROM Alanlar WHERE alan_id="+emlak_sahibi_alan_id+";";
     					rs3=Database.Sorgu(sql3);
     					try {
							while(rs3.next())
							{
								emlak_sahibi_id=rs3.getInt(1);
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
     					sql4="SELECT kullanıcı_ad,kullanıcı_soyad FROM Kullanıcılar WHERE  kullanıcı_no="+emlak_sahibi_id+";";
     					rs4=Database.Sorgu(sql4);
     					try {
							while(rs4.next())
							{
								emlak_sahibi_ad=rs4.getString("kullanıcı_ad");
								emlak_sahibi_soyad=rs4.getString("kullanıcı_soyad");
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
     					System.out.println("emlak_sahibi_ad=>"+emlak_sahibi_ad+" emlak_sahibi_soyad=>"+emlak_sahibi_soyad);
     					satırlar[0]=e_id.get(i);
     					satırlar[1]=emlak_sahibi_ad;
     					satırlar[2]=emlak_sahibi_soyad;
     					satırlar[3]=komisyon;
     				    model.addRow(satırlar);	
     			}
            	 }
            	 
             }
              
    }
    public void setVis()
    {
    	this.setVisible(false);
    }
	public TabloPane2(int isletme_id,int isletme_sahibi_id,int emlak_id,int belirtec,String isletme_tür,int al_no,String mesaj,int belirtec2) {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(0,0,1000,600);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(244, 164, 96));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
				
		JLabel lblNewLabel = new JLabel("İsletme Listesi");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(332, 10, 427, 47);
		contentPane.add(lblNewLabel);
	
		if(belirtec==1) {
		model.setColumnIdentifiers(kolonlar1);
		}
		else if(belirtec==2){
			model.setColumnIdentifiers(kolonlar2);
		}
		else if(belirtec==3) {
				model.setColumnIdentifiers(kolonlar3);
			     }
			     else {
			 		model.setColumnIdentifiers(kolonlar4);
			     }
		creatTablo(isletme_id,emlak_id,belirtec,isletme_tür);

		
		table_1 = new JTable();
		table_1.setModel(model);
		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
		      if(belirtec==1)
		      {    
		    	    int emlak_id=(int) model.getValueAt(table_1.getSelectedRow(),0);
		    		int isletme_id=(int) model.getValueAt(table_1.getSelectedRow(),1);
		    		int kiralama_fiyat=(int) model.getValueAt(table_1.getSelectedRow(),3);
					int cevap=JOptionPane.showConfirmDialog(contentPane,"ID'si "+isletme_id+" Olan İsletmenin Kiralama islemi Tamamlansın Mı?");
					if(cevap==JOptionPane.YES_OPTION)
					{
						//  kiralama islemi gerceklestirilecek
		int isletme_sahibi_id=-1,emlak_sahibi_id=-1,satın_alan_para=-1,isletme_sahibi_para=-1,emlak_sahibi_para=-1,emlakcı_komisyon=-1;		
        String isletme_sahibi_tür="",emlak_sahibi_tür="",satın_alan_tür="";    
        sql1="SELECT kullanıcı_türü FROM Kullanıcılar WHERE kullanıcı_no="+Giris.kullanıcı_id+";";					
		rs1=Database.Sorgu(sql1);
		try {
			while(rs1.next())
			{
			  satın_alan_tür=rs1.getString(1);	
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    if(satın_alan_tür.equals("oyuncu"))	
    {
    	sql1="SELECT kullanıcı_para FROM Oyuncular WHERE kullanıcı_id="+Giris.kullanıcı_id+";";					
		rs1=Database.Sorgu(sql1);
		try {
			while(rs1.next())
			{
			  satın_alan_para=rs1.getInt(1);	
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
    }
    String c1 = JOptionPane.showInputDialog(contentPane,"Lütfen Kiralanacak Gün Sayısını Giriniz:");  
    int gun = Integer.parseInt(c1); 
    if(satın_alan_para>=(kiralama_fiyat*gun)) {
	int isletme_sahibi_alan_id=-1; 	
		sql1="SELECT alan_id FROM İsletmeler WHERE  isletme_id="+isletme_id+";";
		rs1=Database.Sorgu(sql1);
		try {
			while(rs1.next())
			{
				isletme_sahibi_alan_id=rs1.getInt(1);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		sql1="SELECT kullanıcı_id FROM Alanlar WHERE  alan_id="+isletme_sahibi_alan_id+";";
		rs1=Database.Sorgu(sql1);
		try {
			while(rs1.next())
			{
				isletme_sahibi_id=rs1.getInt(1);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		sql1="SELECT kullanıcı_türü FROM Kullanıcılar WHERE kullanıcı_no="+isletme_sahibi_id+";";					
		rs1=Database.Sorgu(sql1);
		try {
			while(rs1.next())
			{
			  isletme_sahibi_tür=rs1.getString(1);	
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    if(isletme_sahibi_tür.equals("oyuncu"))	
    {
    	sql1="SELECT kullanıcı_para FROM Oyuncular WHERE kullanıcı_id="+isletme_sahibi_id+";";					
		rs1=Database.Sorgu(sql1);
		try {
			while(rs1.next())
			{
			  isletme_sahibi_para=rs1.getInt(1);	
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
    }
			int emlak_sahibi_isletme_id=-1;
	sql1="SELECT isletmeci_id,komisyon FROM Emlak WHERE emlak_id="+emlak_id+";";	
	rs1=Database.Sorgu(sql1);
	try {
		while(rs1.next())
		{
			emlak_sahibi_isletme_id=rs1.getInt("isletmeci_id");
			emlakcı_komisyon=rs1.getInt("komisyon");
		}
	} catch (SQLException e2) {
		// TODO Auto-generated catch block
		e2.printStackTrace();
	}
		
    int emlak_sahibi_alan_id=-1; 	
	sql1="SELECT alan_id FROM İsletmeler WHERE  isletme_id="+emlak_sahibi_isletme_id+";";
	rs1=Database.Sorgu(sql1);
	try {
		while(rs1.next())
		{
			emlak_sahibi_alan_id=rs1.getInt(1);
		}
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	sql1="SELECT kullanıcı_id FROM Alanlar WHERE  alan_id="+emlak_sahibi_alan_id+";";
	rs1=Database.Sorgu(sql1);
	try {
		while(rs1.next())
		{
			emlak_sahibi_id=rs1.getInt(1);
		}
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	sql1="SELECT kullanıcı_türü FROM Kullanıcılar WHERE kullanıcı_no="+emlak_sahibi_id+";";					
	rs1=Database.Sorgu(sql1);
	try {
		while(rs1.next())
		{
		  emlak_sahibi_tür=rs1.getString(1);	
		}
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
if(emlak_sahibi_tür.equals("oyuncu"))	
{
	sql1="SELECT kullanıcı_para FROM Oyuncular WHERE kullanıcı_id="+emlak_sahibi_id+";";					
	rs1=Database.Sorgu(sql1);
	try {
		while(rs1.next())
		{
		  emlak_sahibi_para=rs1.getInt(1);	
		}
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}	
}	
     
 
	int p_satın_alan=satın_alan_para-(kiralama_fiyat*gun);	
    int p_emlak=emlak_sahibi_para+emlakcı_komisyon;					
    int p_isletme_sahibi=isletme_sahibi_para+(kiralama_fiyat*gun)-emlakcı_komisyon;
    if(satın_alan_tür.equals("oyuncu"))	
    {
    	sql1="UPDATE  Oyuncular SET kullanıcı_para="+p_satın_alan+" WHERE kullanıcı_id="+Giris.kullanıcı_id+";";					
		Database.update(sql1);
    }
    if(emlak_sahibi_tür.equals("oyuncu"))	
    {
    	sql1="UPDATE  Oyuncular SET kullanıcı_para="+p_emlak+" WHERE kullanıcı_id="+emlak_sahibi_id+";";					
		Database.update(sql1);
    }
    if(isletme_sahibi_tür.equals("oyuncu"))	
    {
    	sql1="UPDATE  Oyuncular SET kullanıcı_para="+p_isletme_sahibi+" WHERE kullanıcı_id="+isletme_sahibi_id+";";					
		Database.update(sql1);
    }
     sql1="UPDATE Alanlar SET kullanıcı_id="+Giris.kullanıcı_id+" WHERE alan_id="+isletme_sahibi_alan_id+";"; 
     Database.update(sql1);
   String s="",t=Database.dAte();
   getsaat();
   s="INSERT INTO İslemler(islem_sahibiı_id,islem_türü,islem_yer_tür,islem_tarihi,islem_saat) VALUES("+Giris.kullanıcı_id+",'kiralama','emlak','"+t+"','"+saat+"');";		
   	Database.add(s);	 
     int syc=-1,syc2=1,islem_id=-1;
    sql1="SELECT COUNT(islem_id) FROM İslemler WHERE islem_sahibiı_id="+Giris.kullanıcı_id+";";  
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
     sql1="SELECT islem_id FROM İslemler WHERE islem_sahibiı_id="+Giris.kullanıcı_id+";";  
     rs1=Database.Sorgu(sql1);
     try {
		while(rs1.next())
		 {
			 if(syc2==syc) {
			 islem_id=rs1.getInt(1);
			 }
			 syc2++;
		 }
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
  String date2=dateAyar(gun);
  //emlak_sahibi_isletme_id
  getsaat(); 
     sql1="INSERT INTO Kiralamalar(islem_id,kiralatan_id,kiralanan_isletme_id,kiralama_yer_id,kiralama_fiyatı,kira_süresi,kira_baslangıc_tarihi,kira_bitis_tarihi,kira_baslangıc_saati) VALUES("+islem_id+","+isletme_sahibi_id+","+isletme_id+","+emlak_sahibi_isletme_id+","+(kiralama_fiyat*gun)+","+gun+",'"+date+"','"+date2+"','"+saat+"');";
     Database.add(sql1); 
     sql1="DELETE FROM KiralıkList WHERE isletme_id="+isletme_id+";";
     Database.update(sql1);
	model.setRowCount(0);
     creatTablo(isletme_id,emlak_id,belirtec,isletme_tür);
     JOptionPane.showMessageDialog(contentPane,"Kiralama İşlemi Basarıyla Gercekleştirilmistir");
    }		
					else {
				JOptionPane.showMessageDialog(contentPane,"Paranız "+c1+" Gün Kiralama İçin yetersizdir...");		
					}
					}
					else {
						JOptionPane.showMessageDialog(contentPane,"Kiralama İptal Edildi");
					}  
		      }
		      else if(belirtec==2){
					// Satın alma islemi gerceklestirilecek
		    	  int count=-1;
		  sql1="SELECT COUNT(alan_id)  FROM Alanlar WHERE kullanıcı_id="+Giris.kullanıcı_id+" AND alan_tür='arsa';";  	  
		   rs1=Database.Sorgu(sql1);
		   try {
			while(rs1.next())
			   {
				   count=rs1.getInt(1);
			   }
		} catch (SQLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		   System.out.println("Giris.kullanıcı_ad=>"+Giris.kullanıcı_ad+" count->"+count);
		   int isletme_sahibi_id=-1,emlak_sahibi_id=-1,satın_alan_para=-1,isletme_sahibi_para=-1,emlak_sahibi_para=-1,emlakcı_komisyon=-1;		
	        String isletme_sahibi_tür="",emlak_sahibi_tür="",satın_alan_tür="";
		    	  int emlak_id=(int) model.getValueAt(table_1.getSelectedRow(),0);
		    		int isletme_alan_id=(int) model.getValueAt(table_1.getSelectedRow(),1);
		    		String tür=(String) model.getValueAt(table_1.getSelectedRow(),2);
		    		int satıs_fiyat=(int) model.getValueAt(table_1.getSelectedRow(),3);
		    		System.out.println("emlak_id=>"+emlak_id);
		    		System.out.println("isletme_alan_id=>"+isletme_alan_id);
		    		System.out.println("tür=>"+tür);
		    		System.out.println("satıs_fiyat=>"+satıs_fiyat);

		if(tür.equals("arsa"))    		
		{
			System.out.println("arsa satıs");
			if(count<2)
			{
				int cevap=JOptionPane.showConfirmDialog(contentPane,"ID'si "+isletme_id+" Olan Arsayı Satın Alma islemi Tamamlansın Mı?");
				if(cevap==JOptionPane.YES_OPTION)
				{
					System.out.println("arsa satıs------------------------------------------------------");
					sql1="SELECT kullanıcı_türü FROM Kullanıcılar WHERE kullanıcı_no="+Giris.kullanıcı_id+";";					
					rs1=Database.Sorgu(sql1);
					try {
						while(rs1.next())
						{
						  satın_alan_tür=rs1.getString(1);	
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			    if(satın_alan_tür.equals("oyuncu"))	
			    {
			    	sql1="SELECT kullanıcı_para FROM Oyuncular WHERE kullanıcı_id="+Giris.kullanıcı_id+";";					
					rs1=Database.Sorgu(sql1);
					try {
						while(rs1.next())
						{
						  satın_alan_para=rs1.getInt(1);	
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}	
			    }
			    if(satın_alan_para>=satıs_fiyat) {
					sql1="SELECT kullanıcı_id FROM Alanlar WHERE  alan_id="+isletme_alan_id+";";
					rs1=Database.Sorgu(sql1);
					try {
						while(rs1.next())
						{
							isletme_sahibi_id=rs1.getInt(1);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					System.out.println("isletme_sahibi_id=>"+isletme_sahibi_id);
					sql1="SELECT kullanıcı_türü FROM Kullanıcılar WHERE kullanıcı_no="+isletme_sahibi_id+";";					
					rs1=Database.Sorgu(sql1);
					try {
						while(rs1.next())
						{
						  isletme_sahibi_tür=rs1.getString(1);	
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			    if(isletme_sahibi_tür.equals("oyuncu"))	
			    {
			    	sql1="SELECT kullanıcı_para FROM Oyuncular WHERE kullanıcı_id="+isletme_sahibi_id+";";					
					rs1=Database.Sorgu(sql1);
					try {
						while(rs1.next())
						{
						  isletme_sahibi_para=rs1.getInt(1);	
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}	
			    }
						int emlak_sahibi_isletme_id=-1;
				sql1="SELECT isletmeci_id,komisyon FROM Emlak WHERE emlak_id="+emlak_id+";";	
				rs1=Database.Sorgu(sql1);
				try {
					while(rs1.next())
					{
						emlak_sahibi_isletme_id=rs1.getInt("isletmeci_id");
						emlakcı_komisyon=rs1.getInt("komisyon");
					}
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
					
			    int emlak_sahibi_alan_id=-1; 	
				sql1="SELECT alan_id FROM İsletmeler WHERE  isletme_id="+emlak_sahibi_isletme_id+";";
				rs1=Database.Sorgu(sql1);
				try {
					while(rs1.next())
					{
						emlak_sahibi_alan_id=rs1.getInt(1);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				sql1="SELECT kullanıcı_id FROM Alanlar WHERE  alan_id="+emlak_sahibi_alan_id+";";
				rs1=Database.Sorgu(sql1);
				try {
					while(rs1.next())
					{
						emlak_sahibi_id=rs1.getInt(1);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				sql1="SELECT kullanıcı_türü FROM Kullanıcılar WHERE kullanıcı_no="+emlak_sahibi_id+";";					
				rs1=Database.Sorgu(sql1);
				try {
					while(rs1.next())
					{
					  emlak_sahibi_tür=rs1.getString(1);	
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			if(emlak_sahibi_tür.equals("oyuncu"))	
			{
				sql1="SELECT kullanıcı_para FROM Oyuncular WHERE kullanıcı_id="+emlak_sahibi_id+";";					
				rs1=Database.Sorgu(sql1);
				try {
					while(rs1.next())
					{
					  emlak_sahibi_para=rs1.getInt(1);	
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
			}	
			     
			 
				int p_satın_alan=satın_alan_para-(satıs_fiyat);	
			    int p_emlak=emlak_sahibi_para+emlakcı_komisyon;					
			    int p_isletme_sahibi=isletme_sahibi_para+(satıs_fiyat)-emlakcı_komisyon;
			    if(satın_alan_tür.equals("oyuncu"))	
			    {
			    	sql1="UPDATE  Oyuncular SET kullanıcı_para="+p_satın_alan+" WHERE kullanıcı_id="+Giris.kullanıcı_id+";";					
					Database.update(sql1);
			    }
			    if(emlak_sahibi_tür.equals("oyuncu"))	
			    {
			    	sql1="UPDATE  Oyuncular SET kullanıcı_para="+p_emlak+" WHERE kullanıcı_id="+emlak_sahibi_id+";";					
					Database.update(sql1);
			    }
			    if(isletme_sahibi_tür.equals("oyuncu"))	
			    {
			    	sql1="UPDATE  Oyuncular SET kullanıcı_para="+p_isletme_sahibi+" WHERE kullanıcı_id="+isletme_sahibi_id+";";					
					Database.update(sql1);
			    }
			    System.out.println("isletme_alan_id->"+isletme_alan_id);
			     sql1="UPDATE Alanlar SET kullanıcı_id="+Giris.kullanıcı_id+" WHERE alan_id="+isletme_alan_id+";"; 
			     Database.update(sql1);
			     String s="",t=Database.dAte();
			     getsaat();
			     s="INSERT INTO İslemler(islem_sahibiı_id,islem_türü,islem_yer_tür,islem_tarihi,islem_saat) VALUES("+Giris.kullanıcı_id+",'satın alma','emlak','"+t+"','"+saat+"');";		
			   	Database.add(s);
			    s="INSERT INTO İslemler(islem_sahibiı_id,islem_türü,islem_yer_tür,islem_tarihi,islem_saat) VALUES("+isletme_sahibi_id+",'satma','emlak','"+t+"','"+saat+"');";		
			   	Database.add(s);
			     int syc=-1,syc2=1,islem_id=-1;
			    sql1="SELECT COUNT(islem_id) FROM İslemler WHERE islem_sahibiı_id="+Giris.kullanıcı_id+";";  
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
			     sql1="SELECT islem_id FROM İslemler WHERE islem_sahibiı_id="+Giris.kullanıcı_id+";";  
			     rs1=Database.Sorgu(sql1);
			     try {
					while(rs1.next())
					 {
						 if(syc2==syc) {
						 islem_id=rs1.getInt(1);
						 }
						 syc2++;
					 }
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			     getsaat();
			     System.out.println("isletme_Sahibi_id=>"+isletme_sahibi_id);
			     sql1="INSERT INTO SatınAlımlar(islem_id,satan_id,satıs_yer_alan_id,satıs_fiyatı,satıs_tarihi,satıs_baslangıc_saati) VALUES("+islem_id+","+isletme_sahibi_id+","+isletme_alan_id+","+satıs_fiyat+",'"+date+"','"+saat+"');";
			     Database.add(sql1); 
			     sql1="DELETE FROM SatısList WHERE satılık_yer_alan_id="+isletme_alan_id+";";
			     Database.update(sql1);
			     JOptionPane.showMessageDialog(contentPane,"Satın Alma İşlemi Basarıyla Gercekleştirilmistir");
			      model.setRowCount(0);
					creatTablo(isletme_id,emlak_id,belirtec,isletme_tür);
			    }		
								else {
							JOptionPane.showMessageDialog(contentPane,"Paranız  Satın Alma  İçin Yetersizdir...");		
								}
								}		
				else {
					JOptionPane.showMessageDialog(contentPane,"Satın Alma İptal Edildi");
				}
			}
			 else {
		    	  JOptionPane.showMessageDialog(contentPane,"İki Adet Arsanız Bulunmak.Bu Arsayı Almak İçin Arsanıza İsletme Kurunuz ya da Minimum Bir Arsa Satınız...");
		      }
			
		}
		else {
		    		int cevap=JOptionPane.showConfirmDialog(contentPane,"ID'si "+isletme_id+" Olan İsletmenin Satın Alma islemi Tamamlansın Mı?");
			if(cevap==JOptionPane.YES_OPTION)
			{
				System.out.println("isletme satıs------------------------------------------------------");
				  emlak_id=(int) model.getValueAt(table_1.getSelectedRow(),0);
		    		 isletme_alan_id=(int) model.getValueAt(table_1.getSelectedRow(),1);
		    		 tür=(String) model.getValueAt(table_1.getSelectedRow(),2);
		    		 satıs_fiyat=(int) model.getValueAt(table_1.getSelectedRow(),3);
		    		System.out.println("emlak_id=>"+emlak_id);
		    		System.out.println("isletme_alan_id=>"+isletme_alan_id);
		    		System.out.println("tür=>"+tür);
		    		System.out.println("satıs_fiyat=>"+satıs_fiyat);

				sql1="SELECT kullanıcı_türü FROM Kullanıcılar WHERE kullanıcı_no="+Giris.kullanıcı_id+";";					
				rs1=Database.Sorgu(sql1);
				try {
					while(rs1.next())
					{
					  satın_alan_tür=rs1.getString(1);	
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    if(satın_alan_tür.equals("oyuncu"))	
		    {
		    	sql1="SELECT kullanıcı_para FROM Oyuncular WHERE kullanıcı_id="+Giris.kullanıcı_id+";";					
				rs1=Database.Sorgu(sql1);
				try {
					while(rs1.next())
					{
					  satın_alan_para=rs1.getInt(1);	
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
		    }
		    if(satın_alan_para>=satıs_fiyat) {
				sql1="SELECT kullanıcı_id FROM Alanlar WHERE  alan_id="+isletme_alan_id+";";
				rs1=Database.Sorgu(sql1);
				try {
					while(rs1.next())
					{
						isletme_sahibi_id=rs1.getInt(1);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("isletme sahibi id =>"+isletme_sahibi_id);
				sql1="SELECT kullanıcı_türü FROM Kullanıcılar WHERE kullanıcı_no="+isletme_sahibi_id+";";					
				rs1=Database.Sorgu(sql1);
				try {
					while(rs1.next())
					{
					  isletme_sahibi_tür=rs1.getString(1);	
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    if(isletme_sahibi_tür.equals("oyuncu"))	
		    {
		    	sql1="SELECT kullanıcı_para FROM Oyuncular WHERE kullanıcı_id="+isletme_sahibi_id+";";					
				rs1=Database.Sorgu(sql1);
				try {
					while(rs1.next())
					{
					  isletme_sahibi_para=rs1.getInt(1);	
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
		    }
					int emlak_sahibi_isletme_id=-1;
			sql1="SELECT isletmeci_id,komisyon FROM Emlak WHERE emlak_id="+emlak_id+";";	
			rs1=Database.Sorgu(sql1);
			try {
				while(rs1.next())
				{
					emlak_sahibi_isletme_id=rs1.getInt("isletmeci_id");
					emlakcı_komisyon=rs1.getInt("komisyon");
				}
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
				
		    int emlak_sahibi_alan_id=-1; 	
			sql1="SELECT alan_id FROM İsletmeler WHERE  isletme_id="+emlak_sahibi_isletme_id+";";
			rs1=Database.Sorgu(sql1);
			try {
				while(rs1.next())
				{
					emlak_sahibi_alan_id=rs1.getInt(1);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			sql1="SELECT kullanıcı_id FROM Alanlar WHERE  alan_id="+emlak_sahibi_alan_id+";";
			rs1=Database.Sorgu(sql1);
			try {
				while(rs1.next())
				{
					emlak_sahibi_id=rs1.getInt(1);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			sql1="SELECT kullanıcı_türü FROM Kullanıcılar WHERE kullanıcı_no="+emlak_sahibi_id+";";					
			rs1=Database.Sorgu(sql1);
			try {
				while(rs1.next())
				{
				  emlak_sahibi_tür=rs1.getString(1);	
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		if(emlak_sahibi_tür.equals("oyuncu"))	
		{
			sql1="SELECT kullanıcı_para FROM Oyuncular WHERE kullanıcı_id="+emlak_sahibi_id+";";					
			rs1=Database.Sorgu(sql1);
			try {
				while(rs1.next())
				{
				  emlak_sahibi_para=rs1.getInt(1);	
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
		}	
		     
		 
			int p_satın_alan=satın_alan_para-(satıs_fiyat);	
		    int p_emlak=emlak_sahibi_para+emlakcı_komisyon;					
		    int p_isletme_sahibi=isletme_sahibi_para+(satıs_fiyat)-emlakcı_komisyon;
		    if(satın_alan_tür.equals("oyuncu"))	
		    {
		    	System.out.println("satın alan oyuncu para->"+p_satın_alan);
		    	sql1="UPDATE  Oyuncular SET kullanıcı_para="+p_satın_alan+" WHERE kullanıcı_id="+Giris.kullanıcı_id+";";					
				Database.update(sql1);
		    }
		    if(emlak_sahibi_tür.equals("oyuncu"))	
		    {
		    	System.out.println("emlak sahibi oyuncu para->"+p_emlak);
		    	sql1="UPDATE  Oyuncular SET kullanıcı_para="+p_emlak+" WHERE kullanıcı_id="+emlak_sahibi_id+";";					
				Database.update(sql1);
		    }
		    if(isletme_sahibi_tür.equals("oyuncu"))	
		    {
		    	System.out.println("isletme sahibi oyuncu para->"+p_isletme_sahibi);
		    	sql1="UPDATE  Oyuncular SET kullanıcı_para="+p_isletme_sahibi+" WHERE kullanıcı_id="+isletme_sahibi_id+";";					
				Database.update(sql1);
		    }
		     String s="",t=Database.dAte();
		     getsaat();
		    	System.out.println("isletme alan id->"+isletme_alan_id);
		    	System.out.println("alan kisi id->"+Giris.kullanıcı_id);
		    	System.out.println("satan kisi id->"+isletme_sahibi_id);
		     sql1="UPDATE Alanlar SET kullanıcı_id="+Giris.kullanıcı_id+" WHERE alan_id="+isletme_alan_id+";"; 
		     Database.update(sql1);
		     s="INSERT INTO İslemler(islem_sahibiı_id,islem_türü,islem_yer_tür,islem_tarihi,islem_saat) VALUES("+Giris.kullanıcı_id+",'satın alma','emlak','"+t+"','"+saat+"');";		
		   	Database.add(s);	
		   	s="INSERT INTO İslemler(islem_sahibiı_id,islem_türü,islem_yer_tür,islem_tarihi,islem_saat) VALUES("+isletme_sahibi_id+",'satma','emlak','"+t+"','"+saat+"');";		
		   	Database.add(s);
		     int syc=-1,syc2=1,islem_id=-1;
		    sql1="SELECT COUNT(islem_id) FROM İslemler WHERE islem_sahibiı_id="+Giris.kullanıcı_id+";";  
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
		     sql1="SELECT islem_id FROM İslemler WHERE islem_sahibiı_id="+Giris.kullanıcı_id+";";  
		     rs1=Database.Sorgu(sql1);
		     try {
				while(rs1.next())
				 {
					 if(syc2==syc) {
					 islem_id=rs1.getInt(1);
					 }
					 syc2++;
				 }
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		     getsaat();
		     sql1="INSERT INTO SatınAlımlar(islem_id,satan_id,satıs_yer_alan_id,satıs_fiyatı,satıs_tarihi,satıs_baslangıc_saati) VALUES("+islem_id+","+isletme_sahibi_id+","+isletme_alan_id+","+satıs_fiyat+",'"+date+"','"+saat+"');";
		     Database.add(sql1); 
		     sql1="DELETE FROM SatısList WHERE satılık_yer_alan_id="+isletme_alan_id+";";
		     Database.update(sql1);
		     JOptionPane.showMessageDialog(contentPane,"Satıs İşlemi Basarıyla Gercekleştirilmistir");
		     model.setRowCount(0);
		     creatTablo(isletme_id,emlak_id,belirtec,isletme_tür);
		    }		
							else {
						JOptionPane.showMessageDialog(contentPane,"Paranız  Satın Alma  İçin Yetersizdir...");		
							}
							}		
			else {
				JOptionPane.showMessageDialog(contentPane,"Satın Alma İptal Edildi");
			}
		      }
			}
		      else if(belirtec==3)
		      {
		    		int secim=(int) model.getValueAt(table_1.getSelectedRow(),0);  
				    int emlak_isletme_id=-1;
				    sql1="SELECT isletmeci_id FROM Emlak WHERE emlak_id="+secim+";";
				    rs1=Database.Sorgu(sql1);
				    try {
						while(rs1.next())
						{
							emlak_isletme_id=rs1.getInt(1);
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		    		int cevap=JOptionPane.showConfirmDialog(contentPane,"ID'si "+emlak_isletme_id+" Olan Emlaga Kiralama İzni Tamamlansın Mı?");
					if(cevap==JOptionPane.YES_OPTION)
					{
						// kiralama izin alma islemi gerceklestirilecek
		             String kiralama_fiyat=JOptionPane.showInputDialog(contentPane,"Lütfen Kiralama Fiyatını Giriniz:");    	
		 			 sql1="INSERT INTO KiralıkList(emlak_id,isletme_id,kiralama_ücret) VALUES("+secim+","+isletme_id+","+kiralama_fiyat+");";
		             Database.add(sql1);
		             String s="",t=Database.dAte();
		             getsaat();
		             s="INSERT INTO İslemler(islem_sahibiı_id,islem_türü,islem_yer_tür,islem_tarihi,islem_saat) VALUES("+Giris.kullanıcı_id+",'listeye ekleme','emlak','"+t+"','"+saat+"');";		
		           	Database.add(s);	 
					}
					else {
						JOptionPane.showMessageDialog(contentPane,"Kiralama İzni İptal Edildi");
					}  
		      }
		      else if(belirtec==4){
		     if(isletme_tür.equals("arsa"))
		     {
		    	 int secim=(int) model.getValueAt(table_1.getSelectedRow(),0);  
					int cevap=JOptionPane.showConfirmDialog(contentPane,"ID'si "+secim+" Olan Emlaga Satın Alma İzni Tamamlansın Mı?");
					if(cevap==JOptionPane.YES_OPTION)
					{
						// Satılma izni islemi gerceklestirilecek
						 String satıs_fiyat=JOptionPane.showInputDialog(contentPane,"Lütfen Satıs Fiyatını Giriniz:");    	
			 			 sql1="INSERT INTO SatısList(emlak_id,satılık_yer_alan_id,satıs_fiyat) VALUES("+secim+","+isletme_id+","+satıs_fiyat+");";
			             Database.add(sql1);
			             String s="",t=Database.dAte();
			          getsaat();
			          s="INSERT INTO İslemler(islem_sahibiı_id,islem_türü,islem_yer_tür,islem_tarihi,islem_saat) VALUES("+Giris.kullanıcı_id+",'listeye ekleme','emlak','"+t+"','"+saat+"');";		
			           	Database.add(s);	 
			          }
					else {
						JOptionPane.showMessageDialog(contentPane,"Satma İzni İptal Edildi");
					} 
		     }
		     else
		     {
		    	 int alan_id=-1;
		    	 sql1="SELECT alan_id FROM İsletmeler WHERE isletme_id="+isletme_id+";";
			     rs1=Database.Sorgu(sql1);
			     try {
					while(rs1.next())
					 {
						 alan_id=rs1.getInt(1);
					 }
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	 
		     int secim=(int) model.getValueAt(table_1.getSelectedRow(),0);  
			int cevap=JOptionPane.showConfirmDialog(contentPane,"ID'si "+secim+" Olan Emlaga Satın Alma İzni Tamamlansın Mı?");
			if(cevap==JOptionPane.YES_OPTION)
			{
				// Satılma izni islemi gerceklestirilecek
				 String satıs_fiyat=JOptionPane.showInputDialog(contentPane,"Lütfen Satıs Fiyatını Giriniz:");    	
	 			 sql1="INSERT INTO SatısList(emlak_id,satılık_yer_alan_id,satıs_fiyat) VALUES("+secim+","+alan_id+","+satıs_fiyat+");";
	             Database.add(sql1);
	             String s="",t=Database.dAte();
	          getsaat();
	          s="INSERT INTO İslemler(islem_sahibiı_id,islem_türü,islem_yer_tür,islem_tarihi,islem_saat) VALUES("+Giris.kullanıcı_id+",'listeye ekleme','emlak','"+t+"','"+saat+"');";		
	           	Database.add(s);	 
	          }
			else {
				JOptionPane.showMessageDialog(contentPane,"Satma İzni İptal Edildi");
			}
		      }
		      }
	 
			}
		});
		table_1.setBounds(164, 274, 499, 142);
	    
		//contentPane.add(table_1);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(32, 79, 909, 460);
		contentPane.add(scrollPane);
		
		scrollPane.setViewportView(table_1);
		
		JButton back = new JButton("");
		back.setBackground(new Color(176, 196, 222));
		back.setBounds(946, 0, 40, 68);
		Image i = new ImageIcon(this.getClass().getResource("/door.png")).getImage();
		back.setIcon(new ImageIcon(i));
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setVis();
				if(belirtec==3 || belirtec==4)
				{
					İsletmeciSettings is = new İsletmeciSettings(isletme_tür,isletme_id,belirtec2,al_no);
				}
				else {
					İslemPanel ip = new İslemPanel(al_no,mesaj);
				}
			}
		
		});
		contentPane.add(back);
		
		
		
		
		
		setVisible(true);
	}
}
