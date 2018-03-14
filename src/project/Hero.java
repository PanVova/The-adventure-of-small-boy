package project;

import java.util.Arrays;

import javax.swing.JOptionPane;

public class Hero {
	String name;
	int health = 100;
	int intelligence = 20;
	int power = 5;
	int dexterity = 10;
	int strike = (intelligence / 3) + (dexterity / 2) + power;
	Item[] items = new Item[10];
	String[] Inventory = new String[10];
	int money = 10;

	Hero(String name) {
		this.name = name;
		if (name.equals("Mukuzaka")) {
			health = 500;
			intelligence = 40;
			power = 20;
			dexterity = 1000;
		}
		System.out.println(name);
	}

	void getItem(Item item)

	{

		for (int i = 0; i < items.length; i++) {

			if (items[i] == null) {
				this.health = this.health + item.health;
				this.intelligence = this.intelligence + item.intelligence;
				this.dexterity = this.dexterity + item.dexterity;
				this.power = this.power + item.power;
				this.strike = (this.intelligence / 3) + (this.dexterity / 2) + this.power;
				items[i] = item;
				break;
			}
		}
	}

	void undressItem(Item item) {
		this.health = this.health - item.health;
		this.intelligence = this.intelligence - item.intelligence;
		this.dexterity = this.dexterity - item.dexterity;
		this.power = this.power - item.power;
		this.strike = (this.intelligence / 3) + (this.dexterity / 2) + this.power;
		item.name = null;
		this.Invetory(item);

	}

	void Invetory(Item item) {
		for (int i = 0; i < Inventory.length; i++) {
			if (Inventory[i] == null) {
				Inventory[i] = item.name;
				break;
			} else if (Inventory[9] != null) {
				System.out.println("Too many items in inventory!!!");
				break;

			}
		}

	}

	void setHealth(int kolbasa) {
		this.health -= kolbasa;
		this.strike = (intelligence / 3) + (dexterity / 2) + power;
	}

	int getStrike() {
		return this.strike;
	}

	void getInfo() throws InterruptedException {
		JOptionPane.showMessageDialog(null,
				"Name: " + this.name + "; Health: " + this.health + "%; Power: " + this.power + "; Dexterity:"
						+ this.dexterity + "; Intelligence: " + this.intelligence + "; Strike:" + this.strike + ";");
		Thread.sleep(500);

	}
}
