import java.awt.Graphics;

public class Ramp extends Sprite
{
	private boolean isLeft;
	private boolean isRight;
	private boolean isAbove;
	private boolean isBelow;
	private boolean slopeRight;


	double scrollAmount;
	
	// constructor
	public Ramp(Scene thisScene, String imageFile, int xSize, int ySize, boolean slope){
		super(thisScene, imageFile, xSize, ySize);		
		
		slopeRight = slope;

	} // end constructor
	
	
	// constructor
	public Ramp(Scene thisScene, String imageFile[][], int xSize, int ySize, boolean slope){
		super(thisScene, imageFile, xSize, ySize);		
		
		slopeRight = slope;

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
				int right = x + (width / 2);
				int bottom = y + (height / 2);
				int topBarrier = y - (height/2);
				int spriteLeft = sprite.x - (sprite.width / 2);
				int spriteTop = sprite.y - (sprite.height / 2);
				int spriteRight = sprite.x + (sprite.width / 2);
				int spriteBottom = sprite.y + (sprite.height / 2);
				
				
				
				int diffX = x - sprite.x;
				double m = height / width;
									
				// reverse if the upward direction is left
				if (slopeRight == false) {
					diffX = diffX * -1;					
				}			
				
				int top = (int)(m * diffX + y);					

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
				if ((left > spriteRight) || ((top > spriteBottom) || (topBarrier > spriteBottom)) || (right < spriteLeft) || (bottom < spriteTop)) {
					collision = false;	
				} // end if
				
				// amiuntLeft is highest
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
					if (isBelow && (bottom > spriteTop)) {
						sprite.setY(sprite.y + (bottom - spriteTop));
						sprite.setDY(0);				
					}
					else if (isAbove && (top < spriteBottom && topBarrier < spriteBottom)) {
						sprite.setY(sprite.y - (spriteBottom - top));
						sprite.setDY(0);				
					}
					else if (isLeft && (left < spriteRight)) {
						sprite.setX(sprite.x - (spriteRight - left));
						sprite.setDX(0);				
					}
					else if (isRight && (right > spriteLeft)) {
						sprite.setX(sprite.x + (right - spriteLeft));	
						sprite.setDX(0);			
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
		int right = x + (width / 2);
		int spriteLeft = sprite.x - (sprite.width / 2);
		int spriteBottom = sprite.y + (sprite.height / 2);
		int spriteRight = sprite.x + (sprite.width / 2);

		int diffX = x - sprite.x;
		double m = height / width;
							
		// reverse if the upward direction is left
		if (slopeRight == false) {
			diffX = diffX * -1;					
		}			
		
		int top = (int)(m * diffX + y);					
		
		
		// we need a NOR
		if (!((left > spriteRight) || (right < spriteLeft))) {
			if (top == spriteBottom) {
				standing = true;		
			}
		} // end if



		
		return standing;
		
	}

}