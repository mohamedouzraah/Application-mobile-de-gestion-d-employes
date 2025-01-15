package Pack;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



public class login extends JFrame {

	private JFrame frame;
	private JTextField t_user;
	private JPasswordField t_motpasse;
	
	Connection conn = null;
	PreparedStatement prepared = null;
	ResultSet resultat = null;



	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login window = new login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public login() {
		initialize();
	}

	
	
	private void initialize() {
		
		frame = new JFrame("Gestion d'un école");
		frame.setBounds(100, 100, 640, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		
		conn = connexionMySQL.connexionDb();
		
		JLabel l_user = new JLabel("Username :");
		l_user.setFont(new Font("Maiandra GD", Font.PLAIN, 11));
		l_user.setBounds(139, 208, 71, 14);
		frame.getContentPane().add(l_user);
		
		JLabel l_motpasse = new JLabel("Mot de passe :");
		l_motpasse.setFont(new Font("Maiandra GD", Font.PLAIN, 11));
		l_motpasse.setBounds(139, 233, 71, 14);
		frame.getContentPane().add(l_motpasse);
		
		t_user = new JTextField();
		t_user.setFont(new Font("Tahoma", Font.PLAIN, 10));
		t_user.setBounds(235, 208, 170, 16);
		frame.getContentPane().add(t_user);
		t_user.setColumns(10);
		
		t_motpasse = new JPasswordField();
		t_motpasse.setFont(new Font("Tahoma", Font.PLAIN, 10));
		t_motpasse.setBounds(235, 233, 170, 16);
		frame.getContentPane().add(t_motpasse);
	
		
		JButton b_conx = new JButton("Se connecter");
		b_conx.setBackground(new Color(0, 128, 192));
		b_conx.setForeground(new Color(255, 255, 255));
		b_conx.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		
		b_conx.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String username0= t_user.getText().toString();
				String motpasse0= t_motpasse.getText().toString();
				
				String sql = "select * from utilisateurs";
				if(username0.equals("")|| motpasse0.equals("")) {
					JOptionPane.showMessageDialog(null,"Remplisser les champs vides");
				}else {
			    try {
				prepared = conn.prepareStatement(sql);
				resultat = prepared.executeQuery();
				
				
					while(resultat.next()) {
					
					String username1 = resultat.getString("username");
					String motpasse1= resultat.getString("mot_passe");
					
					if( username1.equals(username0) && motpasse1.equals(motpasse0) ) {
						
					JOptionPane.showMessageDialog(null, "Conexion réussie");
					menuAdm Obj = new menuAdm();
					Obj.setVisible(true);
					Obj.setLocationRelativeTo(null);}
					
				    
				}
			    }catch(Exception ex) {
					 
					 ex.getMessage();
					 
				 }
				}
				
				
			}
		});
		
		
		b_conx.setBounds(235, 266, 170, 23);
		frame.getContentPane().add(b_conx);
		
		JLabel l_motdepasseoublié = new JLabel("Mot de passe oublié?");
		l_motdepasseoublié.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				motdepasseoublié Obj = new motdepasseoublié();
				Obj.setVisible(true);
				Obj.setLocationRelativeTo(null);
				
				
			}
		});
		l_motdepasseoublié.setFont(new Font("Tahoma", Font.PLAIN, 9));
		l_motdepasseoublié.setBounds(312, 247, 93, 14);
		frame.getContentPane().add(l_motdepasseoublié);
		
		
		
		JLabel lblNewLabel = new JLabel("Connexion Administrateur");
		lblNewLabel.setBounds(240, 134, 160, 14);
		frame.getContentPane().add(lblNewLabel);
		
		//Bachground :--------------------------------------------------------------------------------------------------
		JLabel l_backgr = new JLabel("");
		l_backgr.setIcon(new ImageIcon("C:\\Users\\moham\\Desktop\\Eclipse\\GestionEcoleIcons\\BACK.png"));
		l_backgr.setBounds(0, 0, 640, 480);
		frame.getContentPane().add(l_backgr);
	}
}
