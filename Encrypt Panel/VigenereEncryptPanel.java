package EncryptPanel;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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
import EncryptMethod.Vigenere;

public class VigenereEncryptPanel extends JPanel {
	JPanel top, mid, bot, subPanel;
	File srcFile, keyFile, destDir;
	int[] vigenereKey;
	boolean fileUploaded, keyUploaded, keyCreated;

	public VigenereEncryptPanel() {
		setLayout(new BorderLayout());

		/*
		 * Top Layout
		 */
		top = new JPanel();
		top.setLayout(new GridLayout(5, 1));
		Border topBD = BorderFactory.createLineBorder(Color.blue);
		top.setBorder(BorderFactory.createTitledBorder(topBD, "Thông tin - Mã hóa Vigenere"));

		// PlainText
		JPanel ptPanel = new JPanel();
		ptPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		JLabel ptLabel = new JLabel("Plain Text");
		JTextField ptTextField = new JTextField(30);
		ptTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		ptPanel.add(ptLabel);
		ptPanel.add(ptTextField);

		top.add(ptPanel);

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
//		txtArea.setText(txtArea.getText() + "STT\t\tTen mon hoc\t\t\t\tDiem\n");
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
					int userChoice = fileChooser.showOpenDialog(VigenereEncryptPanel.this);
					if (userChoice == JFileChooser.APPROVE_OPTION) {
						srcFile = fileChooser.getSelectedFile();
						ptTextField.setText(srcFile.getName());
						fileUploaded = true;
					}
				}
				
				// Upload Key
				if (e.getActionCommand().equals("Upload Key")) {
					JFileChooser keyFileChooser = new JFileChooser("D:\\");
					int userChoice = keyFileChooser.showOpenDialog(VigenereEncryptPanel.this);
					if (userChoice == JFileChooser.APPROVE_OPTION) {
						keyFile = keyFileChooser.getSelectedFile();
						
						Vigenere vigenere = new Vigenere();
						
						try {
							FileReader fr = new FileReader(keyFile);
							BufferedReader br = new BufferedReader(fr);
							String keyString = br.readLine();
							if(keyString.contains("\\[")) {
								if(keyString.contains("]")) {
									vigenereKey = vigenere.readKeyArray(keyFile);
								}
							} else {
								vigenereKey = vigenere.readKeyString(keyFile);
							}
							keyTextField.setText(keyString);
							br.close();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						
						keyUploaded = true;
					}
				}

				// Create key
				if (e.getActionCommand().equals("Create key")) {
					Vigenere vigenere = new Vigenere();
					vigenereKey = vigenere.createKeyBaseOnSize(10);
					String keyString = "[";
					for (int i = 0; i < vigenereKey.length; i++) {
						if (i == vigenereKey.length - 1) {
							keyString += vigenereKey[i] + "]";
						} else {
							keyString += vigenereKey[i] + ", ";
						}
					}
					keyTextField.setText(keyString);
					keyCreated = true;
				}

				// Encryption
				if (e.getActionCommand().equals("Encrypt")) {
					if (ptTextField.getText().isBlank() || keyTextField.getText().isBlank()) {
						JOptionPane.showMessageDialog(null, "There is no plain text or key", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {
						JFileChooser dirChooser = new JFileChooser("D:\\");
						dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						int userChoice = dirChooser.showSaveDialog(VigenereEncryptPanel.this);
						if (userChoice == JFileChooser.APPROVE_OPTION) {
							destDir = dirChooser.getSelectedFile();

							Vigenere vigenere = new Vigenere();
							String text = "";
							int[] key;

							// Manual or not
							if (fileUploaded == true) {
								text = srcFile.getAbsolutePath();
							} else {
								text = ptTextField.getText();
							}

							if (keyCreated == true || keyUploaded == true) {
								key = vigenereKey;
							} else {
								String keyString = keyTextField.getText();
								if(vigenere.isNumeric(keyString) == true) {
									key = vigenere.createKeyBaseOnSize(Integer.parseInt(keyString));
								} else {
									key = vigenere.createKeyBaseOnKeyWord(keyString);
								}
							}

							// Encrypting
							try {
								vigenere.encrypt(text, key, destDir);

								// Show result to Text Area
								txtArea.setText(vigenere.getEncryptedString());

								// Print key file
								String keyPrint = "";
								for (int i = 0; i < vigenereKey.length; i++) {
									if (i == vigenereKey.length - 1) {
										keyPrint += vigenereKey[i] + "]";
									} else {
										if(i == 0) {
											keyPrint += "[" + vigenereKey[0];
										} else {
											keyPrint += vigenereKey[i] + ", ";
										}
									}
								}
								File destFile = new File(destDir.getAbsolutePath() + "\\VigenereKey.txt");
								FileWriter fw = new FileWriter(destFile);
								PrintWriter pw = new PrintWriter(fw);
								pw.println(keyPrint);
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
		uploadkey.addActionListener(buttonListener);
		createKey.addActionListener(buttonListener);
		encrypt.addActionListener(buttonListener);

		chooseFile.setActionCommand("Choose File");
		uploadkey.setActionCommand("Upload Key");
		createKey.setActionCommand("Create key");
		encrypt.setActionCommand("Encrypt");
	}
}
