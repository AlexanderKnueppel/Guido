package de.tubs.isf.guido.core.sampling;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import de.ovgu.featureide.fm.core.analysis.cnf.CNF;
import de.ovgu.featureide.fm.core.analysis.cnf.LiteralSet;
import de.ovgu.featureide.fm.core.analysis.cnf.formula.FeatureModelFormula;
import de.ovgu.featureide.fm.core.base.IFeature;
import de.ovgu.featureide.fm.core.base.IFeatureModel;
import de.ovgu.featureide.fm.core.base.impl.DefaultFeatureModelFactory;
import de.ovgu.featureide.fm.core.base.impl.FMFactoryManager;
import de.ovgu.featureide.fm.core.base.impl.FMFormatManager;
import de.ovgu.featureide.fm.core.configuration.Configuration;
import de.ovgu.featureide.fm.core.configuration.Selection;
import de.ovgu.featureide.fm.core.io.manager.FeatureModelManager;
import de.ovgu.featureide.fm.core.io.manager.FileHandler;
import de.ovgu.featureide.fm.core.io.xml.XmlFeatureModelFormat;

public class FeatureIDEUtil {
	
	public static IFeatureModel getFeatueModel(Path fmPath) {
		XmlFeatureModelFormat format = new XmlFeatureModelFormat();
		FMFormatManager.getInstance().addExtension(format);

		FMFactoryManager.getInstance().addExtension(DefaultFeatureModelFactory.getInstance());
		FMFactoryManager.getInstance().setWorkspaceLoader(null);
		FMFactoryManager.getInstance().addFactoryWorkspace(fmPath);

		FileHandler<IFeatureModel> fmHandler = FeatureModelManager.getFileHandler(fmPath);
		IFeatureModel model = fmHandler.getObject();

		if (model == null) {
			throw new NullPointerException("Feature Model is null");
		}
		
		return model;
	}
	
	public static Configuration generateConfiguration(IFeatureModel fm, CNF cnf, LiteralSet solution) {
		Configuration configuration = new Configuration(new FeatureModelFormula(fm));
		for (final int selection : solution.getLiterals()) {
			final String name = cnf.getVariables().getName(selection);
			configuration.setManual(name, selection > 0 ? Selection.SELECTED : Selection.UNSELECTED);
		}
		return configuration;
	}
	
	public static Set<String> ConfigurationToString(Configuration c) {
		Set<String> set = new HashSet<>();
		for (IFeature sf : c.getSelectedFeatures()) {
			set.add(sf.getName());
		}
		return set;
	}
	
	
}
