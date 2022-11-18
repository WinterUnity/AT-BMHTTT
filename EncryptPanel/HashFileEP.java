package EncryptPanel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

import java.io.*;

import EncryptMethod.Hash;

public class HashFileEP extends JPanel {
	JPanel top, mid, bot, subPanel;
	File srcFile, destDir;
	File currentDir = new File("D:\\");
	boolean fileUploaded;

	public HashFileEP() {
		setLayout(new BorderLayout());

		/*
		 * Top Layout
		 */
		top = new JPanel();
		top.setLayout(new GridLayout(5, 1));
		Border topBD = BorderFactory.createLineBorder(Color.blue);
		top.setBorder(BorderFactory.createTitledBorder(topBD, "Thông tin - Mã hóa Hash - File"));

		// PlainText
		JPanel ptPanel = new JPanel();
		ptPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		JLabel ptLabel = new JLabel("Plain Text");
		JTextField ptTextField = new JTextField(30);
		ptTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		ptTextField.setEditable(false);
		ptPanel.add(ptLabel);
		ptPanel.add(ptTextField);

		top.add(ptPanel);

		// Hash function
		JPanel optPanel = new JPanel();
		optPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		JLabel optLabel = new JLabel("Option");
		String[] options = { "MD2", "MD5", "SHA-1", "SHA-224", "SHA-256", "SHA-384", "SHA-512", "SHA-512/224",
				"SHA-512/256" };
		JComboBox<String> optList = new JComboBox<>(options);
		optList.setSelectedItem("MD5");
		optPanel.add(optLabel);
		optPanel.add(optList);

		top.add(optPanel);

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
					int userChoice = fileChooser.showOpenDialog(HashFileEP.this);
					if (userChoice == JFileChooser.APPROVE_OPTION) {
						srcFile = fileChooser.getSelectedFile();
						currentDir = fileChooser.getCurrentDirectory();

						ptTextField.setText(srcFile.getName());
						fileUploaded = true;
					}
				}

				// Encryption
				if (e.getActionCommand().equals("Encrypt")) {
					if (ptTextField.getText().isBlank()) {
						JOptionPane.showMessageDialog(null, "There is no plain text", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {
						JFileChooser dirChooser = new JFileChooser(currentDir);
						dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						int userChoice = dirChooser.showSaveDialog(HashFileEP.this);
						if (userChoice == JFileChooser.APPROVE_OPTION) {
							destDir = dirChooser.getSelectedFile();
							currentDir = dirChooser.getCurrentDirectory();

							// Encrypting
							try {
								Hash hash = new Hash((String) optList.getSelectedItem());
								String encryptedText = hash.hashFile(srcFile.getAbsolutePath());

								// Show result to Text Area
								txtArea.setText("Result");
								txtArea.append("\n" + encryptedText);

								// Create encrypted file
								File destFile = new File(destDir.getAbsolutePath() + "\\HashFile.txt");
								FileWriter fw = new FileWriter(destFile);
								PrintWriter pw = new PrintWriter(fw);
								pw.println(encryptedText);
								pw.close();
								fw.close();

							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
					}
				}
			}
		};

		chooseFile.addActionListener(buttonListener);
		encrypt.addActionListener(buttonListener);

		chooseFile.setActionCommand("Choose File");
		encrypt.setActionCommand("Encrypt");
	}
}