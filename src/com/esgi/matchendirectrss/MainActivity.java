package com.esgi.matchendirectrss;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;


import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


public class MainActivity extends ListActivity implements OnClickListener{

	List headlines;
	List links;
	List dates;
	Button ligue1;
	Button premierLeague;
	Button bundesliga;
	Button serieA;
	Button liga;
	Button ligueDesChampions;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		 ligue1 = (Button) findViewById(R.id.ligue1);
		 premierLeague = (Button) findViewById(R.id.premierLeague);
		 bundesliga = (Button) findViewById(R.id.bundesliga);
		 serieA = (Button) findViewById(R.id.serieA);
		 liga = (Button) findViewById(R.id.liga);
		 ligueDesChampions = (Button) findViewById(R.id.ligueDesChampions);
	        
	        ligue1.setOnClickListener(this);
	        premierLeague.setOnClickListener(this);
	        bundesliga.setOnClickListener(this);
	        serieA.setOnClickListener(this);
	        liga.setOnClickListener(this);
	        ligueDesChampions.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		String urlRss;
		switch (v.getId()) {
		case R.id.ligue1:
			urlRss = "http://www.matchendirect.fr/rss/foot-ligue-1-c10.xml";
			rssFonction(urlRss);
			break;
		case R.id.premierLeague:
			urlRss = "http://www.matchendirect.fr/rss/foot-barclays-premiership-premier-league-c15.xml";
			rssFonction(urlRss);
			break;
		case R.id.bundesliga:
			urlRss = "http://www.matchendirect.fr/rss/foot-bundesliga-1-c11.xml";
			rssFonction(urlRss);
			break;
		case R.id.serieA:
			urlRss = "http://www.matchendirect.fr/rss/foot-serie-a-c13.xml";
			rssFonction(urlRss);
			break;
		case R.id.liga:
			urlRss = "http://www.matchendirect.fr/rss/foot-primera-division-c14.xml";
			rssFonction(urlRss);
			break;
		case R.id.ligueDesChampions:
			urlRss = "http://www.matchendirect.fr/rss/foot-ligue-des-champions-league-p2005.xml";
			rssFonction(urlRss);
			break;
		default:
			break;
		}

	}

	void rssFonction(String urlRss) {
		// Initializing instance variables
		headlines = new ArrayList();
		links =new ArrayList();
		dates =new ArrayList();
		try{
			//URL
			URL url = new URL(urlRss);

			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(false);
			XmlPullParser xpp = factory.newPullParser();
			// We will get the XML from an input stream
			xpp.setInput(getInputStream(url), "UTF_8");
			boolean insideItem = false;// Returns the type of current event: START_TAG, END_TAG, etc..
			int eventType = xpp.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if(eventType == XmlPullParser.START_TAG) {
					if(xpp.getName().equalsIgnoreCase("item")) {
						insideItem = true;
					} else if (xpp.getName().equalsIgnoreCase("title")) {
							if(insideItem)
								headlines.add(xpp.nextText()); //extract the headline
							} else if (xpp.getName().equalsIgnoreCase("link")) {
									if(insideItem)
								links.add(xpp.nextText());
							//extract the link of article
									}else if (xpp.getName().equalsIgnoreCase("pubDate")) {
											if(insideItem)
											dates.add(xpp.nextText());
										//extract date
												}
				}else if(eventType==XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")){
							insideItem=false;
						}
				eventType = xpp.next();
				//move to next element
			}} catch(MalformedURLException e) {
				e.printStackTrace();
			} catch(XmlPullParserException e) {
				e.printStackTrace();}
		catch (IOException e) {
			e.printStackTrace();
		}// Binding data
		ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, headlines);
		setListAdapter(adapter);
	}

	public InputStream getInputStream(URL url) {
		try
		{
			return url.openConnection().getInputStream();
		} catch(IOException e) {
			return null;}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Uri uri = Uri.parse((String) links.get(position));
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


}
