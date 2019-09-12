// Generated from C:/Users/soodo/Documents/MLeseCompiler\Mlese.g4 by ANTLR 4.7.2
package net.qdsu.Mlese.Parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MleseParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, LBRACK=8, RBRACK=9, 
		BOOL=10, INT=11, STRING=12, VOID=13, TRUE=14, FALSE=15, IF=16, ELSE=17, 
		FOR=18, WHILE=19, BREAK=20, CONTINUE=21, RETURN=22, NEW=23, CLASS=24, 
		THIS=25, INTCONST=26, STRINGCONST=27, NULLCONST=28, PLUS=29, MINUS=30, 
		STAR=31, DIV=32, MOD=33, LT=34, GT=35, EQ=36, NE=37, GE=38, LE=39, ANDAND=40, 
		OROR=41, NOT=42, LSHIFT=43, RSHIFT=44, TILDE=45, OR=46, CARET=47, AND=48, 
		ASSIGN=49, PLUSPLUS=50, MINUSMINUS=51, ID=52, Whitespace=53, Newline=54, 
		BlockComment=55, LineComment=56;
	public static final int
		RULE_basicTypeSpecifier = 0, RULE_typeSpecifier = 1, RULE_argumentList = 2, 
		RULE_parameterList = 3, RULE_creator = 4, RULE_wrongCreator = 5, RULE_expression = 6, 
		RULE_constant = 7, RULE_varDeclaration = 8, RULE_varStatement = 9, RULE_block = 10, 
		RULE_nonDeclStatement = 11, RULE_statement = 12, RULE_functionDefinition = 13, 
		RULE_constructorDefinition = 14, RULE_classDefinition = 15, RULE_memberDeclaration = 16, 
		RULE_program = 17, RULE_programSection = 18;
	private static String[] makeRuleNames() {
		return new String[] {
			"basicTypeSpecifier", "typeSpecifier", "argumentList", "parameterList", 
			"creator", "wrongCreator", "expression", "constant", "varDeclaration", 
			"varStatement", "block", "nonDeclStatement", "statement", "functionDefinition", 
			"constructorDefinition", "classDefinition", "memberDeclaration", "program", 
			"programSection"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "','", "'('", "')'", "'.'", "';'", "'{'", "'}'", "'['", "']'", 
			"'bool'", "'int'", "'string'", "'void'", "'true'", "'false'", "'if'", 
			"'else'", "'for'", "'while'", "'break'", "'continue'", "'return'", "'new'", 
			"'class'", "'this'", null, null, "'null'", "'+'", "'-'", "'*'", "'/'", 
			"'%'", "'<'", "'>'", "'=='", "'!='", "'>='", "'<='", "'&&'", "'||'", 
			"'!'", "'<<'", "'>>'", "'~'", "'|'", "'^'", "'&'", "'='", "'++'", "'--'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, "LBRACK", "RBRACK", "BOOL", 
			"INT", "STRING", "VOID", "TRUE", "FALSE", "IF", "ELSE", "FOR", "WHILE", 
			"BREAK", "CONTINUE", "RETURN", "NEW", "CLASS", "THIS", "INTCONST", "STRINGCONST", 
			"NULLCONST", "PLUS", "MINUS", "STAR", "DIV", "MOD", "LT", "GT", "EQ", 
			"NE", "GE", "LE", "ANDAND", "OROR", "NOT", "LSHIFT", "RSHIFT", "TILDE", 
			"OR", "CARET", "AND", "ASSIGN", "PLUSPLUS", "MINUSMINUS", "ID", "Whitespace", 
			"Newline", "BlockComment", "LineComment"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Mlese.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public MleseParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class BasicTypeSpecifierContext extends ParserRuleContext {
		public Token returnType;
		public TerminalNode BOOL() { return getToken(MleseParser.BOOL, 0); }
		public TerminalNode INT() { return getToken(MleseParser.INT, 0); }
		public TerminalNode VOID() { return getToken(MleseParser.VOID, 0); }
		public TerminalNode STRING() { return getToken(MleseParser.STRING, 0); }
		public TerminalNode ID() { return getToken(MleseParser.ID, 0); }
		public BasicTypeSpecifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_basicTypeSpecifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).enterBasicTypeSpecifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).exitBasicTypeSpecifier(this);
		}
	}

	public final BasicTypeSpecifierContext basicTypeSpecifier() throws RecognitionException {
		BasicTypeSpecifierContext _localctx = new BasicTypeSpecifierContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_basicTypeSpecifier);
		try {
			setState(43);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case BOOL:
				enterOuterAlt(_localctx, 1);
				{
				setState(38);
				((BasicTypeSpecifierContext)_localctx).returnType = match(BOOL);
				}
				break;
			case INT:
				enterOuterAlt(_localctx, 2);
				{
				setState(39);
				((BasicTypeSpecifierContext)_localctx).returnType = match(INT);
				}
				break;
			case VOID:
				enterOuterAlt(_localctx, 3);
				{
				setState(40);
				((BasicTypeSpecifierContext)_localctx).returnType = match(VOID);
				}
				break;
			case STRING:
				enterOuterAlt(_localctx, 4);
				{
				setState(41);
				((BasicTypeSpecifierContext)_localctx).returnType = match(STRING);
				}
				break;
			case ID:
				enterOuterAlt(_localctx, 5);
				{
				setState(42);
				((BasicTypeSpecifierContext)_localctx).returnType = match(ID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeSpecifierContext extends ParserRuleContext {
		public TypeSpecifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeSpecifier; }
	 
		public TypeSpecifierContext() { }
		public void copyFrom(TypeSpecifierContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class BasicTypeSpContext extends TypeSpecifierContext {
		public BasicTypeSpecifierContext basicTypeSpecifier() {
			return getRuleContext(BasicTypeSpecifierContext.class,0);
		}
		public BasicTypeSpContext(TypeSpecifierContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).enterBasicTypeSp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).exitBasicTypeSp(this);
		}
	}
	public static class ArrayTypeSpContext extends TypeSpecifierContext {
		public TypeSpecifierContext typeSpecifier() {
			return getRuleContext(TypeSpecifierContext.class,0);
		}
		public TerminalNode LBRACK() { return getToken(MleseParser.LBRACK, 0); }
		public TerminalNode RBRACK() { return getToken(MleseParser.RBRACK, 0); }
		public ArrayTypeSpContext(TypeSpecifierContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).enterArrayTypeSp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).exitArrayTypeSp(this);
		}
	}

	public final TypeSpecifierContext typeSpecifier() throws RecognitionException {
		return typeSpecifier(0);
	}

	private TypeSpecifierContext typeSpecifier(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		TypeSpecifierContext _localctx = new TypeSpecifierContext(_ctx, _parentState);
		TypeSpecifierContext _prevctx = _localctx;
		int _startState = 2;
		enterRecursionRule(_localctx, 2, RULE_typeSpecifier, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new BasicTypeSpContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(46);
			basicTypeSpecifier();
			}
			_ctx.stop = _input.LT(-1);
			setState(53);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new ArrayTypeSpContext(new TypeSpecifierContext(_parentctx, _parentState));
					pushNewRecursionContext(_localctx, _startState, RULE_typeSpecifier);
					setState(48);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					{
					setState(49);
					match(LBRACK);
					setState(50);
					match(RBRACK);
					}
					}
					} 
				}
				setState(55);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class ArgumentListContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ArgumentListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argumentList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).enterArgumentList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).exitArgumentList(this);
		}
	}

	public final ArgumentListContext argumentList() throws RecognitionException {
		ArgumentListContext _localctx = new ArgumentListContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_argumentList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(56);
			expression(0);
			setState(61);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(57);
				match(T__0);
				setState(58);
				expression(0);
				}
				}
				setState(63);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParameterListContext extends ParserRuleContext {
		public List<VarDeclarationContext> varDeclaration() {
			return getRuleContexts(VarDeclarationContext.class);
		}
		public VarDeclarationContext varDeclaration(int i) {
			return getRuleContext(VarDeclarationContext.class,i);
		}
		public ParameterListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parameterList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).enterParameterList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).exitParameterList(this);
		}
	}

	public final ParameterListContext parameterList() throws RecognitionException {
		ParameterListContext _localctx = new ParameterListContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_parameterList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(64);
			varDeclaration();
			setState(69);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(65);
				match(T__0);
				setState(66);
				varDeclaration();
				}
				}
				setState(71);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CreatorContext extends ParserRuleContext {
		public WrongCreatorContext wrongCreator() {
			return getRuleContext(WrongCreatorContext.class,0);
		}
		public BasicTypeSpecifierContext basicTypeSpecifier() {
			return getRuleContext(BasicTypeSpecifierContext.class,0);
		}
		public List<TerminalNode> LBRACK() { return getTokens(MleseParser.LBRACK); }
		public TerminalNode LBRACK(int i) {
			return getToken(MleseParser.LBRACK, i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> RBRACK() { return getTokens(MleseParser.RBRACK); }
		public TerminalNode RBRACK(int i) {
			return getToken(MleseParser.RBRACK, i);
		}
		public CreatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_creator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).enterCreator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).exitCreator(this);
		}
	}

	public final CreatorContext creator() throws RecognitionException {
		CreatorContext _localctx = new CreatorContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_creator);
		try {
			int _alt;
			setState(94);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(72);
				wrongCreator();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(73);
				basicTypeSpecifier();
				setState(74);
				match(T__1);
				setState(75);
				match(T__2);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(77);
				basicTypeSpecifier();
				setState(82); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(78);
						match(LBRACK);
						setState(79);
						expression(0);
						setState(80);
						match(RBRACK);
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(84); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,4,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
				setState(90);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(86);
						match(LBRACK);
						setState(87);
						match(RBRACK);
						}
						} 
					}
					setState(92);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
				}
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(93);
				basicTypeSpecifier();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WrongCreatorContext extends ParserRuleContext {
		public BasicTypeSpecifierContext basicTypeSpecifier() {
			return getRuleContext(BasicTypeSpecifierContext.class,0);
		}
		public List<TerminalNode> LBRACK() { return getTokens(MleseParser.LBRACK); }
		public TerminalNode LBRACK(int i) {
			return getToken(MleseParser.LBRACK, i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<TerminalNode> RBRACK() { return getTokens(MleseParser.RBRACK); }
		public TerminalNode RBRACK(int i) {
			return getToken(MleseParser.RBRACK, i);
		}
		public WrongCreatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_wrongCreator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).enterWrongCreator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).exitWrongCreator(this);
		}
	}

	public final WrongCreatorContext wrongCreator() throws RecognitionException {
		WrongCreatorContext _localctx = new WrongCreatorContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_wrongCreator);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(96);
			basicTypeSpecifier();
			setState(103);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(97);
					match(LBRACK);
					setState(98);
					expression(0);
					setState(99);
					match(RBRACK);
					}
					} 
				}
				setState(105);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			}
			setState(108); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(106);
					match(LBRACK);
					setState(107);
					match(RBRACK);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(110); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			setState(116); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(112);
					match(LBRACK);
					setState(113);
					expression(0);
					setState(114);
					match(RBRACK);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(118); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class PrefixExprContext extends ExpressionContext {
		public Token op;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode PLUSPLUS() { return getToken(MleseParser.PLUSPLUS, 0); }
		public TerminalNode MINUSMINUS() { return getToken(MleseParser.MINUSMINUS, 0); }
		public TerminalNode PLUS() { return getToken(MleseParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(MleseParser.MINUS, 0); }
		public TerminalNode NOT() { return getToken(MleseParser.NOT, 0); }
		public TerminalNode TILDE() { return getToken(MleseParser.TILDE, 0); }
		public PrefixExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).enterPrefixExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).exitPrefixExpr(this);
		}
	}
	public static class BinaryExpContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode STAR() { return getToken(MleseParser.STAR, 0); }
		public TerminalNode DIV() { return getToken(MleseParser.DIV, 0); }
		public TerminalNode MOD() { return getToken(MleseParser.MOD, 0); }
		public TerminalNode PLUS() { return getToken(MleseParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(MleseParser.MINUS, 0); }
		public TerminalNode LSHIFT() { return getToken(MleseParser.LSHIFT, 0); }
		public TerminalNode RSHIFT() { return getToken(MleseParser.RSHIFT, 0); }
		public TerminalNode LT() { return getToken(MleseParser.LT, 0); }
		public TerminalNode LE() { return getToken(MleseParser.LE, 0); }
		public TerminalNode GT() { return getToken(MleseParser.GT, 0); }
		public TerminalNode GE() { return getToken(MleseParser.GE, 0); }
		public TerminalNode EQ() { return getToken(MleseParser.EQ, 0); }
		public TerminalNode NE() { return getToken(MleseParser.NE, 0); }
		public TerminalNode AND() { return getToken(MleseParser.AND, 0); }
		public TerminalNode CARET() { return getToken(MleseParser.CARET, 0); }
		public TerminalNode OR() { return getToken(MleseParser.OR, 0); }
		public TerminalNode ANDAND() { return getToken(MleseParser.ANDAND, 0); }
		public TerminalNode OROR() { return getToken(MleseParser.OROR, 0); }
		public TerminalNode ASSIGN() { return getToken(MleseParser.ASSIGN, 0); }
		public BinaryExpContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).enterBinaryExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).exitBinaryExp(this);
		}
	}
	public static class MemberExpContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode ID() { return getToken(MleseParser.ID, 0); }
		public MemberExpContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).enterMemberExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).exitMemberExp(this);
		}
	}
	public static class IdExpContext extends ExpressionContext {
		public TerminalNode ID() { return getToken(MleseParser.ID, 0); }
		public IdExpContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).enterIdExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).exitIdExp(this);
		}
	}
	public static class NewExpContext extends ExpressionContext {
		public TerminalNode NEW() { return getToken(MleseParser.NEW, 0); }
		public CreatorContext creator() {
			return getRuleContext(CreatorContext.class,0);
		}
		public NewExpContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).enterNewExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).exitNewExp(this);
		}
	}
	public static class SubExpContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public SubExpContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).enterSubExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).exitSubExp(this);
		}
	}
	public static class SuffixExprContext extends ExpressionContext {
		public Token op;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode PLUSPLUS() { return getToken(MleseParser.PLUSPLUS, 0); }
		public TerminalNode MINUSMINUS() { return getToken(MleseParser.MINUSMINUS, 0); }
		public SuffixExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).enterSuffixExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).exitSuffixExpr(this);
		}
	}
	public static class SubscriptExpContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode LBRACK() { return getToken(MleseParser.LBRACK, 0); }
		public TerminalNode RBRACK() { return getToken(MleseParser.RBRACK, 0); }
		public SubscriptExpContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).enterSubscriptExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).exitSubscriptExp(this);
		}
	}
	public static class ThisExpContext extends ExpressionContext {
		public TerminalNode THIS() { return getToken(MleseParser.THIS, 0); }
		public ThisExpContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).enterThisExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).exitThisExp(this);
		}
	}
	public static class ConstExpContext extends ExpressionContext {
		public ConstantContext constant() {
			return getRuleContext(ConstantContext.class,0);
		}
		public ConstExpContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).enterConstExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).exitConstExp(this);
		}
	}
	public static class FuncallExpContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ArgumentListContext argumentList() {
			return getRuleContext(ArgumentListContext.class,0);
		}
		public FuncallExpContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).enterFuncallExp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).exitFuncallExp(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 12;
		enterRecursionRule(_localctx, 12, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(136);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NEW:
				{
				_localctx = new NewExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(121);
				match(NEW);
				setState(122);
				creator();
				}
				break;
			case PLUSPLUS:
			case MINUSMINUS:
				{
				_localctx = new PrefixExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(123);
				((PrefixExprContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==PLUSPLUS || _la==MINUSMINUS) ) {
					((PrefixExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(124);
				expression(18);
				}
				break;
			case PLUS:
			case MINUS:
				{
				_localctx = new PrefixExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(125);
				((PrefixExprContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==PLUS || _la==MINUS) ) {
					((PrefixExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(126);
				expression(17);
				}
				break;
			case NOT:
			case TILDE:
				{
				_localctx = new PrefixExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(127);
				((PrefixExprContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==NOT || _la==TILDE) ) {
					((PrefixExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(128);
				expression(16);
				}
				break;
			case T__1:
				{
				_localctx = new SubExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(129);
				match(T__1);
				setState(130);
				expression(0);
				setState(131);
				match(T__2);
				}
				break;
			case TRUE:
			case FALSE:
			case INTCONST:
			case STRINGCONST:
			case NULLCONST:
				{
				_localctx = new ConstExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(133);
				constant();
				}
				break;
			case THIS:
				{
				_localctx = new ThisExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(134);
				match(THIS);
				}
				break;
			case ID:
				{
				_localctx = new IdExpContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(135);
				match(ID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(189);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(187);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
					case 1:
						{
						_localctx = new BinaryExpContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(138);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(139);
						((BinaryExpContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << STAR) | (1L << DIV) | (1L << MOD))) != 0)) ) {
							((BinaryExpContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(140);
						expression(16);
						}
						break;
					case 2:
						{
						_localctx = new BinaryExpContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(141);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(142);
						((BinaryExpContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==PLUS || _la==MINUS) ) {
							((BinaryExpContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(143);
						expression(15);
						}
						break;
					case 3:
						{
						_localctx = new BinaryExpContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(144);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(145);
						((BinaryExpContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==LSHIFT || _la==RSHIFT) ) {
							((BinaryExpContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(146);
						expression(14);
						}
						break;
					case 4:
						{
						_localctx = new BinaryExpContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(147);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(148);
						((BinaryExpContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << LT) | (1L << GT) | (1L << GE) | (1L << LE))) != 0)) ) {
							((BinaryExpContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(149);
						expression(13);
						}
						break;
					case 5:
						{
						_localctx = new BinaryExpContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(150);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(151);
						((BinaryExpContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==EQ || _la==NE) ) {
							((BinaryExpContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(152);
						expression(12);
						}
						break;
					case 6:
						{
						_localctx = new BinaryExpContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(153);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(154);
						((BinaryExpContext)_localctx).op = match(AND);
						setState(155);
						expression(11);
						}
						break;
					case 7:
						{
						_localctx = new BinaryExpContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(156);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(157);
						((BinaryExpContext)_localctx).op = match(CARET);
						setState(158);
						expression(10);
						}
						break;
					case 8:
						{
						_localctx = new BinaryExpContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(159);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(160);
						((BinaryExpContext)_localctx).op = match(OR);
						setState(161);
						expression(9);
						}
						break;
					case 9:
						{
						_localctx = new BinaryExpContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(162);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(163);
						((BinaryExpContext)_localctx).op = match(ANDAND);
						setState(164);
						expression(8);
						}
						break;
					case 10:
						{
						_localctx = new BinaryExpContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(165);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(166);
						((BinaryExpContext)_localctx).op = match(OROR);
						setState(167);
						expression(7);
						}
						break;
					case 11:
						{
						_localctx = new BinaryExpContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(168);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(169);
						((BinaryExpContext)_localctx).op = match(ASSIGN);
						setState(170);
						expression(5);
						}
						break;
					case 12:
						{
						_localctx = new SuffixExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(171);
						if (!(precpred(_ctx, 23))) throw new FailedPredicateException(this, "precpred(_ctx, 23)");
						setState(172);
						((SuffixExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==PLUSPLUS || _la==MINUSMINUS) ) {
							((SuffixExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						}
						break;
					case 13:
						{
						_localctx = new MemberExpContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(173);
						if (!(precpred(_ctx, 21))) throw new FailedPredicateException(this, "precpred(_ctx, 21)");
						setState(174);
						match(T__3);
						setState(175);
						match(ID);
						}
						break;
					case 14:
						{
						_localctx = new FuncallExpContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(176);
						if (!(precpred(_ctx, 20))) throw new FailedPredicateException(this, "precpred(_ctx, 20)");
						setState(177);
						match(T__1);
						setState(179);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << TRUE) | (1L << FALSE) | (1L << NEW) | (1L << THIS) | (1L << INTCONST) | (1L << STRINGCONST) | (1L << NULLCONST) | (1L << PLUS) | (1L << MINUS) | (1L << NOT) | (1L << TILDE) | (1L << PLUSPLUS) | (1L << MINUSMINUS) | (1L << ID))) != 0)) {
							{
							setState(178);
							argumentList();
							}
						}

						setState(181);
						match(T__2);
						}
						break;
					case 15:
						{
						_localctx = new SubscriptExpContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(182);
						if (!(precpred(_ctx, 19))) throw new FailedPredicateException(this, "precpred(_ctx, 19)");
						setState(183);
						match(LBRACK);
						setState(184);
						expression(0);
						setState(185);
						match(RBRACK);
						}
						break;
					}
					} 
				}
				setState(191);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class ConstantContext extends ParserRuleContext {
		public ConstantContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constant; }
	 
		public ConstantContext() { }
		public void copyFrom(ConstantContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class BoolConstContext extends ConstantContext {
		public Token type;
		public TerminalNode TRUE() { return getToken(MleseParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(MleseParser.FALSE, 0); }
		public BoolConstContext(ConstantContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).enterBoolConst(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).exitBoolConst(this);
		}
	}
	public static class IntConstContext extends ConstantContext {
		public Token type;
		public TerminalNode INTCONST() { return getToken(MleseParser.INTCONST, 0); }
		public IntConstContext(ConstantContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).enterIntConst(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).exitIntConst(this);
		}
	}
	public static class NullConstContext extends ConstantContext {
		public Token type;
		public TerminalNode NULLCONST() { return getToken(MleseParser.NULLCONST, 0); }
		public NullConstContext(ConstantContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).enterNullConst(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).exitNullConst(this);
		}
	}
	public static class StringConstContext extends ConstantContext {
		public Token type;
		public TerminalNode STRINGCONST() { return getToken(MleseParser.STRINGCONST, 0); }
		public StringConstContext(ConstantContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).enterStringConst(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).exitStringConst(this);
		}
	}

	public final ConstantContext constant() throws RecognitionException {
		ConstantContext _localctx = new ConstantContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_constant);
		try {
			setState(197);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TRUE:
				_localctx = new BoolConstContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(192);
				((BoolConstContext)_localctx).type = match(TRUE);
				}
				break;
			case FALSE:
				_localctx = new BoolConstContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(193);
				((BoolConstContext)_localctx).type = match(FALSE);
				}
				break;
			case INTCONST:
				_localctx = new IntConstContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(194);
				((IntConstContext)_localctx).type = match(INTCONST);
				}
				break;
			case STRINGCONST:
				_localctx = new StringConstContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(195);
				((StringConstContext)_localctx).type = match(STRINGCONST);
				}
				break;
			case NULLCONST:
				_localctx = new NullConstContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(196);
				((NullConstContext)_localctx).type = match(NULLCONST);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarDeclarationContext extends ParserRuleContext {
		public TypeSpecifierContext typeSpecifier() {
			return getRuleContext(TypeSpecifierContext.class,0);
		}
		public TerminalNode ID() { return getToken(MleseParser.ID, 0); }
		public VarDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).enterVarDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).exitVarDeclaration(this);
		}
	}

	public final VarDeclarationContext varDeclaration() throws RecognitionException {
		VarDeclarationContext _localctx = new VarDeclarationContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_varDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(199);
			typeSpecifier(0);
			setState(200);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarStatementContext extends ParserRuleContext {
		public VarDeclarationContext varDeclaration() {
			return getRuleContext(VarDeclarationContext.class,0);
		}
		public TerminalNode ASSIGN() { return getToken(MleseParser.ASSIGN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public VarStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).enterVarStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).exitVarStatement(this);
		}
	}

	public final VarStatementContext varStatement() throws RecognitionException {
		VarStatementContext _localctx = new VarStatementContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_varStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(202);
			varDeclaration();
			setState(205);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ASSIGN) {
				{
				setState(203);
				match(ASSIGN);
				setState(204);
				expression(0);
				}
			}

			setState(207);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).exitBlock(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(209);
			match(T__5);
			setState(213);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__4) | (1L << T__5) | (1L << BOOL) | (1L << INT) | (1L << STRING) | (1L << VOID) | (1L << TRUE) | (1L << FALSE) | (1L << IF) | (1L << FOR) | (1L << WHILE) | (1L << BREAK) | (1L << CONTINUE) | (1L << RETURN) | (1L << NEW) | (1L << THIS) | (1L << INTCONST) | (1L << STRINGCONST) | (1L << NULLCONST) | (1L << PLUS) | (1L << MINUS) | (1L << NOT) | (1L << TILDE) | (1L << PLUSPLUS) | (1L << MINUSMINUS) | (1L << ID))) != 0)) {
				{
				{
				setState(210);
				statement();
				}
				}
				setState(215);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(216);
			match(T__6);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NonDeclStatementContext extends ParserRuleContext {
		public NonDeclStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nonDeclStatement; }
	 
		public NonDeclStatementContext() { }
		public void copyFrom(NonDeclStatementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ForStmtContext extends NonDeclStatementContext {
		public ExpressionContext init;
		public ExpressionContext cond;
		public ExpressionContext step;
		public TerminalNode FOR() { return getToken(MleseParser.FOR, 0); }
		public NonDeclStatementContext nonDeclStatement() {
			return getRuleContext(NonDeclStatementContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ForStmtContext(NonDeclStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).enterForStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).exitForStmt(this);
		}
	}
	public static class WhileStmtContext extends NonDeclStatementContext {
		public TerminalNode WHILE() { return getToken(MleseParser.WHILE, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public NonDeclStatementContext nonDeclStatement() {
			return getRuleContext(NonDeclStatementContext.class,0);
		}
		public WhileStmtContext(NonDeclStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).enterWhileStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).exitWhileStmt(this);
		}
	}
	public static class IfStmtContext extends NonDeclStatementContext {
		public TerminalNode IF() { return getToken(MleseParser.IF, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<NonDeclStatementContext> nonDeclStatement() {
			return getRuleContexts(NonDeclStatementContext.class);
		}
		public NonDeclStatementContext nonDeclStatement(int i) {
			return getRuleContext(NonDeclStatementContext.class,i);
		}
		public TerminalNode ELSE() { return getToken(MleseParser.ELSE, 0); }
		public IfStmtContext(NonDeclStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).enterIfStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).exitIfStmt(this);
		}
	}
	public static class BlockStmtContext extends NonDeclStatementContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public BlockStmtContext(NonDeclStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).enterBlockStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).exitBlockStmt(this);
		}
	}
	public static class BreakStmtContext extends NonDeclStatementContext {
		public TerminalNode BREAK() { return getToken(MleseParser.BREAK, 0); }
		public BreakStmtContext(NonDeclStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).enterBreakStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).exitBreakStmt(this);
		}
	}
	public static class ExpStmtContext extends NonDeclStatementContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ExpStmtContext(NonDeclStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).enterExpStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).exitExpStmt(this);
		}
	}
	public static class EmptyStmtContext extends NonDeclStatementContext {
		public EmptyStmtContext(NonDeclStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).enterEmptyStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).exitEmptyStmt(this);
		}
	}
	public static class ReturnStmtContext extends NonDeclStatementContext {
		public TerminalNode RETURN() { return getToken(MleseParser.RETURN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ReturnStmtContext(NonDeclStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).enterReturnStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).exitReturnStmt(this);
		}
	}
	public static class ContinueStmtContext extends NonDeclStatementContext {
		public TerminalNode CONTINUE() { return getToken(MleseParser.CONTINUE, 0); }
		public ContinueStmtContext(NonDeclStatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).enterContinueStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).exitContinueStmt(this);
		}
	}

	public final NonDeclStatementContext nonDeclStatement() throws RecognitionException {
		NonDeclStatementContext _localctx = new NonDeclStatementContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_nonDeclStatement);
		int _la;
		try {
			setState(262);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__5:
				_localctx = new BlockStmtContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(218);
				block();
				}
				break;
			case T__1:
			case TRUE:
			case FALSE:
			case NEW:
			case THIS:
			case INTCONST:
			case STRINGCONST:
			case NULLCONST:
			case PLUS:
			case MINUS:
			case NOT:
			case TILDE:
			case PLUSPLUS:
			case MINUSMINUS:
			case ID:
				_localctx = new ExpStmtContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(219);
				expression(0);
				setState(220);
				match(T__4);
				}
				break;
			case IF:
				_localctx = new IfStmtContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(222);
				match(IF);
				setState(223);
				match(T__1);
				setState(224);
				expression(0);
				setState(225);
				match(T__2);
				setState(226);
				nonDeclStatement();
				setState(229);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
				case 1:
					{
					setState(227);
					match(ELSE);
					setState(228);
					nonDeclStatement();
					}
					break;
				}
				}
				break;
			case WHILE:
				_localctx = new WhileStmtContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(231);
				match(WHILE);
				setState(232);
				match(T__1);
				setState(233);
				expression(0);
				setState(234);
				match(T__2);
				setState(235);
				nonDeclStatement();
				}
				break;
			case FOR:
				_localctx = new ForStmtContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(237);
				match(FOR);
				setState(238);
				match(T__1);
				setState(240);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << TRUE) | (1L << FALSE) | (1L << NEW) | (1L << THIS) | (1L << INTCONST) | (1L << STRINGCONST) | (1L << NULLCONST) | (1L << PLUS) | (1L << MINUS) | (1L << NOT) | (1L << TILDE) | (1L << PLUSPLUS) | (1L << MINUSMINUS) | (1L << ID))) != 0)) {
					{
					setState(239);
					((ForStmtContext)_localctx).init = expression(0);
					}
				}

				setState(242);
				match(T__4);
				setState(244);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << TRUE) | (1L << FALSE) | (1L << NEW) | (1L << THIS) | (1L << INTCONST) | (1L << STRINGCONST) | (1L << NULLCONST) | (1L << PLUS) | (1L << MINUS) | (1L << NOT) | (1L << TILDE) | (1L << PLUSPLUS) | (1L << MINUSMINUS) | (1L << ID))) != 0)) {
					{
					setState(243);
					((ForStmtContext)_localctx).cond = expression(0);
					}
				}

				setState(246);
				match(T__4);
				setState(248);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << TRUE) | (1L << FALSE) | (1L << NEW) | (1L << THIS) | (1L << INTCONST) | (1L << STRINGCONST) | (1L << NULLCONST) | (1L << PLUS) | (1L << MINUS) | (1L << NOT) | (1L << TILDE) | (1L << PLUSPLUS) | (1L << MINUSMINUS) | (1L << ID))) != 0)) {
					{
					setState(247);
					((ForStmtContext)_localctx).step = expression(0);
					}
				}

				setState(250);
				match(T__2);
				setState(251);
				nonDeclStatement();
				}
				break;
			case RETURN:
				_localctx = new ReturnStmtContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(252);
				match(RETURN);
				setState(254);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << TRUE) | (1L << FALSE) | (1L << NEW) | (1L << THIS) | (1L << INTCONST) | (1L << STRINGCONST) | (1L << NULLCONST) | (1L << PLUS) | (1L << MINUS) | (1L << NOT) | (1L << TILDE) | (1L << PLUSPLUS) | (1L << MINUSMINUS) | (1L << ID))) != 0)) {
					{
					setState(253);
					expression(0);
					}
				}

				setState(256);
				match(T__4);
				}
				break;
			case BREAK:
				_localctx = new BreakStmtContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(257);
				match(BREAK);
				setState(258);
				match(T__4);
				}
				break;
			case CONTINUE:
				_localctx = new ContinueStmtContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(259);
				match(CONTINUE);
				setState(260);
				match(T__4);
				}
				break;
			case T__4:
				_localctx = new EmptyStmtContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(261);
				match(T__4);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
	 
		public StatementContext() { }
		public void copyFrom(StatementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class NonVarStmtContext extends StatementContext {
		public NonDeclStatementContext nonDeclStatement() {
			return getRuleContext(NonDeclStatementContext.class,0);
		}
		public NonVarStmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).enterNonVarStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).exitNonVarStmt(this);
		}
	}
	public static class VarStmtContext extends StatementContext {
		public VarStatementContext varStatement() {
			return getRuleContext(VarStatementContext.class,0);
		}
		public VarStmtContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).enterVarStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).exitVarStmt(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_statement);
		try {
			setState(266);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				_localctx = new NonVarStmtContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(264);
				nonDeclStatement();
				}
				break;
			case 2:
				_localctx = new VarStmtContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(265);
				varStatement();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionDefinitionContext extends ParserRuleContext {
		public TypeSpecifierContext typeSpecifier() {
			return getRuleContext(TypeSpecifierContext.class,0);
		}
		public TerminalNode ID() { return getToken(MleseParser.ID, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ParameterListContext parameterList() {
			return getRuleContext(ParameterListContext.class,0);
		}
		public FunctionDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).enterFunctionDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).exitFunctionDefinition(this);
		}
	}

	public final FunctionDefinitionContext functionDefinition() throws RecognitionException {
		FunctionDefinitionContext _localctx = new FunctionDefinitionContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_functionDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(268);
			typeSpecifier(0);
			setState(269);
			match(ID);
			setState(270);
			match(T__1);
			setState(272);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOL) | (1L << INT) | (1L << STRING) | (1L << VOID) | (1L << ID))) != 0)) {
				{
				setState(271);
				parameterList();
				}
			}

			setState(274);
			match(T__2);
			setState(275);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstructorDefinitionContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(MleseParser.ID, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ParameterListContext parameterList() {
			return getRuleContext(ParameterListContext.class,0);
		}
		public ConstructorDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constructorDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).enterConstructorDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).exitConstructorDefinition(this);
		}
	}

	public final ConstructorDefinitionContext constructorDefinition() throws RecognitionException {
		ConstructorDefinitionContext _localctx = new ConstructorDefinitionContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_constructorDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(277);
			match(ID);
			setState(278);
			match(T__1);
			setState(280);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOL) | (1L << INT) | (1L << STRING) | (1L << VOID) | (1L << ID))) != 0)) {
				{
				setState(279);
				parameterList();
				}
			}

			setState(282);
			match(T__2);
			setState(283);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassDefinitionContext extends ParserRuleContext {
		public TerminalNode CLASS() { return getToken(MleseParser.CLASS, 0); }
		public TerminalNode ID() { return getToken(MleseParser.ID, 0); }
		public List<MemberDeclarationContext> memberDeclaration() {
			return getRuleContexts(MemberDeclarationContext.class);
		}
		public MemberDeclarationContext memberDeclaration(int i) {
			return getRuleContext(MemberDeclarationContext.class,i);
		}
		public ClassDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).enterClassDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).exitClassDefinition(this);
		}
	}

	public final ClassDefinitionContext classDefinition() throws RecognitionException {
		ClassDefinitionContext _localctx = new ClassDefinitionContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_classDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(285);
			match(CLASS);
			setState(286);
			match(ID);
			setState(287);
			match(T__5);
			setState(291);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOL) | (1L << INT) | (1L << STRING) | (1L << VOID) | (1L << ID))) != 0)) {
				{
				{
				setState(288);
				memberDeclaration();
				}
				}
				setState(293);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(294);
			match(T__6);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MemberDeclarationContext extends ParserRuleContext {
		public VarStatementContext varStatement() {
			return getRuleContext(VarStatementContext.class,0);
		}
		public FunctionDefinitionContext functionDefinition() {
			return getRuleContext(FunctionDefinitionContext.class,0);
		}
		public ConstructorDefinitionContext constructorDefinition() {
			return getRuleContext(ConstructorDefinitionContext.class,0);
		}
		public MemberDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_memberDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).enterMemberDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).exitMemberDeclaration(this);
		}
	}

	public final MemberDeclarationContext memberDeclaration() throws RecognitionException {
		MemberDeclarationContext _localctx = new MemberDeclarationContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_memberDeclaration);
		try {
			setState(299);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(296);
				varStatement();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(297);
				functionDefinition();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(298);
				constructorDefinition();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ProgramContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(MleseParser.EOF, 0); }
		public List<ProgramSectionContext> programSection() {
			return getRuleContexts(ProgramSectionContext.class);
		}
		public ProgramSectionContext programSection(int i) {
			return getRuleContext(ProgramSectionContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).exitProgram(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(304);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BOOL) | (1L << INT) | (1L << STRING) | (1L << VOID) | (1L << CLASS) | (1L << ID))) != 0)) {
				{
				{
				setState(301);
				programSection();
				}
				}
				setState(306);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(307);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ProgramSectionContext extends ParserRuleContext {
		public ClassDefinitionContext classDefinition() {
			return getRuleContext(ClassDefinitionContext.class,0);
		}
		public FunctionDefinitionContext functionDefinition() {
			return getRuleContext(FunctionDefinitionContext.class,0);
		}
		public VarStatementContext varStatement() {
			return getRuleContext(VarStatementContext.class,0);
		}
		public ProgramSectionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_programSection; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).enterProgramSection(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MleseListener ) ((MleseListener)listener).exitProgramSection(this);
		}
	}

	public final ProgramSectionContext programSection() throws RecognitionException {
		ProgramSectionContext _localctx = new ProgramSectionContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_programSection);
		try {
			setState(312);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(309);
				classDefinition();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(310);
				functionDefinition();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(311);
				varStatement();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 1:
			return typeSpecifier_sempred((TypeSpecifierContext)_localctx, predIndex);
		case 6:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean typeSpecifier_sempred(TypeSpecifierContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return precpred(_ctx, 15);
		case 2:
			return precpred(_ctx, 14);
		case 3:
			return precpred(_ctx, 13);
		case 4:
			return precpred(_ctx, 12);
		case 5:
			return precpred(_ctx, 11);
		case 6:
			return precpred(_ctx, 10);
		case 7:
			return precpred(_ctx, 9);
		case 8:
			return precpred(_ctx, 8);
		case 9:
			return precpred(_ctx, 7);
		case 10:
			return precpred(_ctx, 6);
		case 11:
			return precpred(_ctx, 5);
		case 12:
			return precpred(_ctx, 23);
		case 13:
			return precpred(_ctx, 21);
		case 14:
			return precpred(_ctx, 20);
		case 15:
			return precpred(_ctx, 19);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3:\u013d\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\3\2\3\2\3\2\3\2\3\2\5\2.\n\2\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\7\3\66\n\3\f\3\16\39\13\3\3\4\3\4\3\4\7\4>\n\4\f\4\16\4A\13\4\3\5"+
		"\3\5\3\5\7\5F\n\5\f\5\16\5I\13\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3"+
		"\6\6\6U\n\6\r\6\16\6V\3\6\3\6\7\6[\n\6\f\6\16\6^\13\6\3\6\5\6a\n\6\3\7"+
		"\3\7\3\7\3\7\3\7\7\7h\n\7\f\7\16\7k\13\7\3\7\3\7\6\7o\n\7\r\7\16\7p\3"+
		"\7\3\7\3\7\3\7\6\7w\n\7\r\7\16\7x\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5\b\u008b\n\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5\b"+
		"\u00b6\n\b\3\b\3\b\3\b\3\b\3\b\3\b\7\b\u00be\n\b\f\b\16\b\u00c1\13\b\3"+
		"\t\3\t\3\t\3\t\3\t\5\t\u00c8\n\t\3\n\3\n\3\n\3\13\3\13\3\13\5\13\u00d0"+
		"\n\13\3\13\3\13\3\f\3\f\7\f\u00d6\n\f\f\f\16\f\u00d9\13\f\3\f\3\f\3\r"+
		"\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u00e8\n\r\3\r\3\r\3\r\3\r"+
		"\3\r\3\r\3\r\3\r\3\r\5\r\u00f3\n\r\3\r\3\r\5\r\u00f7\n\r\3\r\3\r\5\r\u00fb"+
		"\n\r\3\r\3\r\3\r\3\r\5\r\u0101\n\r\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u0109\n"+
		"\r\3\16\3\16\5\16\u010d\n\16\3\17\3\17\3\17\3\17\5\17\u0113\n\17\3\17"+
		"\3\17\3\17\3\20\3\20\3\20\5\20\u011b\n\20\3\20\3\20\3\20\3\21\3\21\3\21"+
		"\3\21\7\21\u0124\n\21\f\21\16\21\u0127\13\21\3\21\3\21\3\22\3\22\3\22"+
		"\5\22\u012e\n\22\3\23\7\23\u0131\n\23\f\23\16\23\u0134\13\23\3\23\3\23"+
		"\3\24\3\24\3\24\5\24\u013b\n\24\3\24\2\4\4\16\25\2\4\6\b\n\f\16\20\22"+
		"\24\26\30\32\34\36 \"$&\2\t\3\2\64\65\3\2\37 \4\2,,//\3\2!#\3\2-.\4\2"+
		"$%()\3\2&\'\2\u016b\2-\3\2\2\2\4/\3\2\2\2\6:\3\2\2\2\bB\3\2\2\2\n`\3\2"+
		"\2\2\fb\3\2\2\2\16\u008a\3\2\2\2\20\u00c7\3\2\2\2\22\u00c9\3\2\2\2\24"+
		"\u00cc\3\2\2\2\26\u00d3\3\2\2\2\30\u0108\3\2\2\2\32\u010c\3\2\2\2\34\u010e"+
		"\3\2\2\2\36\u0117\3\2\2\2 \u011f\3\2\2\2\"\u012d\3\2\2\2$\u0132\3\2\2"+
		"\2&\u013a\3\2\2\2(.\7\f\2\2).\7\r\2\2*.\7\17\2\2+.\7\16\2\2,.\7\66\2\2"+
		"-(\3\2\2\2-)\3\2\2\2-*\3\2\2\2-+\3\2\2\2-,\3\2\2\2.\3\3\2\2\2/\60\b\3"+
		"\1\2\60\61\5\2\2\2\61\67\3\2\2\2\62\63\f\4\2\2\63\64\7\n\2\2\64\66\7\13"+
		"\2\2\65\62\3\2\2\2\669\3\2\2\2\67\65\3\2\2\2\678\3\2\2\28\5\3\2\2\29\67"+
		"\3\2\2\2:?\5\16\b\2;<\7\3\2\2<>\5\16\b\2=;\3\2\2\2>A\3\2\2\2?=\3\2\2\2"+
		"?@\3\2\2\2@\7\3\2\2\2A?\3\2\2\2BG\5\22\n\2CD\7\3\2\2DF\5\22\n\2EC\3\2"+
		"\2\2FI\3\2\2\2GE\3\2\2\2GH\3\2\2\2H\t\3\2\2\2IG\3\2\2\2Ja\5\f\7\2KL\5"+
		"\2\2\2LM\7\4\2\2MN\7\5\2\2Na\3\2\2\2OT\5\2\2\2PQ\7\n\2\2QR\5\16\b\2RS"+
		"\7\13\2\2SU\3\2\2\2TP\3\2\2\2UV\3\2\2\2VT\3\2\2\2VW\3\2\2\2W\\\3\2\2\2"+
		"XY\7\n\2\2Y[\7\13\2\2ZX\3\2\2\2[^\3\2\2\2\\Z\3\2\2\2\\]\3\2\2\2]a\3\2"+
		"\2\2^\\\3\2\2\2_a\5\2\2\2`J\3\2\2\2`K\3\2\2\2`O\3\2\2\2`_\3\2\2\2a\13"+
		"\3\2\2\2bi\5\2\2\2cd\7\n\2\2de\5\16\b\2ef\7\13\2\2fh\3\2\2\2gc\3\2\2\2"+
		"hk\3\2\2\2ig\3\2\2\2ij\3\2\2\2jn\3\2\2\2ki\3\2\2\2lm\7\n\2\2mo\7\13\2"+
		"\2nl\3\2\2\2op\3\2\2\2pn\3\2\2\2pq\3\2\2\2qv\3\2\2\2rs\7\n\2\2st\5\16"+
		"\b\2tu\7\13\2\2uw\3\2\2\2vr\3\2\2\2wx\3\2\2\2xv\3\2\2\2xy\3\2\2\2y\r\3"+
		"\2\2\2z{\b\b\1\2{|\7\31\2\2|\u008b\5\n\6\2}~\t\2\2\2~\u008b\5\16\b\24"+
		"\177\u0080\t\3\2\2\u0080\u008b\5\16\b\23\u0081\u0082\t\4\2\2\u0082\u008b"+
		"\5\16\b\22\u0083\u0084\7\4\2\2\u0084\u0085\5\16\b\2\u0085\u0086\7\5\2"+
		"\2\u0086\u008b\3\2\2\2\u0087\u008b\5\20\t\2\u0088\u008b\7\33\2\2\u0089"+
		"\u008b\7\66\2\2\u008az\3\2\2\2\u008a}\3\2\2\2\u008a\177\3\2\2\2\u008a"+
		"\u0081\3\2\2\2\u008a\u0083\3\2\2\2\u008a\u0087\3\2\2\2\u008a\u0088\3\2"+
		"\2\2\u008a\u0089\3\2\2\2\u008b\u00bf\3\2\2\2\u008c\u008d\f\21\2\2\u008d"+
		"\u008e\t\5\2\2\u008e\u00be\5\16\b\22\u008f\u0090\f\20\2\2\u0090\u0091"+
		"\t\3\2\2\u0091\u00be\5\16\b\21\u0092\u0093\f\17\2\2\u0093\u0094\t\6\2"+
		"\2\u0094\u00be\5\16\b\20\u0095\u0096\f\16\2\2\u0096\u0097\t\7\2\2\u0097"+
		"\u00be\5\16\b\17\u0098\u0099\f\r\2\2\u0099\u009a\t\b\2\2\u009a\u00be\5"+
		"\16\b\16\u009b\u009c\f\f\2\2\u009c\u009d\7\62\2\2\u009d\u00be\5\16\b\r"+
		"\u009e\u009f\f\13\2\2\u009f\u00a0\7\61\2\2\u00a0\u00be\5\16\b\f\u00a1"+
		"\u00a2\f\n\2\2\u00a2\u00a3\7\60\2\2\u00a3\u00be\5\16\b\13\u00a4\u00a5"+
		"\f\t\2\2\u00a5\u00a6\7*\2\2\u00a6\u00be\5\16\b\n\u00a7\u00a8\f\b\2\2\u00a8"+
		"\u00a9\7+\2\2\u00a9\u00be\5\16\b\t\u00aa\u00ab\f\7\2\2\u00ab\u00ac\7\63"+
		"\2\2\u00ac\u00be\5\16\b\7\u00ad\u00ae\f\31\2\2\u00ae\u00be\t\2\2\2\u00af"+
		"\u00b0\f\27\2\2\u00b0\u00b1\7\6\2\2\u00b1\u00be\7\66\2\2\u00b2\u00b3\f"+
		"\26\2\2\u00b3\u00b5\7\4\2\2\u00b4\u00b6\5\6\4\2\u00b5\u00b4\3\2\2\2\u00b5"+
		"\u00b6\3\2\2\2\u00b6\u00b7\3\2\2\2\u00b7\u00be\7\5\2\2\u00b8\u00b9\f\25"+
		"\2\2\u00b9\u00ba\7\n\2\2\u00ba\u00bb\5\16\b\2\u00bb\u00bc\7\13\2\2\u00bc"+
		"\u00be\3\2\2\2\u00bd\u008c\3\2\2\2\u00bd\u008f\3\2\2\2\u00bd\u0092\3\2"+
		"\2\2\u00bd\u0095\3\2\2\2\u00bd\u0098\3\2\2\2\u00bd\u009b\3\2\2\2\u00bd"+
		"\u009e\3\2\2\2\u00bd\u00a1\3\2\2\2\u00bd\u00a4\3\2\2\2\u00bd\u00a7\3\2"+
		"\2\2\u00bd\u00aa\3\2\2\2\u00bd\u00ad\3\2\2\2\u00bd\u00af\3\2\2\2\u00bd"+
		"\u00b2\3\2\2\2\u00bd\u00b8\3\2\2\2\u00be\u00c1\3\2\2\2\u00bf\u00bd\3\2"+
		"\2\2\u00bf\u00c0\3\2\2\2\u00c0\17\3\2\2\2\u00c1\u00bf\3\2\2\2\u00c2\u00c8"+
		"\7\20\2\2\u00c3\u00c8\7\21\2\2\u00c4\u00c8\7\34\2\2\u00c5\u00c8\7\35\2"+
		"\2\u00c6\u00c8\7\36\2\2\u00c7\u00c2\3\2\2\2\u00c7\u00c3\3\2\2\2\u00c7"+
		"\u00c4\3\2\2\2\u00c7\u00c5\3\2\2\2\u00c7\u00c6\3\2\2\2\u00c8\21\3\2\2"+
		"\2\u00c9\u00ca\5\4\3\2\u00ca\u00cb\7\66\2\2\u00cb\23\3\2\2\2\u00cc\u00cf"+
		"\5\22\n\2\u00cd\u00ce\7\63\2\2\u00ce\u00d0\5\16\b\2\u00cf\u00cd\3\2\2"+
		"\2\u00cf\u00d0\3\2\2\2\u00d0\u00d1\3\2\2\2\u00d1\u00d2\7\7\2\2\u00d2\25"+
		"\3\2\2\2\u00d3\u00d7\7\b\2\2\u00d4\u00d6\5\32\16\2\u00d5\u00d4\3\2\2\2"+
		"\u00d6\u00d9\3\2\2\2\u00d7\u00d5\3\2\2\2\u00d7\u00d8\3\2\2\2\u00d8\u00da"+
		"\3\2\2\2\u00d9\u00d7\3\2\2\2\u00da\u00db\7\t\2\2\u00db\27\3\2\2\2\u00dc"+
		"\u0109\5\26\f\2\u00dd\u00de\5\16\b\2\u00de\u00df\7\7\2\2\u00df\u0109\3"+
		"\2\2\2\u00e0\u00e1\7\22\2\2\u00e1\u00e2\7\4\2\2\u00e2\u00e3\5\16\b\2\u00e3"+
		"\u00e4\7\5\2\2\u00e4\u00e7\5\30\r\2\u00e5\u00e6\7\23\2\2\u00e6\u00e8\5"+
		"\30\r\2\u00e7\u00e5\3\2\2\2\u00e7\u00e8\3\2\2\2\u00e8\u0109\3\2\2\2\u00e9"+
		"\u00ea\7\25\2\2\u00ea\u00eb\7\4\2\2\u00eb\u00ec\5\16\b\2\u00ec\u00ed\7"+
		"\5\2\2\u00ed\u00ee\5\30\r\2\u00ee\u0109\3\2\2\2\u00ef\u00f0\7\24\2\2\u00f0"+
		"\u00f2\7\4\2\2\u00f1\u00f3\5\16\b\2\u00f2\u00f1\3\2\2\2\u00f2\u00f3\3"+
		"\2\2\2\u00f3\u00f4\3\2\2\2\u00f4\u00f6\7\7\2\2\u00f5\u00f7\5\16\b\2\u00f6"+
		"\u00f5\3\2\2\2\u00f6\u00f7\3\2\2\2\u00f7\u00f8\3\2\2\2\u00f8\u00fa\7\7"+
		"\2\2\u00f9\u00fb\5\16\b\2\u00fa\u00f9\3\2\2\2\u00fa\u00fb\3\2\2\2\u00fb"+
		"\u00fc\3\2\2\2\u00fc\u00fd\7\5\2\2\u00fd\u0109\5\30\r\2\u00fe\u0100\7"+
		"\30\2\2\u00ff\u0101\5\16\b\2\u0100\u00ff\3\2\2\2\u0100\u0101\3\2\2\2\u0101"+
		"\u0102\3\2\2\2\u0102\u0109\7\7\2\2\u0103\u0104\7\26\2\2\u0104\u0109\7"+
		"\7\2\2\u0105\u0106\7\27\2\2\u0106\u0109\7\7\2\2\u0107\u0109\7\7\2\2\u0108"+
		"\u00dc\3\2\2\2\u0108\u00dd\3\2\2\2\u0108\u00e0\3\2\2\2\u0108\u00e9\3\2"+
		"\2\2\u0108\u00ef\3\2\2\2\u0108\u00fe\3\2\2\2\u0108\u0103\3\2\2\2\u0108"+
		"\u0105\3\2\2\2\u0108\u0107\3\2\2\2\u0109\31\3\2\2\2\u010a\u010d\5\30\r"+
		"\2\u010b\u010d\5\24\13\2\u010c\u010a\3\2\2\2\u010c\u010b\3\2\2\2\u010d"+
		"\33\3\2\2\2\u010e\u010f\5\4\3\2\u010f\u0110\7\66\2\2\u0110\u0112\7\4\2"+
		"\2\u0111\u0113\5\b\5\2\u0112\u0111\3\2\2\2\u0112\u0113\3\2\2\2\u0113\u0114"+
		"\3\2\2\2\u0114\u0115\7\5\2\2\u0115\u0116\5\26\f\2\u0116\35\3\2\2\2\u0117"+
		"\u0118\7\66\2\2\u0118\u011a\7\4\2\2\u0119\u011b\5\b\5\2\u011a\u0119\3"+
		"\2\2\2\u011a\u011b\3\2\2\2\u011b\u011c\3\2\2\2\u011c\u011d\7\5\2\2\u011d"+
		"\u011e\5\26\f\2\u011e\37\3\2\2\2\u011f\u0120\7\32\2\2\u0120\u0121\7\66"+
		"\2\2\u0121\u0125\7\b\2\2\u0122\u0124\5\"\22\2\u0123\u0122\3\2\2\2\u0124"+
		"\u0127\3\2\2\2\u0125\u0123\3\2\2\2\u0125\u0126\3\2\2\2\u0126\u0128\3\2"+
		"\2\2\u0127\u0125\3\2\2\2\u0128\u0129\7\t\2\2\u0129!\3\2\2\2\u012a\u012e"+
		"\5\24\13\2\u012b\u012e\5\34\17\2\u012c\u012e\5\36\20\2\u012d\u012a\3\2"+
		"\2\2\u012d\u012b\3\2\2\2\u012d\u012c\3\2\2\2\u012e#\3\2\2\2\u012f\u0131"+
		"\5&\24\2\u0130\u012f\3\2\2\2\u0131\u0134\3\2\2\2\u0132\u0130\3\2\2\2\u0132"+
		"\u0133\3\2\2\2\u0133\u0135\3\2\2\2\u0134\u0132\3\2\2\2\u0135\u0136\7\2"+
		"\2\3\u0136%\3\2\2\2\u0137\u013b\5 \21\2\u0138\u013b\5\34\17\2\u0139\u013b"+
		"\5\24\13\2\u013a\u0137\3\2\2\2\u013a\u0138\3\2\2\2\u013a\u0139\3\2\2\2"+
		"\u013b\'\3\2\2\2 -\67?GV\\`ipx\u008a\u00b5\u00bd\u00bf\u00c7\u00cf\u00d7"+
		"\u00e7\u00f2\u00f6\u00fa\u0100\u0108\u010c\u0112\u011a\u0125\u012d\u0132"+
		"\u013a";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}