package model;

import java.io.File;
import java.util.ArrayList;

public class SongList
{
	ArrayList<Song> list = new ArrayList<Song>();

	private final String listString;

	private static final SongList obj = new SongList();

	private SongList()
	{
		// TODO Auto-generated constructor stub
		File file = new File("D:/EclipseWorkspace/SecondServlet2/WebContent/songs");
		String[] fileList = file.list();
		for (String element : fileList)
		{
			MP3.setSong(new File(file.getAbsolutePath() + "/" + element));
			Song song = new Song(element.replaceAll(".mp3", "").replaceAll(".MP3", ""), MP3.getAlbum(), MP3.getArtist(), 0);
			System.out.println("Song added: IS IT NULL???" + (song == null));
			this.list.add(song);
		}

		String retStr = "";
		for (int i = 0; i < this.list.size(); i++)
		{
			retStr += this.list.get(i).getName().replaceAll(".mp3", "") + "|";
		}

		this.listString = retStr.substring(0, retStr.length() - 1);
	}

	public void add(Song song)
	{
		this.list.add(song);
	}

	public void remove(Song song)
	{
		this.list.remove(song);
	}

	public Song getSong(int index)
	{
		return this.list.get(index);
	}

	public synchronized Song next()
	{
		Song temp = this.list.get(0);
		this.list.remove(0);
		this.list.add(temp);
		return (this.list.get(0));
	}

	public int size()
	{
		// TODO Auto-generated method stub
		return this.list.size();
	}

	public Song getSong(String song)
	{
		// TODO Auto-generated method stub
		System.out.println("Searching Song: " + song);
		for (int i = 0; i < this.list.size(); i++)
		{
			System.out.println("Song to be tested: " + this.list.get(i).getName());
			System.out.println("Song matched: " + this.list.get(i).getName().equals(song));
			if (this.list.get(i).getName().equals(song))
			{
				return this.list.get(i);
			}
		}
		return null;
	}

	public static SongList getInstanceOf()
	{
		return obj;
	}

	@Override
	public String toString()
	{
		return this.listString;
	}
}
