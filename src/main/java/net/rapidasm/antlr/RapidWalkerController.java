package net.rapidasm.antlr;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import net.rapidasm.MathUtils;
import net.rapidasm.Module;
import net.rapidasm.antlr.RapidASMParser.BooleanExpressionContext;
import net.rapidasm.antlr.RapidASMParser.ConditionalBlockContext;
import net.rapidasm.antlr.RapidASMParser.ConditionalHeaderContext;
import net.rapidasm.antlr.RapidASMParser.ConvDeclarationContext;
import net.rapidasm.antlr.RapidASMParser.InstructionContext;
import net.rapidasm.antlr.RapidASMParser.LabelSymbolContext;
import net.rapidasm.antlr.RapidASMParser.NumericDereferenceContext;
import net.rapidasm.antlr.RapidASMParser.NumericImmediateContext;
import net.rapidasm.antlr.RapidASMParser.NumericRelativeDereferenceContext;
import net.rapidasm.antlr.RapidASMParser.NumericSubroutineInvocationContext;
import net.rapidasm.antlr.RapidASMParser.SectionContext;
import net.rapidasm.antlr.RapidASMParser.SkipSymbolContext;
import net.rapidasm.antlr.RapidASMParser.StatementBlockStatementContext;
import net.rapidasm.antlr.RapidASMParser.StoreSymbolContext;
import net.rapidasm.antlr.RapidASMParser.SubroutineContext;
import net.rapidasm.antlr.RapidASMParser.ValueSymbolContext;
import net.rapidasm.antlr.RapidASMParser.VarargContext;
import net.rapidasm.antlr.RapidASMParser.WhileBlockContext;
import net.rapidasm.arch.Architecture;
import net.rapidasm.arch.CallingConvention;
import net.rapidasm.structure.DataSize;
import net.rapidasm.structure.DataType;
import net.rapidasm.structure.Operand;
import net.rapidasm.structure.RapidInstructionStatement;
import net.rapidasm.structure.RapidSection;
import net.rapidasm.structure.RapidStatementBlock;
import net.rapidasm.structure.RapidSubroutine;
import net.rapidasm.structure.RapidWhileBlock;
import net.rapidasm.structure.Vararg;
import net.rapidasm.structure.conditionals.BranchGenerationType;
import net.rapidasm.structure.conditionals.Conditional;
import net.rapidasm.structure.conditionals.Likelihood;
import net.rapidasm.structure.conditionals.RapidIfStatement;
import net.rapidasm.structure.symbols.RapidLabel;
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
	private boolean suppressNumericImmediateGeneration = false;
	
	private List<RapidLabel> cachedLabels;
	
	public RapidWalkerController(Architecture arch) {
		
		this.generatedModule = new Module(arch);
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
	
	private RapidStatementBlock getCurrentBlock() {
		return this.statementStack.peek();
	}
	
	private void pushBlock(RapidStatementBlock block) {
		
		this.getCurrentBlock().addStatement(block);
		this.statementStack.push(block);
		
	}
	
	private void popBlock() {
		this.statementStack.pop();
	}
	
	private Likelihood getLikelihood(String name, Class<? extends RapidStatementBlock> clazz) {
		
		for (Likelihood l : Likelihood.values()) {
			if (l.key.equals(name)) return l;
		}
		
		return this.generatedModule.getDefaulyLikelihood(clazz);
		
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
		
		RapidSubroutine sub = new RapidSubroutine(this.currentSection, name, cc); 
		this.statementStack.push(new RapidStatementBlock(sub));
		sub.statementBlock = this.getCurrentBlock();
		
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
		this.cachedLabels.add(new RapidLabel(ctx.ALPHANUM().getText()));
	}
	
	@Override
	public void enterStoreSymbol(StoreSymbolContext ctx) {
		this.currentSection.addChild(new StoreSymbol(this.currentSection, DataType.getType(this.architecture, ctx.VARSIZE().getText()), MathUtils.parseNumber(ctx.NUMBER().getText())));
	}
	
	@Override
	public void enterSkipSymbol(SkipSymbolContext ctx) {
		this.currentSection.addChild(new SkipSymbol(this.currentSection, (int) MathUtils.parseNumber(ctx.NUMBER().getText())));
	}
	
	@Override
	public void enterValueSymbol(ValueSymbolContext ctx) {
		this.currentSection.addChild(new ValueSymbol(this.currentSection, ctx.ALPHANUM().getText(), DataType.getType(this.architecture, ctx.VARSIZE().getText()), ctx.quantity()));
	}
	
	@Override
	public void enterStatementBlockStatement(StatementBlockStatementContext ctx) {
		this.pushBlock(new RapidStatementBlock(this.getCurrentBlock()));
	}
	
	@Override
	public void exitStatementBlockStatement(StatementBlockStatementContext ctx) {
		this.popBlock();
	}
	
	@Override
	public void enterConditionalBlock(ConditionalBlockContext ctx) {
		
		ConditionalHeaderContext header = ctx.conditionalHeader();
		
		Likelihood like = this.getLikelihood(header.LIKELYHOOD() != null ? header.LIKELYHOOD().getText() : null, RapidIfStatement.class);
		
		BooleanExpressionContext exp = header.booleanParen().booleanExpression();
		BranchGenerationType type = BranchGenerationType.getType(exp.getText());
		Conditional cond = null;
		
		if (type == BranchGenerationType.CONDITIONAL) {
			cond = Conditional.getConditonal(exp.cmpOperator().getText());
		}
		
		RapidIfStatement ris = new RapidIfStatement(this.getCurrentBlock(), type, like, cond, ctx);
		this.pushBlock(ris);
		
		// TODO Make it figure out the expression.
		
	}
	
	@Override
	public void enterConditionalHeader(ConditionalHeaderContext ctx) {
		this.resetOperands();
	}

	@Override
	public void exitConditionalHeader(ConditionalHeaderContext ctx) {
		
		// Get the operands from the cache if they're what we expect.
		if (this.cachedOperands.size() == 2) {
			
			RapidIfStatement ris = (RapidIfStatement) this.getCurrentBlock();
			ris.setOperands(this.cachedOperands.get(0), this.cachedOperands.get(1));
			
		}
		
		// Cleanup.
		this.resetOperands();
		
	}

	@Override
	public void exitConditionalBlock(ConditionalBlockContext ctx) {
		this.popBlock();
	}
	
	@Override
	public void enterWhileBlock(WhileBlockContext ctx) {
		
		RapidWhileBlock rwb = new RapidWhileBlock(this.getCurrentBlock(), ctx);
		this.pushBlock(rwb);
		
		// TODO Make it figure out the expression.
		
	}

	@Override
	public void exitWhileBlock(WhileBlockContext ctx) {
		this.popBlock();
	}
	
	@Override
	public void enterInstruction(InstructionContext ctx) {
		
		RapidStatementBlock block = this.getCurrentBlock();
		this.currentInstructionStatement = new RapidInstructionStatement(block, ctx.ALPHANUM().getText());
		block.addStatement(this.currentInstructionStatement);
		
		this.resetOperands();
		
	}

	@Override
	public void enterNumericDereference(NumericDereferenceContext ctx) {
		
		Operand.PointerDereferenceOperand pdo = new Operand.PointerDereferenceOperand(this.architecture, this.currentSub, ctx.numericImmediate().getText()); // TODO
		this.cachedOperands.add(pdo);
		
		this.suppressNumericImmediateGeneration = true;
		
	}

	@Override
	public void exitNumericDereference(NumericDereferenceContext ctx) {
		this.suppressNumericImmediateGeneration = false;
	}

	@Override
	public void enterNumericRelativeDereference(NumericRelativeDereferenceContext ctx) {
		
		int offset = Integer.parseInt(ctx.plusMinus().getText() + ctx.NUMBER().getText());
		
		Operand.PointerDereferenceOperand pdo = new Operand.PointerDereferenceOperand(this.architecture, this.currentSub, ctx.numericImmediate().getText(), offset);
		this.cachedOperands.add(pdo);
		
		this.suppressNumericImmediateGeneration = true;
		
	}
	
	@Override
	public void exitNumericRelativeDereference(NumericRelativeDereferenceContext ctx) {
		this.suppressNumericImmediateGeneration = false;
	}

	@Override
	public void enterNumericSubroutineInvocation(NumericSubroutineInvocationContext ctx) {
		
		if (this.currentInstructionStatement != null) {
			
			throw new RuntimeException("this isn't supposed to happen!");
			
		}
		
	}

	@Override
	public void enterNumericImmediate(NumericImmediateContext ctx) {
		
		if (this.suppressNumericImmediateGeneration) return;
		
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
