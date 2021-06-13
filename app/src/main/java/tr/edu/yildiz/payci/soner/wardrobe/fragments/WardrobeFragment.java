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

import tr.edu.yildiz.payci.soner.wardrobe.DrawerFormActivity;
import tr.edu.yildiz.payci.soner.wardrobe.R;
import tr.edu.yildiz.payci.soner.wardrobe.adapters.DrawerAdapter;
import tr.edu.yildiz.payci.soner.wardrobe.entities.Drawer;

/**
 * A simple {@link Fragment} subclass.
 */
public class WardrobeFragment extends Fragment {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DrawerAdapter mAdapter;
    View rootLayout;
    View newDrawerButton;
    ArrayList<Drawer> drawers;





    public WardrobeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootLayout = inflater.inflate(R.layout.fragment_wardrobe, container, false);

        defineElements();

        RecyclerView recyclerView = rootLayout.findViewById(R.id.recycler_view_drawers);
        mAdapter = new DrawerAdapter(getContext(), drawers);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        defineListeners();
        return rootLayout;
    }

    public void defineElements() {
        newDrawerButton = rootLayout.findViewById(R.id.new_drawer_btn);
        drawers = new ArrayList<>();
        database.getReference().child("drawers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (mAdapter.getItemCount() > 0) {
                    drawers = new ArrayList<>();
                    mAdapter.empty();
                }

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Drawer drawer = postSnapshot.getValue(Drawer.class);
                    mAdapter.addItem(drawer);
                }
                for (int i=0; i<drawers.size(); i++)
                {
                    Log.d("FirebaseRead", "Drawer with Name: " + drawers.get(i).getName());
                }
                drawers = mAdapter.getItems();
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("error",error.getMessage());
            }
        });

    }

    public void defineListeners() {
        newDrawerButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), DrawerFormActivity.class);
            this.startActivity(intent);
        });
    }

}
