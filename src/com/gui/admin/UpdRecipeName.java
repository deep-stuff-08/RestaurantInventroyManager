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

public class UpdRecipeName {

	JComboBox<String> recname;
	JLabel lnewrecname;
	JTextField newrecname;
	JButton submit;
	JPanel main;
	
	public UpdRecipeName() throws SQLException {
		String[] s=Admin.getConnect().getAllRecipeIdsAndNames();
		recname=new JComboBox<String>(s);
		lnewrecname=new JLabel("Enter new Recipe name");
		newrecname=new JTextField(10);
		submit=new JButton("Submit");
		submit.addActionListener(getSubAction());
		main=new JPanel(new FlowLayout());
		main.add(recname);
		main.add(lnewrecname);
		main.add(newrecname);
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
						s=s.substring(6);
						Admin.getConnect().updateRecipeName(newrecname.getText(),s);
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
