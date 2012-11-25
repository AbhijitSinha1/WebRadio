package pages;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.PlayList;
import model.SongList;
import model.UserList;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.WsOutbound;

/**
 * Servlet implementation class WebRadio
 */

public class WebRadio extends WebSocketServlet
{
	private static final long serialVersionUID = 1L;
	private final SongList sList = SongList.getInstanceOf();
	private final PlayList pList = PlayList.getInstanceOf();
	private final UserList uList = UserList.getInstanceOf();

	private class TheWebSocket extends MessageInbound
	{
		private WsOutbound outbound;

		@Override
		protected void onBinaryMessage(ByteBuffer arg0) throws IOException
		{
			// TODO Auto-generated method stub

		}

		@Override
		protected void onTextMessage(CharBuffer buff) throws IOException
		{
			if (buff.toString().equals("NEXT"))
			{
				WebRadio.this.pList.remove(0);
				if (WebRadio.this.pList.size() > 0)
				{
					WebRadio.this.uList.broadcast("PLAY:" + WebRadio.this.pList.get(0));
					WebRadio.this.uList.broadcast("LIST:" + WebRadio.this.pList.toString());
					WebRadio.this.uList.broadcast("INFO:" + WebRadio.this.pList.getSong(0).getInfo());
				}
			}
			if (buff.toString().equals("ADD"))
			{
				this.outbound.writeTextMessage(CharBuffer.wrap("Cancel|" + this.addSongs()));
			}
			if (buff.toString().startsWith("TIME:"))
			{
				System.out.println(buff.toString().substring(5));
			}
			if (buff.toString().startsWith("SONG:"))
			{
				String song = buff.toString();
				// JOptionPane.showMessageDialog(null, "Song chosen: " + song);
				WebRadio.this.pList.add(WebRadio.this.sList.getSong(song.replaceAll("SONG:", "")));
				WebRadio.this.uList.broadcast(song);

			}
			if (buff.toString().startsWith("PLAY:"))
			{
				WebRadio.this.uList.broadcast("INFO:" + WebRadio.this.pList.getSong(buff.toString().substring(5)).getInfo());
				WebRadio.this.uList.broadcast(buff.toString());
			}
		}

		private String addSongs()
		{
			// TODO Auto-generated method stub
			return (WebRadio.this.sList.toString());
		}

		@Override
		protected void onOpen(WsOutbound outbound)
		{
			this.outbound = outbound;
			WebRadio.this.uList.add(outbound);
			if (WebRadio.this.pList.size() > 0)
			{
				try
				{
					outbound.writeTextMessage(CharBuffer.wrap("LIST:" + WebRadio.this.pList.toString()));
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (WebRadio.this.uList.size() > 1)
				{
					WebRadio.this.uList.sendFirst("FILE?");
					WebRadio.this.uList.sendFirst("TIME?");
				}
			}
		}

		@Override
		protected void onClose(int statue)
		{
			WebRadio.this.uList.remove(this.outbound);
		}

	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected StreamInbound createWebSocketInbound(String arg0, HttpServletRequest arg1)
	{
		// TODO Auto-generated method stub
		TheWebSocket inbound = new TheWebSocket();
		return inbound;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		super.doGet(request, response);
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// TODO Auto-generated method stub
		String url = "/pages/Radio.jsp";
		ServletContext context = this.getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher(url);
		dispatcher.include(request, response);

	}

}
