package model;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.ArrayList;

import org.apache.catalina.websocket.WsOutbound;

public class UserList
{

	private static final UserList obj = new UserList();

	private UserList()
	{

	}

	private final ArrayList<WsOutbound> list = new ArrayList<WsOutbound>();

	public void broadcast(String song)
	{
		// TODO Auto-generated method stub
		System.out.println("There are " + this.list.size() + " users to broadcast to");
		for (int i = 0; i < this.list.size(); i++)
		{
			try
			{
				System.out.println("" + i + " outbound is null: " + (this.list.get(i) == null));
				if (this.list.get(i) != null)
				{
					System.out.println("Sending to connection " + i + " " + song);
					this.list.get(i).writeTextMessage(CharBuffer.wrap(song));
				}
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void add(WsOutbound outbound)
	{
		// TODO Auto-generated method stub
		this.list.add(outbound);
	}

	public void remove(WsOutbound outbound)
	{
		// TODO Auto-generated method stub
		this.list.remove(outbound);

	}

	public static UserList getInstanceOf()
	{
		return obj;
	}

	public void sendFirst(String msg)
	{
		// TODO Auto-generated method stub
		try
		{
			this.list.get(0).writeTextMessage(CharBuffer.wrap(msg));
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int size()
	{
		// TODO Auto-generated method stub
		return this.list.size();
	}

}
