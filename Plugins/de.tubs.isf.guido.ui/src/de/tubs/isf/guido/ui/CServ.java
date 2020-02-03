package de.tubs.isf.guido.ui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;

import java.io.IOException;
import java.io.OutputStream;
import java.net.UnknownHostException;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Text;
import org.xml.sax.SAXException;

import de.tu.bs.guido.network.client.Client;
import de.tu.bs.guido.network.server.Server;
import de.tubs.isf.guido.core.verifier.Mode;

import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;

public class CServ {
	int mode;
	protected Shell shell;
	private Text text;
	private Text consoleServer;
	private Text txtIp;
	private Thread serverThread;
	private Thread clientThread;
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
			CServ window = new CServ();
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
		shell.addListener(SWT.Close, new Listener() {
			public void handleEvent(Event event) {
				SettingsControl.setSettings(ProjectSettings.getSettings());
			}
		});
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		
		ProjectSettings.setSettings(SettingsControl.getSettings());
		shell = new Shell();
		shell.setMinimumSize(new Point(400, 400));
		shell.setSize(400, 500);
		shell.setText("SWT Application");
		shell.addListener(SWT.Close, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				SettingsControl.setSettings(ProjectSettings.getSettings());
				System.exit(0);
			}
			
		});
		shell.setLayout(new GridLayout(1, false));

		TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		TabItem tbtmServer = new TabItem(tabFolder, SWT.NONE);
		tbtmServer.setText("Server");

		SashForm sashForm = new SashForm(tabFolder, SWT.NONE);
		sashForm.setSashWidth(1);
		tbtmServer.setControl(sashForm);

		Composite composite = new Composite(sashForm, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));

		Button btnStartServer = new Button(composite, SWT.NONE);
		btnStartServer.setEnabled(false);
		btnStartServer.setText("Start Server");
		btnStartServer.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				runServer(new String[] { text.getText(), Mode.values()[mode].name() });
				

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {

			}
		});

		Group grpMode = new Group(composite, SWT.NONE);
		grpMode.setText("Mode");
		grpMode.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpMode.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1, 1));

		CCombo combo = new CCombo(grpMode, SWT.BORDER);
		combo.setEditable(false);
		for (Mode iterable_element : Mode.values()) {
			combo.add(iterable_element.name());
		}
		combo.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				btnStartServer.setEnabled(true);
				mode = combo.getSelectionIndex();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		Button btnSearchFile = new Button(composite, SWT.NONE);

		btnSearchFile.setBounds(0, 0, 88, 30);
		btnSearchFile.setText("Search File!");
		btnSearchFile.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog fd = new FileDialog(shell, SWT.OPEN);
				fd.setText("Open");
				fd.setFilterPath("C:/");
				String[] filterExt = { "*.xml" };
				fd.setFilterExtensions(filterExt);
				String selected = fd.open();
				text.setText(selected);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		;

		text = new Text(composite, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));
		text.setBounds(0, 0, 88, 30);
		sashForm.setWeights(new int[] { 1 });

		TabItem tbtmClient = new TabItem(tabFolder, SWT.NONE);
		tbtmClient.setText("Client");

		SashForm sashForm_1 = new SashForm(tabFolder, SWT.NONE);
		tbtmClient.setControl(sashForm_1);

		Composite composite_1 = new Composite(sashForm_1, SWT.NONE);
		composite_1.setLayout(new GridLayout(1, false));

		Group grpIp = new Group(composite_1, SWT.NONE);
		grpIp.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));
		grpIp.setText("IP");
		grpIp.setLayout(new FillLayout(SWT.HORIZONTAL));

		txtIp = new Text(grpIp, SWT.BORDER);

		Button btnStartClient = new Button(composite_1, SWT.NONE);
		btnStartClient.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, false, false, 1, 1));
		btnStartClient.setText("Start Client");

		btnStartClient.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				runClient(new String[] { Mode.values()[mode].toString(), txtIp.getText() });

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		sashForm_1.setWeights(new int[] { 1 });

		TabItem tbtmSettings = new TabItem(tabFolder, SWT.NONE);
		tbtmSettings.setText("Settings");
		
		SashForm sashForm_2 = new SashForm(tabFolder, SWT.NONE);
		tbtmSettings.setControl(sashForm_2);
		
		Composite composite_2 = new Composite(sashForm_2, SWT.NONE);
		composite_2.setLayout(new GridLayout(1, false));
		
		Composite composite_3 = new Composite(composite_2, SWT.NONE);
		composite_3.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		composite_3.setBounds(0, 0, 64, 64);
		composite_3.setLayout(new GridLayout(3, false));
		
		Label lblNewLabel = new Label(composite_3, SWT.NONE);
		lblNewLabel.setBounds(0, 0, 55, 15);
		lblNewLabel.setText("Result Directory");
		
		text_1 = new Text(composite_3, SWT.BORDER);
		text_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		text_1.setBounds(0, 0, 76, 21);
		
		Button btnNewButton = new Button(composite_3, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				DirectoryDialog dialog = new DirectoryDialog(shell);
			   
			  text_1.setText(dialog.open());
			}
		});
		btnNewButton.setText("Change!");
		text_1.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent arg0) {

				ProjectSettings.outputPath = text_1.getText();

			}
		});
		text_1.setText(ProjectSettings.outputPath);
		Composite composite_4 = new Composite(composite_2, SWT.NONE);
		composite_4.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		composite_4.setSize(182, 79);
		composite_4.setLayout(new GridLayout(3, false));
		
		Label label = new Label(composite_4, SWT.NONE);
		label.setText("Punishment Path");
		
		text_2 = new Text(composite_4, SWT.BORDER);
		text_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		text_2.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent arg0) {

				ProjectSettings.punishmentPath = text_2.getText();

			}
		});
		text_2.setText(ProjectSettings.punishmentPath);
		Button button = new Button(composite_4, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				DirectoryDialog dialog = new DirectoryDialog(shell);
			   
			  text_2.setText(dialog.open());
			}
		});
		button.setText("Change!");
		
		Composite composite_5 = new Composite(composite_2, SWT.NONE);
		composite_5.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		composite_5.setBounds(0, 0, 64, 64);
		composite_5.setLayout(new GridLayout(3, false));
		
		Label label_1 = new Label(composite_5, SWT.NONE);
		label_1.setText("Library Path");
		
		text_3 = new Text(composite_5, SWT.BORDER);
		text_3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		text_3.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent arg0) {

				ProjectSettings.libraryPath = text_3.getText();

			}
		});
		text_3.setText(ProjectSettings.libraryPath);
		Button button_1 = new Button(composite_5, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				DirectoryDialog dialog = new DirectoryDialog(shell);
			   
			  text_3.setText(dialog.open());
			}
		});
		button_1.setText("Change!");
		sashForm_2.setWeights(new int[] {1});
		Group grpMode2 = new Group(shell, SWT.NONE);
		grpMode2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpMode2.setText("Console");
		grpMode2.setLayout(new FillLayout(SWT.HORIZONTAL));
		
				setConsole(new Text(grpMode2, SWT.BORDER | SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI));
	}

	/**
	 * @return the console
	 */
	public Text getConsole() {
		return consoleServer;
	}

	/**
	 * @param console the console to set
	 */
	public void setConsole(Text console) {
		this.consoleServer = console;
	}

	protected void runServer(String[] path) {
		serverThread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Server.opS = new CustomOutputStream(consoleServer);
					Server.main(path);
				} catch (IOException | SAXException | ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		serverThread.start();

	}

	protected void runClient(String[] mainArgs) {
		clientThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {

					Client.main(mainArgs);
					Client.opS = new CustomOutputStream(consoleServer);
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
		});
		clientThread.start();

	}

	public class CustomOutputStream extends OutputStream {

		private Text list;

		public CustomOutputStream(Text list) {
			this.list = list;
		}

		@Override
		public void write(int b) throws IOException {
			Display.getDefault().asyncExec(new Runnable() {

				@Override
				public void run() {
					String old = list.getText();
					old = old + (char) b;
					list.setText(old);
					list.setTopIndex(list.getLineCount()-1);

				}
			});

		}
		
	}
}
