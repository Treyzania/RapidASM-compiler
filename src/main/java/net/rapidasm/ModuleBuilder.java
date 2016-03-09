package net.rapidasm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import net.rapidasm.antlr.RapidASMLexer;
import net.rapidasm.antlr.RapidASMParser;
import net.rapidasm.antlr.RapidWalkerController;

public class ModuleBuilder {

	public ModuleBuilder() {
		
	}
	
	public Module createModule(String filename) {
		return this.createModule(new File(filename));
	}
	
	public Module createModule(File file) {
		
		try {
			
			FileInputStream fis = new FileInputStream(file);
			byte[] data = new byte[(int) file.length()];
			fis.read(data);
			fis.close();
			
			String raw = new String(data);
			String preprocessed = raw.replace("\r", ""); // Ughh windows...
			
			ANTLRInputStream stream = new ANTLRInputStream(preprocessed);
			
			// Basic pipeline setup.
			RapidASMLexer lexer = new RapidASMLexer(stream);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			RapidASMParser parser = new RapidASMParser(tokens);
			parser.setTrace(false);
			
			// Walker setup & walk.
			ParseTreeWalker walker = new ParseTreeWalker();
			RapidWalkerController ctrl = new RapidWalkerController(file);
			walker.walk(ctrl, parser.module());
			
			// Get the populated module.
			return ctrl.getModule();
			
		} catch (IOException e) {
			
			e.printStackTrace();
			System.exit(-1);
			
			// Probably would never get to this point, but we'll return null for good measure.
			return null;
			
		}
		
	}
	
}
