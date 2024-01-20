import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class OyuncuListPane extends JFrame {

	private JPanel contentPane;
	private JTable t1= new JTable();
    private DefaultTableModel model = new DefaultTableModel();
	private Object[] kolan = {"Kullanıcı İD","Kullanıcı Ad","Kullanıcı Soyad","Kullanıcı Sifre"};
	private Object[] satır = new Object[4];
    private String sql1,sql2;
	private ResultSet rs1,rs2; 
    
	public void creatTablo()
	{
		ArrayList<Integer> oyuncu_id = new ArrayList<Integer>();
	    int k_id=-1,k_sifre=-1;
	    String k_ad="",k_soyad="";
	sql1 = "SELECT oyuncu_id FROM Oyuncular;";
	rs1=Database.Sorgu(sql1);
	try {
		while(rs1.next())
		{
			oyuncu_id.add(rs1.getInt(1));
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		if(!(oyuncu_id.size()==0))
		{
			for(int i=0;i<oyuncu_id.size();i++)
			{		
		 	sql1="SELECT kullanıcı_id FROM Oyuncular WHERE oyuncu_id="+oyuncu_id.get(i)+";";
		 	rs1=Database.Sorgu(sql1);
		 	try {
				while(rs1.next())
				{
					k_id=rs1.getInt(1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  sql2 = "SELECT kullanıcı_ad,kullanıcı_soyad,kullanıcı_sifre FROM Kullanıcılar WHERE kullanıcı_no="+k_id+";";	
		 	rs2=Database.Sorgu(sql2);
		 	try {
				while(rs2.next())
				{
				    	k_ad=rs2.getString("kullanıcı_ad");
				    	k_soyad=rs2.getString("kullanıcı_soyad");
				    	k_sifre=rs2.getInt("kullanıcı_sifre");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 	satır[0]=k_id;
		 	satır[1]=k_ad;
		 	satır[2]=k_soyad;
		 	satır[3]=k_sifre;
		 	model.addRow(satır);
		}
			
		}
		
	}
	public void setVis()
	{
		this.setVisible(false);
	}
	public OyuncuListPane() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(0,0,907,750);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 192, 203));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel header = new JLabel("OYUNCU BİLGİLERİ");
		header.setHorizontalAlignment(SwingConstants.CENTER);
		header.setFont(new Font("Tahoma", Font.PLAIN, 20));
		header.setBounds(216, 0, 518, 46);
		contentPane.add(header);
		
		model.setColumnIdentifiers(kolan);
		
		JScrollPane s1 = new JScrollPane();
		s1.setBounds(23, 127, 849, 586);
		contentPane.add(s1);
	
		t1.setModel(model);
		t1.setBounds(117, 413, 442, 290);
		t1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
		    int kullanıcı_id = (int) model.getValueAt(t1.getSelectedRow(),0);
			setVis();
			Kullanıcı_Bilgi_Pane kbp = new Kullanıcı_Bilgi_Pane(kullanıcı_id,2);
			
			}
		});
		s1.setViewportView(t1);
		
		creatTablo();
		
		JButton back = new JButton("");
		back.setBackground(new Color(176, 196, 222));
		back.setBounds(844, 0, 49, 78);
		Image i1 = new ImageIcon(this.getClass().getResource("/door.png")).getImage();
		back.setIcon(new ImageIcon(i1));
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			 	// TODO Auto-generated method stub
		    setVis();	
		   	 YöneticiOyun yo = new YöneticiOyun();	
				
			}
		});
		
		contentPane.add(back);
	   setVisible(true);
	}
}
