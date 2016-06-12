package bipin.popular_movies_2.test;

import android.test.suitebuilder.TestSuiteBuilder;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Created by BipinSutar on 6/12/2016.
 */
public class FullTestSuite extends TestSuite{

    public static Test suite() {
        return new TestSuiteBuilder(FullTestSuite.class)
                        .includeAllPackagesUnderHere().build();
    }

    public FullTestSuite() {
                super();
    }
}
