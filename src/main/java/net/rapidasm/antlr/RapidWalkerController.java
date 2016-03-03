package net.rapidasm.antlr;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.antlr.v4.runtime.tree.TerminalNode;

import net.rapidasm.antlr.RapidASMParser.LabelSymbolContext;
import net.rapidasm.antlr.RapidASMParser.SectionContext;
import net.rapidasm.antlr.RapidASMParser.SkipSymbolContext;
import net.rapidasm.antlr.RapidASMParser.StatementBlockContext;
import net.rapidasm.antlr.RapidASMParser.StatementContext;
import net.rapidasm.antlr.RapidASMParser.StoreSymbolContext;
import net.rapidasm.antlr.RapidASMParser.SubroutineContext;
import net.rapidasm.antlr.RapidASMParser.SymbolContext;
import net.rapidasm.structure.RapidStatementBlock;
import net.rapidasm.structure.subroutines.RapidSection;
import net.rapidasm.structure.subroutines.RapidStatement;
import net.rapidasm.structure.subroutines.RapidSubroutine;
import net.rapidasm.structure.symbols.LabelSymbol;

public class RapidWalkerController extends RapidASMBaseListener {

	public List<RapidSection> sectionsEncountered;
	
	private Stack<RapidStatementBlock> statementStack;
	private RapidSection currentSection;
	
	private List<LabelSymbol> cachedLabels;
	
	public RapidWalkerController() {
		
		this.sectionsEncountered = new ArrayList<>();
		this.statementStack = new Stack<>();
		this.cachedLabels = new ArrayList<>();
		
	}
	
	@Override
	public void enterSection(SectionContext ctx) {
		
		RapidSection section = new RapidSection(ctx.ALPHANUM().getText());
		
		this.sectionsEncountered.add(section);
		this.currentSection = section;
		
	}

	@Override
	public void exitSection(SectionContext ctx) {
		
		this.currentSection = null;
		
	}
	
	@SuppressWarnings("unused")
	@Override
	public void enterSubroutine(SubroutineContext ctx) {
		
		RapidSubroutine sub = new RapidSubroutine();
		
		sub.name = ctx.ALPHANUM().getText();
		sub.callingConvention = null; // FIXME TODO Change this.
		
		this.currentSection.addChild(sub);
		//this.currentSub = sub;
		
	}
	
	@Override
	public void exitSubroutine(SubroutineContext ctx) {
		//this.currentSub = null;
	}
	
	@Override
	public void enterSymbol(SymbolContext ctx) {
		
		/*
		TerminalNode symbolName = ctx.WORD(0);
		
		SymbolType type = SymbolType.getTypeForName(symbolName.getText());
		String[] operands = new String[type.operandCount];
		
		RapidSymbol symb = new RapidSymbol(type, operands);
		
		// Populate the string based on the values of the symbol.
		if (type == SymbolType.SKIP) {
			
			operands[0] = ctx.WORD(1).getText();
			
		} else if (type == SymbolType.STORE) {
			
			operands[0] = ctx.VARSIZE().getText();
			operands[1] = ctx.WORD(1).getText();
			
		} else if (type == SymbolType.VALUE) {
			
			operands[0] = ctx.VARSIZE().getText();
			operands[1] = ctx.WORD(1).getText();
			operands[2] = ctx.ALPHANUM().getText();
			
		} else if (type == SymbolType.LABEL) {
			
			symb = new LabelSymbol(ctx.WORD(1).getText());
			this.cachedLabels.add((LabelSymbol) symb);
			
		}
		
		if (this.currentSub == null) {
			this.currentSection.addChild(symb);
		} else {
			throw new IllegalStateException("The symbol type " + type.name + " should not be defiend inside a subroutine!");
		}
		*/
		
	}
	
	@Override
	public void enterStoreSymbol(StoreSymbolContext ctx) {
		
		
		
	}
	
	@Override
	public void enterLabelSymbol(LabelSymbolContext ctx) {
		
		LabelSymbol ls = new LabelSymbol(ctx.ALPHANUM().getText());
		this.cachedLabels.add(ls);
		
	}

	@Override
	public void enterSkipSymbol(SkipSymbolContext ctx) {
		
	}

	@Override
	public void enterStatementBlock(StatementBlockContext ctx) {
		this.statementStack.push(new RapidStatementBlock());
	}
	
	@Override
	public void exitStatementBlock(StatementBlockContext ctx) {
		this.statementStack.pop();
	}

	@Override
	public void enterStatement(StatementContext ctx) {
		
		
		
	}

	@Override
	public void exitStatement(StatementContext ctx) {
		// TODO Auto-generated method stub
		super.exitStatement(ctx);
	}
	
}
