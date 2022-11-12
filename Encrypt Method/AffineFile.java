package EncryptMethod;

import java.io.*;
import java.nio.file.*;
import java.util.Random;

public class AffineFile {
	int keyA1, keyA2, keyB;
	int[] keyPair = new int[2];
	byte[] encryptedBytes, decryptedBytes;

	/**
	 * Tạo khóa
	 * 
	 * @return int[] keyPair
	 */
	public int[] createKey() throws IOException {
		Random rd = new Random();
		while (keyA1 == 0 || this.isPrime(keyA1) != true) {
			keyA1 = rd.nextInt(10);
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
	 * 
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
	 * Mã hóa Affine
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
			keyPair = input;
			keyA1 = keyPair[0];
			keyB = keyPair[1];

			for (int i = 0; i < fileContent.length; i++) {
				fileContent[i] = (byte) ((keyA1 * fileContent[i] + keyB));
			}

			// Create encrypted file
			try {
				File destFile = new File(
						destDir.getAbsolutePath() + "\\AffineEncryptedFile" + getFileExtension(srcFile));
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

	/**
	 * Giải mã Affine
	 * 
	 * @param source
	 * @param input
	 * @param destDir
	 * @throws Exception
	 */
	public void decrypt(File srcFile, int[] input, File destDir) throws Exception {
		byte[] fileContent = Files.readAllBytes(srcFile.toPath());

		// Check if source is file path or not
		if (srcFile.isFile()) {
			decryptedBytes = new byte[fileContent.length];
			keyPair = input;
			keyA2 = this.findKeyA2(keyPair[0]);
			keyB = keyPair[1];

			for (int i = 0; i < fileContent.length; i++) {
				fileContent[i] = (byte) ((keyA2 * (fileContent[i] - keyB)));
			}

			// Create encrypted file
			try {
				File destFile = new File(
						destDir.getAbsolutePath() + "\\AffineDecryptedFile" + getFileExtension(srcFile));
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

	private int findKeyA2(int keyA1) {
		int result = 0, length = 256;
		for (int i = 0; i < length; i++) {
			if ((i * keyA1) % length == 1) {
				result = i;
			}
		}
		return result;
	}

	public String getFileExtension(File file) {
		String fileName = file.getName();

		int i = fileName.lastIndexOf(".");
		String fileExtension = fileName.substring(i);

		return fileExtension;
	}
}
