// Generated from C:/Users/soodo/Documents/MLeseCompiler\Mlese.g4 by ANTLR 4.7.2
package net.qdsu.Mlese.Parser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MleseLexer extends Lexer {
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
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "LBRACK", "RBRACK", 
			"BOOL", "INT", "STRING", "VOID", "TRUE", "FALSE", "IF", "ELSE", "FOR", 
			"WHILE", "BREAK", "CONTINUE", "RETURN", "NEW", "CLASS", "THIS", "INTCONST", 
			"STRINGCONST", "NULLCONST", "PLUS", "MINUS", "STAR", "DIV", "MOD", "LT", 
			"GT", "EQ", "NE", "GE", "LE", "ANDAND", "OROR", "NOT", "LSHIFT", "RSHIFT", 
			"TILDE", "OR", "CARET", "AND", "ASSIGN", "PLUSPLUS", "MINUSMINUS", "ID", 
			"ESC", "IdentifierNondigit", "NonzeroDigit", "Digit", "Whitespace", "Newline", 
			"BlockComment", "LineComment"
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


	public MleseLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Mlese.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2:\u017d\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3"+
		"\n\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3"+
		"\r\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3"+
		"\20\3\20\3\20\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3"+
		"\23\3\24\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\25\3\26\3"+
		"\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\27\3\27\3"+
		"\27\3\30\3\30\3\30\3\30\3\31\3\31\3\31\3\31\3\31\3\31\3\32\3\32\3\32\3"+
		"\32\3\32\3\33\3\33\7\33\u00e7\n\33\f\33\16\33\u00ea\13\33\3\33\3\33\3"+
		"\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\5\33\u00f8\n\33\3\34"+
		"\3\34\3\34\7\34\u00fd\n\34\f\34\16\34\u0100\13\34\3\34\3\34\3\35\3\35"+
		"\3\35\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3#\3#\3$\3$\3"+
		"%\3%\3%\3&\3&\3&\3\'\3\'\3\'\3(\3(\3(\3)\3)\3)\3*\3*\3*\3+\3+\3,\3,\3"+
		",\3-\3-\3-\3.\3.\3/\3/\3\60\3\60\3\61\3\61\3\62\3\62\3\63\3\63\3\63\3"+
		"\64\3\64\3\64\3\65\3\65\3\65\7\65\u0144\n\65\f\65\16\65\u0147\13\65\3"+
		"\66\3\66\3\66\3\66\3\66\3\66\5\66\u014f\n\66\3\67\3\67\38\38\39\39\3:"+
		"\6:\u0158\n:\r:\16:\u0159\3:\3:\3;\5;\u015f\n;\3;\3;\3;\3;\3<\3<\3<\3"+
		"<\7<\u0169\n<\f<\16<\u016c\13<\3<\3<\3<\3<\3<\3=\3=\3=\3=\7=\u0177\n="+
		"\f=\16=\u017a\13=\3=\3=\4\u00fe\u016a\2>\3\3\5\4\7\5\t\6\13\7\r\b\17\t"+
		"\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27"+
		"-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W"+
		"-Y.[/]\60_\61a\62c\63e\64g\65i\66k\2m\2o\2q\2s\67u8w9y:\3\2\7\5\2C\\a"+
		"ac|\3\2\63;\3\2\62;\4\2\13\13\"\"\4\2\f\f\17\17\2\u0185\2\3\3\2\2\2\2"+
		"\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2"+
		"\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2"+
		"\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2"+
		"\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2"+
		"\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2"+
		"\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2"+
		"K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3"+
		"\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2"+
		"\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\2s\3\2\2\2\2u\3\2\2\2\2w\3\2\2\2\2"+
		"y\3\2\2\2\3{\3\2\2\2\5}\3\2\2\2\7\177\3\2\2\2\t\u0081\3\2\2\2\13\u0083"+
		"\3\2\2\2\r\u0085\3\2\2\2\17\u0087\3\2\2\2\21\u0089\3\2\2\2\23\u008b\3"+
		"\2\2\2\25\u008d\3\2\2\2\27\u0092\3\2\2\2\31\u0096\3\2\2\2\33\u009d\3\2"+
		"\2\2\35\u00a2\3\2\2\2\37\u00a7\3\2\2\2!\u00ad\3\2\2\2#\u00b0\3\2\2\2%"+
		"\u00b5\3\2\2\2\'\u00b9\3\2\2\2)\u00bf\3\2\2\2+\u00c5\3\2\2\2-\u00ce\3"+
		"\2\2\2/\u00d5\3\2\2\2\61\u00d9\3\2\2\2\63\u00df\3\2\2\2\65\u00f7\3\2\2"+
		"\2\67\u00f9\3\2\2\29\u0103\3\2\2\2;\u0108\3\2\2\2=\u010a\3\2\2\2?\u010c"+
		"\3\2\2\2A\u010e\3\2\2\2C\u0110\3\2\2\2E\u0112\3\2\2\2G\u0114\3\2\2\2I"+
		"\u0116\3\2\2\2K\u0119\3\2\2\2M\u011c\3\2\2\2O\u011f\3\2\2\2Q\u0122\3\2"+
		"\2\2S\u0125\3\2\2\2U\u0128\3\2\2\2W\u012a\3\2\2\2Y\u012d\3\2\2\2[\u0130"+
		"\3\2\2\2]\u0132\3\2\2\2_\u0134\3\2\2\2a\u0136\3\2\2\2c\u0138\3\2\2\2e"+
		"\u013a\3\2\2\2g\u013d\3\2\2\2i\u0140\3\2\2\2k\u014e\3\2\2\2m\u0150\3\2"+
		"\2\2o\u0152\3\2\2\2q\u0154\3\2\2\2s\u0157\3\2\2\2u\u015e\3\2\2\2w\u0164"+
		"\3\2\2\2y\u0172\3\2\2\2{|\7.\2\2|\4\3\2\2\2}~\7*\2\2~\6\3\2\2\2\177\u0080"+
		"\7+\2\2\u0080\b\3\2\2\2\u0081\u0082\7\60\2\2\u0082\n\3\2\2\2\u0083\u0084"+
		"\7=\2\2\u0084\f\3\2\2\2\u0085\u0086\7}\2\2\u0086\16\3\2\2\2\u0087\u0088"+
		"\7\177\2\2\u0088\20\3\2\2\2\u0089\u008a\7]\2\2\u008a\22\3\2\2\2\u008b"+
		"\u008c\7_\2\2\u008c\24\3\2\2\2\u008d\u008e\7d\2\2\u008e\u008f\7q\2\2\u008f"+
		"\u0090\7q\2\2\u0090\u0091\7n\2\2\u0091\26\3\2\2\2\u0092\u0093\7k\2\2\u0093"+
		"\u0094\7p\2\2\u0094\u0095\7v\2\2\u0095\30\3\2\2\2\u0096\u0097\7u\2\2\u0097"+
		"\u0098\7v\2\2\u0098\u0099\7t\2\2\u0099\u009a\7k\2\2\u009a\u009b\7p\2\2"+
		"\u009b\u009c\7i\2\2\u009c\32\3\2\2\2\u009d\u009e\7x\2\2\u009e\u009f\7"+
		"q\2\2\u009f\u00a0\7k\2\2\u00a0\u00a1\7f\2\2\u00a1\34\3\2\2\2\u00a2\u00a3"+
		"\7v\2\2\u00a3\u00a4\7t\2\2\u00a4\u00a5\7w\2\2\u00a5\u00a6\7g\2\2\u00a6"+
		"\36\3\2\2\2\u00a7\u00a8\7h\2\2\u00a8\u00a9\7c\2\2\u00a9\u00aa\7n\2\2\u00aa"+
		"\u00ab\7u\2\2\u00ab\u00ac\7g\2\2\u00ac \3\2\2\2\u00ad\u00ae\7k\2\2\u00ae"+
		"\u00af\7h\2\2\u00af\"\3\2\2\2\u00b0\u00b1\7g\2\2\u00b1\u00b2\7n\2\2\u00b2"+
		"\u00b3\7u\2\2\u00b3\u00b4\7g\2\2\u00b4$\3\2\2\2\u00b5\u00b6\7h\2\2\u00b6"+
		"\u00b7\7q\2\2\u00b7\u00b8\7t\2\2\u00b8&\3\2\2\2\u00b9\u00ba\7y\2\2\u00ba"+
		"\u00bb\7j\2\2\u00bb\u00bc\7k\2\2\u00bc\u00bd\7n\2\2\u00bd\u00be\7g\2\2"+
		"\u00be(\3\2\2\2\u00bf\u00c0\7d\2\2\u00c0\u00c1\7t\2\2\u00c1\u00c2\7g\2"+
		"\2\u00c2\u00c3\7c\2\2\u00c3\u00c4\7m\2\2\u00c4*\3\2\2\2\u00c5\u00c6\7"+
		"e\2\2\u00c6\u00c7\7q\2\2\u00c7\u00c8\7p\2\2\u00c8\u00c9\7v\2\2\u00c9\u00ca"+
		"\7k\2\2\u00ca\u00cb\7p\2\2\u00cb\u00cc\7w\2\2\u00cc\u00cd\7g\2\2\u00cd"+
		",\3\2\2\2\u00ce\u00cf\7t\2\2\u00cf\u00d0\7g\2\2\u00d0\u00d1\7v\2\2\u00d1"+
		"\u00d2\7w\2\2\u00d2\u00d3\7t\2\2\u00d3\u00d4\7p\2\2\u00d4.\3\2\2\2\u00d5"+
		"\u00d6\7p\2\2\u00d6\u00d7\7g\2\2\u00d7\u00d8\7y\2\2\u00d8\60\3\2\2\2\u00d9"+
		"\u00da\7e\2\2\u00da\u00db\7n\2\2\u00db\u00dc\7c\2\2\u00dc\u00dd\7u\2\2"+
		"\u00dd\u00de\7u\2\2\u00de\62\3\2\2\2\u00df\u00e0\7v\2\2\u00e0\u00e1\7"+
		"j\2\2\u00e1\u00e2\7k\2\2\u00e2\u00e3\7u\2\2\u00e3\64\3\2\2\2\u00e4\u00e8"+
		"\5o8\2\u00e5\u00e7\5q9\2\u00e6\u00e5\3\2\2\2\u00e7\u00ea\3\2\2\2\u00e8"+
		"\u00e6\3\2\2\2\u00e8\u00e9\3\2\2\2\u00e9\u00f8\3\2\2\2\u00ea\u00e8\3\2"+
		"\2\2\u00eb\u00ec\7/\2\2\u00ec\u00ed\7\64\2\2\u00ed\u00ee\7\63\2\2\u00ee"+
		"\u00ef\7\66\2\2\u00ef\u00f0\79\2\2\u00f0\u00f1\7\66\2\2\u00f1\u00f2\7"+
		":\2\2\u00f2\u00f3\7\65\2\2\u00f3\u00f4\78\2\2\u00f4\u00f5\7\66\2\2\u00f5"+
		"\u00f8\7:\2\2\u00f6\u00f8\7\62\2\2\u00f7\u00e4\3\2\2\2\u00f7\u00eb\3\2"+
		"\2\2\u00f7\u00f6\3\2\2\2\u00f8\66\3\2\2\2\u00f9\u00fe\7$\2\2\u00fa\u00fd"+
		"\5k\66\2\u00fb\u00fd\13\2\2\2\u00fc\u00fa\3\2\2\2\u00fc\u00fb\3\2\2\2"+
		"\u00fd\u0100\3\2\2\2\u00fe\u00ff\3\2\2\2\u00fe\u00fc\3\2\2\2\u00ff\u0101"+
		"\3\2\2\2\u0100\u00fe\3\2\2\2\u0101\u0102\7$\2\2\u01028\3\2\2\2\u0103\u0104"+
		"\7p\2\2\u0104\u0105\7w\2\2\u0105\u0106\7n\2\2\u0106\u0107\7n\2\2\u0107"+
		":\3\2\2\2\u0108\u0109\7-\2\2\u0109<\3\2\2\2\u010a\u010b\7/\2\2\u010b>"+
		"\3\2\2\2\u010c\u010d\7,\2\2\u010d@\3\2\2\2\u010e\u010f\7\61\2\2\u010f"+
		"B\3\2\2\2\u0110\u0111\7\'\2\2\u0111D\3\2\2\2\u0112\u0113\7>\2\2\u0113"+
		"F\3\2\2\2\u0114\u0115\7@\2\2\u0115H\3\2\2\2\u0116\u0117\7?\2\2\u0117\u0118"+
		"\7?\2\2\u0118J\3\2\2\2\u0119\u011a\7#\2\2\u011a\u011b\7?\2\2\u011bL\3"+
		"\2\2\2\u011c\u011d\7@\2\2\u011d\u011e\7?\2\2\u011eN\3\2\2\2\u011f\u0120"+
		"\7>\2\2\u0120\u0121\7?\2\2\u0121P\3\2\2\2\u0122\u0123\7(\2\2\u0123\u0124"+
		"\7(\2\2\u0124R\3\2\2\2\u0125\u0126\7~\2\2\u0126\u0127\7~\2\2\u0127T\3"+
		"\2\2\2\u0128\u0129\7#\2\2\u0129V\3\2\2\2\u012a\u012b\7>\2\2\u012b\u012c"+
		"\7>\2\2\u012cX\3\2\2\2\u012d\u012e\7@\2\2\u012e\u012f\7@\2\2\u012fZ\3"+
		"\2\2\2\u0130\u0131\7\u0080\2\2\u0131\\\3\2\2\2\u0132\u0133\7~\2\2\u0133"+
		"^\3\2\2\2\u0134\u0135\7`\2\2\u0135`\3\2\2\2\u0136\u0137\7(\2\2\u0137b"+
		"\3\2\2\2\u0138\u0139\7?\2\2\u0139d\3\2\2\2\u013a\u013b\7-\2\2\u013b\u013c"+
		"\7-\2\2\u013cf\3\2\2\2\u013d\u013e\7/\2\2\u013e\u013f\7/\2\2\u013fh\3"+
		"\2\2\2\u0140\u0145\5m\67\2\u0141\u0144\5m\67\2\u0142\u0144\5q9\2\u0143"+
		"\u0141\3\2\2\2\u0143\u0142\3\2\2\2\u0144\u0147\3\2\2\2\u0145\u0143\3\2"+
		"\2\2\u0145\u0146\3\2\2\2\u0146j\3\2\2\2\u0147\u0145\3\2\2\2\u0148\u0149"+
		"\7^\2\2\u0149\u014f\7$\2\2\u014a\u014b\7^\2\2\u014b\u014f\7^\2\2\u014c"+
		"\u014d\7^\2\2\u014d\u014f\7p\2\2\u014e\u0148\3\2\2\2\u014e\u014a\3\2\2"+
		"\2\u014e\u014c\3\2\2\2\u014fl\3\2\2\2\u0150\u0151\t\2\2\2\u0151n\3\2\2"+
		"\2\u0152\u0153\t\3\2\2\u0153p\3\2\2\2\u0154\u0155\t\4\2\2\u0155r\3\2\2"+
		"\2\u0156\u0158\t\5\2\2\u0157\u0156\3\2\2\2\u0158\u0159\3\2\2\2\u0159\u0157"+
		"\3\2\2\2\u0159\u015a\3\2\2\2\u015a\u015b\3\2\2\2\u015b\u015c\b:\2\2\u015c"+
		"t\3\2\2\2\u015d\u015f\7\17\2\2\u015e\u015d\3\2\2\2\u015e\u015f\3\2\2\2"+
		"\u015f\u0160\3\2\2\2\u0160\u0161\7\f\2\2\u0161\u0162\3\2\2\2\u0162\u0163"+
		"\b;\2\2\u0163v\3\2\2\2\u0164\u0165\7\61\2\2\u0165\u0166\7,\2\2\u0166\u016a"+
		"\3\2\2\2\u0167\u0169\13\2\2\2\u0168\u0167\3\2\2\2\u0169\u016c\3\2\2\2"+
		"\u016a\u016b\3\2\2\2\u016a\u0168\3\2\2\2\u016b\u016d\3\2\2\2\u016c\u016a"+
		"\3\2\2\2\u016d\u016e\7,\2\2\u016e\u016f\7\61\2\2\u016f\u0170\3\2\2\2\u0170"+
		"\u0171\b<\2\2\u0171x\3\2\2\2\u0172\u0173\7\61\2\2\u0173\u0174\7\61\2\2"+
		"\u0174\u0178\3\2\2\2\u0175\u0177\n\6\2\2\u0176\u0175\3\2\2\2\u0177\u017a"+
		"\3\2\2\2\u0178\u0176\3\2\2\2\u0178\u0179\3\2\2\2\u0179\u017b\3\2\2\2\u017a"+
		"\u0178\3\2\2\2\u017b\u017c\b=\2\2\u017cz\3\2\2\2\16\2\u00e8\u00f7\u00fc"+
		"\u00fe\u0143\u0145\u014e\u0159\u015e\u016a\u0178\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}