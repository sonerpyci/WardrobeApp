package tr.edu.yildiz.payci.soner.wardrobe.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import tr.edu.yildiz.payci.soner.wardrobe.CombineFormActivity;
import tr.edu.yildiz.payci.soner.wardrobe.R;
import tr.edu.yildiz.payci.soner.wardrobe.entities.Combine;


public class CombineAdapter extends RecyclerView.Adapter<CombineAdapter.MyViewHolder> {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private Context context;
    public boolean fromActivity = false;
    private List<Combine> combines;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, content;

        MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.combine_name);
        }
    }

    public CombineAdapter(Context context, List<Combine> combines) {
        this.context = context;
        this.combines = combines;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_combine_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Combine item = combines.get(position);
        holder.name.setText(item.getName());

        holder.itemView.setBackgroundColor(item.getIsSelected() ? Color.CYAN : Color.WHITE);

        holder.itemView.setOnClickListener(view -> {
            if (fromActivity) {
                Intent intent = new Intent(holder.itemView.getContext(), CombineFormActivity.class);
                intent.putExtra("combineGuid", item.getGuid());
                holder.itemView.getContext().startActivity(intent);
            } else {
                item.setIsSelected(!item.getIsSelected());
                holder.itemView.setBackgroundColor(item.getIsSelected() ? Color.CYAN : Color.WHITE);
            }

        });

        holder.itemView.setOnLongClickListener(v -> {
            if (fromActivity) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Dikkat")
                        .setMessage("L??tfen Uygulamak istedi??iniz i??lemi se??iniz.")
                        .setNeutralButton("Geri", (dialog, which) -> {

                        })
                        .setNegativeButton("Sil", (dialog, which) -> {
                            // TODO : SURE TO DELETE COMBINE ?
                            final AlertDialog.Builder builder2 = new AlertDialog.Builder(builder.getContext());
                            builder2.setTitle("Kombin Silme ????lemi")
                                    .setMessage("Kombini silmek istedi??inize emin misiniz?")
                                    .setNeutralButton("Hay??r", (dialog1, which1) -> {

                                    }).setPositiveButton("Evet", (dialog12, which12) -> {
                                //TODO : DELETE COMBINE
                                database.getReference().child("combines").child(item.getGuid()).removeValue();
                            }).show();
                        });
                builder.create().show();
            }

            return true;
        });
    }

    @Override
    public int getItemCount() {
        return combines.size();
    }

    public ArrayList<Combine> getItems() {
        return (ArrayList<Combine>) combines;
    }

    public ArrayList<Combine> getSelectedItems() {
        ArrayList<Combine> selectedCombines = new ArrayList<>();
        for (Combine combine: getItems()) {
            if (combine.getIsSelected()) {
                selectedCombines.add(combine);
            }
        }
        return selectedCombines;
    }

    public void empty() {
        combines = new ArrayList<>();
    }

    public void addItem(Combine combine) {
        combines.add(combine);
    }

}