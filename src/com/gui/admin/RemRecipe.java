package com.gui.admin;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class RemRecipe {

	JComboBox<String> recname;
	JButton submit;
	JPanel main;
	
	public RemRecipe() throws SQLException {
		String[] s=Admin.getConnect().getAllRecipeIdsAndNames();
		recname=new JComboBox<String>(s);
		submit=new JButton("Submit");
		submit.addActionListener(getSubAction());
		main=new JPanel(new FlowLayout());
		main.add(recname);
		main.add(submit);
	}

	private ActionListener getSubAction() {
		ActionListener ac=new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int a=JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this item?");
				if(a==JOptionPane.YES_OPTION) {
					try {
						String s=(String)recname.getSelectedItem();
						s=s.substring(0, 5);
						Admin.getConnect().removeRecipe(s);
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
