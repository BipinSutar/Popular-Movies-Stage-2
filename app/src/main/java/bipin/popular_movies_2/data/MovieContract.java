package bipin.popular_movies_2.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by BipinSutar on 6/12/2016.
 */
public class MovieContract {
    public static final String CONTENT_AUTHORITY = "bipin.popular_movies_2.app";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_POSTER = PosterEntry.TABLE_NAME;
    public static final String PATH_TRAILER = TrailerEntry.TABLE_NAME;

    //ImagePoster contract inner class
    public static final class PosterEntry implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().
                appendPath(PATH_POSTER).build();

        //Base URIs Strings useful for appending query parameters, and then buiding the URI
        //String example:  CONTENT_TYPE: vnd.android.cursor.dir/bipin.popular_movies_2.app/poster
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+CONTENT_AUTHORITY+
                "/"+PATH_POSTER;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+CONTENT_AUTHORITY+
                "/"+PATH_POSTER;

        public static final String TABLE_NAME = "poster";

        public static final String COL_TITLE = "title";
        public static final String COL_MOVIE_ID = "movie_id";
        public static final String COL_RELEASE_DATE = "release_date";
        public static final String COL_DURATION = "duration";
        public static final String COL_DESCRIPTION = "description";
        public static final String COL_IMAGE_URL = "image_url";
        public static final String COL_VOTE_AVERAGE = "vote_average";
        public static final String COL_POPULARITY = "popularity";
        public static final String COL_VOTE_COUNT = "vote_count";
        public static final String COL_FAVOURITE = "favourite";

        /*
        URI constructor returning the URI for a specific poster entry
         */
        public static Uri buildPosterUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Long getIdFromUri(Uri uri){
            return Long.parseLong(uri.getLastPathSegment());
        }

        //Used appendPath instead of appendQueryParam because there's no sorting order column
        public static Uri buildPosterWithSorting(String sort_order){
            return CONTENT_URI.buildUpon().appendPath(sort_order).build();
        }

        public static Uri buildPosterWithSortingAndMinumVotes(String sort_order, String min_votes){
            return CONTENT_URI.buildUpon().appendPath(sort_order).appendPath(min_votes).build();
        }

        public static String getSortingOrder(Uri uri){
            return uri.getPathSegments().get(1);
        }

        public static int getVoteCount(Uri uri){
            return Integer.parseInt(uri.getPathSegments().get(2));
        }
    }

    //Trailer contract inner class
    public static final class TrailerEntry implements BaseColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().
                appendPath(PATH_TRAILER).build();

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+CONTENT_AUTHORITY+
                "/"+PATH_TRAILER;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+CONTENT_AUTHORITY+
                "/"+PATH_TRAILER;

        public static final String TABLE_NAME = "trailer";

        public static final String COL_NAME = "name";
        public static final String COL_SITE = "site";
        public static final String COL_TRAILER_KEY = "key"; //The unique trailer's id on the player website
        public static final String COL_SIZE = "size";
        public static final String COL_TYPE = "type";
        public static final String COL_TRAILER_ID = "id";

        //Movie foreign key
        public static final String COL_MOVIE_ID = "movie";

        public static Uri buildTrailerUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildTrailerUriWithMovieId(long id){
            return CONTENT_URI.buildUpon().appendQueryParameter(COL_MOVIE_ID, Long.toString(id))
                    .build();
        }

        public static long getMovieId(Uri uri){
            return Long.parseLong(uri.getQueryParameter(COL_MOVIE_ID));
        }
    }
}
