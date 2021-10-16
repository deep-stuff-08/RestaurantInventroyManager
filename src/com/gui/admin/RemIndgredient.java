package com.gui.admin;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class RemIndgredient {

	JComboBox<String> indname;
	JButton submit;
	JPanel main;
	
	public RemIndgredient() throws SQLException {
		String[] s=Admin.getConnect().getAllIndgredients();
		indname=new JComboBox<String>(s);
		submit=new JButton("Submit");
		submit.addActionListener(getSubAction());
		main=new JPanel(new FlowLayout());
		main.add(indname);
		main.add(submit);
	}

	private ActionListener getSubAction() {
		ActionListener ac=new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int a=JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this item?");
				if(a==JOptionPane.YES_OPTION) {
					try {
						Admin.getConnect().removeIndgredient((String)indname.getSelectedItem());
						String[] s = Admin.getConnect().getAllIndgredients();
						indname.removeAllItems();
						for(String str : s) {
							indname.addItem(str);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		};
		return ac;
	}
	
	public JPanel getMain() {
		return main;
	}
	
}
