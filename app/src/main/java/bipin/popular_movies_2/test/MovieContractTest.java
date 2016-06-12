package bipin.popular_movies_2.test;

import android.net.Uri;
import android.test.AndroidTestCase;

import bipin.popular_movies_2.data.MovieContract;

/**
 * Created by BipinSutar on 6/12/2016.
 */
public class MovieContractTest extends AndroidTestCase {
    public static final String TEST_SORT_ORDER = "desc";


    public void testBuildWeatherLocation() {
        Uri locationUri = MovieContract.PosterEntry.buildPosterWithSorting(TEST_SORT_ORDER);
        assertNotNull("Error: Null Uri returned.",
                locationUri);
        assertEquals("Error: sorting order not properly appended to the end of the Uri",
                TEST_SORT_ORDER, locationUri.getLastPathSegment());
        assertEquals("Error: Uri doesn't match our expected result",
                locationUri.toString(),
                "content://bipin.popular_movies_2.app/poster/"+TEST_SORT_ORDER);
  }
}
