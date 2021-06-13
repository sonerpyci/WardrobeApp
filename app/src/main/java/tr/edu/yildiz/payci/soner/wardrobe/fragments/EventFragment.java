package tr.edu.yildiz.payci.soner.wardrobe.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

import tr.edu.yildiz.payci.soner.wardrobe.EventFormActivity;
import tr.edu.yildiz.payci.soner.wardrobe.R;
import tr.edu.yildiz.payci.soner.wardrobe.adapters.EventAdapter;
import tr.edu.yildiz.payci.soner.wardrobe.adapters.SliderAdapter;
import tr.edu.yildiz.payci.soner.wardrobe.entities.Event;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private EventAdapter mAdapter;
    View newEventButton;
    ArrayList<Event> events;
    View rootLayout;
    private SliderView sliderView;

    public EventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootLayout = inflater.inflate(R.layout.fragment_event, container, false);

        defineElements();


        RecyclerView recyclerView = rootLayout.findViewById(R.id.recycler_view_events);
        mAdapter = new EventAdapter(getContext(), events);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        sliderView = rootLayout.findViewById(R.id.slider_view);
        final SliderAdapter adapter = new SliderAdapter(getActivity());
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimations.SLIDE);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.startAutoCycle();


        defineListeners();

        return rootLayout;
    }

    public void defineElements() {
        newEventButton = rootLayout.findViewById(R.id.new_event_btn);
        events = new ArrayList<>();
        database.getReference().child("events").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (mAdapter.getItemCount() > 0) {
                    events = new ArrayList<>();
                    mAdapter.empty();
                }

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Event event = postSnapshot.getValue(Event.class);
                    mAdapter.addItem(event);
                }
                for (int i=0; i<events.size(); i++)
                {
                    Log.d("FirebaseRead", "Event with Name: " + events.get(i).getName());
                }
                events = mAdapter.getItems();
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("error",error.getMessage());
            }
        });


    }

    public void defineListeners() {
        newEventButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), EventFormActivity.class);
            this.startActivity(intent);
        });
    }
}
