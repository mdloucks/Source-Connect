package application.managers;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

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
	
	/**
	 * TODO setup a server that tells us if there's an update, and check the user's os
	 * 
	 * queries our server to check for an update, if there is, ask the user for an update in the windows tray
	 * 
	 * I don't thinks this works for mac or linux (unless we add more libraries)
	 * 
	 * @throws AWTException 
	 */
	public static void checkUpdates() {
		
        if (SystemTray.isSupported()) {
        	
        	try {
        		
                //Obtain only one instance of the SystemTray object
                SystemTray tray = SystemTray.getSystemTray();

                Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
                TrayIcon trayIcon = new TrayIcon(image, "Source Connect");
                trayIcon.setImageAutoSize(true);
                trayIcon.setToolTip("Source Connect update");
                
                trayIcon.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(java.awt.event.ActionEvent arg0) {
						
                    	try {
							Desktop.getDesktop().browse(new URL("http://SourceConnect.com").toURI());
						} catch (MalformedURLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (URISyntaxException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
                });
                
                
                tray.add(trayIcon);
                trayIcon.displayMessage("Update availiable for Source Connect", "Version: " + "0.0.0 " + "has been released!", MessageType.INFO);
            	
        	} catch(AWTException e) {
        		
        	}

        } else {
            System.err.println("system tray is not supported by your operating system");
        }
	}
	
	public static ArrayList<File> getRemoteRepositories() {
		
		return null;
	}
}
