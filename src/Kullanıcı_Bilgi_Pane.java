import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.SwingConstants;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Kullanıcı_Bilgi_Pane extends JFrame {

	private JPanel contentPane;
	private JTable t1= new JTable(),t2= new JTable(),t3= new JTable();
	private JScrollPane s1 = new JScrollPane(),s2 = new JScrollPane(),s3 = new JScrollPane(); ;
    private Object [] kolon1 = {"İD","AD","Soyad","Mevcut Yemek","Mevcut Esya","Mevcut Para","Kayıt Tarihi"};
    private Object [] kolon2 = {"İsletme İD","İsletme Tür","Ürün Satıs Fiyatı","Sabit Geliri","Calısan Sayısı"};
    private Object [] kolon3 = {"İslem İD","İslemin Uygulandıgı Yer","İslem Türü","Maliyet","İslem Tarihi","İslem Saati"};
    private Object[] satır1= new Object[7];
    private Object[] satır2=new Object[5];
    private Object[] satır3= new Object[6];
    private final JLabel l1 = new JLabel("Kullanıcı Genel Bilgileri:");
    private final JLabel l2 = new JLabel("Kullanıcı İsletme Bilgileri:");
    private final JLabel l3 = new JLabel("Kullanıcı İslem Bilgileri:");
	private  DefaultTableModel model1 = new DefaultTableModel();
	private  DefaultTableModel model2 = new DefaultTableModel();
	private  DefaultTableModel model3 = new DefaultTableModel();
	private String sql1,sql2,sql3;
	private ResultSet rs1,rs2,r3;
	private int sabit_ücret=-1;
	   private String saat="";
	    public void getsaat()
	    {
	 	    Date d = new Date();
	 	    SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss");
	         saat=format2.format(d);
	         System.out.println("saat->"+saat);
	    }
	public void creatTables(int kullanıcı_id)
	{
		String k_tür="";
	sql1="SELECT kullanıcı_ad,kullanıcı_soyad,baslangıc_tarihi,kullanıcı_türü FROM Kullanıcılar WHERE kullanıcı_no="+kullanıcı_id+";";
	rs1=Database.Sorgu(sql1);
	try {
		while(rs1.next())
		{
		    k_tür=rs1.getString("kullanıcı_türü");	
			satır1[6]=rs1.getString("baslangıc_tarihi");
			satır1[1]=rs1.getString("kullanıcı_ad");
			satır1[2]=rs1.getString("kullanıcı_soyad");
		    satır1[0]=kullanıcı_id;
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	if(k_tür.equals("yönetici"))
	{
		satır1[4]="Sınırsız";
		satır1[3]="Sınırsız";
		satır1[5]="Sınırsız";
	}
	else
	{
	sql1="SELECT kullanıcı_yemek,kullanıcı_esya,kullanıcı_para FROM Oyuncular WHERE kullanıcı_id="+kullanıcı_id+";";	
	 rs1=Database.Sorgu(sql1);	
	try {
		while(rs1.next())	
		{
			satır1[4]=rs1.getInt("kullanıcı_esya");
			satır1[3]=rs1.getInt("kullanıcı_yemek");
			satır1[5]=rs1.getInt("kullanıcı_para");
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	  	model1.addRow(satır1);
	  	
     sql1="SELECT isletme_sabit_gelir FROM İsletmeSistem ;";
     rs1=Database.Sorgu(sql1);
     try {
		while(rs1.next())
		 {
			 sabit_ücret=rs1.getInt(1);
		 }
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
     ArrayList<Integer> alan_id = new ArrayList<Integer>();
     sql1="SELECT  alan_id FROM Alanlar WHERE kullanıcı_id="+kullanıcı_id+";";
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
    		  satır2[0]="(alan_id)"+alan_id.get(i);
    	      satır2[1]="arsa";
    	      satır2[2]="-----";
    	      satır2[3]="-----";
    	      satır2[4]="-----";
    	      model2.addRow(satır2);
    	   }
    	   else {
    		   String isletme_tür="";
    		   int calısan_say=-1,isletme_id=-1,satıs_fiyat=-1;
    		 sql2="SELECT isletme_tür,calısan_sayısı,isletme_id FROM İsletmeler WHERE alan_id="+alan_id.get(i)+";";   
    		   rs2=Database.Sorgu(sql2);
    		   try {
				while(rs2.next())
				   {
					   isletme_tür=rs2.getString("isletme_tür");
					   System.out.println("isletme_tür=>"+isletme_tür);
					   isletme_id=rs2.getInt("isletme_id");
					   calısan_say=rs2.getInt("calısan_sayısı");
				   }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		 if(isletme_tür.equals("emlak"))  
    		 {
    			 sql1="SELECT komisyon FROM Emlak WHERE isletmeci_id="+isletme_id+";";
    			 rs1=Database.Sorgu(sql1);
    			 try {
					while(rs1.next())
					 {
						 satıs_fiyat=rs1.getInt(1);
					 }
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		 }
    		 else if(isletme_tür.equals("magaza"))
    		 {
    			 sql1="SELECT esya_ücreti FROM Magaza WHERE isletmeci_id="+isletme_id+";";
    			 rs1=Database.Sorgu(sql1);
    			 try {
					while(rs1.next())
					 {
						 satıs_fiyat=rs1.getInt(1);
					 }
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		 }
    		 else if(isletme_tür.equals("market"))
    		 {
    			 sql1="SELECT yiyecek_ücreti FROM Market WHERE isletmeci_id="+isletme_id+";";
    			 rs1=Database.Sorgu(sql1);
    			 try {
					while(rs1.next())
					 {
						 satıs_fiyat=rs1.getInt(1);
					 }
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		 }
    		 satır2[0]=isletme_id;
   	      satır2[1]=isletme_tür;
   	      satır2[2]=satıs_fiyat;
   	      satır2[3]=sabit_ücret;
   	      satır2[4]=calısan_say;
   	      model2.addRow(satır2);
    		 
    	   } 	       
    	}
    }
    //"İslem İD","İslemin Yeri","İslem Türü","Maliyet","İslem Tarihi"
    String islem_tarih="",islem_tür="",maliyet="",islem_yer_türü="",islem_saat=""; 
    ArrayList<Integer> islem_id = new ArrayList<Integer>();
    
    sql1="SELECT islem_id FROM İslemler WHERE islem_sahibiı_id="+kullanıcı_id+";"; 
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
    System.out.println("islem_id_sayısı="+islem_id.size());
    if(islem_id.size()>0)
    {
    for(int i=0;i<islem_id.size();i++)	
    	{
    sql1="SELECT islem_yer_tür,islem_tarihi,islem_türü,islem_saat FROM İslemler WHERE islem_id="+islem_id.get(i)+";"; 
    rs1=Database.Sorgu(sql1);
    try {
		while(rs1.next())
		{
		    	islem_tür=rs1.getString("islem_türü");
		    	islem_yer_türü=rs1.getString("islem_yer_tür");
		    	islem_tarih=rs1.getString("islem_tarihi");
		        islem_saat=rs1.getString("islem_saat");
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    if(islem_tür.equals("listeye ekleme") || islem_tür.equals("listeden cıkarma") || islem_tür.equals("oyuna giris") || islem_tür.equals("oyundan cıkıs") || islem_tür.equals("mesaj") || islem_tür.equals("alana giris") || islem_tür.equals("alandan cıkıs") || islem_tür.equals("calısmaya baslama") || islem_tür.equals("calısma bitis") || islem_tür.equals("kiralık bitis"))
    {
           maliyet=""; 	
           System.out.println("islem_tür->"+islem_tür+" maliyet->"+maliyet);
           satır3[0]=islem_id.get(i);
           satır3[1]=islem_yer_türü;
           satır3[2]=islem_tür;
           satır3[3]=maliyet;
           satır3[4]=islem_tarih;
           satır3[5]=islem_saat;
           model3.addRow(satır3);
           continue;
    } 
    else if(islem_tür.equals("satın alma"))
    {
    	sql2="SELECT satıs_fiyatı FROM SatınAlımlar WHERE islem_id="+islem_id.get(i)+";";
    	rs2=Database.Sorgu(sql2);
    	try {
			while(rs2.next())
			{
				maliyet=Integer.toString(rs2.getInt(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   System.out.println("islem_tür->"+islem_tür+" maliyet->"+maliyet);
   satır3[0]=islem_id.get(i);
   satır3[1]=islem_yer_türü;
   satır3[2]=islem_tür;
   satır3[3]="-"+maliyet;
   satır3[4]=islem_tarih;
   satır3[5]=islem_saat;
   model3.addRow(satır3);
   continue;
    }
    else if(islem_tür.equals("kiralama"))
    {
    	sql2="SELECT kiralama_fiyatı FROM Kiralamalar WHERE islem_id="+islem_id.get(i)+";";
    	rs2=Database.Sorgu(sql2);
    	try {
			while(rs2.next())
			{
				maliyet=Integer.toString(rs2.getInt(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 System.out.println("islem_tür->"+islem_tür+" maliyet->"+maliyet);
    	    satır3[0]=islem_id.get(i);
    	    satır3[1]=islem_yer_türü;
    	    satır3[2]=islem_tür;
    	    satır3[3]="-"+maliyet;
    	    satır3[4]=islem_tarih;
    	    satır3[5]=islem_saat;
    	    model3.addRow(satır3);
            continue;

    }
    else if(islem_tür.equals("isletme kurma"))
    {
    	String tür="";
    	sql2="SELECT islem_yer_tür FROM İslemler WHERE islem_id="+islem_id.get(i)+";";
    	rs2=Database.Sorgu(sql2);
    	try {
			while(rs2.next())
			{
				tür=rs2.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    if(tür.equals("emlak"))	
    {
    	sql2="SELECT isletme_kurma_fiyatı FROM EmlakBilgi ;";
    	rs2=Database.Sorgu(sql2);
    	try {
			while(rs2.next())
			{
				maliyet=Integer.toString(rs2.getInt(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    else if(tür.equals("market"))
    {
    	sql2="SELECT isletme_kurma_fiyatı FROM MarketBilgi ;";
    	rs2=Database.Sorgu(sql2);
    	try {
			while(rs2.next())
			{
				maliyet=Integer.toString(rs2.getInt(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    else if(tür.equals("magaza")){
    	sql2="SELECT isletme_kurma_fiyatı FROM MagazaBilgi ;";
    	rs2=Database.Sorgu(sql2);
    	try {
			while(rs2.next())
			{
				maliyet=Integer.toString(rs2.getInt(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    System.out.println("islem_tür->"+islem_tür+" maliyet->"+maliyet);
    satır3[0]=islem_id.get(i);
    satır3[1]=islem_yer_türü;
    satır3[2]=islem_tür;
    satır3[3]="-"+maliyet;
    satır3[4]=islem_tarih;
    satır3[5]=islem_saat;
    model3.addRow(satır3);
    continue;

    }
    else if(islem_tür.equals("yükseltme"))
    {
    	sql2="SELECT seviye_yükseltme_ücreti FROM İsletmeSistem;";
    	rs2=Database.Sorgu(sql2);
    	try {
			while(rs2.next())
			{
				maliyet=Integer.toString(rs2.getInt(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    maliyet="-("+maliyet+"+(2000*k))";
     System.out.println("islem_tür->"+islem_tür+" maliyet->"+maliyet);
     satır3[0]=islem_id.get(i);
     satır3[1]=islem_yer_türü;
     satır3[2]=islem_tür;
     satır3[3]=maliyet;
     satır3[4]=islem_tarih;
     satır3[5]=islem_saat;
     model3.addRow(satır3);
     continue;

    }
    }
    } 
    ArrayList<Integer>kiralama_id = new ArrayList<Integer>();
   ArrayList<Integer> satma_id = new ArrayList<Integer>();
    int is_id=-1;
    int al_id=-1;
    String t="";
    sql1="SELECT satılık_id FROM SatınAlımlar WHERE satan_id="+kullanıcı_id+";";
    rs1=Database.Sorgu(sql1);
    try {
		while(rs1.next())
		{
			satma_id.add(rs1.getInt(1));
		}
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
    if(satma_id.size()>0)
    {
    	for(int i=0;i<satma_id.size();i++)
    	{
    		 sql1="SELECT satıs_yer_alan_id,islem_id,satıs_fiyatı,satıs_tarihi,satıs_baslangıc_saati FROM SatınAlımlar WHERE satılık_id="+satma_id.get(i)+";";
    		    rs1=Database.Sorgu(sql1);
    		    try {
    				while(rs1.next())
    				{
    					maliyet=Integer.toString(rs1.getInt("satıs_fiyatı"));
    					islem_tarih=rs1.getString("satıs_tarihi");
    				    islem_tür="satma";
    				    is_id=rs1.getInt("islem_id");
    			 	    islem_saat=rs1.getString("satıs_baslangıc_saati");
    			        al_id=rs1.getInt("satıs_yer_alan_id");	
    				}
    			} catch (SQLException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    		 sql2="SELECT isletme_tür FROM İsletmeler WHERE alan_id="+al_id+";";
    		 rs2=Database.Sorgu(sql2);
    		 try {
				while(rs2.next())
				 {
					 t=rs2.getString(1);
				 }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		 if(t.isEmpty())
    		 {
    			 t="arsa";
    		 }
    		    satır3[0]=is_id;
    		    satır3[1]=t;
    		    satır3[2]=islem_tür;
    		    satır3[3]="+"+maliyet;
    		    satır3[4]=islem_tarih;
    		    satır3[5]=islem_saat;
    		    model3.addRow(satır3);	
    		
    		
    	}
    	
    }
    sql1="SELECT kira_id FROM Kiralamalar WHERE kiralatan_id="+kullanıcı_id+";";
    rs1=Database.Sorgu(sql1);
    try {
		while(rs1.next())
		{
			kiralama_id.add(rs1.getInt(1));
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    if(!(kiralama_id.size()==0))
    {
    for(int i=0;i<kiralama_id.size();i++)
    {	
    sql1="SELECT islem_id,kiralama_fiyatı,kira_baslangıc_tarihi,kira_baslangıc_saati FROM Kiralamalar WHERE kira_id="+kiralama_id.get(i)+";";
    rs1=Database.Sorgu(sql1);
    try {
		while(rs1.next())
		{
			maliyet=Integer.toString(rs1.getInt("kiralama_fiyatı"));
			islem_tarih=rs1.getString("kira_baslangıc_tarihi");
		    islem_tür="kiralık verme";
		    is_id=rs1.getInt("islem_id");
		    islem_saat=rs1.getString("kira_baslangıc_saati");
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    satır3[0]=is_id;
    satır3[1]="emlak";
    satır3[2]=islem_tür;
    satır3[3]="+"+maliyet;
    satır3[4]=islem_tarih;
    satır3[5]=islem_saat;
    model3.addRow(satır3);
    }
    }
	
	}
	public void setVis()
	{
		this.setVisible(false);
	}
	public Kullanıcı_Bilgi_Pane(int kullanıcı_id,int belirtec) {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(0,0,1100,1000);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(75, 0, 130));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel header = new JLabel("BİLGİLER");
		header.setHorizontalAlignment(SwingConstants.CENTER);
		header.setFont(new Font("Tahoma", Font.ITALIC, 20));
		header.setBounds(179, 0, 709, 41);
		contentPane.add(header);
		
		JButton back = new JButton("");
		back.setBackground(new Color(176, 196, 222));
		back.setBounds(1041, -1, 45, 63);
		Image i1 = new ImageIcon(this.getClass().getResource("/door.png")).getImage();
		back.setIcon(new ImageIcon(i1));
		contentPane.add(back);
		
		s1.setBounds(22, 82, 1054, 108);
		contentPane.add(s1);
		
		s2.setBounds(22, 219, 1054, 159);
		contentPane.add(s2);
		
		s3.setBounds(22, 428, 1054, 392);
		contentPane.add(s3);
		model1.setColumnIdentifiers(kolon1);	
		t1.setModel(model1);
		t1.setBounds(37, 256, 208, 180);
		model2.setColumnIdentifiers(kolon2);	
		t2.setModel(model2);

		t2.setBounds(377, 271, 211, 180);
		model3.setColumnIdentifiers(kolon3);	
		t3.setModel(model3);

		t3.setBounds(680, 271, 159, 172);
		t3.addMouseListener( new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
		int onay = JOptionPane.showConfirmDialog(contentPane,"Bu İslem Geçmisten Silinsin Mi?");	
			if(onay==JOptionPane.YES_OPTION)	
			{
				int islem_id=(int) t3.getValueAt(t3.getSelectedRow(),0);
				String s = "DELETE FROM İslemler WHERE islem_id="+islem_id+";";
			    Database.update(s);
			    model1.setRowCount(0);
			    model2.setRowCount(0);
				model3.setRowCount(0);
                  creatTables(kullanıcı_id);
			}		
			}		
		});
			
		
        s1.setViewportView(t1); 
		s2.setViewportView(t2);
		s3.setViewportView(t3);

		
		l1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		l1.setBounds(0, 51, 608, 30);
		
		contentPane.add(l1);
		l2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		l2.setBounds(0, 190, 608, 30);
		
		contentPane.add(l2);
		l3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		l3.setBounds(0, 388, 608, 30);
		
		contentPane.add(l3);
		
		creatTables(kullanıcı_id);
		
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			setVis();
			if(belirtec==1) {
			OyuncuOyun oo = new OyuncuOyun();	
			}
			else if(belirtec==2){
				OyuncuListPane olp = new OyuncuListPane();
			}
			else {
				YöneticiOyun yo = new YöneticiOyun();
			}
			}
		});
		setVisible(true);
	}
}
