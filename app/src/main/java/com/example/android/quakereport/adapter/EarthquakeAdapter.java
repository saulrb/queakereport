package com.example.android.quakereport.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.graphics.drawable.GradientDrawable;

import com.example.android.quakereport.R;
import com.example.android.quakereport.model.Earthquake;

import java.util.ArrayList;

/**
 * Created by saul on 7/1/2017.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    private static final String LOG_TAG = EarthquakeAdapter.class.getSimpleName();

    /**
     * My constructor for override this class so it can inject the list of elements I need
     * @param context
     * @param earthquakeArrayList
     */
    public EarthquakeAdapter(Activity context,  ArrayList<Earthquake> earthquakeArrayList) {
        super(context, 0, earthquakeArrayList);
    }

    /***
     * Override this method so the view will be showed with prover values
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_earthquake_item,parent,false);
        }
        Earthquake currentEarthquake = getItem(position);
        //find element for magnitud in the listitem layout and assign its current value
        TextView magTextView = (TextView) listItemView.findViewById(R.id.mag);
        magTextView.setText(currentEarthquake.getMagFormatted());

        GradientDrawable magnitudeCircle = (GradientDrawable) magTextView.getBackground();
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMag());

        magnitudeCircle.setColor(magnitudeColor);

        // Populate offset of the location
        String[] place = currentEarthquake.getPlace().split("of");
        if(place.length > 1) {
            TextView offsetTextView = (TextView) listItemView.findViewById(R.id.offset);
            offsetTextView.setText(place[0].concat("of"));
            //find element for place in the listitem layout and assign its current value
            TextView placeTextView = (TextView) listItemView.findViewById(R.id.place);
            placeTextView.setText(place[1]);
        }else{
            //find element for place in the listitem layout and assign its current value
            TextView placeTextView = (TextView) listItemView.findViewById(R.id.place);
            placeTextView.setText(currentEarthquake.getPlace());
        }
        //find element time in the listitem layout and assign its current value
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date);
        dateTextView.setText(currentEarthquake.getDate());
        // find element time in the listitem layout and assign its current value
        TextView timeTextView = (TextView) listItemView.findViewById(R.id.time);
        timeTextView.setText(currentEarthquake.getTime());

        return listItemView;
    }

    private int getMagnitudeColor(double mag){
        int magnitudeColorResourceId = 0;
        int magnitudeFloor = (int) Math.floor(mag);
        switch (magnitudeFloor){
            case 0:
            case 1: magnitudeColorResourceId = R.color.magnitude1; break;
            case 2: magnitudeColorResourceId = R.color.magnitude2; break;
            case 3: magnitudeColorResourceId = R.color.magnitude3; break;
            case 4: magnitudeColorResourceId = R.color.magnitude4; break;
            case 5: magnitudeColorResourceId = R.color.magnitude5; break;
            case 6: magnitudeColorResourceId = R.color.magnitude6; break;
            case 7: magnitudeColorResourceId = R.color.magnitude7; break;
            case 8: magnitudeColorResourceId = R.color.magnitude8; break;
            case 9: magnitudeColorResourceId = R.color.magnitude9; break;
            default: magnitudeColorResourceId = R.color.magnitude10; break;
        }
        return  ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }
}
