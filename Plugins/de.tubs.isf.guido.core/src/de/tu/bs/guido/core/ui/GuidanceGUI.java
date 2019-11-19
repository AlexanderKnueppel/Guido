package de.tu.bs.guido.core.ui;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import de.tu.bs.guido.core.automaticProof.GuidanceSystemResult;
import de.tu.bs.guido.core.automaticProof.Prover;
import de.tu.bs.guido.core.automaticProof.ProverThread;
import de.tu.bs.guido.core.keYHandler.KeYGUI;
import de.uka.ilkd.key.speclang.Contract;

public class GuidanceGUI {
	
	KeYGUI keYGUI;
	JTextField classF;
	JTextField method;
	JTextField parameter;
	JTextField source;
	JTextField redux;
	JTextField contractNumber;
	JTextArea resultText;
	Prover prover;

	public GuidanceGUI(Prover prover){
		this.prover = prover;
		keYGUI = new KeYGUI(false);
	}
	
	public void show() {
		JFrame frame = new JFrame("KeY Guidance System");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container contentPane = frame.getContentPane();

		GroupLayout layout = new GroupLayout(contentPane);
		contentPane.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		JPanel createProofAttempt = createProofAttempt();
		JPanel createInfo = createInfo();
		JButton proofButton = createProofButton();
		
		proofButton.addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
            	keYGUI.dispose();
            	if(isValidEntrySet()){
            		File sourceFile = new File(source.getText());
                	File reduxPath = new File(redux.getText());
                	String classString = classF.getText();
                	String methodString = method.getText();
                	String contractNumberString = contractNumber.getText();
                	String[] parameterArray = getParameter(parameter.getText());
                	GuidanceSystemResult res;
                	if(reduxPath.isFile()){
                		//res = ProverThread.startProvingThread("G", prover, sourceFile, reduxPath, classString, methodString, parameterArray, Integer.parseInt(contractNumberString));

                    	res = prover.getResultForProof(sourceFile, reduxPath, classString, methodString, parameterArray, Integer.parseInt(contractNumberString));
                    	resultText.append(res.getHtml());
                	}
                	else {
                		//res = ProverThread.startProvingThread("G", prover, sourceFile, null, classString, methodString, parameterArray, Integer.parseInt(contractNumberString));

                        res = prover.getResultForProof(sourceFile, null, classString, methodString, parameterArray, Integer.parseInt(contractNumberString));
                    	resultText.append(res.getHtml());
                	}
            	}
            	else {
                	resultText.append("Cannot be proved - some information are missing.\n");
            	}
            }
        });

		JLabel infoLabel = new JLabel();
		infoLabel.setText(
				"<html><body>Perform Proof Automatically<br></body></html>");

		layout.setHorizontalGroup(
				layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(infoLabel)
						.addComponent(createProofAttempt)
						.addComponent(proofButton)
						.addComponent(createInfo)
						)
		);
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(infoLabel)
				.addComponent(createProofAttempt)
				.addComponent(proofButton)
				.addComponent(createInfo)
				
		);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private JPanel createProofAttempt() {

		JPanel panel = new JPanel();

		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		JLabel sourceLabel = new JLabel("Source: ");
		source = new JTextField(15);
		JButton chooseSourceButton = new JButton("Get Source");
		
		chooseSourceButton.addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
            	addFile();
            }
        });

		JLabel reduxLabel = new JLabel("Reduxpath: ");
	    redux = new JTextField(15);

		JLabel classLabel = new JLabel("Class: ");
		classF = new JTextField(15);
		
		JLabel methodLabel = new JLabel("Method: ");
	    method = new JTextField(15);
		
		JLabel parameterLabel = new JLabel("Parameter: ");
		parameter = new JTextField(15);
		
		JLabel contractNumberLabel = new JLabel("Contract Number: ");
		contractNumber = new JTextField(15);
		
		JButton chooseProofAttemptButton = new JButton("Get Proof Attempt");
		
		chooseProofAttemptButton.addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
            	while(!new File(source.getText()).exists()){
            		addFile();
            	}
            	Contract contract = keYGUI.openGetMethodDialog(new File(source.getText()));
                String methodString = contract.getTarget().name().toString();
                methodString = methodString.substring(methodString.indexOf(":")+2);
                method.setText(methodString);
                classF.setText(contract.getKJT().getFullName());
                contractNumber.setText(Integer.toString(contract.id()));
                parameter.setText(setParameter(contract.getName()));
            	           			
            }
        });

		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
						.addComponent(classLabel)
						.addComponent(methodLabel)
						.addComponent(parameterLabel)
						.addComponent(contractNumberLabel)
						.addComponent(sourceLabel)
						.addComponent(reduxLabel))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(classF)
						.addComponent(method)
						.addComponent(parameter)
						.addComponent(contractNumber)
						.addComponent(source)
						.addComponent(redux))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(chooseSourceButton)
						.addComponent(chooseProofAttemptButton)
						));
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(sourceLabel)
						.addComponent(source).addComponent(chooseSourceButton))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(reduxLabel)
						.addComponent(redux))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(classLabel)
						.addComponent(classF)
						.addComponent(chooseProofAttemptButton)
						)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(methodLabel)
						.addComponent(method)
						)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(parameterLabel)
						.addComponent(parameter)
						)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(contractNumberLabel)
						.addComponent(contractNumber)
						)
				);
		return panel;
	}
	
	private JPanel createInfo() {

		JPanel panel = new JPanel();

		panel.setBorder(new TitledBorder(new EtchedBorder(), "Information"));

		    resultText = new JTextArea(16,58);
		    resultText.setEditable(false);
		    JScrollPane scroll = new JScrollPane(resultText);
		    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		    panel.add(scroll);


		return panel;
	}
	
	private JButton createProofButton(){
		JButton button = new JButton("Proof");
		return button;
	}
	
	private boolean isValidEntrySet(){
		File sourceFile = new File(source.getText());
    	String classString = classF.getText();
    	String methodString = method.getText();
    	String contractNumberString = contractNumber.getText();
    	
    	return sourceFile.exists() && !classString.isEmpty() && !methodString.isEmpty() && !contractNumberString.isEmpty();
	}
	
	private String setParameter(String contract){
		return contract.substring(contract.indexOf("(")+1, contract.indexOf(")"));
	}
	
	private String[] getParameter(String input){
		return input.isEmpty() ? null : input.split(",");
	}
	
	private void addFile(){
		File sourceFile = keYGUI.openFile();
    	source.setText(sourceFile.getPath());
	}
}
