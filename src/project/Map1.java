package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Map1 {

	public static void main(String[] args) throws InterruptedException, ClassNotFoundException, SQLException {
		Random r = new Random();
		char[][] way1 = new char[20][20];
		int[][] bomb = new int[20][20];
		int[][] mp = new int[20][20];
		int[][] item = new int[20][20];
		int counter = 0;

		while (counter < 20) {
			int x = r.nextInt(20);
			int y = r.nextInt(20);
			if (bomb[x][y] == 0) {
				bomb[x][y] = 1;
				counter++;

			}
		}
		counter = 0;
		while (counter < 3) {
			int x = r.nextInt(20);
			int y = r.nextInt(20);
			if (bomb[x][y] == 0) {
				if (mp[x][y] == 0) {
					mp[x][y] = 2;
					counter++;
				}
			}
		}
		counter = 0;
		while (counter < 3) {
			int x = r.nextInt(20);
			int y = r.nextInt(20);
			if (bomb[x][y] == 0) {
				if (mp[x][y] == 0) {
					if (item[x][y] == 0) {
						item[x][y] = 3;
						counter++;
					}
				}
			}
		}

		counter = 0;

		for (int k = 0; k < 20; k++) {
			for (int j = 0; j < 20; j++) {
				if (bomb[k][j] != 0) {
					way1[k][j] = '*';
				} else if (mp[k][j] != 0) {
					way1[k][j] = '+';

				} else if (item[k][j] != 0) {
					way1[k][j] = '#';
				} else {
					way1[k][j] = ' ';
				}
			}
		}

		int xp = 0;
		int level = 1;
		int currentPositionX = 0;
		int currentPositionY = 0;
		way1[currentPositionX][currentPositionY] = '@';
		int trinkets = 0;
		Scanner in = new Scanner(System.in);
		for (int i = 0; i < way1.length; i++) {
			System.out.println(Arrays.toString(way1[i]));

		}
		Player hero = new Player("Pivo");
		Enemy enemy = new Enemy("Muraska");

		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:C:/GOG Games/bla-bla.db");
		Statement st = conn.createStatement();

		Random random = new Random();
		int b;

		ResultSet rs;
		int money = 0;
		boolean flag = true;
		while (level != 5) {

			if (mp[currentPositionY][currentPositionX] != 0) {

				int d = random.nextInt(3) + 1;
				switch (d) {
				case 1:
					hero.health = hero.health + 25;
					break;
				case 2:
					hero.health = hero.health + 50;
					break;
				case 3:
					hero.health = hero.health + 75;
					break;
				}
				System.out.println("You have now " + hero.health + " health");

				mp[currentPositionY][currentPositionX] = 0;
			}

			if (item[currentPositionY][currentPositionX] != 0) {
				b = random.nextInt(3) + 1;
				System.out.println("b : " + b);
				rs = st.executeQuery("SELECT * FROM  WEAPON WHERE id=" + b);
				Item itemWeapon = new Item(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5));
				hero.getItem(itemWeapon);
				System.out.println("You have just pick up  " + itemWeapon.name + ";");
				hero.Invetory(itemWeapon);
				item[currentPositionY][currentPositionX] = 0;
			}

			System.out.println(currentPositionX);
			System.out.println(currentPositionY);
			System.out.println(hero.health);

			if (hero.health <= 0) {
				System.out.println("You lose");
				break;
			}

			for (int i = 0; i < 20; i++) {
				if (bomb[currentPositionY][currentPositionX] != 0) {
					enemy.getEnemy();
					b = random.nextInt(3) + 1;
					
					System.out.println("b : " + b);
					rs = st.executeQuery("SELECT * FROM  WEAPON WHERE id=" + b);
					Item itemWeapon = new Item(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5));
					enemy.getItem(itemWeapon);
					enemy.getInfo();

					while (hero.health >= 0 && enemy.health >= 0) {
						System.out.println("Choose Attack,Exit");
						switch (in.nextLine()) {

						case "Attack":
							hero.setHealth(enemy.getStrike());
							hero.getInfo();
							enemy.setHealth(hero.getStrike());
							enemy.getInfo();
							if (hero.health > 0 && enemy.health <= 0) {
								xp = xp + 5;
								hero.EXP(xp);
								hero.money = hero.money + r.nextInt(15) + 5;
								System.out.println("You have now " + hero.money + " coins");
								itemWeapon.getInfo();
								System.out.println("Do you want to get this item ?");
								String user = in.nextLine();

								switch (user) {
								case "yes":
									hero.getItem(itemWeapon);
									hero.getInfo();
									hero.Invetory(itemWeapon);
									break;
								case "no":
									hero.undressItem(itemWeapon);
									break;
								}
								System.out.println(Arrays.toString(hero.Inventory));
								trinkets=r.nextInt(2) ;
								System.out.println("You won");

							}
							break;

						case "Exit":
							int a = (int) (Math.random() * 3);
							hero.setHealth(enemy.getStrike());
							hero.getInfo();
							if (a == 1) {

								enemy.health = -5;
								System.out.println("You have successfully escape");
							}
							break;
						default:
							System.out.println("Wrong!");
							break;
						}

					}
					bomb[currentPositionY][currentPositionX] = 0;
				}
			}

			switch (in.nextLine()) {
			case "a":
				if (currentPositionX > 0) {
					way1[currentPositionY][currentPositionX--] = '_';
					way1[currentPositionY][currentPositionX] = '@';
				}
				break;
			case "d":
				if (currentPositionX < 20) {
					way1[currentPositionY][currentPositionX++] = '_';
					way1[currentPositionY][currentPositionX] = '@';
				}
				break;
			case "w":
				if (currentPositionY > 0) {
					way1[currentPositionY--][currentPositionX] = '_';
					way1[currentPositionY][currentPositionX] = '@';
				}
				break;
			case "s":
				if (currentPositionY < 20) {
					way1[currentPositionY++][currentPositionX] = '_';
					way1[currentPositionY][currentPositionX] = '@';
				}
				break;
			case "shop":
				System.out.println("Choose buy,sell,back");
				String userShop = in.nextLine();
				int price = r.nextInt(15) + 5;
				b = random.nextInt(3) + 1;
				System.out.println("b : " + b);
				rs = st.executeQuery("SELECT * FROM  WEAPON WHERE id=" + b);
				Item itemToBuy = new Item(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5));
				switch (userShop) {
				case "buy":
					System.out.println("Name: " + itemToBuy.name + "; Health: " + itemToBuy.health + "%; Power: "
							+ itemToBuy.power + "; Dexterity:" + itemToBuy.dexterity + "; Intelligence: "
							+ itemToBuy.intelligence + "; Strike:" + itemToBuy.strike + "; Price : " + price + ";");
					System.out.println("Do you want to buy it ?");
					userShop = in.nextLine();
					switch (userShop) {
					case "yes":
						if (hero.money >= price) {
							hero.money = hero.money - price;
							hero.getItem(itemToBuy);
							hero.getInfo();
							hero.Invetory(itemToBuy);
							System.out.println(Arrays.toString(hero.Inventory));
						} else {
							System.out.println("Not enough money ");
						}
						break;
					case "no":
						System.out.println("Ok it is your opinion");
						break;
					}
					break;
				case "back":
					break;
				case "sell":
					if (trinkets > 0 ) {
						trinkets=0;
						hero.money=(int) (hero.money+(trinkets*1.5));
					}
					System.out.println("You have now " + hero.money + " coins");
					break;
				}
				break;
			default:
				System.out.println("Wrong input. A,W,S,D only");
			}
			for (int i = 0; i < way1.length; i++) {
				System.out.println(Arrays.toString(way1[i]));
			}
		}
		in.close();
	}
}
