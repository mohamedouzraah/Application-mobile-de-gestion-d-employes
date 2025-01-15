package Pack;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class gestion_utl extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField t_motpasse;
	private JTextField t_date_naissance;
	private JTextField t_username;
	DefaultTableModel tableModel;
	private JTable table;
	String[] columnNames= new String[]{"Nom d'utilisateur","Date de naissance","Mot de passe"};
	
	private JMenuBar barre;
	private JMenu m_affichage, m_fenetre;
	private JMenuItem i_mtr, i_g_mtr, i_quitter;

	Connection conn = null;
	PreparedStatement prepared = null;
	ResultSet resultat = null;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gestion_utl frame = new gestion_utl();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public gestion_utl() {
		
		setTitle("Gestion d'un école");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		conn = connexionMySQL.connexionDb();
		
		
		
        barre= new JMenuBar();
		
		m_affichage= new JMenu("Affichage");
		m_fenetre= new JMenu("Fenetre");
		
		
		i_mtr= new JMenuItem("Les administrateurs"); i_mtr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			 setContentPane(afficherAdm());
			}
		});
		
		i_g_mtr= new JMenuItem("Gestion des administrateurs");i_g_mtr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setContentPane(contentPane);
			}
		});
		i_quitter= new JMenuItem("Quitter");i_quitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane a = new JOptionPane();
				int b = a.showConfirmDialog(null, "Voullez vous vraiment quetter ", "Quitter", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			
				if(b == JOptionPane.OK_OPTION) {
				dispose();
				}
			}
		});
		
		setJMenuBar(barre);
		barre.add(m_affichage);
		barre.add(m_fenetre);

		m_affichage.add(i_mtr);
		m_affichage.addSeparator();
		m_affichage.add(i_g_mtr);
		
		m_fenetre.add(i_quitter);
		
		
		
		
		JLabel lblNewLabel = new JLabel("Gestion des utilisateurs");
		lblNewLabel.setBounds(230, 134, 150, 14);
		contentPane.add(lblNewLabel);
		
		JLabel l_username = new JLabel("Username :");
		l_username.setBounds(17, 205, 100, 14);
		contentPane.add(l_username);
		
		JLabel l_date_naissance = new JLabel("Date de naissance :");
		l_date_naissance.setBounds(17, 234, 120, 14);
		contentPane.add(l_date_naissance);
		
		JLabel l_motpasse = new JLabel("Mot de passe :");
		l_motpasse.setBounds(17, 259, 100, 14);
		contentPane.add(l_motpasse);
		
		
		t_username = new JTextField();
		t_username.setFont(new Font("Tahoma", Font.PLAIN, 9));
		t_username.setBounds(130, 205, 150, 15);
		contentPane.add(t_username);
		t_username.setColumns(10);
		
		t_motpasse = new JTextField();
		t_motpasse.setFont(new Font("Tahoma", Font.PLAIN, 9));
		t_motpasse.setBounds(130, 259, 150, 15);
		contentPane.add(t_motpasse);
		t_motpasse.setColumns(10);
		
		t_date_naissance = new JTextField();
		t_date_naissance.setFont(new Font("Tahoma", Font.PLAIN, 9));
		t_date_naissance.setBounds(130, 234, 150, 15);
		contentPane.add(t_date_naissance);
		t_date_naissance.setColumns(10);
		
		
		JButton b_ajouter = new JButton("Ajouter");
		b_ajouter.setBackground(new Color(0, 128, 192));
		b_ajouter.setForeground(new Color(255, 255, 255));
		b_ajouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String sql="insert into utilisateurs(username,date_naissance,mot_passe) values(?,?,?)";
				if(!t_username.getText().equals("") && !t_date_naissance.getText().equals("") && !t_motpasse.getText().equals("")) {
				try {
					prepared = conn.prepareStatement(sql);
					prepared.setString(1,t_username.getText().toString());
					prepared.setString(2,t_date_naissance.getText().toString());
					prepared.setString(3,t_motpasse.getText().toString());
					prepared.execute();
					
					JOptionPane.showMessageDialog(null, "L'utilisateur est ajouteé !");
					t_username.setText("");
					t_date_naissance.setText("");
					t_motpasse.setText("");
					
					tableModel.setRowCount(0);
					updateTable();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				}else {
					JOptionPane.showMessageDialog(null,"Rempler les champs !");
				}
				
			}
		});
		
		b_ajouter.setBounds(158, 287, 100, 23);
		contentPane.add(b_ajouter);
		
		
		
		JButton b_supprimer = new JButton("Supprimer");
		b_supprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane a = new JOptionPane();
				int b = a.showConfirmDialog(null, "Voullez vous vraiment supprimer l'utlisateur ", "Supprimer un utilisateur", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			
				if(b == JOptionPane.OK_OPTION) {
				String sql="delete from utilisateurs where username=? and date_naissance=? and mot_passe=? ";
				try {
					if(!t_username.getText().equals("") && !t_date_naissance.getText().equals("") && !t_motpasse.getText().equals("")) {
					prepared= conn.prepareStatement(sql);
					prepared.setString(1, t_username.getText().toString());
					prepared.setString(2, t_date_naissance.getText().toString());
					prepared.setString(3, t_motpasse.getText().toString());
					prepared.execute();
					
					JOptionPane.showMessageDialog(null, "L'utilisateur a été supprimée !");
					t_username.setText("");
					t_date_naissance.setText("");
					t_motpasse.setText("");
					
					tableModel.setRowCount(0);
					updateTable();
					}else {
						JOptionPane.showMessageDialog(null,"Rempler les champs !");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				
			}
			}
		});
		b_supprimer.setForeground(Color.WHITE);
		b_supprimer.setBackground(new Color(0, 128, 192));
		b_supprimer.setBounds(158, 321, 100, 23);
		contentPane.add(b_supprimer);
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(300, 180, 300, 180);
		contentPane.add(scrollPane);
		
		tableModel = new DefaultTableModel(columnNames,0);
		table= new JTable(tableModel);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
			int ligne = table.getSelectedRow();
			String username1 = table.getModel().getValueAt(ligne,0).toString();
			String date_naissance1 = table.getModel().getValueAt(ligne,1).toString();
			String motpasse1 = table.getModel().getValueAt(ligne, 2).toString();
			
			t_username.setText(username1);
			t_date_naissance.setText(date_naissance1);
			t_motpasse.setText(motpasse1);
			}
		});
		scrollPane.setViewportView(table);
		
		//Bachground :--------------------------------------------------------------------------------------------------
		JLabel t_backgr = new JLabel("");
		t_backgr.setIcon(new ImageIcon("C:\\Users\\moham\\Desktop\\Eclipse\\GestionEcoleIcons\\BACK1.png"));
		t_backgr.setBounds(0, 0, 640, 480);
		contentPane.add(t_backgr);
		
		updateTable();
	}
	
	public JPanel  afficherAdm() {
		
		JPanel contenu =new JPanel();
        contenu.setBounds(0, 0, 640, 480);
        contenu.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("Les Administrateurs");
		lblNewLabel.setBounds(230, 134, 150, 14);
		contenu.add(lblNewLabel);
        
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(100, 180, 440, 200);
		contenu.add(scrollPane);
		
		tableModel = new DefaultTableModel(columnNames,0);
		table= new JTable(tableModel);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
			int ligne = table.getSelectedRow();
			String username1 = table.getModel().getValueAt(ligne,0).toString();
			String date_naissance1 = table.getModel().getValueAt(ligne,1).toString();
			String motpasse1 = table.getModel().getValueAt(ligne, 2).toString();
			
			t_username.setText(username1);
			t_date_naissance.setText(date_naissance1);
			t_motpasse.setText(motpasse1);
			}
		});
		scrollPane.setViewportView(table);
		
		JLabel t_backgr = new JLabel("");
		t_backgr.setIcon(new ImageIcon("C:\\Users\\moham\\Desktop\\Eclipse\\GestionEcoleIcons\\BACK1.png"));
		t_backgr.setBounds(0, 0, 640, 480);
		contenu.add(t_backgr);
		
		updateTable();
		contenu.setVisible(true);		
		return contenu;
	}
	
	public void updateTable(){
		String sql="select * from utilisateurs";
		
		try {
			
			prepared = conn.prepareStatement(sql);
			resultat = prepared.executeQuery();
			while(resultat.next()) {
				String username= resultat.getString("username");
				String date_naissance= resultat.getString("date_naissance");
				String motpasse= resultat.getString("mot_passe");
				tableModel.addRow(new Object[]{username,date_naissance ,motpasse});
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
	}
}
