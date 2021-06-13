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

import java.util.ArrayList;

import tr.edu.yildiz.payci.soner.wardrobe.CombineFormActivity;
import tr.edu.yildiz.payci.soner.wardrobe.R;
import tr.edu.yildiz.payci.soner.wardrobe.adapters.CombineAdapter;
import tr.edu.yildiz.payci.soner.wardrobe.entities.Combine;

/**
 * A simple {@link Fragment} subclass.
 */
public class CabinetFragment extends Fragment {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    View rootLayout;
    View newCombineButton;
    View shareCombineButton;
    private CombineAdapter mAdapter;
    ArrayList<Combine> combines;

    public CabinetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootLayout = inflater.inflate(R.layout.fragment_cabinet, container, false);

        defineElements();

        RecyclerView recyclerView = rootLayout.findViewById(R.id.recycler_view_combines);
        mAdapter = new CombineAdapter(getContext(), combines);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        defineListeners();
        return rootLayout;
    }

    public void defineElements() {
        newCombineButton = rootLayout.findViewById(R.id.new_combine_btn);
        shareCombineButton = rootLayout.findViewById(R.id.share_combine_btn);
        combines = new ArrayList<>();
        database.getReference().child("combines").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (mAdapter.getItemCount() > 0) {
                    combines = new ArrayList<>();
                    mAdapter.empty();
                }

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Combine combine = postSnapshot.getValue(Combine.class);
                    mAdapter.addItem(combine);
                }
                for (int i=0; i<combines.size(); i++)
                {
                    Log.d("FirebaseRead", "Combine with Name: " + combines.get(i).getName());
                }
                combines = mAdapter.getItems();
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("error",error.getMessage());
            }
        });
    }

    public void defineListeners() {
        newCombineButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CombineFormActivity.class);
            this.startActivity(intent);
        });

        shareCombineButton.setOnClickListener(v -> {
            // TODO : Fill the listener func.
        });
    }

}
