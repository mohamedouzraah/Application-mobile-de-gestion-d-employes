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
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class gestion_etd extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField t_email;
	private JTextField t_date_naissance;
	private JTextField t_cin;
	private JTextField t_nom;
	private JTextField t_prenom;
	private JComboBox filiere;
	
	DefaultTableModel tableModel;
	private JTable table;
	String[] columnNames= new String[]{"Id Etudiant","Prenom","Nom","CIN","Date naissance","Email","filiere"};
	
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
					gestion_etd frame = new gestion_etd();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	
	public gestion_etd() {

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
		
		i_mtr= new JMenuItem("Les etudiants"); i_mtr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			 setContentPane(afficherEtd());
			}
		});
		
		i_g_mtr= new JMenuItem("Gestion des etudiants");i_g_mtr.addActionListener(new ActionListener() {
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
		
		
		
		
		
		JLabel lblNewLabel = new JLabel("Gestion d'un etudiant");
		lblNewLabel.setBounds(240, 134, 121, 14);
		contentPane.add(lblNewLabel);
		
		
		JLabel l_prenom = new JLabel("Prenom :");
		l_prenom.setBounds(20, 180, 100, 14);
		contentPane.add(l_prenom);
		
		JLabel l_nom = new JLabel("Nom :");
		l_nom.setBounds(20, 200, 46, 14);
		contentPane.add(l_nom);
		
		JLabel l_cin = new JLabel("CIN :");
		l_cin.setBounds(20, 220, 46, 14);
		contentPane.add(l_cin);
		
		JLabel lblNewLabel_4 = new JLabel("Date de naissance :");
		lblNewLabel_4.setBounds(20, 240, 120, 14);
		contentPane.add(lblNewLabel_4);
		
		JLabel l_email = new JLabel("E-mail :");
		l_email.setBounds(20, 260, 50, 14);
		contentPane.add(l_email);
		
		JLabel t_filièr = new JLabel("Filière :");
		t_filièr.setBounds(20, 280, 46, 14);
		contentPane.add(t_filièr);
		
		t_email = new JTextField();
		t_email.setBounds(140, 260, 140, 15);
		contentPane.add(t_email);
		t_email.setColumns(10);
		
		t_date_naissance = new JTextField();
		t_date_naissance.setBounds(140, 240, 140, 15);
		contentPane.add(t_date_naissance);
		t_date_naissance.setColumns(10);
		
		t_cin = new JTextField();
		t_cin.setBounds(140, 220, 140, 15);
		contentPane.add(t_cin);
		t_cin.setColumns(10);
		
		t_nom = new JTextField();
		t_nom.setBounds(140, 200, 140, 15);
		contentPane.add(t_nom);
		t_nom.setColumns(10);
		
		t_prenom = new JTextField();
		t_prenom.setBounds(140, 180, 140, 15);
		contentPane.add(t_prenom);
		t_prenom.setColumns(10);
		
		filiere = new JComboBox();
		filiere.setBounds(140, 280, 140, 15);
		contentPane.add(filiere);

		
		JButton b_ajouter = new JButton("Ajouter");
		b_ajouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			 String prenom = t_prenom.getText().toString();
			 String nom = t_nom.getText().toString();
			 String cin = t_cin.getText().toString();
			 String date_naissance = t_date_naissance.getText().toString();
			 String email = t_email.getText().toString();
			 String filiere1 = filiere.getSelectedItem().toString();
				
			 String sql="Insert Into etudiants (prenom,nom,cin,date_naissance,email,filiere) values(?,?,?,?,?,?)";
			 try {
				 if(!t_prenom.getText().equals("") && !t_nom.getText().equals("") && !t_cin.getText().equals("") && !t_date_naissance.getText().equals("") && !t_email.getText().equals("")) {
				prepared = conn.prepareStatement(sql);
				prepared.setString(1,prenom);
				prepared.setString(2,nom);
				prepared.setString(3,cin);
				prepared.setString(4,date_naissance);
				prepared.setString(5,email);
				prepared.setString(6,filiere1);
				prepared.execute();
				
				JOptionPane.showMessageDialog(null,"L'etudiant est ajouteé !");
				t_prenom.setText("");
				t_nom.setText("");
				t_cin.setText("");
				t_date_naissance.setText("");
				t_email.setText("");
				tableModel.setRowCount(0);
				updateTable();
				}else {
				JOptionPane.showMessageDialog(null,"Rempler les champs !");
				}
				 
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			 
		}
		});
		b_ajouter.setBounds(20, 320, 94, 23);
		b_ajouter.setForeground(Color.WHITE);
		b_ajouter.setBackground(new Color(0, 128, 192));
		contentPane.add(b_ajouter);
		
		JButton b_modifie = new JButton("Modifier");
		b_modifie.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			int ligne = table.getSelectedRow();
			
			if(ligne == -1) {
				JOptionPane.showMessageDialog(null,"Selectioner un etudiant !");
			
			}else {
				String id = table.getModel().getValueAt(ligne,0).toString();
				String sql="Update etudiants set prenom = ? , nom = ? , cin = ? , date_naissance = ? , email = ? , filiere = ? where id_etudiant='"+id+"'";
				
				try {
					prepared = conn.prepareStatement(sql);
					
					prepared.setString(1, t_prenom.getText().toString());
					prepared.setString(2, t_nom.getText().toString());
					prepared.setString(3, t_cin.getText().toString());
					prepared.setString(4, t_date_naissance.getText().toString());
					prepared.setString(5, t_email.getText().toString());
					prepared.setString(6, filiere.getSelectedItem().toString());
					
					prepared.execute();
					
					JOptionPane.showMessageDialog(null,"les données du l'etudiant ont modifiée !");
					t_prenom.setText("");
					t_nom.setText("");
					t_cin.setText("");
					t_date_naissance.setText("");
					t_email.setText("");
					tableModel.setRowCount(0);
					updateTable();
					
				} catch (SQLException e1) {
					e1.printStackTrace();
			    }
				
				}
		}	
		});
		b_modifie.setBounds(117, 320, 80, 23);
		b_modifie.setForeground(Color.WHITE);
		b_modifie.setBackground(new Color(0, 128, 192));
		contentPane.add(b_modifie);
		
		JButton b_supprimer = new JButton("Supprimer");
		b_supprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane a = new JOptionPane();
				int b = a.showConfirmDialog(null, "Voullez vous vraiment supprimer l'etudiant ", "Supprimer un etudiant", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			
				if(b == JOptionPane.OK_OPTION) {
				
					
				int ligne = table.getSelectedRow();
				if(ligne == -1) {
					JOptionPane.showMessageDialog(null,"Selectioner un etudiant !");
				
				}else {
				String id = table.getModel().getValueAt(ligne,0).toString();
				
				String sql="delete from etudiants where id_etudiant='"+id+"'";
				
				try {
					prepared = conn.prepareStatement(sql);
					prepared.execute();
					
					JOptionPane.showMessageDialog(null,"les données du l'etudiant ont supprimée !");
					t_prenom.setText("");
					t_nom.setText("");
					t_cin.setText("");
					t_date_naissance.setText("");
					t_email.setText("");
					tableModel.setRowCount(0);
					updateTable();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				}
			}
			}
		});
		b_supprimer.setBounds(200, 320, 94, 23);
		b_supprimer.setForeground(Color.WHITE);
		b_supprimer.setBackground(new Color(0, 128, 192));
		contentPane.add(b_supprimer);
	
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(300, 180, 300, 180);
		contentPane.add(scrollPane);
		
		tableModel = new DefaultTableModel(columnNames,0);
		table = new JTable(tableModel);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
			int ligne = table.getSelectedRow();
			String id = table.getModel().getValueAt(ligne,0).toString();
			String sql="select * from etudiants where id_etudiant='"+id+"'";
			try {
				prepared = conn.prepareStatement(sql);
				resultat = prepared.executeQuery();
				
				if(resultat.next()) {
				t_prenom.setText(resultat.getString("prenom"));
				t_nom.setText(resultat.getString("nom"));
				t_cin.setText(resultat.getString("cin"));
				t_date_naissance.setText(resultat.getString("date_naissance"));
				t_email.setText(resultat.getString("email"));
				filiere.setSelectedItem(resultat.getString("filiere"));
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
				
			}
		});
		scrollPane.setViewportView(table);
		

		//Bachground :--------------------------------------------------------------------------------------------------
		JLabel lblNewLabel_6 = new JLabel("");
		lblNewLabel_6.setIcon(new ImageIcon("C:\\Users\\moham\\Desktop\\Eclipse\\GestionEcoleIcons\\BACK1.png"));
		lblNewLabel_6.setBounds(0, 0, 640, 480);
		contentPane.add(lblNewLabel_6);
		
		updateTable();
		updateFiliere();
	}
	
public JPanel  afficherEtd() {
		
		JPanel contenu =new JPanel();
        contenu.setBounds(0, 0, 640, 480);
        contenu.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("Les Etudiants");
		lblNewLabel.setBounds(230, 134, 150, 14);
		contenu.add(lblNewLabel);
        
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(100, 180, 440, 200);
		contenu.add(scrollPane);
		

		tableModel = new DefaultTableModel(columnNames,0);
		table = new JTable(tableModel);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
			int ligne = table.getSelectedRow();
			String id = table.getModel().getValueAt(ligne,0).toString();
			String sql="select * from etudiants where id_etudiant='"+id+"'";
			try {
				prepared = conn.prepareStatement(sql);
				resultat = prepared.executeQuery();
				
				if(resultat.next()) {
				t_prenom.setText(resultat.getString("prenom"));
				t_nom.setText(resultat.getString("nom"));
				t_cin.setText(resultat.getString("cin"));
				t_date_naissance.setText(resultat.getString("date_naissance"));
				t_email.setText(resultat.getString("email"));
				filiere.setSelectedItem(resultat.getString("filiere"));
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
				
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
		String sql="select * from etudiants";
		
		try {
			
			prepared = conn.prepareStatement(sql);
			resultat = prepared.executeQuery();
			while(resultat.next()) {
				String id_etudiant= resultat.getString("id_etudiant");
				String prenom= resultat.getString("prenom");
				String nom= resultat.getString("nom");
				String cin= resultat.getString("cin");
				String date_naissance= resultat.getString("date_naissance");
				String email= resultat.getString("email");
				String filiere= resultat.getString("filiere");
				tableModel.addRow(new Object[]{id_etudiant,prenom,nom,cin,date_naissance,email,filiere});
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
	}
	
	public void updateFiliere() {
		String sql="select * from filieres";
		try {
			prepared = conn.prepareStatement(sql);
			resultat = prepared.executeQuery();
			while(resultat.next()) {
				String nom = resultat.getString("nom").toString();
				filiere.addItem(nom);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
