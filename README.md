# SimpleGame-Java

by Sean Mahady and Phillip Varble

https://github.com/smahady/SimpleGame-Java/

#SimpleGame-Java has a few basic goals:


-to use Java AWT and Swing to implement a fully usable version of Simple Game as close to simpleGame.js as possible
- to add new classes and functions to make it easier to build scolling levels

#Changes from simpleGame.js:

- non .wav support is limited at this time
- instead of implementing the update() method, the update(Graphics g) method must be implemented. Also, the class for the Panel the game is set to take place in must inherit Scene
- new classes are added! See below:


#The Scene Class

Accessible Properties:
height – the height of the Scene. This does not need to be the same as the viewport.
width – the width of the Scene. This does not need to be the same as the viewport.
keysDown – An array containing keys that are pressed. Will include virtual keys if the virtual joystick is used. 
	Keys include: K_ESC, K_SPACE , K_PGUP,  K _PG_DOWN, K_END, K_HOME, K_LEFT, K_UP, K_DOWN, K_0(-9), K_A(-Z)

Methods:
Scene() - base constructor. Does not need to be called. Automatically sets up a Jpanel with basic properties.
Scene(xSize, ySize) – Typically called with Super(xSize, ySize). Sets the size of our viewport in pixels. 
start() - Starts the Scene calling the update method. 
stop() - Stops animation and movement within the scene. 
setBG(color) – sets the background to a Color object.
changeBoundSize(x,y) – Changes the Boundaries to any x and y- including far larger than the standard viewport! This allows you to use offsets to draw a larger area than the Scene. 


Things not written in:
clear() - not needed. Action taken automatically by Swing’s Jpanel. 

#The Sprite Class

Accessible Properties:
x – the Sprite’s x position. 
y – the sprite’s y position.
drawX – lets us know the position onscreen of the Sprite, including factored offsets, for scrolling games
drawY – lets us know the position onscreen of the Sprite, including factored offsets, for scrolling games
dx – the movement of the sprite in respect to x
dy – the movement of the sprite in respect to y
scene – a reference to the scene the sprite exists in
height – the height of the sprite
width – the width of the sprite


Methods:
Sprite(scene, imageFile, xSize, ySize) – initializes the sprite, automatically loading the image. NEW: As a bonus, it redraws the sprite if stretched or compressed, saving on compression time later. 
Sprite(scene, imageFile[][], xSize, ySize) – NEW! accepts an array of strings for the imagefile. This could be done to load a non-spritesheet as a spritesheet, but more commonly to load a massive sprite from pieces, in case you wanted to handle something very large with one collision check. Very useful with the Block and Platform elements to make floors and walls. 
Sprite(scene, image, xSize, ySize) – NEW! Accepts a BufferedImage
loadAnimation(sheetWidth, sheetHeight, cellWidth, cellHeight) – Sets variables for a spriteSheet. In the future might jump automatically to generating cycles, but for now trying to keep this as close as possible to the javascript version.
 generateAnimationCycles() - slices a spritesheet up into animations. Only handles rows as states, and columns as cells… for now.
renameCycles(cycleNameArray) – allows the cycles of a spritesheet to be named by strings
setAnimationSpeed(speed) – sets the speed of the animation timer for a sprite in milliseconds
setCurrentCycle(cycleName) – sets an animation cycle to a previously assigned name
setCurrentCycle(cycleState) – sets animation cycles by number (in case you never assigned names) 
playAnimation() - begins a sequence of animations
pauseAnimation() - pauses the animation sequence
changeImage(fileName) – loads a new image
setImage(fileName) – loads a new image
hide() -  removes a sprite from visibility and collision 	
show() - reverses hide()
report() - handy for debugging, prints out info about a sprite
speed() - returns the speed of a sprite, with respect to whatever angle it is moving at
setPosition(xNew, yNew) – moves a sprite to a position.
setCPos(xNew, yNew) – NEW! When dealing with sprites of different sizes, finding it from the center like in other methods is fine, but for placing elements, especially environmental ones, it’s a pain, so this let’s us set the left and top-most corners!
setX(xNew) – moves a sprite to an x position
setY(yNew) – moves a sprite to a y coord
setDX(dxNew) – adds horizontal movement to a sprite. Negative is to the left, positive is to the right.
setDY(dyNew) – adds vertical movement to a sprite. Negative is to the top, positive is to the bottom.
setSpeed(speed in pixels) – sets the speed.
changespeedBy(speed) – changes the speed by an amount
setImgAngle(degrees) – sets the angle an image is drawn at
checkBounds() - checks if a sprite is off the Scene. New! This works if a scene is larger than the viewport, so don’t be shy with it! :)
getGraphics() - returns the graphics context of the sprite. Only works on the source image in the file, not the cells of the sprite sheet. Could be used to make one sprite write to another by passing this g to the sprite’s update method. 
getImgAngle() - returns the angle of the image
setMoveangle(degrees) sets a moetion angle without rotation angle
getMoveAngle() - returns the movement angle of the sprite in degrees
setAngle(degees) – sets both the image and motion angle. 
addVector(degrees, thrust) – sets motion based on thrust and angle in DX and DY. 
setBoundAction(action) – sets the bounding action to WRAP, BOUNCE, STOP, DIE, CONTINUE
collidesWith(sprite) – checks if the sprite collides with another sprite and returns true if so. NEW! Many inherited Sprite classes have abilities that can respond to player or enemy sprites colliding with them, to act as barriers or platforms. 
visible() - returns true if sprite is visible
distanceTo(sprite) – tells the distance between two sprites.
angleTo(sprite) – returns the angle, ind egrees, of one sprite to another
update(g) – updates movement and drawing	
update(g, offsetX, offsetY) – updates movement and drawing, but adjusts to offsets on the X and Y for scrolling

#The Background Class

The background class inherits most of the Sprite class. It is distinct in that it is assumed to not be moving, and tiles automatically. This works well with the Sprite’s tiled constructor. It is also assumed that it may scroll at rates different than the player’s layer. 

Methods:
Background(scene,imageFile, xSize, ySize, scrollX, scrollY) – scrollX and scrollY are doubles that adjust the background to faster or slower. Fractions of a pixel are best for further back, and multiples of a pixel make for foregrounds. 
Background(scene, imageFile[][], xSize, ySize, scrollX, scrollY) – tiled version of the constructor. 
update(g) – prints the background, repeating. Ignores positioning for the sprite.
update(g, offetX, offsetY) – prints the background respecting the offsets, and it’s scroll adjustments. Repeats the background.

#The Block Class

The Block class is designed to stop sprites from crossing it… assuming its collidesWith method is used. 

Methods:
Block(scene, imageFile, xSize, ySize) – like the Sprite constructor
Block(scene, imageFile[][], xSize, ySize) – like the Sprite’s tiled constructor
collideswith(Sprite) – tracks one sprite and ensures it cannot cross. Useful for RPGs as things you must walk around, and platformers as things to stand on.  
standingOn(sprite) – returns true of a sprite is standing on a block. Useful to change state. 

#The Platform Class

The Platform class is mostly useful for platformers. It only stops falling, but let’s you jump onto it.  

Methods:
Platform(scene, imageFile, xSize, ySize) – like the Sprite constructor
Platform(scene, imageFile[][], xSize, ySize) – like the Sprite’s tiled constructor
collideswith(Sprite) – tracks one sprite and ensures it cannot move downward. Only can do one at a time (for now).   
standingOn(sprite) – returns true of a sprite is standing on a platform. Useful to change state.
The Ramp Class

The Ramp class is good to make more advanced looking platformers that have non-horizontal surfaces to walk on.  

Methods:
Ramp(scene, imageFile, xSize, ySize, direction) – like the Sprite constructor, except it let’s you know if it’s up-left or up-right
Ramp(scene, imageFile[][], xSize, ySize, direction) – like the Sprite’s tiled constructor
collideswith(Sprite) – tracks one sprite and ensures it cannot move across the Ramp. Only can do one at a time (for now).   
standingOn(sprite) – returns true of a sprite is standing on a ramp. Useful to change state. 

The Stairs Class

The Stairs class is simlar to both the Ramp class and the Platform class, a diagonal surface to stand on.   

Methods:
Stairs(scene, imageFile, xSize, ySize, direction) – like the Sprite constructor, except it let’s you know if it’s up-left or up-right
Stairs(scene, imageFile[][], xSize, ySize, direction) – like the Sprite’s tiled constructor
collideswith(Sprite) – tracks one sprite and ensures it cannot move across the Ramp. Only can do one at a time (for now).   
standingOn(sprite) – returns true of a sprite is standing on the stairs. Useful to change state. 

#The Sound Class
The sound class is an object that takes some string, relates it to an input audio file it can use, then is able to play the audio clip on demand once, on a loop or a set amount of iterations, and can stop the sound any time. Currently, the only supported sound format is .wav.
Properties:
(private) soundClip – Object that has the audio file
Methods:
void Sound(string) – Constructor to Sound. Initializes soundClip by reading the input file and processing it for soundClip to use.
void playSound(void) – Plays the sound once
void playSound(int) – Plays the sound n times. If 0 or negative, plays sound endlessly
void stopSound(void) – Stops the audio clip from playing and/or, as above, from looping
Joystick.java
The joystick class is one that adds a virtual joystick on top of the game scene. It does this by adding two sprites, a bottom circle sprite and top circle sprite, puts them on the game scene. A jpanel is created and added to the game scene as the hitbox for the joystick. To simulate a real joystick, the middle of the top circle can never go over the radius of the bottom circle. The joystick can rely two types of information: the distance from the joystick to the mouse and the distance from the middle of the top circle from the distance of the bottom circle. This allows an analogue behavior, similar to a real joystick.
Properties:
protected Sprite top - Top circle sprite. Moves around similar to a real joystick.
protected Sprite bottom - Bottom circle sprite. Acts as bounds to top.
JPanel joyPanel - The hitbox detector for the mouse. Used by scene to implement mouse action listeners.
protected int posX - Joystick horizontal position relative to the game scene.
protected int posY - Joystick vertical position relative to the game scene.
protected Scene parentScene – Used for telling joystick who it is being used by.
Methods:
Void Joystick(Scene) – Constructor of joystick. Takes the game scene and attaches the aforementioned sprites to it, then adds a JPanel hitbox to the game scene.
Void Update(Graphics) – Calls update on sprites. If the game scene tells it it’s has been pressed upon, it does some trigonometry to determine where the top joystick should be, where the middle of the top sprite must not go further than the bottom sprites radius. Then updates bottom and top sprites respectively, using the graphics parameter. See sprite.update for details.
Void setPosition(int, int) – Sets a new position for the joystick, used by both sprites and jpanel.
int getMouseDiffX() – Gets horizontal differential information from mouse to joystick.
int getMouseDiffY() – Gets vertical differential information from mouse to joystick.
int getJoyDiffX() – Gets horizontal differential information from the middle of top sprite to middle of bottom sprite (or joystick hitbox). The maximum will be radius of bottom sprite, minimum is negative radius of bottom, simulating analogue control. No changes returns 0.
int getJoyDiffY() – Gets vertical differential information from the middle of the top sprite to middle of bottom sprite(or joystick hitbox). The maximum will be radius of bottom sprite, minimum is negative radius of bottom, simulating analogue control. No changes returns 0.

#Software Engineering 
Sean wrote:
	Scene.java
	Sprite.java
	Block.java
	Ramp.java
	Platform.java
	Stairs.java
	Demo.java
Phillip wrote:
	Sound.java
	Joystick,java
	astroids.java
State Transition Diagram 
(for the cat demo)
Demo()---→ update(g)---→cat.update(g)
			----->blocks[*].update(g);
			–---→checkKeys()
			----→ checkMove(); ---→ endgame conditions
		^--------------------|			
User Instructions – Unzip in a directory and type “make run”

