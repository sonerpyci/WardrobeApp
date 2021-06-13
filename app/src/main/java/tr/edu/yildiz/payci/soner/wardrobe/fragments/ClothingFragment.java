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

import tr.edu.yildiz.payci.soner.wardrobe.ClothingFormActivity;
import tr.edu.yildiz.payci.soner.wardrobe.R;
import tr.edu.yildiz.payci.soner.wardrobe.adapters.ClothingAdapter;
import tr.edu.yildiz.payci.soner.wardrobe.entities.Clothing;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClothingFragment extends Fragment {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private ClothingAdapter mAdapter;
    View rootLayout;
    View newClothingButton;
    ArrayList<Clothing> clothes;


    public ClothingFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootLayout = inflater.inflate(R.layout.fragment_clothing, container, false);

        defineElements();

        RecyclerView recyclerView = rootLayout.findViewById(R.id.recycler_view_clothes);
        mAdapter = new ClothingAdapter(getContext(), clothes);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);



        defineListeners();
        return rootLayout;
    }

    public void defineElements() {
        newClothingButton = rootLayout.findViewById(R.id.new_clothing_btn);
        clothes = new ArrayList<>();
        database.getReference().child("clothes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (mAdapter.getItemCount() > 0) {
                    clothes = new ArrayList<>();
                    mAdapter.empty();
                }

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Clothing clothing = postSnapshot.getValue(Clothing.class);
                    mAdapter.addItem(clothing);
                }
                for (int i=0; i<clothes.size(); i++)
                {
                    Log.d("FirebaseRead", "Drawer with Name: " + clothes.get(i).getName());
                }
                clothes = mAdapter.getItems();
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("error",error.getMessage());
            }
        });
    }

    public void defineListeners() {
        newClothingButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ClothingFormActivity.class);
            this.startActivity(intent);
        });
    }

}
