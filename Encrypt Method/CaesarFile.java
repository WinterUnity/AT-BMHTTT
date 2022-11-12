package EncryptMethod;

import java.io.*;
import java.nio.file.Files;
import java.util.Random;

public class CaesarFile {
	int key;
	byte[] encryptedBytes, decryptedBytes;

	/**
	 * Tạo khóa Caesar
	 * 
	 * @return int key
	 */
	public int createKey() {
		Random rd = new Random();
		while (key == 0) {
			key = rd.nextInt(10);
		}
		return key;
	}

	/**
	 * Đọc khóa Caesar
	 * 
	 * @param srcFile
	 * @return int key
	 * @throws IOException
	 */
	public int readKey(File srcFile) throws IOException {
		FileReader fr = new FileReader(srcFile);
		BufferedReader br = new BufferedReader(fr);
		key = Integer.parseInt(br.readLine());
		br.close();

		return key;
	}

	/**
	 * Mã hóa Caesar
	 * 
	 * @param source
	 * @param input
	 * @param destDir
	 * @throws Exception
	 */
	public void encrypt(File srcFile, int input, File destDir) throws Exception {
		byte[] fileContent = Files.readAllBytes(srcFile.toPath());

		// Check if source is file path or not
		if (srcFile.isFile()) {
			// Encrypting
			encryptedBytes = new byte[fileContent.length];
			key = input;
			
			for (int i = 0; i < fileContent.length; i++) {
				fileContent[i] = (byte) (fileContent[i] + key);
			}
			
			// Create encrypted file
			try {
				File destFile = new File(destDir.getAbsolutePath() + "\\CaesarEncryptedFile" + getFileExtension(srcFile));
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
	 * Giải mã Caesar
	 * 
	 * @param source
	 * @param input
	 * @param destDir
	 * @throws Exception
	 */
	public void decrypt(File srcFile, int input, File destDir) throws Exception {
		byte[] fileContent = Files.readAllBytes(srcFile.toPath());

		// Check if source is file path or not
		if (srcFile.isFile()) {
			// Decrypting
			decryptedBytes = new byte[fileContent.length];
			key = input;
			
			for (int i = 0; i < fileContent.length; i++) {
				fileContent[i] = (byte) (fileContent[i] - key);
			}
			
			// Create encrypted file
			try {
				File destFile = new File(destDir.getAbsolutePath() + "\\CaesarDecryptedFile" + getFileExtension(srcFile));
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
	
	public String getFileExtension(File file) {
		String fileName = file.getName();
		
		int i = fileName.lastIndexOf(".");
		String fileExtension = fileName.substring(i);
		
		return fileExtension;
	}
}