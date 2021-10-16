package com.gui.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;

import javax.swing.JPanel;
import javax.swing.JTable;

public class ViewIndgredients {

	JPanel main;
	JTable tblMain;

	public ViewIndgredients() throws SQLException {
		main=new JPanel(new BorderLayout(0, 0));
		main.setBackground(Color.white);
		Object[][] s=Admin.getConnect().getAllTblQuantity();
		tblMain = new JTable(s, new String[] {"Name", "Quantity"});
		tblMain.setSize(800, 600);
		tblMain.setRowHeight(25);
		tblMain.setFont(new Font(tblMain.getFont().getName(), Font.PLAIN, 17));
		tblMain.getTableHeader().setFont(new Font(tblMain.getFont().getName(), Font.BOLD, 17));
		main.add(tblMain.getTableHeader(), BorderLayout.PAGE_START);
		main.add(tblMain, BorderLayout.CENTER);
	}
		
	public JPanel getMain() {
		return main;
	}
}
