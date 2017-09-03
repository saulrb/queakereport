package com.example.android.quakereport.loader;

//import android.content.AsyncTaskLoader;
import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import com.example.android.quakereport.QueryUtils;
import com.example.android.quakereport.model.Earthquake;

import java.util.List;

/**
 * Created by saul on 2/9/2017.
 */

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {

    private String URL = null;
    /**
     * Constructor
     * @param context
     */
    public EarthquakeLoader(Context context, String URL) {
        super(context);
        this.URL = URL;
    }

    @Override
    protected void onStartLoading(){
        forceLoad();
    }

    /**
     * Called on a worker thread to perform the actual load and to return
     * the result of the load operation.
     * <p>
     * Implementations should not deliver the result directly, but should return them
     * from this method, which will eventually end up calling {@link #deliverResult} on
     * the UI thread.  If implementations need to process the results on the UI thread
     * they may override {@link #deliverResult} and do so there.
     * <p>
     * To support cancellation, this method should periodically check the value of
     * {@link #isLoadInBackgroundCanceled} and terminate when it returns true.
     * Subclasses may also override {@link #cancelLoadInBackground} to interrupt the load
     * directly instead of polling {@link #isLoadInBackgroundCanceled}.
     * <p>
     * When the load is canceled, this method may either return normally or throw
     * {@link OperationCanceledException}.  In either case, the {@link Loader} will
     * call {@link #onCanceled} to perform post-cancellation cleanup and to dispose of the
     * result object, if any.
     *
     * @return The result of the load operation.
     * @throws OperationCanceledException if the load is canceled during execution.
     * @see #isLoadInBackgroundCanceled
     * @see #cancelLoadInBackground
     * @see #onCanceled
     */
    @Override
    public List<Earthquake> loadInBackground() {
        List<Earthquake> earthquakes = null;
        if ( this.URL != null ) {
            earthquakes = QueryUtils.fetchEartquakeData(this.URL);
        }
        return earthquakes;
    }
}
