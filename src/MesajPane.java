import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.Color;

public class MesajPane extends JFrame {

	private JPanel contentPane;
	private JTextArea tf1;
    private String mesaj="",sql;
    private ResultSet rs;
    private JButton back;
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
	public MesajPane(int alan_no,int k_id) {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(0, 0, 453, 639);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(135, 206, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tf1 = new JTextArea(10,10);
		tf1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		tf1.setBounds(55, 38, 297, 515);
		contentPane.add(tf1);
		tf1.setColumns(10);
		
		JButton b1 = new JButton("Gönder");
		b1.setBounds(145, 563, 120, 39);
		b1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mesaj=tf1.getText();
				if(mesaj.isEmpty())
				{
					JOptionPane.showMessageDialog(contentPane,"Bos Bir Mesaj Gönderilemez...");
				}
				else {
					String s="",t=Database.dAte();
					getsaat(); 
					s="INSERT INTO İslemler(islem_sahibiı_id,islem_türü,islem_yer_tür,islem_tarihi,islem_saat) VALUES("+Giris.kullanıcı_id+",'mesaj','sanal ortam','"+t+"','"+saat+"');";		
						Database.add(s);	 	
					 sql="INSERT INTO İletisimler(gönderen_id,alıcı_id,mesaj) VALUES("+Giris.kullanıcı_id+","+k_id+",'"+mesaj+"');";
					 Database.add(sql);
				JOptionPane.showMessageDialog(contentPane,"Mesajınız Gönderildi...");
				}
			}
			
		});
		contentPane.add(b1);
		
		JLabel header = new JLabel("MESAJER");
		header.setFont(new Font("Tahoma", Font.PLAIN, 18));
		header.setHorizontalAlignment(SwingConstants.CENTER);
		header.setBounds(98, 0, 205, 29);
		contentPane.add(header);
		
		back = new JButton("");
		back.setBackground(new Color(176, 196, 222));
		back.setBounds(386, 7, 53, 76);
		Image x1 = new ImageIcon(this.getClass().getResource("/door.png")).getImage();
		back.setIcon(new ImageIcon(x1));
	    back.addActionListener((ActionListener) new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
             setVis();
             İslemPanel ip = new İslemPanel(alan_no,mesaj);
			}
	    });
		contentPane.add(back);
		setVisible(true);
	}
}
