package project301.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * Calls Google Place API to get suggestions for requester when posting
 * a new task
 *
 * @classname : PlaceArrayAdapterController
 * @Date :   18/03/2018
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 *
 * source: http://www.truiton.com/2015/04/android-places-api-autocomplete-getplacebyid/
 */


public class PlaceArrayAdapterController
        extends ArrayAdapter<PlaceArrayAdapterController.PlaceAutocomplete> implements Filterable {
    private static final String TAG = "PlaceArrayAdapter";
    private GoogleApiClient mGoogleApiClient;
    private final AutocompleteFilter mPlaceFilter;
    private final LatLngBounds mBounds;
    private ArrayList<PlaceAutocomplete> mResultList;

    /**
     * Constructor
     *
     * @param context  Context
     * @param resource Layout resource
     * @param bounds   Used to specify the search bounds
     * @param filter   Used to specify place types
     */
    public PlaceArrayAdapterController(Context context, int resource, LatLngBounds bounds,
                             AutocompleteFilter filter) {
        super(context, resource);
        mBounds = bounds;
        mPlaceFilter = filter;
    }

    public void setGoogleApiClient(GoogleApiClient googleApiClient) {
        Log.d("PlaceArrayAdapterContro","setGoogleApiClient");
        if (googleApiClient == null || !googleApiClient.isConnected()) {
            mGoogleApiClient = null;
        } else {
            mGoogleApiClient = googleApiClient;
        }
    }

    /**
     * Returns the number of suggestions the Google Places API found
     *
     * @return mResultList.size() int
     */
    @Override
    public int getCount() {
        Log.d("PlaceArrayAdapterContro","getCount");

        return mResultList.size();
    }

    /**
     * Returns a Place when the user clicks on a suggestion.
     *
     * @param position int
     * @return PlaceAutocomplete
     */
    @Override
    public PlaceAutocomplete getItem(int position) {
        Log.d("PlaceArrayAdapterContro","PlaceAutoComplete");

        return mResultList.get(position);
    }

    /**
     * Returns a list of predictions based on the input provided y the user
     *
     * @param constraint
     * @return
     */
    private ArrayList<PlaceAutocomplete> getPredictions(CharSequence constraint) {
        Log.d("PlaceArrayAdapterContro","getPredictions");

        if (mGoogleApiClient != null) {
            Log.i(TAG, "Executing autocomplete query for: " + constraint);
            PendingResult<AutocompletePredictionBuffer> results =
                    Places.GeoDataApi
                            .getAutocompletePredictions(mGoogleApiClient, constraint.toString(),
                                    mBounds, mPlaceFilter);
            // Wait for predictions, set the timeout.
            AutocompletePredictionBuffer autocompletePredictions = results
                    .await(60, TimeUnit.SECONDS);
            final Status status = autocompletePredictions.getStatus();
            if (!status.isSuccess()) {
                Toast.makeText(getContext(), "Error: " + status.toString(),
                        Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Error getting place predictions: " + status
                        .toString());
                autocompletePredictions.release();
                return null;
            }

            Log.i(TAG, "Query completed. Received " + autocompletePredictions.getCount()
                    + " predictions.");
            Iterator<AutocompletePrediction> iterator = autocompletePredictions.iterator();
            ArrayList resultList = new ArrayList<>(autocompletePredictions.getCount());
            while (iterator.hasNext()) {
                AutocompletePrediction prediction = iterator.next();
                //noinspection unchecked
                resultList.add(new PlaceAutocomplete(prediction.getPlaceId(),
                        prediction.getFullText(null)));
            }
            // Buffer release
            autocompletePredictions.release();
            //noinspection unchecked
            return resultList;
        }
        Log.e(TAG, "Google API client is not connected.");
        return null;
    }

    /**
     * Calls getPredictions() method and filters the results to show only
     * the suggestions return by getPredictions()
     *
     * @return Filter
     */
    @NonNull
    @Override
    public Filter getFilter() {
        Log.d("PlaceArrayAdapterContro","getFilter");
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if (constraint != null) {
                    // Query the autocomplete API for the entered constraint
                    mResultList = getPredictions(constraint);
                    if (mResultList != null) {
                        // Results
                        results.values = mResultList;
                        results.count = mResultList.size();
                    }
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    // The API returned at least one result, update the data.
                    notifyDataSetChanged();
                } else {
                    // The API did not return any results, invalidate the data set.
                    notifyDataSetInvalidated();
                }
            }
        };
    }

    public class PlaceAutocomplete {

        public final CharSequence placeId;
        public final CharSequence description;

        PlaceAutocomplete(CharSequence placeId, CharSequence description) {
            Log.d("PlaceArrayAdapterContro","PlaceAutocomplete");

            this.placeId = placeId;
            this.description = description;
        }

        @Override
        public String toString() {
            return description.toString();
        }
    }
}