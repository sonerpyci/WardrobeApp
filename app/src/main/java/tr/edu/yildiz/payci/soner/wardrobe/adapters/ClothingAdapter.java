package tr.edu.yildiz.payci.soner.wardrobe.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import tr.edu.yildiz.payci.soner.wardrobe.R;
import tr.edu.yildiz.payci.soner.wardrobe.dal.StorageHelper;
import tr.edu.yildiz.payci.soner.wardrobe.entities.Clothing;


public class ClothingAdapter extends RecyclerView.Adapter<ClothingAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Clothing> clothes;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, content;
        ImageView image;

        MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.clothing_name);
            content = view.findViewById(R.id.clothing_content);
            image = view.findViewById(R.id.adapter_clothing_image_view);
        }
    }

    public ClothingAdapter(Context context, ArrayList<Clothing> clothes) {
        this.context = context;
        this.clothes = clothes;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_clothing_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Clothing item = clothes.get(position);
        holder.name.setText(item.getName());
        if (item.getPrice() > 0 ) {
            holder.content.setText(String.format("%s bölgesi için. %.2f tutarında.", item.getType(), item.getPrice()));
        } else {
            holder.content.setText(String.format("%s bölgesi için. Ücret Bilgisi yok.", item.getType()));
        }

        holder.itemView.setBackgroundColor(item.isSelected() ? Color.CYAN : Color.WHITE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(v.getContext(), "clicked", Toast.LENGTH_SHORT).show();

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), String.format("clicked successfully."), Toast.LENGTH_SHORT).show();
                item.setSelected(!item.isSelected());
                holder.itemView.setBackgroundColor(item.isSelected() ? Color.CYAN : Color.WHITE);
            }
        });

        if (item.getPhoto() != null) {

            StorageReference ref = StorageHelper.getStorageEngine().getReference("photos").child(item.getPhoto().getUid());
            // Load the image using Glide
            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    String imageURL = uri.toString();
                    Glide.with(holder.itemView.getContext()).load(imageURL).into(holder.image);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });
        }


    }


    @Override
    public int getItemCount() {
        return clothes.size();
    }

    public ArrayList<Clothing> getItems() {
        return clothes;
    }
    public ArrayList<Clothing> getSelectedItems() {
        ArrayList<Clothing> selectedClothes = new ArrayList<>();
        for (Clothing clothing: getItems()) {
            if (clothing.isSelected()) {
                selectedClothes.add(clothing);
            }
        }
        return selectedClothes;
    }

    public void empty() {
        clothes = new ArrayList<>();
    }

    public void addItem(Clothing clothing) {
        clothes.add(clothing);
    }

}