import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.Image;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class GraphicsPanel extends JPanel implements MouseListener, KeyListener {

	private Rectangle roulette;  
	private Rectangle slotmachine;      
	private Rectangle blackjack;  
	private Rectangle mainMenuButton;  
	boolean mainMenu = true;
	boolean rouletteChoice = false;
	boolean slotsChoice = false;
	boolean blackjackChoice = false;
	String rouletteColor = "";
	String rouletteBet = "empty";

	int balance = 1000;

	// Variables for the spinning roulette wheel
	private double rotationAngle = 0;   
	private boolean isSpinning = false; 
	private Timer timer;                
	private long lastTime;             
	private double targetAngle = 0;    
	private double rotationSpeed = 10;  
	boolean updatedBalance = false;  

	public GraphicsPanel() {
		setPreferredSize(new Dimension(730, 730));   // Set these dimensions to the width
		addMouseListener(this); // Mouse listener 
		addKeyListener(this);   // Key listener
		setFocusable(true);
		roulette = new Rectangle(100, 500, 100, 100);
		slotmachine = new Rectangle(300, 500, 100, 100);
		blackjack = new Rectangle(500, 500, 100, 100);
		mainMenuButton = new Rectangle(50, 50, 100, 100);


		timer = new Timer(10, e -> {
			if (isSpinning) {
				long currentTime = System.currentTimeMillis();
				if (currentTime - lastTime > 20) {
					rotationAngle += rotationSpeed; 
					if (rotationAngle >= 360) {
						rotationAngle -= 360; 
					}

					if (rotationAngle >= targetAngle - 5 && rotationAngle <= targetAngle + 5) {
						rotationSpeed *= 0.98; 
					}

					lastTime = currentTime;

					if (Math.abs(rotationAngle - targetAngle) < 1) {
						isSpinning = false; 
					}
					repaint();
				}
			}

			if (!isSpinning && !updatedBalance) {  
				updateBalanceR();
				updatedBalance = true; 
				repaint();  
			}
		});
	}
	//function: 
	//description: updates the balance for 
	//parameters: none
	//return: none
	private void updateBalanceR() {
		double randomNumber = Math.random();
		if (randomNumber < 0.47) {
			rouletteColor = "RED"; // 47% chance for RED
		} else if (randomNumber < 0.94) {
			rouletteColor = "BLACK"; // 47% chance for BLACK
		} else {
			rouletteColor = "GREEN"; // 6% chance for GREEN
		}

		if (rouletteColor.equals("BLACK") && rouletteBet.equals("BLACK")) {
			balance *= 2;  // Double balance on BLACK win
		} else if (rouletteColor.equals("RED") && rouletteBet.equals("RED")) {
			balance *= 2;  // Double balance on RED win
		} else if (rouletteColor.equals("GREEN") && rouletteBet.equals("GREEN")) {
			balance *= 18;  // 10x balance on GREEN win
		} else {
			balance /= 2;  // Halve the balance on a loss
		}

		System.out.println("Spun: " + rouletteColor + ", Bet: " + rouletteBet + ", New balance: " + balance);
	}

	// method: paintComponent
	// description: This method will paint the items onto the graphics panel.  This method is called when the panel is
	//               first rendered.  It can also be called by this.repaint()
	// parameters: Graphics g - This object is used to draw your images onto the graphics panel.
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Color darkRed = new Color(148, 34, 40); // Color dark red
		Color lightRed = new Color(207, 55, 75); // Color light red

		Graphics2D g2 = (Graphics2D) g;

		// Draw the main menu if it's the main menu state
		if (mainMenu && balance != 0) {
			ClassLoader cldr = this.getClass().getClassLoader();    // These five lines of code load the background picture.
			String imagePath = "images/output-onlinepngtools.png";            // Change this line if you want to use a different 
			URL imageURL = cldr.getResource(imagePath);                // background image.  The image should be saved in the
			ImageIcon image = new ImageIcon(imageURL);                // images directory.
			Image img = image.getImage();
			int imgWidth = img.getWidth(null);
			int imgHeight = img.getHeight(null);
			

			g2.setColor(darkRed);
			g2.fillRect(-10, -10, 10000, 10000);
			g2.setColor(lightRed);
			g2.fillRect(roulette.x, roulette.y, roulette.width, roulette.height);
			g2.fillRect(slotmachine.x, slotmachine.y, slotmachine.width, slotmachine.height);
			g2.fillRect(blackjack.x, blackjack.y, blackjack.width, blackjack.height);
			g2.fillRect(495, 5, 100, 20);

			image.paintIcon(this, g2, 20, 10);  
			

			g2.setColor(Color.black);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 
			g2.drawString("Roulette", 115, 550);
			g2.drawString("Slots", 325, 550);
			g2.drawString("Blackjack", 510, 550);


			g2.drawString("$$$ = " + String.valueOf(balance), 500, 20);
		}

		// If roulette is selected, draw the roulette game screen
		else if (rouletteChoice && balance != 0) {
			g2.setColor(darkRed);
			g2.fillRect(-10, -10, 100000, 10000);
			g2.setColor(Color.BLACK);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 50)); 
			g2.drawString("ROULETTE", 250, 100);
			g2.setFont(new Font("TimesRoman", Font.PLAIN, 20));
			g2.drawString("R,B,G, to place bet, SPACE to spin", 225, 575);
			g2.drawString("Betting on: " + rouletteBet, 225, 600);
			g2.setColor(lightRed);
			g2.fillRect(495, 5, 100, 20);
			g2.setColor(Color.BLACK);
			g2.drawString("$$$ = " + String.valueOf(balance), 500, 20);
			g2.setFont(new Font("TimesRoman", Font.PLAIN, 20));
			g2.setColor(lightRed);
			g2.fillRect(mainMenuButton.x, mainMenuButton.y, mainMenuButton.width, mainMenuButton.height);
			g2.setColor(Color.black);
			g2.drawString("Main Menu", 50, 100);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 50)); 



			ClassLoader cldr = this.getClass().getClassLoader();    // These five lines of code load the background picture.
			String imagePath = "images/rouletteWheel-removebg-preview.png";            // Change this line if you want to use a different 
			URL imageURL = cldr.getResource(imagePath);                // background image.  The image should be saved in the
			ImageIcon image = new ImageIcon(imageURL);                // images directory.
			Image img = image.getImage();
			int imgWidth = img.getWidth(null);
			int imgHeight = img.getHeight(null);

			g2.rotate(Math.toRadians(rotationAngle), 220 + imgWidth / 2, 290 + imgHeight / 2);
			image.paintIcon(this, g2, 220, 290);  
			g2.rotate(-Math.toRadians(rotationAngle), 220 + imgWidth / 2, 290 + imgHeight / 2);  

			if (rouletteColor.equals("RED")) {
				g2.setColor(Color.red);
				g2.drawString("RED", 300, 200);
			} else if (rouletteColor.equals("BLACK")) {
				g2.setColor(Color.black);
				g2.drawString("BLACK", 300, 200);
			} else if (rouletteColor.equals("GREEN")) {
				g2.setColor(Color.green);
				g2.drawString("GREEN", 300, 200);
			}
			else {
				g2.drawString("", 300, 200);

			}
		}
		else if (slotsChoice == true && balance != 0) {
			//slots code goes here
		}

		else if (blackjackChoice == true && balance != 0) {
			//blackjack code goes here
		}

		else if (balance == 0) {

			mainMenu = false;
			slotsChoice = false;
			blackjackChoice = false;
			g2.setColor(Color.BLACK);
			g2.fillRect(-10, -10, 999, 999);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 100)); 
			g2.setColor(Color.red);
			g2.drawString("YOU LOSE", 100, 350);

		}
		else if (balance >= 1000000) {

			mainMenu = false;
			slotsChoice = false;
			blackjackChoice = false;
			g2.setColor(Color.BLACK);
			g2.fillRect(-10, -10, 999, 999);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 100)); 
			g2.setColor(Color.green);
			g2.drawString("YOU WIN", 100, 350);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 
			g2.drawString("but you also got kicked out", 100, 600);

		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (roulette.contains(getMousePosition())) {
			rouletteChoice = true;
			mainMenu = false;
			blackjackChoice = false;
			slotsChoice = false;
			this.repaint();
		} else if (slotmachine.contains(getMousePosition())) {
			slotsChoice = true;
			blackjackChoice = false;
			mainMenu = false;
			rouletteChoice = false;

			this.repaint();

		} else if (blackjack.contains(getMousePosition())) {
			blackjackChoice = true;
			mainMenu = false;
			rouletteChoice = false;
			slotsChoice = false;

			this.repaint();
		}
		else if (mainMenuButton.contains(getMousePosition())) {
			mainMenu = true;
			rouletteChoice = false;
			slotsChoice = false;
			blackjackChoice = false;

			this.repaint();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode(); 

		if (keyCode == KeyEvent.VK_SPACE && rouletteChoice == true) {
			targetAngle = Math.random() * 360; // Random angle between 0 and 360
			rotationSpeed = 10;  
			isSpinning = true;
			lastTime = System.currentTimeMillis();
			updatedBalance = false; 
			timer.start(); 
		} else if (keyCode == KeyEvent.VK_R && rouletteChoice == true) {
			rouletteBet = "RED";
			this.repaint();

		} else if (keyCode == KeyEvent.VK_B && rouletteChoice == true) {
			rouletteBet = "BLACK";
			this.repaint();

		} else if (keyCode == KeyEvent.VK_G && rouletteChoice == true) {
			rouletteBet = "GREEN";
			this.repaint();

		}
		else if (keyCode == KeyEvent.VK_9 ) {
			balance = 0;
			this.repaint();

		}
		else if (keyCode == KeyEvent.VK_1 ) {
			balance += 100;
			this.repaint();

		}
		else if (keyCode == KeyEvent.VK_2 ) {
			balance += 1000;
			this.repaint();

		}
		else if (keyCode == KeyEvent.VK_3 ) {
			balance += 100000;
			this.repaint();

		}
		else if (keyCode == KeyEvent.VK_4 ) {
			balance += 999999999;
			this.repaint();

		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
