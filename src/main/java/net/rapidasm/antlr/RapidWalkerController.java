package net.rapidasm.antlr;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import net.rapidasm.MathUtils;
import net.rapidasm.Module;
import net.rapidasm.antlr.RapidASMParser.ConvDeclarationContext;
import net.rapidasm.antlr.RapidASMParser.LabelSymbolContext;
import net.rapidasm.antlr.RapidASMParser.SectionContext;
import net.rapidasm.antlr.RapidASMParser.SkipSymbolContext;
import net.rapidasm.antlr.RapidASMParser.StatementBlockContext;
import net.rapidasm.antlr.RapidASMParser.StoreSymbolContext;
import net.rapidasm.antlr.RapidASMParser.SubroutineContext;
import net.rapidasm.antlr.RapidASMParser.ValueSymbolContext;
import net.rapidasm.arch.EmptyConvention;
import net.rapidasm.structure.DataSize;
import net.rapidasm.structure.RapidStatementBlock;
import net.rapidasm.structure.subroutines.RapidSection;
import net.rapidasm.structure.subroutines.RapidSubroutine;
import net.rapidasm.structure.symbols.LabelSymbol;
import net.rapidasm.structure.symbols.SkipSymbol;
import net.rapidasm.structure.symbols.StoreSymbol;
import net.rapidasm.structure.symbols.ValueSymbol;

public class RapidWalkerController extends RapidASMBaseListener {

	private Module generatedModule;
	
	public List<RapidSection> sectionsEncountered;
	
	private Stack<RapidStatementBlock> statementStack;
	private RapidSection currentSection;
	
	private List<LabelSymbol> cachedLabels;
	
	public RapidWalkerController() {
		
		this.generatedModule = new Module();
		
		this.sectionsEncountered = new ArrayList<>();
		this.statementStack = new Stack<>();
		this.cachedLabels = new ArrayList<>();
		
	}
	
	public Module getModule() {
		return this.generatedModule;
	}
	
	@Override
	public void enterSection(SectionContext ctx) {
		
		RapidSection section = new RapidSection(this.generatedModule, ctx.ALPHANUM().getText());
		
		this.generatedModule.sections.add(section);
		this.currentSection = section;
		
	}

	@Override
	public void exitSection(SectionContext ctx) {
		
		this.currentSection = null;
		
	}
	
	@Override
	public void enterSubroutine(SubroutineContext ctx) {
		
		RapidSubroutine sub = new RapidSubroutine();
		
		sub.name = ctx.ALPHANUM().getText();
		
		ConvDeclarationContext conv = ctx.convDeclaration();
		if (conv != null) {
			sub.callingConvention = new EmptyConvention(conv.ALPHANUM().getText());
		} else {
			sub.callingConvention = new EmptyConvention("UNDEFINED");
		}
		
		this.currentSection.addChild(sub);
		
	}
	
	@Override
	public void exitSubroutine(SubroutineContext ctx) {
		
	}
	
	@Override
	public void enterLabelSymbol(LabelSymbolContext ctx) {
		this.cachedLabels.add(new LabelSymbol(ctx.ALPHANUM().getText()));
	}
	
	@Override
	public void enterStoreSymbol(StoreSymbolContext ctx) {
		this.currentSection.addChild(new StoreSymbol(this.currentSection, DataSize.getSize(ctx.VARSIZE().getText()), MathUtils.parseNumber(ctx.NUMBER().getText())));
	}
	
	@Override
	public void enterSkipSymbol(SkipSymbolContext ctx) {
		this.currentSection.addChild(new SkipSymbol(this.currentSection, (int) MathUtils.parseNumber(ctx.NUMBER().getText())));
	}
	
	@Override
	public void enterValueSymbol(ValueSymbolContext ctx) {
		this.currentSection.addChild(new ValueSymbol(this.currentSection, ctx.ALPHANUM().getText(), DataSize.getSize(ctx.VARSIZE().getText()), ctx.quantity()));
	}
	
	@Override
	public void enterStatementBlock(StatementBlockContext ctx) {
		this.statementStack.push(new RapidStatementBlock(this.statementStack.peek()));
	}
	
	@Override
	public void exitStatementBlock(StatementBlockContext ctx) {
		this.statementStack.pop();
	}
	
}
