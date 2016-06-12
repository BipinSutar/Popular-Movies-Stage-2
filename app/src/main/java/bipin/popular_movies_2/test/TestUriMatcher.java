package bipin.popular_movies_2.test;

import android.content.UriMatcher;
import android.net.Uri;
import android.test.AndroidTestCase;

import bipin.popular_movies_2.data.MovieContract;
import bipin.popular_movies_2.data.MovieProvider;

/**
 * Created by BipinSutar on 6/12/2016.
 */
public class TestUriMatcher extends AndroidTestCase {

    public static final String TEST_SORTING = "desc";
    public static final String TEST_MIN_VOTES = "100";

    public static final Uri TEST_POSTER_URI = MovieContract.PosterEntry.CONTENT_URI;
    public static final Uri TEST_POSTER_WITH_SORTING_URI = MovieContract.PosterEntry.buildPosterWithSorting(TEST_SORTING);
    public static final Uri TEST_POSTER_WITH_SORTING_AND_MIN_VOTES_URI =
            MovieContract.PosterEntry.buildPosterWithSortingAndMinumVotes(TEST_SORTING,TEST_MIN_VOTES);
    public static final Uri TEST_TRAILER_URI = MovieContract.TrailerEntry.CONTENT_URI;

    public void testUriMatcher(){
        UriMatcher testMatcher = MovieProvider.buildUriMatcher();

        assertEquals("Error: The POSTER URI was matched incorrectly.",
                testMatcher.match(TEST_POSTER_URI), MovieProvider.POSTER);
        assertEquals("Error: The POSTER WITH SORTING was matched incorrectly.",
                testMatcher.match(TEST_POSTER_WITH_SORTING_URI), MovieProvider.POSTER_WITH_SORTING);
        assertEquals("Error: The POSTER WITH SORTING AND MIN VOTES was matched incorrectly.",
                testMatcher.match(TEST_POSTER_WITH_SORTING_AND_MIN_VOTES_URI), MovieProvider.POSTER_WITH_SORTING_AND_MIN_VOTES);
        assertEquals("Error: The TRAILER URI was matched incorrectly.",
                testMatcher.match(TEST_TRAILER_URI), MovieProvider.TRAILER);
    }
}
