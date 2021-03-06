import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

public class Background extends Sprite

{

	double scrollAmountX;
	double scrollAmountY;
	
	// constructor
	public Background(Scene thisScene, String imageFile, int xSize, int ySize, double scrollX, double scrollY){
		super(thisScene, imageFile, xSize, ySize);		
		
		// this lets us manipulate scroll levels
		scrollAmountX = scrollX;
		scrollAmountY = scrollY;
		setBoundAction(CONTINUE);		
		

	} // end constructor
	
	// constructor
	public Background(Scene thisScene, String imageFile[][], int xSize, int ySize, double scrollX, double scrollY){
		super(thisScene, imageFile, xSize, ySize);		
		
		// this lets us manipulate scroll levels
		scrollAmountX = scrollX;
		scrollAmountY = scrollY;
		setBoundAction(CONTINUE);	

	} // end constructor
	
	public void update(Graphics g) {
		
		
		
		// code for testing
      //Font font = new Font("Arial", Font.PLAIN, 20);
      //g.setFont(font);
      //g.setColor(new Color(0, 0, 0));
		//g.drawString("externX: " + externX + ", externY: " + externY , 500, 100);
		
		// this tiles the background until we've at least filled our viewport
		for (int ix = 0; ix < cWidth; ix = ix + width) {
			for (int iy = 0; iy < cHeight; iy = iy + height) {
				setPosition(ix + width/2, iy + height/2);				
				super.update(g);			
			}	
		}


		
		//super.update(g, externX, externY);
	}
	
	public void update(Graphics g, int offX, int offY) {
		
		
		int externX = (int)(offX * scrollAmountX);
		int externY = (int)(offY * scrollAmountY);
		
		// code for testing
      //Font font = new Font("Arial", Font.PLAIN, 20);
      //g.setFont(font);
      //g.setColor(new Color(0, 0, 0));
		//g.drawString("externX: " + externX + ", externY: " + externY , 500, 100);
		
		// this tiles the background until we've at least filled our viewport
		for (int ix = (externX * -1); ix < cWidth; ix = ix + width) {
			for (int iy = (externY -1); iy < cHeight; iy = iy + height) {
				setPosition(ix + width/2, iy + height/2);				
				super.update(g);			
			}	
		}


		
		//super.update(g, externX, externY);
	}

}