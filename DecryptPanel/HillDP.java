package DecryptPanel;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.border.Border;

import EncryptMethod.Hill;

public class HillDP extends JPanel {
	JPanel top, mid, bot, subPanel;
	File textFile, keyFile, destDir;
	File currentDir = new File("D:\\");
	int[][] matrixKey = new int[2][2];
	boolean fileUploaded, keyUploaded;

	public HillDP() {
		setLayout(new BorderLayout());

		/*
		 * Top Layout
		 */
		top = new JPanel();
		top.setLayout(new GridLayout(4, 1));
		Border topBD = BorderFactory.createLineBorder(Color.blue);
		top.setBorder(BorderFactory.createTitledBorder(topBD, "Thông tin - Giải mã Hill"));

		// CipherText
		JPanel ctPanel = new JPanel();
		ctPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		JLabel ctLabel = new JLabel("Cipher Text");
		JTextField ctTextField = new JTextField(30);
		ctTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		ctPanel.add(ctLabel);
		ctPanel.add(ctTextField);

		top.add(ctPanel);

		// Key
		JPanel keyPanel = new JPanel();
		keyPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		JLabel keyLabel = new JLabel("Key");
		keyPanel.add(keyLabel);

		JPanel subKeyPanel = new JPanel();
		subKeyPanel.setLayout(new GridLayout(2, 2));
		JTextField keyTextField1 = new JTextField(15);
		keyTextField1.setHorizontalAlignment(SwingConstants.CENTER);
		JTextField keyTextField2 = new JTextField(15);
		keyTextField2.setHorizontalAlignment(SwingConstants.CENTER);
		JTextField keyTextField3 = new JTextField(15);
		keyTextField3.setHorizontalAlignment(SwingConstants.CENTER);
		JTextField keyTextField4 = new JTextField(15);
		keyTextField4.setHorizontalAlignment(SwingConstants.CENTER);
		subKeyPanel.add(keyTextField1);
		subKeyPanel.add(keyTextField2);
		subKeyPanel.add(keyTextField3);
		subKeyPanel.add(keyTextField4);

		keyPanel.add(subKeyPanel);

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

		JScrollPane scrollPane = new JScrollPane(txtArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
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
				// Upload File
				if (e.getActionCommand().equals("Choose File")) {
					JFileChooser textFileChooser = new JFileChooser(currentDir);
					int userChoice = textFileChooser.showOpenDialog(HillDP.this);
					if (userChoice == JFileChooser.APPROVE_OPTION) {
						textFile = textFileChooser.getSelectedFile();
						currentDir = textFileChooser.getCurrentDirectory();

						ctTextField.setText(textFile.getName());
						fileUploaded = true;
					}
				}

				// Load key
				if (e.getActionCommand().equals("Load key")) {
					JFileChooser keyFileChooser = new JFileChooser(currentDir);
					int userChoice = keyFileChooser.showOpenDialog(HillDP.this);
					if (userChoice == JFileChooser.APPROVE_OPTION) {
						keyFile = keyFileChooser.getSelectedFile();
						currentDir = keyFileChooser.getCurrentDirectory();

						Hill hill = new Hill();
						try {
							matrixKey = hill.readKey(keyFile);
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						keyTextField1.setText(String.valueOf(matrixKey[0][0]));
						keyTextField2.setText(String.valueOf(matrixKey[0][1]));
						keyTextField3.setText(String.valueOf(matrixKey[1][0]));
						keyTextField4.setText(String.valueOf(matrixKey[1][1]));
						keyUploaded = true;
					}

				}

				// Decryption
				if (e.getActionCommand().equals("Decrypt")) {
					if (ctTextField.getText().isBlank() || keyTextField1.getText().isBlank()
							|| keyTextField2.getText().isBlank() || keyTextField3.getText().isBlank()
							|| keyTextField4.getText().isBlank()) {
						JOptionPane.showMessageDialog(null, "There is no plain text or key", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {
						// Choose Directory to save decrypted file
						JFileChooser dirChooser = new JFileChooser(currentDir);
						dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						int userChoice = dirChooser.showSaveDialog(HillDP.this);
						if (userChoice == JFileChooser.APPROVE_OPTION) {
							destDir = dirChooser.getSelectedFile();
							currentDir = dirChooser.getCurrentDirectory();

							String text = "";
							int[][] key = new int[2][2];

							// Manual or not
							if (fileUploaded == true) {
								text = textFile.getAbsolutePath();
								fileUploaded = false;
							} else {
								text = ctTextField.getText();
							}

							if (keyUploaded == true) {
								key = matrixKey;
								keyUploaded = false;
							} else {
								key[0][0] = Integer.parseInt(keyTextField1.getText());
								key[0][1] = Integer.parseInt(keyTextField2.getText());
								key[1][0] = Integer.parseInt(keyTextField3.getText());
								key[1][1] = Integer.parseInt(keyTextField4.getText());
							}

							// Decrypting
							Hill hill = new Hill();
							try {
								hill.decrypt(text, key, destDir);

								// Show result to Text Area
								txtArea.setText("Result");
								txtArea.append("\n" + hill.getDecryptedString());
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
