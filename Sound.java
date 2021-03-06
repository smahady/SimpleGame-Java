import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;
//import javax.media.*;

public class Sound extends JFrame
{
	//Create a new sound to be played. Only does wav files
	private Clip soundClip;
	public Sound(String soundSource)
	{
		//Initialize sound source
		try
		{
			File soundFile = new File(soundSource);
			AudioInputStream audio = AudioSystem.getAudioInputStream(soundFile);
			soundClip = AudioSystem.getClip();
			soundClip.open(audio);
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
		}
	}
	
	public void playSound()
	{
		//Play sound
		try
		{
			soundClip.start();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void playSound(int n)
	{
		//Loop playing sounds x times, for whatever reason
		if(n >= 1)
			soundClip.loop(n);
		else
			soundClip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void stopSound()
	{
		//Stop sounds.
		if(soundClip.isRunning())
			soundClip.stop();
	}
	
	/*
	public static void main(String[] args)
	{
		Sound beh = new Sound("Laser1.wav");
		beh.playSound();
	}
	*/
}