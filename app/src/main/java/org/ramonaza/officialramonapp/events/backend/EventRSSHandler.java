package org.ramonaza.officialramonapp.events.backend;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by ilan on 9/8/15.
 */
public class EventRSSHandler {
    
    private String rawRSS;

    private static final String ITEM_SPLITTER="<item>";
    private static final String ATTRIBUTE_SPLITTER=" <br/> ";
    
    public EventRSSHandler(String rssSource, boolean isStream){
        if(isStream) rawRSS=getRssFromStream(rssSource);
        else rawRSS=rssSource;
    }
    
    private static String getRssFromStream(String url){
        StringBuilder builder = new StringBuilder(100000);

        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse execute = client.execute(httpGet);
            InputStream content = execute.getEntity().getContent();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
            String s;
            while ((s = buffer.readLine()) != null) {
                builder.append(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        String totalRss=builder.toString();
        String strippedRss;
        if (totalRss.indexOf(ITEM_SPLITTER) == -1)
            strippedRss = "";
        else strippedRss=totalRss.substring(totalRss.indexOf(ITEM_SPLITTER)+ITEM_SPLITTER.length());
        return strippedRss;
    }
    
    public EventInfoWrapper[] getEventsFromRss(){
        // In case of no events posted
        if (rawRSS.length() == 0)
            return new EventInfoWrapper[0];

        String[] itemmedRSS = rawRSS.split(ITEM_SPLITTER);
        EventInfoWrapper[] events = new EventInfoWrapper[itemmedRSS.length];
        for(int i=0;i<itemmedRSS.length;i++){
            String[] splitFeed=itemmedRSS[i].split(ATTRIBUTE_SPLITTER);
            EventInfoWrapper currentEvent=new EventInfoWrapper();
            currentEvent.setName(splitFeed[2]);
            currentEvent.setDesc(splitFeed[3]);
            currentEvent.setPlanner(splitFeed[7]);
            currentEvent.setMeet(splitFeed[5] + " @ " + splitFeed[4]);
            currentEvent.setMapsLocation(splitFeed[8]);
            currentEvent.setBring(splitFeed[6]);
            currentEvent.setDate(splitFeed[1]);
            currentEvent.setId(i);
            events[i]=currentEvent;
        }
        return events;
    }

    public String convertEventsToRss(){
        return rawRSS;
    }

    public EventInfoWrapper getEvent(int index){
        EventInfoWrapper[] allEvents=getEventsFromRss();
        return allEvents[index];
    }

    public String getEventRSS(int index){
        String[] itemmedRSS=rawRSS.split(ITEM_SPLITTER);
        return itemmedRSS[index];
    }
}
