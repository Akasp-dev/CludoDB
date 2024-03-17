package com.connector.cludodb;
 
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.table.*;
public class DataDisplay extends Thread {
	
	static JTable table;
	static DefaultTableModel tableModel;
	public static JTable createDataTable(DatabaseConnection con, String tableName, String[] tableLabels) throws SQLException {
		
		        if(tableName.equals("allClients")) {
		        	tableModel = new DefaultTableModel(DatabaseConnection.getAllClients(),tableLabels);
		        	table = new JTable(tableModel);
		        }else {
		        	tableModel = new DefaultTableModel(DatabaseConnection.showTableData(tableName),tableLabels);
		        	table = new JTable(tableModel) {
						public boolean isCellEditable(int row, int column) {
						if(column != 5) return false;
						else return true;
					}
				};
		     }
				
		String[] wybory = {"Zakończone", "Nie ukończone"};
		JComboBox comboBox = new JComboBox(wybory);
		if(!Window.clientsList.getSelectedItem().toString().equals("Dane_Kontaktowe") || tableName.equals("allClients")) {
			new ButtonColumn(table, 5);
		}
		
			
		table.setRowHeight(30);
		table.setBackground(Color.decode("#34495e"));
		table.setFont(new Font("Arial", 0, 24));
		table.setForeground(Color.WHITE);
		table.getTableHeader().setFont(new Font("Arial", 0, 20));
		    
		return table;
	}
	public static void refreshTable() throws SQLException {
		if (Window.clientsList.getSelectedItem().toString().equals("Dane_Kontaktowe")) {
			Window.table = createDataTable(Window.con, Window.clientsList.getSelectedItem().toString(), Window.nazwy_klientów);
			Window.scrollPane.getViewport().setView(Window.table);
			Styling.setStyle();
		} else {
			Window.table = createDataTable(Window.con, Window.clientsList.getSelectedItem().toString(), Window.nazwy);
			Window.scrollPane.getViewport().setView(Window.table);
			Styling.setStyle();
		} 
	}
}
class ButtonColumn extends AbstractCellEditor
implements TableCellRenderer, TableCellEditor, ActionListener
{
	JTable table;
	JButton renderButton;
	JButton editButton;
	String text;
	
	public ButtonColumn(JTable table, int column)
	{
	    super();
	    this.table = table;
	    renderButton = new JButton("Wyświetl");
	
	    editButton = new JButton("Wyświetl");
	    editButton.setFocusPainted( false );
	    editButton.addActionListener( this );
	
	    TableColumnModel columnModel = table.getColumnModel();
	    columnModel.getColumn(column).setCellRenderer( this );
	    columnModel.getColumn(column).setCellEditor( this );
	}
	
	public Component getTableCellRendererComponent(
	    JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	{
	   /* if (hasFocus)
	    {
	        renderButton.setForeground(table.getForeground());
	        renderButton.setBackground(UIManager.getColor("Button.background"));
	    }
	    else if (isSelected)
	    {
	        renderButton.setForeground(table.getSelectionForeground());
	         renderButton.setBackground(table.getSelectionBackground());
	    }
	    else
	    {
	        renderButton.setForeground(table.getForeground());
	        renderButton.setBackground(UIManager.getColor("Button.background"));
	    }*/
	    renderButton.setText("Wyświetl");
	    renderButton.setForeground(Color.BLACK);
	    return renderButton;
	}
	
	public Component getTableCellEditorComponent(
	    JTable table, Object value, boolean isSelected, int row, int column)
	{
	    return editButton;
	}
	
	public Object getCellEditorValue()
	{
	    return text;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFrame frame = new JFrame();
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(Color.decode("#2c3e50"));
		
		String selectedItem = Window.clientsList.getSelectedItem().toString();
		
		JTextArea textField = new JTextArea(17, 35);
		textField.setFont(new Font("Arial", Font.PLAIN, 25));
		textField.setForeground(Color.WHITE);
		textField.setBackground(Color.decode("#34495e"));
		textField.setCaretColor(Color.WHITE);
		try {
			textField.append(Window.con.getDesc(selectedItem, Window.con.returnRowId(selectedItem, table.getSelectedRow())));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		JButton editBtn = new JButton("Zapisz");
		editBtn.setFont(new Font("Arial", 0, 28));
		editBtn.setBackground(Color.decode("#34495e"));
		editBtn.setForeground(Color.WHITE);
		editBtn.setFont(new Font("Arial", Font.PLAIN, 25));
		editBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Window.con.editDesc(selectedItem, textField.getText(), Window.con.returnRowId(selectedItem, table.getSelectedRow()));
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		mainPanel.add(textField);
		mainPanel.add(editBtn);
		
		frame.add(mainPanel);
		frame.setSize(800,600);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
	}
}