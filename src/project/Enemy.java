package project;

import java.util.Random;

public class Enemy extends Hero {

	Enemy(String name) {

		super(name);
	}

	int enemys[] = new int[5];

	void getEnemy() {
		Random r = new Random();
		int enemy = r.nextInt(5) + 1;
		switch (enemy) {
		case 1:
			this.name = "Powerful Troll";
			this.health = 30;
			this.dexterity = 5;
			this.intelligence = 4;
			this.power = 6;
			break;
		case 2:
			this.name = "Small Cat";
			this.health = 10;
			this.dexterity = 2;
			this.intelligence = 2;
			this.power = 3;
			break;
		case 3:
			this.name = "Book";
			this.health = 40;
			this.dexterity = 1;
			this.intelligence = 10;
			this.power = 3;
			break;
		case 4:
			this.name = "Assasin";
			this.health = 60;
			this.dexterity = 10;
			this.intelligence = 10;
			this.power = 3;
			break;
		case 5:
			this.name = "Girl";
			this.health = 80;
			this.dexterity = 1;
			this.intelligence = 2;
			this.power = 2;
			break;
		}
	}

}
