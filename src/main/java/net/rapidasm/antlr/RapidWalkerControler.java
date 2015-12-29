package net.rapidasm.antlr;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.antlr.v4.runtime.tree.TerminalNode;

import net.rapidasm.antlr.RapidASMParser.SectionContext;
import net.rapidasm.antlr.RapidASMParser.StatementBlockContext;
import net.rapidasm.antlr.RapidASMParser.StatementContext;
import net.rapidasm.antlr.RapidASMParser.SubroutineContext;
import net.rapidasm.structure.subroutines.RapidSection;
import net.rapidasm.structure.subroutines.RapidStatement;
import net.rapidasm.structure.subroutines.RapidSubroutine;

public class RapidWalkerControler extends RapidASMBaseListener {

	public List<RapidSection> sectionsEncountered;
	
	private Stack<RapidStatementBlock> statementStack;
	private RapidSection currentSection;
	private RapidSubroutine currentSub;
	private RapidStatement currentStatement; // Is this one necessary?
	
	public RapidWalkerControler() {
		
		this.sectionsEncountered = new ArrayList<>();
		this.statementStack = new Stack<>();
		
	}
	
	@Override
	public void enterSection(SectionContext ctx) {
		
		RapidSection section = new RapidSection();
		
		this.sectionsEncountered.add(section);
		this.currentSection = section;
		
	}

	@Override
	public void exitSection(SectionContext ctx) {
		
		this.currentSection = null;
		
	}
	
	@Override
	public void enterSubroutine(SubroutineContext ctx) {
		
		RapidSubroutine sub = new RapidSubroutine();
		
		List<TerminalNode> alphaNums = ctx.ALPHANUM();
		
		String subName, subConv;
		
		if (alphaNums.size() == 2) {
			
			subName = alphaNums.get(1).getText();
			subConv = alphaNums.get(0).getText();
			
		} else if (alphaNums.size() == 1) {
			
			subName = alphaNums.get(0).getText();
			
		} else {
			throw ctx.exception;
		}
		
		sub.name = subName;
		sub.callingConvention = null; // FIXME TODO Change this.
		
		this.currentSection.addSubroutine(sub);
		this.currentSub = sub;
		
	}
	
	@Override
	public void exitSubroutine(SubroutineContext ctx) {
		this.currentSub = null;
	}
	
	@Override
	public void enterStatementBlock(StatementBlockContext ctx) {
		this.statementStack.push(new RapidStatementBlock());
	}
	
	@Override
	public void exitStatementBlock(StatementBlockContext ctx) {
		this.statementStack.pop();
	}
	
}
