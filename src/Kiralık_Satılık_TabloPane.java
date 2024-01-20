import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;

public class Kiralık_Satılık_TabloPane extends JFrame {

	private JPanel contentPane;
	private JTable table;
    private JScrollPane scrollPane;
	private DefaultTableModel model = new DefaultTableModel();
	private Object[] kolonlar;
	private Object[] satırlar;
	private String sql1,sql2,sql3,sql4,sql5;
	private ResultSet rs1,rs2,rs3,rs4,rs5;
	private int emlak_id=0,isletme_id=0,alan_id=0,kul_id=0,isletme_alan_id=-1;
	public void setVis()
	{
		this.setVisible(false);
	}
	public void buildTablo(Object kolon[],int click)
	{
		model.setColumnIdentifiers(kolon);
		kolonlar=kolon;
		satırlar = new Object[kolonlar.length];
		if(click==1) {
			ArrayList<Integer> kl_id = new ArrayList<Integer>();
			sql1="SELECT kl_id FROM KiralıkList;";
			rs1=Database.Sorgu(sql1);
				try {
					while(rs1.next())
					{
						kl_id.add(rs1.getInt(1));
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(kl_id.size()>0)
			{
			for(int i=0;i<kl_id.size();i++)
				{
				sql1="SELECT emlak_id,isletme_id,kiralama_ücret FROM KiralıkList WHERE kl_id="+kl_id.get(i)+";";
				rs1=Database.Sorgu(sql1);
					try {
						while(rs1.next())
						{
							emlak_id=rs1.getInt("emlak_id");
							isletme_id=rs1.getInt("isletme_id");
							satırlar[1]=rs1.getInt("kiralama_ücret");
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					sql2="SELECT alan_id,isletme_tür,calısan_sayısı FROM İsletmeler WHERE isletme_id="+isletme_id+";";
					rs2=Database.Sorgu(sql2);
					try {
						while(rs2.next())
						{
							satırlar[0]=rs2.getString("isletme_tür");
							satırlar[2]=rs2.getInt("calısan_sayısı");
							alan_id=rs2.getInt("alan_id");	
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 sql3="SELECT kullanıcı_id FROM Alanlar WHERE alan_id="+alan_id+";";
					   rs3=Database.Sorgu(sql3);
					   try {
						while(rs3.next())
						   {
							   kul_id=rs3.getInt("kullanıcı_id");
						   }
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					   sql4="SELECT kullanıcı_ad,kullanıcı_soyad FROM Kullanıcılar WHERE kullanıcı_no="+kul_id+";";
						 rs4=Database.Sorgu(sql4);
						  try {
							while(rs4.next())
							  {
								  satırlar[3]=rs4.getString("kullanıcı_ad")+" "+rs4.getString("kullanıcı_soyad");
							  }
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}  
						  sql5="SELECT isletmeci_id FROM Emlak WHERE emlak_id="+emlak_id+";";
						   rs5=Database.Sorgu(sql5);
						   try {
							while(rs5.next())
							   {
								   satırlar[4]=rs5.getInt(1);
							   }
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						model.addRow(satırlar);	 
					   
			}
			}

		}
		else {
			ArrayList<Integer> sl_id =new ArrayList<Integer>();
			sql1="SELECT sl_id FROM SatısList;";
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
							emlak_id=rs1.getInt("emlak_id");
							isletme_alan_id=rs1.getInt("satılık_yer_alan_id");
							satırlar[1]=rs1.getInt("satıs_fiyat");
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
               String tür ="";
              sql2="SELECT alan_tür FROM Alanlar WHERE alan_id="+isletme_alan_id+";"; 
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
                if(tür.equals("arsa"))    
                {
                	satırlar[0]="arsa";
                	satırlar[2]=0;
                	 sql3="SELECT kullanıcı_id FROM Alanlar WHERE alan_id="+isletme_alan_id+";";
                     rs3=Database.Sorgu(sql3);
                     
     					try {
     						while(rs3.next())
     						{
     						
     								kul_id=rs3.getInt(1);
     							
     						}
     					} catch (SQLException e1) {
     						// TODO Auto-generated catch block
     						e1.printStackTrace();
     					}
     				
     					sql4="SELECT kullanıcı_ad,kullanıcı_soyad FROM Kullanıcılar WHERE kullanıcı_no="+kul_id+";";
     					 rs4=Database.Sorgu(sql4);
     					  try {
     						while(rs4.next())
     						  {
     							  satırlar[3]=rs4.getString("kullanıcı_ad")+" "+rs4.getString("kullanıcı_soyad");
     						  }
     					} catch (SQLException e) {
     						// TODO Auto-generated catch block
     						e.printStackTrace();
     					}
     					   sql5="SELECT isletmeci_id FROM Emlak WHERE emlak_id="+emlak_id+";";
     					   rs5=Database.Sorgu(sql5);
     					   try {
     						while(rs5.next())
     						   {
     							   satırlar[4]=rs5.getInt(1);
     						   }
     					} catch (SQLException e) {
     						// TODO Auto-generated catch block
     						e.printStackTrace();
     					}
     					model.addRow(satırlar);	

                }
                else {
                sql1="SELECT isletme_tür,calısan_sayısı FROM İsletmeler WHERE alan_id="+isletme_alan_id+";";	
                rs1=Database.Sorgu(sql1);
                try {
					while(rs1.next())
					{
						satırlar[0]=rs1.getString("isletme_tür");
						satırlar[2]=rs1.getInt("calısan_sayısı");
						
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                sql3="SELECT kullanıcı_id FROM Alanlar WHERE alan_id="+isletme_alan_id+";";
                rs3=Database.Sorgu(sql3);
                
					try {
						while(rs3.next())
						{
						
								kul_id=rs3.getInt(1);
							
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
					sql4="SELECT kullanıcı_ad,kullanıcı_soyad FROM Kullanıcılar WHERE kullanıcı_no="+kul_id+";";
					 rs4=Database.Sorgu(sql4);
					  try {
						while(rs4.next())
						  {
							  satırlar[3]=rs4.getString("kullanıcı_ad")+" "+rs4.getString("kullanıcı_soyad");
						  }
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					   sql5="SELECT isletmeci_id FROM Emlak WHERE emlak_id="+emlak_id+";";
					   rs5=Database.Sorgu(sql5);
					   try {
						while(rs5.next())
						   {
							   satırlar[4]=rs5.getInt(1);
						   }
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					model.addRow(satırlar);	
                }
                
				}
				
			}
		
		}
		
	}
	public Kiralık_Satılık_TabloPane(Object kolon[],String label,int click,int belirtec) {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(0,0,1200,700);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(72, 61, 139));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JLabel header = new JLabel(label);
		header.setFont(new Font("Tahoma", Font.PLAIN, 20));
		header.setHorizontalAlignment(SwingConstants.CENTER);
		header.setBounds(203, 10, 616, 48);
		contentPane.add(header);
		
		table = new JTable();
		table.setModel(model);
		table.setBounds(151, 359, 510, 255);
		
	 scrollPane = new JScrollPane();
		scrollPane.setBounds(33, 82, 1143, 571);
		scrollPane.setViewportView(table);
		contentPane.add(scrollPane);
		
		JButton back = new JButton("");
		back.setBounds(1123, 10, 53, 62);
		Image x1 = new ImageIcon(this.getClass().getResource("/door.png")).getImage();
	    back.setIcon(new ImageIcon(x1));
		back.addActionListener((ActionListener) new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
             setVis();
             if(belirtec==2) {
             OyuncuOyun oo = new OyuncuOyun();
             }
             else {
            	 YöneticiOyun yo = new YöneticiOyun();
             }
             }
	    });
		contentPane.add(back);
	buildTablo(kolon,click);
		setVisible(true);
	}
}
