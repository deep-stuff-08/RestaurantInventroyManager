package com.gui.admin;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

public class OrderIndgredients {

	JPanel main;
	JComboBox<String> indname;
	JLabel lquantity;
	JTextField quantity;
	JButton submit;
	public OrderIndgredients() throws SQLException {
		main=new JPanel(new FlowLayout());
		String[] s=Admin.getConnect().getAllIndgredients();
		indname=new JComboBox<String>(s);
		lquantity=new JLabel("Enter Quantity");
		NumberFormat numf=NumberFormat.getIntegerInstance();
		NumberFormatter nf=new NumberFormatter(numf);
		nf.setValueClass(Integer.class);
		nf.setMinimum(0);
		nf.setMaximum(Integer.MAX_VALUE);
		nf.setCommitsOnValidEdit(true);
		nf.setAllowsInvalid(false);
		quantity=new JFormattedTextField(nf);
		quantity.setText("0");
		quantity.setColumns(5);
		submit=new JButton("Submit");
		submit.addActionListener(addSubAction());
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
					if(qua.isEmpty() || Integer.parseInt(qua) == 0) {
						return;
					}
					try {
						Admin.getConnect().updateQuantity((String)indname.getSelectedItem(), Integer.parseInt(qua));
					} catch (NumberFormatException | FileNotFoundException | SQLException e1) {
						e1.printStackTrace();
					}
					quantity.setText("0");
				}
			}
		};
		return ac;
	}
	
	public JPanel getMain() {
		return main;
	}
}
