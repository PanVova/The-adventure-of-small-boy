package project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
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

import javax.swing.*;

public class Main extends JFrame implements KeyListener {
	JButton Play;
	JButton Exit;
	JButton Settings;
	JButton Example;
	JButton Example1;
	JPanel MainPanel;
	JPanel PlayPanel;
	JPanel SettingsPanel;
	JLabel MainPicture;
	JLabel Character;
	JLabel Grass;
	JLabel Bomb;
	JLabel Health;
	JLabel Item;

	Main() throws ClassNotFoundException {

		super("The adventure of small boy(Panasenko Vova)");
		Class.forName("org.sqlite.JDBC");

		Play = new JButton("Play");
		Play.setSize(300, 100);
		Play.setLocation(440, 300);

		Settings = new JButton("Settings");
		Settings.setSize(300, 100);
		Settings.setLocation(440, 410);

		Exit = new JButton("Exit");
		Exit.setSize(300, 100);
		Exit.setLocation(440, 520);

		MainPicture = new JLabel(new ImageIcon("img\\Project.png"));
		MainPicture.setSize(1200, 750);
		MainPicture.setLocation(0, 0);

		Example = new JButton("Back");
		Example.setSize(300, 100);
		Example.setLocation(440, 410);

		Example1 = new JButton("Panel Play");
		Example1.setSize(300, 100);
		Example1.setLocation(440, 410);

		MainPanel = new JPanel();

		MainPanel.setSize(1200, 750);
		MainPanel.add(Play);
		MainPanel.add(Exit);
		MainPanel.add(Settings);
		MainPanel.add(MainPicture);
		addKeyListener(this);

		MainPanel.setLayout(null);

		PlayPanel = new JPanel();
		PlayPanel.setLayout(new FlowLayout(0));
		SettingsPanel = new JPanel();
		SettingsPanel.setSize(1200, 750);
		SettingsPanel.add(Example);
		SettingsPanel.setLayout(null);

		setSize(1200, 750);
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setContentPane(MainPanel);
		initListeners();
	}

	private void initListeners() {
		Example.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				change2();
			}
		});
		Exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		Play.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Play();
				try {
					change1();
				} catch (ClassNotFoundException | InterruptedException | SQLException e) {
					e.printStackTrace();
				}
			}
		});
		Settings.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Settings();

			}
		});
	}

	void change2() {
		SettingsPanel.setVisible(false);
		setContentPane(MainPanel);
		MainPanel.setVisible(true);
	}

	void Play() {
		MainPanel.setVisible(false);
		setContentPane(PlayPanel);
		PlayPanel.setVisible(true);
	}

	void Settings() {
		MainPanel.setVisible(false);
		setContentPane(SettingsPanel);
		SettingsPanel.setVisible(true);
	}

	public boolean isFocusTraversable() {
		return true;
	}

	void change1() throws InterruptedException, ClassNotFoundException, SQLException {

		Random r = new Random();
		JLabel[][] map = new JLabel[20][20];
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
		int currentPositionX = 0;
		int currentPositionY = 0;

		for (int k = 0; k < 20; k++) {
			for (int j = 0; j < 20; j++) {
				if (k == 0 && j == 0) {
					PlayPanel.add(map[currentPositionX][currentPositionY] = new JLabel(
							new ImageIcon("C:\\GOG Games\\Character.png")));
				} else if (bomb[k][j] != 0) {
					PlayPanel.add(map[k][j] = new JLabel(new ImageIcon("img\\Bomb.png")));
				} else if (mp[k][j] != 0) {
					PlayPanel.add(map[k][j] = new JLabel(new ImageIcon("img\\Health.png")));

				} else if (item[k][j] != 0) {
					PlayPanel.add(map[k][j] = new JLabel(new ImageIcon("img\\Item.png")));
				} else {
					PlayPanel.add(map[k][j] = new JLabel(new ImageIcon("img\\Grass.png")));
				}
			}
		}
		PlayPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		setSize(1130, 1140);

		this.addKeyListener(new KeyAdapter() {

			Connection conn = DriverManager.getConnection("jdbc:sqlite:img/bla-bla.db");
			Statement st = conn.createStatement();
			ResultSet rs;
			int trinkets = 0;
			int currentPositionX = 0;
			int currentPositionY = 0;
			int xp = 0;
			int level = 1;
			Random random = new Random();
			int b;
			int count = 1;
			String name = JOptionPane.showInputDialog("enter name");
			Player hero = new Player(name);
			Enemy enemy = new Enemy("Muraska");

			public void keyPressed(KeyEvent e) {
				{
					int keyCode = e.getKeyCode();
					switch (keyCode) {
					case KeyEvent.VK_UP:
						System.out.println("up");
						if (currentPositionY > 0) {
							map[currentPositionY][currentPositionX].setIcon(new ImageIcon("img\\Grass.png"));
							map[currentPositionY - 1][currentPositionX]
									.setIcon(new ImageIcon("C:\\GOG Games\\Character.png"));
							currentPositionY--;
						}
						break;
					case KeyEvent.VK_DOWN:
						System.out.println("down");
						if (currentPositionY < 19) {
							map[currentPositionY][currentPositionX].setIcon(new ImageIcon("img\\Grass.png"));
							map[currentPositionY + 1][currentPositionX]
									.setIcon(new ImageIcon("C:\\GOG Games\\Character.png"));
							currentPositionY++;
						}

						break;
					case KeyEvent.VK_LEFT:
						System.out.println("left");
						if (currentPositionX > 0) {
							map[currentPositionY][currentPositionX].setIcon(new ImageIcon("img\\Grass.png"));
							map[currentPositionY][currentPositionX - 1]
									.setIcon(new ImageIcon("C:\\GOG Games\\Character.png"));
							currentPositionX--;
						}
						break;
					case KeyEvent.VK_RIGHT:
						if (currentPositionX < 19) {

							System.out.println("right");
							map[currentPositionY][currentPositionX].setIcon(new ImageIcon("img\\Grass.png"));
							map[currentPositionY][currentPositionX + 1]
									.setIcon(new ImageIcon("C:\\GOG Games\\Character.png"));
							currentPositionX++;
							break;
						}
					case KeyEvent.VK_T:
						try {
							JOptionPane.showMessageDialog(null, "Choose buy,sell,back");
							String userShop = JOptionPane.showInputDialog("Choose: Buy-1,Back-2,Sell-3");
							int price = r.nextInt(15) + 5;
							b = random.nextInt(3) + 1;
							System.out.println("b : " + b);
							rs = st.executeQuery("SELECT * FROM  WEAPON WHERE id=" + b);
							Item itemToBuy = new Item(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getInt(4),
									rs.getInt(5));
							switch (userShop) {
							case "1":
								JOptionPane.showMessageDialog(null,
										"Name: " + itemToBuy.name + "; Health: " + itemToBuy.health + "%; Power: "
												+ itemToBuy.power + "; Dexterity:" + itemToBuy.dexterity
												+ "; Intelligence: " + itemToBuy.intelligence + "; Strike:"
												+ itemToBuy.strike + "; Price : " + price + ";");
								JOptionPane.showMessageDialog(null, "Do you want to buy it ?");
								userShop = JOptionPane.showInputDialog("Choose: Yes-1,No-2");
								switch (userShop) {
								case "1":
									if (hero.money >= price) {
										hero.money = hero.money - price;
										hero.getItem(itemToBuy);
										hero.getInfo();
										hero.Invetory(itemToBuy);
										JOptionPane.showMessageDialog(null, Arrays.toString(hero.Inventory));
									} else {
										JOptionPane.showMessageDialog(null, "Not enough money ");
									}
									break;
								case "2":
									JOptionPane.showMessageDialog(null, "Ok it is your opinion");
									break;
								}
								break;
							case "2":
								break;
							case "3":
								if (trinkets > 0) {
									trinkets = 0;
									hero.money = (int) (hero.money + (trinkets * 1.5));
									JOptionPane.showMessageDialog(null, "You have earned " + trinkets * 1.5 + " coins");
								}
								JOptionPane.showMessageDialog(null, "You have now " + hero.money + " coins");
								break;
							}
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
					PlayPanel.validate();
					PlayPanel.repaint();
				}
				if (hero.health <= 0) {
					JOptionPane.showMessageDialog(null, "You lose");
					System.exit(0);
				}
				try {
					if (item[currentPositionY][currentPositionX] != 0) {
						System.out.println("item");
						b = random.nextInt(11) + 1;
						System.out.println("b : " + b);

						rs = st.executeQuery("SELECT * FROM  WEAPON WHERE id=" + b);
						Item itemWeapon = new Item(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getInt(4),
								rs.getInt(5));
						hero.getItem(itemWeapon);
						JOptionPane.showMessageDialog(null, "You have just pick up  " + itemWeapon.name + ";");
						hero.Invetory(itemWeapon);
						item[currentPositionY][currentPositionX] = 0;

					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
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
					JOptionPane.showMessageDialog(null, "You have now " + hero.health + " health");

					mp[currentPositionY][currentPositionX] = 0;
				}
				try {
					if (bomb[currentPositionY][currentPositionX] != 0) {
						enemy.getEnemy();
						b = random.nextInt(3) + 1;
						System.out.println("b : " + b);

						rs = st.executeQuery("SELECT * FROM  WEAPON WHERE id=" + b);
						Item itemWeapon = new Item(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getInt(4),
								rs.getInt(5));
						enemy.getItem(itemWeapon);
						enemy.getInfo();
						while (hero.health >= 0 && enemy.health >= 0) {
							String action = JOptionPane.showInputDialog("Choose: Attack-1,Exit-2");
							switch (action) {

							case "1":
								hero.setHealth(enemy.getStrike());
								hero.getInfo();
								enemy.setHealth(hero.getStrike());
								enemy.getInfo();
								if (hero.health > 0 && enemy.health <= 0) {

									this.xp = this.xp + random.nextInt(5) + 2;
									if (this.xp >= 10) {
										hero.EXP(this.xp);
										this.xp = this.xp - 10;
										count = 0;
									}
									System.out.println(this.xp);
									if (count >= 1) {
										hero.EXP(this.xp);
									}
									count++;
									hero.money = hero.money + r.nextInt(15) + 5;
									JOptionPane.showMessageDialog(null, "You have now " + hero.money + " coins");
									itemWeapon.getInfo();
									System.out.println("Do you want to get this item ?");
									String user = JOptionPane.showInputDialog("Choose: Yes-1,No-2");

									switch (user) {
									case "1":
										hero.getItem(itemWeapon);
										hero.getInfo();
										hero.Invetory(itemWeapon);
										break;
									case "2":
										hero.undressItem(itemWeapon);
										break;
									}
									JOptionPane.showMessageDialog(null, Arrays.toString(hero.Inventory));
									trinkets = r.nextInt(2);

								}
								break;

							case "2":
								int a = (int) (Math.random() * 3);
								hero.setHealth(enemy.getStrike());
								hero.getInfo();
								if (a == 1) {

									enemy.health = -5;
									JOptionPane.showMessageDialog(null, "You have successfully escape");
								}
								break;
							default:
								JOptionPane.showMessageDialog(null, "Wrong!");
								break;
							}

						}
						bomb[currentPositionY][currentPositionX] = 0;

					}
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}

			@Override
			public void keyReleased(KeyEvent arg0) {

			}

			@Override
			public void keyTyped(KeyEvent arg0) {

			}
		});
	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
}
