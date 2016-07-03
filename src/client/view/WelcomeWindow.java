package client.view;

import javax.swing.JFrame;

public class WelcomeWindow extends JFrame {
	public static final int HEIGHT = 860;
	public static final int WIDTH = 860;

	public WelcomeWindow() {
		super("Welcome!");
		this.setSize(WIDTH, HEIGHT);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
	}

}
