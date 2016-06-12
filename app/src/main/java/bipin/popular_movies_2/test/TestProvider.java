package bipin.popular_movies_2.test;

import android.content.ComponentName;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.List;

import bipin.popular_movies_2.data.MovieContract;
import bipin.popular_movies_2.data.MovieProvider;

/**
 * Created by BipinSutar on 6/12/2016.
 */
public class TestProvider extends AndroidTestCase {

    public void testProviderRegistry() {
        PackageManager pm = mContext.getPackageManager();

        // We define the component name based on the package name from the context and the
        // WeatherProvider class.
        ComponentName componentName = new ComponentName(mContext.getPackageName(),
                MovieProvider.class.getName());
        try {
            // Fetch the provider info using the component name from the PackageManager
            // This throws an exception if the provider isn't registered.
            ProviderInfo providerInfo = pm.getProviderInfo(componentName, 0);

            // Make sure that the registered authority matches the authority from the Contract.
            assertEquals("Error: MovieProvider registered with authority: " + providerInfo.authority +
                    " instead of authority: " + MovieContract.CONTENT_AUTHORITY,
                    providerInfo.authority, MovieContract.CONTENT_AUTHORITY);
        } catch (PackageManager.NameNotFoundException e) {
            // I guess the provider isn't registered correctly.
            assertTrue("Error: MovieProvider not registered at " + mContext.getPackageName(),
                    false);
        }
    }




    public boolean validateCursor(ContentValues testValues, Cursor returnedCursor){
        returnedCursor.moveToNext();

        int title_index = returnedCursor.getColumnIndex(MovieContract.PosterEntry.COL_TITLE);

        if(!testValues.getAsString(MovieContract.PosterEntry.COL_TITLE).equals(
                returnedCursor.getString(title_index))){
            return false;
        }

        return true;
    }



    public void testInsert() {
        ContentValues [] testValues = TestDB.createMultiplePosterValues();

       Utils.TestContentObserver tco = Utils.getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(MovieContract.PosterEntry.CONTENT_URI, true, tco);


        for(ContentValues value : testValues) {
            Uri posterUri = mContext.getContentResolver().insert(MovieContract.PosterEntry.CONTENT_URI, value);

            //Content observer get called?
            tco.waitForNotificationOrFail();
            mContext.getContentResolver().unregisterContentObserver(tco);

            //Parse obtained id from posterUri
            long row_id = ContentUris.parseId(posterUri);
            assertTrue(row_id != -1);
        }

    }

    public void testBulkInsert(){
        ContentValues [] values = TestDB.createMultiplePosterValues();

        int inserted = mContext.getContentResolver().bulkInsert(MovieContract.PosterEntry.CONTENT_URI,
                values);

        assertEquals(values.length, inserted);

        //Try inserting the same values

        inserted = mContext.getContentResolver().bulkInsert(MovieContract.PosterEntry.CONTENT_URI,
                values);

        assertEquals(values.length, inserted);
    }

    public void testIdQuery(){
        ContentValues [] testValues = TestDB.createMultiplePosterValues();
        List<Integer> ids = new ArrayList<Integer>();

        Utils.TestContentObserver tco =Utils.getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(MovieContract.PosterEntry.CONTENT_URI, true, tco);


        for(ContentValues value : testValues) {
            Uri posterUri = mContext.getContentResolver().insert(MovieContract.PosterEntry.CONTENT_URI, value);

            //Content observer get called?
            tco.waitForNotificationOrFail();
            mContext.getContentResolver().unregisterContentObserver(tco);

            //Parse obtained id from posterUri
            long row_id = ContentUris.parseId(posterUri);
            ids.add(value.getAsInteger(MovieContract.PosterEntry.COL_MOVIE_ID));
            assertTrue(row_id != -1);
        }

        Uri id_uri = MovieContract.PosterEntry.buildPosterUri(ids.get(0));


        Cursor obtained_cursor = mContext.getContentResolver().query(id_uri,null,null,null,null);


        assertEquals("Wrong record number form URI: "+id_uri,1, obtained_cursor.getCount());

    }


}
