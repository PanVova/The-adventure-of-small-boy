package project;

import java.util.Arrays;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Player extends Hero {
	Player(String name) throws InterruptedException {
		super(name);

	}

	int xp = 0;
	int level = 1;

	void EXP(int xp) {
		this.xp = xp;
		if (this.xp >= 10) {
			this.xp = this.xp - 10;
			if (this.xp >= 10) {
				this.xp = this.xp - 10;
			}
			this.level++;
			boolean flag = true;
			Scanner in = new Scanner(System.in);
			while (flag) {
				JOptionPane.showMessageDialog(null,
						"Choose your upgrade," + this.name + " : Health,Intelligence,Dexterity,Power");
				String userUpgrade = JOptionPane
						.showInputDialog("Choose: Health-1,Intellligence-2,Dexterity-3,Power-4");
				switch (userUpgrade) {
				case "1":
					JOptionPane.showMessageDialog(null, "You have chose : health");
					this.health = this.health + 10;
					flag = false;
					break;
				case "2":
					JOptionPane.showMessageDialog(null, "You have chose : intelligence");
					this.intelligence = this.intelligence + 2;
					flag = false;
					break;
				case "3":
					JOptionPane.showMessageDialog(null, "You have chose : dexterity");
					this.dexterity = this.dexterity + 4;
					flag = false;
					break;
				case "4":
					JOptionPane.showMessageDialog(null, "You have chose : power");
					this.power = this.power + 2;
					flag = false;
					break;
				default:
					JOptionPane.showMessageDialog(null, "Wrong!");
				}
			}
			if (level == 4) {
				JOptionPane.showMessageDialog(null, "You win the game,but you can continue to play");
			}
		}
		JOptionPane.showMessageDialog(null, "xp=" + this.xp + " level=" + this.level);

	}
}
