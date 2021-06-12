package tr.edu.yildiz.payci.soner.wardrobe.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import tr.edu.yildiz.payci.soner.wardrobe.CombineFormActivity;
import tr.edu.yildiz.payci.soner.wardrobe.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WardrobeFragment extends Fragment {
    View rootLayout;
    View newDrawerButton;

    public WardrobeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootLayout = inflater.inflate(R.layout.fragment_wardrobe, container, false);

        defineElements();
        defineListeners();
        return rootLayout;
    }

    public void defineElements() {
        newDrawerButton = rootLayout.findViewById(R.id.new_drawer_btn);

    }

    public void defineListeners() {
        newDrawerButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CombineFormActivity.class);
            this.startActivity(intent);
        });
    }

}
