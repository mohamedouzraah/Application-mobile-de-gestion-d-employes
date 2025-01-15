package Pack;

import javax.swing.*;
import java.sql.*;

public class connexionMySQL {
	

	
 public static Connection connexionDb() {
		Connection conn =null ;
		try {
		 
		 Class.forName("com.mysql.jdbc.Driver");
		 conn = DriverManager.getConnection("jdbc:mysql://localhost/gestion_ecole","root","");
		
	 }catch(Exception e) {
		 
		 e.getMessage();
		 
	 }
	return conn;
	
 }
}
 