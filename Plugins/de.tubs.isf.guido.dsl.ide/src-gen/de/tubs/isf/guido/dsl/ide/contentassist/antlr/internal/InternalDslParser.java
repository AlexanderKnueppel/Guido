package de.tubs.isf.guido.dsl.ide.contentassist.antlr.internal;

import java.io.InputStream;
import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.AbstractInternalContentAssistParser;
import org.eclipse.xtext.ide.editor.contentassist.antlr.internal.DFA;
import de.tubs.isf.guido.dsl.services.DslGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalDslParser extends AbstractInternalContentAssistParser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_ANY_OTHER", "RULE_ID", "RULE_INT", 
		"RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_STRING", "RULE_WS", "','", 
		"'.'", "'.*'", "':'", "';'", "'Constructs'", "'Databases'", "'Definition'", 
		"'Description'", "'Hypothesis'", "'Import'", "'Parameter'", "'System'", 
		"'['", "']'", "'effective'", "'efficient'", "'is'", "'less'", "'more'", 
		"'on'", "'than'", "'{'", "'}'"
	};
	public static final int EOF=-1;
	public static final int T__11=11;
	public static final int T__12=12;
	public static final int T__13=13;
	public static final int T__14=14;
	public static final int T__15=15;
	public static final int T__16=16;
	public static final int T__17=17;
	public static final int T__18=18;
	public static final int T__19=19;
	public static final int T__20=20;
	public static final int T__21=21;
	public static final int T__22=22;
	public static final int T__23=23;
	public static final int T__24=24;
	public static final int T__25=25;
	public static final int T__26=26;
	public static final int T__27=27;
	public static final int T__28=28;
	public static final int T__29=29;
	public static final int T__30=30;
	public static final int T__31=31;
	public static final int T__32=32;
	public static final int T__33=33;
	public static final int T__34=34;
	public static final int RULE_ANY_OTHER=4;
	public static final int RULE_ID=5;
	public static final int RULE_INT=6;
	public static final int RULE_ML_COMMENT=7;
	public static final int RULE_SL_COMMENT=8;
	public static final int RULE_STRING=9;
	public static final int RULE_WS=10;

	// delegates
	public AbstractInternalContentAssistParser[] getDelegates() {
		return new AbstractInternalContentAssistParser[] {};
	}

	// delegators


	public InternalDslParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public InternalDslParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	@Override public String[] getTokenNames() { return InternalDslParser.tokenNames; }
	@Override public String getGrammarFileName() { return "InternalDsl.g"; }


		private DslGrammarAccess grammarAccess;

		public void setGrammarAccess(DslGrammarAccess grammarAccess) {
			this.grammarAccess = grammarAccess;
		}

		@Override
		protected Grammar getGrammar() {
			return grammarAccess.getGrammar();
		}

		@Override
		protected String getValueForTokenName(String tokenName) {
			return tokenName;
		}



	// $ANTLR start "entryRuleDsl"
	// InternalDsl.g:53:1: entryRuleDsl : ruleDsl EOF ;
	public final void entryRuleDsl() throws RecognitionException {
		try {
			// InternalDsl.g:54:1: ( ruleDsl EOF )
			// InternalDsl.g:55:1: ruleDsl EOF
			{
			 before(grammarAccess.getDslRule()); 
			pushFollow(FOLLOW_1);
			ruleDsl();
			state._fsp--;

			 after(grammarAccess.getDslRule()); 
			match(input,EOF,FOLLOW_2); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "entryRuleDsl"



	// $ANTLR start "ruleDsl"
	// InternalDsl.g:62:1: ruleDsl : ( ( rule__Dsl__ElementsAssignment )* ) ;
	public final void ruleDsl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:66:2: ( ( ( rule__Dsl__ElementsAssignment )* ) )
			// InternalDsl.g:67:2: ( ( rule__Dsl__ElementsAssignment )* )
			{
			// InternalDsl.g:67:2: ( ( rule__Dsl__ElementsAssignment )* )
			// InternalDsl.g:68:3: ( rule__Dsl__ElementsAssignment )*
			{
			 before(grammarAccess.getDslAccess().getElementsAssignment()); 
			// InternalDsl.g:69:3: ( rule__Dsl__ElementsAssignment )*
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( (LA1_0==RULE_ID||LA1_0==17||(LA1_0 >= 20 && LA1_0 <= 21)||LA1_0==23) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// InternalDsl.g:69:4: rule__Dsl__ElementsAssignment
					{
					pushFollow(FOLLOW_3);
					rule__Dsl__ElementsAssignment();
					state._fsp--;

					}
					break;

				default :
					break loop1;
				}
			}

			 after(grammarAccess.getDslAccess().getElementsAssignment()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "ruleDsl"



	// $ANTLR start "entryRuleAbstractElement"
	// InternalDsl.g:78:1: entryRuleAbstractElement : ruleAbstractElement EOF ;
	public final void entryRuleAbstractElement() throws RecognitionException {
		try {
			// InternalDsl.g:79:1: ( ruleAbstractElement EOF )
			// InternalDsl.g:80:1: ruleAbstractElement EOF
			{
			 before(grammarAccess.getAbstractElementRule()); 
			pushFollow(FOLLOW_1);
			ruleAbstractElement();
			state._fsp--;

			 after(grammarAccess.getAbstractElementRule()); 
			match(input,EOF,FOLLOW_2); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "entryRuleAbstractElement"



	// $ANTLR start "ruleAbstractElement"
	// InternalDsl.g:87:1: ruleAbstractElement : ( ( rule__AbstractElement__Alternatives ) ) ;
	public final void ruleAbstractElement() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:91:2: ( ( ( rule__AbstractElement__Alternatives ) ) )
			// InternalDsl.g:92:2: ( ( rule__AbstractElement__Alternatives ) )
			{
			// InternalDsl.g:92:2: ( ( rule__AbstractElement__Alternatives ) )
			// InternalDsl.g:93:3: ( rule__AbstractElement__Alternatives )
			{
			 before(grammarAccess.getAbstractElementAccess().getAlternatives()); 
			// InternalDsl.g:94:3: ( rule__AbstractElement__Alternatives )
			// InternalDsl.g:94:4: rule__AbstractElement__Alternatives
			{
			pushFollow(FOLLOW_2);
			rule__AbstractElement__Alternatives();
			state._fsp--;

			}

			 after(grammarAccess.getAbstractElementAccess().getAlternatives()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "ruleAbstractElement"



	// $ANTLR start "entryRuleType"
	// InternalDsl.g:103:1: entryRuleType : ruleType EOF ;
	public final void entryRuleType() throws RecognitionException {
		try {
			// InternalDsl.g:104:1: ( ruleType EOF )
			// InternalDsl.g:105:1: ruleType EOF
			{
			 before(grammarAccess.getTypeRule()); 
			pushFollow(FOLLOW_1);
			ruleType();
			state._fsp--;

			 after(grammarAccess.getTypeRule()); 
			match(input,EOF,FOLLOW_2); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "entryRuleType"



	// $ANTLR start "ruleType"
	// InternalDsl.g:112:1: ruleType : ( ( rule__Type__Alternatives ) ) ;
	public final void ruleType() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:116:2: ( ( ( rule__Type__Alternatives ) ) )
			// InternalDsl.g:117:2: ( ( rule__Type__Alternatives ) )
			{
			// InternalDsl.g:117:2: ( ( rule__Type__Alternatives ) )
			// InternalDsl.g:118:3: ( rule__Type__Alternatives )
			{
			 before(grammarAccess.getTypeAccess().getAlternatives()); 
			// InternalDsl.g:119:3: ( rule__Type__Alternatives )
			// InternalDsl.g:119:4: rule__Type__Alternatives
			{
			pushFollow(FOLLOW_2);
			rule__Type__Alternatives();
			state._fsp--;

			}

			 after(grammarAccess.getTypeAccess().getAlternatives()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "ruleType"



	// $ANTLR start "entryRuleHypothesis"
	// InternalDsl.g:128:1: entryRuleHypothesis : ruleHypothesis EOF ;
	public final void entryRuleHypothesis() throws RecognitionException {
		try {
			// InternalDsl.g:129:1: ( ruleHypothesis EOF )
			// InternalDsl.g:130:1: ruleHypothesis EOF
			{
			 before(grammarAccess.getHypothesisRule()); 
			pushFollow(FOLLOW_1);
			ruleHypothesis();
			state._fsp--;

			 after(grammarAccess.getHypothesisRule()); 
			match(input,EOF,FOLLOW_2); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "entryRuleHypothesis"



	// $ANTLR start "ruleHypothesis"
	// InternalDsl.g:137:1: ruleHypothesis : ( ( rule__Hypothesis__Group__0 ) ) ;
	public final void ruleHypothesis() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:141:2: ( ( ( rule__Hypothesis__Group__0 ) ) )
			// InternalDsl.g:142:2: ( ( rule__Hypothesis__Group__0 ) )
			{
			// InternalDsl.g:142:2: ( ( rule__Hypothesis__Group__0 ) )
			// InternalDsl.g:143:3: ( rule__Hypothesis__Group__0 )
			{
			 before(grammarAccess.getHypothesisAccess().getGroup()); 
			// InternalDsl.g:144:3: ( rule__Hypothesis__Group__0 )
			// InternalDsl.g:144:4: rule__Hypothesis__Group__0
			{
			pushFollow(FOLLOW_2);
			rule__Hypothesis__Group__0();
			state._fsp--;

			}

			 after(grammarAccess.getHypothesisAccess().getGroup()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "ruleHypothesis"



	// $ANTLR start "entryRuleParameter"
	// InternalDsl.g:153:1: entryRuleParameter : ruleParameter EOF ;
	public final void entryRuleParameter() throws RecognitionException {
		try {
			// InternalDsl.g:154:1: ( ruleParameter EOF )
			// InternalDsl.g:155:1: ruleParameter EOF
			{
			 before(grammarAccess.getParameterRule()); 
			pushFollow(FOLLOW_1);
			ruleParameter();
			state._fsp--;

			 after(grammarAccess.getParameterRule()); 
			match(input,EOF,FOLLOW_2); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "entryRuleParameter"



	// $ANTLR start "ruleParameter"
	// InternalDsl.g:162:1: ruleParameter : ( ( rule__Parameter__NameAssignment ) ) ;
	public final void ruleParameter() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:166:2: ( ( ( rule__Parameter__NameAssignment ) ) )
			// InternalDsl.g:167:2: ( ( rule__Parameter__NameAssignment ) )
			{
			// InternalDsl.g:167:2: ( ( rule__Parameter__NameAssignment ) )
			// InternalDsl.g:168:3: ( rule__Parameter__NameAssignment )
			{
			 before(grammarAccess.getParameterAccess().getNameAssignment()); 
			// InternalDsl.g:169:3: ( rule__Parameter__NameAssignment )
			// InternalDsl.g:169:4: rule__Parameter__NameAssignment
			{
			pushFollow(FOLLOW_2);
			rule__Parameter__NameAssignment();
			state._fsp--;

			}

			 after(grammarAccess.getParameterAccess().getNameAssignment()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "ruleParameter"



	// $ANTLR start "entryRuleDatabases"
	// InternalDsl.g:178:1: entryRuleDatabases : ruleDatabases EOF ;
	public final void entryRuleDatabases() throws RecognitionException {
		try {
			// InternalDsl.g:179:1: ( ruleDatabases EOF )
			// InternalDsl.g:180:1: ruleDatabases EOF
			{
			 before(grammarAccess.getDatabasesRule()); 
			pushFollow(FOLLOW_1);
			ruleDatabases();
			state._fsp--;

			 after(grammarAccess.getDatabasesRule()); 
			match(input,EOF,FOLLOW_2); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "entryRuleDatabases"



	// $ANTLR start "ruleDatabases"
	// InternalDsl.g:187:1: ruleDatabases : ( ( rule__Databases__Group__0 ) ) ;
	public final void ruleDatabases() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:191:2: ( ( ( rule__Databases__Group__0 ) ) )
			// InternalDsl.g:192:2: ( ( rule__Databases__Group__0 ) )
			{
			// InternalDsl.g:192:2: ( ( rule__Databases__Group__0 ) )
			// InternalDsl.g:193:3: ( rule__Databases__Group__0 )
			{
			 before(grammarAccess.getDatabasesAccess().getGroup()); 
			// InternalDsl.g:194:3: ( rule__Databases__Group__0 )
			// InternalDsl.g:194:4: rule__Databases__Group__0
			{
			pushFollow(FOLLOW_2);
			rule__Databases__Group__0();
			state._fsp--;

			}

			 after(grammarAccess.getDatabasesAccess().getGroup()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "ruleDatabases"



	// $ANTLR start "entryRuleSystem"
	// InternalDsl.g:203:1: entryRuleSystem : ruleSystem EOF ;
	public final void entryRuleSystem() throws RecognitionException {
		try {
			// InternalDsl.g:204:1: ( ruleSystem EOF )
			// InternalDsl.g:205:1: ruleSystem EOF
			{
			 before(grammarAccess.getSystemRule()); 
			pushFollow(FOLLOW_1);
			ruleSystem();
			state._fsp--;

			 after(grammarAccess.getSystemRule()); 
			match(input,EOF,FOLLOW_2); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "entryRuleSystem"



	// $ANTLR start "ruleSystem"
	// InternalDsl.g:212:1: ruleSystem : ( ( rule__System__Group__0 ) ) ;
	public final void ruleSystem() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:216:2: ( ( ( rule__System__Group__0 ) ) )
			// InternalDsl.g:217:2: ( ( rule__System__Group__0 ) )
			{
			// InternalDsl.g:217:2: ( ( rule__System__Group__0 ) )
			// InternalDsl.g:218:3: ( rule__System__Group__0 )
			{
			 before(grammarAccess.getSystemAccess().getGroup()); 
			// InternalDsl.g:219:3: ( rule__System__Group__0 )
			// InternalDsl.g:219:4: rule__System__Group__0
			{
			pushFollow(FOLLOW_2);
			rule__System__Group__0();
			state._fsp--;

			}

			 after(grammarAccess.getSystemAccess().getGroup()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "ruleSystem"



	// $ANTLR start "entryRuleDescription"
	// InternalDsl.g:228:1: entryRuleDescription : ruleDescription EOF ;
	public final void entryRuleDescription() throws RecognitionException {
		try {
			// InternalDsl.g:229:1: ( ruleDescription EOF )
			// InternalDsl.g:230:1: ruleDescription EOF
			{
			 before(grammarAccess.getDescriptionRule()); 
			pushFollow(FOLLOW_1);
			ruleDescription();
			state._fsp--;

			 after(grammarAccess.getDescriptionRule()); 
			match(input,EOF,FOLLOW_2); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "entryRuleDescription"



	// $ANTLR start "ruleDescription"
	// InternalDsl.g:237:1: ruleDescription : ( ( rule__Description__Group__0 ) ) ;
	public final void ruleDescription() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:241:2: ( ( ( rule__Description__Group__0 ) ) )
			// InternalDsl.g:242:2: ( ( rule__Description__Group__0 ) )
			{
			// InternalDsl.g:242:2: ( ( rule__Description__Group__0 ) )
			// InternalDsl.g:243:3: ( rule__Description__Group__0 )
			{
			 before(grammarAccess.getDescriptionAccess().getGroup()); 
			// InternalDsl.g:244:3: ( rule__Description__Group__0 )
			// InternalDsl.g:244:4: rule__Description__Group__0
			{
			pushFollow(FOLLOW_2);
			rule__Description__Group__0();
			state._fsp--;

			}

			 after(grammarAccess.getDescriptionAccess().getGroup()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "ruleDescription"



	// $ANTLR start "entryRuleDefinition"
	// InternalDsl.g:253:1: entryRuleDefinition : ruleDefinition EOF ;
	public final void entryRuleDefinition() throws RecognitionException {
		try {
			// InternalDsl.g:254:1: ( ruleDefinition EOF )
			// InternalDsl.g:255:1: ruleDefinition EOF
			{
			 before(grammarAccess.getDefinitionRule()); 
			pushFollow(FOLLOW_1);
			ruleDefinition();
			state._fsp--;

			 after(grammarAccess.getDefinitionRule()); 
			match(input,EOF,FOLLOW_2); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "entryRuleDefinition"



	// $ANTLR start "ruleDefinition"
	// InternalDsl.g:262:1: ruleDefinition : ( ( rule__Definition__Group__0 ) ) ;
	public final void ruleDefinition() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:266:2: ( ( ( rule__Definition__Group__0 ) ) )
			// InternalDsl.g:267:2: ( ( rule__Definition__Group__0 ) )
			{
			// InternalDsl.g:267:2: ( ( rule__Definition__Group__0 ) )
			// InternalDsl.g:268:3: ( rule__Definition__Group__0 )
			{
			 before(grammarAccess.getDefinitionAccess().getGroup()); 
			// InternalDsl.g:269:3: ( rule__Definition__Group__0 )
			// InternalDsl.g:269:4: rule__Definition__Group__0
			{
			pushFollow(FOLLOW_2);
			rule__Definition__Group__0();
			state._fsp--;

			}

			 after(grammarAccess.getDefinitionAccess().getGroup()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "ruleDefinition"



	// $ANTLR start "entryRuleProperty"
	// InternalDsl.g:278:1: entryRuleProperty : ruleProperty EOF ;
	public final void entryRuleProperty() throws RecognitionException {
		try {
			// InternalDsl.g:279:1: ( ruleProperty EOF )
			// InternalDsl.g:280:1: ruleProperty EOF
			{
			 before(grammarAccess.getPropertyRule()); 
			pushFollow(FOLLOW_1);
			ruleProperty();
			state._fsp--;

			 after(grammarAccess.getPropertyRule()); 
			match(input,EOF,FOLLOW_2); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "entryRuleProperty"



	// $ANTLR start "ruleProperty"
	// InternalDsl.g:287:1: ruleProperty : ( ( rule__Property__Alternatives ) ) ;
	public final void ruleProperty() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:291:2: ( ( ( rule__Property__Alternatives ) ) )
			// InternalDsl.g:292:2: ( ( rule__Property__Alternatives ) )
			{
			// InternalDsl.g:292:2: ( ( rule__Property__Alternatives ) )
			// InternalDsl.g:293:3: ( rule__Property__Alternatives )
			{
			 before(grammarAccess.getPropertyAccess().getAlternatives()); 
			// InternalDsl.g:294:3: ( rule__Property__Alternatives )
			// InternalDsl.g:294:4: rule__Property__Alternatives
			{
			pushFollow(FOLLOW_2);
			rule__Property__Alternatives();
			state._fsp--;

			}

			 after(grammarAccess.getPropertyAccess().getAlternatives()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "ruleProperty"



	// $ANTLR start "entryRuleConstructs"
	// InternalDsl.g:303:1: entryRuleConstructs : ruleConstructs EOF ;
	public final void entryRuleConstructs() throws RecognitionException {
		try {
			// InternalDsl.g:304:1: ( ruleConstructs EOF )
			// InternalDsl.g:305:1: ruleConstructs EOF
			{
			 before(grammarAccess.getConstructsRule()); 
			pushFollow(FOLLOW_1);
			ruleConstructs();
			state._fsp--;

			 after(grammarAccess.getConstructsRule()); 
			match(input,EOF,FOLLOW_2); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "entryRuleConstructs"



	// $ANTLR start "ruleConstructs"
	// InternalDsl.g:312:1: ruleConstructs : ( ( rule__Constructs__Group__0 ) ) ;
	public final void ruleConstructs() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:316:2: ( ( ( rule__Constructs__Group__0 ) ) )
			// InternalDsl.g:317:2: ( ( rule__Constructs__Group__0 ) )
			{
			// InternalDsl.g:317:2: ( ( rule__Constructs__Group__0 ) )
			// InternalDsl.g:318:3: ( rule__Constructs__Group__0 )
			{
			 before(grammarAccess.getConstructsAccess().getGroup()); 
			// InternalDsl.g:319:3: ( rule__Constructs__Group__0 )
			// InternalDsl.g:319:4: rule__Constructs__Group__0
			{
			pushFollow(FOLLOW_2);
			rule__Constructs__Group__0();
			state._fsp--;

			}

			 after(grammarAccess.getConstructsAccess().getGroup()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "ruleConstructs"



	// $ANTLR start "entryRuleOption"
	// InternalDsl.g:328:1: entryRuleOption : ruleOption EOF ;
	public final void entryRuleOption() throws RecognitionException {
		try {
			// InternalDsl.g:329:1: ( ruleOption EOF )
			// InternalDsl.g:330:1: ruleOption EOF
			{
			 before(grammarAccess.getOptionRule()); 
			pushFollow(FOLLOW_1);
			ruleOption();
			state._fsp--;

			 after(grammarAccess.getOptionRule()); 
			match(input,EOF,FOLLOW_2); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "entryRuleOption"



	// $ANTLR start "ruleOption"
	// InternalDsl.g:337:1: ruleOption : ( ( rule__Option__NameAssignment ) ) ;
	public final void ruleOption() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:341:2: ( ( ( rule__Option__NameAssignment ) ) )
			// InternalDsl.g:342:2: ( ( rule__Option__NameAssignment ) )
			{
			// InternalDsl.g:342:2: ( ( rule__Option__NameAssignment ) )
			// InternalDsl.g:343:3: ( rule__Option__NameAssignment )
			{
			 before(grammarAccess.getOptionAccess().getNameAssignment()); 
			// InternalDsl.g:344:3: ( rule__Option__NameAssignment )
			// InternalDsl.g:344:4: rule__Option__NameAssignment
			{
			pushFollow(FOLLOW_2);
			rule__Option__NameAssignment();
			state._fsp--;

			}

			 after(grammarAccess.getOptionAccess().getNameAssignment()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "ruleOption"



	// $ANTLR start "entryRuleFile"
	// InternalDsl.g:353:1: entryRuleFile : ruleFile EOF ;
	public final void entryRuleFile() throws RecognitionException {
		try {
			// InternalDsl.g:354:1: ( ruleFile EOF )
			// InternalDsl.g:355:1: ruleFile EOF
			{
			 before(grammarAccess.getFileRule()); 
			pushFollow(FOLLOW_1);
			ruleFile();
			state._fsp--;

			 after(grammarAccess.getFileRule()); 
			match(input,EOF,FOLLOW_2); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "entryRuleFile"



	// $ANTLR start "ruleFile"
	// InternalDsl.g:362:1: ruleFile : ( ( rule__File__Group__0 ) ) ;
	public final void ruleFile() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:366:2: ( ( ( rule__File__Group__0 ) ) )
			// InternalDsl.g:367:2: ( ( rule__File__Group__0 ) )
			{
			// InternalDsl.g:367:2: ( ( rule__File__Group__0 ) )
			// InternalDsl.g:368:3: ( rule__File__Group__0 )
			{
			 before(grammarAccess.getFileAccess().getGroup()); 
			// InternalDsl.g:369:3: ( rule__File__Group__0 )
			// InternalDsl.g:369:4: rule__File__Group__0
			{
			pushFollow(FOLLOW_2);
			rule__File__Group__0();
			state._fsp--;

			}

			 after(grammarAccess.getFileAccess().getGroup()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "ruleFile"



	// $ANTLR start "entryRuleImport"
	// InternalDsl.g:378:1: entryRuleImport : ruleImport EOF ;
	public final void entryRuleImport() throws RecognitionException {
		try {
			// InternalDsl.g:379:1: ( ruleImport EOF )
			// InternalDsl.g:380:1: ruleImport EOF
			{
			 before(grammarAccess.getImportRule()); 
			pushFollow(FOLLOW_1);
			ruleImport();
			state._fsp--;

			 after(grammarAccess.getImportRule()); 
			match(input,EOF,FOLLOW_2); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "entryRuleImport"



	// $ANTLR start "ruleImport"
	// InternalDsl.g:387:1: ruleImport : ( ( rule__Import__Group__0 ) ) ;
	public final void ruleImport() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:391:2: ( ( ( rule__Import__Group__0 ) ) )
			// InternalDsl.g:392:2: ( ( rule__Import__Group__0 ) )
			{
			// InternalDsl.g:392:2: ( ( rule__Import__Group__0 ) )
			// InternalDsl.g:393:3: ( rule__Import__Group__0 )
			{
			 before(grammarAccess.getImportAccess().getGroup()); 
			// InternalDsl.g:394:3: ( rule__Import__Group__0 )
			// InternalDsl.g:394:4: rule__Import__Group__0
			{
			pushFollow(FOLLOW_2);
			rule__Import__Group__0();
			state._fsp--;

			}

			 after(grammarAccess.getImportAccess().getGroup()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "ruleImport"



	// $ANTLR start "entryRuleQualifiedNameWithWildcard"
	// InternalDsl.g:403:1: entryRuleQualifiedNameWithWildcard : ruleQualifiedNameWithWildcard EOF ;
	public final void entryRuleQualifiedNameWithWildcard() throws RecognitionException {
		try {
			// InternalDsl.g:404:1: ( ruleQualifiedNameWithWildcard EOF )
			// InternalDsl.g:405:1: ruleQualifiedNameWithWildcard EOF
			{
			 before(grammarAccess.getQualifiedNameWithWildcardRule()); 
			pushFollow(FOLLOW_1);
			ruleQualifiedNameWithWildcard();
			state._fsp--;

			 after(grammarAccess.getQualifiedNameWithWildcardRule()); 
			match(input,EOF,FOLLOW_2); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "entryRuleQualifiedNameWithWildcard"



	// $ANTLR start "ruleQualifiedNameWithWildcard"
	// InternalDsl.g:412:1: ruleQualifiedNameWithWildcard : ( ( rule__QualifiedNameWithWildcard__Group__0 ) ) ;
	public final void ruleQualifiedNameWithWildcard() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:416:2: ( ( ( rule__QualifiedNameWithWildcard__Group__0 ) ) )
			// InternalDsl.g:417:2: ( ( rule__QualifiedNameWithWildcard__Group__0 ) )
			{
			// InternalDsl.g:417:2: ( ( rule__QualifiedNameWithWildcard__Group__0 ) )
			// InternalDsl.g:418:3: ( rule__QualifiedNameWithWildcard__Group__0 )
			{
			 before(grammarAccess.getQualifiedNameWithWildcardAccess().getGroup()); 
			// InternalDsl.g:419:3: ( rule__QualifiedNameWithWildcard__Group__0 )
			// InternalDsl.g:419:4: rule__QualifiedNameWithWildcard__Group__0
			{
			pushFollow(FOLLOW_2);
			rule__QualifiedNameWithWildcard__Group__0();
			state._fsp--;

			}

			 after(grammarAccess.getQualifiedNameWithWildcardAccess().getGroup()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "ruleQualifiedNameWithWildcard"



	// $ANTLR start "entryRuleQualifiedName"
	// InternalDsl.g:428:1: entryRuleQualifiedName : ruleQualifiedName EOF ;
	public final void entryRuleQualifiedName() throws RecognitionException {
		try {
			// InternalDsl.g:429:1: ( ruleQualifiedName EOF )
			// InternalDsl.g:430:1: ruleQualifiedName EOF
			{
			 before(grammarAccess.getQualifiedNameRule()); 
			pushFollow(FOLLOW_1);
			ruleQualifiedName();
			state._fsp--;

			 after(grammarAccess.getQualifiedNameRule()); 
			match(input,EOF,FOLLOW_2); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "entryRuleQualifiedName"



	// $ANTLR start "ruleQualifiedName"
	// InternalDsl.g:437:1: ruleQualifiedName : ( ( rule__QualifiedName__Group__0 ) ) ;
	public final void ruleQualifiedName() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:441:2: ( ( ( rule__QualifiedName__Group__0 ) ) )
			// InternalDsl.g:442:2: ( ( rule__QualifiedName__Group__0 ) )
			{
			// InternalDsl.g:442:2: ( ( rule__QualifiedName__Group__0 ) )
			// InternalDsl.g:443:3: ( rule__QualifiedName__Group__0 )
			{
			 before(grammarAccess.getQualifiedNameAccess().getGroup()); 
			// InternalDsl.g:444:3: ( rule__QualifiedName__Group__0 )
			// InternalDsl.g:444:4: rule__QualifiedName__Group__0
			{
			pushFollow(FOLLOW_2);
			rule__QualifiedName__Group__0();
			state._fsp--;

			}

			 after(grammarAccess.getQualifiedNameAccess().getGroup()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "ruleQualifiedName"



	// $ANTLR start "rule__AbstractElement__Alternatives"
	// InternalDsl.g:452:1: rule__AbstractElement__Alternatives : ( ( ruleType ) | ( ruleImport ) );
	public final void rule__AbstractElement__Alternatives() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:456:2: ( ( ruleType ) | ( ruleImport ) )
			int alt2=2;
			int LA2_0 = input.LA(1);
			if ( (LA2_0==RULE_ID||LA2_0==17||LA2_0==20||LA2_0==23) ) {
				alt2=1;
			}
			else if ( (LA2_0==21) ) {
				alt2=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 2, 0, input);
				throw nvae;
			}

			switch (alt2) {
				case 1 :
					// InternalDsl.g:457:2: ( ruleType )
					{
					// InternalDsl.g:457:2: ( ruleType )
					// InternalDsl.g:458:3: ruleType
					{
					 before(grammarAccess.getAbstractElementAccess().getTypeParserRuleCall_0()); 
					pushFollow(FOLLOW_2);
					ruleType();
					state._fsp--;

					 after(grammarAccess.getAbstractElementAccess().getTypeParserRuleCall_0()); 
					}

					}
					break;
				case 2 :
					// InternalDsl.g:463:2: ( ruleImport )
					{
					// InternalDsl.g:463:2: ( ruleImport )
					// InternalDsl.g:464:3: ruleImport
					{
					 before(grammarAccess.getAbstractElementAccess().getImportParserRuleCall_1()); 
					pushFollow(FOLLOW_2);
					ruleImport();
					state._fsp--;

					 after(grammarAccess.getAbstractElementAccess().getImportParserRuleCall_1()); 
					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__AbstractElement__Alternatives"



	// $ANTLR start "rule__Type__Alternatives"
	// InternalDsl.g:473:1: rule__Type__Alternatives : ( ( ruleHypothesis ) | ( ruleParameter ) | ( ruleSystem ) | ( ruleDatabases ) );
	public final void rule__Type__Alternatives() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:477:2: ( ( ruleHypothesis ) | ( ruleParameter ) | ( ruleSystem ) | ( ruleDatabases ) )
			int alt3=4;
			switch ( input.LA(1) ) {
			case 20:
				{
				alt3=1;
				}
				break;
			case RULE_ID:
				{
				alt3=2;
				}
				break;
			case 23:
				{
				alt3=3;
				}
				break;
			case 17:
				{
				alt3=4;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 3, 0, input);
				throw nvae;
			}
			switch (alt3) {
				case 1 :
					// InternalDsl.g:478:2: ( ruleHypothesis )
					{
					// InternalDsl.g:478:2: ( ruleHypothesis )
					// InternalDsl.g:479:3: ruleHypothesis
					{
					 before(grammarAccess.getTypeAccess().getHypothesisParserRuleCall_0()); 
					pushFollow(FOLLOW_2);
					ruleHypothesis();
					state._fsp--;

					 after(grammarAccess.getTypeAccess().getHypothesisParserRuleCall_0()); 
					}

					}
					break;
				case 2 :
					// InternalDsl.g:484:2: ( ruleParameter )
					{
					// InternalDsl.g:484:2: ( ruleParameter )
					// InternalDsl.g:485:3: ruleParameter
					{
					 before(grammarAccess.getTypeAccess().getParameterParserRuleCall_1()); 
					pushFollow(FOLLOW_2);
					ruleParameter();
					state._fsp--;

					 after(grammarAccess.getTypeAccess().getParameterParserRuleCall_1()); 
					}

					}
					break;
				case 3 :
					// InternalDsl.g:490:2: ( ruleSystem )
					{
					// InternalDsl.g:490:2: ( ruleSystem )
					// InternalDsl.g:491:3: ruleSystem
					{
					 before(grammarAccess.getTypeAccess().getSystemParserRuleCall_2()); 
					pushFollow(FOLLOW_2);
					ruleSystem();
					state._fsp--;

					 after(grammarAccess.getTypeAccess().getSystemParserRuleCall_2()); 
					}

					}
					break;
				case 4 :
					// InternalDsl.g:496:2: ( ruleDatabases )
					{
					// InternalDsl.g:496:2: ( ruleDatabases )
					// InternalDsl.g:497:3: ruleDatabases
					{
					 before(grammarAccess.getTypeAccess().getDatabasesParserRuleCall_3()); 
					pushFollow(FOLLOW_2);
					ruleDatabases();
					state._fsp--;

					 after(grammarAccess.getTypeAccess().getDatabasesParserRuleCall_3()); 
					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Type__Alternatives"



	// $ANTLR start "rule__Property__Alternatives"
	// InternalDsl.g:506:1: rule__Property__Alternatives : ( ( ( rule__Property__Group_0__0 ) ) | ( ( rule__Property__Group_1__0 ) ) | ( ( rule__Property__Group_2__0 ) ) | ( ( rule__Property__Group_3__0 ) ) );
	public final void rule__Property__Alternatives() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:510:2: ( ( ( rule__Property__Group_0__0 ) ) | ( ( rule__Property__Group_1__0 ) ) | ( ( rule__Property__Group_2__0 ) ) | ( ( rule__Property__Group_3__0 ) ) )
			int alt4=4;
			int LA4_0 = input.LA(1);
			if ( (LA4_0==30) ) {
				int LA4_1 = input.LA(2);
				if ( (LA4_1==27) ) {
					alt4=1;
				}
				else if ( (LA4_1==26) ) {
					alt4=3;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 4, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}
			else if ( (LA4_0==29) ) {
				int LA4_2 = input.LA(2);
				if ( (LA4_2==27) ) {
					alt4=2;
				}
				else if ( (LA4_2==26) ) {
					alt4=4;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 4, 2, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 4, 0, input);
				throw nvae;
			}

			switch (alt4) {
				case 1 :
					// InternalDsl.g:511:2: ( ( rule__Property__Group_0__0 ) )
					{
					// InternalDsl.g:511:2: ( ( rule__Property__Group_0__0 ) )
					// InternalDsl.g:512:3: ( rule__Property__Group_0__0 )
					{
					 before(grammarAccess.getPropertyAccess().getGroup_0()); 
					// InternalDsl.g:513:3: ( rule__Property__Group_0__0 )
					// InternalDsl.g:513:4: rule__Property__Group_0__0
					{
					pushFollow(FOLLOW_2);
					rule__Property__Group_0__0();
					state._fsp--;

					}

					 after(grammarAccess.getPropertyAccess().getGroup_0()); 
					}

					}
					break;
				case 2 :
					// InternalDsl.g:517:2: ( ( rule__Property__Group_1__0 ) )
					{
					// InternalDsl.g:517:2: ( ( rule__Property__Group_1__0 ) )
					// InternalDsl.g:518:3: ( rule__Property__Group_1__0 )
					{
					 before(grammarAccess.getPropertyAccess().getGroup_1()); 
					// InternalDsl.g:519:3: ( rule__Property__Group_1__0 )
					// InternalDsl.g:519:4: rule__Property__Group_1__0
					{
					pushFollow(FOLLOW_2);
					rule__Property__Group_1__0();
					state._fsp--;

					}

					 after(grammarAccess.getPropertyAccess().getGroup_1()); 
					}

					}
					break;
				case 3 :
					// InternalDsl.g:523:2: ( ( rule__Property__Group_2__0 ) )
					{
					// InternalDsl.g:523:2: ( ( rule__Property__Group_2__0 ) )
					// InternalDsl.g:524:3: ( rule__Property__Group_2__0 )
					{
					 before(grammarAccess.getPropertyAccess().getGroup_2()); 
					// InternalDsl.g:525:3: ( rule__Property__Group_2__0 )
					// InternalDsl.g:525:4: rule__Property__Group_2__0
					{
					pushFollow(FOLLOW_2);
					rule__Property__Group_2__0();
					state._fsp--;

					}

					 after(grammarAccess.getPropertyAccess().getGroup_2()); 
					}

					}
					break;
				case 4 :
					// InternalDsl.g:529:2: ( ( rule__Property__Group_3__0 ) )
					{
					// InternalDsl.g:529:2: ( ( rule__Property__Group_3__0 ) )
					// InternalDsl.g:530:3: ( rule__Property__Group_3__0 )
					{
					 before(grammarAccess.getPropertyAccess().getGroup_3()); 
					// InternalDsl.g:531:3: ( rule__Property__Group_3__0 )
					// InternalDsl.g:531:4: rule__Property__Group_3__0
					{
					pushFollow(FOLLOW_2);
					rule__Property__Group_3__0();
					state._fsp--;

					}

					 after(grammarAccess.getPropertyAccess().getGroup_3()); 
					}

					}
					break;

			}
		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Property__Alternatives"



	// $ANTLR start "rule__Hypothesis__Group__0"
	// InternalDsl.g:539:1: rule__Hypothesis__Group__0 : rule__Hypothesis__Group__0__Impl rule__Hypothesis__Group__1 ;
	public final void rule__Hypothesis__Group__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:543:2: ( rule__Hypothesis__Group__0__Impl rule__Hypothesis__Group__1 )
			// InternalDsl.g:544:2: rule__Hypothesis__Group__0__Impl rule__Hypothesis__Group__1
			{
			pushFollow(FOLLOW_4);
			rule__Hypothesis__Group__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Hypothesis__Group__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Hypothesis__Group__0"



	// $ANTLR start "rule__Hypothesis__Group__0__Impl"
	// InternalDsl.g:551:1: rule__Hypothesis__Group__0__Impl : ( 'Hypothesis' ) ;
	public final void rule__Hypothesis__Group__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:555:2: ( ( 'Hypothesis' ) )
			// InternalDsl.g:556:2: ( 'Hypothesis' )
			{
			// InternalDsl.g:556:2: ( 'Hypothesis' )
			// InternalDsl.g:557:2: 'Hypothesis'
			{
			 before(grammarAccess.getHypothesisAccess().getHypothesisKeyword_0()); 
			match(input,20,FOLLOW_2); 
			 after(grammarAccess.getHypothesisAccess().getHypothesisKeyword_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Hypothesis__Group__0__Impl"



	// $ANTLR start "rule__Hypothesis__Group__1"
	// InternalDsl.g:566:1: rule__Hypothesis__Group__1 : rule__Hypothesis__Group__1__Impl rule__Hypothesis__Group__2 ;
	public final void rule__Hypothesis__Group__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:570:2: ( rule__Hypothesis__Group__1__Impl rule__Hypothesis__Group__2 )
			// InternalDsl.g:571:2: rule__Hypothesis__Group__1__Impl rule__Hypothesis__Group__2
			{
			pushFollow(FOLLOW_5);
			rule__Hypothesis__Group__1__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Hypothesis__Group__2();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Hypothesis__Group__1"



	// $ANTLR start "rule__Hypothesis__Group__1__Impl"
	// InternalDsl.g:578:1: rule__Hypothesis__Group__1__Impl : ( ( rule__Hypothesis__NameAssignment_1 ) ) ;
	public final void rule__Hypothesis__Group__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:582:2: ( ( ( rule__Hypothesis__NameAssignment_1 ) ) )
			// InternalDsl.g:583:2: ( ( rule__Hypothesis__NameAssignment_1 ) )
			{
			// InternalDsl.g:583:2: ( ( rule__Hypothesis__NameAssignment_1 ) )
			// InternalDsl.g:584:2: ( rule__Hypothesis__NameAssignment_1 )
			{
			 before(grammarAccess.getHypothesisAccess().getNameAssignment_1()); 
			// InternalDsl.g:585:2: ( rule__Hypothesis__NameAssignment_1 )
			// InternalDsl.g:585:3: rule__Hypothesis__NameAssignment_1
			{
			pushFollow(FOLLOW_2);
			rule__Hypothesis__NameAssignment_1();
			state._fsp--;

			}

			 after(grammarAccess.getHypothesisAccess().getNameAssignment_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Hypothesis__Group__1__Impl"



	// $ANTLR start "rule__Hypothesis__Group__2"
	// InternalDsl.g:593:1: rule__Hypothesis__Group__2 : rule__Hypothesis__Group__2__Impl rule__Hypothesis__Group__3 ;
	public final void rule__Hypothesis__Group__2() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:597:2: ( rule__Hypothesis__Group__2__Impl rule__Hypothesis__Group__3 )
			// InternalDsl.g:598:2: rule__Hypothesis__Group__2__Impl rule__Hypothesis__Group__3
			{
			pushFollow(FOLLOW_6);
			rule__Hypothesis__Group__2__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Hypothesis__Group__3();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Hypothesis__Group__2"



	// $ANTLR start "rule__Hypothesis__Group__2__Impl"
	// InternalDsl.g:605:1: rule__Hypothesis__Group__2__Impl : ( ( rule__Hypothesis__Group_2__0 ) ) ;
	public final void rule__Hypothesis__Group__2__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:609:2: ( ( ( rule__Hypothesis__Group_2__0 ) ) )
			// InternalDsl.g:610:2: ( ( rule__Hypothesis__Group_2__0 ) )
			{
			// InternalDsl.g:610:2: ( ( rule__Hypothesis__Group_2__0 ) )
			// InternalDsl.g:611:2: ( rule__Hypothesis__Group_2__0 )
			{
			 before(grammarAccess.getHypothesisAccess().getGroup_2()); 
			// InternalDsl.g:612:2: ( rule__Hypothesis__Group_2__0 )
			// InternalDsl.g:612:3: rule__Hypothesis__Group_2__0
			{
			pushFollow(FOLLOW_2);
			rule__Hypothesis__Group_2__0();
			state._fsp--;

			}

			 after(grammarAccess.getHypothesisAccess().getGroup_2()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Hypothesis__Group__2__Impl"



	// $ANTLR start "rule__Hypothesis__Group__3"
	// InternalDsl.g:620:1: rule__Hypothesis__Group__3 : rule__Hypothesis__Group__3__Impl rule__Hypothesis__Group__4 ;
	public final void rule__Hypothesis__Group__3() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:624:2: ( rule__Hypothesis__Group__3__Impl rule__Hypothesis__Group__4 )
			// InternalDsl.g:625:2: rule__Hypothesis__Group__3__Impl rule__Hypothesis__Group__4
			{
			pushFollow(FOLLOW_7);
			rule__Hypothesis__Group__3__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Hypothesis__Group__4();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Hypothesis__Group__3"



	// $ANTLR start "rule__Hypothesis__Group__3__Impl"
	// InternalDsl.g:632:1: rule__Hypothesis__Group__3__Impl : ( '{' ) ;
	public final void rule__Hypothesis__Group__3__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:636:2: ( ( '{' ) )
			// InternalDsl.g:637:2: ( '{' )
			{
			// InternalDsl.g:637:2: ( '{' )
			// InternalDsl.g:638:2: '{'
			{
			 before(grammarAccess.getHypothesisAccess().getLeftCurlyBracketKeyword_3()); 
			match(input,33,FOLLOW_2); 
			 after(grammarAccess.getHypothesisAccess().getLeftCurlyBracketKeyword_3()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Hypothesis__Group__3__Impl"



	// $ANTLR start "rule__Hypothesis__Group__4"
	// InternalDsl.g:647:1: rule__Hypothesis__Group__4 : rule__Hypothesis__Group__4__Impl rule__Hypothesis__Group__5 ;
	public final void rule__Hypothesis__Group__4() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:651:2: ( rule__Hypothesis__Group__4__Impl rule__Hypothesis__Group__5 )
			// InternalDsl.g:652:2: rule__Hypothesis__Group__4__Impl rule__Hypothesis__Group__5
			{
			pushFollow(FOLLOW_8);
			rule__Hypothesis__Group__4__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Hypothesis__Group__5();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Hypothesis__Group__4"



	// $ANTLR start "rule__Hypothesis__Group__4__Impl"
	// InternalDsl.g:659:1: rule__Hypothesis__Group__4__Impl : ( ( rule__Hypothesis__DescriptionAssignment_4 ) ) ;
	public final void rule__Hypothesis__Group__4__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:663:2: ( ( ( rule__Hypothesis__DescriptionAssignment_4 ) ) )
			// InternalDsl.g:664:2: ( ( rule__Hypothesis__DescriptionAssignment_4 ) )
			{
			// InternalDsl.g:664:2: ( ( rule__Hypothesis__DescriptionAssignment_4 ) )
			// InternalDsl.g:665:2: ( rule__Hypothesis__DescriptionAssignment_4 )
			{
			 before(grammarAccess.getHypothesisAccess().getDescriptionAssignment_4()); 
			// InternalDsl.g:666:2: ( rule__Hypothesis__DescriptionAssignment_4 )
			// InternalDsl.g:666:3: rule__Hypothesis__DescriptionAssignment_4
			{
			pushFollow(FOLLOW_2);
			rule__Hypothesis__DescriptionAssignment_4();
			state._fsp--;

			}

			 after(grammarAccess.getHypothesisAccess().getDescriptionAssignment_4()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Hypothesis__Group__4__Impl"



	// $ANTLR start "rule__Hypothesis__Group__5"
	// InternalDsl.g:674:1: rule__Hypothesis__Group__5 : rule__Hypothesis__Group__5__Impl rule__Hypothesis__Group__6 ;
	public final void rule__Hypothesis__Group__5() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:678:2: ( rule__Hypothesis__Group__5__Impl rule__Hypothesis__Group__6 )
			// InternalDsl.g:679:2: rule__Hypothesis__Group__5__Impl rule__Hypothesis__Group__6
			{
			pushFollow(FOLLOW_9);
			rule__Hypothesis__Group__5__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Hypothesis__Group__6();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Hypothesis__Group__5"



	// $ANTLR start "rule__Hypothesis__Group__5__Impl"
	// InternalDsl.g:686:1: rule__Hypothesis__Group__5__Impl : ( ( rule__Hypothesis__DefinitionAssignment_5 ) ) ;
	public final void rule__Hypothesis__Group__5__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:690:2: ( ( ( rule__Hypothesis__DefinitionAssignment_5 ) ) )
			// InternalDsl.g:691:2: ( ( rule__Hypothesis__DefinitionAssignment_5 ) )
			{
			// InternalDsl.g:691:2: ( ( rule__Hypothesis__DefinitionAssignment_5 ) )
			// InternalDsl.g:692:2: ( rule__Hypothesis__DefinitionAssignment_5 )
			{
			 before(grammarAccess.getHypothesisAccess().getDefinitionAssignment_5()); 
			// InternalDsl.g:693:2: ( rule__Hypothesis__DefinitionAssignment_5 )
			// InternalDsl.g:693:3: rule__Hypothesis__DefinitionAssignment_5
			{
			pushFollow(FOLLOW_2);
			rule__Hypothesis__DefinitionAssignment_5();
			state._fsp--;

			}

			 after(grammarAccess.getHypothesisAccess().getDefinitionAssignment_5()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Hypothesis__Group__5__Impl"



	// $ANTLR start "rule__Hypothesis__Group__6"
	// InternalDsl.g:701:1: rule__Hypothesis__Group__6 : rule__Hypothesis__Group__6__Impl rule__Hypothesis__Group__7 ;
	public final void rule__Hypothesis__Group__6() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:705:2: ( rule__Hypothesis__Group__6__Impl rule__Hypothesis__Group__7 )
			// InternalDsl.g:706:2: rule__Hypothesis__Group__6__Impl rule__Hypothesis__Group__7
			{
			pushFollow(FOLLOW_10);
			rule__Hypothesis__Group__6__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Hypothesis__Group__7();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Hypothesis__Group__6"



	// $ANTLR start "rule__Hypothesis__Group__6__Impl"
	// InternalDsl.g:713:1: rule__Hypothesis__Group__6__Impl : ( ( rule__Hypothesis__ConstructsAssignment_6 ) ) ;
	public final void rule__Hypothesis__Group__6__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:717:2: ( ( ( rule__Hypothesis__ConstructsAssignment_6 ) ) )
			// InternalDsl.g:718:2: ( ( rule__Hypothesis__ConstructsAssignment_6 ) )
			{
			// InternalDsl.g:718:2: ( ( rule__Hypothesis__ConstructsAssignment_6 ) )
			// InternalDsl.g:719:2: ( rule__Hypothesis__ConstructsAssignment_6 )
			{
			 before(grammarAccess.getHypothesisAccess().getConstructsAssignment_6()); 
			// InternalDsl.g:720:2: ( rule__Hypothesis__ConstructsAssignment_6 )
			// InternalDsl.g:720:3: rule__Hypothesis__ConstructsAssignment_6
			{
			pushFollow(FOLLOW_2);
			rule__Hypothesis__ConstructsAssignment_6();
			state._fsp--;

			}

			 after(grammarAccess.getHypothesisAccess().getConstructsAssignment_6()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Hypothesis__Group__6__Impl"



	// $ANTLR start "rule__Hypothesis__Group__7"
	// InternalDsl.g:728:1: rule__Hypothesis__Group__7 : rule__Hypothesis__Group__7__Impl ;
	public final void rule__Hypothesis__Group__7() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:732:2: ( rule__Hypothesis__Group__7__Impl )
			// InternalDsl.g:733:2: rule__Hypothesis__Group__7__Impl
			{
			pushFollow(FOLLOW_2);
			rule__Hypothesis__Group__7__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Hypothesis__Group__7"



	// $ANTLR start "rule__Hypothesis__Group__7__Impl"
	// InternalDsl.g:739:1: rule__Hypothesis__Group__7__Impl : ( '}' ) ;
	public final void rule__Hypothesis__Group__7__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:743:2: ( ( '}' ) )
			// InternalDsl.g:744:2: ( '}' )
			{
			// InternalDsl.g:744:2: ( '}' )
			// InternalDsl.g:745:2: '}'
			{
			 before(grammarAccess.getHypothesisAccess().getRightCurlyBracketKeyword_7()); 
			match(input,34,FOLLOW_2); 
			 after(grammarAccess.getHypothesisAccess().getRightCurlyBracketKeyword_7()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Hypothesis__Group__7__Impl"



	// $ANTLR start "rule__Hypothesis__Group_2__0"
	// InternalDsl.g:755:1: rule__Hypothesis__Group_2__0 : rule__Hypothesis__Group_2__0__Impl rule__Hypothesis__Group_2__1 ;
	public final void rule__Hypothesis__Group_2__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:759:2: ( rule__Hypothesis__Group_2__0__Impl rule__Hypothesis__Group_2__1 )
			// InternalDsl.g:760:2: rule__Hypothesis__Group_2__0__Impl rule__Hypothesis__Group_2__1
			{
			pushFollow(FOLLOW_11);
			rule__Hypothesis__Group_2__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Hypothesis__Group_2__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Hypothesis__Group_2__0"



	// $ANTLR start "rule__Hypothesis__Group_2__0__Impl"
	// InternalDsl.g:767:1: rule__Hypothesis__Group_2__0__Impl : ( 'on' ) ;
	public final void rule__Hypothesis__Group_2__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:771:2: ( ( 'on' ) )
			// InternalDsl.g:772:2: ( 'on' )
			{
			// InternalDsl.g:772:2: ( 'on' )
			// InternalDsl.g:773:2: 'on'
			{
			 before(grammarAccess.getHypothesisAccess().getOnKeyword_2_0()); 
			match(input,31,FOLLOW_2); 
			 after(grammarAccess.getHypothesisAccess().getOnKeyword_2_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Hypothesis__Group_2__0__Impl"



	// $ANTLR start "rule__Hypothesis__Group_2__1"
	// InternalDsl.g:782:1: rule__Hypothesis__Group_2__1 : rule__Hypothesis__Group_2__1__Impl rule__Hypothesis__Group_2__2 ;
	public final void rule__Hypothesis__Group_2__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:786:2: ( rule__Hypothesis__Group_2__1__Impl rule__Hypothesis__Group_2__2 )
			// InternalDsl.g:787:2: rule__Hypothesis__Group_2__1__Impl rule__Hypothesis__Group_2__2
			{
			pushFollow(FOLLOW_4);
			rule__Hypothesis__Group_2__1__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Hypothesis__Group_2__2();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Hypothesis__Group_2__1"



	// $ANTLR start "rule__Hypothesis__Group_2__1__Impl"
	// InternalDsl.g:794:1: rule__Hypothesis__Group_2__1__Impl : ( 'Parameter' ) ;
	public final void rule__Hypothesis__Group_2__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:798:2: ( ( 'Parameter' ) )
			// InternalDsl.g:799:2: ( 'Parameter' )
			{
			// InternalDsl.g:799:2: ( 'Parameter' )
			// InternalDsl.g:800:2: 'Parameter'
			{
			 before(grammarAccess.getHypothesisAccess().getParameterKeyword_2_1()); 
			match(input,22,FOLLOW_2); 
			 after(grammarAccess.getHypothesisAccess().getParameterKeyword_2_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Hypothesis__Group_2__1__Impl"



	// $ANTLR start "rule__Hypothesis__Group_2__2"
	// InternalDsl.g:809:1: rule__Hypothesis__Group_2__2 : rule__Hypothesis__Group_2__2__Impl rule__Hypothesis__Group_2__3 ;
	public final void rule__Hypothesis__Group_2__2() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:813:2: ( rule__Hypothesis__Group_2__2__Impl rule__Hypothesis__Group_2__3 )
			// InternalDsl.g:814:2: rule__Hypothesis__Group_2__2__Impl rule__Hypothesis__Group_2__3
			{
			pushFollow(FOLLOW_12);
			rule__Hypothesis__Group_2__2__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Hypothesis__Group_2__3();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Hypothesis__Group_2__2"



	// $ANTLR start "rule__Hypothesis__Group_2__2__Impl"
	// InternalDsl.g:821:1: rule__Hypothesis__Group_2__2__Impl : ( ( rule__Hypothesis__TypesAssignment_2_2 ) ) ;
	public final void rule__Hypothesis__Group_2__2__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:825:2: ( ( ( rule__Hypothesis__TypesAssignment_2_2 ) ) )
			// InternalDsl.g:826:2: ( ( rule__Hypothesis__TypesAssignment_2_2 ) )
			{
			// InternalDsl.g:826:2: ( ( rule__Hypothesis__TypesAssignment_2_2 ) )
			// InternalDsl.g:827:2: ( rule__Hypothesis__TypesAssignment_2_2 )
			{
			 before(grammarAccess.getHypothesisAccess().getTypesAssignment_2_2()); 
			// InternalDsl.g:828:2: ( rule__Hypothesis__TypesAssignment_2_2 )
			// InternalDsl.g:828:3: rule__Hypothesis__TypesAssignment_2_2
			{
			pushFollow(FOLLOW_2);
			rule__Hypothesis__TypesAssignment_2_2();
			state._fsp--;

			}

			 after(grammarAccess.getHypothesisAccess().getTypesAssignment_2_2()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Hypothesis__Group_2__2__Impl"



	// $ANTLR start "rule__Hypothesis__Group_2__3"
	// InternalDsl.g:836:1: rule__Hypothesis__Group_2__3 : rule__Hypothesis__Group_2__3__Impl ;
	public final void rule__Hypothesis__Group_2__3() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:840:2: ( rule__Hypothesis__Group_2__3__Impl )
			// InternalDsl.g:841:2: rule__Hypothesis__Group_2__3__Impl
			{
			pushFollow(FOLLOW_2);
			rule__Hypothesis__Group_2__3__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Hypothesis__Group_2__3"



	// $ANTLR start "rule__Hypothesis__Group_2__3__Impl"
	// InternalDsl.g:847:1: rule__Hypothesis__Group_2__3__Impl : ( ( rule__Hypothesis__Group_2_3__0 )* ) ;
	public final void rule__Hypothesis__Group_2__3__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:851:2: ( ( ( rule__Hypothesis__Group_2_3__0 )* ) )
			// InternalDsl.g:852:2: ( ( rule__Hypothesis__Group_2_3__0 )* )
			{
			// InternalDsl.g:852:2: ( ( rule__Hypothesis__Group_2_3__0 )* )
			// InternalDsl.g:853:2: ( rule__Hypothesis__Group_2_3__0 )*
			{
			 before(grammarAccess.getHypothesisAccess().getGroup_2_3()); 
			// InternalDsl.g:854:2: ( rule__Hypothesis__Group_2_3__0 )*
			loop5:
			while (true) {
				int alt5=2;
				int LA5_0 = input.LA(1);
				if ( (LA5_0==11) ) {
					alt5=1;
				}

				switch (alt5) {
				case 1 :
					// InternalDsl.g:854:3: rule__Hypothesis__Group_2_3__0
					{
					pushFollow(FOLLOW_13);
					rule__Hypothesis__Group_2_3__0();
					state._fsp--;

					}
					break;

				default :
					break loop5;
				}
			}

			 after(grammarAccess.getHypothesisAccess().getGroup_2_3()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Hypothesis__Group_2__3__Impl"



	// $ANTLR start "rule__Hypothesis__Group_2_3__0"
	// InternalDsl.g:863:1: rule__Hypothesis__Group_2_3__0 : rule__Hypothesis__Group_2_3__0__Impl rule__Hypothesis__Group_2_3__1 ;
	public final void rule__Hypothesis__Group_2_3__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:867:2: ( rule__Hypothesis__Group_2_3__0__Impl rule__Hypothesis__Group_2_3__1 )
			// InternalDsl.g:868:2: rule__Hypothesis__Group_2_3__0__Impl rule__Hypothesis__Group_2_3__1
			{
			pushFollow(FOLLOW_4);
			rule__Hypothesis__Group_2_3__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Hypothesis__Group_2_3__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Hypothesis__Group_2_3__0"



	// $ANTLR start "rule__Hypothesis__Group_2_3__0__Impl"
	// InternalDsl.g:875:1: rule__Hypothesis__Group_2_3__0__Impl : ( ',' ) ;
	public final void rule__Hypothesis__Group_2_3__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:879:2: ( ( ',' ) )
			// InternalDsl.g:880:2: ( ',' )
			{
			// InternalDsl.g:880:2: ( ',' )
			// InternalDsl.g:881:2: ','
			{
			 before(grammarAccess.getHypothesisAccess().getCommaKeyword_2_3_0()); 
			match(input,11,FOLLOW_2); 
			 after(grammarAccess.getHypothesisAccess().getCommaKeyword_2_3_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Hypothesis__Group_2_3__0__Impl"



	// $ANTLR start "rule__Hypothesis__Group_2_3__1"
	// InternalDsl.g:890:1: rule__Hypothesis__Group_2_3__1 : rule__Hypothesis__Group_2_3__1__Impl ;
	public final void rule__Hypothesis__Group_2_3__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:894:2: ( rule__Hypothesis__Group_2_3__1__Impl )
			// InternalDsl.g:895:2: rule__Hypothesis__Group_2_3__1__Impl
			{
			pushFollow(FOLLOW_2);
			rule__Hypothesis__Group_2_3__1__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Hypothesis__Group_2_3__1"



	// $ANTLR start "rule__Hypothesis__Group_2_3__1__Impl"
	// InternalDsl.g:901:1: rule__Hypothesis__Group_2_3__1__Impl : ( ( rule__Hypothesis__TypesAssignment_2_3_1 ) ) ;
	public final void rule__Hypothesis__Group_2_3__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:905:2: ( ( ( rule__Hypothesis__TypesAssignment_2_3_1 ) ) )
			// InternalDsl.g:906:2: ( ( rule__Hypothesis__TypesAssignment_2_3_1 ) )
			{
			// InternalDsl.g:906:2: ( ( rule__Hypothesis__TypesAssignment_2_3_1 ) )
			// InternalDsl.g:907:2: ( rule__Hypothesis__TypesAssignment_2_3_1 )
			{
			 before(grammarAccess.getHypothesisAccess().getTypesAssignment_2_3_1()); 
			// InternalDsl.g:908:2: ( rule__Hypothesis__TypesAssignment_2_3_1 )
			// InternalDsl.g:908:3: rule__Hypothesis__TypesAssignment_2_3_1
			{
			pushFollow(FOLLOW_2);
			rule__Hypothesis__TypesAssignment_2_3_1();
			state._fsp--;

			}

			 after(grammarAccess.getHypothesisAccess().getTypesAssignment_2_3_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Hypothesis__Group_2_3__1__Impl"



	// $ANTLR start "rule__Databases__Group__0"
	// InternalDsl.g:917:1: rule__Databases__Group__0 : rule__Databases__Group__0__Impl rule__Databases__Group__1 ;
	public final void rule__Databases__Group__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:921:2: ( rule__Databases__Group__0__Impl rule__Databases__Group__1 )
			// InternalDsl.g:922:2: rule__Databases__Group__0__Impl rule__Databases__Group__1
			{
			pushFollow(FOLLOW_14);
			rule__Databases__Group__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Databases__Group__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Databases__Group__0"



	// $ANTLR start "rule__Databases__Group__0__Impl"
	// InternalDsl.g:929:1: rule__Databases__Group__0__Impl : ( 'Databases' ) ;
	public final void rule__Databases__Group__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:933:2: ( ( 'Databases' ) )
			// InternalDsl.g:934:2: ( 'Databases' )
			{
			// InternalDsl.g:934:2: ( 'Databases' )
			// InternalDsl.g:935:2: 'Databases'
			{
			 before(grammarAccess.getDatabasesAccess().getDatabasesKeyword_0()); 
			match(input,17,FOLLOW_2); 
			 after(grammarAccess.getDatabasesAccess().getDatabasesKeyword_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Databases__Group__0__Impl"



	// $ANTLR start "rule__Databases__Group__1"
	// InternalDsl.g:944:1: rule__Databases__Group__1 : rule__Databases__Group__1__Impl rule__Databases__Group__2 ;
	public final void rule__Databases__Group__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:948:2: ( rule__Databases__Group__1__Impl rule__Databases__Group__2 )
			// InternalDsl.g:949:2: rule__Databases__Group__1__Impl rule__Databases__Group__2
			{
			pushFollow(FOLLOW_4);
			rule__Databases__Group__1__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Databases__Group__2();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Databases__Group__1"



	// $ANTLR start "rule__Databases__Group__1__Impl"
	// InternalDsl.g:956:1: rule__Databases__Group__1__Impl : ( ':' ) ;
	public final void rule__Databases__Group__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:960:2: ( ( ':' ) )
			// InternalDsl.g:961:2: ( ':' )
			{
			// InternalDsl.g:961:2: ( ':' )
			// InternalDsl.g:962:2: ':'
			{
			 before(grammarAccess.getDatabasesAccess().getColonKeyword_1()); 
			match(input,14,FOLLOW_2); 
			 after(grammarAccess.getDatabasesAccess().getColonKeyword_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Databases__Group__1__Impl"



	// $ANTLR start "rule__Databases__Group__2"
	// InternalDsl.g:971:1: rule__Databases__Group__2 : rule__Databases__Group__2__Impl ;
	public final void rule__Databases__Group__2() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:975:2: ( rule__Databases__Group__2__Impl )
			// InternalDsl.g:976:2: rule__Databases__Group__2__Impl
			{
			pushFollow(FOLLOW_2);
			rule__Databases__Group__2__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Databases__Group__2"



	// $ANTLR start "rule__Databases__Group__2__Impl"
	// InternalDsl.g:982:1: rule__Databases__Group__2__Impl : ( ( rule__Databases__ValuesAssignment_2 ) ) ;
	public final void rule__Databases__Group__2__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:986:2: ( ( ( rule__Databases__ValuesAssignment_2 ) ) )
			// InternalDsl.g:987:2: ( ( rule__Databases__ValuesAssignment_2 ) )
			{
			// InternalDsl.g:987:2: ( ( rule__Databases__ValuesAssignment_2 ) )
			// InternalDsl.g:988:2: ( rule__Databases__ValuesAssignment_2 )
			{
			 before(grammarAccess.getDatabasesAccess().getValuesAssignment_2()); 
			// InternalDsl.g:989:2: ( rule__Databases__ValuesAssignment_2 )
			// InternalDsl.g:989:3: rule__Databases__ValuesAssignment_2
			{
			pushFollow(FOLLOW_2);
			rule__Databases__ValuesAssignment_2();
			state._fsp--;

			}

			 after(grammarAccess.getDatabasesAccess().getValuesAssignment_2()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Databases__Group__2__Impl"



	// $ANTLR start "rule__System__Group__0"
	// InternalDsl.g:998:1: rule__System__Group__0 : rule__System__Group__0__Impl rule__System__Group__1 ;
	public final void rule__System__Group__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1002:2: ( rule__System__Group__0__Impl rule__System__Group__1 )
			// InternalDsl.g:1003:2: rule__System__Group__0__Impl rule__System__Group__1
			{
			pushFollow(FOLLOW_14);
			rule__System__Group__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__System__Group__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__System__Group__0"



	// $ANTLR start "rule__System__Group__0__Impl"
	// InternalDsl.g:1010:1: rule__System__Group__0__Impl : ( 'System' ) ;
	public final void rule__System__Group__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1014:2: ( ( 'System' ) )
			// InternalDsl.g:1015:2: ( 'System' )
			{
			// InternalDsl.g:1015:2: ( 'System' )
			// InternalDsl.g:1016:2: 'System'
			{
			 before(grammarAccess.getSystemAccess().getSystemKeyword_0()); 
			match(input,23,FOLLOW_2); 
			 after(grammarAccess.getSystemAccess().getSystemKeyword_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__System__Group__0__Impl"



	// $ANTLR start "rule__System__Group__1"
	// InternalDsl.g:1025:1: rule__System__Group__1 : rule__System__Group__1__Impl rule__System__Group__2 ;
	public final void rule__System__Group__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1029:2: ( rule__System__Group__1__Impl rule__System__Group__2 )
			// InternalDsl.g:1030:2: rule__System__Group__1__Impl rule__System__Group__2
			{
			pushFollow(FOLLOW_4);
			rule__System__Group__1__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__System__Group__2();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__System__Group__1"



	// $ANTLR start "rule__System__Group__1__Impl"
	// InternalDsl.g:1037:1: rule__System__Group__1__Impl : ( ':' ) ;
	public final void rule__System__Group__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1041:2: ( ( ':' ) )
			// InternalDsl.g:1042:2: ( ':' )
			{
			// InternalDsl.g:1042:2: ( ':' )
			// InternalDsl.g:1043:2: ':'
			{
			 before(grammarAccess.getSystemAccess().getColonKeyword_1()); 
			match(input,14,FOLLOW_2); 
			 after(grammarAccess.getSystemAccess().getColonKeyword_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__System__Group__1__Impl"



	// $ANTLR start "rule__System__Group__2"
	// InternalDsl.g:1052:1: rule__System__Group__2 : rule__System__Group__2__Impl ;
	public final void rule__System__Group__2() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1056:2: ( rule__System__Group__2__Impl )
			// InternalDsl.g:1057:2: rule__System__Group__2__Impl
			{
			pushFollow(FOLLOW_2);
			rule__System__Group__2__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__System__Group__2"



	// $ANTLR start "rule__System__Group__2__Impl"
	// InternalDsl.g:1063:1: rule__System__Group__2__Impl : ( ( rule__System__NameAssignment_2 ) ) ;
	public final void rule__System__Group__2__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1067:2: ( ( ( rule__System__NameAssignment_2 ) ) )
			// InternalDsl.g:1068:2: ( ( rule__System__NameAssignment_2 ) )
			{
			// InternalDsl.g:1068:2: ( ( rule__System__NameAssignment_2 ) )
			// InternalDsl.g:1069:2: ( rule__System__NameAssignment_2 )
			{
			 before(grammarAccess.getSystemAccess().getNameAssignment_2()); 
			// InternalDsl.g:1070:2: ( rule__System__NameAssignment_2 )
			// InternalDsl.g:1070:3: rule__System__NameAssignment_2
			{
			pushFollow(FOLLOW_2);
			rule__System__NameAssignment_2();
			state._fsp--;

			}

			 after(grammarAccess.getSystemAccess().getNameAssignment_2()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__System__Group__2__Impl"



	// $ANTLR start "rule__Description__Group__0"
	// InternalDsl.g:1079:1: rule__Description__Group__0 : rule__Description__Group__0__Impl rule__Description__Group__1 ;
	public final void rule__Description__Group__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1083:2: ( rule__Description__Group__0__Impl rule__Description__Group__1 )
			// InternalDsl.g:1084:2: rule__Description__Group__0__Impl rule__Description__Group__1
			{
			pushFollow(FOLLOW_14);
			rule__Description__Group__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Description__Group__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Description__Group__0"



	// $ANTLR start "rule__Description__Group__0__Impl"
	// InternalDsl.g:1091:1: rule__Description__Group__0__Impl : ( 'Description' ) ;
	public final void rule__Description__Group__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1095:2: ( ( 'Description' ) )
			// InternalDsl.g:1096:2: ( 'Description' )
			{
			// InternalDsl.g:1096:2: ( 'Description' )
			// InternalDsl.g:1097:2: 'Description'
			{
			 before(grammarAccess.getDescriptionAccess().getDescriptionKeyword_0()); 
			match(input,19,FOLLOW_2); 
			 after(grammarAccess.getDescriptionAccess().getDescriptionKeyword_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Description__Group__0__Impl"



	// $ANTLR start "rule__Description__Group__1"
	// InternalDsl.g:1106:1: rule__Description__Group__1 : rule__Description__Group__1__Impl rule__Description__Group__2 ;
	public final void rule__Description__Group__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1110:2: ( rule__Description__Group__1__Impl rule__Description__Group__2 )
			// InternalDsl.g:1111:2: rule__Description__Group__1__Impl rule__Description__Group__2
			{
			pushFollow(FOLLOW_15);
			rule__Description__Group__1__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Description__Group__2();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Description__Group__1"



	// $ANTLR start "rule__Description__Group__1__Impl"
	// InternalDsl.g:1118:1: rule__Description__Group__1__Impl : ( ':' ) ;
	public final void rule__Description__Group__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1122:2: ( ( ':' ) )
			// InternalDsl.g:1123:2: ( ':' )
			{
			// InternalDsl.g:1123:2: ( ':' )
			// InternalDsl.g:1124:2: ':'
			{
			 before(grammarAccess.getDescriptionAccess().getColonKeyword_1()); 
			match(input,14,FOLLOW_2); 
			 after(grammarAccess.getDescriptionAccess().getColonKeyword_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Description__Group__1__Impl"



	// $ANTLR start "rule__Description__Group__2"
	// InternalDsl.g:1133:1: rule__Description__Group__2 : rule__Description__Group__2__Impl rule__Description__Group__3 ;
	public final void rule__Description__Group__2() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1137:2: ( rule__Description__Group__2__Impl rule__Description__Group__3 )
			// InternalDsl.g:1138:2: rule__Description__Group__2__Impl rule__Description__Group__3
			{
			pushFollow(FOLLOW_16);
			rule__Description__Group__2__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Description__Group__3();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Description__Group__2"



	// $ANTLR start "rule__Description__Group__2__Impl"
	// InternalDsl.g:1145:1: rule__Description__Group__2__Impl : ( ( rule__Description__NameAssignment_2 ) ) ;
	public final void rule__Description__Group__2__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1149:2: ( ( ( rule__Description__NameAssignment_2 ) ) )
			// InternalDsl.g:1150:2: ( ( rule__Description__NameAssignment_2 ) )
			{
			// InternalDsl.g:1150:2: ( ( rule__Description__NameAssignment_2 ) )
			// InternalDsl.g:1151:2: ( rule__Description__NameAssignment_2 )
			{
			 before(grammarAccess.getDescriptionAccess().getNameAssignment_2()); 
			// InternalDsl.g:1152:2: ( rule__Description__NameAssignment_2 )
			// InternalDsl.g:1152:3: rule__Description__NameAssignment_2
			{
			pushFollow(FOLLOW_2);
			rule__Description__NameAssignment_2();
			state._fsp--;

			}

			 after(grammarAccess.getDescriptionAccess().getNameAssignment_2()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Description__Group__2__Impl"



	// $ANTLR start "rule__Description__Group__3"
	// InternalDsl.g:1160:1: rule__Description__Group__3 : rule__Description__Group__3__Impl ;
	public final void rule__Description__Group__3() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1164:2: ( rule__Description__Group__3__Impl )
			// InternalDsl.g:1165:2: rule__Description__Group__3__Impl
			{
			pushFollow(FOLLOW_2);
			rule__Description__Group__3__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Description__Group__3"



	// $ANTLR start "rule__Description__Group__3__Impl"
	// InternalDsl.g:1171:1: rule__Description__Group__3__Impl : ( ';' ) ;
	public final void rule__Description__Group__3__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1175:2: ( ( ';' ) )
			// InternalDsl.g:1176:2: ( ';' )
			{
			// InternalDsl.g:1176:2: ( ';' )
			// InternalDsl.g:1177:2: ';'
			{
			 before(grammarAccess.getDescriptionAccess().getSemicolonKeyword_3()); 
			match(input,15,FOLLOW_2); 
			 after(grammarAccess.getDescriptionAccess().getSemicolonKeyword_3()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Description__Group__3__Impl"



	// $ANTLR start "rule__Definition__Group__0"
	// InternalDsl.g:1187:1: rule__Definition__Group__0 : rule__Definition__Group__0__Impl rule__Definition__Group__1 ;
	public final void rule__Definition__Group__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1191:2: ( rule__Definition__Group__0__Impl rule__Definition__Group__1 )
			// InternalDsl.g:1192:2: rule__Definition__Group__0__Impl rule__Definition__Group__1
			{
			pushFollow(FOLLOW_14);
			rule__Definition__Group__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Definition__Group__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group__0"



	// $ANTLR start "rule__Definition__Group__0__Impl"
	// InternalDsl.g:1199:1: rule__Definition__Group__0__Impl : ( 'Definition' ) ;
	public final void rule__Definition__Group__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1203:2: ( ( 'Definition' ) )
			// InternalDsl.g:1204:2: ( 'Definition' )
			{
			// InternalDsl.g:1204:2: ( 'Definition' )
			// InternalDsl.g:1205:2: 'Definition'
			{
			 before(grammarAccess.getDefinitionAccess().getDefinitionKeyword_0()); 
			match(input,18,FOLLOW_2); 
			 after(grammarAccess.getDefinitionAccess().getDefinitionKeyword_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group__0__Impl"



	// $ANTLR start "rule__Definition__Group__1"
	// InternalDsl.g:1214:1: rule__Definition__Group__1 : rule__Definition__Group__1__Impl rule__Definition__Group__2 ;
	public final void rule__Definition__Group__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1218:2: ( rule__Definition__Group__1__Impl rule__Definition__Group__2 )
			// InternalDsl.g:1219:2: rule__Definition__Group__1__Impl rule__Definition__Group__2
			{
			pushFollow(FOLLOW_6);
			rule__Definition__Group__1__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Definition__Group__2();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group__1"



	// $ANTLR start "rule__Definition__Group__1__Impl"
	// InternalDsl.g:1226:1: rule__Definition__Group__1__Impl : ( ':' ) ;
	public final void rule__Definition__Group__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1230:2: ( ( ':' ) )
			// InternalDsl.g:1231:2: ( ':' )
			{
			// InternalDsl.g:1231:2: ( ':' )
			// InternalDsl.g:1232:2: ':'
			{
			 before(grammarAccess.getDefinitionAccess().getColonKeyword_1()); 
			match(input,14,FOLLOW_2); 
			 after(grammarAccess.getDefinitionAccess().getColonKeyword_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group__1__Impl"



	// $ANTLR start "rule__Definition__Group__2"
	// InternalDsl.g:1241:1: rule__Definition__Group__2 : rule__Definition__Group__2__Impl rule__Definition__Group__3 ;
	public final void rule__Definition__Group__2() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1245:2: ( rule__Definition__Group__2__Impl rule__Definition__Group__3 )
			// InternalDsl.g:1246:2: rule__Definition__Group__2__Impl rule__Definition__Group__3
			{
			pushFollow(FOLLOW_4);
			rule__Definition__Group__2__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Definition__Group__3();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group__2"



	// $ANTLR start "rule__Definition__Group__2__Impl"
	// InternalDsl.g:1253:1: rule__Definition__Group__2__Impl : ( '{' ) ;
	public final void rule__Definition__Group__2__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1257:2: ( ( '{' ) )
			// InternalDsl.g:1258:2: ( '{' )
			{
			// InternalDsl.g:1258:2: ( '{' )
			// InternalDsl.g:1259:2: '{'
			{
			 before(grammarAccess.getDefinitionAccess().getLeftCurlyBracketKeyword_2()); 
			match(input,33,FOLLOW_2); 
			 after(grammarAccess.getDefinitionAccess().getLeftCurlyBracketKeyword_2()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group__2__Impl"



	// $ANTLR start "rule__Definition__Group__3"
	// InternalDsl.g:1268:1: rule__Definition__Group__3 : rule__Definition__Group__3__Impl rule__Definition__Group__4 ;
	public final void rule__Definition__Group__3() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1272:2: ( rule__Definition__Group__3__Impl rule__Definition__Group__4 )
			// InternalDsl.g:1273:2: rule__Definition__Group__3__Impl rule__Definition__Group__4
			{
			pushFollow(FOLLOW_14);
			rule__Definition__Group__3__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Definition__Group__4();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group__3"



	// $ANTLR start "rule__Definition__Group__3__Impl"
	// InternalDsl.g:1280:1: rule__Definition__Group__3__Impl : ( ( ( rule__Definition__ParameterAAssignment_3 ) ) ( ( rule__Definition__ParameterAAssignment_3 )* ) ) ;
	public final void rule__Definition__Group__3__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1284:2: ( ( ( ( rule__Definition__ParameterAAssignment_3 ) ) ( ( rule__Definition__ParameterAAssignment_3 )* ) ) )
			// InternalDsl.g:1285:2: ( ( ( rule__Definition__ParameterAAssignment_3 ) ) ( ( rule__Definition__ParameterAAssignment_3 )* ) )
			{
			// InternalDsl.g:1285:2: ( ( ( rule__Definition__ParameterAAssignment_3 ) ) ( ( rule__Definition__ParameterAAssignment_3 )* ) )
			// InternalDsl.g:1286:2: ( ( rule__Definition__ParameterAAssignment_3 ) ) ( ( rule__Definition__ParameterAAssignment_3 )* )
			{
			// InternalDsl.g:1286:2: ( ( rule__Definition__ParameterAAssignment_3 ) )
			// InternalDsl.g:1287:3: ( rule__Definition__ParameterAAssignment_3 )
			{
			 before(grammarAccess.getDefinitionAccess().getParameterAAssignment_3()); 
			// InternalDsl.g:1288:3: ( rule__Definition__ParameterAAssignment_3 )
			// InternalDsl.g:1288:4: rule__Definition__ParameterAAssignment_3
			{
			pushFollow(FOLLOW_17);
			rule__Definition__ParameterAAssignment_3();
			state._fsp--;

			}

			 after(grammarAccess.getDefinitionAccess().getParameterAAssignment_3()); 
			}

			// InternalDsl.g:1291:2: ( ( rule__Definition__ParameterAAssignment_3 )* )
			// InternalDsl.g:1292:3: ( rule__Definition__ParameterAAssignment_3 )*
			{
			 before(grammarAccess.getDefinitionAccess().getParameterAAssignment_3()); 
			// InternalDsl.g:1293:3: ( rule__Definition__ParameterAAssignment_3 )*
			loop6:
			while (true) {
				int alt6=2;
				int LA6_0 = input.LA(1);
				if ( (LA6_0==RULE_ID) ) {
					alt6=1;
				}

				switch (alt6) {
				case 1 :
					// InternalDsl.g:1293:4: rule__Definition__ParameterAAssignment_3
					{
					pushFollow(FOLLOW_17);
					rule__Definition__ParameterAAssignment_3();
					state._fsp--;

					}
					break;

				default :
					break loop6;
				}
			}

			 after(grammarAccess.getDefinitionAccess().getParameterAAssignment_3()); 
			}

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group__3__Impl"



	// $ANTLR start "rule__Definition__Group__4"
	// InternalDsl.g:1302:1: rule__Definition__Group__4 : rule__Definition__Group__4__Impl rule__Definition__Group__5 ;
	public final void rule__Definition__Group__4() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1306:2: ( rule__Definition__Group__4__Impl rule__Definition__Group__5 )
			// InternalDsl.g:1307:2: rule__Definition__Group__4__Impl rule__Definition__Group__5
			{
			pushFollow(FOLLOW_4);
			rule__Definition__Group__4__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Definition__Group__5();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group__4"



	// $ANTLR start "rule__Definition__Group__4__Impl"
	// InternalDsl.g:1314:1: rule__Definition__Group__4__Impl : ( ':' ) ;
	public final void rule__Definition__Group__4__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1318:2: ( ( ':' ) )
			// InternalDsl.g:1319:2: ( ':' )
			{
			// InternalDsl.g:1319:2: ( ':' )
			// InternalDsl.g:1320:2: ':'
			{
			 before(grammarAccess.getDefinitionAccess().getColonKeyword_4()); 
			match(input,14,FOLLOW_2); 
			 after(grammarAccess.getDefinitionAccess().getColonKeyword_4()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group__4__Impl"



	// $ANTLR start "rule__Definition__Group__5"
	// InternalDsl.g:1329:1: rule__Definition__Group__5 : rule__Definition__Group__5__Impl rule__Definition__Group__6 ;
	public final void rule__Definition__Group__5() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1333:2: ( rule__Definition__Group__5__Impl rule__Definition__Group__6 )
			// InternalDsl.g:1334:2: rule__Definition__Group__5__Impl rule__Definition__Group__6
			{
			pushFollow(FOLLOW_18);
			rule__Definition__Group__5__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Definition__Group__6();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group__5"



	// $ANTLR start "rule__Definition__Group__5__Impl"
	// InternalDsl.g:1341:1: rule__Definition__Group__5__Impl : ( ( ( rule__Definition__OptionsAAssignment_5 ) ) ( ( rule__Definition__OptionsAAssignment_5 )* ) ) ;
	public final void rule__Definition__Group__5__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1345:2: ( ( ( ( rule__Definition__OptionsAAssignment_5 ) ) ( ( rule__Definition__OptionsAAssignment_5 )* ) ) )
			// InternalDsl.g:1346:2: ( ( ( rule__Definition__OptionsAAssignment_5 ) ) ( ( rule__Definition__OptionsAAssignment_5 )* ) )
			{
			// InternalDsl.g:1346:2: ( ( ( rule__Definition__OptionsAAssignment_5 ) ) ( ( rule__Definition__OptionsAAssignment_5 )* ) )
			// InternalDsl.g:1347:2: ( ( rule__Definition__OptionsAAssignment_5 ) ) ( ( rule__Definition__OptionsAAssignment_5 )* )
			{
			// InternalDsl.g:1347:2: ( ( rule__Definition__OptionsAAssignment_5 ) )
			// InternalDsl.g:1348:3: ( rule__Definition__OptionsAAssignment_5 )
			{
			 before(grammarAccess.getDefinitionAccess().getOptionsAAssignment_5()); 
			// InternalDsl.g:1349:3: ( rule__Definition__OptionsAAssignment_5 )
			// InternalDsl.g:1349:4: rule__Definition__OptionsAAssignment_5
			{
			pushFollow(FOLLOW_17);
			rule__Definition__OptionsAAssignment_5();
			state._fsp--;

			}

			 after(grammarAccess.getDefinitionAccess().getOptionsAAssignment_5()); 
			}

			// InternalDsl.g:1352:2: ( ( rule__Definition__OptionsAAssignment_5 )* )
			// InternalDsl.g:1353:3: ( rule__Definition__OptionsAAssignment_5 )*
			{
			 before(grammarAccess.getDefinitionAccess().getOptionsAAssignment_5()); 
			// InternalDsl.g:1354:3: ( rule__Definition__OptionsAAssignment_5 )*
			loop7:
			while (true) {
				int alt7=2;
				int LA7_0 = input.LA(1);
				if ( (LA7_0==RULE_ID) ) {
					alt7=1;
				}

				switch (alt7) {
				case 1 :
					// InternalDsl.g:1354:4: rule__Definition__OptionsAAssignment_5
					{
					pushFollow(FOLLOW_17);
					rule__Definition__OptionsAAssignment_5();
					state._fsp--;

					}
					break;

				default :
					break loop7;
				}
			}

			 after(grammarAccess.getDefinitionAccess().getOptionsAAssignment_5()); 
			}

			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group__5__Impl"



	// $ANTLR start "rule__Definition__Group__6"
	// InternalDsl.g:1363:1: rule__Definition__Group__6 : rule__Definition__Group__6__Impl rule__Definition__Group__7 ;
	public final void rule__Definition__Group__6() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1367:2: ( rule__Definition__Group__6__Impl rule__Definition__Group__7 )
			// InternalDsl.g:1368:2: rule__Definition__Group__6__Impl rule__Definition__Group__7
			{
			pushFollow(FOLLOW_10);
			rule__Definition__Group__6__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Definition__Group__7();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group__6"



	// $ANTLR start "rule__Definition__Group__6__Impl"
	// InternalDsl.g:1375:1: rule__Definition__Group__6__Impl : ( ( rule__Definition__Group_6__0 )* ) ;
	public final void rule__Definition__Group__6__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1379:2: ( ( ( rule__Definition__Group_6__0 )* ) )
			// InternalDsl.g:1380:2: ( ( rule__Definition__Group_6__0 )* )
			{
			// InternalDsl.g:1380:2: ( ( rule__Definition__Group_6__0 )* )
			// InternalDsl.g:1381:2: ( rule__Definition__Group_6__0 )*
			{
			 before(grammarAccess.getDefinitionAccess().getGroup_6()); 
			// InternalDsl.g:1382:2: ( rule__Definition__Group_6__0 )*
			loop8:
			while (true) {
				int alt8=2;
				int LA8_0 = input.LA(1);
				if ( (LA8_0==11) ) {
					alt8=1;
				}

				switch (alt8) {
				case 1 :
					// InternalDsl.g:1382:3: rule__Definition__Group_6__0
					{
					pushFollow(FOLLOW_13);
					rule__Definition__Group_6__0();
					state._fsp--;

					}
					break;

				default :
					break loop8;
				}
			}

			 after(grammarAccess.getDefinitionAccess().getGroup_6()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group__6__Impl"



	// $ANTLR start "rule__Definition__Group__7"
	// InternalDsl.g:1390:1: rule__Definition__Group__7 : rule__Definition__Group__7__Impl rule__Definition__Group__8 ;
	public final void rule__Definition__Group__7() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1394:2: ( rule__Definition__Group__7__Impl rule__Definition__Group__8 )
			// InternalDsl.g:1395:2: rule__Definition__Group__7__Impl rule__Definition__Group__8
			{
			pushFollow(FOLLOW_19);
			rule__Definition__Group__7__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Definition__Group__8();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group__7"



	// $ANTLR start "rule__Definition__Group__7__Impl"
	// InternalDsl.g:1402:1: rule__Definition__Group__7__Impl : ( '}' ) ;
	public final void rule__Definition__Group__7__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1406:2: ( ( '}' ) )
			// InternalDsl.g:1407:2: ( '}' )
			{
			// InternalDsl.g:1407:2: ( '}' )
			// InternalDsl.g:1408:2: '}'
			{
			 before(grammarAccess.getDefinitionAccess().getRightCurlyBracketKeyword_7()); 
			match(input,34,FOLLOW_2); 
			 after(grammarAccess.getDefinitionAccess().getRightCurlyBracketKeyword_7()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group__7__Impl"



	// $ANTLR start "rule__Definition__Group__8"
	// InternalDsl.g:1417:1: rule__Definition__Group__8 : rule__Definition__Group__8__Impl rule__Definition__Group__9 ;
	public final void rule__Definition__Group__8() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1421:2: ( rule__Definition__Group__8__Impl rule__Definition__Group__9 )
			// InternalDsl.g:1422:2: rule__Definition__Group__8__Impl rule__Definition__Group__9
			{
			pushFollow(FOLLOW_20);
			rule__Definition__Group__8__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Definition__Group__9();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group__8"



	// $ANTLR start "rule__Definition__Group__8__Impl"
	// InternalDsl.g:1429:1: rule__Definition__Group__8__Impl : ( 'is' ) ;
	public final void rule__Definition__Group__8__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1433:2: ( ( 'is' ) )
			// InternalDsl.g:1434:2: ( 'is' )
			{
			// InternalDsl.g:1434:2: ( 'is' )
			// InternalDsl.g:1435:2: 'is'
			{
			 before(grammarAccess.getDefinitionAccess().getIsKeyword_8()); 
			match(input,28,FOLLOW_2); 
			 after(grammarAccess.getDefinitionAccess().getIsKeyword_8()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group__8__Impl"



	// $ANTLR start "rule__Definition__Group__9"
	// InternalDsl.g:1444:1: rule__Definition__Group__9 : rule__Definition__Group__9__Impl rule__Definition__Group__10 ;
	public final void rule__Definition__Group__9() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1448:2: ( rule__Definition__Group__9__Impl rule__Definition__Group__10 )
			// InternalDsl.g:1449:2: rule__Definition__Group__9__Impl rule__Definition__Group__10
			{
			pushFollow(FOLLOW_21);
			rule__Definition__Group__9__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Definition__Group__10();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group__9"



	// $ANTLR start "rule__Definition__Group__9__Impl"
	// InternalDsl.g:1456:1: rule__Definition__Group__9__Impl : ( ( rule__Definition__PropertyAssignment_9 ) ) ;
	public final void rule__Definition__Group__9__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1460:2: ( ( ( rule__Definition__PropertyAssignment_9 ) ) )
			// InternalDsl.g:1461:2: ( ( rule__Definition__PropertyAssignment_9 ) )
			{
			// InternalDsl.g:1461:2: ( ( rule__Definition__PropertyAssignment_9 ) )
			// InternalDsl.g:1462:2: ( rule__Definition__PropertyAssignment_9 )
			{
			 before(grammarAccess.getDefinitionAccess().getPropertyAssignment_9()); 
			// InternalDsl.g:1463:2: ( rule__Definition__PropertyAssignment_9 )
			// InternalDsl.g:1463:3: rule__Definition__PropertyAssignment_9
			{
			pushFollow(FOLLOW_2);
			rule__Definition__PropertyAssignment_9();
			state._fsp--;

			}

			 after(grammarAccess.getDefinitionAccess().getPropertyAssignment_9()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group__9__Impl"



	// $ANTLR start "rule__Definition__Group__10"
	// InternalDsl.g:1471:1: rule__Definition__Group__10 : rule__Definition__Group__10__Impl rule__Definition__Group__11 ;
	public final void rule__Definition__Group__10() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1475:2: ( rule__Definition__Group__10__Impl rule__Definition__Group__11 )
			// InternalDsl.g:1476:2: rule__Definition__Group__10__Impl rule__Definition__Group__11
			{
			pushFollow(FOLLOW_16);
			rule__Definition__Group__10__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Definition__Group__11();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group__10"



	// $ANTLR start "rule__Definition__Group__10__Impl"
	// InternalDsl.g:1483:1: rule__Definition__Group__10__Impl : ( ( rule__Definition__Group_10__0 )? ) ;
	public final void rule__Definition__Group__10__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1487:2: ( ( ( rule__Definition__Group_10__0 )? ) )
			// InternalDsl.g:1488:2: ( ( rule__Definition__Group_10__0 )? )
			{
			// InternalDsl.g:1488:2: ( ( rule__Definition__Group_10__0 )? )
			// InternalDsl.g:1489:2: ( rule__Definition__Group_10__0 )?
			{
			 before(grammarAccess.getDefinitionAccess().getGroup_10()); 
			// InternalDsl.g:1490:2: ( rule__Definition__Group_10__0 )?
			int alt9=2;
			int LA9_0 = input.LA(1);
			if ( (LA9_0==32) ) {
				alt9=1;
			}
			switch (alt9) {
				case 1 :
					// InternalDsl.g:1490:3: rule__Definition__Group_10__0
					{
					pushFollow(FOLLOW_2);
					rule__Definition__Group_10__0();
					state._fsp--;

					}
					break;

			}

			 after(grammarAccess.getDefinitionAccess().getGroup_10()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group__10__Impl"



	// $ANTLR start "rule__Definition__Group__11"
	// InternalDsl.g:1498:1: rule__Definition__Group__11 : rule__Definition__Group__11__Impl ;
	public final void rule__Definition__Group__11() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1502:2: ( rule__Definition__Group__11__Impl )
			// InternalDsl.g:1503:2: rule__Definition__Group__11__Impl
			{
			pushFollow(FOLLOW_2);
			rule__Definition__Group__11__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group__11"



	// $ANTLR start "rule__Definition__Group__11__Impl"
	// InternalDsl.g:1509:1: rule__Definition__Group__11__Impl : ( ';' ) ;
	public final void rule__Definition__Group__11__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1513:2: ( ( ';' ) )
			// InternalDsl.g:1514:2: ( ';' )
			{
			// InternalDsl.g:1514:2: ( ';' )
			// InternalDsl.g:1515:2: ';'
			{
			 before(grammarAccess.getDefinitionAccess().getSemicolonKeyword_11()); 
			match(input,15,FOLLOW_2); 
			 after(grammarAccess.getDefinitionAccess().getSemicolonKeyword_11()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group__11__Impl"



	// $ANTLR start "rule__Definition__Group_6__0"
	// InternalDsl.g:1525:1: rule__Definition__Group_6__0 : rule__Definition__Group_6__0__Impl rule__Definition__Group_6__1 ;
	public final void rule__Definition__Group_6__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1529:2: ( rule__Definition__Group_6__0__Impl rule__Definition__Group_6__1 )
			// InternalDsl.g:1530:2: rule__Definition__Group_6__0__Impl rule__Definition__Group_6__1
			{
			pushFollow(FOLLOW_4);
			rule__Definition__Group_6__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Definition__Group_6__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group_6__0"



	// $ANTLR start "rule__Definition__Group_6__0__Impl"
	// InternalDsl.g:1537:1: rule__Definition__Group_6__0__Impl : ( ',' ) ;
	public final void rule__Definition__Group_6__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1541:2: ( ( ',' ) )
			// InternalDsl.g:1542:2: ( ',' )
			{
			// InternalDsl.g:1542:2: ( ',' )
			// InternalDsl.g:1543:2: ','
			{
			 before(grammarAccess.getDefinitionAccess().getCommaKeyword_6_0()); 
			match(input,11,FOLLOW_2); 
			 after(grammarAccess.getDefinitionAccess().getCommaKeyword_6_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group_6__0__Impl"



	// $ANTLR start "rule__Definition__Group_6__1"
	// InternalDsl.g:1552:1: rule__Definition__Group_6__1 : rule__Definition__Group_6__1__Impl rule__Definition__Group_6__2 ;
	public final void rule__Definition__Group_6__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1556:2: ( rule__Definition__Group_6__1__Impl rule__Definition__Group_6__2 )
			// InternalDsl.g:1557:2: rule__Definition__Group_6__1__Impl rule__Definition__Group_6__2
			{
			pushFollow(FOLLOW_14);
			rule__Definition__Group_6__1__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Definition__Group_6__2();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group_6__1"



	// $ANTLR start "rule__Definition__Group_6__1__Impl"
	// InternalDsl.g:1564:1: rule__Definition__Group_6__1__Impl : ( ( rule__Definition__ParameterAAssignment_6_1 ) ) ;
	public final void rule__Definition__Group_6__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1568:2: ( ( ( rule__Definition__ParameterAAssignment_6_1 ) ) )
			// InternalDsl.g:1569:2: ( ( rule__Definition__ParameterAAssignment_6_1 ) )
			{
			// InternalDsl.g:1569:2: ( ( rule__Definition__ParameterAAssignment_6_1 ) )
			// InternalDsl.g:1570:2: ( rule__Definition__ParameterAAssignment_6_1 )
			{
			 before(grammarAccess.getDefinitionAccess().getParameterAAssignment_6_1()); 
			// InternalDsl.g:1571:2: ( rule__Definition__ParameterAAssignment_6_1 )
			// InternalDsl.g:1571:3: rule__Definition__ParameterAAssignment_6_1
			{
			pushFollow(FOLLOW_2);
			rule__Definition__ParameterAAssignment_6_1();
			state._fsp--;

			}

			 after(grammarAccess.getDefinitionAccess().getParameterAAssignment_6_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group_6__1__Impl"



	// $ANTLR start "rule__Definition__Group_6__2"
	// InternalDsl.g:1579:1: rule__Definition__Group_6__2 : rule__Definition__Group_6__2__Impl rule__Definition__Group_6__3 ;
	public final void rule__Definition__Group_6__2() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1583:2: ( rule__Definition__Group_6__2__Impl rule__Definition__Group_6__3 )
			// InternalDsl.g:1584:2: rule__Definition__Group_6__2__Impl rule__Definition__Group_6__3
			{
			pushFollow(FOLLOW_4);
			rule__Definition__Group_6__2__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Definition__Group_6__3();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group_6__2"



	// $ANTLR start "rule__Definition__Group_6__2__Impl"
	// InternalDsl.g:1591:1: rule__Definition__Group_6__2__Impl : ( ':' ) ;
	public final void rule__Definition__Group_6__2__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1595:2: ( ( ':' ) )
			// InternalDsl.g:1596:2: ( ':' )
			{
			// InternalDsl.g:1596:2: ( ':' )
			// InternalDsl.g:1597:2: ':'
			{
			 before(grammarAccess.getDefinitionAccess().getColonKeyword_6_2()); 
			match(input,14,FOLLOW_2); 
			 after(grammarAccess.getDefinitionAccess().getColonKeyword_6_2()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group_6__2__Impl"



	// $ANTLR start "rule__Definition__Group_6__3"
	// InternalDsl.g:1606:1: rule__Definition__Group_6__3 : rule__Definition__Group_6__3__Impl ;
	public final void rule__Definition__Group_6__3() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1610:2: ( rule__Definition__Group_6__3__Impl )
			// InternalDsl.g:1611:2: rule__Definition__Group_6__3__Impl
			{
			pushFollow(FOLLOW_2);
			rule__Definition__Group_6__3__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group_6__3"



	// $ANTLR start "rule__Definition__Group_6__3__Impl"
	// InternalDsl.g:1617:1: rule__Definition__Group_6__3__Impl : ( ( rule__Definition__OptionsAAssignment_6_3 ) ) ;
	public final void rule__Definition__Group_6__3__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1621:2: ( ( ( rule__Definition__OptionsAAssignment_6_3 ) ) )
			// InternalDsl.g:1622:2: ( ( rule__Definition__OptionsAAssignment_6_3 ) )
			{
			// InternalDsl.g:1622:2: ( ( rule__Definition__OptionsAAssignment_6_3 ) )
			// InternalDsl.g:1623:2: ( rule__Definition__OptionsAAssignment_6_3 )
			{
			 before(grammarAccess.getDefinitionAccess().getOptionsAAssignment_6_3()); 
			// InternalDsl.g:1624:2: ( rule__Definition__OptionsAAssignment_6_3 )
			// InternalDsl.g:1624:3: rule__Definition__OptionsAAssignment_6_3
			{
			pushFollow(FOLLOW_2);
			rule__Definition__OptionsAAssignment_6_3();
			state._fsp--;

			}

			 after(grammarAccess.getDefinitionAccess().getOptionsAAssignment_6_3()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group_6__3__Impl"



	// $ANTLR start "rule__Definition__Group_10__0"
	// InternalDsl.g:1633:1: rule__Definition__Group_10__0 : rule__Definition__Group_10__0__Impl rule__Definition__Group_10__1 ;
	public final void rule__Definition__Group_10__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1637:2: ( rule__Definition__Group_10__0__Impl rule__Definition__Group_10__1 )
			// InternalDsl.g:1638:2: rule__Definition__Group_10__0__Impl rule__Definition__Group_10__1
			{
			pushFollow(FOLLOW_6);
			rule__Definition__Group_10__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Definition__Group_10__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group_10__0"



	// $ANTLR start "rule__Definition__Group_10__0__Impl"
	// InternalDsl.g:1645:1: rule__Definition__Group_10__0__Impl : ( 'than' ) ;
	public final void rule__Definition__Group_10__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1649:2: ( ( 'than' ) )
			// InternalDsl.g:1650:2: ( 'than' )
			{
			// InternalDsl.g:1650:2: ( 'than' )
			// InternalDsl.g:1651:2: 'than'
			{
			 before(grammarAccess.getDefinitionAccess().getThanKeyword_10_0()); 
			match(input,32,FOLLOW_2); 
			 after(grammarAccess.getDefinitionAccess().getThanKeyword_10_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group_10__0__Impl"



	// $ANTLR start "rule__Definition__Group_10__1"
	// InternalDsl.g:1660:1: rule__Definition__Group_10__1 : rule__Definition__Group_10__1__Impl rule__Definition__Group_10__2 ;
	public final void rule__Definition__Group_10__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1664:2: ( rule__Definition__Group_10__1__Impl rule__Definition__Group_10__2 )
			// InternalDsl.g:1665:2: rule__Definition__Group_10__1__Impl rule__Definition__Group_10__2
			{
			pushFollow(FOLLOW_4);
			rule__Definition__Group_10__1__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Definition__Group_10__2();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group_10__1"



	// $ANTLR start "rule__Definition__Group_10__1__Impl"
	// InternalDsl.g:1672:1: rule__Definition__Group_10__1__Impl : ( '{' ) ;
	public final void rule__Definition__Group_10__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1676:2: ( ( '{' ) )
			// InternalDsl.g:1677:2: ( '{' )
			{
			// InternalDsl.g:1677:2: ( '{' )
			// InternalDsl.g:1678:2: '{'
			{
			 before(grammarAccess.getDefinitionAccess().getLeftCurlyBracketKeyword_10_1()); 
			match(input,33,FOLLOW_2); 
			 after(grammarAccess.getDefinitionAccess().getLeftCurlyBracketKeyword_10_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group_10__1__Impl"



	// $ANTLR start "rule__Definition__Group_10__2"
	// InternalDsl.g:1687:1: rule__Definition__Group_10__2 : rule__Definition__Group_10__2__Impl rule__Definition__Group_10__3 ;
	public final void rule__Definition__Group_10__2() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1691:2: ( rule__Definition__Group_10__2__Impl rule__Definition__Group_10__3 )
			// InternalDsl.g:1692:2: rule__Definition__Group_10__2__Impl rule__Definition__Group_10__3
			{
			pushFollow(FOLLOW_14);
			rule__Definition__Group_10__2__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Definition__Group_10__3();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group_10__2"



	// $ANTLR start "rule__Definition__Group_10__2__Impl"
	// InternalDsl.g:1699:1: rule__Definition__Group_10__2__Impl : ( ( rule__Definition__ParameterBAssignment_10_2 ) ) ;
	public final void rule__Definition__Group_10__2__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1703:2: ( ( ( rule__Definition__ParameterBAssignment_10_2 ) ) )
			// InternalDsl.g:1704:2: ( ( rule__Definition__ParameterBAssignment_10_2 ) )
			{
			// InternalDsl.g:1704:2: ( ( rule__Definition__ParameterBAssignment_10_2 ) )
			// InternalDsl.g:1705:2: ( rule__Definition__ParameterBAssignment_10_2 )
			{
			 before(grammarAccess.getDefinitionAccess().getParameterBAssignment_10_2()); 
			// InternalDsl.g:1706:2: ( rule__Definition__ParameterBAssignment_10_2 )
			// InternalDsl.g:1706:3: rule__Definition__ParameterBAssignment_10_2
			{
			pushFollow(FOLLOW_2);
			rule__Definition__ParameterBAssignment_10_2();
			state._fsp--;

			}

			 after(grammarAccess.getDefinitionAccess().getParameterBAssignment_10_2()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group_10__2__Impl"



	// $ANTLR start "rule__Definition__Group_10__3"
	// InternalDsl.g:1714:1: rule__Definition__Group_10__3 : rule__Definition__Group_10__3__Impl rule__Definition__Group_10__4 ;
	public final void rule__Definition__Group_10__3() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1718:2: ( rule__Definition__Group_10__3__Impl rule__Definition__Group_10__4 )
			// InternalDsl.g:1719:2: rule__Definition__Group_10__3__Impl rule__Definition__Group_10__4
			{
			pushFollow(FOLLOW_4);
			rule__Definition__Group_10__3__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Definition__Group_10__4();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group_10__3"



	// $ANTLR start "rule__Definition__Group_10__3__Impl"
	// InternalDsl.g:1726:1: rule__Definition__Group_10__3__Impl : ( ':' ) ;
	public final void rule__Definition__Group_10__3__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1730:2: ( ( ':' ) )
			// InternalDsl.g:1731:2: ( ':' )
			{
			// InternalDsl.g:1731:2: ( ':' )
			// InternalDsl.g:1732:2: ':'
			{
			 before(grammarAccess.getDefinitionAccess().getColonKeyword_10_3()); 
			match(input,14,FOLLOW_2); 
			 after(grammarAccess.getDefinitionAccess().getColonKeyword_10_3()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group_10__3__Impl"



	// $ANTLR start "rule__Definition__Group_10__4"
	// InternalDsl.g:1741:1: rule__Definition__Group_10__4 : rule__Definition__Group_10__4__Impl rule__Definition__Group_10__5 ;
	public final void rule__Definition__Group_10__4() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1745:2: ( rule__Definition__Group_10__4__Impl rule__Definition__Group_10__5 )
			// InternalDsl.g:1746:2: rule__Definition__Group_10__4__Impl rule__Definition__Group_10__5
			{
			pushFollow(FOLLOW_18);
			rule__Definition__Group_10__4__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Definition__Group_10__5();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group_10__4"



	// $ANTLR start "rule__Definition__Group_10__4__Impl"
	// InternalDsl.g:1753:1: rule__Definition__Group_10__4__Impl : ( ( rule__Definition__OptionsBAssignment_10_4 ) ) ;
	public final void rule__Definition__Group_10__4__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1757:2: ( ( ( rule__Definition__OptionsBAssignment_10_4 ) ) )
			// InternalDsl.g:1758:2: ( ( rule__Definition__OptionsBAssignment_10_4 ) )
			{
			// InternalDsl.g:1758:2: ( ( rule__Definition__OptionsBAssignment_10_4 ) )
			// InternalDsl.g:1759:2: ( rule__Definition__OptionsBAssignment_10_4 )
			{
			 before(grammarAccess.getDefinitionAccess().getOptionsBAssignment_10_4()); 
			// InternalDsl.g:1760:2: ( rule__Definition__OptionsBAssignment_10_4 )
			// InternalDsl.g:1760:3: rule__Definition__OptionsBAssignment_10_4
			{
			pushFollow(FOLLOW_2);
			rule__Definition__OptionsBAssignment_10_4();
			state._fsp--;

			}

			 after(grammarAccess.getDefinitionAccess().getOptionsBAssignment_10_4()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group_10__4__Impl"



	// $ANTLR start "rule__Definition__Group_10__5"
	// InternalDsl.g:1768:1: rule__Definition__Group_10__5 : rule__Definition__Group_10__5__Impl rule__Definition__Group_10__6 ;
	public final void rule__Definition__Group_10__5() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1772:2: ( rule__Definition__Group_10__5__Impl rule__Definition__Group_10__6 )
			// InternalDsl.g:1773:2: rule__Definition__Group_10__5__Impl rule__Definition__Group_10__6
			{
			pushFollow(FOLLOW_10);
			rule__Definition__Group_10__5__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Definition__Group_10__6();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group_10__5"



	// $ANTLR start "rule__Definition__Group_10__5__Impl"
	// InternalDsl.g:1780:1: rule__Definition__Group_10__5__Impl : ( ( rule__Definition__Group_10_5__0 )* ) ;
	public final void rule__Definition__Group_10__5__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1784:2: ( ( ( rule__Definition__Group_10_5__0 )* ) )
			// InternalDsl.g:1785:2: ( ( rule__Definition__Group_10_5__0 )* )
			{
			// InternalDsl.g:1785:2: ( ( rule__Definition__Group_10_5__0 )* )
			// InternalDsl.g:1786:2: ( rule__Definition__Group_10_5__0 )*
			{
			 before(grammarAccess.getDefinitionAccess().getGroup_10_5()); 
			// InternalDsl.g:1787:2: ( rule__Definition__Group_10_5__0 )*
			loop10:
			while (true) {
				int alt10=2;
				int LA10_0 = input.LA(1);
				if ( (LA10_0==11) ) {
					alt10=1;
				}

				switch (alt10) {
				case 1 :
					// InternalDsl.g:1787:3: rule__Definition__Group_10_5__0
					{
					pushFollow(FOLLOW_13);
					rule__Definition__Group_10_5__0();
					state._fsp--;

					}
					break;

				default :
					break loop10;
				}
			}

			 after(grammarAccess.getDefinitionAccess().getGroup_10_5()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group_10__5__Impl"



	// $ANTLR start "rule__Definition__Group_10__6"
	// InternalDsl.g:1795:1: rule__Definition__Group_10__6 : rule__Definition__Group_10__6__Impl ;
	public final void rule__Definition__Group_10__6() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1799:2: ( rule__Definition__Group_10__6__Impl )
			// InternalDsl.g:1800:2: rule__Definition__Group_10__6__Impl
			{
			pushFollow(FOLLOW_2);
			rule__Definition__Group_10__6__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group_10__6"



	// $ANTLR start "rule__Definition__Group_10__6__Impl"
	// InternalDsl.g:1806:1: rule__Definition__Group_10__6__Impl : ( '}' ) ;
	public final void rule__Definition__Group_10__6__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1810:2: ( ( '}' ) )
			// InternalDsl.g:1811:2: ( '}' )
			{
			// InternalDsl.g:1811:2: ( '}' )
			// InternalDsl.g:1812:2: '}'
			{
			 before(grammarAccess.getDefinitionAccess().getRightCurlyBracketKeyword_10_6()); 
			match(input,34,FOLLOW_2); 
			 after(grammarAccess.getDefinitionAccess().getRightCurlyBracketKeyword_10_6()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group_10__6__Impl"



	// $ANTLR start "rule__Definition__Group_10_5__0"
	// InternalDsl.g:1822:1: rule__Definition__Group_10_5__0 : rule__Definition__Group_10_5__0__Impl rule__Definition__Group_10_5__1 ;
	public final void rule__Definition__Group_10_5__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1826:2: ( rule__Definition__Group_10_5__0__Impl rule__Definition__Group_10_5__1 )
			// InternalDsl.g:1827:2: rule__Definition__Group_10_5__0__Impl rule__Definition__Group_10_5__1
			{
			pushFollow(FOLLOW_4);
			rule__Definition__Group_10_5__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Definition__Group_10_5__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group_10_5__0"



	// $ANTLR start "rule__Definition__Group_10_5__0__Impl"
	// InternalDsl.g:1834:1: rule__Definition__Group_10_5__0__Impl : ( ',' ) ;
	public final void rule__Definition__Group_10_5__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1838:2: ( ( ',' ) )
			// InternalDsl.g:1839:2: ( ',' )
			{
			// InternalDsl.g:1839:2: ( ',' )
			// InternalDsl.g:1840:2: ','
			{
			 before(grammarAccess.getDefinitionAccess().getCommaKeyword_10_5_0()); 
			match(input,11,FOLLOW_2); 
			 after(grammarAccess.getDefinitionAccess().getCommaKeyword_10_5_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group_10_5__0__Impl"



	// $ANTLR start "rule__Definition__Group_10_5__1"
	// InternalDsl.g:1849:1: rule__Definition__Group_10_5__1 : rule__Definition__Group_10_5__1__Impl rule__Definition__Group_10_5__2 ;
	public final void rule__Definition__Group_10_5__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1853:2: ( rule__Definition__Group_10_5__1__Impl rule__Definition__Group_10_5__2 )
			// InternalDsl.g:1854:2: rule__Definition__Group_10_5__1__Impl rule__Definition__Group_10_5__2
			{
			pushFollow(FOLLOW_14);
			rule__Definition__Group_10_5__1__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Definition__Group_10_5__2();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group_10_5__1"



	// $ANTLR start "rule__Definition__Group_10_5__1__Impl"
	// InternalDsl.g:1861:1: rule__Definition__Group_10_5__1__Impl : ( ( rule__Definition__ParameterBAssignment_10_5_1 ) ) ;
	public final void rule__Definition__Group_10_5__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1865:2: ( ( ( rule__Definition__ParameterBAssignment_10_5_1 ) ) )
			// InternalDsl.g:1866:2: ( ( rule__Definition__ParameterBAssignment_10_5_1 ) )
			{
			// InternalDsl.g:1866:2: ( ( rule__Definition__ParameterBAssignment_10_5_1 ) )
			// InternalDsl.g:1867:2: ( rule__Definition__ParameterBAssignment_10_5_1 )
			{
			 before(grammarAccess.getDefinitionAccess().getParameterBAssignment_10_5_1()); 
			// InternalDsl.g:1868:2: ( rule__Definition__ParameterBAssignment_10_5_1 )
			// InternalDsl.g:1868:3: rule__Definition__ParameterBAssignment_10_5_1
			{
			pushFollow(FOLLOW_2);
			rule__Definition__ParameterBAssignment_10_5_1();
			state._fsp--;

			}

			 after(grammarAccess.getDefinitionAccess().getParameterBAssignment_10_5_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group_10_5__1__Impl"



	// $ANTLR start "rule__Definition__Group_10_5__2"
	// InternalDsl.g:1876:1: rule__Definition__Group_10_5__2 : rule__Definition__Group_10_5__2__Impl rule__Definition__Group_10_5__3 ;
	public final void rule__Definition__Group_10_5__2() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1880:2: ( rule__Definition__Group_10_5__2__Impl rule__Definition__Group_10_5__3 )
			// InternalDsl.g:1881:2: rule__Definition__Group_10_5__2__Impl rule__Definition__Group_10_5__3
			{
			pushFollow(FOLLOW_4);
			rule__Definition__Group_10_5__2__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Definition__Group_10_5__3();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group_10_5__2"



	// $ANTLR start "rule__Definition__Group_10_5__2__Impl"
	// InternalDsl.g:1888:1: rule__Definition__Group_10_5__2__Impl : ( ':' ) ;
	public final void rule__Definition__Group_10_5__2__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1892:2: ( ( ':' ) )
			// InternalDsl.g:1893:2: ( ':' )
			{
			// InternalDsl.g:1893:2: ( ':' )
			// InternalDsl.g:1894:2: ':'
			{
			 before(grammarAccess.getDefinitionAccess().getColonKeyword_10_5_2()); 
			match(input,14,FOLLOW_2); 
			 after(grammarAccess.getDefinitionAccess().getColonKeyword_10_5_2()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group_10_5__2__Impl"



	// $ANTLR start "rule__Definition__Group_10_5__3"
	// InternalDsl.g:1903:1: rule__Definition__Group_10_5__3 : rule__Definition__Group_10_5__3__Impl ;
	public final void rule__Definition__Group_10_5__3() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1907:2: ( rule__Definition__Group_10_5__3__Impl )
			// InternalDsl.g:1908:2: rule__Definition__Group_10_5__3__Impl
			{
			pushFollow(FOLLOW_2);
			rule__Definition__Group_10_5__3__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group_10_5__3"



	// $ANTLR start "rule__Definition__Group_10_5__3__Impl"
	// InternalDsl.g:1914:1: rule__Definition__Group_10_5__3__Impl : ( ( rule__Definition__OptionsBAssignment_10_5_3 ) ) ;
	public final void rule__Definition__Group_10_5__3__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1918:2: ( ( ( rule__Definition__OptionsBAssignment_10_5_3 ) ) )
			// InternalDsl.g:1919:2: ( ( rule__Definition__OptionsBAssignment_10_5_3 ) )
			{
			// InternalDsl.g:1919:2: ( ( rule__Definition__OptionsBAssignment_10_5_3 ) )
			// InternalDsl.g:1920:2: ( rule__Definition__OptionsBAssignment_10_5_3 )
			{
			 before(grammarAccess.getDefinitionAccess().getOptionsBAssignment_10_5_3()); 
			// InternalDsl.g:1921:2: ( rule__Definition__OptionsBAssignment_10_5_3 )
			// InternalDsl.g:1921:3: rule__Definition__OptionsBAssignment_10_5_3
			{
			pushFollow(FOLLOW_2);
			rule__Definition__OptionsBAssignment_10_5_3();
			state._fsp--;

			}

			 after(grammarAccess.getDefinitionAccess().getOptionsBAssignment_10_5_3()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__Group_10_5__3__Impl"



	// $ANTLR start "rule__Property__Group_0__0"
	// InternalDsl.g:1930:1: rule__Property__Group_0__0 : rule__Property__Group_0__0__Impl rule__Property__Group_0__1 ;
	public final void rule__Property__Group_0__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1934:2: ( rule__Property__Group_0__0__Impl rule__Property__Group_0__1 )
			// InternalDsl.g:1935:2: rule__Property__Group_0__0__Impl rule__Property__Group_0__1
			{
			pushFollow(FOLLOW_22);
			rule__Property__Group_0__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Property__Group_0__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Property__Group_0__0"



	// $ANTLR start "rule__Property__Group_0__0__Impl"
	// InternalDsl.g:1942:1: rule__Property__Group_0__0__Impl : ( 'more' ) ;
	public final void rule__Property__Group_0__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1946:2: ( ( 'more' ) )
			// InternalDsl.g:1947:2: ( 'more' )
			{
			// InternalDsl.g:1947:2: ( 'more' )
			// InternalDsl.g:1948:2: 'more'
			{
			 before(grammarAccess.getPropertyAccess().getMoreKeyword_0_0()); 
			match(input,30,FOLLOW_2); 
			 after(grammarAccess.getPropertyAccess().getMoreKeyword_0_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Property__Group_0__0__Impl"



	// $ANTLR start "rule__Property__Group_0__1"
	// InternalDsl.g:1957:1: rule__Property__Group_0__1 : rule__Property__Group_0__1__Impl ;
	public final void rule__Property__Group_0__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1961:2: ( rule__Property__Group_0__1__Impl )
			// InternalDsl.g:1962:2: rule__Property__Group_0__1__Impl
			{
			pushFollow(FOLLOW_2);
			rule__Property__Group_0__1__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Property__Group_0__1"



	// $ANTLR start "rule__Property__Group_0__1__Impl"
	// InternalDsl.g:1968:1: rule__Property__Group_0__1__Impl : ( 'efficient' ) ;
	public final void rule__Property__Group_0__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1972:2: ( ( 'efficient' ) )
			// InternalDsl.g:1973:2: ( 'efficient' )
			{
			// InternalDsl.g:1973:2: ( 'efficient' )
			// InternalDsl.g:1974:2: 'efficient'
			{
			 before(grammarAccess.getPropertyAccess().getEfficientKeyword_0_1()); 
			match(input,27,FOLLOW_2); 
			 after(grammarAccess.getPropertyAccess().getEfficientKeyword_0_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Property__Group_0__1__Impl"



	// $ANTLR start "rule__Property__Group_1__0"
	// InternalDsl.g:1984:1: rule__Property__Group_1__0 : rule__Property__Group_1__0__Impl rule__Property__Group_1__1 ;
	public final void rule__Property__Group_1__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:1988:2: ( rule__Property__Group_1__0__Impl rule__Property__Group_1__1 )
			// InternalDsl.g:1989:2: rule__Property__Group_1__0__Impl rule__Property__Group_1__1
			{
			pushFollow(FOLLOW_22);
			rule__Property__Group_1__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Property__Group_1__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Property__Group_1__0"



	// $ANTLR start "rule__Property__Group_1__0__Impl"
	// InternalDsl.g:1996:1: rule__Property__Group_1__0__Impl : ( 'less' ) ;
	public final void rule__Property__Group_1__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2000:2: ( ( 'less' ) )
			// InternalDsl.g:2001:2: ( 'less' )
			{
			// InternalDsl.g:2001:2: ( 'less' )
			// InternalDsl.g:2002:2: 'less'
			{
			 before(grammarAccess.getPropertyAccess().getLessKeyword_1_0()); 
			match(input,29,FOLLOW_2); 
			 after(grammarAccess.getPropertyAccess().getLessKeyword_1_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Property__Group_1__0__Impl"



	// $ANTLR start "rule__Property__Group_1__1"
	// InternalDsl.g:2011:1: rule__Property__Group_1__1 : rule__Property__Group_1__1__Impl ;
	public final void rule__Property__Group_1__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2015:2: ( rule__Property__Group_1__1__Impl )
			// InternalDsl.g:2016:2: rule__Property__Group_1__1__Impl
			{
			pushFollow(FOLLOW_2);
			rule__Property__Group_1__1__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Property__Group_1__1"



	// $ANTLR start "rule__Property__Group_1__1__Impl"
	// InternalDsl.g:2022:1: rule__Property__Group_1__1__Impl : ( 'efficient' ) ;
	public final void rule__Property__Group_1__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2026:2: ( ( 'efficient' ) )
			// InternalDsl.g:2027:2: ( 'efficient' )
			{
			// InternalDsl.g:2027:2: ( 'efficient' )
			// InternalDsl.g:2028:2: 'efficient'
			{
			 before(grammarAccess.getPropertyAccess().getEfficientKeyword_1_1()); 
			match(input,27,FOLLOW_2); 
			 after(grammarAccess.getPropertyAccess().getEfficientKeyword_1_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Property__Group_1__1__Impl"



	// $ANTLR start "rule__Property__Group_2__0"
	// InternalDsl.g:2038:1: rule__Property__Group_2__0 : rule__Property__Group_2__0__Impl rule__Property__Group_2__1 ;
	public final void rule__Property__Group_2__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2042:2: ( rule__Property__Group_2__0__Impl rule__Property__Group_2__1 )
			// InternalDsl.g:2043:2: rule__Property__Group_2__0__Impl rule__Property__Group_2__1
			{
			pushFollow(FOLLOW_23);
			rule__Property__Group_2__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Property__Group_2__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Property__Group_2__0"



	// $ANTLR start "rule__Property__Group_2__0__Impl"
	// InternalDsl.g:2050:1: rule__Property__Group_2__0__Impl : ( 'more' ) ;
	public final void rule__Property__Group_2__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2054:2: ( ( 'more' ) )
			// InternalDsl.g:2055:2: ( 'more' )
			{
			// InternalDsl.g:2055:2: ( 'more' )
			// InternalDsl.g:2056:2: 'more'
			{
			 before(grammarAccess.getPropertyAccess().getMoreKeyword_2_0()); 
			match(input,30,FOLLOW_2); 
			 after(grammarAccess.getPropertyAccess().getMoreKeyword_2_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Property__Group_2__0__Impl"



	// $ANTLR start "rule__Property__Group_2__1"
	// InternalDsl.g:2065:1: rule__Property__Group_2__1 : rule__Property__Group_2__1__Impl ;
	public final void rule__Property__Group_2__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2069:2: ( rule__Property__Group_2__1__Impl )
			// InternalDsl.g:2070:2: rule__Property__Group_2__1__Impl
			{
			pushFollow(FOLLOW_2);
			rule__Property__Group_2__1__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Property__Group_2__1"



	// $ANTLR start "rule__Property__Group_2__1__Impl"
	// InternalDsl.g:2076:1: rule__Property__Group_2__1__Impl : ( 'effective' ) ;
	public final void rule__Property__Group_2__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2080:2: ( ( 'effective' ) )
			// InternalDsl.g:2081:2: ( 'effective' )
			{
			// InternalDsl.g:2081:2: ( 'effective' )
			// InternalDsl.g:2082:2: 'effective'
			{
			 before(grammarAccess.getPropertyAccess().getEffectiveKeyword_2_1()); 
			match(input,26,FOLLOW_2); 
			 after(grammarAccess.getPropertyAccess().getEffectiveKeyword_2_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Property__Group_2__1__Impl"



	// $ANTLR start "rule__Property__Group_3__0"
	// InternalDsl.g:2092:1: rule__Property__Group_3__0 : rule__Property__Group_3__0__Impl rule__Property__Group_3__1 ;
	public final void rule__Property__Group_3__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2096:2: ( rule__Property__Group_3__0__Impl rule__Property__Group_3__1 )
			// InternalDsl.g:2097:2: rule__Property__Group_3__0__Impl rule__Property__Group_3__1
			{
			pushFollow(FOLLOW_23);
			rule__Property__Group_3__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Property__Group_3__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Property__Group_3__0"



	// $ANTLR start "rule__Property__Group_3__0__Impl"
	// InternalDsl.g:2104:1: rule__Property__Group_3__0__Impl : ( 'less' ) ;
	public final void rule__Property__Group_3__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2108:2: ( ( 'less' ) )
			// InternalDsl.g:2109:2: ( 'less' )
			{
			// InternalDsl.g:2109:2: ( 'less' )
			// InternalDsl.g:2110:2: 'less'
			{
			 before(grammarAccess.getPropertyAccess().getLessKeyword_3_0()); 
			match(input,29,FOLLOW_2); 
			 after(grammarAccess.getPropertyAccess().getLessKeyword_3_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Property__Group_3__0__Impl"



	// $ANTLR start "rule__Property__Group_3__1"
	// InternalDsl.g:2119:1: rule__Property__Group_3__1 : rule__Property__Group_3__1__Impl ;
	public final void rule__Property__Group_3__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2123:2: ( rule__Property__Group_3__1__Impl )
			// InternalDsl.g:2124:2: rule__Property__Group_3__1__Impl
			{
			pushFollow(FOLLOW_2);
			rule__Property__Group_3__1__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Property__Group_3__1"



	// $ANTLR start "rule__Property__Group_3__1__Impl"
	// InternalDsl.g:2130:1: rule__Property__Group_3__1__Impl : ( 'effective' ) ;
	public final void rule__Property__Group_3__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2134:2: ( ( 'effective' ) )
			// InternalDsl.g:2135:2: ( 'effective' )
			{
			// InternalDsl.g:2135:2: ( 'effective' )
			// InternalDsl.g:2136:2: 'effective'
			{
			 before(grammarAccess.getPropertyAccess().getEffectiveKeyword_3_1()); 
			match(input,26,FOLLOW_2); 
			 after(grammarAccess.getPropertyAccess().getEffectiveKeyword_3_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Property__Group_3__1__Impl"



	// $ANTLR start "rule__Constructs__Group__0"
	// InternalDsl.g:2146:1: rule__Constructs__Group__0 : rule__Constructs__Group__0__Impl rule__Constructs__Group__1 ;
	public final void rule__Constructs__Group__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2150:2: ( rule__Constructs__Group__0__Impl rule__Constructs__Group__1 )
			// InternalDsl.g:2151:2: rule__Constructs__Group__0__Impl rule__Constructs__Group__1
			{
			pushFollow(FOLLOW_14);
			rule__Constructs__Group__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Constructs__Group__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Constructs__Group__0"



	// $ANTLR start "rule__Constructs__Group__0__Impl"
	// InternalDsl.g:2158:1: rule__Constructs__Group__0__Impl : ( 'Constructs' ) ;
	public final void rule__Constructs__Group__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2162:2: ( ( 'Constructs' ) )
			// InternalDsl.g:2163:2: ( 'Constructs' )
			{
			// InternalDsl.g:2163:2: ( 'Constructs' )
			// InternalDsl.g:2164:2: 'Constructs'
			{
			 before(grammarAccess.getConstructsAccess().getConstructsKeyword_0()); 
			match(input,16,FOLLOW_2); 
			 after(grammarAccess.getConstructsAccess().getConstructsKeyword_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Constructs__Group__0__Impl"



	// $ANTLR start "rule__Constructs__Group__1"
	// InternalDsl.g:2173:1: rule__Constructs__Group__1 : rule__Constructs__Group__1__Impl rule__Constructs__Group__2 ;
	public final void rule__Constructs__Group__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2177:2: ( rule__Constructs__Group__1__Impl rule__Constructs__Group__2 )
			// InternalDsl.g:2178:2: rule__Constructs__Group__1__Impl rule__Constructs__Group__2
			{
			pushFollow(FOLLOW_24);
			rule__Constructs__Group__1__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Constructs__Group__2();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Constructs__Group__1"



	// $ANTLR start "rule__Constructs__Group__1__Impl"
	// InternalDsl.g:2185:1: rule__Constructs__Group__1__Impl : ( ':' ) ;
	public final void rule__Constructs__Group__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2189:2: ( ( ':' ) )
			// InternalDsl.g:2190:2: ( ':' )
			{
			// InternalDsl.g:2190:2: ( ':' )
			// InternalDsl.g:2191:2: ':'
			{
			 before(grammarAccess.getConstructsAccess().getColonKeyword_1()); 
			match(input,14,FOLLOW_2); 
			 after(grammarAccess.getConstructsAccess().getColonKeyword_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Constructs__Group__1__Impl"



	// $ANTLR start "rule__Constructs__Group__2"
	// InternalDsl.g:2200:1: rule__Constructs__Group__2 : rule__Constructs__Group__2__Impl rule__Constructs__Group__3 ;
	public final void rule__Constructs__Group__2() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2204:2: ( rule__Constructs__Group__2__Impl rule__Constructs__Group__3 )
			// InternalDsl.g:2205:2: rule__Constructs__Group__2__Impl rule__Constructs__Group__3
			{
			pushFollow(FOLLOW_4);
			rule__Constructs__Group__2__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Constructs__Group__3();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Constructs__Group__2"



	// $ANTLR start "rule__Constructs__Group__2__Impl"
	// InternalDsl.g:2212:1: rule__Constructs__Group__2__Impl : ( '[' ) ;
	public final void rule__Constructs__Group__2__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2216:2: ( ( '[' ) )
			// InternalDsl.g:2217:2: ( '[' )
			{
			// InternalDsl.g:2217:2: ( '[' )
			// InternalDsl.g:2218:2: '['
			{
			 before(grammarAccess.getConstructsAccess().getLeftSquareBracketKeyword_2()); 
			match(input,24,FOLLOW_2); 
			 after(grammarAccess.getConstructsAccess().getLeftSquareBracketKeyword_2()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Constructs__Group__2__Impl"



	// $ANTLR start "rule__Constructs__Group__3"
	// InternalDsl.g:2227:1: rule__Constructs__Group__3 : rule__Constructs__Group__3__Impl rule__Constructs__Group__4 ;
	public final void rule__Constructs__Group__3() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2231:2: ( rule__Constructs__Group__3__Impl rule__Constructs__Group__4 )
			// InternalDsl.g:2232:2: rule__Constructs__Group__3__Impl rule__Constructs__Group__4
			{
			pushFollow(FOLLOW_25);
			rule__Constructs__Group__3__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Constructs__Group__4();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Constructs__Group__3"



	// $ANTLR start "rule__Constructs__Group__3__Impl"
	// InternalDsl.g:2239:1: rule__Constructs__Group__3__Impl : ( ( rule__Constructs__ElementsAssignment_3 ) ) ;
	public final void rule__Constructs__Group__3__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2243:2: ( ( ( rule__Constructs__ElementsAssignment_3 ) ) )
			// InternalDsl.g:2244:2: ( ( rule__Constructs__ElementsAssignment_3 ) )
			{
			// InternalDsl.g:2244:2: ( ( rule__Constructs__ElementsAssignment_3 ) )
			// InternalDsl.g:2245:2: ( rule__Constructs__ElementsAssignment_3 )
			{
			 before(grammarAccess.getConstructsAccess().getElementsAssignment_3()); 
			// InternalDsl.g:2246:2: ( rule__Constructs__ElementsAssignment_3 )
			// InternalDsl.g:2246:3: rule__Constructs__ElementsAssignment_3
			{
			pushFollow(FOLLOW_2);
			rule__Constructs__ElementsAssignment_3();
			state._fsp--;

			}

			 after(grammarAccess.getConstructsAccess().getElementsAssignment_3()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Constructs__Group__3__Impl"



	// $ANTLR start "rule__Constructs__Group__4"
	// InternalDsl.g:2254:1: rule__Constructs__Group__4 : rule__Constructs__Group__4__Impl rule__Constructs__Group__5 ;
	public final void rule__Constructs__Group__4() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2258:2: ( rule__Constructs__Group__4__Impl rule__Constructs__Group__5 )
			// InternalDsl.g:2259:2: rule__Constructs__Group__4__Impl rule__Constructs__Group__5
			{
			pushFollow(FOLLOW_26);
			rule__Constructs__Group__4__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Constructs__Group__5();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Constructs__Group__4"



	// $ANTLR start "rule__Constructs__Group__4__Impl"
	// InternalDsl.g:2266:1: rule__Constructs__Group__4__Impl : ( ( rule__Constructs__Group_4__0 )* ) ;
	public final void rule__Constructs__Group__4__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2270:2: ( ( ( rule__Constructs__Group_4__0 )* ) )
			// InternalDsl.g:2271:2: ( ( rule__Constructs__Group_4__0 )* )
			{
			// InternalDsl.g:2271:2: ( ( rule__Constructs__Group_4__0 )* )
			// InternalDsl.g:2272:2: ( rule__Constructs__Group_4__0 )*
			{
			 before(grammarAccess.getConstructsAccess().getGroup_4()); 
			// InternalDsl.g:2273:2: ( rule__Constructs__Group_4__0 )*
			loop11:
			while (true) {
				int alt11=2;
				int LA11_0 = input.LA(1);
				if ( (LA11_0==11) ) {
					alt11=1;
				}

				switch (alt11) {
				case 1 :
					// InternalDsl.g:2273:3: rule__Constructs__Group_4__0
					{
					pushFollow(FOLLOW_13);
					rule__Constructs__Group_4__0();
					state._fsp--;

					}
					break;

				default :
					break loop11;
				}
			}

			 after(grammarAccess.getConstructsAccess().getGroup_4()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Constructs__Group__4__Impl"



	// $ANTLR start "rule__Constructs__Group__5"
	// InternalDsl.g:2281:1: rule__Constructs__Group__5 : rule__Constructs__Group__5__Impl rule__Constructs__Group__6 ;
	public final void rule__Constructs__Group__5() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2285:2: ( rule__Constructs__Group__5__Impl rule__Constructs__Group__6 )
			// InternalDsl.g:2286:2: rule__Constructs__Group__5__Impl rule__Constructs__Group__6
			{
			pushFollow(FOLLOW_16);
			rule__Constructs__Group__5__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Constructs__Group__6();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Constructs__Group__5"



	// $ANTLR start "rule__Constructs__Group__5__Impl"
	// InternalDsl.g:2293:1: rule__Constructs__Group__5__Impl : ( ']' ) ;
	public final void rule__Constructs__Group__5__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2297:2: ( ( ']' ) )
			// InternalDsl.g:2298:2: ( ']' )
			{
			// InternalDsl.g:2298:2: ( ']' )
			// InternalDsl.g:2299:2: ']'
			{
			 before(grammarAccess.getConstructsAccess().getRightSquareBracketKeyword_5()); 
			match(input,25,FOLLOW_2); 
			 after(grammarAccess.getConstructsAccess().getRightSquareBracketKeyword_5()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Constructs__Group__5__Impl"



	// $ANTLR start "rule__Constructs__Group__6"
	// InternalDsl.g:2308:1: rule__Constructs__Group__6 : rule__Constructs__Group__6__Impl ;
	public final void rule__Constructs__Group__6() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2312:2: ( rule__Constructs__Group__6__Impl )
			// InternalDsl.g:2313:2: rule__Constructs__Group__6__Impl
			{
			pushFollow(FOLLOW_2);
			rule__Constructs__Group__6__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Constructs__Group__6"



	// $ANTLR start "rule__Constructs__Group__6__Impl"
	// InternalDsl.g:2319:1: rule__Constructs__Group__6__Impl : ( ';' ) ;
	public final void rule__Constructs__Group__6__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2323:2: ( ( ';' ) )
			// InternalDsl.g:2324:2: ( ';' )
			{
			// InternalDsl.g:2324:2: ( ';' )
			// InternalDsl.g:2325:2: ';'
			{
			 before(grammarAccess.getConstructsAccess().getSemicolonKeyword_6()); 
			match(input,15,FOLLOW_2); 
			 after(grammarAccess.getConstructsAccess().getSemicolonKeyword_6()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Constructs__Group__6__Impl"



	// $ANTLR start "rule__Constructs__Group_4__0"
	// InternalDsl.g:2335:1: rule__Constructs__Group_4__0 : rule__Constructs__Group_4__0__Impl rule__Constructs__Group_4__1 ;
	public final void rule__Constructs__Group_4__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2339:2: ( rule__Constructs__Group_4__0__Impl rule__Constructs__Group_4__1 )
			// InternalDsl.g:2340:2: rule__Constructs__Group_4__0__Impl rule__Constructs__Group_4__1
			{
			pushFollow(FOLLOW_4);
			rule__Constructs__Group_4__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Constructs__Group_4__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Constructs__Group_4__0"



	// $ANTLR start "rule__Constructs__Group_4__0__Impl"
	// InternalDsl.g:2347:1: rule__Constructs__Group_4__0__Impl : ( ',' ) ;
	public final void rule__Constructs__Group_4__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2351:2: ( ( ',' ) )
			// InternalDsl.g:2352:2: ( ',' )
			{
			// InternalDsl.g:2352:2: ( ',' )
			// InternalDsl.g:2353:2: ','
			{
			 before(grammarAccess.getConstructsAccess().getCommaKeyword_4_0()); 
			match(input,11,FOLLOW_2); 
			 after(grammarAccess.getConstructsAccess().getCommaKeyword_4_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Constructs__Group_4__0__Impl"



	// $ANTLR start "rule__Constructs__Group_4__1"
	// InternalDsl.g:2362:1: rule__Constructs__Group_4__1 : rule__Constructs__Group_4__1__Impl ;
	public final void rule__Constructs__Group_4__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2366:2: ( rule__Constructs__Group_4__1__Impl )
			// InternalDsl.g:2367:2: rule__Constructs__Group_4__1__Impl
			{
			pushFollow(FOLLOW_2);
			rule__Constructs__Group_4__1__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Constructs__Group_4__1"



	// $ANTLR start "rule__Constructs__Group_4__1__Impl"
	// InternalDsl.g:2373:1: rule__Constructs__Group_4__1__Impl : ( ( rule__Constructs__ElementsAssignment_4_1 ) ) ;
	public final void rule__Constructs__Group_4__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2377:2: ( ( ( rule__Constructs__ElementsAssignment_4_1 ) ) )
			// InternalDsl.g:2378:2: ( ( rule__Constructs__ElementsAssignment_4_1 ) )
			{
			// InternalDsl.g:2378:2: ( ( rule__Constructs__ElementsAssignment_4_1 ) )
			// InternalDsl.g:2379:2: ( rule__Constructs__ElementsAssignment_4_1 )
			{
			 before(grammarAccess.getConstructsAccess().getElementsAssignment_4_1()); 
			// InternalDsl.g:2380:2: ( rule__Constructs__ElementsAssignment_4_1 )
			// InternalDsl.g:2380:3: rule__Constructs__ElementsAssignment_4_1
			{
			pushFollow(FOLLOW_2);
			rule__Constructs__ElementsAssignment_4_1();
			state._fsp--;

			}

			 after(grammarAccess.getConstructsAccess().getElementsAssignment_4_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Constructs__Group_4__1__Impl"



	// $ANTLR start "rule__File__Group__0"
	// InternalDsl.g:2389:1: rule__File__Group__0 : rule__File__Group__0__Impl rule__File__Group__1 ;
	public final void rule__File__Group__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2393:2: ( rule__File__Group__0__Impl rule__File__Group__1 )
			// InternalDsl.g:2394:2: rule__File__Group__0__Impl rule__File__Group__1
			{
			pushFollow(FOLLOW_27);
			rule__File__Group__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__File__Group__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__File__Group__0"



	// $ANTLR start "rule__File__Group__0__Impl"
	// InternalDsl.g:2401:1: rule__File__Group__0__Impl : ( RULE_ID ) ;
	public final void rule__File__Group__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2405:2: ( ( RULE_ID ) )
			// InternalDsl.g:2406:2: ( RULE_ID )
			{
			// InternalDsl.g:2406:2: ( RULE_ID )
			// InternalDsl.g:2407:2: RULE_ID
			{
			 before(grammarAccess.getFileAccess().getIDTerminalRuleCall_0()); 
			match(input,RULE_ID,FOLLOW_2); 
			 after(grammarAccess.getFileAccess().getIDTerminalRuleCall_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__File__Group__0__Impl"



	// $ANTLR start "rule__File__Group__1"
	// InternalDsl.g:2416:1: rule__File__Group__1 : rule__File__Group__1__Impl rule__File__Group__2 ;
	public final void rule__File__Group__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2420:2: ( rule__File__Group__1__Impl rule__File__Group__2 )
			// InternalDsl.g:2421:2: rule__File__Group__1__Impl rule__File__Group__2
			{
			pushFollow(FOLLOW_4);
			rule__File__Group__1__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__File__Group__2();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__File__Group__1"



	// $ANTLR start "rule__File__Group__1__Impl"
	// InternalDsl.g:2428:1: rule__File__Group__1__Impl : ( '.' ) ;
	public final void rule__File__Group__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2432:2: ( ( '.' ) )
			// InternalDsl.g:2433:2: ( '.' )
			{
			// InternalDsl.g:2433:2: ( '.' )
			// InternalDsl.g:2434:2: '.'
			{
			 before(grammarAccess.getFileAccess().getFullStopKeyword_1()); 
			match(input,12,FOLLOW_2); 
			 after(grammarAccess.getFileAccess().getFullStopKeyword_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__File__Group__1__Impl"



	// $ANTLR start "rule__File__Group__2"
	// InternalDsl.g:2443:1: rule__File__Group__2 : rule__File__Group__2__Impl ;
	public final void rule__File__Group__2() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2447:2: ( rule__File__Group__2__Impl )
			// InternalDsl.g:2448:2: rule__File__Group__2__Impl
			{
			pushFollow(FOLLOW_2);
			rule__File__Group__2__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__File__Group__2"



	// $ANTLR start "rule__File__Group__2__Impl"
	// InternalDsl.g:2454:1: rule__File__Group__2__Impl : ( RULE_ID ) ;
	public final void rule__File__Group__2__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2458:2: ( ( RULE_ID ) )
			// InternalDsl.g:2459:2: ( RULE_ID )
			{
			// InternalDsl.g:2459:2: ( RULE_ID )
			// InternalDsl.g:2460:2: RULE_ID
			{
			 before(grammarAccess.getFileAccess().getIDTerminalRuleCall_2()); 
			match(input,RULE_ID,FOLLOW_2); 
			 after(grammarAccess.getFileAccess().getIDTerminalRuleCall_2()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__File__Group__2__Impl"



	// $ANTLR start "rule__Import__Group__0"
	// InternalDsl.g:2470:1: rule__Import__Group__0 : rule__Import__Group__0__Impl rule__Import__Group__1 ;
	public final void rule__Import__Group__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2474:2: ( rule__Import__Group__0__Impl rule__Import__Group__1 )
			// InternalDsl.g:2475:2: rule__Import__Group__0__Impl rule__Import__Group__1
			{
			pushFollow(FOLLOW_4);
			rule__Import__Group__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__Import__Group__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Import__Group__0"



	// $ANTLR start "rule__Import__Group__0__Impl"
	// InternalDsl.g:2482:1: rule__Import__Group__0__Impl : ( 'Import' ) ;
	public final void rule__Import__Group__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2486:2: ( ( 'Import' ) )
			// InternalDsl.g:2487:2: ( 'Import' )
			{
			// InternalDsl.g:2487:2: ( 'Import' )
			// InternalDsl.g:2488:2: 'Import'
			{
			 before(grammarAccess.getImportAccess().getImportKeyword_0()); 
			match(input,21,FOLLOW_2); 
			 after(grammarAccess.getImportAccess().getImportKeyword_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Import__Group__0__Impl"



	// $ANTLR start "rule__Import__Group__1"
	// InternalDsl.g:2497:1: rule__Import__Group__1 : rule__Import__Group__1__Impl ;
	public final void rule__Import__Group__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2501:2: ( rule__Import__Group__1__Impl )
			// InternalDsl.g:2502:2: rule__Import__Group__1__Impl
			{
			pushFollow(FOLLOW_2);
			rule__Import__Group__1__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Import__Group__1"



	// $ANTLR start "rule__Import__Group__1__Impl"
	// InternalDsl.g:2508:1: rule__Import__Group__1__Impl : ( ( rule__Import__ImportedNamespaceAssignment_1 ) ) ;
	public final void rule__Import__Group__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2512:2: ( ( ( rule__Import__ImportedNamespaceAssignment_1 ) ) )
			// InternalDsl.g:2513:2: ( ( rule__Import__ImportedNamespaceAssignment_1 ) )
			{
			// InternalDsl.g:2513:2: ( ( rule__Import__ImportedNamespaceAssignment_1 ) )
			// InternalDsl.g:2514:2: ( rule__Import__ImportedNamespaceAssignment_1 )
			{
			 before(grammarAccess.getImportAccess().getImportedNamespaceAssignment_1()); 
			// InternalDsl.g:2515:2: ( rule__Import__ImportedNamespaceAssignment_1 )
			// InternalDsl.g:2515:3: rule__Import__ImportedNamespaceAssignment_1
			{
			pushFollow(FOLLOW_2);
			rule__Import__ImportedNamespaceAssignment_1();
			state._fsp--;

			}

			 after(grammarAccess.getImportAccess().getImportedNamespaceAssignment_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Import__Group__1__Impl"



	// $ANTLR start "rule__QualifiedNameWithWildcard__Group__0"
	// InternalDsl.g:2524:1: rule__QualifiedNameWithWildcard__Group__0 : rule__QualifiedNameWithWildcard__Group__0__Impl rule__QualifiedNameWithWildcard__Group__1 ;
	public final void rule__QualifiedNameWithWildcard__Group__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2528:2: ( rule__QualifiedNameWithWildcard__Group__0__Impl rule__QualifiedNameWithWildcard__Group__1 )
			// InternalDsl.g:2529:2: rule__QualifiedNameWithWildcard__Group__0__Impl rule__QualifiedNameWithWildcard__Group__1
			{
			pushFollow(FOLLOW_28);
			rule__QualifiedNameWithWildcard__Group__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__QualifiedNameWithWildcard__Group__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__QualifiedNameWithWildcard__Group__0"



	// $ANTLR start "rule__QualifiedNameWithWildcard__Group__0__Impl"
	// InternalDsl.g:2536:1: rule__QualifiedNameWithWildcard__Group__0__Impl : ( ruleQualifiedName ) ;
	public final void rule__QualifiedNameWithWildcard__Group__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2540:2: ( ( ruleQualifiedName ) )
			// InternalDsl.g:2541:2: ( ruleQualifiedName )
			{
			// InternalDsl.g:2541:2: ( ruleQualifiedName )
			// InternalDsl.g:2542:2: ruleQualifiedName
			{
			 before(grammarAccess.getQualifiedNameWithWildcardAccess().getQualifiedNameParserRuleCall_0()); 
			pushFollow(FOLLOW_2);
			ruleQualifiedName();
			state._fsp--;

			 after(grammarAccess.getQualifiedNameWithWildcardAccess().getQualifiedNameParserRuleCall_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__QualifiedNameWithWildcard__Group__0__Impl"



	// $ANTLR start "rule__QualifiedNameWithWildcard__Group__1"
	// InternalDsl.g:2551:1: rule__QualifiedNameWithWildcard__Group__1 : rule__QualifiedNameWithWildcard__Group__1__Impl ;
	public final void rule__QualifiedNameWithWildcard__Group__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2555:2: ( rule__QualifiedNameWithWildcard__Group__1__Impl )
			// InternalDsl.g:2556:2: rule__QualifiedNameWithWildcard__Group__1__Impl
			{
			pushFollow(FOLLOW_2);
			rule__QualifiedNameWithWildcard__Group__1__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__QualifiedNameWithWildcard__Group__1"



	// $ANTLR start "rule__QualifiedNameWithWildcard__Group__1__Impl"
	// InternalDsl.g:2562:1: rule__QualifiedNameWithWildcard__Group__1__Impl : ( ( '.*' )? ) ;
	public final void rule__QualifiedNameWithWildcard__Group__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2566:2: ( ( ( '.*' )? ) )
			// InternalDsl.g:2567:2: ( ( '.*' )? )
			{
			// InternalDsl.g:2567:2: ( ( '.*' )? )
			// InternalDsl.g:2568:2: ( '.*' )?
			{
			 before(grammarAccess.getQualifiedNameWithWildcardAccess().getFullStopAsteriskKeyword_1()); 
			// InternalDsl.g:2569:2: ( '.*' )?
			int alt12=2;
			int LA12_0 = input.LA(1);
			if ( (LA12_0==13) ) {
				alt12=1;
			}
			switch (alt12) {
				case 1 :
					// InternalDsl.g:2569:3: '.*'
					{
					match(input,13,FOLLOW_2); 
					}
					break;

			}

			 after(grammarAccess.getQualifiedNameWithWildcardAccess().getFullStopAsteriskKeyword_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__QualifiedNameWithWildcard__Group__1__Impl"



	// $ANTLR start "rule__QualifiedName__Group__0"
	// InternalDsl.g:2578:1: rule__QualifiedName__Group__0 : rule__QualifiedName__Group__0__Impl rule__QualifiedName__Group__1 ;
	public final void rule__QualifiedName__Group__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2582:2: ( rule__QualifiedName__Group__0__Impl rule__QualifiedName__Group__1 )
			// InternalDsl.g:2583:2: rule__QualifiedName__Group__0__Impl rule__QualifiedName__Group__1
			{
			pushFollow(FOLLOW_27);
			rule__QualifiedName__Group__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__QualifiedName__Group__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__QualifiedName__Group__0"



	// $ANTLR start "rule__QualifiedName__Group__0__Impl"
	// InternalDsl.g:2590:1: rule__QualifiedName__Group__0__Impl : ( RULE_ID ) ;
	public final void rule__QualifiedName__Group__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2594:2: ( ( RULE_ID ) )
			// InternalDsl.g:2595:2: ( RULE_ID )
			{
			// InternalDsl.g:2595:2: ( RULE_ID )
			// InternalDsl.g:2596:2: RULE_ID
			{
			 before(grammarAccess.getQualifiedNameAccess().getIDTerminalRuleCall_0()); 
			match(input,RULE_ID,FOLLOW_2); 
			 after(grammarAccess.getQualifiedNameAccess().getIDTerminalRuleCall_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__QualifiedName__Group__0__Impl"



	// $ANTLR start "rule__QualifiedName__Group__1"
	// InternalDsl.g:2605:1: rule__QualifiedName__Group__1 : rule__QualifiedName__Group__1__Impl ;
	public final void rule__QualifiedName__Group__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2609:2: ( rule__QualifiedName__Group__1__Impl )
			// InternalDsl.g:2610:2: rule__QualifiedName__Group__1__Impl
			{
			pushFollow(FOLLOW_2);
			rule__QualifiedName__Group__1__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__QualifiedName__Group__1"



	// $ANTLR start "rule__QualifiedName__Group__1__Impl"
	// InternalDsl.g:2616:1: rule__QualifiedName__Group__1__Impl : ( ( rule__QualifiedName__Group_1__0 )* ) ;
	public final void rule__QualifiedName__Group__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2620:2: ( ( ( rule__QualifiedName__Group_1__0 )* ) )
			// InternalDsl.g:2621:2: ( ( rule__QualifiedName__Group_1__0 )* )
			{
			// InternalDsl.g:2621:2: ( ( rule__QualifiedName__Group_1__0 )* )
			// InternalDsl.g:2622:2: ( rule__QualifiedName__Group_1__0 )*
			{
			 before(grammarAccess.getQualifiedNameAccess().getGroup_1()); 
			// InternalDsl.g:2623:2: ( rule__QualifiedName__Group_1__0 )*
			loop13:
			while (true) {
				int alt13=2;
				int LA13_0 = input.LA(1);
				if ( (LA13_0==12) ) {
					alt13=1;
				}

				switch (alt13) {
				case 1 :
					// InternalDsl.g:2623:3: rule__QualifiedName__Group_1__0
					{
					pushFollow(FOLLOW_29);
					rule__QualifiedName__Group_1__0();
					state._fsp--;

					}
					break;

				default :
					break loop13;
				}
			}

			 after(grammarAccess.getQualifiedNameAccess().getGroup_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__QualifiedName__Group__1__Impl"



	// $ANTLR start "rule__QualifiedName__Group_1__0"
	// InternalDsl.g:2632:1: rule__QualifiedName__Group_1__0 : rule__QualifiedName__Group_1__0__Impl rule__QualifiedName__Group_1__1 ;
	public final void rule__QualifiedName__Group_1__0() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2636:2: ( rule__QualifiedName__Group_1__0__Impl rule__QualifiedName__Group_1__1 )
			// InternalDsl.g:2637:2: rule__QualifiedName__Group_1__0__Impl rule__QualifiedName__Group_1__1
			{
			pushFollow(FOLLOW_4);
			rule__QualifiedName__Group_1__0__Impl();
			state._fsp--;

			pushFollow(FOLLOW_2);
			rule__QualifiedName__Group_1__1();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__QualifiedName__Group_1__0"



	// $ANTLR start "rule__QualifiedName__Group_1__0__Impl"
	// InternalDsl.g:2644:1: rule__QualifiedName__Group_1__0__Impl : ( '.' ) ;
	public final void rule__QualifiedName__Group_1__0__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2648:2: ( ( '.' ) )
			// InternalDsl.g:2649:2: ( '.' )
			{
			// InternalDsl.g:2649:2: ( '.' )
			// InternalDsl.g:2650:2: '.'
			{
			 before(grammarAccess.getQualifiedNameAccess().getFullStopKeyword_1_0()); 
			match(input,12,FOLLOW_2); 
			 after(grammarAccess.getQualifiedNameAccess().getFullStopKeyword_1_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__QualifiedName__Group_1__0__Impl"



	// $ANTLR start "rule__QualifiedName__Group_1__1"
	// InternalDsl.g:2659:1: rule__QualifiedName__Group_1__1 : rule__QualifiedName__Group_1__1__Impl ;
	public final void rule__QualifiedName__Group_1__1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2663:2: ( rule__QualifiedName__Group_1__1__Impl )
			// InternalDsl.g:2664:2: rule__QualifiedName__Group_1__1__Impl
			{
			pushFollow(FOLLOW_2);
			rule__QualifiedName__Group_1__1__Impl();
			state._fsp--;

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__QualifiedName__Group_1__1"



	// $ANTLR start "rule__QualifiedName__Group_1__1__Impl"
	// InternalDsl.g:2670:1: rule__QualifiedName__Group_1__1__Impl : ( RULE_ID ) ;
	public final void rule__QualifiedName__Group_1__1__Impl() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2674:2: ( ( RULE_ID ) )
			// InternalDsl.g:2675:2: ( RULE_ID )
			{
			// InternalDsl.g:2675:2: ( RULE_ID )
			// InternalDsl.g:2676:2: RULE_ID
			{
			 before(grammarAccess.getQualifiedNameAccess().getIDTerminalRuleCall_1_1()); 
			match(input,RULE_ID,FOLLOW_2); 
			 after(grammarAccess.getQualifiedNameAccess().getIDTerminalRuleCall_1_1()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__QualifiedName__Group_1__1__Impl"



	// $ANTLR start "rule__Dsl__ElementsAssignment"
	// InternalDsl.g:2686:1: rule__Dsl__ElementsAssignment : ( ruleAbstractElement ) ;
	public final void rule__Dsl__ElementsAssignment() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2690:2: ( ( ruleAbstractElement ) )
			// InternalDsl.g:2691:2: ( ruleAbstractElement )
			{
			// InternalDsl.g:2691:2: ( ruleAbstractElement )
			// InternalDsl.g:2692:3: ruleAbstractElement
			{
			 before(grammarAccess.getDslAccess().getElementsAbstractElementParserRuleCall_0()); 
			pushFollow(FOLLOW_2);
			ruleAbstractElement();
			state._fsp--;

			 after(grammarAccess.getDslAccess().getElementsAbstractElementParserRuleCall_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Dsl__ElementsAssignment"



	// $ANTLR start "rule__Hypothesis__NameAssignment_1"
	// InternalDsl.g:2701:1: rule__Hypothesis__NameAssignment_1 : ( ruleQualifiedName ) ;
	public final void rule__Hypothesis__NameAssignment_1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2705:2: ( ( ruleQualifiedName ) )
			// InternalDsl.g:2706:2: ( ruleQualifiedName )
			{
			// InternalDsl.g:2706:2: ( ruleQualifiedName )
			// InternalDsl.g:2707:3: ruleQualifiedName
			{
			 before(grammarAccess.getHypothesisAccess().getNameQualifiedNameParserRuleCall_1_0()); 
			pushFollow(FOLLOW_2);
			ruleQualifiedName();
			state._fsp--;

			 after(grammarAccess.getHypothesisAccess().getNameQualifiedNameParserRuleCall_1_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Hypothesis__NameAssignment_1"



	// $ANTLR start "rule__Hypothesis__TypesAssignment_2_2"
	// InternalDsl.g:2716:1: rule__Hypothesis__TypesAssignment_2_2 : ( ruleParameter ) ;
	public final void rule__Hypothesis__TypesAssignment_2_2() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2720:2: ( ( ruleParameter ) )
			// InternalDsl.g:2721:2: ( ruleParameter )
			{
			// InternalDsl.g:2721:2: ( ruleParameter )
			// InternalDsl.g:2722:3: ruleParameter
			{
			 before(grammarAccess.getHypothesisAccess().getTypesParameterParserRuleCall_2_2_0()); 
			pushFollow(FOLLOW_2);
			ruleParameter();
			state._fsp--;

			 after(grammarAccess.getHypothesisAccess().getTypesParameterParserRuleCall_2_2_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Hypothesis__TypesAssignment_2_2"



	// $ANTLR start "rule__Hypothesis__TypesAssignment_2_3_1"
	// InternalDsl.g:2731:1: rule__Hypothesis__TypesAssignment_2_3_1 : ( ruleParameter ) ;
	public final void rule__Hypothesis__TypesAssignment_2_3_1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2735:2: ( ( ruleParameter ) )
			// InternalDsl.g:2736:2: ( ruleParameter )
			{
			// InternalDsl.g:2736:2: ( ruleParameter )
			// InternalDsl.g:2737:3: ruleParameter
			{
			 before(grammarAccess.getHypothesisAccess().getTypesParameterParserRuleCall_2_3_1_0()); 
			pushFollow(FOLLOW_2);
			ruleParameter();
			state._fsp--;

			 after(grammarAccess.getHypothesisAccess().getTypesParameterParserRuleCall_2_3_1_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Hypothesis__TypesAssignment_2_3_1"



	// $ANTLR start "rule__Hypothesis__DescriptionAssignment_4"
	// InternalDsl.g:2746:1: rule__Hypothesis__DescriptionAssignment_4 : ( ruleDescription ) ;
	public final void rule__Hypothesis__DescriptionAssignment_4() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2750:2: ( ( ruleDescription ) )
			// InternalDsl.g:2751:2: ( ruleDescription )
			{
			// InternalDsl.g:2751:2: ( ruleDescription )
			// InternalDsl.g:2752:3: ruleDescription
			{
			 before(grammarAccess.getHypothesisAccess().getDescriptionDescriptionParserRuleCall_4_0()); 
			pushFollow(FOLLOW_2);
			ruleDescription();
			state._fsp--;

			 after(grammarAccess.getHypothesisAccess().getDescriptionDescriptionParserRuleCall_4_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Hypothesis__DescriptionAssignment_4"



	// $ANTLR start "rule__Hypothesis__DefinitionAssignment_5"
	// InternalDsl.g:2761:1: rule__Hypothesis__DefinitionAssignment_5 : ( ruleDefinition ) ;
	public final void rule__Hypothesis__DefinitionAssignment_5() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2765:2: ( ( ruleDefinition ) )
			// InternalDsl.g:2766:2: ( ruleDefinition )
			{
			// InternalDsl.g:2766:2: ( ruleDefinition )
			// InternalDsl.g:2767:3: ruleDefinition
			{
			 before(grammarAccess.getHypothesisAccess().getDefinitionDefinitionParserRuleCall_5_0()); 
			pushFollow(FOLLOW_2);
			ruleDefinition();
			state._fsp--;

			 after(grammarAccess.getHypothesisAccess().getDefinitionDefinitionParserRuleCall_5_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Hypothesis__DefinitionAssignment_5"



	// $ANTLR start "rule__Hypothesis__ConstructsAssignment_6"
	// InternalDsl.g:2776:1: rule__Hypothesis__ConstructsAssignment_6 : ( ruleConstructs ) ;
	public final void rule__Hypothesis__ConstructsAssignment_6() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2780:2: ( ( ruleConstructs ) )
			// InternalDsl.g:2781:2: ( ruleConstructs )
			{
			// InternalDsl.g:2781:2: ( ruleConstructs )
			// InternalDsl.g:2782:3: ruleConstructs
			{
			 before(grammarAccess.getHypothesisAccess().getConstructsConstructsParserRuleCall_6_0()); 
			pushFollow(FOLLOW_2);
			ruleConstructs();
			state._fsp--;

			 after(grammarAccess.getHypothesisAccess().getConstructsConstructsParserRuleCall_6_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Hypothesis__ConstructsAssignment_6"



	// $ANTLR start "rule__Parameter__NameAssignment"
	// InternalDsl.g:2791:1: rule__Parameter__NameAssignment : ( ruleQualifiedName ) ;
	public final void rule__Parameter__NameAssignment() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2795:2: ( ( ruleQualifiedName ) )
			// InternalDsl.g:2796:2: ( ruleQualifiedName )
			{
			// InternalDsl.g:2796:2: ( ruleQualifiedName )
			// InternalDsl.g:2797:3: ruleQualifiedName
			{
			 before(grammarAccess.getParameterAccess().getNameQualifiedNameParserRuleCall_0()); 
			pushFollow(FOLLOW_2);
			ruleQualifiedName();
			state._fsp--;

			 after(grammarAccess.getParameterAccess().getNameQualifiedNameParserRuleCall_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Parameter__NameAssignment"



	// $ANTLR start "rule__Databases__ValuesAssignment_2"
	// InternalDsl.g:2806:1: rule__Databases__ValuesAssignment_2 : ( ruleFile ) ;
	public final void rule__Databases__ValuesAssignment_2() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2810:2: ( ( ruleFile ) )
			// InternalDsl.g:2811:2: ( ruleFile )
			{
			// InternalDsl.g:2811:2: ( ruleFile )
			// InternalDsl.g:2812:3: ruleFile
			{
			 before(grammarAccess.getDatabasesAccess().getValuesFileParserRuleCall_2_0()); 
			pushFollow(FOLLOW_2);
			ruleFile();
			state._fsp--;

			 after(grammarAccess.getDatabasesAccess().getValuesFileParserRuleCall_2_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Databases__ValuesAssignment_2"



	// $ANTLR start "rule__System__NameAssignment_2"
	// InternalDsl.g:2821:1: rule__System__NameAssignment_2 : ( RULE_ID ) ;
	public final void rule__System__NameAssignment_2() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2825:2: ( ( RULE_ID ) )
			// InternalDsl.g:2826:2: ( RULE_ID )
			{
			// InternalDsl.g:2826:2: ( RULE_ID )
			// InternalDsl.g:2827:3: RULE_ID
			{
			 before(grammarAccess.getSystemAccess().getNameIDTerminalRuleCall_2_0()); 
			match(input,RULE_ID,FOLLOW_2); 
			 after(grammarAccess.getSystemAccess().getNameIDTerminalRuleCall_2_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__System__NameAssignment_2"



	// $ANTLR start "rule__Description__NameAssignment_2"
	// InternalDsl.g:2836:1: rule__Description__NameAssignment_2 : ( RULE_STRING ) ;
	public final void rule__Description__NameAssignment_2() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2840:2: ( ( RULE_STRING ) )
			// InternalDsl.g:2841:2: ( RULE_STRING )
			{
			// InternalDsl.g:2841:2: ( RULE_STRING )
			// InternalDsl.g:2842:3: RULE_STRING
			{
			 before(grammarAccess.getDescriptionAccess().getNameSTRINGTerminalRuleCall_2_0()); 
			match(input,RULE_STRING,FOLLOW_2); 
			 after(grammarAccess.getDescriptionAccess().getNameSTRINGTerminalRuleCall_2_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Description__NameAssignment_2"



	// $ANTLR start "rule__Definition__ParameterAAssignment_3"
	// InternalDsl.g:2851:1: rule__Definition__ParameterAAssignment_3 : ( ( ruleQualifiedName ) ) ;
	public final void rule__Definition__ParameterAAssignment_3() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2855:2: ( ( ( ruleQualifiedName ) ) )
			// InternalDsl.g:2856:2: ( ( ruleQualifiedName ) )
			{
			// InternalDsl.g:2856:2: ( ( ruleQualifiedName ) )
			// InternalDsl.g:2857:3: ( ruleQualifiedName )
			{
			 before(grammarAccess.getDefinitionAccess().getParameterAParameterCrossReference_3_0()); 
			// InternalDsl.g:2858:3: ( ruleQualifiedName )
			// InternalDsl.g:2859:4: ruleQualifiedName
			{
			 before(grammarAccess.getDefinitionAccess().getParameterAParameterQualifiedNameParserRuleCall_3_0_1()); 
			pushFollow(FOLLOW_2);
			ruleQualifiedName();
			state._fsp--;

			 after(grammarAccess.getDefinitionAccess().getParameterAParameterQualifiedNameParserRuleCall_3_0_1()); 
			}

			 after(grammarAccess.getDefinitionAccess().getParameterAParameterCrossReference_3_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__ParameterAAssignment_3"



	// $ANTLR start "rule__Definition__OptionsAAssignment_5"
	// InternalDsl.g:2870:1: rule__Definition__OptionsAAssignment_5 : ( ruleOption ) ;
	public final void rule__Definition__OptionsAAssignment_5() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2874:2: ( ( ruleOption ) )
			// InternalDsl.g:2875:2: ( ruleOption )
			{
			// InternalDsl.g:2875:2: ( ruleOption )
			// InternalDsl.g:2876:3: ruleOption
			{
			 before(grammarAccess.getDefinitionAccess().getOptionsAOptionParserRuleCall_5_0()); 
			pushFollow(FOLLOW_2);
			ruleOption();
			state._fsp--;

			 after(grammarAccess.getDefinitionAccess().getOptionsAOptionParserRuleCall_5_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__OptionsAAssignment_5"



	// $ANTLR start "rule__Definition__ParameterAAssignment_6_1"
	// InternalDsl.g:2885:1: rule__Definition__ParameterAAssignment_6_1 : ( ( ruleQualifiedName ) ) ;
	public final void rule__Definition__ParameterAAssignment_6_1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2889:2: ( ( ( ruleQualifiedName ) ) )
			// InternalDsl.g:2890:2: ( ( ruleQualifiedName ) )
			{
			// InternalDsl.g:2890:2: ( ( ruleQualifiedName ) )
			// InternalDsl.g:2891:3: ( ruleQualifiedName )
			{
			 before(grammarAccess.getDefinitionAccess().getParameterAParameterCrossReference_6_1_0()); 
			// InternalDsl.g:2892:3: ( ruleQualifiedName )
			// InternalDsl.g:2893:4: ruleQualifiedName
			{
			 before(grammarAccess.getDefinitionAccess().getParameterAParameterQualifiedNameParserRuleCall_6_1_0_1()); 
			pushFollow(FOLLOW_2);
			ruleQualifiedName();
			state._fsp--;

			 after(grammarAccess.getDefinitionAccess().getParameterAParameterQualifiedNameParserRuleCall_6_1_0_1()); 
			}

			 after(grammarAccess.getDefinitionAccess().getParameterAParameterCrossReference_6_1_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__ParameterAAssignment_6_1"



	// $ANTLR start "rule__Definition__OptionsAAssignment_6_3"
	// InternalDsl.g:2904:1: rule__Definition__OptionsAAssignment_6_3 : ( ruleOption ) ;
	public final void rule__Definition__OptionsAAssignment_6_3() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2908:2: ( ( ruleOption ) )
			// InternalDsl.g:2909:2: ( ruleOption )
			{
			// InternalDsl.g:2909:2: ( ruleOption )
			// InternalDsl.g:2910:3: ruleOption
			{
			 before(grammarAccess.getDefinitionAccess().getOptionsAOptionParserRuleCall_6_3_0()); 
			pushFollow(FOLLOW_2);
			ruleOption();
			state._fsp--;

			 after(grammarAccess.getDefinitionAccess().getOptionsAOptionParserRuleCall_6_3_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__OptionsAAssignment_6_3"



	// $ANTLR start "rule__Definition__PropertyAssignment_9"
	// InternalDsl.g:2919:1: rule__Definition__PropertyAssignment_9 : ( ruleProperty ) ;
	public final void rule__Definition__PropertyAssignment_9() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2923:2: ( ( ruleProperty ) )
			// InternalDsl.g:2924:2: ( ruleProperty )
			{
			// InternalDsl.g:2924:2: ( ruleProperty )
			// InternalDsl.g:2925:3: ruleProperty
			{
			 before(grammarAccess.getDefinitionAccess().getPropertyPropertyParserRuleCall_9_0()); 
			pushFollow(FOLLOW_2);
			ruleProperty();
			state._fsp--;

			 after(grammarAccess.getDefinitionAccess().getPropertyPropertyParserRuleCall_9_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__PropertyAssignment_9"



	// $ANTLR start "rule__Definition__ParameterBAssignment_10_2"
	// InternalDsl.g:2934:1: rule__Definition__ParameterBAssignment_10_2 : ( ( ruleQualifiedName ) ) ;
	public final void rule__Definition__ParameterBAssignment_10_2() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2938:2: ( ( ( ruleQualifiedName ) ) )
			// InternalDsl.g:2939:2: ( ( ruleQualifiedName ) )
			{
			// InternalDsl.g:2939:2: ( ( ruleQualifiedName ) )
			// InternalDsl.g:2940:3: ( ruleQualifiedName )
			{
			 before(grammarAccess.getDefinitionAccess().getParameterBParameterCrossReference_10_2_0()); 
			// InternalDsl.g:2941:3: ( ruleQualifiedName )
			// InternalDsl.g:2942:4: ruleQualifiedName
			{
			 before(grammarAccess.getDefinitionAccess().getParameterBParameterQualifiedNameParserRuleCall_10_2_0_1()); 
			pushFollow(FOLLOW_2);
			ruleQualifiedName();
			state._fsp--;

			 after(grammarAccess.getDefinitionAccess().getParameterBParameterQualifiedNameParserRuleCall_10_2_0_1()); 
			}

			 after(grammarAccess.getDefinitionAccess().getParameterBParameterCrossReference_10_2_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__ParameterBAssignment_10_2"



	// $ANTLR start "rule__Definition__OptionsBAssignment_10_4"
	// InternalDsl.g:2953:1: rule__Definition__OptionsBAssignment_10_4 : ( ruleOption ) ;
	public final void rule__Definition__OptionsBAssignment_10_4() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2957:2: ( ( ruleOption ) )
			// InternalDsl.g:2958:2: ( ruleOption )
			{
			// InternalDsl.g:2958:2: ( ruleOption )
			// InternalDsl.g:2959:3: ruleOption
			{
			 before(grammarAccess.getDefinitionAccess().getOptionsBOptionParserRuleCall_10_4_0()); 
			pushFollow(FOLLOW_2);
			ruleOption();
			state._fsp--;

			 after(grammarAccess.getDefinitionAccess().getOptionsBOptionParserRuleCall_10_4_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__OptionsBAssignment_10_4"



	// $ANTLR start "rule__Definition__ParameterBAssignment_10_5_1"
	// InternalDsl.g:2968:1: rule__Definition__ParameterBAssignment_10_5_1 : ( ( ruleQualifiedName ) ) ;
	public final void rule__Definition__ParameterBAssignment_10_5_1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2972:2: ( ( ( ruleQualifiedName ) ) )
			// InternalDsl.g:2973:2: ( ( ruleQualifiedName ) )
			{
			// InternalDsl.g:2973:2: ( ( ruleQualifiedName ) )
			// InternalDsl.g:2974:3: ( ruleQualifiedName )
			{
			 before(grammarAccess.getDefinitionAccess().getParameterBParameterCrossReference_10_5_1_0()); 
			// InternalDsl.g:2975:3: ( ruleQualifiedName )
			// InternalDsl.g:2976:4: ruleQualifiedName
			{
			 before(grammarAccess.getDefinitionAccess().getParameterBParameterQualifiedNameParserRuleCall_10_5_1_0_1()); 
			pushFollow(FOLLOW_2);
			ruleQualifiedName();
			state._fsp--;

			 after(grammarAccess.getDefinitionAccess().getParameterBParameterQualifiedNameParserRuleCall_10_5_1_0_1()); 
			}

			 after(grammarAccess.getDefinitionAccess().getParameterBParameterCrossReference_10_5_1_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__ParameterBAssignment_10_5_1"



	// $ANTLR start "rule__Definition__OptionsBAssignment_10_5_3"
	// InternalDsl.g:2987:1: rule__Definition__OptionsBAssignment_10_5_3 : ( ruleOption ) ;
	public final void rule__Definition__OptionsBAssignment_10_5_3() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:2991:2: ( ( ruleOption ) )
			// InternalDsl.g:2992:2: ( ruleOption )
			{
			// InternalDsl.g:2992:2: ( ruleOption )
			// InternalDsl.g:2993:3: ruleOption
			{
			 before(grammarAccess.getDefinitionAccess().getOptionsBOptionParserRuleCall_10_5_3_0()); 
			pushFollow(FOLLOW_2);
			ruleOption();
			state._fsp--;

			 after(grammarAccess.getDefinitionAccess().getOptionsBOptionParserRuleCall_10_5_3_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Definition__OptionsBAssignment_10_5_3"



	// $ANTLR start "rule__Constructs__ElementsAssignment_3"
	// InternalDsl.g:3002:1: rule__Constructs__ElementsAssignment_3 : ( ruleQualifiedName ) ;
	public final void rule__Constructs__ElementsAssignment_3() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3006:2: ( ( ruleQualifiedName ) )
			// InternalDsl.g:3007:2: ( ruleQualifiedName )
			{
			// InternalDsl.g:3007:2: ( ruleQualifiedName )
			// InternalDsl.g:3008:3: ruleQualifiedName
			{
			 before(grammarAccess.getConstructsAccess().getElementsQualifiedNameParserRuleCall_3_0()); 
			pushFollow(FOLLOW_2);
			ruleQualifiedName();
			state._fsp--;

			 after(grammarAccess.getConstructsAccess().getElementsQualifiedNameParserRuleCall_3_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Constructs__ElementsAssignment_3"



	// $ANTLR start "rule__Constructs__ElementsAssignment_4_1"
	// InternalDsl.g:3017:1: rule__Constructs__ElementsAssignment_4_1 : ( ruleQualifiedName ) ;
	public final void rule__Constructs__ElementsAssignment_4_1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3021:2: ( ( ruleQualifiedName ) )
			// InternalDsl.g:3022:2: ( ruleQualifiedName )
			{
			// InternalDsl.g:3022:2: ( ruleQualifiedName )
			// InternalDsl.g:3023:3: ruleQualifiedName
			{
			 before(grammarAccess.getConstructsAccess().getElementsQualifiedNameParserRuleCall_4_1_0()); 
			pushFollow(FOLLOW_2);
			ruleQualifiedName();
			state._fsp--;

			 after(grammarAccess.getConstructsAccess().getElementsQualifiedNameParserRuleCall_4_1_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Constructs__ElementsAssignment_4_1"



	// $ANTLR start "rule__Option__NameAssignment"
	// InternalDsl.g:3032:1: rule__Option__NameAssignment : ( RULE_ID ) ;
	public final void rule__Option__NameAssignment() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3036:2: ( ( RULE_ID ) )
			// InternalDsl.g:3037:2: ( RULE_ID )
			{
			// InternalDsl.g:3037:2: ( RULE_ID )
			// InternalDsl.g:3038:3: RULE_ID
			{
			 before(grammarAccess.getOptionAccess().getNameIDTerminalRuleCall_0()); 
			match(input,RULE_ID,FOLLOW_2); 
			 after(grammarAccess.getOptionAccess().getNameIDTerminalRuleCall_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Option__NameAssignment"



	// $ANTLR start "rule__Import__ImportedNamespaceAssignment_1"
	// InternalDsl.g:3047:1: rule__Import__ImportedNamespaceAssignment_1 : ( ruleQualifiedNameWithWildcard ) ;
	public final void rule__Import__ImportedNamespaceAssignment_1() throws RecognitionException {

				int stackSize = keepStackSize();
			
		try {
			// InternalDsl.g:3051:2: ( ( ruleQualifiedNameWithWildcard ) )
			// InternalDsl.g:3052:2: ( ruleQualifiedNameWithWildcard )
			{
			// InternalDsl.g:3052:2: ( ruleQualifiedNameWithWildcard )
			// InternalDsl.g:3053:3: ruleQualifiedNameWithWildcard
			{
			 before(grammarAccess.getImportAccess().getImportedNamespaceQualifiedNameWithWildcardParserRuleCall_1_0()); 
			pushFollow(FOLLOW_2);
			ruleQualifiedNameWithWildcard();
			state._fsp--;

			 after(grammarAccess.getImportAccess().getImportedNamespaceQualifiedNameWithWildcardParserRuleCall_1_0()); 
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving

				restoreStackSize(stackSize);

		}
	}
	// $ANTLR end "rule__Import__ImportedNamespaceAssignment_1"

	// Delegated rules



	public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
	public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x0000000000B20022L});
	public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000000020L});
	public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000080000000L});
	public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000200000000L});
	public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000080000L});
	public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000040000L});
	public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000010000L});
	public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000400000000L});
	public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000400000L});
	public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000000802L});
	public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000000000200L});
	public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000000008000L});
	public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000000000022L});
	public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000400000800L});
	public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000010000000L});
	public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000060000000L});
	public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000100008000L});
	public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000008000000L});
	public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000004000000L});
	public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000001000000L});
	public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x0000000002000800L});
	public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000000002000000L});
	public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000000000001000L});
	public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000000000002000L});
	public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000000000001002L});
}
