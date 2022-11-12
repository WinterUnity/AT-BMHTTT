package EncryptPanel;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.border.Border;

import EncryptMethod.AffineFile;

public class AffineEncryptFilePanel extends JPanel {
	JPanel top, mid, bot, subPanel;
	File srcFile, destDir;
	int[] affineKey;
	boolean fileUploaded, keyCreated;

	public AffineEncryptFilePanel() {
		setLayout(new BorderLayout());

		/*
		 * Top Layout
		 */
		top = new JPanel();
		top.setLayout(new GridLayout(5, 1));
		Border topBD = BorderFactory.createLineBorder(Color.blue);
		top.setBorder(BorderFactory.createTitledBorder(topBD, "Thông tin - Mã hóa Affine - File"));

		// PlainText
		JPanel ptPanel = new JPanel();
		ptPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		JLabel ptLabel = new JLabel("Plain Text");
		JTextField ptTextField = new JTextField(30);
		ptTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		ptPanel.add(ptLabel);
		ptPanel.add(ptTextField);

		top.add(ptPanel);

		// KeyA
		JPanel keyAPanel = new JPanel();
		keyAPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		JLabel keyALabel = new JLabel("KeyA");
		JTextField keyATextField = new JTextField(30);
		keyATextField.setHorizontalAlignment(SwingConstants.RIGHT);
		keyAPanel.add(keyALabel);
		keyAPanel.add(keyATextField);

		top.add(keyAPanel);

		// KeyB
		JPanel keyBPanel = new JPanel();
		keyBPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		JLabel keyBLabel = new JLabel("KeyB");
		JTextField keyBTextField = new JTextField(30);
		keyBTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		keyBPanel.add(keyBLabel);
		keyBPanel.add(keyBTextField);

		top.add(keyBPanel);

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
					JFileChooser fileChooser = new JFileChooser("D:\\");
					int userChoice = fileChooser.showOpenDialog(AffineEncryptFilePanel.this);
					if (userChoice == JFileChooser.APPROVE_OPTION) {
						srcFile = fileChooser.getSelectedFile();
						ptTextField.setText(srcFile.getName());
						fileUploaded = true;
					}
				}

				// Create key
				if (e.getActionCommand().equals("Create key")) {
					AffineFile affine = new AffineFile();
					try {
						affineKey = affine.createKey();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					keyATextField.setText(String.valueOf(affineKey[0]));
					keyBTextField.setText(String.valueOf(affineKey[1]));
					keyCreated = true;
				}

				// Encryption
				if (e.getActionCommand().equals("Encrypt")) {
					if (ptTextField.getText().isBlank() || keyATextField.getText().isBlank()
							|| keyBTextField.getText().isBlank()) {
						JOptionPane.showMessageDialog(null, "There is no plain text or key", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {
						if (fileUploaded != true) {
							JOptionPane.showMessageDialog(null, "File not uploaded", "Error",
									JOptionPane.ERROR_MESSAGE);
						} else {
							JFileChooser dirChooser = new JFileChooser("D:\\");
							dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
							int userChoice = dirChooser.showSaveDialog(AffineEncryptFilePanel.this);
							if (userChoice == JFileChooser.APPROVE_OPTION) {
								destDir = dirChooser.getSelectedFile();

								int[] key = new int[2];

								// Manual or not
								if (keyCreated == true) {
									key = affineKey;
								} else {
									key[0] = Integer.parseInt(keyATextField.getText());
									key[1] = Integer.parseInt(keyBTextField.getText());
								}

								// Encrypting
								AffineFile affineFile = new AffineFile();
								try {
									affineFile.encrypt(srcFile, key, destDir);

									// Show result to Text Area
									txtArea.setText("Encrypt successfully to " + destDir.getAbsolutePath());

									// Print key file
									File destFile = new File(destDir.getAbsolutePath() + "\\AffineKey.txt");
									FileWriter fw = new FileWriter(destFile);
									PrintWriter pw = new PrintWriter(fw);
									pw.println("[" + affineKey[0] + ", " + affineKey[1] + "]");
									pw.close();
									fw.close();
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
		createKey.addActionListener(buttonListener);
		encrypt.addActionListener(buttonListener);

		chooseFile.setActionCommand("Choose File");
		createKey.setActionCommand("Create key");
		encrypt.setActionCommand("Encrypt");
	}
}
