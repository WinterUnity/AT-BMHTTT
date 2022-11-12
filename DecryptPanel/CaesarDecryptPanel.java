package DecryptPanel;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import EncryptMethod.Caesar;

public class CaesarDecryptPanel extends JPanel {
	JPanel top, mid, bot, subPanel;
	File textFile, keyFile, destDir;
	int caesarKey;
	boolean fileUploaded, keyCreated;

	public CaesarDecryptPanel() {
		setLayout(new BorderLayout());

		/*
		 * Top Layout
		 */
		top = new JPanel();
		top.setLayout(new GridLayout(5,1));
		Border topBD = BorderFactory.createLineBorder(Color.blue);
		top.setBorder(BorderFactory.createTitledBorder(topBD, "Thông tin - Giải mã Caesar"));

		// CipherText
		JPanel ctPanel = new JPanel();
		ctPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		JLabel ptLabel = new JLabel("Cipher Text");
		JTextField ptTextField = new JTextField(30);
		ptTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		ctPanel.add(ptLabel);
		ctPanel.add(ptTextField);

		top.add(ctPanel);

		// Key
		JPanel keyPanel = new JPanel();
		keyPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		JLabel keyLabel = new JLabel("Key");
		JTextField keyTextField = new JTextField(30);
		keyTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		keyPanel.add(keyLabel);
		keyPanel.add(keyTextField);

		top.add(keyPanel);

		/*
		 * Mid Layout
		 */
		mid = new JPanel();
		mid.setLayout(new FlowLayout());
		Border midBD = BorderFactory.createLineBorder(Color.BLUE);
		mid.setBorder(BorderFactory.createTitledBorder(midBD, "Thao tác"));

		// Choose file
		Button chooseFile = new Button("Chọn File");
		mid.add(chooseFile);

		// Create key
		Button loadKey = new Button("Load khóa");
		mid.add(loadKey);

		// Decrypt
		Button decrypt = new Button("Giải mã");
		mid.add(decrypt);
		
		/*
		 * Bot Layout
		 */
		bot = new JPanel();
		bot.setLayout(new BorderLayout());
		Border botBD = BorderFactory.createLineBorder(Color.gray);
		bot.setBorder(botBD);
		
		JTextArea txtArea = new JTextArea(10, 800);
		txtArea.append("Result");
		bot.add(txtArea);
		
		JScrollPane scrollPane = new JScrollPane(txtArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
				, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		bot.add(scrollPane, BorderLayout.SOUTH);
		
		/*
		 * Main DecryptPanel
		 */
		subPanel = new JPanel();
		subPanel.setLayout(new BorderLayout());
		subPanel.add(mid, BorderLayout.SOUTH);
		subPanel.add(top, BorderLayout.CENTER);
		add(subPanel);
		
		add(bot, BorderLayout.SOUTH);
		
		/*
		 * Handle event
		 */
		ActionListener buttonListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Upload File
				if (e.getActionCommand().equals("Choose File")) {
					JFileChooser textFileChooser = new JFileChooser("D:\\");
					int userChoice = textFileChooser.showOpenDialog(CaesarDecryptPanel.this);
					if(userChoice == JFileChooser.APPROVE_OPTION) {
						textFile = textFileChooser.getSelectedFile();
						ptTextField.setText(textFile.getName());
						fileUploaded = true;
					}
				}
				
				//Create key
				if(e.getActionCommand().equals("Load key")) {
					JFileChooser keyFileChooser = new JFileChooser("D:\\");
					int userChoice = keyFileChooser.showOpenDialog(CaesarDecryptPanel.this);
					if(userChoice == JFileChooser.APPROVE_OPTION) {
						keyFile = keyFileChooser.getSelectedFile();
						
						Caesar caesar = new Caesar();
						try {
							caesarKey = caesar.readKey(keyFile);
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						keyTextField.setText(Integer.toString(caesarKey));
						keyCreated = true;
					}
					
				}
				
				//Decryption
				if(e.getActionCommand().equals("Decrypt")) {
					if(ptTextField.getText().isBlank() ||
						keyTextField.getText().isBlank()) {
						JOptionPane.showMessageDialog(null, "There is no plain text or key", "Error", JOptionPane.ERROR_MESSAGE);
					}
					else {
						//Choose Directory to save decrypted file
						JFileChooser dirChooser = new JFileChooser("D:\\");
						dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						int userChoice = dirChooser.showSaveDialog(CaesarDecryptPanel.this);
						if(userChoice == JFileChooser.APPROVE_OPTION) {
							destDir = dirChooser.getSelectedFile();
							
							String text = "";
							int key = 0;
							
							//Manual or not
							if(fileUploaded == true) {
								text = textFile.getAbsolutePath();
							} else {
								text = ptTextField.getText();
							}
							
							if(keyCreated == true) {
								key = caesarKey;
							} else {
								key = Integer.parseInt(keyTextField.getText());
							}
							
							//Decrypting
							Caesar caesar = new Caesar();
							try {
								caesar.decrypt(text, key, destDir);
								
								//Show result to Text Area
								txtArea.setText(caesar.getDecryptedString());
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
					}
				}
			}
		};
		
		chooseFile.addActionListener(buttonListener);
		loadKey.addActionListener(buttonListener);
		decrypt.addActionListener(buttonListener);
		
		chooseFile.setActionCommand("Choose File");
		loadKey.setActionCommand("Load key");
		decrypt.setActionCommand("Decrypt");
	}
}
