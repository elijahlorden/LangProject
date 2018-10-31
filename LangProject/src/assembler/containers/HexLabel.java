package assembler.containers;

import javafx.scene.control.Label;

public class HexLabel extends Label {
	private DebugLabel sourceLabel;
	
	public HexLabel(DebugLabel sourceLabel) {
		this.sourceLabel = sourceLabel;
	}
	
	public DebugLabel getSource() {
		return sourceLabel;
	}
	
	
}
