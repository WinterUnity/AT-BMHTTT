package EncryptMethod;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class VigenereFile {
	final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	int[] key;
	byte[] encryptedBytes, decryptedBytes;

	/**
	 * Tạo khóa dựa theo kích thước khóa
	 * 
	 * @param size
	 * @return int[] key
	 */
	public int[] createKeyBaseOnSize(int size) {
		key = new int[size];
		Random rd = new Random();
		int number;
		for (int i = 0; i < key.length; i++) {
			number = rd.nextInt(alphabet.length() / 2);
			key[i] = number;
		}

		return key;
	}

	/**
	 * Tạo khóa dựa theo tên của khóa
	 * 
	 * @param keyWord
	 * @return int[] key
	 */
	public int[] createKeyBaseOnKeyWord(String keyWord) {
		key = new int[keyWord.length()];
		for (int i = 0; i < keyWord.length(); i++) {
			for (int j = 0; j < alphabet.length(); j++) {
				if (keyWord.charAt(i) == alphabet.charAt(j)) {
					key[i] = j;
				}
			}
		}

		return key;
	}

	/**
	 * Đọc khóa
	 * 
	 * @param srcFile
	 * @return int[] key
	 * @throws Exception
	 */
	public int[] readKeyArray(File srcFile) throws Exception {
		FileReader fr = new FileReader(srcFile);
		BufferedReader br = new BufferedReader(fr);
		String keyString = br.readLine();
		String[] stringArray = keyString.replace("[", "").replace("]", "").split(", ");
		key = new int[stringArray.length];
		for (int i = 0; i < stringArray.length; i++) {
			key[i] = Integer.valueOf(stringArray[i]);
		}
		br.close();
		
		return key;
	}

	public int[] readKeyString(File srcFile) throws IOException {
		FileReader fr = new FileReader(srcFile);
		BufferedReader br = new BufferedReader(fr);
		String keyString = br.readLine();
		key = createKeyBaseOnKeyWord(keyString);
		br.close();
		
		return key;
	}

	/**
	 * Mã hóa VigenereFile
	 * 
	 * @param source
	 * @param input
	 * @param destDir
	 * @throws Exception
	 */
	public void encrypt(File srcFile, int[] input, File destDir) throws Exception {
		byte[] fileContent = Files.readAllBytes(srcFile.toPath());

		// Check if source is file path or not
		if (srcFile.isFile()) {
			// Encrypting
			encryptedBytes = new byte[fileContent.length];
			key = input;
			int count = 0;

			for (int i = 0; i < fileContent.length; i++) {
				if (count % key.length == 0) {
					count = 0;
				}
				fileContent[i] = (byte) (fileContent[i] + key[count]);
				count++;
			}

			// Create encrypted file
			try {
				File destFile = new File(
						destDir.getAbsolutePath() + "\\VigenereEncryptedFile" + getFileExtension(srcFile));
				FileOutputStream fos = new FileOutputStream(destFile);
				fos.write(fileContent);
				fos.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			System.out.println("This is not a file");
		}
	}

	// Giải mã VigenereFile
	public void decrypt(File srcFile, int[] input, File destDir) throws Exception {
		byte[] fileContent = Files.readAllBytes(srcFile.toPath());

		// Check if source is file path or not
		if (srcFile.isFile()) {
			// Decrypting
			decryptedBytes = new byte[fileContent.length];
			key = input;
			int count = 0;

			for (int i = 0; i < fileContent.length; i++) {
				if (count % key.length == 0) {
					count = 0;
				}
				fileContent[i] = (byte) (fileContent[i] - key[count]);
				count++;
			}

			// Create decrypted file
			try {
				File destFile = new File(
						destDir.getAbsolutePath() + "\\VigenereDecryptedFile" + getFileExtension(srcFile));
				FileOutputStream fos = new FileOutputStream(destFile);
				fos.write(fileContent);
				fos.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			System.out.println("This is not a file");
		}
	}

	private int findKeyA2(int key) {
		int result = 0, half = alphabet.length() / 2;
		for (int i = 0; i < half; i++) {
			if ((i * key) % half == 1) {
				result = i;
			}
		}
		return result;
	}

	public boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			int number = Integer.parseInt(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public String getFileExtension(File file) {
		String fileName = file.getName();

		int i = fileName.lastIndexOf(".");
		String fileExtension = fileName.substring(i);

		return fileExtension;
	}
}