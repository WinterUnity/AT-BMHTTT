package EncryptMethod;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Affine {
	final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	int keyA1, keyA2, keyB;
	int[] keyPair = new int[2];
	String encryptedString, decryptedString;
	
	//Tạo khóa
	public int[] createKey() {
		Random rd = new Random();
		while(keyA1==0 && this.isPrime(keyA1)!=true) {
			keyA1 = rd.nextInt(alphabet.length()/2);
			keyPair[0] = keyA1;
		}
		keyB = rd.nextInt(10);
		keyPair[1] = keyB;
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("key.txt"));
			oos.writeObject(keyPair);
			oos.close();
			System.out.println("Key create successfully");
		} catch (Exception e) {
			System.out.println("Key cannot save to file");
		}

		return keyPair;
	}
	
	private boolean isPrime(int number) {
		if(number < 2) {
			return false;
		}
		for(int i=3; i<number; i++) {
			if(number%i == 0) {
				return false;
			}
		}
		return true;
	}
	
	//Đọc khóa
	public int[] readKey() {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("key.txt"));
			keyPair = (int[]) ois.readObject();
			for (int i = 0; i < keyPair.length; i++) {
				System.out.println(keyPair[i]);
			}
			return keyPair;
		} catch (IOException e) {
			System.out.println("Key cannot read from file");
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			System.out.println("Key not found");
			throw new RuntimeException(e);
		}
	}
	
	//Mã hóa Affine
	public void encrypt(String srcFile) throws Exception{
		keyA1 = keyPair[0]; keyB = keyPair[1];
		File file = new File(srcFile);
		if(file.isFile()) {
			Path path = Path.of(srcFile);
			String fileString = Files.readString(path);
			
			int count, number, half = alphabet.length()/2;
			encryptedString = "";
			for (int i = 0; i < fileString.length(); i++) {
				count = 0;
				for (int j = 0; j < alphabet.length(); j++) {
					if(fileString.charAt(i) == alphabet.charAt(j)) {
						if(j < half) {
							number = (keyA1*j + keyB) % half;
							encryptedString += alphabet.charAt(number);
						} else {
							number = (keyA1*j + keyB) % half + half;
							encryptedString += alphabet.charAt(number);
						}
					} else {
						count++;
						if(count == 52) {
							encryptedString += fileString.charAt(i);
						}
					}
				}
			}
			
			try {
				FileWriter fw = new FileWriter("CipherText.txt");
				PrintWriter pw = new PrintWriter(fw);
				pw.println(encryptedString);
				pw.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			System.out.println("Encrypt successfully");
		} else {
			System.out.println("This is not a File");
		}
	}
	
	//Giải mã Affine
	public void decrypt(String srcFile) throws Exception {
		keyA2 = this.findKeyA2(keyPair[0]); keyB = keyPair[1];
		File file = new File(srcFile);
		if(file.isFile()) {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcFile));
			
			byte byteRead;
			List<Byte> list = new ArrayList<Byte>();
			while((byteRead = (byte) bis.read()) != -1) {
				list.add(byteRead);
			}
			
			byte[] ex = new byte[list.size()];
			for (int a = 0; a < ex.length; a++) {
				ex[a] = list.get(a);
			}
			
			String content = new String(ex, "UTF-8");
			
			int count, number, total, negative, positive;
			int half = alphabet.length()/2;
			decryptedString= "";
			for (int i = 0; i < content.length(); i++) {
				count = 0;
				for (int j = 0; j < alphabet.length(); j++) {
					if(content.charAt(i) == alphabet.charAt(j)) {
						if(j < half) {
							total = keyA2*(j-keyB);
							if(total < 0) {
								negative = total / half;
								positive = negative*(-1) + 1;
								number = total + half*positive;
								decryptedString += alphabet.charAt(number);
							} else {
								number = total % half;
								decryptedString += alphabet.charAt(number);
							}
						} else {
							total = keyA2*((j-half)-keyB);
							if(total < 0) {
								negative = total / half;
								positive = negative*(-1) + 1;
								number = total + half*positive + half;
								decryptedString += alphabet.charAt(number);
							} else {
								number = total % half + half;
								decryptedString += alphabet.charAt(number);
							}
						}
					} else {
						count++;
						if(count == 52) {
							decryptedString += content.charAt(i);
						}
					}
				}
			}
			
			try {
				FileWriter fw = new FileWriter("PlainText.txt");
				PrintWriter pw = new PrintWriter(fw);
				pw.println(decryptedString);
				pw.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			} 
			System.out.println("Decrypt successfully");
		} else {
			System.out.println("This is not a File");
		}
	}
	
	private int findKeyA2(int key) {
		int result = 0, half = alphabet.length()/2;
		for (int i = 0; i < half; i++) {
			if((i*key) % half == 1){
				result = i;
			}
		}
		return result;
	}
	
	public static void main(String[] args) throws Exception {
		Affine a = new Affine();
//		a.createKey();
		a.readKey();
		a.encrypt("D:\\BMHTTT\\Affine\\SourceText.txt");
		a.decrypt("CipherText.txt");
	}
}