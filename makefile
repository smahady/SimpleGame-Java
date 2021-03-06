SimpleGameDemo.class: SimpleGameDemo.java GameLevel.class GameLevel2.class Demo.class Scene.class Background.class Block.class Sprite.class Sound.class Ramp.class Stairs.class Platform.class astroidsClone.class
	javac -d . SimpleGameDemo.java 
GameLevel.class: GameLevel.java Demo.class Scene.class Sprite.class
	javac -d . GameLevel.java
GameLevel2.class: GameLevel2.java Demo.class Scene.class Sprite.class
	javac -d . GameLevel.java
Demo.class: Demo.java Scene.class Sprite.class
	javac -d . Demo.java
astroidsClone.class: astroidsClone.java Scene.class Sprite.class
	javac -d . astroidsClone.java
Background.class: Background.java Sprite.class
	javac -d . Background.java
Block.class: Block.java Sprite.class
	javac -d . Block.java
Scene.class: Scene.java Sound.class Joystick.class
	javac -d . Scene.java 
Ramp.class : Ramp.java Sprite.class
	javac -d . Ramp.java
Platform.class: Platform.java Sprite.class
	javac -d . Platform.java 
Stairs.class : Stairs.java Sprite.class
	javac -d . Stairs.java
Sprite.class: Sprite.java
	javac -d . Sprite.java
Sound.class: Sound.java
	javac -d . Sound.java
Joystick.class:
	javac -d . Joystick.java
clean:
	rm -f *.class
run: SimpleGameDemo.class
	java -Dsun.java2d.opengl=true SimpleGameDemo 

