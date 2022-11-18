package EncryptPanel;

import java.awt.*;
import java.awt.event.*;

import javax.crypto.*;
import javax.swing.*;
import javax.swing.border.Border;

import java.io.*;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

import EncryptMethod.RSA;

public class RSAFileEP extends JPanel {
	JPanel top, mid, bot, subPanel;
	File srcFile, keyFile, destDir;
	File currentDir = new File("D:\\");
	PrivateKey privateKey;
	PublicKey publicKey;
	KeyPair keyPair;

	boolean fileUploaded, keyUploaded, keyCreated;

	public RSAFileEP() {
		RSA rsa = new RSA();
		rsa.createKey();
		setLayout(new BorderLayout());

		/*
		 * Top Layout
		 */
		top = new JPanel();
		top.setLayout(new GridLayout(5, 1));
		Border topBD = BorderFactory.createLineBorder(Color.blue);
		top.setBorder(BorderFactory.createTitledBorder(topBD, "Thông tin - Mã hóa RSA - File"));

		// PlainText
		JPanel ptPanel = new JPanel();
		ptPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		JLabel ptLabel = new JLabel("File");
		JTextField ptTextField = new JTextField(30);
		ptTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		ptTextField.setEditable(false);
		ptPanel.add(ptLabel);
		ptPanel.add(ptTextField);

		top.add(ptPanel);

		// Key
		JPanel keyPanel = new JPanel();
		keyPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		JLabel keyLabel = new JLabel("Key");
		JTextField keyTextField = new JTextField(30);
		keyTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		keyTextField.setEditable(false);
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

		// Upload key
		Button uploadkey = new Button("Upload khóa");
		mid.add(uploadkey);

		// Create key
		Button createKey = new Button("Tạo khóa");
		mid.add(createKey);

		// Encrypt
		Button encrypt = new Button("Mã hóa");
		mid.add(encrypt);

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
		 * Main EncryptPanel
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
					JFileChooser fileChooser = new JFileChooser(currentDir);
					int userChoice = fileChooser.showOpenDialog(RSAFileEP.this);
					if (userChoice == JFileChooser.APPROVE_OPTION) {
						srcFile = fileChooser.getSelectedFile();
						currentDir = fileChooser.getCurrentDirectory();

						ptTextField.setText(srcFile.getName());
						fileUploaded = true;
					}
				}
				
				// Load key
				if (e.getActionCommand().equals("Load key")) {
					JFileChooser keyFileChooser = new JFileChooser(currentDir);
					int userChoice = keyFileChooser.showOpenDialog(RSAFileEP.this);
					if (userChoice == JFileChooser.APPROVE_OPTION) {
						keyFile = keyFileChooser.getSelectedFile();
						currentDir = keyFileChooser.getCurrentDirectory();

						try {
							publicKey = rsa.readPublicKey(keyFile);
						} catch (ClassNotFoundException | NoSuchAlgorithmException | InvalidKeySpecException | IOException e1) {
							e1.printStackTrace();
						}
						keyTextField.setText("Key uploaded");
						keyUploaded = true;
					}

				}

				// Create key
				if (e.getActionCommand().equals("Create key")) {
					publicKey = rsa.getPublicKey();
					privateKey = rsa.getPrivateKey();
					keyTextField.setText("Key created");
					keyCreated = true;
				}

				// Encryption
				if (e.getActionCommand().equals("Encrypt")) {
					if (ptTextField.getText().isBlank() || keyTextField.getText().isBlank()) {
						JOptionPane.showMessageDialog(null, "There is no plain text or key", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {
						if (fileUploaded != true) {
							JOptionPane.showMessageDialog(null, "Source file not uploaded", "Error",
									JOptionPane.ERROR_MESSAGE);
						}
						if (keyCreated != true) {
							JOptionPane.showMessageDialog(null, "Key not created", "Error", JOptionPane.ERROR_MESSAGE);
						} else {
							JFileChooser dirChooser = new JFileChooser(currentDir);
							dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
							int userChoice = dirChooser.showSaveDialog(RSAFileEP.this);
							if (userChoice == JFileChooser.APPROVE_OPTION) {
								destDir = dirChooser.getSelectedFile();
								currentDir = dirChooser.getCurrentDirectory();

								// Encrypting
								try {
									rsa.encryptFile(srcFile, publicKey, destDir);

									// Show result to Text Area
									txtArea.setText("Result");
									txtArea.append("\nFile encrypted success fully");

									// Print key file
									File destFile1 = new File(destDir.getAbsolutePath() + "\\RSAPublicKey.txt");
									FileOutputStream fos1 = new FileOutputStream(destFile1);
									fos1.write(rsa.getEncodedPublicKey());
									fos1.close();
									
									File destFile2 = new File(destDir.getAbsolutePath() + "\\RSAPrivateKey.txt");
									FileOutputStream fos2 = new FileOutputStream(destFile2);
									fos2.write(rsa.getEncodedPrivateKey());
									fos2.close();
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
		uploadkey.addActionListener(buttonListener);
		createKey.addActionListener(buttonListener);
		encrypt.addActionListener(buttonListener);

		chooseFile.setActionCommand("Choose File");
		uploadkey.setActionCommand("Upload Key");
		createKey.setActionCommand("Create key");
		encrypt.setActionCommand("Encrypt");
	}
}