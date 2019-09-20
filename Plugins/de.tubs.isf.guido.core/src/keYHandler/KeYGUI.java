package keYHandler;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import de.uka.ilkd.key.gui.KeYFileChooser;
import de.uka.ilkd.key.gui.MainWindow;
import de.uka.ilkd.key.proof.init.InitConfig;
import de.uka.ilkd.key.proof.io.AbstractProblemLoader;
import de.uka.ilkd.key.proof.io.ProblemLoaderException;
import de.uka.ilkd.key.settings.ProofIndependentSettings;
import de.uka.ilkd.key.speclang.Contract;

public class KeYGUI {

	public KeYGUI(boolean visible){
		createMain(visible);
	}

	private void createMain(boolean visible){
		main = MainWindow.getInstance();		
		main.setVisible(visible);
	}
	
	public MainWindow getMainWindow(){
		return main;
	}
	
	public Contract openGetMethodDialog(File file){
		AbstractProblemLoader apl;
		Contract c = null;
		try {
			apl = main.getUserInterface().load(null, file,
					null, null, null, null, false);
			System.out.println("Done loading proof");
    		InitConfig ic = apl.getInitConfig();
    		c = GetMethodDialog.showInstance(ic, file);
    		
		} catch (ProblemLoaderException e1) {
			e1.printStackTrace();
		}
		finally {
			main.dispose();
		}
		return c;
	}
	
	public File openFile(){
		KeYFileChooser keYFileChooser = 
	            KeYFileChooser.getFileChooser("Select file to load proof or problem");

	        boolean loaded = keYFileChooser.showOpenDialog(main);

	        if (loaded) {
	            File file = keYFileChooser.getSelectedFile();
	            if (ProofIndependentSettings.DEFAULT_INSTANCE.getViewSettings().getNotifyLoadBehaviour() &&
	                    file.toString().endsWith(".java")) {
	                Object[] message = { "When you load a Java file, all java files in the current",
	                        "directory and all subdirectories will be loaded as well."};
	                JOptionPane.showMessageDialog(main, message, 
	                        "Please note", JOptionPane.WARNING_MESSAGE);
	            }
	            return file;
	        }
	        return null;
	    }

	public void dispose(){
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.getExitMainAction();
		main.removeAll();
		main.dispose();
	}
	
	private MainWindow main;
	
}
