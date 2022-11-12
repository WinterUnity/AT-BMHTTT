package DecryptPanel;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.border.Border;

import EncryptMethod.AffineFile;

public class AffineDecryptFilePanel extends JPanel {
	JPanel top, mid, bot, subPanel;
	File textFile, keyFile, destDir;
	int[] affineKey;
	boolean fileUploaded, keyUploaded;

	public AffineDecryptFilePanel() {
		setLayout(new BorderLayout());

		/*
		 * Top Layout
		 */
		top = new JPanel();
		top.setLayout(new GridLayout(5, 1));
		Border topBD = BorderFactory.createLineBorder(Color.blue);
		top.setBorder(BorderFactory.createTitledBorder(topBD, "Thông tin - Giải mã Affine"));

		// CipherText
		JPanel ctPanel = new JPanel();
		ctPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		JLabel ctLabel = new JLabel("Cipher Text");
		JTextField ctTextField = new JTextField(30);
		ctTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		ctPanel.add(ctLabel);
		ctPanel.add(ctTextField);

		top.add(ctPanel);

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
					int userChoice = textFileChooser.showOpenDialog(AffineDecryptFilePanel.this);
					if (userChoice == JFileChooser.APPROVE_OPTION) {
						textFile = textFileChooser.getSelectedFile();
						ctTextField.setText(textFile.getName());
						fileUploaded = true;
					}
				}

				// Load key
				if (e.getActionCommand().equals("Load key")) {
					JFileChooser keyFileChooser = new JFileChooser("D:\\");
					int userChoice = keyFileChooser.showOpenDialog(AffineDecryptFilePanel.this);
					if (userChoice == JFileChooser.APPROVE_OPTION) {
						keyFile = keyFileChooser.getSelectedFile();

						AffineFile affinefile = new AffineFile();
						try {
							affineKey = affinefile.readKey(keyFile);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						keyATextField.setText(String.valueOf(affineKey[0]));
						keyBTextField.setText(String.valueOf(affineKey[1]));
						keyUploaded = true;
					}

				}

				// Decryption
				if (e.getActionCommand().equals("Decrypt")) {
					if (ctTextField.getText().isBlank() || keyATextField.getText().isBlank()
							|| keyBTextField.getText().isBlank()) {
						JOptionPane.showMessageDialog(null, "There is no plain text or key", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {
						if (fileUploaded == true) {
							JOptionPane.showMessageDialog(null, "There is no plain text or key", "Error",
									JOptionPane.ERROR_MESSAGE);
						} else {
							// Choose Directory to save decrypted file
							JFileChooser dirChooser = new JFileChooser("D:\\");
							dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
							int userChoice = dirChooser.showSaveDialog(AffineDecryptFilePanel.this);
							if (userChoice == JFileChooser.APPROVE_OPTION) {
								destDir = dirChooser.getSelectedFile();

								int[] key = new int[2];

								// Manual or not

								if (keyUploaded == true) {
									key = affineKey;
								} else {
									key[0] = Integer.parseInt(keyATextField.getText());
									key[1] = Integer.parseInt(keyBTextField.getText());
								}

								// Decrypting
								AffineFile affineFile = new AffineFile();
								try {
									affineFile.decrypt(textFile, key, destDir);

									// Show result to Text Area
									txtArea.setText("Result");
									txtArea.append("File decrypted successfully");
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
