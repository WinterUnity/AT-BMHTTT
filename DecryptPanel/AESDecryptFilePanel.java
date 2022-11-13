package DecryptPanel;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.crypto.SecretKey;
import javax.swing.*;
import javax.swing.border.Border;

import EncryptMethod.AES;

public class AESDecryptFilePanel extends JPanel {
	JPanel top, mid, bot, subPanel;
	File textFile, keyFile, destDir;
	SecretKey aesKey;
	
	String srcFilePath, destFilePath;
	boolean fileUploaded, keyUploaded;

	public AESDecryptFilePanel() {
		setLayout(new BorderLayout());

		/*
		 * Top Layout
		 */
		top = new JPanel();
		top.setLayout(new GridLayout(5, 1));
		Border topBD = BorderFactory.createLineBorder(Color.blue);
		top.setBorder(BorderFactory.createTitledBorder(topBD, "Thông tin - Giải mã AES - File"));

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
					JFileChooser textFileChooser = new JFileChooser("D:\\");
					int userChoice = textFileChooser.showOpenDialog(AESDecryptFilePanel.this);
					if (userChoice == JFileChooser.APPROVE_OPTION) {
						textFile = textFileChooser.getSelectedFile();
						ctTextField.setText(textFile.getName());
						fileUploaded = true;
					}
				}

				// Load key
				if (e.getActionCommand().equals("Load key")) {
					JFileChooser keyFileChooser = new JFileChooser("D:\\");
					int userChoice = keyFileChooser.showOpenDialog(AESDecryptFilePanel.this);
					if (userChoice == JFileChooser.APPROVE_OPTION) {
						keyFile = keyFileChooser.getSelectedFile();

						AES aes = new AES();
						try {
							aesKey = aes.readKey(keyFile.getAbsolutePath());
						} catch (ClassNotFoundException e1) {
							e1.printStackTrace();
						}
						keyTextField.setText(aesKey.toString());
						keyUploaded = true;
					}

				}

				// Decryption
				if (e.getActionCommand().equals("Decrypt")) {
					if (ctTextField.getText().isBlank() || keyTextField.getText().isBlank()) {
						JOptionPane.showMessageDialog(null, "There is no plain text or key", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {
						if (fileUploaded != true) {
							JOptionPane.showMessageDialog(null, "Source file not uploaded", "Error",
									JOptionPane.ERROR_MESSAGE);
						} 
						if (keyUploaded != true) {
							JOptionPane.showMessageDialog(null, "key not uploaded", "Error",
									JOptionPane.ERROR_MESSAGE);
						} else {
							AES aes = new AES();
							// Choose Directory to save decrypted file
							JFileChooser dirChooser = new JFileChooser("D:\\");
							dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
							int userChoice = dirChooser.showSaveDialog(AESDecryptFilePanel.this);
							if (userChoice == JFileChooser.APPROVE_OPTION) {
								destDir = dirChooser.getSelectedFile();
								destFilePath = destDir.getAbsolutePath() + "\\CaesarDecryptedFile" + aes.getFileExtension(textFile);
								srcFilePath = textFile.getAbsolutePath();
								
								// Decrypting
								try {
									aes.decryptFile(srcFilePath, aesKey, destFilePath);

									// Show result to Text Area
									txtArea.setText("Result");
									txtArea.append("\nFile decrypted successfully");
								} catch (Exception e1) {
									e1.printStackTrace();
								}
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
