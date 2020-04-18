package com.gui.admin;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

public class AddIndgredients {

	JPanel main;
	JLabel lindname;
	JLabel lquantity;
	JTextField indname;
	JTextField quantity;
	JButton submit;
	public AddIndgredients() {
		main=new JPanel(new FlowLayout());
		lindname=new JLabel("Enter Indgredients Name");
		lquantity=new JLabel("Enter Quantity");
		indname=new JTextField(10);
		NumberFormat numf=NumberFormat.getIntegerInstance();
		NumberFormatter nf=new NumberFormatter(numf);
		nf.setValueClass(Integer.class);
		nf.setMinimum(0);
		nf.setMaximum(Integer.MAX_VALUE);
		nf.setCommitsOnValidEdit(true);
		nf.setAllowsInvalid(false);
		quantity=new JFormattedTextField(nf);
		quantity.setText("0");
		quantity.setColumns(2);
		submit=new JButton("Submit");
		submit.addActionListener(addSubAction());
		main.add(lindname);
		main.add(indname);
		main.add(lquantity);
		main.add(quantity);
		main.add(submit);
	}
	
	private ActionListener addSubAction() {
		ActionListener ac=new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int a=JOptionPane.showConfirmDialog(null, "Are you sure you want to add this indgredient?");
				if(a==JOptionPane.YES_OPTION) {
					String qua=quantity.getText().replaceAll(",", "");
					if (indname.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Error: Indgredient name cannot be empty", "Error Occurred", JOptionPane.ERROR_MESSAGE);
					}
					else if (Integer.parseInt(qua)==0) {
						JOptionPane.showMessageDialog(null, "Error: Quantity cannot be empty", "Error Occurred", JOptionPane.ERROR_MESSAGE);;
					}
					else {
						String ind=indname.getText();
						try {
							Admin.getConnect().addIndgredient(ind, qua);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
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
