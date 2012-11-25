package model;

public class Song
{
	private final String Name;
	private final String Album;
	private final String Artist;
	private final Integer Duration;

	public Song(String name, String album, String artist, Integer duration)
	{
		this.Name = name;
		this.Album = album;
		this.Artist = artist;
		this.Duration = duration;
	}

	public String getName()
	{
		return this.Name;
	}

	public String getAlbum()
	{
		return this.Album;
	}

	public String getArtist()
	{
		return this.Artist;
	}

	public int getDuration()
	{
		return this.Duration;
	}

	public String getInfo()
	{
		// TODO Auto-generated method stub
		return this.getName() + "|" + this.getAlbum() + "|" + this.getArtist();
	}
}
