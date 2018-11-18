package main;

import java.io.IOException;

import assembler.Assembler;
import assembler.HexDebugger;
import assembler.containers.ASMObject;
import gui.DebuggerWindow;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class Main extends Application {
	
	
	
	public static void main(String[] args) throws IOException {
		//System.out.println(Util.byteArrayToBinaryString(DataLexer.parseSWord("257", 0, true)));
		//System.out.println(Util.byteArrayToHexString(DataLexer.parseSWord("257", 0, true), 5));
		//ParsedInstruction p = InstructionParser.parseLDI("LDI $r1 label".split(" "), 0);
		//System.out.println(Util.byteArrayToHexString(p.getChunk(),10));
		//System.out.println(p.getReloc());
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		Assembler asm = new Assembler("source/Program.asm");
		asm.parse();
		asm.compile();
		
		//ProjectBuilder.build("source");
		
		stage.setScene(DebuggerWindow.newDebuggerWindow(asm.getObject()));
		
		
		stage.setResizable(false);
		stage.show();
	}


	
	
	
	
	
	
}
