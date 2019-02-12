package vragon;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class Enemies {
	
	public final int WIDTH = 800, HEIGHT = 800;
	public Random rand;
	public ArrayList<Rectangle> columns;
	public String level = "0";
	public int score;

	//*************4 - AJOUTER ENNEMIS & NIVEAU*************
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

}

//Vragon v11.0
//created by CANDAPPANE Vincent v2017
