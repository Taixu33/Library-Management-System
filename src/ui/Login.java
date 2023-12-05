package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import model.Admin;
import model.User;
import service.UserService;

/**
 * 	Login
 * 	The database has set the indices for keys and accounts to be of type UNIQUE.
 * @author Administrator
 *
 */
public class Login {
	private JFrame jf = new JFrame();
	private Container c = jf.getContentPane();
	private JPanel topPanel = new JPanel(); // Top panel
	private JLabel userLabel = new JLabel("Username:"); // Username label
	private JLabel passwordLabel = new JLabel("Password:");// Password label
	private JLabel titleLabel = new JLabel("Welcome to the Library Management System");
	
	private ImageIcon image = new ImageIcon("src//images//logo.jpg"); // Path relative to the class file
	private JLabel logo = new JLabel();//logo
	
	private JTextField userText = new JTextField();// Text field for username input
	private JPasswordField passwordText = new JPasswordField();// Text field for password input
	private JButton loginBtn = new JButton("Login");//Login button
	private JButton registerBtn = new JButton("Register");//Register button
	
	private ButtonGroup btnGroup = new ButtonGroup();
	private JRadioButton jr1 = new JRadioButton("Student",true);
	private JRadioButton jr2 = new JRadioButton("Administrator",false);
	
	private Font userAndPasswdFont = new Font("Arial",Font.BOLD,15);
	
	private String userName = "";
	private String userPassword = "";
	
	
	public Login() {
		init();
		
		jf.setVisible(true);
	}
	
	private void init(){
		c.setLayout(null);
		jf.setSize(400,300);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setLocationRelativeTo(null);
		jf.setResizable(false);
		
		/*
		 * Top of the Login Window
		 */
		topPanel.setLayout(null);
		topPanel.setBackground(Color.GRAY);
		topPanel.setSize(400,100);
		
		// Set the title
		titleLabel.setFont(new Font("Arial",Font.BOLD,15));//Font
		titleLabel.setForeground(Color.BLACK);//Font color
		titleLabel.setBounds(40,0,400,100);
		topPanel.add(titleLabel);
		
		// Set the size and position of the image, and add it to the panel
		image.setImage(image.getImage().getScaledInstance(55, 55, Image.SCALE_AREA_AVERAGING));
		logo.setIcon(image);
		logo.setBounds(10,20,55,55);
		topPanel.add(logo);
		
		
		
		/*
		 * Login Window Text Fields
		 */
		userLabel.setBounds(60,20,200,200);
		userLabel.setFont(userAndPasswdFont);
		passwordLabel.setBounds(60,60,200,200);
		passwordLabel.setFont(userAndPasswdFont);
		userText.setColumns(9);
		userText.setBounds(140,110,150,20);
		passwordText.setColumns(11);
		passwordText.setBounds(140, 150, 150, 20);
		
		//Login Button
		loginBtn.setBounds(190,220,100,30);
		registerBtn.setBounds(70,220,100,30);
		
		
		
		/*
		 * Regular User and Administrator
		 */
		jr1.setBounds(100,180,100,15);
		jr2.setBounds(200,180,130,15);
		btnGroup.add(jr1);
		btnGroup.add(jr2);
		
		
		c.add(topPanel);
		c.add(userLabel);
		c.add(passwordLabel);
		c.add(userText);
		c.add(passwordText);
		c.add(registerBtn);
		c.add(loginBtn);
		c.add(jr1);
		c.add(jr2);
		
		createBtnListener();
	}
	
	private void createBtnListener() {
		/**
		 * Listener event for the register button
		 */
		registerBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new RegisterDialog(jf, "Welcome to Register");
			}
		});
		
		/**
		 * Listener event for the register button
		 */
		loginBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Retrieve the username and password entered by the user
				userName = userText.getText();
				userPassword = String.valueOf(passwordText.getPassword()); // String.valueOf() -> convert character array to string
		
				UserService userService = new UserService();
		
				if (jr1.isSelected()) {
					// Ordinary user login
					User user = userService.login(userName, userPassword);
					if (user != null) {
						// Switch to ordinary user interface
						System.out.println("Login successful!");
						jf.dispose(); // Destroy the login window
						new UserView(user);
					} else {
						// Prompt for incorrect account or password
						System.out.println("Incorrect username or password!");
						createDialog(jf);
					}
				} else {
					// Administrator login
					Admin admin = userService.adminLogin(userName, userPassword);
					if (admin != null) {
						// Switch to administrator page
						System.out.println("Administrator login successful!");
						jf.dispose(); // Destroy the login window
						new AdminView(admin);
					} else {
						createDialog(jf);
					}
				}
			}
		});		
		
	}
	
	
	private void  createDialog(JFrame frame) {
		JDialog msgDialog = new JDialog(frame, "Login Failed!", true);
		JLabel msgLabel = new JLabel();
		Container con = msgDialog.getContentPane();
		
		msgDialog.setSize(250,150);
		msgDialog.setLocationRelativeTo(null);
		
		msgLabel.setText("Incorrect username or password!");
		msgLabel.setFont(new Font("Verdana", Font.PLAIN, 15));
		
		con.add(msgLabel,BorderLayout.WEST);
		
		msgDialog.setVisible(true); 
	}
}
