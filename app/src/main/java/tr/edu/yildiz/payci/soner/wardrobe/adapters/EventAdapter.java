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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import tr.edu.yildiz.payci.soner.wardrobe.EventFormActivity;
import tr.edu.yildiz.payci.soner.wardrobe.R;
import tr.edu.yildiz.payci.soner.wardrobe.entities.Event;


public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final Calendar myCalendar = Calendar.getInstance();
    private Context context;
    private List<Event> events;
    String dateFormat = "dd/MM/yyyy";

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, content;

        MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.event_name);
            content = view.findViewById(R.id.event_content);

        }
    }

    public EventAdapter(Context context, List<Event> events) {
        this.context = context;
        this.events = events;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_event_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Event item = events.get(position);
        holder.name.setText(item.getName());
        if (item.getDate() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
            holder.content.setText(String.format("İsim: %s, Tür: %s, Tarih: %s", item.getName(), item.getType(), sdf.format(item.getDate())));
        } else {
            holder.content.setText(String.format("İsim: %s, Tür: %s", item.getName(), item.getType()));
        }

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(holder.itemView.getContext(), EventFormActivity.class);
            intent.putExtra("eventGuid", item.getGuid());
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
                        builder2.setTitle("Etkinlik Silme İşlemi")
                                .setMessage("Etkinliği silmek istediğinize emin misiniz?")
                                .setNeutralButton("Hayır", (dialog1, which1) -> {

                                }).setPositiveButton("Evet", (dialog12, which12) -> {
                                    //TODO : DELETE DRAWER
                                    database.getReference().child("events").child(item.getGuid()).removeValue();
                                }).show();
                    });
            builder.create().show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public ArrayList<Event> getItems() {
        return (ArrayList<Event>) events;
    }


    public void empty() {
        events = new ArrayList<>();
    }

    public void addItem(Event event) {
        events.add(event);
    }

}