package tr.edu.yildiz.payci.soner.wardrobe;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import tr.edu.yildiz.payci.soner.wardrobe.adapters.CombineAdapter;
import tr.edu.yildiz.payci.soner.wardrobe.entities.Combine;
import tr.edu.yildiz.payci.soner.wardrobe.entities.Event;
import tr.edu.yildiz.payci.soner.wardrobe.entities.Location;

public class EventFormActivity extends AppCompatActivity {
    private CombineAdapter mAdapter;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final Calendar myCalendar = Calendar.getInstance();
    View saveEventButton;
    TextView eventNameTxtElement;
    Spinner eventTypeSpinner;
    TextView eventDateElement;
    ArrayList<Combine> combines;
    ArrayAdapter<String> eventTypeAdapter;
    Event event;
    String eventGuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_form);
        defineElements();

        RecyclerView recyclerView = this.findViewById(R.id.recycler_view_combines_list);
        mAdapter = new CombineAdapter(getApplicationContext(), combines);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setClickable(true);
        recyclerView.setFocusable(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        mAdapter.fromActivity=false;
        recyclerView.setAdapter(mAdapter);


        defineListeners();

    }

    private void updateDatePickerOnSelect() {
        eventDateElement = findViewById(R.id.event_date_input);
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        eventDateElement.setText(sdf.format(myCalendar.getTime()));
    }

    public void defineElements(){
        saveEventButton =  this.findViewById(R.id.save_event_btn);
        eventNameTxtElement = this.findViewById(R.id.event_name_text);
        eventTypeSpinner = this.findViewById(R.id.event_type_spinner);
        eventDateElement = findViewById(R.id.event_date_input);

        String[] items = new String[]{"Sinema", "Tiyatro", "Spor", "Sempozyum", "Gezi", "Tatil", "Diğer"};
        eventTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        eventTypeSpinner.setAdapter(eventTypeAdapter);


        combines = new ArrayList<>();

        if (getIntent().hasExtra("eventGuid")) {
            Bundle extras = getIntent().getExtras();
            eventGuid = extras.getString("eventGuid");
        }

        if (eventGuid == null) {
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
                        Log.d("FirebaseRead", "Drawer with Name: " + combines.get(i).getName());
                    }
                    combines = mAdapter.getItems();
                    mAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Log.e("error",error.getMessage());
                }
            });

        } else {

            FirebaseDatabase.getInstance()
                    .getReference("combines").get().continueWith(task -> {
                if (task.isCanceled()) {
                    // Handle the error...
                }
                else if (task.isComplete()) {
                    DataSnapshot combinesSnapshot = task.getResult();
                    for (DataSnapshot postSnapshot : combinesSnapshot.getChildren()) {
                        Combine combines = postSnapshot.getValue(Combine.class);
                        mAdapter.addItem(combines);
                    }
                    for (int i = 0; i < combines.size(); i++) {
                        Log.d("FirebaseRead", "Combine with Name: " + combines.get(i).getName());
                    }
                    combines = mAdapter.getItems();
                    mAdapter.notifyDataSetChanged();

                    database.getReference().child("events").child(eventGuid).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null) {
                                event = dataSnapshot.getValue(Event.class);

                                eventNameTxtElement.setText(event.getName());
                                eventTypeSpinner.setSelection(eventTypeAdapter.getPosition(event.getType()));

                                String myFormat = "dd/MM/yyyy"; //In which you need put here
                                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                                eventDateElement.setText(sdf.format(event.getDate()));

                                for (Combine combine : mAdapter.getItems()) {
                                    for (Combine eventCombine : event.getCombines()) {
                                        if (eventCombine.getGuid().equals(combine.getGuid())) {
                                            combine.setIsSelected(true);
                                        }
                                    }
                                }

                            }

                            for (int i=0; i<combines.size(); i++)
                            {
                                Log.d("FirebaseRead", "Drawer with Name: " + combines.get(i).getName());
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
                return task;
            });



        }


    }

    public void defineListeners(){
        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDatePickerOnSelect();
        };

        eventDateElement.setOnClickListener((v) -> {
            new DatePickerDialog(EventFormActivity.this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        saveEventButton.setOnClickListener(v -> {
            String eventName = eventNameTxtElement.getText().toString();
            String eventType = eventTypeSpinner.getSelectedItem().toString();
            String eventDateText = eventDateElement.getText().toString();
            Date eventDate = null;

            try {
                eventDate = new SimpleDateFormat("dd/MM/yyyy", Locale.US).parse(eventDateText);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //String Location = eventNameTxtElement.getText().toString();

            if (event == null) {
                event = new Event(eventName, eventType, eventDate, new Location(35, 48) );

                DatabaseReference ref = database.getReference().child("events").push();
                event.setGuid(ref.getKey());
                event.setCombines(mAdapter.getSelectedItems());
                ref.setValue(event, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        Log.e("ERROR_INSERT", "Failed to write Event", databaseError.toException());
                    }
                });
            } else {
                DatabaseReference ref = database.getReference().child("events").child(event.getGuid());
                event.setName(eventName);
                event.setType(eventType);
                event.setDate(eventDate);
                event.setLocation(new Location(55, 46));
                event.setCombines(mAdapter.getSelectedItems());
                ref.setValue(event, (databaseError, databaseReference) -> {
                    if (databaseError != null) {
                        Log.e("ERROR_INSERT", "Failed to write Event", databaseError.toException());
                    }
                });
            }

            finish();
            super.onBackPressed();
        });


    }




}