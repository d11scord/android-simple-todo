package com.example.maxutkalove;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewMainAdapter.ItemClickListener {

    final int ADD = 1;
    final int EDT = 2;
    final int DEL = 3;

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

        View.OnClickListener onClickListener = new View.OnClickListener() {
            public void onClick(View view) {
                int position = adapter.getItemCount();
                showAlertDialogButtonClicked(view, "Add todo", ADD, position);
            }
        };

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(onClickListener);
    }

    @Override
    public void onItemClick(final View view, final int position) {

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
                        switch (item.getItemId()) {
                            case R.id.menu_edit_todo:
                                showAlertDialogButtonClicked(view, "Edit todo", EDT, position);
                                break;
                            case R.id.menu_delete_todo:
                                showAlertDialogButtonClicked(view, "Delete todo", DEL, position);
                                break;
                        }
                        return true;
                    }
                });
                popup.show();
        }
    }

    public void showAlertDialogButtonClicked(View view, String title, final int type, final int position) {

        // create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        // set the custom layout
        final View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_edit, null);
        builder.setView(dialogView);

        // add a button
        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case Dialog.BUTTON_POSITIVE:
                        // send data from the AlertDialog to the Activity
                        EditText editText = dialogView.findViewById(R.id.dialog_add_edit_todo);
                        sendDialogDataToActivity(editText.getText().toString(), type, position);
                        break;
                    case Dialog.BUTTON_NEUTRAL:
                        break;
                }
            }
        };

        if (type == DEL) {
            TextView deleteText = dialogView.findViewById(R.id.dialog_delete_todo);
            deleteText.setText("Are you sure you want to delete this beautiful todo? This cannot be undone.");
            dialogView.findViewById(R.id.dialog_delete_todo).setVisibility(View.VISIBLE);
            dialogView.findViewById(R.id.dialog_add_edit_todo).setVisibility(View.GONE);
        }
        String positiveBtn = type == DEL ? "DELETE" : "SAVE";
        builder.setPositiveButton(positiveBtn, onClickListener);
        builder.setNeutralButton("CANCEL", onClickListener);
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // do something with the data coming from the AlertDialog
    private void sendDialogDataToActivity(String str, int type, int position) {

        switch (type) {
            case ADD:
                data.add(position, str);
                adapter.notifyItemInserted(position);
                break;
            case EDT:
                data.set(position, str);
                adapter.notifyItemChanged(position);
                break;
            case DEL:
                data.remove(position);
                adapter.notifyItemRemoved(position);
        }
    }
}