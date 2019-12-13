package de.tubs.isf.guido.verification.systems.key;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.tubs.isf.guido.core.analysis.JMLContractAnalyzer;
import de.tubs.isf.guido.core.analysis.JavaSourceCodeAnalyzer;
import de.tubs.isf.guido.core.databasis.IDataBasisElement;
import de.tubs.isf.guido.core.verifier.SettingsObject;
import de.uka.ilkd.key.core.KeYMediator;
import de.uka.ilkd.key.gui.MainWindow;
import de.uka.ilkd.key.java.JavaInfo;
import de.uka.ilkd.key.java.abstraction.KeYJavaType;
import de.uka.ilkd.key.proof.Proof;
import de.uka.ilkd.key.proof.init.InitConfig;
import de.uka.ilkd.key.proof.init.ProofInputException;
import de.uka.ilkd.key.proof.init.ProofOblInput;
import de.uka.ilkd.key.proof.io.AbstractProblemLoader;
import de.uka.ilkd.key.proof.io.ProblemLoaderException;
import de.uka.ilkd.key.proof.mgt.SpecificationRepository;
import de.uka.ilkd.key.speclang.Contract;
import de.uka.ilkd.key.ui.AbstractMediatorUserInterfaceControl;

public class GuiBasedKeyControl extends AbstractKeyControl {
	
	JavaSourceCodeAnalyzer jsca = null;

	/**
	 * Stellt die Schnittstelle
	 * 
	 * @param source
	 *            Die zu beweisende Quellcodeeinheit (ein Ordner)
	 * @param classPath
	 *            Ein separater ClassPath (wuerde JavaRedux ueberschreiben).
	 * @param className
	 *            Der Name der Klasse, in der eine Methode bewiesen werden soll.
	 * @param methodName
	 *            Der Name der zu beweisenden Methode.
	 * @param so
	 *            Enthaelt die Optionen fuer den Beweis.
	 * @return Gibt fuer jeden Vertrag an dieser Methode ein Result-Objekt
	 *         zurueck.
	 */
	public List<IDataBasisElement> getResultForProof(File source, File classPath,
			String className, String methodName, String[] parameters, int contractNumber,
			SettingsObject so) {
		try {
			return executeProofsForMethod(source, classPath, className, methodName, parameters,
					contractNumber, (KeySettingsObject) so);
		} catch (ProofInputException | ProblemLoaderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private void waitForKeyGui() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Wendet die Settings auf den aktuell ausgewaehlten Befehl an.
	 * 
	 * @param m
	 * @param so
	 */
	private void applySettings(KeYMediator m, KeySettingsObject so) {
		waitForKeyGui();
		Proof p = m.getSelectedProof();
		applySettings(p, so);
		m.setMaxAutomaticSteps((int) so.getMaxEffort());
	}

	private void applyTaclets(KeYMediator m, SettingsObject so) {
		waitForKeyGui();
		Proof p = m.getSelectedProof();
		applyTaclets(p, so);
	}

	private void loadProofAndApplySettings(MainWindow main, File source,
			File classPath, String className, String methodName, String[] parameters, 
			KeySettingsObject so) throws ProofInputException,
			ProblemLoaderException {
		
		File dir = source;
		if (dir.isFile())
			dir = source.getParentFile();

		jsca = new JavaSourceCodeAnalyzer(dir, className, methodName, parameters);
		jsca.setContractAnalyzer(new JMLContractAnalyzer());
		
		System.out.println("Loading proof to apply taclets");
		AbstractProblemLoader apl = main.getUserInterface().load(null, source,
				null, null, null, null, false);
		System.out.println("Done loading proof");
		InitConfig ic = apl.getInitConfig();
		JavaInfo ji = ic.getServices().getJavaInfo();
		KeYJavaType kjt = ji.getTypeByClassName(className);
		// ProofManagementDialog.showInstance(ic);
		if (kjt == null) {
			throw new IllegalArgumentException(new ClassNotFoundException(
					className));
		}
		SpecificationRepository specRepo = ic.getServices()
				.getSpecificationRepository();
		List<Contract> contracts = getCorrectContract(methodName, parameters, specRepo, kjt);
		Contract contract = contracts.iterator().next();
		ProofOblInput poi = contract.createProofObl(ic);
		KeYMediator mediator = main.getMediator();
		AbstractMediatorUserInterfaceControl ui = mediator.getUI();

		ui.createProof(ic, poi);
		applyTaclets(main.getMediator(), so);
		applySettings(main.getMediator(), so);
	}

	/**
	 * Fuehrt alle Beweise fuer eine Methode durch.
	 * 
	 * @param source
	 *            Die zu beweisende Quellcodeeinheit (ein Ordner)
	 * @param classPath
	 *            Ein separater ClassPath (wuerde JavaRedux ueberschreiben).
	 * @param className
	 *            Der Name der Klasse, in der eine Methode bewiesen werden soll.
	 * @param methodName
	 *            Der Name der zu beweisenden Methode.
	 * @param so
	 *            Enthaelt die Optionen fuer den Beweis.
	 * @return Gibt fuer jeden Vertrag an dieser Methode ein Result-Objekt
	 *         zurueck.
	 * @throws ProblemLoaderException
	 *             wird geworfen, sofern KeY ein Problem mit dem Laden des Codes
	 *             hat.
	 * @throws ProofInputException
	 */
	private List<IDataBasisElement> executeProofsForMethod(File source, File classPath,
			String className, String methodName, String[] parameters, int contractNumber,
			KeySettingsObject so) throws ProblemLoaderException,
			ProofInputException {
		MainWindow main = MainWindow.getInstance();
		List<IDataBasisElement> results = new ArrayList<>();
		try {
			main.setVisible(false);
			// if (!so.getTacletMap().isEmpty())
			loadProofAndApplySettings(main, source, classPath, className,
					methodName, parameters, so);
			System.out.println("Going to load proof to proof it");
			AbstractProblemLoader apl = main.getUserInterface().load(null,
					source, null, null, null, null, false);
			System.out.println("Done loading proof");
			InitConfig ic = apl.getInitConfig();
			JavaInfo ji = ic.getServices().getJavaInfo();
			KeYJavaType kjt = ji.getTypeByClassName(className);
			// ProofManagementDialog.showInstance(ic);
			if (kjt == null) {
				throw new IllegalArgumentException(new ClassNotFoundException(
						className));
			}
			SpecificationRepository specRepo = ic.getServices()
					.getSpecificationRepository();
			List<Contract> contracts = getCorrectContract(methodName, parameters, specRepo,
					kjt);
			if (contractNumber == -1) {
				for (Contract contract : contracts) {
					results.add(getResult(main, ic, contract, so));
				}
			} else {
				results.add(getResult(main, ic, contracts.get(contractNumber),
						so));
			}
		} finally {
			main.dispose();
		}
		return results;
	}

	private KeyDataBasisElement getResult(MainWindow main, InitConfig ic, Contract contract,
			KeySettingsObject so) throws ProofInputException {
		ProofOblInput poi = contract.createProofObl(ic);
		KeYMediator mediator = main.getMediator();
		AbstractMediatorUserInterfaceControl ui = mediator.getUI();

		Proof p = ui.createProof(ic, poi);
		applySettings(mediator, so);
		ui.getProofControl().startAndWaitForAutoMode(p);

		return createResult(contract, p,
				jsca.analyze().stream().map(l -> l.getLanguageConstruct()).collect(Collectors.toList()));
	}

	@Override
	public int getNumberOfContracts(File source, File classPath,
			String className, String methodName, String[] parameters) {
		MainWindow main = MainWindow.getInstance();
		try {
			main.setVisible(false);
			System.out.println("Going to load proof to proof it");
			AbstractProblemLoader apl = null;
			try {
				apl = main.getUserInterface().load(null,
						source, null, null, null, null, false);
			} catch (ProblemLoaderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Done loading proof");
			InitConfig ic = apl.getInitConfig();
			JavaInfo ji = ic.getServices().getJavaInfo();
			KeYJavaType kjt = ji.getTypeByClassName(className);
			// ProofManagementDialog.showInstance(ic);
			if (kjt == null) {
				throw new IllegalArgumentException(new ClassNotFoundException(
						className));
			}
			SpecificationRepository specRepo = ic.getServices()
					.getSpecificationRepository();
			List<Contract> contracts = getCorrectContract(methodName, parameters, specRepo,
					kjt);
			return contracts.size();
		} finally {
			main.dispose();
		}
	}

	@Override
	public void performProof(SettingsObject so) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isClosed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<IDataBasisElement> getCurrentResults() {
		// TODO Auto-generated method stub
		return null;
	}
}
