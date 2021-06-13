package tr.edu.yildiz.payci.soner.wardrobe.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import tr.edu.yildiz.payci.soner.wardrobe.DrawerFormActivity;
import tr.edu.yildiz.payci.soner.wardrobe.R;
import tr.edu.yildiz.payci.soner.wardrobe.entities.Drawer;



public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.MyViewHolder> {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private Context context;
    private List<Drawer> drawers;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, content;

        MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.drawer_name);
            content = view.findViewById(R.id.drawer_content);

        }
    }

    public DrawerAdapter(Context context, List<Drawer> drawers) {
        this.context = context;
        this.drawers = drawers;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_drawer_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Drawer item = drawers.get(position);
        holder.name.setText(item.getName());
        if (item.getClothes().size() > 0 ) {
            holder.content.setText(String.format("Bu çekmecede %d adet kıyafet bulunuyor.", item.getClothes().size()));
        }
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(holder.itemView.getContext(), DrawerFormActivity.class);
            intent.putExtra("drawerGuid", item.getGuid());
            holder.itemView.getContext().startActivity(intent);
        });

        holder.itemView.setOnLongClickListener(v -> {
            final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Dikkat")
                    .setMessage("Lütfen Uygulamak istediğiniz işlemi seçiniz.")
                    .setNeutralButton("Geri", (dialog, which) -> {

                    })
                    .setNegativeButton("Sil", (dialog, which) -> {
                        // TODO : SURE TO DELETE DRAWER ?
                        final AlertDialog.Builder builder2 = new AlertDialog.Builder(builder.getContext());
                        builder2.setTitle("Çekmece Silme İşlemi")
                                .setMessage("Çekmeyeci silmek istediğinize emin misiniz?")
                                .setNeutralButton("Hayır", (dialog1, which1) -> {

                                }).setPositiveButton("Evet", (dialog12, which12) -> {
                                    //TODO : DELETE DRAWER
                                    database.getReference().child("drawers").child(item.getGuid()).removeValue();
                                }).show();
                    });
            builder.create().show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return drawers.size();
    }

    public ArrayList<Drawer> getItems() {
        return (ArrayList<Drawer>) drawers;
    }


    public void empty() {
        drawers = new ArrayList<>();
    }

    public void addItem(Drawer drawer) {
        drawers.add(drawer);
    }

}