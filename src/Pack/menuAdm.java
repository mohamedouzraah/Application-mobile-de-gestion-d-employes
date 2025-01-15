package Pack;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

public class menuAdm extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					menuAdm frame = new menuAdm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public menuAdm() {
		//Frame :---------------------------------------------------------------------------------------------
		setTitle("Gestion d'un école");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Buttons : -------------------------------------------------------------------------------------------
		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gestion_utl Obj = new gestion_utl();
				Obj.setVisible(true);
				Obj.setLocationRelativeTo(null);
			}
		});
		btnNewButton.setIcon(new ImageIcon("C:\\Users\\moham\\Desktop\\Eclipse\\GestionEcoleIcons\\G_UTL.png"));
		btnNewButton.setBounds(152, 120, 100, 100);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setIcon(new ImageIcon("C:\\Users\\moham\\Desktop\\Eclipse\\GestionEcoleIcons\\G_FLR.png"));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gestion_flr Obj = new gestion_flr();
				Obj.setVisible(true);
				Obj.setLocationRelativeTo(null);
				
			}
		});
		btnNewButton_1.setBounds(152, 253, 100, 100);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gestion_matiere Obj = new gestion_matiere();
			    Obj.setVisible(true);
			    Obj.setLocationRelativeTo(null);
			}
		});
		btnNewButton_2.setIcon(new ImageIcon("C:\\Users\\moham\\Desktop\\Eclipse\\GestionEcoleIcons\\G_MTR.png"));
		btnNewButton_2.setBounds(388, 253, 100, 100);
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gestion_etd Obj = new gestion_etd();
				Obj.setVisible(true);
				Obj.setLocationRelativeTo(null);
			}
		});
		btnNewButton_3.setIcon(new ImageIcon("C:\\Users\\moham\\Desktop\\Eclipse\\GestionEcoleIcons\\G_ETD.png"));
		btnNewButton_3.setBounds(388, 120, 100, 100);
		contentPane.add(btnNewButton_3);
		
		//Titres des buttons :-------------------------------------------------------------------------------------------
		JLabel lblNewLabel = new JLabel("Gestion des utilisateurs");
		lblNewLabel.setBounds(135, 220, 140, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Gestion des filières");
		lblNewLabel_1.setBounds(148, 353, 120, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Gestion des matières");
		lblNewLabel_2.setBounds(384, 353, 140, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Gestion des etudiants");
		lblNewLabel_3.setBounds(376, 220, 140, 14);
		contentPane.add(lblNewLabel_3);
		
		//Bachground :--------------------------------------------------------------------------------------------------
		JLabel lblNewLabel_6 = new JLabel("");
		lblNewLabel_6.setIcon(new ImageIcon("C:\\Users\\moham\\Desktop\\Eclipse\\GestionEcoleIcons\\BACK.png"));
		lblNewLabel_6.setBounds(0, 0, 640, 480);
		contentPane.add(lblNewLabel_6);
	}
}
