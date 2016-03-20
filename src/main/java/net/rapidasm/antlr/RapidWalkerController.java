package net.rapidasm.antlr;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import net.rapidasm.MathUtils;
import net.rapidasm.Module;
import net.rapidasm.antlr.RapidASMParser.ConditionalBlockContext;
import net.rapidasm.antlr.RapidASMParser.ConvDeclarationContext;
import net.rapidasm.antlr.RapidASMParser.InstructionContext;
import net.rapidasm.antlr.RapidASMParser.LabelSymbolContext;
import net.rapidasm.antlr.RapidASMParser.NumericDereferenceContext;
import net.rapidasm.antlr.RapidASMParser.NumericImmediateContext;
import net.rapidasm.antlr.RapidASMParser.NumericRelativeDereferenceContext;
import net.rapidasm.antlr.RapidASMParser.NumericSubroutineInvocationContext;
import net.rapidasm.antlr.RapidASMParser.SectionContext;
import net.rapidasm.antlr.RapidASMParser.SkipSymbolContext;
import net.rapidasm.antlr.RapidASMParser.StatementBlockContext;
import net.rapidasm.antlr.RapidASMParser.StoreSymbolContext;
import net.rapidasm.antlr.RapidASMParser.SubroutineContext;
import net.rapidasm.antlr.RapidASMParser.ValueSymbolContext;
import net.rapidasm.antlr.RapidASMParser.VarargContext;
import net.rapidasm.arch.Architecture;
import net.rapidasm.arch.CallingConvention;
import net.rapidasm.structure.DataSize;
import net.rapidasm.structure.Operand;
import net.rapidasm.structure.RapidInstructionStatement;
import net.rapidasm.structure.RapidSection;
import net.rapidasm.structure.RapidStatementBlock;
import net.rapidasm.structure.RapidSubroutine;
import net.rapidasm.structure.Vararg;
import net.rapidasm.structure.conditionals.RapidIfStatement;
import net.rapidasm.structure.symbols.LabelSymbol;
import net.rapidasm.structure.symbols.SkipSymbol;
import net.rapidasm.structure.symbols.StoreSymbol;
import net.rapidasm.structure.symbols.ValueSymbol;

public class RapidWalkerController extends RapidASMBaseListener {

	private Module generatedModule;
	private final Architecture architecture;
	
	public List<RapidSection> sectionsEncountered;
	
	private Stack<RapidStatementBlock> statementStack;
	private RapidSection currentSection;
	private RapidSubroutine currentSub;
	
	private RapidInstructionStatement currentInstructionStatement;
	
	private List<Operand> cachedOperands;
	private List<LabelSymbol> cachedLabels;
	
	public RapidWalkerController(File file, Architecture arch) {
		
		this.generatedModule = new Module(file);
		this.architecture = arch;
		
		this.sectionsEncountered = new ArrayList<>();
		this.cachedLabels = new ArrayList<>();
		this.resetOperands();
		
	}
	
	public Module getModule() {
		return this.generatedModule;
	}
	
	public Architecture getArchitecture() {
		return architecture;
	}
	
	private void resetOperands() {
		this.cachedOperands = new ArrayList<>();
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
		
		this.statementStack = new Stack<>();
		String name = ctx.ALPHANUM().getText();
		
		ConvDeclarationContext conv = ctx.convDeclaration();
		CallingConvention cc = this.architecture.getCallingConvention(conv != null ? conv.ALPHANUM().getText() : "nocall");
		
		RapidSubroutine sub = new RapidSubroutine(name, cc); 
		
		this.currentSection.addChild(sub);
		this.currentSub = sub;
		
	}
	
	@Override
	public void enterVararg(VarargContext ctx) {
		
		String name = ctx.ALPHANUM().getText();
		DataSize size = DataSize.getSize(this.architecture, ctx.VARSIZE().getText());
		
		this.currentSub.signature.addVararg(new Vararg(name, size));
		
	}

	@Override
	public void exitSubroutine(SubroutineContext ctx) {
		
		this.currentSub = null;
		this.statementStack = null;
		
	}
	
	@Override
	public void enterLabelSymbol(LabelSymbolContext ctx) {
		this.cachedLabels.add(new LabelSymbol(ctx.ALPHANUM().getText()));
	}
	
	@Override
	public void enterStoreSymbol(StoreSymbolContext ctx) {
		this.currentSection.addChild(new StoreSymbol(this.currentSection, DataSize.getSize(this.architecture, ctx.VARSIZE().getText()), MathUtils.parseNumber(ctx.NUMBER().getText())));
	}
	
	@Override
	public void enterSkipSymbol(SkipSymbolContext ctx) {
		this.currentSection.addChild(new SkipSymbol(this.currentSection, (int) MathUtils.parseNumber(ctx.NUMBER().getText())));
	}
	
	@Override
	public void enterValueSymbol(ValueSymbolContext ctx) {
		this.currentSection.addChild(new ValueSymbol(this.currentSection, ctx.ALPHANUM().getText(), DataSize.getSize(this.architecture, ctx.VARSIZE().getText()), ctx.quantity()));
	}
	
	@Override
	public void enterStatementBlock(StatementBlockContext ctx) {
		
		RapidStatementBlock block = null;
		
		if (!this.statementStack.isEmpty()) {
			
			RapidStatementBlock parent = this.statementStack.peek();
			
			block = new RapidStatementBlock(parent);
			parent.addStatement(block);
			
		} else {
			
			block = new RapidStatementBlock(this.currentSub);
			this.currentSub.statementBlock = block;
			
		}
		
		this.statementStack.push(block);
		
	}
	
	@Override
	public void exitStatementBlock(StatementBlockContext ctx) {
		this.statementStack.pop();
	}
	
	@Override
	public void enterConditionalBlock(ConditionalBlockContext ctx) {
		
		RapidStatementBlock parent = this.statementStack.peek();
		RapidIfStatement statement = new RapidIfStatement(parent, ctx);
		
		this.statementStack.push(statement.getBody());
		
	}

	@Override
	public void enterInstruction(InstructionContext ctx) {
		
		RapidStatementBlock block = this.statementStack.peek();
		this.currentInstructionStatement = new RapidInstructionStatement(block, ctx.ALPHANUM().getText());
		
		this.resetOperands();
		block.addStatement(this.currentInstructionStatement);
		
	}

	@Override
	public void enterNumericDereference(NumericDereferenceContext ctx) {
		
		Operand.PointerDereferenceOperand pdo = new Operand.PointerDereferenceOperand(this.architecture, this.currentSub, ctx.numericImmediate().getText()); // TODO
		this.cachedOperands.add(pdo);
		
	}

	@Override
	public void enterNumericRelativeDereference(NumericRelativeDereferenceContext ctx) {
		
		int offset = Integer.getInteger(ctx.plusMinus().getText() + ctx.NUMBER().getText());
		
		Operand.PointerDereferenceOperand pdo = new Operand.PointerDereferenceOperand(this.architecture, this.currentSub, ctx.numericImmediate().getText(), offset);
		this.cachedOperands.add(pdo);
		
	}

	@Override
	public void enterNumericSubroutineInvocation(NumericSubroutineInvocationContext ctx) {
		
		if (this.currentInstructionStatement != null) {
			
			throw new RuntimeException("this isn't supposed to happen!");
			
		}
		
	}

	@Override
	public void enterNumericImmediate(NumericImmediateContext ctx) {
		
		Operand.ImmediateOperand io = new Operand.ImmediateOperand(this.architecture, ctx.getText());
		this.cachedOperands.add(io);
		
	}

	@Override
	public void exitInstruction(InstructionContext ctx) {
		
		this.currentInstructionStatement.setOperands(this.cachedOperands);
		this.resetOperands();
		this.currentInstructionStatement = null;
		
	}
	
}
