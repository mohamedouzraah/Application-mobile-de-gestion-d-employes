package Pack;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;

public class motdepasseoublié extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField t_username;
	private JTextField t_datenaissance;
	private JButton b_motdepasse;

	Connection conn = null;
	PreparedStatement prepared = null;
	ResultSet resultat = null;
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					motdepasseoublié frame = new motdepasseoublié();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public motdepasseoublié() {
		
		
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 399, 157);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		
		conn = connexionMySQL.connexionDb();

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel l_username = new JLabel("Username :");
		l_username.setBounds(55, 31, 76, 14);
		contentPane.add(l_username);
		
		t_username = new JTextField();
		t_username.setFont(new Font("Tahoma", Font.PLAIN, 9));
		t_username.setBounds(157, 28, 144, 14);
		contentPane.add(t_username);
		t_username.setColumns(10);
		
		t_datenaissance = new JTextField();
		t_datenaissance.setFont(new Font("Tahoma", Font.PLAIN, 9));
		t_datenaissance.setText("JJ/MM/AAAA");
		t_datenaissance.setBounds(157, 56, 144, 14);
		contentPane.add(t_datenaissance);
		t_datenaissance.setColumns(10);
		
		JLabel l_datenaissance = new JLabel("Date  naissance :");
		l_datenaissance.setBounds(55, 56, 101, 14);
		contentPane.add(l_datenaissance);
		
		b_motdepasse = new JButton("Get mot de passe");
		b_motdepasse.setBounds(157, 84, 144, 23);
		contentPane.add(b_motdepasse);
		
		b_motdepasse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String username0= t_username.getText().toString();
				String datenaissance0= t_datenaissance.getText().toString();
				
				String sql = "select * from utilisateurs";
                
			    try {
				prepared = conn.prepareStatement(sql);
				resultat = prepared.executeQuery();
				
				if(username0.equals("")||datenaissance0.equals("")) {
					JOptionPane.showMessageDialog(null,"remplisser les champs vides");
				}else {
				
					while(resultat.next()) {
					
					String username1 = resultat.getString("username");
					String datenaissance1= resultat.getString("date_naissance");
					String motpasse1= resultat.getString("mot_passe");
					
					if( username1.equals(username0) && datenaissance1.equals(datenaissance0) ) {
						
					JOptionPane.showMessageDialog(null, "Le mot de passe est :"+ motpasse1);}
					
				    }
				}
			    }catch(Exception ex) {
					 
					 ex.getMessage();
					 
				 }
				
				
			}
		});
	}
	
}
