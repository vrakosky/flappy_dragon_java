package vragon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Menu extends JFrame implements ActionListener{
	
	/**
	 * 
	 */
		private static final long serialVersionUID = 1L;
		
		//************1 - ATTRIBUTS du Joueur*****************
		public JFrame frame;
		public JTextArea playerNameTyped;
		public JLabel instruction, title, name;
		public JButton play;
		public JPanel p;
	
		//************2 - CONSTRUCTEUR MENU PLAYER************
		public void menu(){
			
		//************3 - Fenêtre MENU************************
			setLayout(new FlowLayout(FlowLayout.CENTER,30,30));
			setTitle("VRAGON : The Game");
			
		//************4 - GAME title************************
			title = new JLabel("Welcome to the Vragon Game !");
			add(title);
			
		//************5 - GAME title************************
			name = new JLabel("******************* Please enter your name *******************");
			add(name);
			
		//************6 - NOM du Joueur************************
			playerNameTyped = new JTextArea(10,25);
			add(playerNameTyped);
			
		//************7 - INSTRUCTIONS************************
			instruction = new JLabel("Instruction : To move the Fireball, press SPACE BAR or MOUSE CLICK ");
			add(instruction,BorderLayout.NORTH);
			
		//************8 - BOUTON Play*************************
			play = new JButton("Jouez !");
			play.setBackground(Color.orange);;
			add(play);
			play.addActionListener(this);

			setSize(500,450);
			setLocationRelativeTo(null);
			setVisible(true);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			
		//************9 - Listener : Click**************
		
			//close menu when button clicked
			play.addActionListener(new ActionListener(){
	
				@Override
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					Vragon.playerName = playerNameTyped.getText();
				}
			});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}
}

//Vragon v11.0
//created by CANDAPPANE Vincent v2017
