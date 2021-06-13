package tr.edu.yildiz.payci.soner.wardrobe.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tr.edu.yildiz.payci.soner.wardrobe.R;
import tr.edu.yildiz.payci.soner.wardrobe.entities.Drawer;



public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.MyViewHolder> {
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
    }


    @Override
    public int getItemCount() {
        return drawers.size();
    }

}