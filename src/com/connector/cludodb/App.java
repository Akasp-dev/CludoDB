package com.connector.cludodb;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.SQLException;

public class App {
	
	static int screenWidth;
	static int screenHeight;
	  
	public static void main(String[] args) throws SQLException {
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			screenWidth = (int)screenSize.getWidth();
			screenHeight = (int)screenSize.getHeight();
			DatabaseConnection connection = new DatabaseConnection("jdbc:mysql://s177.cyber-folks.pl:3306/i7328_clients", "i7328", "IncludO2020*");
			Window dataWindow = new Window("CludoDB", screenWidth / 2 + 300, screenHeight / 2 + 200, connection);
			dataWindow.start();
		}
	}
