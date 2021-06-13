package tr.edu.yildiz.payci.soner.wardrobe.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tr.edu.yildiz.payci.soner.wardrobe.R;
import tr.edu.yildiz.payci.soner.wardrobe.entities.Clothing;


public class ClothingAdapter extends RecyclerView.Adapter<ClothingAdapter.MyViewHolder> {
    private Context context;
    private List<Clothing> clothes;

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

    public ClothingAdapter(Context context, List<Clothing> clothes) {
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


        byte[] imageBytes = Base64.decode(item.getPhoto().getBase64EncodedContent(), Base64.NO_WRAP);
        Bitmap bmp = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        holder.image.setImageBitmap(Bitmap.createScaledBitmap(bmp, holder.image.getWidth(), holder.image.getHeight(), false));

    }


    @Override
    public int getItemCount() {
        return clothes.size();
    }

}