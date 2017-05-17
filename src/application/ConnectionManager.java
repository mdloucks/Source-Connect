package application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * class that manages ftp, databases, and networking between all of the users
 * 
 * @author louck
 *
 */
public class ConnectionManager {

	
	/**
	 * Sends the specified file path file to the given socket
	 * 
	 * @param file
	 * @param s
	 * @throws IOException
	 */
	public void sendFile(String file, Socket s) throws IOException {
		
		DataOutputStream dos = new DataOutputStream(s.getOutputStream());
		FileInputStream fis = new FileInputStream(file);
		
		File f = new File(file);
		// sends name of file
		dos.writeUTF(file);
		// sends size of file
		dos.writeInt((int) f.length());
		
		// fills up the buffer on each write, then sends that buffer to client
		byte[] buffer = new byte[8192];
		while (fis.read(buffer) > 0) {
			dos.write(buffer);
		}
		
		fis.close();
		dos.close();	
	}
	
	public void saveFile(Socket clientSock) throws IOException {
		
		DataInputStream dis = new DataInputStream(clientSock.getInputStream());
		FileOutputStream fos = new FileOutputStream(dis.readUTF());
		
		byte[] buffer = new byte[8192];
		
		// receives size of file
		int filesize = dis.readInt();
		int read = 0;
		int totalRead = 0;
		int remaining = filesize;
		
		while((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
			totalRead += read;
			remaining -= read;
			System.out.println("read " + totalRead + " bytes.");
			fos.write(buffer, 0, read);
		}
		
		fos.close();
		dis.close();
	}
}
