package gui;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class VMWindow {
	
	/***
	 * The length of the screen in characters
	 */
	final int screenLength = 5;
	/***
	 * The height of the screen in characters
	 */
	final int screenHeight = 5;
	
	private Scene scene;
	private GridPane screenPane;
	private Label[] screenLabels;
	
	public VMWindow() {
		
		
		
		
		screenLabels = new Label[screenLength * screenHeight];
		
		
	}
	
	
}
