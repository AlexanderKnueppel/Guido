package de.tu.bs.masterthesis.key.simultator.options;

import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Field;
import java.util.Map;

import org.junit.Test;

import de.tu.bs.guido.key.simulator.control.AbstractKeyControl;
import de.tu.bs.masterthesis.key.simulator.options.strategy.ArithmeticTreatmentOptions;
import de.tu.bs.masterthesis.key.simulator.options.strategy.AutoInductionOptions;
import de.tu.bs.masterthesis.key.simulator.options.strategy.BlockTreatmentOptions;
import de.tu.bs.masterthesis.key.simulator.options.strategy.ClassAxiomRulesOptions;
import de.tu.bs.masterthesis.key.simulator.options.strategy.DependencyContractsOptions;
import de.tu.bs.masterthesis.key.simulator.options.strategy.ExpandLocalQueriesOptions;
import de.tu.bs.masterthesis.key.simulator.options.strategy.LoopTreatmentOptions;
import de.tu.bs.masterthesis.key.simulator.options.strategy.MethodTreatmentOptions;
import de.tu.bs.masterthesis.key.simulator.options.strategy.OneStepSimplificationOptions;
import de.tu.bs.masterthesis.key.simulator.options.strategy.ProofSplittingOptions;
import de.tu.bs.masterthesis.key.simulator.options.strategy.QuantifierTreatmentOptions;
import de.tu.bs.masterthesis.key.simulator.options.strategy.QueryTreatmentOptions;
import de.tu.bs.masterthesis.key.simulator.options.strategy.StopAtOptions;
import de.tu.bs.masterthesis.key.simulator.options.strategy.StrategyOptionable;

public class OptionTest {

	@SuppressWarnings("unchecked")
	@Test
	public void testOptionAvaiability() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		StrategyOptionable[][] options = new StrategyOptionable[][]{ArithmeticTreatmentOptions.values(),
				AutoInductionOptions.values(),BlockTreatmentOptions.values(),ClassAxiomRulesOptions.values(),
				DependencyContractsOptions.values(),ExpandLocalQueriesOptions.values(),LoopTreatmentOptions.values(),
				MethodTreatmentOptions.values(),ProofSplittingOptions.values(),QuantifierTreatmentOptions.values(),
				QueryTreatmentOptions.values(),StopAtOptions.values(),OneStepSimplificationOptions.values()
		};

		Field propertiesField = AbstractKeyControl.class.getDeclaredField("PROPERTIES");
		Field valuesField = AbstractKeyControl.class.getDeclaredField("VALUES");
		propertiesField.setAccessible(true);
		valuesField.setAccessible(true);
		Map<String, String> properties = (Map<String, String>) propertiesField.get(null);
		Map<String, Map<String, String>> values = (Map<String, Map<String, String>>) valuesField.get(null);
		
		for (StrategyOptionable[] optionables : options) {
			for (StrategyOptionable optionable : optionables) {
				String type = optionable.getType();
				String value = optionable.getValue();
				String prop = properties.get(type);
				assertNotNull("Type was null for "+prop, prop);
				String propValue = values.get(type).get(value);
				assertNotNull("Value was null for "+value+" of "+type, propValue);
			}
		}
	}
	
//	Test not working without loading proof... no time left right now
//	@Test
//	public void testTacletAvaiability(){
//		MainWindow main = MainWindow.getInstance();
//		main.setVisible(false);
//		ChoiceSettings csDefault = ProofSettings.DEFAULT_SETTINGS.getChoiceSettings();
//		Map<String,Set<String>> choicesMap = csDefault.getChoices();
//		List<TacletOptionable> options = new ArrayList<>();
//		for (KeyTacletOptions option : KeyTacletOptions.values()) {
//			options.addAll(Arrays.asList(option.getOptions()));
//		}
//		options.forEach(option->{
//			assertNotNull("Type was not found: "+option.getType(),choicesMap.get(option.getType()));
//			assertNotNull("Option was not found: "+option.getValue(),choicesMap.get(option.getType()).contains(option.getValue()));
//		});
//		
//	}

}
