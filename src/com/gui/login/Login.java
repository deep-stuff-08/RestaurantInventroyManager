package com.gui.login;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.database.UserConnector;
import com.gui.Order;
import com.gui.admin.Admin;

public class Login {
	JFrame frame;
	JLabel lblUsername;
	JLabel lblPassword;
	JTextField txtUsername;
	JPasswordField txtPassword;
	JButton btnSubmit;
	
	public Login() {
		frame=new JFrame("DBMS Mini Project");
		frame.setBounds(100, 100, 200, 130);
		frame.setLayout(new FlowLayout());
		
		lblUsername = new JLabel("Username");
		lblPassword = new JLabel("Password");
		txtUsername = new JTextField(10);
		txtPassword = new JPasswordField(10);
		btnSubmit = new JButton("Login");
		
		btnSubmit.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					UserConnector con = new UserConnector();
					int status = con.checkUser(txtUsername.getText(), new String(txtPassword.getPassword()));
					if(status == UserConnector.USER_NOT_FOUND) {
						JOptionPane.showMessageDialog(null, "User Not Found");
					} else if(status == UserConnector.PASSWORD_INCORRECT) {
						JOptionPane.showMessageDialog(null, "Password Incorrect");
					} else if(status == UserConnector.USER_ADMIN) {
						new Admin();
						frame.dispose();
					} else if(status == UserConnector.USER_NORMAL) {
						new Order();
						frame.dispose();
					} else {
						JOptionPane.showMessageDialog(null, "Unknown Error");
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		frame.add(lblUsername);
		frame.add(txtUsername);
		frame.add(lblPassword);
		frame.add(txtPassword);
		frame.add(btnSubmit);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
