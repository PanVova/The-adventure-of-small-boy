package project;

import javax.swing.JOptionPane;

public class Item implements Cloneable {
	String name;
	int health;
	int intelligence;
	int power;
	int dexterity;
	int strike;

	Item(String name, int health, int intelligence, int power, int dexterity) {
		this.name = name;
		this.health = health;
		this.intelligence = intelligence;
		this.power = power;
		this.dexterity = dexterity;

	}

	Item(Item item) throws CloneNotSupportedException {
		this(item.name, item.health, item.intelligence, item.power, item.dexterity);

	}

	void getInfo() {
		JOptionPane.showMessageDialog(null,
				"Item what you got :" + "Name " + this.name + "; Health " + this.health + "; Intelligence "
						+ this.intelligence + "; Power " + this.power + "; Dexterity " + this.dexterity + ";");
	}

}
