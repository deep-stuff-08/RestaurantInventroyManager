package com.gui.admin;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class UpdIndgredient {

	JComboBox<String> indname;
	JTextField newindname;
	JLabel lnewindname;
	JButton submit;
	JPanel main;
	
	public UpdIndgredient() throws SQLException {
		String[] s=Admin.getConnect().getAllIndgredients();
		indname=new JComboBox<String>(s);
		newindname=new JTextField(10);
		lnewindname=new JLabel("Enter new Indgredient name");
		submit=new JButton("Submit");
		submit.addActionListener(getSubAction());
		main=new JPanel(new FlowLayout());
		main.add(indname);
		main.add(lnewindname);
		main.add(newindname);
		main.add(submit);
	}

	private ActionListener getSubAction() {
		ActionListener ac=new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int a=JOptionPane.showConfirmDialog(null, "Are you sure you want to update this item?");
				if(a==JOptionPane.YES_OPTION) {
					try {
						Admin.getConnect().updateIndgredients(newindname.getText(),(String)indname.getSelectedItem());
					} catch (Exception e1) {
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
