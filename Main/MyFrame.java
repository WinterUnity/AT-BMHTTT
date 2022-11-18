package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MyFrame extends JFrame {
	public MyFrame() {

		// Frame
		setTitle("Tool mã hóa code");
		setSize(800, 500);
		MyPanel myPanel = new MyPanel();

		setJMenuBar(createMenuBar(myPanel));
		getContentPane().add(myPanel);

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
	}

	public JMenuBar createMenuBar(MyPanel myPanel) {
		// MenuBar
		JMenuBar menuBar = new JMenuBar();

		ActionListener itemAction = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				myPanel.change(e.getActionCommand());
			}
		};

		/*
		 * File menu
		 */
		JMenu fileMenu = new JMenu("File");
		fileMenu.setFont(new Font(null, Font.BOLD, 12));
		menuBar.add(fileMenu);

		// Default menu item
		JMenuItem defaultItem = new JMenuItem("Home");
		defaultItem.setMnemonic(KeyEvent.VK_H);
		defaultItem.setActionCommand("Home");
		defaultItem.addActionListener(itemAction);
		fileMenu.add(defaultItem);

		// Exit menu item
		fileMenu.addSeparator();
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.setMnemonic(KeyEvent.VK_E);
		exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		fileMenu.add(exitItem);

		/*
		 * Encrypt Menu
		 */
		JMenu encryptMenu = new JMenu("Mã hóa văn bản");
		menuBar.add(encryptMenu);

		// Caesar encrypt menu item
		JMenuItem caesarEncryptItem = new JMenuItem("Mã hóa Caesar");
		caesarEncryptItem.setMnemonic(KeyEvent.VK_C);
		caesarEncryptItem.setActionCommand("CaesarEncrypt");
		caesarEncryptItem.addActionListener(itemAction);
		encryptMenu.add(caesarEncryptItem);

		// Substitution encrypt menu item
		encryptMenu.addSeparator();
		JMenuItem subEncrptItem = new JMenuItem("Mã hóa Substitution");
		subEncrptItem.setMnemonic(KeyEvent.VK_S);
		subEncrptItem.setActionCommand("SubstitutionEncrypt");
		subEncrptItem.addActionListener(itemAction);
		encryptMenu.add(subEncrptItem);

		// Affine encrypt menu item
		encryptMenu.addSeparator();
		JMenuItem affineEncryptItem = new JMenuItem("Mã hóa Affine");
		affineEncryptItem.setMnemonic(KeyEvent.VK_A);
		affineEncryptItem.setDisplayedMnemonicIndex(7);
		affineEncryptItem.setActionCommand("AffineEncrypt");
		affineEncryptItem.addActionListener(itemAction);
		encryptMenu.add(affineEncryptItem);

		// Vigenere encrypt menu item
		encryptMenu.addSeparator();
		JMenuItem vigenereEncryptItem = new JMenuItem("Mã hóa Vigenere");
		vigenereEncryptItem.setMnemonic(KeyEvent.VK_V);
		vigenereEncryptItem.setActionCommand("VigenereEncrypt");
		vigenereEncryptItem.addActionListener(itemAction);
		encryptMenu.add(vigenereEncryptItem);

		// Hill encrypt menu item
		encryptMenu.addSeparator();
		JMenuItem hillEncryptItem = new JMenuItem("Mã hóa Hill");
		hillEncryptItem.setMnemonic(KeyEvent.VK_H);
		hillEncryptItem.setActionCommand("HillEncrypt");
		hillEncryptItem.addActionListener(itemAction);
		encryptMenu.add(hillEncryptItem);

		// Permutation encrypt menu item
		encryptMenu.addSeparator();
		JMenuItem permutationEncryptItem = new JMenuItem("Mã hóa Permutation");
		permutationEncryptItem.setMnemonic(KeyEvent.VK_P);
		permutationEncryptItem.setActionCommand("PermutationEncrypt");
		permutationEncryptItem.addActionListener(itemAction);
		encryptMenu.add(permutationEncryptItem);

		// RSA encrypt menu item
		encryptMenu.addSeparator();
		JMenuItem rsaEncryptItem = new JMenuItem("Mã hóa RSA");
		rsaEncryptItem.setActionCommand("RSAEncrypt");
		rsaEncryptItem.addActionListener(itemAction);
		encryptMenu.add(rsaEncryptItem);

		// Hash encrypt menu item
		encryptMenu.addSeparator();
		JMenuItem hashEncryptItem = new JMenuItem("Mã hóa Hash");
		hashEncryptItem.setActionCommand("HashEncrypt");
		hashEncryptItem.addActionListener(itemAction);
		encryptMenu.add(hashEncryptItem);

		/*
		 * Decrypt Menu
		 */
		JMenu decryptMenu = new JMenu("Giải mã văn bản");
		menuBar.add(decryptMenu);

		// Caesar decrypt menu item
		JMenuItem caesarDecryptItem = new JMenuItem("Giải mã Caesar");
		caesarDecryptItem.setMnemonic(KeyEvent.VK_C);
		caesarDecryptItem.setActionCommand("CaesarDecrypt");
		caesarDecryptItem.addActionListener(itemAction);
		decryptMenu.add(caesarDecryptItem);

		// Substitution decrypt menu item
		decryptMenu.addSeparator();
		JMenuItem subDecryptItem = new JMenuItem("Giải mã Substitution");
		subDecryptItem.setMnemonic(KeyEvent.VK_S);
		subDecryptItem.setActionCommand("SubstitutionDecrypt");
		subDecryptItem.addActionListener(itemAction);
		decryptMenu.add(subDecryptItem);

		// Affine decrypt menu item
		decryptMenu.addSeparator();
		JMenuItem affineDecryptItem = new JMenuItem("Giải mã Affine");
		affineDecryptItem.setMnemonic(KeyEvent.VK_A);
		affineDecryptItem.setActionCommand("AffineDecrypt");
		affineDecryptItem.addActionListener(itemAction);
		decryptMenu.add(affineDecryptItem);

		// Vigenere decrypt menu item
		decryptMenu.addSeparator();
		JMenuItem vigenereDecryptItem = new JMenuItem("Giải mã Vigenere");
		vigenereDecryptItem.setMnemonic(KeyEvent.VK_V);
		vigenereDecryptItem.setActionCommand("VigenereDecrypt");
		vigenereDecryptItem.addActionListener(itemAction);
		decryptMenu.add(vigenereDecryptItem);

		// Hill decrypt menu item
		decryptMenu.addSeparator();
		JMenuItem hillDecryptItem = new JMenuItem("Giải mã Hill");
		hillDecryptItem.setMnemonic(KeyEvent.VK_H);
		hillDecryptItem.setActionCommand("HillDecrypt");
		hillDecryptItem.addActionListener(itemAction);
		decryptMenu.add(hillDecryptItem);

		// Permutation decrypt menu item
		decryptMenu.addSeparator();
		JMenuItem permutationDecryptItem = new JMenuItem("Giải mã Permutation");
		permutationDecryptItem.setMnemonic(KeyEvent.VK_P);
		permutationDecryptItem.setActionCommand("PermutationDecrypt");
		permutationDecryptItem.addActionListener(itemAction);
		decryptMenu.add(permutationDecryptItem);

		// RSA decrypt menu item
		decryptMenu.addSeparator();
		JMenuItem rsaDecryptItem = new JMenuItem("Giải mã RSA");
		rsaDecryptItem.setActionCommand("RSADecrypt");
		rsaDecryptItem.addActionListener(itemAction);
		decryptMenu.add(rsaDecryptItem);

		/*
		 * Encrypt File menu
		 */
		JMenu encryptFileMenu = new JMenu("Mã hóa File");
		menuBar.add(encryptFileMenu);

		// Caesar Encrypt File menu item
		JMenuItem caesarEncryptFileItem = new JMenuItem("Mã hóa Caesar");
		caesarEncryptFileItem.setMnemonic(KeyEvent.VK_C);
		caesarEncryptFileItem.setActionCommand("CaesarEncryptFile");
		caesarEncryptFileItem.addActionListener(itemAction);
		encryptFileMenu.add(caesarEncryptFileItem);

		// Affine encrypt file menu item
		encryptFileMenu.addSeparator();
		JMenuItem affineEncryptFileItem = new JMenuItem("Mã hóa Affine");
		affineEncryptFileItem.setMnemonic(KeyEvent.VK_A);
		affineEncryptFileItem.setDisplayedMnemonicIndex(7);
		affineEncryptFileItem.setActionCommand("AffineEncryptFile");
		affineEncryptFileItem.addActionListener(itemAction);
		encryptFileMenu.add(affineEncryptFileItem);

		// Vigenere encrypt file menu item
		encryptFileMenu.addSeparator();
		JMenuItem vigenereEncryptFileItem = new JMenuItem("Mã hóa Vigenere");
		vigenereEncryptFileItem.setMnemonic(KeyEvent.VK_V);
		vigenereEncryptFileItem.setActionCommand("VigenereEncryptFile");
		vigenereEncryptFileItem.addActionListener(itemAction);
		encryptFileMenu.add(vigenereEncryptFileItem);

		// Hill encrypt file menu item
		encryptFileMenu.addSeparator();
		JMenuItem hillEncryptFileItem = new JMenuItem("Mã hóa Hill");
		hillEncryptFileItem.setMnemonic(KeyEvent.VK_H);
		hillEncryptFileItem.setActionCommand("HillEncryptFile");
		hillEncryptFileItem.addActionListener(itemAction);
		encryptFileMenu.add(hillEncryptFileItem);

		// Permutation encrypt file menu item
		encryptFileMenu.addSeparator();
		JMenuItem permutationEncryptFileItem = new JMenuItem("Mã hóa Permutation");
		permutationEncryptFileItem.setMnemonic(KeyEvent.VK_P);
		permutationEncryptFileItem.setActionCommand("PermutationEncryptFile");
		permutationEncryptFileItem.addActionListener(itemAction);
		encryptFileMenu.add(permutationEncryptFileItem);

		// AES encrypt file menu item
		encryptFileMenu.addSeparator();
		JMenuItem aesEncryptFileItem = new JMenuItem("Mã hóa AES");
		aesEncryptFileItem.setActionCommand("AESEncryptFile");
		aesEncryptFileItem.addActionListener(itemAction);
		encryptFileMenu.add(aesEncryptFileItem);

		// RSA encrypt file menu item
		encryptFileMenu.addSeparator();
		JMenuItem rsaEncryptFileItem = new JMenuItem("Mã hóa RSA");
		rsaEncryptFileItem.setActionCommand("RSAEncryptFile");
		rsaEncryptFileItem.addActionListener(itemAction);
		encryptFileMenu.add(rsaEncryptFileItem);

		// Hash encrypt file menu item
		encryptFileMenu.addSeparator();
		JMenuItem hashEncryptFileItem = new JMenuItem("Mã hóa Hash");
		hashEncryptFileItem.setActionCommand("HashEncryptFile");
		hashEncryptFileItem.addActionListener(itemAction);
		encryptFileMenu.add(hashEncryptFileItem);

		/*
		 * Decrypt File Menu
		 */
		JMenu decryptFileMenu = new JMenu("Giải mã File");
		menuBar.add(decryptFileMenu);

		// Caesar decrypt file menu item
		JMenuItem caesarDecryptFileItem = new JMenuItem("Giải mã Caesar");
		caesarDecryptFileItem.setMnemonic(KeyEvent.VK_C);
		caesarDecryptFileItem.setActionCommand("CaesarDecryptFile");
		caesarDecryptFileItem.addActionListener(itemAction);
		decryptFileMenu.add(caesarDecryptFileItem);

		// Affine decrypt file menu item
		decryptFileMenu.addSeparator();
		JMenuItem affineDecryptFileItem = new JMenuItem("Giải mã Affine");
		affineDecryptFileItem.setMnemonic(KeyEvent.VK_A);
		affineDecryptFileItem.setActionCommand("AffineDecryptFile");
		affineDecryptFileItem.addActionListener(itemAction);
		decryptFileMenu.add(affineDecryptFileItem);

		// Vigenere decrypt file menu item
		decryptFileMenu.addSeparator();
		JMenuItem vigenereDecryptFileItem = new JMenuItem("Giải mã Vigenere");
		vigenereDecryptFileItem.setMnemonic(KeyEvent.VK_V);
		vigenereDecryptFileItem.setActionCommand("VigenereDecryptFile");
		vigenereDecryptFileItem.addActionListener(itemAction);
		decryptFileMenu.add(vigenereDecryptFileItem);

		// Hill decrypt file menu item
		decryptFileMenu.addSeparator();
		JMenuItem hillDecryptFileItem = new JMenuItem("Giải mã Hill");
		hillDecryptFileItem.setMnemonic(KeyEvent.VK_H);
		hillDecryptFileItem.setActionCommand("HillDecryptFile");
		hillDecryptFileItem.addActionListener(itemAction);
		decryptFileMenu.add(hillDecryptFileItem);

		// Permutation decrypt file menu item
		decryptFileMenu.addSeparator();
		JMenuItem permutationDecryptFileItem = new JMenuItem("Giải mã Permutation");
		permutationDecryptFileItem.setMnemonic(KeyEvent.VK_P);
		permutationDecryptFileItem.setActionCommand("PermutationDecryptFile");
		permutationDecryptFileItem.addActionListener(itemAction);
		decryptFileMenu.add(permutationDecryptFileItem);

		// AES decrypt file menu item
		decryptFileMenu.addSeparator();
		JMenuItem aesDecryptFileItem = new JMenuItem("Giải mã AES");
		aesDecryptFileItem.setActionCommand("AESDecryptFile");
		aesDecryptFileItem.addActionListener(itemAction);
		decryptFileMenu.add(aesDecryptFileItem);

		// RSA decrypt file menu item
		decryptFileMenu.addSeparator();
		JMenuItem rsaDecryptFileItem = new JMenuItem("Giải mã RSA");
		rsaDecryptFileItem.setActionCommand("RSADecryptFile");
		rsaDecryptFileItem.addActionListener(itemAction);
		decryptFileMenu.add(rsaDecryptFileItem);

		return menuBar;
	}

	public static void main(String[] args) {
		new MyFrame();
	}
}