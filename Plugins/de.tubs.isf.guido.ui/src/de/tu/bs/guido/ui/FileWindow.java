package de.tu.bs.guido.ui;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JFileChooser;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.xml.sax.SAXException;

import de.tu.bs.guido.network.client.Client;
import de.tu.bs.guido.network.server.Server;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import swing2swt.layout.FlowLayout;

public class FileWindow extends Dialog {

	String input1;
	String input2;
	String defaultPath = new String("./generated");
	private Shell shell_1;
	private Text text;
	private GridData gd_lblStartTheclient;

	/**
	 * InputDialog constructor
	 * 
	 * @param parent the parent
	 */
	public FileWindow(Shell parent) {
		// Pass the default styles here
		this(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);
	}

	/**
	 * InputDialog constructor
	 * 
	 * @param parent the parent
	 * @param style  the style
	 */
	public FileWindow(Shell parent, int style) {
		// Let users override the default styles
		super(parent, style);
		setText("Input Dialog");

	}

	public String open() {
		// Create the dialog window

		shell_1 = new Shell(getParent(), SWT.SHELL_TRIM | SWT.BORDER | SWT.PRIMARY_MODAL);
		shell_1.setMinimumSize(new Point(300, 300));
		shell_1.setText(getText());
		shell_1.setSize(536, 375);
		createContents(shell_1);
		shell_1.pack();
		shell_1.addListener(SWT.Close, new Listener() {
			public void handleEvent(Event event) {
				SettingsControl.setSettings(ProjectSettings.getSettings());
			}
		});
		// Create the cancel button and add a handler
		// so that pressing it will set input to null
		Button cancel = new Button(shell_1, SWT.PUSH);
		cancel.setText("Cancel");
		cancel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		cancel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				input1 = null;
				shell_1.close();
			}
		});
		shell_1.open();
		Display display = getParent().getDisplay();
		while (!shell_1.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		// Return the entered value, or null
		return input1;
	}

	/**
	 * Creates the dialog's contents
	 * 
	 * @param shell the dialog window
	 */
	private void createContents(final Shell shell) {
		shell.setSize(500, 500);
		GridLayout gl_shell_1 = new GridLayout(2, true);
		gl_shell_1.horizontalSpacing = 20;
		shell.setLayout(gl_shell_1);

		// Show the message
		Label lblStartTheclient = new Label(shell, SWT.NONE);
		lblStartTheclient.setText("Start the Client!");
		GridData data;
		gd_lblStartTheclient = new GridData();
		gd_lblStartTheclient.horizontalSpan = 3;
		lblStartTheclient.setLayoutData(gd_lblStartTheclient);

		Composite composite_1 = new Composite(shell_1, SWT.NONE);
		composite_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		Button buttonClientStart = new Button(composite_1, SWT.PUSH);
		buttonClientStart.setText("Start");
		buttonClientStart.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {

				runClient();
			}
		});
		new Label(shell_1, SWT.NONE);
		new Label(shell_1, SWT.NONE);

		data = new GridData(GridData.FILL_HORIZONTAL);

		data = new GridData(GridData.FILL_HORIZONTAL);
		new Label(shell_1, SWT.NONE);

		// Create the OK button and add a handler
		// so that pressing it will set input
		// to the entered value

		// Show the message

		Label labelServer = new Label(shell, SWT.NONE);

		labelServer.setText("Start the Server!");
		data = new GridData();
		data.horizontalSpan = 3;
		labelServer.setLayoutData(data);

		// Display the input box
		final Text textServer = new Text(shell, SWT.BORDER);
		textServer.setText(ProjectSettings.pathJob);
		textServer.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent arg0) {
				System.out.println("war");
				ProjectSettings.pathJob = textServer.getText();
				

			}
		});

		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		textServer.setLayoutData(data);

		Composite composite = new Composite(shell_1, SWT.NONE);
		composite.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		Button buttonServerStart = new Button(composite, SWT.PUSH);
		buttonServerStart.setText("Start");
		buttonServerStart.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {

				runServer(textServer.getText().split(","));
			}
		});
		Button buttonServerImport = new Button(composite, SWT.PUSH);
		buttonServerImport.setText("Import he Jobs");
		buttonServerImport.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					textServer.setText(file.getPath());
					

				} else {

				}

			}
		});
		new Label(shell_1, SWT.NONE);
		new Label(shell_1, SWT.NONE);
		new Label(shell_1, SWT.NONE);

		Label lblStarteDasGuidance = new Label(shell_1, SWT.NONE);
		lblStarteDasGuidance.setText("Start the Guidance Sytem");
		new Label(shell_1, SWT.NONE);

		Composite composite_2 = new Composite(shell_1, SWT.NONE);
		composite_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		Button button = new Button(composite_2, SWT.NONE);
		button.setText("Start");
		new Label(shell_1, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {

				runGuidance();
			}
		});
		new Label(shell_1, SWT.NONE);
		new Label(shell_1, SWT.NONE);
		Label lblSpeicherortDerGenerierten = new Label(shell_1, SWT.NONE);
		lblSpeicherortDerGenerierten.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		lblSpeicherortDerGenerierten.setText("Desired Location of the generated Files");
				new Label(shell_1, SWT.NONE);
		
				text = new Text(shell_1, SWT.BORDER);
				text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
				Button btnChangeLocation = new Button(shell_1, SWT.NONE);
				btnChangeLocation.setText("Change Location");
				btnChangeLocation.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent event) {
						File workingDirectory = null;
						if (defaultPath.equals("./")) {
							workingDirectory = new File(System.getProperty("user.dir"));
						} else {
							workingDirectory = new File(defaultPath);
						}
						JFileChooser fc = new JFileChooser(workingDirectory);
						int returnVal = fc.showOpenDialog(null);
						if (returnVal == JFileChooser.APPROVE_OPTION) {
							File file = fc.getSelectedFile();
							textServer.setText(file.getPath());
							File x = new File(textServer.getText());
							defaultPath = file.getAbsolutePath();

						} else {

						}

					}
				});
		// Set the OK button as the default, so
		// user can type input and press Enter
		// to dismiss
		Button ok = new Button(shell, SWT.PUSH);
		ok.setText("OK");
		data = new GridData(GridData.FILL_HORIZONTAL);
		ok.setLayoutData(data);
		ok.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {

				shell.close();
			}
		});
		shell.setDefaultButton(ok);
	}

	protected void runServer(String[] path) {
		
		
		try {

			Server.main(path);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void runClient() {
		try {
			Client.main(new String[1]);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected boolean isResizable() {
		return true;
	}

	protected void runGuidance() {
		de.tu.bs.guido.core.automaticProof.Main.main(null);
	}
}
