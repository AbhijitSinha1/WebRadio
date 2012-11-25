package model;

import java.util.ArrayList;

public class PlayList
{
	ArrayList<Song> list = new ArrayList<Song>();

	private static final PlayList obj = new PlayList();

	private PlayList()
	{

	}

	public void add(Song song)
	{
		System.out.println("song added is null: " + (song == null));
		if (!this.list.contains(song))
		{
			this.list.add(song);
		}
	}

	public int size()
	{
		// TODO Auto-generated method stub
		return this.list.size();
	}

	@Override
	public String toString()
	{
		String retStr = "";
		for (int i = 0; i < this.list.size(); i++)
		{
			System.out.println("Song is null: " + (this.list.get(i) == null));
			retStr += this.list.get(i).getName() + "|";
		}
		return retStr.substring(0, retStr.length() - 1);
	}

	public static PlayList getInstanceOf()
	{
		return obj;
	}

	public void remove(int i)
	{
		// TODO Auto-generated method stub
		if (this.list.size() > 1)
		{
			this.list.remove(0);
		}
	}

	public String get(int i)
	{
		// TODO Auto-generated method stub
		return this.list.get(i).getName();
	}

	public Song getSong(int i)
	{
		// TODO Auto-generated method stub
		return this.list.get(i);
	}

	public Song getSong(String substring)
	{
		// TODO Auto-generated method stub
		for (int i = 0; i < this.list.size(); i++)
		{
			if (this.list.get(i).getName().equals(substring))
			{
				return this.list.get(i);
			}
		}
		return null;
	}
}
