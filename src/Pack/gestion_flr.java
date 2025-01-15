package Pack;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.AbstractListModel;
import javax.swing.DefaultComboBoxModel;

public class gestion_flr extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField t_nom;
	private JComboBox c_type;
	DefaultTableModel tableModel;
	private JTable table;
	String[] columnNames= new String[]{"Id Filiére","Nom de filiére","Type de Filiére"};
	
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
					gestion_flr frame = new gestion_flr();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	
	public gestion_flr() {
		
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
		
		i_mtr= new JMenuItem("Les filiéres");
		i_g_mtr= new JMenuItem("Gestion des filieres");
		i_quitter= new JMenuItem("Quitter");
		i_quitter.addActionListener(new ActionListener() {
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
		
		
		
		
		
		JLabel lblNewLabel = new JLabel("Gestion des filiéres");
		lblNewLabel.setBounds(230, 134, 150, 14);
		contentPane.add(lblNewLabel);
		JLabel l_nom = new JLabel("Nom de filiére :");
		l_nom.setBounds(17, 205, 100, 14);
		contentPane.add(l_nom);
		JLabel l_type = new JLabel("Type de filiére :");
		l_type.setBounds(17, 234, 120, 14);
		contentPane.add(l_type);
		
		t_nom = new JTextField();
		t_nom.setFont(new Font("Tahoma", Font.PLAIN, 9));
		t_nom.setBounds(130, 205, 150, 15);
		contentPane.add(t_nom);
		c_type = new JComboBox();
		c_type.setModel(new DefaultComboBoxModel(new String[] {"Sélectionnez","Licence fondamentale","Licence Profetionnel"}));
		c_type.setBounds(130, 234, 150, 22);
		contentPane.add(c_type);
		
		
		JButton b_ajouter = new JButton("Ajouter");
		b_ajouter.setBackground(new Color(0, 128, 192));
		b_ajouter.setForeground(new Color(255, 255, 255));
		b_ajouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String sql="insert into filieres(nom , type) values(?,?)";
				try {
					if(!t_nom.getText().equals("") && !c_type.getSelectedItem().equals("Sélectionnez")) {
					prepared = conn.prepareStatement(sql);
					prepared.setString(1,t_nom.getText().toString());
					prepared.setString(2,c_type.getSelectedItem().toString());
					prepared.execute();
					
					JOptionPane.showMessageDialog(null, "La filiere est ajouteé !");
					t_nom.setText("");
					c_type.setSelectedItem("Sélectionnez");
					
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
		
		b_ajouter.setBounds(158, 287, 100, 23);
		contentPane.add(b_ajouter);
		
		
		
		JButton b_supprimer = new JButton("Supprimer");
		b_supprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane a = new JOptionPane();
				int b = a.showConfirmDialog(null, "Voullez vous vraiment supprimer la filiére ", "Supprimer une filiére", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			
				if(b == JOptionPane.OK_OPTION) {
				
				
				String sql="delete from filieres where nom=? and type=?";
				try {
					if(!t_nom.getText().equals("") && !c_type.getSelectedItem().equals("Sélectionnez")) {
					prepared= conn.prepareStatement(sql);
					prepared.setString(1, t_nom.getText().toString());
					prepared.setString(2, c_type.getSelectedItem().toString());
					prepared.execute();
					
					JOptionPane.showMessageDialog(null, "la filiére a été supprimée !");
					t_nom.setText("");
					c_type.setSelectedItem("Sélectionnez");
					
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
				String nom1 = table.getModel().getValueAt(ligne,1).toString();
				String type1 = table.getModel().getValueAt(ligne,2).toString();
				
				t_nom.setText(nom1);
				c_type.setSelectedItem(type1);
			}
		});
		scrollPane.setViewportView(table);
		
		//Bachground :--------------------------------------------------------------------------------------------------
		JLabel lblNewLabel_6 = new JLabel("");
		lblNewLabel_6.setIcon(new ImageIcon("C:\\Users\\moham\\Desktop\\Eclipse\\GestionEcoleIcons\\BACK1.png"));
		lblNewLabel_6.setBounds(0, 0, 640, 480);
		contentPane.add(lblNewLabel_6);
				
		updateTable();
	}

public void updateTable(){
	String sql="select * from filieres";
	
	try {
		
		prepared = conn.prepareStatement(sql);
		resultat = prepared.executeQuery();
		while(resultat.next()) {
			String id= resultat.getString("id_filiere");
			String nom= resultat.getString("nom");
			String type= resultat.getString("type");
			tableModel.addRow(new Object[]{id,nom ,type});
			}
		
	    } catch (SQLException e) {
		e.printStackTrace();
		}    
 }

}
