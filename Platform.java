import java.awt.Graphics;

public class Platform extends Sprite
{
	private boolean isLeft;
	private boolean isRight;
	private boolean isAbove;
	private boolean isBelow;



	double scrollAmount;
	
	// constructor
	public Platform(Scene thisScene, String imageFile, int xSize, int ySize){
		super(thisScene, imageFile, xSize, ySize);		
		

	} // end constructor
	
	// constructor
	public Platform(Scene thisScene, String imageFile[][], int xSize, int ySize){
		super(thisScene, imageFile, xSize, ySize);		
		

	} // end constructor
	
	// collideswith(Sprite) - checks for collision with another sprite, pushes sprite back because it's a block
	@Override	
	public boolean collidesWith(Sprite sprite) {
		// collisions only happen when both are visible
		boolean collision = false;
		
		if (isVisible) {
			if (sprite.visible()) {
				// check borders
				int left = x - (width / 2);
				int top = y - (height / 2);
				int right = x + (width / 2);
				int bottom = y + (height / 2);
				int spriteLeft = sprite.x - (sprite.width / 2);
				int spriteTop = sprite.y - (sprite.height / 2);
				int spriteRight = sprite.x + (sprite.width / 2);
				int spriteBottom = sprite.y + (sprite.height / 2);
				
				int amountLeft;
				int amountRight;
				int amountAbove;
				int amountBelow;
				
				// assume collision
				collision = true;
				
			
				// calculate amounts
				amountLeft = left - spriteRight;
				amountAbove = top - spriteBottom;
				amountRight = spriteLeft - right;
				amountBelow = spriteTop - bottom;
				
				// determine if there's a miss
				if ((left > spriteRight) || (top > spriteBottom) || (right < spriteLeft) || (bottom < spriteTop)) {
					collision = false;	
				} // end if
				
				// amountLeft is highest
				if (amountLeft > amountRight && amountLeft > amountAbove && amountRight > amountBelow) {
					isLeft = true;
					isRight = false;
					isAbove = false;
					isBelow = false;
				} else if (amountRight > amountBelow && amountRight > amountAbove) {
					isRight = true;
					isLeft = false;
					isAbove = false;
					isBelow = false;				
				} else if (amountBelow > amountAbove) {
					isBelow = true;
					isLeft = false;
					isAbove = false;
					isRight = false;				
				} else {
					isAbove = true;
					isLeft = false;
					isBelow = false;
					isRight = false;				
				}
						
						

			
				// move the sprite back
				if (collision == true) {
					if (isAbove && (top < spriteBottom)) {
						sprite.setY(sprite.y - (spriteBottom - top));
						sprite.setDY(0);				
					}
				}
				
		
			
				
			} // end if		
		} // end if
		
		return collision;
	} // end collideswith(sprite)
	
	// returns true if the bottom of one sprite is on top of a block
	public boolean standingOn(Sprite sprite) {
		boolean standing = false;
		
		int left = x - (width / 2);
		int top = y - (height / 2);
		int right = x + (width / 2);
		int spriteLeft = sprite.x - (sprite.width / 2);
		int spriteBottom = sprite.y + (sprite.height / 2);
		int spriteRight = sprite.x + (sprite.width / 2);
		
		
		// we need a NOR
		if (!((left > spriteRight) || (right < spriteLeft))) {
			if (top == spriteBottom) {
				standing = true;		
			}
		} // end if



		
		return standing;
		
	}

}