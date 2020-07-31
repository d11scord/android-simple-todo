package com.example.maxutkalove;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewMainAdapter.ItemClickListener {

    List<String> data;
    RecyclerViewMainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        data = new ArrayList<>();
        data.add("Horse");
        data.add("Cow");
        data.add("Camel");
        data.add("Sheep");
        data.add("Goat");

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler_view_main);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewMainAdapter(this, data);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {

        switch (view.getId()) {
            // Click on recycler item
            // TODO: open new activity with some information
            case R.id.item_todo_text:
                Toast.makeText(
                    MainActivity.this,
                    "You clicked " + adapter.getItem(position) + " on row number " + position,
                    Toast.LENGTH_SHORT
                ).show();
                break;
            // Click on menu button
            case R.id.item_todo_menu:
                PopupMenu popup = new PopupMenu(MainActivity.this, view);
                popup.getMenuInflater().inflate(R.menu.menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(
                            MainActivity.this,
                            item.getTitle(),
                            Toast.LENGTH_SHORT
                        ).show();
                        return true;
                    }
                });
            popup.show();
        }
    }
}

