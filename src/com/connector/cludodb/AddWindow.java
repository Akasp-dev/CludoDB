package com.connector.cludodb;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddWindow extends JFrame {
	public AddWindow() {
		createWindow();
	}
	
	 
	private void createWindow() {
		String[] choices = { "Tak", "Nie" };
		String[] stany = {"Zakończone", "Niezakończone"};
		 
		JPanel mainPanel = new JPanel();
		JPanel btnPanel = new JPanel();
		mainPanel.setBackground(Color.decode("#2c3e50"));
		btnPanel.setBackground(Color.decode("#2c3e50"));
		mainPanel.setLayout(new BoxLayout(mainPanel, 1));
		final JTextField name = FrameUtilities.createTextPanel(mainPanel, "Nazwa produktu: ");
		final JTextField key = FrameUtilities.createTextPanel(mainPanel, "Klucz produktu: ");
		final JComboBox own = FrameUtilities.createComboBoxPanel(mainPanel, "Wkład własny?: ", choices);
		final JTextField ownpr = FrameUtilities.createTextPanel(mainPanel, "Wysokość wkładu własnego: ");
		final JComboBox fund = FrameUtilities.createComboBoxPanel(mainPanel, "Dofinansowanie?: ", choices);
		final JTextField orderDate = FrameUtilities.createTextPanel(mainPanel, "Data zamówienia: ");
		final JComboBox stan = FrameUtilities.createComboBoxPanel(mainPanel, "Stan zamówienia", stany);
		 
		btnPanel.setPreferredSize(new Dimension(700, 150));
		btnPanel.setLayout(new BoxLayout(btnPanel, 0));
		JButton dodaj = new JButton("Dodaj");
		dodaj.setFont(new Font("Arial", 0, 23));
		dodaj.setBackground(Color.decode("#34495e"));
		dodaj.setForeground(Color.WHITE);
		btnPanel.add(dodaj);
		mainPanel.add(btnPanel);
		   
		dodaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					DatabaseConnection.addToDataBase(Window.clientsList.getSelectedItem().toString(), name.getText(), key.getText(), own.getSelectedItem().toString(), ownpr.getText(), fund.getSelectedItem().toString(), orderDate.getText());
					DataDisplay.refreshTable();
				} catch (SQLException e1) {
					e1.printStackTrace();
				} 
			}
		});
		   
		add(mainPanel);
		setSize(700, 350);
		setLocationRelativeTo((Component)null);
		setResizable(false);
		setVisible(true);
	}
}

