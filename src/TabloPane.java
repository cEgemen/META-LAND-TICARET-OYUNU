import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import javax.swing.SwingConstants;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;

public class TabloPane extends JFrame {

	private JPanel contentPane;
	private DefaultTableModel model=new DefaultTableModel();
	private DefaultTableModel model2=new DefaultTableModel();
	private Object[] kolonlar={"Gönderen Ad-Soyad","Mesaj"};
	private Object[] satırlar=new Object[2];
	private Object[] kolonlar2={"Kullanıcı ID","Kullanıcı Ad-Soyad","Calısama Baslangıc Tarih","Calısma Gün Sayısı","Calısma Bitis Tarihi","Calısma Saatleri"};
	private Object[] satırlar2=new Object[6];
	private JTable table_1;
	private JScrollPane scrollPane;
    private String sql1,sql2;
    private ResultSet rs1,rs2;
    private JButton back;
	
    public void setVis()
    {
    	this.setVisible(false);
    }
	public void setClose()
    {
    	this.setClose();
    }
    public void creatTables(int belirtec,int isletme_id)
    { 
    	
    if(belirtec==1 || belirtec==2)	
    	{
    	ArrayList<Integer> iletisim_id = new ArrayList<Integer>();
    	String g_ad="",g_soyad="";
    	int count=-1;
         sql1="SELECT COUNT(mesaj) FROM İletisimler WHERE alıcı_id="+Giris.kullanıcı_id+";";
         rs1=Database.Sorgu(sql1);
			try {
				while(rs1.next())
				 {
					count=rs1.getInt(1);
				 }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	
		if(count>0)	
			{
			 sql1="SELECT iletisim_id FROM İletisimler WHERE alıcı_id="+Giris.kullanıcı_id+";";
	         rs1=Database.Sorgu(sql1);
				try {
					while(rs1.next())
					 {
						iletisim_id.add(rs1.getInt(1));
					 }
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
		for(int i=0;i<iletisim_id.size();i++)
		{
			int gönderen_id=-1;
			sql1="SELECT mesaj,gönderen_id FROM İletisimler WHERE iletisim_id="+iletisim_id.get(i)+";";
	         rs1=Database.Sorgu(sql1);
				try {
					while(rs1.next())
					 {
						satırlar[1]=rs1.getString("mesaj");
					     gönderen_id=rs1.getInt("gönderen_id");
					 }
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 sql2="SELECT kullanıcı_ad,kullanıcı_soyad FROM Kullanıcılar WHERE kullanıcı_no="+gönderen_id+";";
				 rs2=Database.Sorgu(sql2);
				 try {
					while(rs2.next())
					 {
						 g_ad=rs2.getString("kullanıcı_ad");
						 g_soyad=rs2.getString("kullanıcı_soyad");
					 }
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
				 satırlar[0]=g_ad+" "+g_soyad;
					model.addRow(satırlar);	 
		}
    	}
    }
    else {
    	int count=1;
    	ArrayList<Integer> calısanlar_id = new ArrayList<Integer>();
    	 sql1="SELECT calısanlar_id FROM Calısanlar WHERE isletme_id="+isletme_id+";"; 
    	   rs1=Database.Sorgu(sql1); 	
    	    try {
    			while(rs1.next())	
    			{
    				System.out.println("count->"+count);
    			        calısanlar_id.add(rs1.getInt(1));	
    			        System.out.println("calısan_id=>"+rs1.getInt(1));
    			         count++;
    			}
    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	
   if(calısanlar_id.size()>0) { 
  for(int i=0;i<calısanlar_id.size();i++) {	
	  int k_id=-1;
   sql1="SELECT kullanıcı_id,calısma_baslangıc_tarihi,calısma_gun_sayısı,calısma_bitis_tarihi,calısma_saatleri FROM Calısanlar WHERE calısanlar_id="+calısanlar_id.get(i)+";"; 
   rs1=Database.Sorgu(sql1); 	
    try {
		while(rs1.next())	
		{
			        k_id=rs1.getInt("kullanıcı_id");
		        	satırlar2[0]=rs1.getInt("kullanıcı_id");
			    	satırlar2[2]=rs1.getString("calısma_baslangıc_tarihi");
			    	satırlar2[3]=rs1.getInt("calısma_gun_sayısı");
			    	satırlar2[4]=rs1.getString("calısma_bitis_tarihi");
			    	satırlar2[5]=rs1.getString("calısma_saatleri");
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  sql2="SELECT kullanıcı_ad,kullanıcı_soyad FROM Kullanıcılar WHERE kullanıcı_no="+k_id+";";
  rs2=Database.Sorgu(sql2);
  try {
	while(rs2.next())
	  {
		satırlar2[1]=rs2.getString("kullanıcı_ad")+" "+rs2.getString("kullanıcı_soyad");  
	  }
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
  model2.addRow(satırlar2);
  }
   }	
    }
    }
	public TabloPane(int alan_no,String mesaj,int isletme_id,int belirtec) {
		setBounds(0,0,690,591);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(102, 205, 170));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel();
		if(belirtec==1)
		{
			lblNewLabel.setText("Mesaj Kutusu");
     	}
		else {
			lblNewLabel.setText("Calısanlar");
		}
		
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(150, 10, 427, 47);
		contentPane.add(lblNewLabel);
		
		model.setColumnIdentifiers(kolonlar);
		model2.setColumnIdentifiers(kolonlar2);
		creatTables(belirtec,isletme_id);
		table_1 = new JTable();
		if(belirtec==3) {
		table_1.setModel(model2);
		}
		else {
			table_1.setModel(model);
		}
		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(belirtec==1 || belirtec==2)
				{ 
			String mesaj = (String) model.getValueAt(table_1.getSelectedRow(),1);
			  JOptionPane.showMessageDialog(contentPane,"Mesajınız: "+mesaj);
			  int onay =JOptionPane.showConfirmDialog(contentPane,"Mesajı Silmek İster Misiniz?");
			  if(onay==JOptionPane.YES_OPTION)
			  {
				sql1="DELETE FROM İletisimler WHERE mesaj='"+mesaj+"';";
				Database.update(sql1);
				model.setRowCount(0);
				creatTables(belirtec,isletme_id);
			  }
			}
			}
		});
		table_1.setBounds(164, 274, 499, 142);
	    
		//contentPane.add(table_1);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 79, 656, 465);
		contentPane.add(scrollPane);
		
		scrollPane.setViewportView(table_1);
		
		back = new JButton("");
		back.setBackground(new Color(176, 196, 222));
		back.setBounds(625, 0, 51, 70);
		Image x1 = new ImageIcon(this.getClass().getResource("/door.png")).getImage();
		back.setIcon(new ImageIcon(x1));
	    back.addActionListener((ActionListener) new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
             setVis();
             if(belirtec==1) {
             OyuncuOyun oo = new OyuncuOyun();
             }
             if(belirtec==2)
             {
            	 YöneticiOyun yo = new YöneticiOyun();
             }
             else {
                    setVis();          	
             }
             }
	    });
	    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		contentPane.add(back);
		
		
		
		setVisible(true);
	}
}
