package com.example.reminders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DialogCustom.DialogListener {

    private String reminderDescription;
    private Boolean important;
    ListView listView;


    @Override
    public void communicateWithMain(String reminderDesc, Boolean impor, Boolean deleteFlag, int idToBeDeleted) {
        if(!deleteFlag)
            openEditDialog(reminderDesc, impor);
        else
            deleteReminder(idToBeDeleted);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.listView);

        final ArrayList<String> description = new ArrayList<>();
        description.add("android");
        description.add("is");
        description.add("great");
        description.add("android");
        description.add("is");
        description.add("great");

        final ArrayList<Integer> imgId = new ArrayList<>();
        imgId.add(R.color.Green);
        imgId.add(R.color.Red);
        imgId.add(R.color.Green);
        imgId.add(R.color.Red);
        imgId.add(R.color.Green);
        imgId.add(R.color.Green);

        CustomListView customListView = new CustomListView(this, description, imgId);
        listView.setAdapter(customListView);

    }

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.listView);

        final ArrayList<String> description = new ArrayList<>();
        description.add("android");
        description.add("is");
        description.add("great");
        description.add("android");
        description.add("is");
        description.add("great");
        description.add("android");
        description.add("is");
        description.add("great");
        description.add("android");
        description.add("is");
        description.add("great");

        final ArrayList<Integer> imgId = new ArrayList<>();
        imgId.add(R.color.Green);
        imgId.add(R.color.Red);
        imgId.add(R.color.Green);
        imgId.add(R.color.Red);
        imgId.add(R.color.Green);
        imgId.add(R.color.Green);
        imgId.add(R.color.Green);
        imgId.add(R.color.Red);
        imgId.add(R.color.Green);
        imgId.add(R.color.Red);
        imgId.add(R.color.Green);
        imgId.add(R.color.Green);
        CustomListView customListView = new CustomListView(this, description, imgId);
        listView.setAdapter(customListView);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(imgId.get(position) == R.color.Red) {
                    openAlertDialog(description.get(position), true, position);
                }
                else {
                    openAlertDialog(description.get(position), false, position);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_element_1:
                openDialog();
                return true;

            case R.id.menu_element_2:
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void openDialog(){
        DialogCustom Dialog = new DialogCustom(1, "", false);
        Dialog.show(getSupportFragmentManager(), "Dialog");

    }

    public void openEditDialog(String text, Boolean isChecked){
        DialogCustom Dialog = new DialogCustom(0, text, isChecked);
        Dialog.show(getSupportFragmentManager(), "Dialog");
    }

    public void openAlertDialog(String text, Boolean isChecked, int idToBeDeleted){
        AlertDialog alertDialog = new AlertDialog(text, isChecked, idToBeDeleted);
        alertDialog.show(getSupportFragmentManager(), "dialog");
    }

    public void deleteReminder(int idToBeDeleted){
        //TODO
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}
