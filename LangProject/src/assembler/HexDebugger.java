package assembler;

import java.util.ArrayList;

import assembler.containers.DebugLabel;
import assembler.containers.HexLabel;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import main.Util;

public class HexDebugger extends GridPane {
	
	private byte[] data;
	private HexLabel[] gridLabels;
	private ArrayList<DebugLabel> labels;
	private boolean stop = false;
	
	/*
	 * Event handler for the hex labels
	 */
	private final EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent e) {
			HexLabel src = (HexLabel) e.getSource();
			if (e.getEventType() == MouseEvent.MOUSE_ENTERED) {
				if (stop) return;
				highlightLabel(src.getSource().getAddress());
				newSelectedLabel(src.getSource());
			} else if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
				if (e.getButton() == MouseButton.PRIMARY) {
					highlightLabel(src.getSource().getAddress());
					newSelectedLabel(src.getSource());
					stop = true;
				} else if (e.getButton() == MouseButton.SECONDARY) {
					stop = false;
					highlightLabel(src.getSource().getAddress());
					newSelectedLabel(src.getSource());
				}
			}
		}
	};
	
	public HexDebugger(byte[] data, ArrayList<DebugLabel> labels) {
		this.data = data;
		this.labels = labels;
		this.setStyle("-fx-border-width: 0px; -fx-border-style: none; -fx-border-insets: 0; -fx-background-color: #000000;");
		
		gridLabels = new HexLabel[data.length];
		this.setMinHeight(600);
		int x = 0;
		int y = 0;
		
		for (int i=0; i<data.length; i++) {
			gridLabels[i] = newLabel(data[i], getLabelAt(i));
			this.add(gridLabels[i], x, y);
			if (x >= 35) { x = 0; y++; } else x++;
		}
		
		this.setHgap(1);
		this.setVgap(0);
		
	}
	
	/***
	 * Create a new HexLabel
	 * @param data the byte from which to create the HexLabel
	 * @param dl the DebugLabel object associated with the HexLabel
	 * @return a HexLabel object representing the provided data and associated with the provided DebugLabel
	 */
	@SuppressWarnings("unchecked")
	private HexLabel newLabel(byte data, DebugLabel dl) {
		HexLabel label = new HexLabel(dl);
		label.setText(Util.byteToHexString(data));
		label.setTextFill(Color.web("#FFFFFF"));
		label.setOnMouseEntered(mouseHandler);
		label.setOnMouseClicked(mouseHandler);
		return label;
	}
	
	/***
	 * Get the debug label associated with the provided address
	 * @param address the address of the label
	 * @return the DebigLabel associated with the provided address (may return null)
	 */
	private DebugLabel getLabelAt(int address) {
		for (DebugLabel l : labels) {
			if (l != null && l.getAddress() <= address && l.getAddress() + l.getLength() > address) return l;
		}
		return null;
	}
	
	/***
	 * Highlight the label associated with the provided address
	 * All addresses associated with the found DebugLabel (if any) will be highlighted
	 * Highlight priority is as follows:
	 * - Addresses in the same file will be highlighted yellow
	 * - Addresses in the same section will be highlighted green
	 * - Addresses associated with the same program label will be highlighted orange
	 * - Addresses associated with the same DebugLabel will be highlighted cyan
	 * @param address The address of the label to highlight
	 */
	private void highlightLabel(int address) {
		DebugLabel label = getLabelAt(address);
		if (label != null) {
			for (int i=0; i<gridLabels.length; i++) {
				HexLabel l = gridLabels[i];
				if (l.getSource().getFilename().equals(label.getFilename())) {
					l.setTextFill(Color.web("#FFFF00"));
					if (l.getSource().getType() == label.getType()) {
						l.setTextFill(Color.web("#00FF00"));
						if (l.getSource().getLabel().equals(label.getLabel())) {
							l.setTextFill(Color.web("#FFA500"));
							if (i >= label.getAddress() && i < label.getAddress() + label.getLength()) {
								l.setTextFill(Color.web("#00FFFF"));
								continue;
							}
						continue;
						}
						continue;
					}
					continue;
				}
				l.setTextFill(Color.web("#FFFFFF"));
			}
		}
	}
	
	/***
	 * Called when a new label is selected in the debugger pane
	 * Can be overridden by a subclass to implement additional functionality
	 * @param label the label that was selected
	 */
	protected void newSelectedLabel(DebugLabel label) {}
	
	
}
