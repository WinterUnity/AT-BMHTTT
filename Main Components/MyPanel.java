package Main;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import EncryptPanel.*;
import DecryptPanel.*;
import EncryptMethod.AffineFile;

public class MyPanel extends JPanel{
	JPanel mainPanel, defaultPanel,
			caesarEncryptPanel, substitutionEncryptPanel, affineEncryptPanel, vigenereEncryptPanel,
			caesarDecryptPanel, substitutionDecryptPanel, affineDecryptPanel, vigenereDecryptPanel,
			caesarEncryptFilePanel, substitutionEncryptFilePanel, affineEncryptFilePanel, vigenereEncryptFilePanel,
			caesarDecryptFilePanel, substitutionDecryptFilePanel, affineDecryptFilePanel, vigenereDecryptFilePanel,
			aesEncryptFilePanel, rsaEncryptFilePanel,
			aesDecryptFilePanel, rsaDecryptFllepanel;
	
	CardLayout cardLayout;
	
	public MyPanel() {
		setLayout(new BorderLayout());
		
		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);
		
		defaultPanel = new DefaultPanel();
		caesarEncryptPanel = new CaesarEncryptPanel();
		substitutionEncryptPanel = new SubstitutionEncryptPanel();
		affineEncryptPanel = new AffineEncryptPanel();
		vigenereEncryptPanel = new VigenereEncryptPanel();
		
		caesarDecryptPanel = new CaesarDecryptPanel();
		substitutionDecryptPanel = new SubstitutionDecryptPanel();
		affineDecryptPanel = new AffineDecryptPanel();
		vigenereDecryptPanel = new VigenereDecryptPanel();
		
		caesarEncryptFilePanel = new CaesarEncryptFilePanel();
		affineEncryptFilePanel = new AffineEncryptFilePanel();
		vigenereEncryptFilePanel = new VigenereEncryptFilePanel();
		aesEncryptFilePanel = new AESEncryptFilePanel();
		
		caesarDecryptFilePanel = new CaesarDecryptFilePanel();
		affineDecryptFilePanel = new AffineDecryptFilePanel();
		vigenereDecryptFilePanel = new VigenereDecryptFilePanel();
		aesDecryptFilePanel = new AESDecryptFilePanel();
		
		
		
		mainPanel.add("Home", defaultPanel);
		mainPanel.add("CaesarEncrypt", caesarEncryptPanel);
		mainPanel.add("SubstitutionEncrypt", substitutionEncryptPanel);
		mainPanel.add("AffineEncrypt", affineEncryptPanel);
		mainPanel.add("VigenereEncrypt", vigenereEncryptPanel);
		
		mainPanel.add("CaesarDecrypt", caesarDecryptPanel);
		mainPanel.add("SubstitutionDecrypt", substitutionDecryptPanel);
		mainPanel.add("AffineDecrypt", affineDecryptPanel);
		mainPanel.add("VigenereDecrypt", vigenereDecryptPanel);
		
		mainPanel.add("CaesarEncryptFile", caesarEncryptFilePanel);
		mainPanel.add("AffineEncryptFile", affineEncryptFilePanel);
		mainPanel.add("VigenereEncryptFile", vigenereEncryptFilePanel);
		mainPanel.add("AESEncryptFile", aesEncryptFilePanel);
		
		mainPanel.add("CaesarDecryptFile", caesarDecryptFilePanel);
		mainPanel.add("AffineDecryptFile", affineDecryptFilePanel);
		mainPanel.add("VigenereDecryptFile", vigenereDecryptFilePanel);
		mainPanel.add("AESDecryptFile", aesDecryptFilePanel);
		
		add(mainPanel, BorderLayout.CENTER);
	}

	public void change(String actionCommand) {
		cardLayout.show(mainPanel, actionCommand);
	}
}