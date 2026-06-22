package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.logic_view_login;

import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPasswordField;

public class view_login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public JTextField txt_user;
	public JPasswordField txt_psw;
	public JButton btn_ok;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					view_login frame = new view_login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public view_login() {
		setTitle("LOGIN");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 441, 208);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel user = new JLabel("USUARIO:");
		user.setBounds(40, 37, 71, 24);
		contentPane.add(user);
		
		JLabel lblNewLabel_1 = new JLabel("CLAVE:");
		lblNewLabel_1.setBounds(40, 86, 71, 24);
		contentPane.add(lblNewLabel_1);
		
		txt_user = new JTextField();
		txt_user.setBounds(100, 39, 96, 20);
		contentPane.add(txt_user);
		txt_user.setColumns(10);
		
		btn_ok = new JButton("INICIAR");
		btn_ok.setBounds(172, 139, 89, 23);
		contentPane.add(btn_ok);
		
		txt_psw = new JPasswordField();
		txt_psw.setBounds(100, 88, 96, 20);
		contentPane.add(txt_psw);
		
		new logic_view_login(this);

	}
}
