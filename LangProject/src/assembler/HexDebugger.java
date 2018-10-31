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
	private DebugLabel selectedLabel;
	private boolean stop = false;
	
	private final EventHandler mouseHandler = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent e) {
			HexLabel src = (HexLabel) e.getSource();
			if (e.getEventType() == e.MOUSE_ENTERED) {
				if (stop) return;
				highlightLabel(src.getSource().getAddress());
				selectedLabel = src.getSource();
			} else if (e.getEventType() == e.MOUSE_CLICKED) {
				if (e.getButton() == MouseButton.PRIMARY) {
					highlightLabel(src.getSource().getAddress());
					stop = true;
				} else if (e.getButton() == MouseButton.SECONDARY) {
					stop = false;
					highlightLabel(src.getSource().getAddress());
				}
			}
		}
	};
	
	public HexDebugger(byte[] data, ArrayList<DebugLabel> labels) {
		this.data = data;
		this.labels = labels;
		this.setStyle("-fx-background-color: #000000;");
		gridLabels = new HexLabel[data.length];
		this.setMinHeight(600);
		int x = 0;
		int y = 0;
		
		for (int i=0; i<data.length; i++) {
			gridLabels[i] = newLabel(data[i], getLabelAt(i));
			this.add(gridLabels[i], x, y);
			x++;
			if (x > 35) { x = 0; y++; }
		}
		
		this.setHgap(1);
		this.setVgap(0);
		
	}
	
	@SuppressWarnings("unchecked")
	private HexLabel newLabel(byte data, DebugLabel dl) {
		HexLabel label = new HexLabel(dl);
		label.setText(Util.byteToHexString(data));
		label.setTextFill(Color.web("#FFFFFF"));
		label.setOnMouseEntered(mouseHandler);
		label.setOnMouseClicked(mouseHandler);
		return label;
	}
	
	private DebugLabel getLabelAt(int address) {
		for (DebugLabel l : labels) {
			if (l != null && l.getAddress() <= address && l.getAddress() + l.getLength() > address) return l;
		}
		return null;
	}
	
	private void resetLabels() {
		for (int i=0; i<gridLabels.length; i++) {
			gridLabels[i].setTextFill(Color.web("#FFFFFF"));
		}
	}
	
	private void highlightLabel(int address) {
		DebugLabel label = getLabelAt(address);
		if (label != null) {
			for (int i=0; i<gridLabels.length; i++) {
				HexLabel l = gridLabels[i];
				if (l.getSource().getFilename().equals(label.getFilename())) {
					l.setTextFill(Color.web("#FFFF00"));
					if (l.getSource().getType() == label.getType()) {
						l.setTextFill(Color.web("#00FF00"));
						if (i >= label.getAddress() && i < label.getAddress() + label.getLength()) {
							l.setTextFill(Color.web("#00FFFF"));
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
	
	public DebugLabel getSelectedLabel() {
		return selectedLabel;
	}
	
	
}
