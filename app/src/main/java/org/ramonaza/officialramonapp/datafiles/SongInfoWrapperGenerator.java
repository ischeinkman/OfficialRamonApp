package org.ramonaza.officialramonapp.datafiles;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilanscheinkman on 4/28/15.
 */
public abstract class SongInfoWrapperGenerator {

    public static final String[] allSongNames={"For Tomorow And Today", "World of AZA","AZA All The Way","Proud To Be An Aleph", "El HaMaayan", "Never Too Many", "Gentlemen"};

    public static SongInfoWrapper fromName(String name,Context context){
        String lyrics=getStringResourceByName(name.toLowerCase().replace(" ", "") + "text",context);
        return new SongInfoWrapper(name,lyrics);
    }

    public static List<SongInfoWrapper> allSongs(Context context){
        List<SongInfoWrapper> songs=new ArrayList<SongInfoWrapper>();
        for(String songName:allSongNames){
            songs.add(fromName(songName,context));
        }
        return songs;
    }

    private static String getStringResourceByName(String aString,Context context) {
        String packageName = "org.ramonaza.officialramonapp";
        int resId = context.getResources().getIdentifier(aString, "string", packageName);
        return context.getString(resId);
    }
}
