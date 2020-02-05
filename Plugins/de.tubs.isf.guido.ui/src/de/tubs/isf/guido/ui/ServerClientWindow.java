package de.tubs.isf.guido.ui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;


import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.RowData;

public class ServerClientWindow {

	protected Shell shell;
	private Text textServerJob;
	private Text text;
	private Text text_1;
	private Text text_2;
	private Text text_3;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ServerClientWindow window = new ServerClientWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(620, 510);
		shell.setMinimumSize(620, 510);
		shell.setText("SWT Application");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));

		TabFolder tabFolder = new TabFolder(shell, SWT.NONE);

		TabItem tbtmServer = new TabItem(tabFolder, SWT.NONE);
		tbtmServer.setText("Server");


		TabItem tbtmClient = new TabItem(tabFolder, SWT.NONE);
		tbtmClient.setText("Client");
		
		SashForm sashForm_3 = new SashForm(tabFolder, SWT.VERTICAL);
		tbtmClient.setControl(sashForm_3);
		
		SashForm sashForm_4 = new SashForm(sashForm_3, SWT.NONE);
		
		Group grpIpAdress = new Group(sashForm_4, SWT.NONE);
		grpIpAdress.setText("IP Adress");
		grpIpAdress.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		text_1 = new Text(grpIpAdress, SWT.BORDER);
		text_1.setText("127.0.01");
		
		Button btnStartClient = new Button(sashForm_4, SWT.NONE);
		btnStartClient.setText("Start Client");
		sashForm_4.setWeights(new int[] {1, 1});
		
		Label lblNewLabel = new Label(sashForm_3, SWT.NONE);
		sashForm_3.setWeights(new int[] {43, 398});
		Composite compositeClient = new Composite(tabFolder, SWT.NONE);


		TabItem tbtmSettings = new TabItem(tabFolder, SWT.NONE);
		tbtmSettings.setText("Settings");
		
		SashForm sashForm_5 = new SashForm(tabFolder, SWT.VERTICAL);
		tbtmSettings.setControl(sashForm_5);
		
		SashForm sashForm_7 = new SashForm(sashForm_5, SWT.NONE);
		
		Label lblNewLabel_1 = new Label(sashForm_7, SWT.NONE);
		lblNewLabel_1.setText("New Label");
		
		text_2 = new Text(sashForm_7, SWT.BORDER);
		sashForm_7.setWeights(new int[] {1, 1});
		
		SashForm sashForm_6 = new SashForm(sashForm_5, SWT.NONE);
		
		Label label = new Label(sashForm_6, SWT.NONE);
		label.setText("New Label");
		
		text_3 = new Text(sashForm_6, SWT.BORDER);
		sashForm_6.setWeights(new int[] {1, 1});
		sashForm_5.setWeights(new int[] {1, 1});
		Composite compositeSettings = new Composite(tabFolder, SWT.NONE);


	    SashForm sashForm = new SashForm(tabFolder, SWT.VERTICAL);
	    sashForm.setSashWidth(3);
		tbtmServer.setControl(sashForm);
		
		tbtmServer.setControl(sashForm);
		
		Composite composite_1 = new Composite(sashForm, SWT.NONE);
		composite_1.setLayout(new GridLayout(1, false));
		
		Composite composite = new Composite(composite_1, SWT.NONE);
		RowLayout layout = new RowLayout();
		composite.setLayout(layout);
		
		
		Button button_1 = new Button(composite, SWT.CENTER);
		button_1.setLayoutData(new RowData(90, 55));
		button_1.setBounds(5, 5, 70, 32);
		button_1.setText("Start Server");
		
		Group group = new Group(composite, SWT.NONE);
		group.setLayoutData(new RowData(484, 30));
		group.setText("Mode");
		group.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		
		CCombo combo = new CCombo(group, SWT.BORDER | SWT.READ_ONLY);
		
		Composite composite_2 = new Composite(composite_1, SWT.NONE);
		composite_2.setLayout(new RowLayout(SWT.HORIZONTAL));
		Button button = new Button(composite_2, SWT.PUSH);
		button.setLayoutData(new RowData(90, 37));
		button.setText("Open Job File!");
		textServerJob = new Text(composite_2, SWT.BORDER);
		textServerJob.setLayoutData(new RowData(475, 31));
		textServerJob.setText("Insert Path to JobFile here!");
		
		Group grpConsole = new Group(sashForm, SWT.NONE);
		grpConsole.setText("Console");
		grpConsole.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		text = new Text(grpConsole, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		sashForm.setWeights(new int[] {121, 319});
		
		
		
		
		
		
		

	}
}
