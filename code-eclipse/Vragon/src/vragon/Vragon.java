package vragon;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Vragon extends JFrame implements ActionListener, MouseListener, KeyListener {
	
	//*************1 - VARIABLES*************

	//public static Vragon vragon;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final int WIDTH = 800, HEIGHT = 800;

	public Renderer renderer;

	public Rectangle dragon;

	public ArrayList<Rectangle> columns;

	public int ticks, yMotion, score, rotate;

	public boolean gameOver, started;
	
	public Color color;

	public Random rand;
	
	public String level = "0";
	
	public static String playerName = " ";
	
	//*************2 - ACCESSEUR ET MUTATEURS*************
		//2a - ACCESSEUR EN LECTURE
		public String getPlayerName(){
			return playerName;
		}
		//2b - ACCESSEUR EN LECTURE
		public void setPlayerName(String playerName){
			Vragon.playerName = playerName;
		}

	//*************3 - CONSTRUCTEUR VRAGON*************
	public Vragon() {
		
		//Icone du jeu
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("/eye5.png"));

			JFrame jframe = new JFrame();
			Timer timer = new Timer(20, this);
	
			renderer = new Renderer();
			rand = new Random();
	
			jframe.add(renderer);
			
			jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jframe.setSize(WIDTH, HEIGHT);
			jframe.addMouseListener(this);
			jframe.addKeyListener(this);
			jframe.setResizable(false);
			jframe.setVisible(true);
			jframe.setLocationRelativeTo(null);
			
			dragon = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 20);
			columns = new ArrayList<Rectangle>();
	
			addColumn(true);
			addColumn(true);
			addColumn(true);
			addColumn(true);
	
			timer.start();
	}

	//*************4 - AJOUT ENNEMIS & NIVEAU*************
	public void addColumn(boolean start) {
		int space = 300;
		int level1 = 15;
		int level2 = 50;
		int level3 = 100;
		//int level4 = 150;
		
		//Hauteur
		int height = 50 + rand.nextInt(300);

		//Niveau 1
		int width1 = level1;
		//Niveau 2
		int width2 = level2;
		//Niveau 3
		int width3 = level3;
		//Niveau 4
		//int width4 = level4;
		//Niveau Final
		int widthFinal = 1 + rand.nextInt(300);
			
		if (start) {
			level = "EASY";
			columns.add(new Rectangle(WIDTH + width1 + columns.size() * 300, HEIGHT - height - 120, width1, height));
			columns.add(new Rectangle(WIDTH + width1 + (columns.size() - 1) * 300, 0, width1, HEIGHT - height - space));
		}else if (score>=1 && score<=5){
			level = "COOL";
			columns.add(new Rectangle(columns.get(columns.size() - 1).x + 600, HEIGHT - height - 120, width2, height));
			columns.add(new Rectangle(columns.get(columns.size() - 1).x, 0, width2, HEIGHT - height - space));
		}else if (score>=5 && score<7){
			level = "HARD";
			columns.add(new Rectangle(columns.get(columns.size() - 1).x + 600, HEIGHT - height - 120, width3, height));
			columns.add(new Rectangle(columns.get(columns.size() - 1).x, 0, width3, HEIGHT - height - space));
		}else {
			level = "SPICY";
			columns.add(new Rectangle(columns.get(columns.size() - 1).x + 600, HEIGHT - height - 120, widthFinal, height));
			columns.add(new Rectangle(columns.get(columns.size() - 1).x, 0, widthFinal, HEIGHT - height - space));
		}
	}

	//*************5 - COLORER ENNEMIES EN MULTICOLOR*************
	public void paintColumn(Graphics g, Rectangle column) {
		//Color c = new Color(randColor, randColor, randColor, 255);
		//color = new Color(20, 55, 55, 255);
		int randColorR = rand.nextInt(255);
		int randColorG = rand.nextInt(255);
		int randColorB = rand.nextInt(255);
		color = new Color(randColorR, randColorG, randColorB, 255);
		g.setColor(color);
		g.fillRect(column.x, column.y, column.width, column.height);
	}

	//*************6 - FONCTION DRAGON SAUTE*************
	public void jump() {
		if (gameOver) {
			// Rejouer
			dragon = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 20);
			columns.clear();
			yMotion = 0;
			score = 0;

			addColumn(true);
			addColumn(true);
			addColumn(true);
			addColumn(true);

			gameOver = false;
		}

		if (!started) {
			started = true;
		} else if (!gameOver) {
			if (yMotion > 0) {
				yMotion = 0;
			}
			yMotion -= 10;
		}
	}
	
	//*************7 - MOUVEMENT & SCORE*************
	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		ticks++;

		if (started) {
			int speed = 10;

			for (int i = 0; i < columns.size(); i++) {
				Rectangle column = columns.get(i);
				column.x -= speed;
			}

			if (ticks % 2 == 0 && yMotion < 15) {
				yMotion += 2;
			}

			for (int i = 0; i < columns.size(); i++) {
				Rectangle column = columns.get(i);
				if (column.x + column.width < 0) {

					columns.remove(column);

					if (column.y == 0) {
						addColumn(false);
					}
				}
			}

			dragon.y += yMotion;

			for (Rectangle column : columns) {

				// SCORE
				if (column.y == 0 && dragon.x + dragon.width / 2 > column.x + column.width / 2 - 5
						&& dragon.x + dragon.width / 2 < column.x + column.width / 2 + 5) {
					score++;
				}

				if (column.intersects(dragon)) {
					gameOver = true;
					
					if (dragon.x <= column.x) {
						dragon.x = column.x - dragon.width;

					} else {
						if (column.y != 0) {
							dragon.y = column.y - dragon.height;
						} else if (dragon.y < column.height) {
							dragon.y = dragon.height;
						}
					}
				}
			}

			if (dragon.y > HEIGHT - 120 || dragon.y < 0) {
				gameOver = true;
			}

			if ((dragon.y) + yMotion >= HEIGHT - 120) {
				dragon.y = HEIGHT - 120 - dragon.height - 11;
			}
		}
		renderer.repaint();
	}

	
	//*************8 - AFFICHAGE *************
	public void repaint(Graphics g) {
		// Arriere-plan
		//g.setColor(Color.cyan);
		Color back = new Color(94, 194, 182, 150);
		g.setColor(back);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		// Terre
		//g.setColor(Color.orange);
		Color sand = new Color(94, 194, 182, 180);
		g.setColor(sand);
		g.fillRect(0, HEIGHT - 120, WIDTH, 120);
		// Herbe
		//g.setColor(Color.green);
		Color herb = new Color(99, 194, 182, 170);
		g.setColor(herb);
		g.fillRect(0, HEIGHT - 120, WIDTH, 20);
		
		//Joueur EYE
		AffineTransform at = AffineTransform.getTranslateInstance(dragon.x, dragon.y);
		at.rotate(Math.toRadians(rotate += 5), dragon.width, dragon.height);
		//g.drawImage(Renderer.image, dragon.x, dragon.y, dragon.width+20, dragon.height+20, null);
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(Renderer.image, at, null);

		// Enemies
		for (Rectangle column : columns) {
			paintColumn(g, column);
		}

		// Game Over
		//g.setColor(Color.white);
		Color notif = new Color(224, 220, 220, 255);
		g.setColor(notif);
		g.setFont(new Font("Haettenschweiler", 1, 100));
		//g.setFont(new Font("BebasneueBold", 1, 100));

		//AFFICHAGE --> Debut
		if (!started) {
			g.drawString("     Click to start !", 75, HEIGHT / 2 - 50);
		}
		//AFFICHAGE --> FIN
		if (gameOver) {
			g.drawString("GAME OVER", 220, HEIGHT / 3 - 50);
			g.drawString("Score  :  "+score, 170 , HEIGHT / 2 - 100);
			g.drawString(this.getPlayerName()+ " you lose !", 100, HEIGHT / 1 - 50);
		}
		//AFFICHAGE --> SCORE
		if (!gameOver && started) {
			g.drawString(String.valueOf(score), WIDTH / 2 - 25, 100);
			g.drawString(String.valueOf("LEVEL : "+ level), 220, HEIGHT / 1 - 50);

		}

	}

	//*************9 - LES LISTENERS(evenements) *************
	@Override
	public void mouseClicked(MouseEvent e) {
		jump();
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			jump();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}

//Vragon v11.0
//created by CANDAPPANE Vincent v2017
