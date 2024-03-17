 package com.connector.cludodb;
import java.awt.Color;
		import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.*;

public class AddClientWindow extends JFrame {
	public AddClientWindow() {
		JButton submit = new JButton("Dodaj");
		submit.setFont(new Font("Arial", 0, 25));
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(Color.decode("#2c3e50"));
		mainPanel.setLayout(new BoxLayout(mainPanel, 1));
		final JTextField name = FrameUtilities.createTextPanel(mainPanel, "Imie: ");
		final JTextField surname = FrameUtilities.createTextPanel(mainPanel, "Nazwisko: ");
		final JTextField phone = FrameUtilities.createTextPanel(mainPanel, "Numer telefonu: ");
		final JTextField email = FrameUtilities.createTextPanel(mainPanel, "Email: ");
		   
		JPanel btnPanel = new JPanel();
		btnPanel.setBackground(Color.decode("#2c3e50"));
		btnPanel.add(submit);
		mainPanel.add(btnPanel);
		   
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (name.getText().isBlank() && surname.getText().isBlank()) { System.out.println("you bad"); }
					else { DatabaseConnection.addClient(name.getText(), surname.getText(), phone.getText(), email.getText()); }
					DataDisplay.refreshTable();
					Window.clientsList.setModel(new DefaultComboBoxModel(DatabaseConnection.getTableList()));
				} catch (SQLException e1) {
					e1.printStackTrace();
				} 
			}
		});
		add(mainPanel);
		setResizable(true);
		setSize(700, 250);
		setResizable(false);
		setLocationRelativeTo((Component)null);
		setVisible(true);
	}
}
