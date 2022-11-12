package EncryptMethod;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Affine {
	final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	int keyA1, keyA2, keyB;
	int[] keyPair = new int[2];
	String encryptedString, decryptedString;

	public String getEncryptedString() {
		return encryptedString;
	}

	public String getDecryptedString() {
		return decryptedString;
	}

	/**
	 * Tạo khóa
	 * @return int[] keyPair
	 */
	public int[] createKey() throws IOException {
		Random rd = new Random();
		while (keyA1 == 0 || this.isPrime(keyA1) != true) {
			keyA1 = rd.nextInt(alphabet.length() / 2);
			keyPair[0] = keyA1;
		}
		keyB = rd.nextInt(10);
		keyPair[1] = keyB;

		return keyPair;
	}

	private boolean isPrime(int number) {
		if (number < 2) {
			return false;
		}
		for (int i = 3; i < number; i++) {
			if (number % i == 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Đọc khóa
	 * @return int[] keyPair
	 * @throws IOException
	 */
	public int[] readKey(File srcFile) throws IOException {
		FileReader fr = new FileReader(srcFile);
		BufferedReader br = new BufferedReader(fr);
		String keyString = br.readLine();
		String[] stringArray = keyString.replace("\\[", "").replace("]", "").split(", ");
		keyPair[0] = Integer.valueOf(stringArray[0]);
		keyPair[1] = Integer.valueOf(stringArray[1]);
		br.close();
		
		return keyPair;
	}

	/**
	 *  Mã hóa Affine
	 * @param source
	 * @param input
	 * @param destDir
	 * @throws Exception
	 */
	public void encrypt(String source, int[] input, File destDir) throws Exception {
		String plainText;
		File file = new File(source);

		// Check if source is file path or not
		if (file.isFile()) {
			Path path = Path.of(source);
			plainText = Files.readString(path);
		} else {
			plainText = source;
		}

		// Encrypting
		int count, number, length = alphabet.length(), half = alphabet.length() / 2;
		encryptedString = "";
		keyPair = input;
		keyA1 = keyPair[0];
		keyB = keyPair[1];
		for (int i = 0; i < plainText.length(); i++) {
			count = 0;
			for (int j = 0; j < alphabet.length(); j++) {
				if (plainText.charAt(i) == alphabet.charAt(j)) {
					if (j < half) {
						number = (keyA1 * j + keyB) % half;
						encryptedString += alphabet.charAt(number);
					} else {
						number = (keyA1 * j + keyB) % half + half;
						encryptedString += alphabet.charAt(number);
					}
				} else {
					count++;
					if (count == length) {
						encryptedString += plainText.charAt(i);
					}
				}
			}
		}

		// Create encrypted file
		try {
			File destFile = new File(destDir.getAbsolutePath() + "\\AffineEncrypted.txt");
			FileWriter fw = new FileWriter(destFile);
			PrintWriter pw = new PrintWriter(fw);
			pw.println(encryptedString);
			pw.close();
			fw.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 *  Giải mã Affine
	 * @param source
	 * @param input
	 * @param destDir
	 * @throws Exception
	 */
	public void decrypt(String source, int[] input, File destDir) throws Exception {
		String cipherText;
		File file = new File(source);

		// Check if source is file path or not
		if (file.isFile()) {
			Path path = Path.of(source);
			cipherText = Files.readString(path);
		} else {
			cipherText = source;
		}

		int count, number, total, negative, positive;
		int length = alphabet.length(), half = alphabet.length() / 2;
		decryptedString = "";
		keyPair = input;
		keyA2 = this.findKeyA2(keyPair[0]);
		keyB = keyPair[1];
		for (int i = 0; i < cipherText.length(); i++) {
			count = 0;
			for (int j = 0; j < alphabet.length(); j++) {
				if (cipherText.charAt(i) == alphabet.charAt(j)) {
					if (j < half) {
						total = keyA2 * (j - keyB);
						if (total < 0) {
							negative = total / half;
							positive = negative * (-1) + 1;
							number = total + half * positive;
							decryptedString += alphabet.charAt(number);
						} else {
							number = total % half;
							decryptedString += alphabet.charAt(number);
						}
					} else {
						total = keyA2 * ((j - half) - keyB);
						if (total < 0) {
							negative = total / half;
							positive = negative * (-1) + 1;
							number = total + half * positive + half;
							decryptedString += alphabet.charAt(number);
						} else {
							number = total % half + half;
							decryptedString += alphabet.charAt(number);
						}
					}
				} else {
					count++;
					if (count == length) {
						decryptedString += cipherText.charAt(i);
					}
				}
			}
		}

		// Create decrypted file
		try {
			File destFile = new File(destDir.getAbsolutePath() + "\\AffineDecrypted.txt");
			FileWriter fw = new FileWriter(destFile);
			PrintWriter pw = new PrintWriter(fw);
			pw.println(decryptedString);
			pw.close();
			fw.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private int findKeyA2(int keyA1) {
		int result = 0, half = alphabet.length() / 2;
		for (int i = 0; i < half; i++) {
			if ((i * keyA1) % half == 1) {
				result = i;
			}
		}
		return result;
	}
}