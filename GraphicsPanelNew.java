// Class: GraphicsPanel
// Written by: Mr. Swope
// Date: 12/2/15
// Description: This class is the main class for this project.  It extends the Jpanel class and will be drawn on
// 				on the JPanel in the GraphicsMain class.  
//
// Since you will modify this class you should add comments that describe when and how you modified the class.  
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;
import javax.swing.JPanel;

public class GraphicsPanel extends JPanel implements MouseListener{
		
	private final int SQUARE_WIDTH = 90;    // The width of one space on the board.  Constant used for drawing board.
	private final int OFFSET = 5;
	// private Location from;   			    //NLC
	// private Location to;   				    // no location class
	private boolean click;   				// false until the game has started by somebody clicking on the board.  should also be set to false
	                         				// after an attempted move.
	private int turn;						// used to keep track of who's turn it is - should only be 1 or 2.
	
	public GraphicsPanel(){
	
	}
	
	// method: paintComponent
	// description: This method will paint the items onto the graphics panel.  This method is called when the panel is
	//   			first rendered.  It can also be called by this.repaint()
	// parameters: Graphics g - This object is used to draw your images onto the graphics panel.
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
	
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// use math to figure out the row and column that was clicked.
		
	
	}
	
	public void printBoard(){
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}
	

}
