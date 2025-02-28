// Class: GraphicsPanel
// Written by: Mr. Swope
// Date: 12/2/15
// Description: This class is the main class for this project.  It extends the Jpanel class and will be drawn on
// 				on the JPanel in the GraphicsMain class.  
//
// Since you will modify this class you should add comments that describe when and how you modified the class.  
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.Image;

public class GraphicsPanel extends JPanel implements MouseListener{

	private final int SQUARE_WIDTH = 90;    // The width of one space on the board.  Constant used for drawing board.
	private final int OFFSET = 5;
	private boolean click;   				// false until the game has started by somebody clicking on the board.  should also be set to false
	private Rectangle roulette;  
	private Rectangle slotmachine;      
	private Rectangle blackjack;  
	private Image rouletteWheelImage;
	boolean mainMenu = true;
	boolean rouletteChoice = false;
	boolean slotsChoice = false;
	boolean blackjackChoice = false;


	public GraphicsPanel(){
		
	
		setPreferredSize(new Dimension(SQUARE_WIDTH*8+OFFSET*2,SQUARE_WIDTH*8+OFFSET*2));   // Set these dimensions to the width 	
		addMouseListener(this);
		roulette = new Rectangle(100, 500, 100,100);
		slotmachine = new Rectangle(300, 500, 100,100);
		blackjack = new Rectangle(500, 500, 100,100);
	
		
		
	}

	// method: paintComponent
	// description: This method will paint the items onto the graphics panel.  This method is called when the panel is
	//   			first rendered.  It can also be called by this.repaint()
	// parameters: Graphics g - This object is used to draw your images onto the graphics panel.
	public void paintComponent(Graphics g){
		
		Color darkRed = new Color(148, 34, 40); // Color dark red
		Graphics2D g2 = (Graphics2D) g;
		//if mainMenu is true, and this.repaint is triggered, will print the main menu.
		if (mainMenu == true) {
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
			
			
		}

		//if roulette is true, and this.repaint is triggered, will make the design for the roulette game
		if (rouletteChoice) {

			g.setFont(new Font("TimesRoman", Font.PLAIN, 50)); 
			g2.drawString("ROULETTE", 250, 100);
			g2.setColor(Color.white);
			g2.fillRect(-10, -10, 100000, 10000);
			
			ClassLoader cldr = this.getClass().getClassLoader();	// These five lines of code load the background picture.
			String imagePath = "images/rouletteWheel.png";				// Change this line if you want to use a different 
			URL imageURL = cldr.getResource(imagePath);				// background image.  The image should be saved in the
			ImageIcon image = new ImageIcon(imageURL);				// images directory.
			image.paintIcon(this, g2, 300, 300);
			System.out.println(image);
			System.out.println(imageURL);
		}

	}


	@Override
	public void mousePressed(MouseEvent e) {
		// use math to figure out the row and column that was clicked.
		if (roulette.contains(getMousePosition())) {
			rouletteChoice = true;
			mainMenu = false;
			this.repaint();
		}
		else if (slotmachine.contains(getMousePosition())) {
			slotsChoice = true;
			mainMenu = false;
			this.repaint();
		}
		else if (blackjack.contains(getMousePosition())) {
			blackjackChoice = true;
			mainMenu = false;
			this.repaint();
		}

	}

	public void printBoard(){

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		rouletteChoice = false;
		mainMenu = true;
		this.repaint();

	}


}
