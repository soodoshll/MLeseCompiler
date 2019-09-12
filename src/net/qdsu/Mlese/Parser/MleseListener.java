// Generated from C:/Users/soodo/Documents/MLeseCompiler\Mlese.g4 by ANTLR 4.7.2
package net.qdsu.Mlese.Parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MleseParser}.
 */
public interface MleseListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MleseParser#basicTypeSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterBasicTypeSpecifier(MleseParser.BasicTypeSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link MleseParser#basicTypeSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitBasicTypeSpecifier(MleseParser.BasicTypeSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by the {@code basicTypeSp}
	 * labeled alternative in {@link MleseParser#typeSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterBasicTypeSp(MleseParser.BasicTypeSpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code basicTypeSp}
	 * labeled alternative in {@link MleseParser#typeSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitBasicTypeSp(MleseParser.BasicTypeSpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayTypeSp}
	 * labeled alternative in {@link MleseParser#typeSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterArrayTypeSp(MleseParser.ArrayTypeSpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayTypeSp}
	 * labeled alternative in {@link MleseParser#typeSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitArrayTypeSp(MleseParser.ArrayTypeSpContext ctx);
	/**
	 * Enter a parse tree produced by {@link MleseParser#argumentList}.
	 * @param ctx the parse tree
	 */
	void enterArgumentList(MleseParser.ArgumentListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MleseParser#argumentList}.
	 * @param ctx the parse tree
	 */
	void exitArgumentList(MleseParser.ArgumentListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MleseParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void enterParameterList(MleseParser.ParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MleseParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void exitParameterList(MleseParser.ParameterListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MleseParser#creator}.
	 * @param ctx the parse tree
	 */
	void enterCreator(MleseParser.CreatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link MleseParser#creator}.
	 * @param ctx the parse tree
	 */
	void exitCreator(MleseParser.CreatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link MleseParser#wrongCreator}.
	 * @param ctx the parse tree
	 */
	void enterWrongCreator(MleseParser.WrongCreatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link MleseParser#wrongCreator}.
	 * @param ctx the parse tree
	 */
	void exitWrongCreator(MleseParser.WrongCreatorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code prefixExpr}
	 * labeled alternative in {@link MleseParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPrefixExpr(MleseParser.PrefixExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code prefixExpr}
	 * labeled alternative in {@link MleseParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPrefixExpr(MleseParser.PrefixExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code binaryExp}
	 * labeled alternative in {@link MleseParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBinaryExp(MleseParser.BinaryExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code binaryExp}
	 * labeled alternative in {@link MleseParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBinaryExp(MleseParser.BinaryExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code memberExp}
	 * labeled alternative in {@link MleseParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMemberExp(MleseParser.MemberExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code memberExp}
	 * labeled alternative in {@link MleseParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMemberExp(MleseParser.MemberExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code idExp}
	 * labeled alternative in {@link MleseParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterIdExp(MleseParser.IdExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code idExp}
	 * labeled alternative in {@link MleseParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitIdExp(MleseParser.IdExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code newExp}
	 * labeled alternative in {@link MleseParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNewExp(MleseParser.NewExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code newExp}
	 * labeled alternative in {@link MleseParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNewExp(MleseParser.NewExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code subExp}
	 * labeled alternative in {@link MleseParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterSubExp(MleseParser.SubExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code subExp}
	 * labeled alternative in {@link MleseParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitSubExp(MleseParser.SubExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code suffixExpr}
	 * labeled alternative in {@link MleseParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterSuffixExpr(MleseParser.SuffixExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code suffixExpr}
	 * labeled alternative in {@link MleseParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitSuffixExpr(MleseParser.SuffixExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code subscriptExp}
	 * labeled alternative in {@link MleseParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterSubscriptExp(MleseParser.SubscriptExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code subscriptExp}
	 * labeled alternative in {@link MleseParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitSubscriptExp(MleseParser.SubscriptExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code thisExp}
	 * labeled alternative in {@link MleseParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterThisExp(MleseParser.ThisExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code thisExp}
	 * labeled alternative in {@link MleseParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitThisExp(MleseParser.ThisExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code constExp}
	 * labeled alternative in {@link MleseParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterConstExp(MleseParser.ConstExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code constExp}
	 * labeled alternative in {@link MleseParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitConstExp(MleseParser.ConstExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code funcallExp}
	 * labeled alternative in {@link MleseParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterFuncallExp(MleseParser.FuncallExpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code funcallExp}
	 * labeled alternative in {@link MleseParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitFuncallExp(MleseParser.FuncallExpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolConst}
	 * labeled alternative in {@link MleseParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterBoolConst(MleseParser.BoolConstContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolConst}
	 * labeled alternative in {@link MleseParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitBoolConst(MleseParser.BoolConstContext ctx);
	/**
	 * Enter a parse tree produced by the {@code intConst}
	 * labeled alternative in {@link MleseParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterIntConst(MleseParser.IntConstContext ctx);
	/**
	 * Exit a parse tree produced by the {@code intConst}
	 * labeled alternative in {@link MleseParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitIntConst(MleseParser.IntConstContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stringConst}
	 * labeled alternative in {@link MleseParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterStringConst(MleseParser.StringConstContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stringConst}
	 * labeled alternative in {@link MleseParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitStringConst(MleseParser.StringConstContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nullConst}
	 * labeled alternative in {@link MleseParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterNullConst(MleseParser.NullConstContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nullConst}
	 * labeled alternative in {@link MleseParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitNullConst(MleseParser.NullConstContext ctx);
	/**
	 * Enter a parse tree produced by {@link MleseParser#varDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVarDeclaration(MleseParser.VarDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MleseParser#varDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVarDeclaration(MleseParser.VarDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MleseParser#varStatement}.
	 * @param ctx the parse tree
	 */
	void enterVarStatement(MleseParser.VarStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MleseParser#varStatement}.
	 * @param ctx the parse tree
	 */
	void exitVarStatement(MleseParser.VarStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MleseParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(MleseParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link MleseParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(MleseParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code blockStmt}
	 * labeled alternative in {@link MleseParser#nonDeclStatement}.
	 * @param ctx the parse tree
	 */
	void enterBlockStmt(MleseParser.BlockStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code blockStmt}
	 * labeled alternative in {@link MleseParser#nonDeclStatement}.
	 * @param ctx the parse tree
	 */
	void exitBlockStmt(MleseParser.BlockStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expStmt}
	 * labeled alternative in {@link MleseParser#nonDeclStatement}.
	 * @param ctx the parse tree
	 */
	void enterExpStmt(MleseParser.ExpStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expStmt}
	 * labeled alternative in {@link MleseParser#nonDeclStatement}.
	 * @param ctx the parse tree
	 */
	void exitExpStmt(MleseParser.ExpStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ifStmt}
	 * labeled alternative in {@link MleseParser#nonDeclStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStmt(MleseParser.IfStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ifStmt}
	 * labeled alternative in {@link MleseParser#nonDeclStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStmt(MleseParser.IfStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code whileStmt}
	 * labeled alternative in {@link MleseParser#nonDeclStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStmt(MleseParser.WhileStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code whileStmt}
	 * labeled alternative in {@link MleseParser#nonDeclStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStmt(MleseParser.WhileStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code forStmt}
	 * labeled alternative in {@link MleseParser#nonDeclStatement}.
	 * @param ctx the parse tree
	 */
	void enterForStmt(MleseParser.ForStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code forStmt}
	 * labeled alternative in {@link MleseParser#nonDeclStatement}.
	 * @param ctx the parse tree
	 */
	void exitForStmt(MleseParser.ForStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code returnStmt}
	 * labeled alternative in {@link MleseParser#nonDeclStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStmt(MleseParser.ReturnStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code returnStmt}
	 * labeled alternative in {@link MleseParser#nonDeclStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStmt(MleseParser.ReturnStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code breakStmt}
	 * labeled alternative in {@link MleseParser#nonDeclStatement}.
	 * @param ctx the parse tree
	 */
	void enterBreakStmt(MleseParser.BreakStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code breakStmt}
	 * labeled alternative in {@link MleseParser#nonDeclStatement}.
	 * @param ctx the parse tree
	 */
	void exitBreakStmt(MleseParser.BreakStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code continueStmt}
	 * labeled alternative in {@link MleseParser#nonDeclStatement}.
	 * @param ctx the parse tree
	 */
	void enterContinueStmt(MleseParser.ContinueStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code continueStmt}
	 * labeled alternative in {@link MleseParser#nonDeclStatement}.
	 * @param ctx the parse tree
	 */
	void exitContinueStmt(MleseParser.ContinueStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code emptyStmt}
	 * labeled alternative in {@link MleseParser#nonDeclStatement}.
	 * @param ctx the parse tree
	 */
	void enterEmptyStmt(MleseParser.EmptyStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code emptyStmt}
	 * labeled alternative in {@link MleseParser#nonDeclStatement}.
	 * @param ctx the parse tree
	 */
	void exitEmptyStmt(MleseParser.EmptyStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nonVarStmt}
	 * labeled alternative in {@link MleseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterNonVarStmt(MleseParser.NonVarStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nonVarStmt}
	 * labeled alternative in {@link MleseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitNonVarStmt(MleseParser.NonVarStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code varStmt}
	 * labeled alternative in {@link MleseParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterVarStmt(MleseParser.VarStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code varStmt}
	 * labeled alternative in {@link MleseParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitVarStmt(MleseParser.VarStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link MleseParser#functionDefinition}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDefinition(MleseParser.FunctionDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MleseParser#functionDefinition}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDefinition(MleseParser.FunctionDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MleseParser#constructorDefinition}.
	 * @param ctx the parse tree
	 */
	void enterConstructorDefinition(MleseParser.ConstructorDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MleseParser#constructorDefinition}.
	 * @param ctx the parse tree
	 */
	void exitConstructorDefinition(MleseParser.ConstructorDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MleseParser#classDefinition}.
	 * @param ctx the parse tree
	 */
	void enterClassDefinition(MleseParser.ClassDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MleseParser#classDefinition}.
	 * @param ctx the parse tree
	 */
	void exitClassDefinition(MleseParser.ClassDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MleseParser#memberDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterMemberDeclaration(MleseParser.MemberDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MleseParser#memberDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitMemberDeclaration(MleseParser.MemberDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link MleseParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(MleseParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link MleseParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(MleseParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link MleseParser#programSection}.
	 * @param ctx the parse tree
	 */
	void enterProgramSection(MleseParser.ProgramSectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MleseParser#programSection}.
	 * @param ctx the parse tree
	 */
	void exitProgramSection(MleseParser.ProgramSectionContext ctx);
}