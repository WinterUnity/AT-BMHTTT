package Main;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import EncryptPanel.*;
import DecryptPanel.*;

public class MyPanel extends JPanel{
	JPanel mainPanel, defaultPanel,
			caesarEncryptPanel, substitutionEncryptPanel, affineEncryptPanel, vigenereEncryptPanel,
			caesarDecryptPanel, substitutionDecryptPanel, affineDecryptPanel, vigenereDecryptPanel,
			caesarEncryptFilePanel, substitutionEncryptFilePanel, affineEncryptFilePanel, vigenereEncryptFilePanel,
			caesarDecryptFilePanel, substitutionDecryptFilePanel, affineDecryptFilePanel, vigenereDecryptFilePanel;
	
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
		
		caesarDecryptFilePanel = new CaesarDecryptFilePanel();
		
		
		
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
		
		mainPanel.add("CaesarDecryptFile", caesarDecryptFilePanel);
		
		add(mainPanel, BorderLayout.CENTER);
	}

	public void change(String actionCommand) {
		cardLayout.show(mainPanel, actionCommand);
	}
}