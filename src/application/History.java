package application;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class History {
	
	
	public History() {
		
	}
	
	/**
	 * Converts an image, found at the given path, to
	 * a byte array for storage in revision history.
	 * To retrieve an image from the history, call
	 * convertBytesToImage().
	 * 
	 * @param path
	 * the absolute path to the image
	 * @return
	 * A byte array representing the image.
	 * 
	 * @see {@link #convertBytesToImage}
	 * @author Seth
	 */
	public static byte[] convertImageToBytes(String path) {
		
		BufferedImage img = null;
		byte[] byteArray = new byte[1];
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		try {
			img = ImageIO.read(new File(path));
			ImageIO.write(img, "jpg", out);
			out.flush();
			byteArray = out.toByteArray();
			out.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("image was not found at: " + path);
		}
		
		return byteArray;
	}
	
	/**
	 * Creates a BufferedImage from the byte array
	 * provided.
	 * 
	 * @param bytes
	 * an array of bytes representing an image
	 * @return
	 * A BufferedImage created from the given byte array.
	 * 
	 * @see {@link #convertImageToBytes}
	 * @author Seth
	 */
	public static BufferedImage convertBytesToImage(byte[] bytes) {
		
		BufferedImage img = null;
		InputStream in = new ByteArrayInputStream(bytes);
		
		try {
			img = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("the byte array could not be read");
		}
		
		return img;
	}
	
}
