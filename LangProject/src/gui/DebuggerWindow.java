package gui;

import assembler.HexDebugger;
import assembler.containers.ASMObject;
import assembler.containers.DebugLabel;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class DebuggerWindow {
	
	/***
	 * Create a debugger window to view a single compiled object
	 * This will not work correctly if the provided object has not already been compiled
	 * @param obj the object to view
	 * @return a Scene object containing the debugger
	 */
	public static Scene newDebuggerWindow(ASMObject obj) {
		VBox vBox = new VBox();
		vBox.setStyle("-fx-background-color: #000000;");
		Label filenameLabel = new Label();
		filenameLabel.setTextFill(Color.web("#FFFFFF"));
		filenameLabel.setText("Filename: N/A");
		Label addressLabel = new Label();
		addressLabel.setTextFill(Color.web("#FFFFFF"));
		addressLabel.setText("Address: N/A");
		Label lengthLabel = new Label();
		lengthLabel.setTextFill(Color.web("#FFFFFF"));
		lengthLabel.setText("Length: N/A");
		Label labelLabel = new Label();
		labelLabel.setTextFill(Color.web("#FFFFFF"));
		labelLabel.setText("Label: N/A");
		Label textLabel = new Label();
		textLabel.setTextFill(Color.web("#FFFFFF"));
		textLabel.setText("Text: N/A");
		Label relocLabel = new Label();
		relocLabel.setTextFill(Color.web("#FFFFFF"));
		relocLabel.setText("Reloc: N/A");
		
		vBox.getChildren().add(filenameLabel);
		vBox.getChildren().add(addressLabel);
		vBox.getChildren().add(lengthLabel);
		vBox.getChildren().add(labelLabel);
		vBox.getChildren().add(textLabel);
		vBox.getChildren().add(relocLabel);
		
		HexDebugger debugger = new HexDebugger(obj.getCompiledObject(), obj.getDebugLabels()) {
			@Override
			protected void newSelectedLabel(DebugLabel label) { //Override the internally called method
				if (label != null) {
					filenameLabel.setText("Filename: " + label.getFilename() + " (Line " + label.getLn() + ")");
					addressLabel.setText("Address: " + label.getAddress());
					lengthLabel.setText("Length: " + label.getLength());
					labelLabel.setText("Label: " + label.getLabel());
					textLabel.setText("Text: " + label.getLineText());
					if (label.getReloc() != null) relocLabel.setText("Reloc: " + label.getReloc().toString()); else relocLabel.setText("Reloc: N/A");
				} else {
					
				}
			}
		};
		
		ScrollPane hexPane = new ScrollPane();
		hexPane.setContent(debugger);
		hexPane.setPrefSize(512, 600);
		hexPane.setStyle("-fx-border-width: 0px; -fx-border-style: none; -fx-border-insets: 0; -fx-background-color: #000000;");
		vBox.getChildren().add(hexPane);
		
		
		
		Scene scene = new Scene(vBox);
		return scene;
	}
	
}
