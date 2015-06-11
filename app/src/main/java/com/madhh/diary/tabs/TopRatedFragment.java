package com.madhh.diary.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.madhh.diary.R;

/*first tag that display lost of all dates
Create ArrayAdapter and populates all the elements in the list.

*/
public class TopRatedFragment extends Fragment {

    private ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_top_rated, container, false);
        rootView.findViewById(R.id.action_settings);
        //listView = (ListView) rootView.findViewById(R.id.dateList);
        listView = (ListView) rootView.findViewById(R.id.dateList);
        populateListView();
        return rootView;
    }

    //Create list of items and populate list of dates here.
    private void populateListView()
    {

        //create List of Items
        String[] demoDates = {"Jan 1st", "Jan 2nd", "Jan 3rd", "Jan 4th", "Jan 5th",
                "Jan 6th", "Jan 7th", "Jan 8th", "Jan 9th", "Jan 10th",
                "Jan 11th", "Jan 12th", "Jan 13th", "Jan 14th", "Jan 15th"};

        //build adapter
        ArrayAdapter<String> dateAdapter;
        dateAdapter = new ArrayAdapter<String>(
                this.getActivity(), //context for the activity
                R.layout.date_text_view, // layout to be used
                demoDates); // items to be displayed



        //configure the list view
        listView.setAdapter(dateAdapter);

    }
}
