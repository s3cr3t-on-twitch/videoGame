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
import java.util.ArrayList;

public class GraphicsPanel extends JPanel implements MouseListener, KeyListener {

	private final int SQUARE_WIDTH = 90;    // The width of one space on the board.  Constant used for drawing board.
	private final int OFFSET = 5;
	private boolean click;   				// false until the game has started by somebody clicking on the board.  should also be set to false
	private Rectangle roulette;  
	private Rectangle slotmachine;      
	private Rectangle blackjack;  
	boolean mainMenu = true;
	boolean rouletteChoice = false;
	boolean slotsChoice = false;
	boolean blackjackChoice = false;
	boolean SLOTS_GO = false;
	String rouletteColor = "test";
	String rouletteBet = "nothing yet, press a button and spin";
	String firSlot = "a";
	String secSlot = "b";
	String thiSlot = "c";
	int balance = 1000;
	private Rectangle hitBox;	//Click to hit
	private Rectangle stayBox;	//Click to stay
	private Rectangle mainMenuButton; //Go back to main menu
	private ArrayList<Integer> deckCards = new ArrayList<Integer>();	//deck of cards
	boolean hit = false;	//select hit for turn
	int compTotal=0;	//computer score
	int userTotal=0;	//User score
	int userCards = 0;	//how many cards the user has pulled
	int compCards = 0;	//how many cards the computer has pulled
	boolean stay = false;	//stay during turn
	int cardNum = 0;	//the number of the card pulled
	private ArrayList<Integer> playerHand = new ArrayList<Integer>();	//player hand cards
	private ArrayList<Integer> computerHand = new ArrayList<Integer>();	//computer hand cards
	int cardPulled;	//which card is pulled
	int cardRemove;	//which card you will be removing from the deck
	int faceCard = 0;	//if it is a face card, what value
	String card = "";	//which card num/face value to be printed
	String suit = "";	//which suit to be printed


	// Variables for the spinning roulette wheel
	private double rotationAngle = 0;   
	private boolean isSpinning = false; 
	private Timer timer;                
	private long lastTime;             
	private double targetAngle = 0;    
	private double rotationSpeed = 10;  
	boolean updatedBalance = false;  

	public GraphicsPanel() {
		setPreferredSize(new Dimension(SQUARE_WIDTH * 8 + OFFSET * 2, SQUARE_WIDTH * 8 + OFFSET * 2));   // Set these dimensions to the width
		addMouseListener(this); // Mouse listener 
		addKeyListener(this);   // Key listener
		setFocusable(true);
		roulette = new Rectangle(100, 500, 100, 100);
		slotmachine = new Rectangle(300, 500, 100, 100);
		hitBox = new Rectangle(300, 500, 100, 100);	//Click to hit
		stayBox = new Rectangle(425, 500, 150, 100);	//Click to hit
		blackjack = new Rectangle(500, 500, 100, 100);

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

	private void updateBalanceR() {
		if (rouletteChoice) {
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

		//7 thigimagigs


	}
	private void updateBalanceS() {
		if (slotsChoice) {
			if (firSlot.equals(secSlot) && secSlot.equals(thiSlot)) {
				// All three symbols match (jackpot)
				balance += balance * 2;  // Triple balance (original + 2x winnings)
			} else if (firSlot.equals(secSlot) || firSlot.equals(thiSlot) || secSlot.equals(thiSlot)) {
				// Two symbols match
				balance += balance * 0.25;  // 25% winnings
			} else {
				// No match, apply a loss
				balance -= balance * 0.15;  // 15% loss
			}
		}
	}


	//Function: assignSuit

	//Description: it takes the index of the card passed and it returns the suit that corresponds to it

	//Parameters: int cardIndex

	//Return: string suit

	public String assignSuit(int cardIndex) {

		String suit = "";

		if (cardIndex / 13 == 3) {

			suit = "♠";  // Spades

		} 

		else if (cardIndex / 13 == 2) {

			suit = "♣";  // Clubs

		} 

		else if (cardIndex / 13 == 1) {

			suit = "♥";  // Hearts

		} 

		else {

			suit = "♦";  // Diamonds

		}

		return suit;

	}

	//Function: assignCard

	//Description: assigns the card value as a string to be drawn on the card

	//Parameters: int cardIndex--index of the cards

	//Return: the string that has the value of the card

	public String assignCard(int cardIndex) {

		if (cardIndex == 0) {

			return "K";  // King

		}

		else if (cardIndex == 11) {

			return "J";  // Jack

		} 

		else if (cardIndex == 12) {

			return "Q";  // Queen

		}

		else {

			return String.valueOf(cardIndex);  // Numeric cards: 1-10

		}

	}
	// method: paintComponent
	// description: This method will paint the items onto the graphics panel.  This method is called when the panel is
	//               first rendered.  It can also be called by this.repaint()
	// parameters: Graphics g - This object is used to draw your images onto the graphics panel.
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Color darkRed = new Color(148, 34, 40); // Color dark red
		Color feltGreen = new Color(45, 122, 42); //Color the same as green felt
		Graphics2D g2 = (Graphics2D) g;
		if (balance == 0) {

			mainMenu = false;

			slotsChoice = false;

			blackjackChoice = false;

			g2.setColor(Color.BLACK);

			g2.fillRect(-10, -10, 999, 999);

			g.setFont(new Font("TimesRoman", Font.PLAIN, 100)); 

			g2.setColor(Color.red);

			g2.drawString("YOU LOSE", 100, 350);

		}
		// Draw the main menu if it's the main menu state
		if (mainMenu) {
			roulette = new Rectangle(100, 500, 100, 100);
			slotmachine = new Rectangle(300, 500, 100, 100);
			hitBox = new Rectangle(300, 500, 100, 100);	//Click to hit
			stayBox = new Rectangle(425, 500, 150, 100);	//Click to hit
			blackjack = new Rectangle(500, 500, 100, 100);
			g2.setColor(darkRed);
			g2.fillRect(-10, -10, 10000, 10000);
			g2.setColor(Color.red);
			g2.fillRect(roulette.x, roulette.y, roulette.width, roulette.height);
			g2.fillRect(slotmachine.x, slotmachine.y, slotmachine.width, slotmachine.height);
			g2.fillRect(blackjack.x, blackjack.y, blackjack.width, blackjack.height);
			g2.setColor(Color.black);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 50)); 
			g2.drawString("CASINO", 250, 100);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 
			g2.drawString("Roulette", 115, 550);
			g2.drawString("Slots", 325, 550);
			g2.drawString("Blackjack", 510, 550);

			g2.drawString("$$$ = " + String.valueOf(balance), 500, 20);
		}

		// If roulette is selected, draw the roulette game screen
		if (rouletteChoice) {
			roulette = new Rectangle(0,0,0,0);
			slotmachine = new Rectangle(0,0,0,0);
			hitBox = new Rectangle(0,0,0,0);	//Click to hit
			stayBox = new Rectangle(0,00,0,0);	//Click to hit
			blackjack = new Rectangle(0,0,0,0);
			g2.setColor(Color.white);
			g2.fillRect(-10, -10, 100000, 10000);
			g2.setColor(Color.BLACK);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 50)); 
			g2.drawString("ROULETTE", 250, 100);
			g2.setFont(new Font("TimesRoman", Font.PLAIN, 20));
			g2.drawString("R,B,G, to place bet, SPACE to spin", 225, 575);
			g2.drawString("Betting on " + rouletteBet, 225, 600);

			g2.drawString("$$$ = " + String.valueOf(balance), 500, 20);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 50)); 


			ClassLoader cldr = this.getClass().getClassLoader();    // These five lines of code load the background picture.
			String imagePath = "images/rouletteWheel.png";            // Change this line if you want to use a different 
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
			} else {
				g2.setColor(Color.green);
				g2.drawString("GREEN", 300, 200);
			}
		}
		else if (slotsChoice) {
			roulette = new Rectangle(0,0,0,0);
			slotmachine = new Rectangle(0,0,0,0);
			hitBox = new Rectangle(0,0,0,0);	//Click to hit
			stayBox = new Rectangle(0,00,0,0);	//Click to hit
			blackjack = new Rectangle(0,0,0,0);
			g2.setColor(Color.white);
			g2.fillRect(-10, -10, 100000, 10000);
			g2.setColor(Color.BLACK);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 50)); 
			g2.drawString("SLOT MACHINE", 150, 100);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
			g2.drawString("SPACE to Spin", 250, 575);
			g2.drawString("$$$ = " + String.valueOf(balance), 500, 20);

			ClassLoader cldr = this.getClass().getClassLoader();
			String imagePath = "images/jackpot-slot-machine-removebg-preview.png";
			URL imageURL = cldr.getResource(imagePath);
			ImageIcon image = new ImageIcon(imageURL);
			Image img = image.getImage();
			image.paintIcon(this, g2, 50, 100);

			if (SLOTS_GO) {
				double rand1 = Math.random();
				double rand2 = Math.random();
				double rand3 = Math.random();

				g2.setFont(new Font("TimesRoman", Font.PLAIN, 100));
				g2.setColor(Color.white);
				g2.fillRect(169, 330, 92, 120);
				g2.fillRect(285, 330, 92, 120);
				g2.fillRect(400, 330, 92, 120);

				// First, assign the new slot values
				firSlot = getSlotSymbol(rand1);
				secSlot = getSlotSymbol(rand2);
				thiSlot = getSlotSymbol(rand3);

				// Then, draw the symbols
				g2.setColor(Color.black);
				g2.drawString(firSlot, 169, 420);
				g2.drawString(secSlot, 300, 420);
				g2.drawString(thiSlot, 422, 420);

				// Now update the balance AFTER setting new slot values
				updateBalanceS();

				SLOTS_GO = false;  // Reset after updating balance
			}

		}
		//if blackjack is true, and this.repaint is triggered, will make the design for the blackjack game

		else if(blackjackChoice) {
			hitBox = new Rectangle(300, 500, 100, 100);	//Click to hit
			stayBox = new Rectangle(425, 500, 150, 100);	//Click to hit
			roulette = new Rectangle(0,0,0,0);
			slotmachine = new Rectangle(0,0,0,0);
			blackjack = new Rectangle(0,0,0,0);
			//background is felt green

			g2.setColor(feltGreen);

			g2.fillRect(-10, -10, 100000, 10000);

			//PRINT the name of the game, the balance, and the RULES

			g.setFont(new Font("TimesRoman", Font.PLAIN, 50));

			g.setColor(Color.BLACK);

			g2.drawString("BLACK JACK", 200, 100);

			g.setFont(new Font("TimesRoman", Font.PLAIN, 20));

			g2.drawString("Welcome to Black Jack!", 20, 400);

			g2.drawString("Press hit every time you would like a new card. ",20, 620);

			g2.drawString( "Press stay each turn you would like to stay.", 20, 640);

			g2.drawString("Your money double every time you win, ", 20 , 660);

			g2.drawString("halves when you lose, and stays the same when you tie.", 20, 680);

			g2.drawString("$$$ = " + String.valueOf(balance), 500, 20);



			//Hit box and stay box

			g.setFont(new Font("TimesRoman", Font.PLAIN, 50));

			g2.setColor(darkRed);

			g2.fillRect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);

			g2.setColor(Color.WHITE);

			g2.drawString("HIT", 310, 565);

			g2.setColor(darkRed);

			g2.fillRect(stayBox.x, stayBox.y, stayBox.width, stayBox.height);

			g2.setColor(Color.WHITE);

			g2.drawString("STAY", 435, 565);







			if (hit ) {

				// HUMAN TURN: Draw user card

				//draw random card

				cardPulled = (int) (Math.random() * deckCards.size());

				//make the card removed remembered for later

				cardRemove = cardPulled;

				//get the value for the index of the card pulled

				cardPulled = deckCards.get(cardPulled);

				//adds this card to the playerHand ArrayList

				playerHand.add(cardPulled);

				//go through the playerHand

				for(int i = 0; i < playerHand.size(); i++) {

					//create background of cards

					g2.setColor(Color.WHITE);

					g2.fillRect(315 + userCards, 375, 70, 100);

					//print the suit of the card on the card

					g.setColor(Color.BLACK);

					//calls assignSuit function to get suit

					suit = assignSuit(cardPulled);

					g.setFont(new Font("TimesRoman", Font.PLAIN, 25));

					g2.drawString(suit, 315 + userCards, 395);

					//print the value of the card on the card after getting the card value

					faceCard = cardPulled % 13;

					//calls assignCard function to get suit

					card = assignCard(faceCard);

					g.setFont(new Font("TimesRoman", Font.PLAIN, 45));

					g2.drawString(card, 335 + userCards, 430);



				}

				//removes the cards from the deck so that they cannot be reused

				deckCards.remove(cardRemove);

				//adds the values to the total

				//if it is a face card

				if (card.equals("K") || card.equals("Q") || card.equals("J")) {

					userTotal += 10;

				} 

				//if it is not a face card

				else {

					userTotal += (faceCard == 0 ? 10 : faceCard);

				}

				//updates for card spacing

				userCards += 80;

				//end hit

				hit = false;



				//Creates the white space for the next card

				g2.setColor(Color.WHITE);

				g2.fillRect(315 + userCards, 375, 70, 100);

				g.setFont(new Font("TimesRoman", Font.PLAIN, 25));

				g2.drawString(suit, 315 + userCards, 395);

				g.setFont(new Font("TimesRoman", Font.PLAIN, 45));

				g2.drawString(card, 335 + userCards, 430);

			}

			//print score

			g2.setColor(Color.BLACK);

			g.setFont(new Font("TimesRoman", Font.PLAIN, 25));

			g2.drawString("User Score: " + userTotal, 40, 340);





			if (userTotal > 21) {

				// User loses

				//black screen

				g2.setColor(Color.BLACK);

				g2.fillRect(0, 0, 750, 750);

				//color to red and print out you lose

				g2.setColor(Color.RED);

				g.setFont(new Font("TimesRoman", Font.PLAIN, 55));

				g2.drawString("You Lose", 230, 275);

				//print out the scores

				g.setFont(new Font("TimesRoman", Font.PLAIN, 25));

				g2.drawString("Your Score: " + userTotal, 100, 450);

				g2.drawString("Computer Score: " + compTotal, 400, 450);

				//create the main menu button

				g2.setColor(Color.red);

				g2.fillRect(mainMenuButton.x, mainMenuButton.y, mainMenuButton.width, mainMenuButton.height);

				g2.setColor(Color.black);

				g2.drawString("Main Menu", 50, 100);

				//update the balance

				balance/=2;

			}

			else if(stay&& userTotal == compTotal && userTotal!=0) {

				//black screen

				g2.setColor(Color.BLACK);

				g2.fillRect(0, 0, 750, 750);

				//print tied and scores

				g2.setColor(Color.GREEN);

				g.setFont(new Font("TimesRoman", Font.PLAIN, 55));

				g2.drawString("You Tied", 200, 250);

				g.setFont(new Font("TimesRoman", Font.PLAIN, 25));

				g2.drawString("Your Score: " + userTotal, 100, 450);

				g2.drawString("Computer Score: " + compTotal, 400, 450);

				//create button for main menu

				g2.setColor(Color.red);

				g2.fillRect(mainMenuButton.x, mainMenuButton.y, mainMenuButton.width, mainMenuButton.height);

				g2.setColor(Color.black);

				g2.drawString("Main Menu", 50, 100);

			}



			if (compTotal<userTotal && userTotal<=21) {

				// COMPUTER TURN: Draw computer card

				//draw random card

				cardPulled = (int) (Math.random() * deckCards.size());

				//make the card removed remembered for later

				cardRemove = cardPulled;

				//get the value for the index of the card pulled

				cardPulled = deckCards.get(cardPulled);

				//adds this card to the computerHand ArrayList

				computerHand.add(cardPulled);

				//go through the computerHand

				for(int i = 0; i < computerHand.size(); i++) {

					//create background of cards

					g2.setColor(Color.WHITE);

					g2.fillRect(315 + compCards, 205, 70, 100);

					//print the suit of the card on the card

					g.setColor(Color.BLACK);

					//calls assignSuit function to get suit

					suit = assignSuit(cardPulled);

					g.setFont(new Font("TimesRoman", Font.PLAIN, 25));

					g2.drawString(suit, 315 + compCards, 225);

					//print the value of the card on the card after getting the card value

					faceCard = cardPulled % 13;

					//calls assignCard function to get suit

					card = assignCard(faceCard);

					g.setFont(new Font("TimesRoman", Font.PLAIN, 45));

					g2.drawString(card, 335 + compCards, 260);



				}

				//removes the cards from the deck so that they cannot be reused

				deckCards.remove(cardRemove);

				//adds the values to the total

				//if it is a face card

				if (card.equals("K") || card.equals("Q") || card.equals("J")) {

					compTotal += 10;

				} 

				//if it is not a face card



				else {

					compTotal += (faceCard == 0 ? 10 : faceCard);

				}

				//updates for card spacing

				compCards += 80;



				//Creates the white space for the next card

				g2.setColor(Color.WHITE);

				g2.fillRect(315 + compCards, 205, 70, 100);

				g.setFont(new Font("TimesRoman", Font.PLAIN, 25));

				g2.drawString(suit, 315 + compCards, 225);

				g.setFont(new Font("TimesRoman", Font.PLAIN, 45));

				g2.drawString(card, 335 + compCards, 260);

			}

			//print score

			g2.setColor(Color.BLACK);

			g.setFont(new Font("TimesRoman", Font.PLAIN, 25));

			g2.drawString("Computer Score: " + compTotal, 40, 180);







			//if they tie

			if(userTotal == 21 && compTotal == 21) {

				//black screen

				g2.setColor(Color.BLACK);

				g2.fillRect(0, 0, 750, 750);

				//print tied and scores

				g2.setColor(Color.GREEN);

				g.setFont(new Font("TimesRoman", Font.PLAIN, 55));

				g2.drawString("You Tied", 200, 250);

				g.setFont(new Font("TimesRoman", Font.PLAIN, 25));

				g2.drawString("Your Score: " + userTotal, 100, 450);

				g2.drawString("Computer Score: " + compTotal, 400, 450);

				//create button for main menu

				g2.setColor(Color.red);

				g2.fillRect(mainMenuButton.x, mainMenuButton.y, mainMenuButton.width, mainMenuButton.height);

				g2.setColor(Color.black);

				g2.drawString("Main Menu", 50, 100);

			}

			else if (compTotal > 21 || userTotal>compTotal && stay && compTotal > 21) {

				// Computer loses

				//black screen

				g2.setColor(Color.BLACK);

				g2.fillRect(0, 0, 750, 750);

				//print you win and the scores

				g2.setColor(Color.GREEN);

				g.setFont(new Font("TimesRoman", Font.PLAIN, 55));

				g2.drawString("You Win!", 200, 250);

				g.setFont(new Font("TimesRoman", Font.PLAIN, 25));

				g2.drawString("Your Score: " + userTotal, 100, 450);

				g2.drawString("Computer Score: " + compTotal, 400, 450);

				//main menu button

				g2.setColor(Color.red);

				g2.fillRect(mainMenuButton.x, mainMenuButton.y, mainMenuButton.width, mainMenuButton.height);

				g2.setColor(Color.black);

				g2.drawString("Main Menu", 50, 100);

				//double balance

				balance*=2;

			}

			//if you lose because you stayed and the computer beat you

			else if(compTotal>userTotal && stay) {

				//create black screen

				g2.setColor(Color.BLACK);

				g2.fillRect(0, 0, 750, 750);

				//print you lose and the scores

				g2.setColor(Color.RED);

				g.setFont(new Font("TimesRoman", Font.PLAIN, 55));

				g2.drawString("You Lose!", 200, 250);

				g.setFont(new Font("TimesRoman", Font.PLAIN, 25));

				g2.drawString("Your Score: " + userTotal, 100, 450);

				g2.drawString("Computer Score: " + compTotal, 400, 450);

				//create the main menu button

				g2.setColor(Color.red);

				g2.fillRect(mainMenuButton.x, mainMenuButton.y, mainMenuButton.width, mainMenuButton.height);

				g2.setColor(Color.black);

				g2.drawString("Main Menu", 50, 100);

				//update balance

				balance/=2;

			}





		}

		if (mainMenu == true || rouletteChoice || slotsChoice || blackjackChoice) {

			stay = false; // Reset the stay variable to false when starting a new round or switching games

		}





	}


private String getSlotSymbol(double rand) {
	if (rand < 0.14285714285 * 1) return "∑";
	else if (rand < 0.14285714285 * 2) return "∞";
	else if (rand < 0.14285714285 * 3) return "¥";
	else if (rand < 0.14285714285 * 4) return "$";
	else if (rand < 0.14285714285 * 5) return "ø";
	else if (rand < 0.14285714285 * 6) return "π";
	else return "7";
}




@Override
public void mousePressed(MouseEvent e) {
	if (roulette.contains(getMousePosition())) {
		rouletteChoice = true;
		mainMenu = false;
		this.repaint();
	} else if (slotmachine.contains(getMousePosition())) {
		slotsChoice = true;
		mainMenu = false;
		this.repaint();
	} else if (blackjack.contains(getMousePosition())) {
		blackjackChoice = true;
		mainMenu = false;
		this.repaint();
	}
	compTotal=0;

	userTotal=0;

	userCards = 0;

	compCards = 0;

	stay = false;

	deckCards.clear();

	for(int i = 1; i<=52; i++) {

		deckCards.add((cardNum%13));

		cardNum++;

	}



	this.repaint();

	{

if(hitBox.contains(getMousePosition())) {

	hit = true;

	this.repaint();

}

else if(stayBox.contains(getMousePosition())) {

	stay = true;

	this.repaint();

}



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

	if (keyCode==KeyEvent.VK_SPACE && slotsChoice==true) {
		SLOTS_GO=true;
		repaint();
	}
	else if (keyCode == KeyEvent.VK_SPACE && rouletteChoice == true) {
		targetAngle = Math.random() * 360; // Random angle between 0 and 360
		rotationSpeed = 10;  
		isSpinning = true;
		lastTime = System.currentTimeMillis();
		updatedBalance = false; 
		timer.start(); 
	} else if (keyCode == KeyEvent.VK_R) {
		rouletteBet = "RED";
		this.repaint();

	} else if (keyCode == KeyEvent.VK_B) {
		rouletteBet = "BLACK";
		this.repaint();

	} else if (keyCode == KeyEvent.VK_G) {
		rouletteBet = "GREEN";
		this.repaint();

	}
	else if (keyCode == KeyEvent.VK_9 ) {

		balance = 0;

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













