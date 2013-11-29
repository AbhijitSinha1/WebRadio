package model;

import java.io.File;
import java.io.FileInputStream;

public class MP3 {
	private static String album;
	private static String artist;

	public static void setSong(File song) {
		try {
			FileInputStream file = new FileInputStream(song);
			int size = (int) song.length();
			file.skip(size - 128);
			byte[] last128 = new byte[128];
			file.read(last128);
			String id3 = new String(last128);
			String tag = id3.substring(0, 3);
			if (tag.equals("TAG")) {

				artist = id3.substring(33, 62);
				album = id3.substring(63, 91);
			} else {
				// retStr = "Title: No Info|Artist: No Info|Album: No Info";
			}
			file.close();
		} catch (Exception e) {
			System.out.println("Error: " + e.toString());
		}

	}

	public static String getAlbum() {
		return album;
	}

	public static String getArtist() {
		return artist;
	}
}
