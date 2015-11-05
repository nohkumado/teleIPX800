package com.nohkumado.teleIPX800;
import android.app.*;
import android.content.*;
import android.preference.*;
import android.util.*;
import com.nohkumado.ipx800control.*;
import java.io.*;
import java.nio.charset.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.xmlpull.v1.*;

public class IpxStatus
{
  private static final String ns = null;
  protected Ipx800Control ipx = null;
  protected boolean connected = false;
  protected boolean leds[] = new boolean[32];
  protected boolean buttons[] = new boolean[32];
  protected Date ipxDate;
  protected int[] analog = new int[16];
  protected int[] counter = new int[8];
	protected ProgressDialog progressDialog;
	protected MainActivity context;


  public final static String TAG = "IPXSTATUS";

  private String tinfo;

  private String version;

  protected Pattern ledPat = Pattern.compile("led(\\d+)");
  protected Pattern butPat = Pattern.compile("btn(\\d+)");
  protected Pattern anaPat = Pattern.compile("analog(\\d+)");
  protected Pattern selPat = Pattern.compile("anselect(\\d+)");
  protected Pattern couPat = Pattern.compile("count(\\d+)");



  public IpxStatus(Ipx800Control i)
  {
		ipx = i;
		//refresh();

  }

	public void connected(boolean p0)
	{
		connected = p0;
	}

  public void refresh(MainActivity c)
  {
		context = c;
		String result = "";
		if (progressDialog == null)
		{
			progressDialog = new ProgressDialog(context);
			progressDialog.setMessage("@strings/updateipx");
			progressDialog.setIndeterminate(true);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		}

		if (ipx != null)
		{
			SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
			if (sp.contains("servername")) ipx.setHost(sp.getString("servername", ipx.getHost()));
			else sp.edit().putString("servername", ipx.getHost()).apply();
			if (sp.contains("serverport")) ipx.setPort(sp.getInt("serverport", ipx.getPort()));
			else sp.edit().putInt("serverport", ipx.getPort()).apply();//else Log.d(TAG, "proceeding with createview");


			UpdateIpxStatusTask updateIt = new UpdateIpxStatusTask(progressDialog, this);
			//Log.d(TAG, "starting thread with " + hexgrid);
			updateIt.execute(new Ipx800Control[] {ipx});
		}
		/*
		 if (ipx != null)
		 {
		 SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
		 if (sp.contains("servername")) ipx.setHost(sp.getString("servername", ipx.getHost()));
		 else sp.edit().putString("servername", ipx.getHost()).apply();
		 if (sp.contains("serverport")) ipx.setPort(sp.getInt("serverport", ipx.getPort()));
		 else sp.edit().putInt("serverport", ipx.getPort()).apply();//else Log.d(TAG, "proceeding with createview");

		 String result = ipx.status();
		 //Log.d(TAG,"ipx returned "+result);
		 if (result.length() > 0)
		 {
		 connected = true;
		 InputStream stream = new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8));
		 try
		 {
		 parse(stream);
		 }
		 catch (Exception e)
		 { Log.e(TAG, "something went wrong parsing " + result);}
		 }
		 }*/
  }

  public boolean isConnected()
  {
		return connected;
  }

  public void parse(InputStream in) throws XmlPullParserException, IOException 
  {
		try 
		{
			XmlPullParser parser = Xml.newPullParser();
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in, null);
			parser.nextTag();
			readStatus(parser);
		} 
		finally 
		{
			in.close();
		}
		if(context != null) context.statusUpdated();
  }

  private void readStatus(XmlPullParser parser) throws XmlPullParserException, IOException 
  {
		Log.d(TAG, "starting parsing " + parser.getName());
    parser.require(XmlPullParser.START_TAG, ns, "response");
    while (parser.next() != XmlPullParser.END_TAG) 
		{
			if (parser.getEventType() != XmlPullParser.START_TAG)
			{
				continue;
			}
			String name = parser.getName();
			//Log.d(TAG,"found tag to parse  "+name);
			// Starts by looking for the entry tag
			if (name.startsWith("led"))
			{
				Matcher match = ledPat.matcher(name);

				if (match.find())
				{
					int index = Integer.parseInt(match.group(1));
					String content = readText(parser);
					if (content.equals("1")) leds[index] = true;
					else leds[index] = false;
				}
				else Log.e(TAG, "couldn't find a number in " + name);
			}
			else if (name.startsWith("btn"))
			{
				Matcher match = butPat.matcher(name);
				if (match.find())
				{
					int index = Integer.parseInt(match.group(1));
					String content = readText(parser);

					if (content.equals("UP")) buttons[index] = true;
					else buttons[index] = false;
				}
			}
			else if (name.matches("day")) //   <day>16/10/2015 </day>
			{
				String content = readText(parser);

				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				ipxDate = new Date();
				try
				{
					ipxDate = dateFormat.parse(content);
				}
				catch (ParseException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
			}
			else if (name.startsWith("time")) //   <time0>19:30:59</time0> 
			{
				String content = readText(parser);

				SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
				Date tmpStamp = new Date();
				try
				{
					tmpStamp = dateFormat.parse(content);
					ipxDate.setHours(tmpStamp.getHours());
					ipxDate.setMinutes(tmpStamp.getMinutes());
					ipxDate.setSeconds(tmpStamp.getSeconds());
				}
				catch (ParseException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
			}//<analog0>0</analog0>
			else if (name.startsWith("analog"))
			{
				Matcher match = anaPat.matcher(name);
				if (match.find())
				{
					int index = Integer.parseInt(match.group(1));
					String content = readText(parser);
					int value = Integer.parseInt(content);

					analog[index] = value;
					//Log.d(TAG, "set analog " + index + " to " + value);
				}
			}
			else if (name.startsWith("anselect")) //<anselect0>0</anselect0>
			{
				Matcher match = selPat.matcher(name);
				if (match.find())
				{

					int index = Integer.parseInt(match.group(1));
					String content = readText(parser);
					//Log.d(TAG, "extracted anselect " + index + " to " + content);
					/*int value = Integer.parseInt(content);
					 TODO find out what this is x16
					 analog[index] = value;
					 Log.d(TAG, "set analog " + counter + " to " + value);
					 */
				}
			}
			else if (name.startsWith("count")) //<count0>23</count0>
			{
				Matcher match = couPat.matcher(name);
				if (match.find())
				{
					int index = Integer.parseInt(match.group(1));
					String content = readText(parser);
					int value = Integer.parseInt(content);

					counter[index] = value;
				}
			}
			else if (name.matches("tinfo")) // <tinfo>---</tinfo>
			{
				tinfo = readText(parser);
			}
			else if (name.matches("version")) //   <version>3.05.59d</version>
			{
				version = readText(parser);
			}
			else
			{
				skip(parser);
			}
    }  
  }

  /* 
   example of parsing sub trees, not needed at the moment....
   // Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them off
   // to their respective "read" methods for processing. Otherwise, skips the tag.
   private Entry readEntry(XmlPullParser parser) throws XmlPullParserException, IOException
   {
   parser.require(XmlPullParser.START_TAG, ns, "entry");
   String title = null;
   String summary = null;
   String link = null;
   while (parser.next() != XmlPullParser.END_TAG)
   {
   if (parser.getEventType() != XmlPullParser.START_TAG)
   {
   continue;
   }
   String name = parser.getName();
   if (name.equals("title"))
   {
   title = readTitle(parser);
   }
   else if (name.equals("summary"))
   {
   summary = readSummary(parser);
   }
   else if (name.equals("link"))
   {
   link = readLink(parser);
   }
   else
   {
   skip(parser);
   }
   }
   return new Entry(title, summary, link);
   }
   // Processes link tags in the feed.
   private String readLink(XmlPullParser parser) throws IOException, XmlPullParserException
   {
   String link = "";
   parser.require(XmlPullParser.START_TAG, ns, "link");
   String tag = parser.getName();
   String relType = parser.getAttributeValue(null, "rel");  
   if (tag.equals("link"))
   {
   if (relType.equals("alternate"))
   {
   link = parser.getAttributeValue(null, "href");
   parser.nextTag();
   } 
   }
   parser.require(XmlPullParser.END_TAG, ns, "link");
   return link;
   }

   */

// For the tags title and summary, extracts their text values.
  private String readText(XmlPullParser parser) throws IOException, XmlPullParserException
  {
    String result = "";
    if (parser.next() == XmlPullParser.TEXT)
		{
			result = parser.getText();
			parser.nextTag();
    }
    return result;
  }
  private void skip(XmlPullParser parser) throws XmlPullParserException, IOException
  {
    if (parser.getEventType() != XmlPullParser.START_TAG)
		{
			throw new IllegalStateException();
    }
    int depth = 1;
    while (depth != 0)
		{
			switch (parser.next())
			{
        case XmlPullParser.END_TAG:
					depth--;
					break;
        case XmlPullParser.START_TAG:
					depth++;
					break;
			}
    }
  }
}

