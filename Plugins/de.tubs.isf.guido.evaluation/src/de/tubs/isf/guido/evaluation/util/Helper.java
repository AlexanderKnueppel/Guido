package de.tubs.isf.guido.evaluation.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import de.tubs.isf.guido.core.costs.Option;
import de.tubs.isf.guido.core.costs.Parameter;
import de.tubs.isf.guido.core.verifier.OptionableContainer;
import de.tubs.isf.guido.core.verifier.SettingsObject;

public class Helper {
	
	public static List<Parameter> convertTo(SettingsObject settings) {
		List<Parameter> result = new ArrayList<Parameter>();

		for (OptionableContainer oc : settings.getAllPossibleSettings()) {
			List<Option> options = Arrays.asList(oc.getOptions()).stream().map(o -> new Option(o.getValue()))
					.collect(Collectors.toList());
			result.add(new Parameter(oc.getValue(), options.toArray(new Option[0])));
		}

		return result;
	}
}
