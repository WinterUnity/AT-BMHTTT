package EncryptMethod;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Random;

public class Permutation {
	int[] key;
	String encryptedString, decryptedString;
	byte[] encryptedBytes, decryptedBytes;

	public String getEncryptedString() {
		return encryptedString;
	}

	public String getDecryptedString() {
		return decryptedString;
	}

	/**
	 * Tạo khóa
	 * 
	 * @param size
	 * @return
	 */
	public int[] createKey(int size) {
		key = new int[size];
		for (int i = 0; i < key.length; i++) {
			key[i] = i;
		}
		shuffleArray(key);

		return key;
	}

	private int[] shuffleArray(int[] array) {
		Random rd = new Random();
		int number, temp;
		for (int i = 0; i < array.length; ++i) {
			number = rd.nextInt(array.length - i);
			temp = array[array.length - 1 - i];
			array[array.length - 1 - i] = array[number];
			array[number] = temp;
		}

		return array;
	}

	/**
	 * Đọc khóa
	 * 
	 * @param srcFile
	 * @return
	 * @throws IOException
	 */
	public int[] readKey(File srcFile) throws IOException {
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

	/**
	 * Mã hóa Hoán vị - Text
	 * 
	 * @param source
	 * @param input
	 * @param destDir
	 * @throws Exception
	 */
	public void encrypt(String source, int[] input, File destDir) throws Exception {
		String plainText;
		File file = new File(source);
		key = input;

		// Check if source is file path or not
		if (file.isFile()) {
			Path path = Path.of(source);
			plainText = Files.readString(path);
		} else {
			plainText = source;
		}

		// Encrypting
		int missingLength = input.length - (plainText.length() % key.length);
		if (plainText.length() % key.length != 0) {
			for (int m = 0; m < missingLength; m++) {
				plainText += "z";
			}
		}

		String subString;
		encryptedString = "";
		for (int i = 0; i < plainText.length(); i += key.length) {
			subString = plainText.substring(i, i + key.length);
			for (int j = 0; j < key.length; j++) {
				encryptedString += subString.charAt(key[j]);
			}
		}

		if (missingLength != 0) {
			encryptedString = missingLength + encryptedString;
		}

		// Create encrypted file
		try {
			File destFile = new File(destDir.getAbsolutePath() + "\\PermutationEncrypted.txt");
			FileWriter fw = new FileWriter(destFile);
			PrintWriter pw = new PrintWriter(fw);
			pw.print(encryptedString);
			pw.close();
			fw.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Mã hóa Hoán vị - File
	 * 
	 * @param srcFile
	 * @param input
	 * @param destDir
	 * @throws Exception
	 */
	public void encryptFile(File srcFile, int[] input, File destDir) throws Exception {
		byte[] fileContent = Files.readAllBytes(srcFile.toPath());
		byte[] exFileContent = null;

		// Check if source is file path or not
		if (srcFile.isFile()) {
			// Encrypting
			key = input;
			int number = fileContent.length % key.length;
			if (number != 0) {
				exFileContent = new byte[fileContent.length + key.length - number];
				for (int i1 = 0; i1 < fileContent.length; i1++) {
					exFileContent[i1] = fileContent[i1];
				}
				for (int i2 = fileContent.length; i2 < exFileContent.length; i2++) {
					exFileContent[i2] = 0;
				}
			}

			encryptedBytes = new byte[exFileContent.length + number];
			byte[] subArray = new byte[key.length];
			for (int i = 0; i < exFileContent.length; i += key.length) {
				subArray = Arrays.copyOfRange(exFileContent, i, i + key.length);
				for (int j = 0; j < key.length; j++) {
					encryptedBytes[i + j] = subArray[key[j]];
				}
			}

			for (int i3 = exFileContent.length; i3 < encryptedBytes.length; i3++) {
				encryptedBytes[i3] = 0;
			}

			// Create encrypted file
			try {
				File destFile = new File(
						destDir.getAbsolutePath() + "\\PermutationEncryptedFile" + getFileExtension(srcFile));
				FileOutputStream fos = new FileOutputStream(destFile);
				fos.write(encryptedBytes);
				fos.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			System.out.println("This is not a file");
		}

	}

	/**
	 * Giải mã Hoán vị - Text
	 * 
	 * @param source
	 * @param input
	 * @param destDir
	 * @throws Exception
	 */
	public void decrypt(String source, int[] input, File destDir) throws Exception {
		String cipherText;
		File file = new File(source);
		key = input;

		// Check if source is file path or not
		if (file.isFile()) {
			Path path = Path.of(source);
			cipherText = Files.readString(path);
		} else {
			cipherText = source;
		}

		// Decrypting
		int checkLength = cipherText.length() % key.length;
		int deleteNumber = 0;
		if (checkLength != 0) {
			String numberString = cipherText.substring(0, 1);
			deleteNumber = Integer.valueOf(numberString);
			cipherText = cipherText.substring(1);
		}
		
		int count = 0;
		String subString;
		decryptedString = "";
		char[] decryptedArray;
		outer:
		for (int i = 0; i < cipherText.length(); i += key.length) {
			subString = cipherText.substring(i, i + key.length);
			decryptedArray = new char[key.length];
			for (int j = 0; j < key.length; j++) {
				decryptedArray[key[j]] = subString.charAt(j);
				count++;
			}
			decryptedString += String.valueOf(decryptedArray);
		}

		decryptedString = decryptedString.substring(0, decryptedString.length() - deleteNumber);

		// Create decrypted file
		try {
			File destFile = new File(destDir.getAbsolutePath() + "\\PermutationDecrypted.txt");
			FileWriter fw = new FileWriter(destFile);
			PrintWriter pw = new PrintWriter(fw);
			pw.println(decryptedString);
			pw.close();
			fw.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Giải mã Hoán vị - File
	 * 
	 * @param srcFile
	 * @param input
	 * @param destDir
	 * @throws Exception
	 */
	public void decryptFile(File srcFile, int[] input, File destDir) throws Exception {
		byte[] fileContent = Files.readAllBytes(srcFile.toPath());
		byte[] exFileContent = null;

		// Check if source is file path or not
		if (srcFile.isFile()) {
			// Encrypting
			key = input;
			int number = fileContent.length % key.length;
			if (number != 0) {
				exFileContent = new byte[fileContent.length - number];
				for (int i1 = 0; i1 < exFileContent.length; i1++) {
					exFileContent[i1] = fileContent[i1];
				}
			}

			byte[] subArray = new byte[key.length];
			byte[] resultArray = new byte[exFileContent.length];
			for (int i = 0; i < exFileContent.length; i += key.length) {
				subArray = Arrays.copyOfRange(exFileContent, i, i + key.length);
				for (int j = 0; j < key.length; j++) {
					resultArray[i + key[j]] = subArray[j];
				}
			}

			decryptedBytes = new byte[exFileContent.length - key.length + number];
			for (int i3 = 0; i3 < decryptedBytes.length; i3++) {
				decryptedBytes[i3] = resultArray[i3];
			}

			// Create decrypted file
			try {
				File destFile = new File(
						destDir.getAbsolutePath() + "\\PermutationDecryptedFile" + getFileExtension(srcFile));
				FileOutputStream fos = new FileOutputStream(destFile);
				fos.write(decryptedBytes);
				fos.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			System.out.println("This is not a file");
		}

	}
	
	// Kiểm tra String nhập vào có phải là số hay không
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
	
	// Lấy extension của file
	public String getFileExtension(File file) {
		String fileName = file.getName();

		int i = fileName.lastIndexOf(".");
		String fileExtension = fileName.substring(i);

		return fileExtension;
	}
}
