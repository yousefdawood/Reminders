package com.example.reminders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements DialogCustom.DialogListener{

    RemindersDbAdapter dbAdapter;
    RemindersSimpleCursorAdapter cursorAdapter;
    ListView listView;
    Reminder currentReminder;

    @Override
    public void editReminder(String reminderDescription, Boolean important) {
        currentReminder.setContent(reminderDescription);
        if (important)
            currentReminder.setImportant(1);
        else
            currentReminder.setImportant(0);

        dbAdapter.updateReminder(currentReminder);
        updateReminders();
    }

    public void deleteReminder(){
        dbAdapter.deleteReminderById(currentReminder.getId());
        updateReminders();
    }


    @Override
    public void passNewReminder(String reminderDescription, Boolean important) {
        dbAdapter.createReminder(reminderDescription, important);
        updateReminders();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbAdapter = new RemindersDbAdapter(this);
        dbAdapter.open();
        Cursor cursor = dbAdapter.fetchAllReminders();
        String s = DatabaseUtils.dumpCursorToString(cursor);
        listView  = (ListView)findViewById(R.id.listView);
        int [] id = {R.id.text};
        System.out.print(id[0]);
        String[] content = new String[] {dbAdapter.COL_CONTENT};
        cursorAdapter = new RemindersSimpleCursorAdapter(this, R.layout.reminder_row, cursor, content, id, 0);
        listView.setAdapter(cursorAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateReminders();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                int remId = cursor.getInt(dbAdapter.INDEX_ID);
                String remContent = cursor.getString(dbAdapter.INDEX_CONTENT);
                int remImportant = cursor.getInt(dbAdapter.INDEX_IMPORTANT);
                currentReminder = new Reminder(remId, remContent, remImportant);
                PopUp popUp = new PopUp(MainActivity.this);
                popUp.show(getSupportFragmentManager(), "dialog");
            }
        });
    }

    public void updateReminders(){
        Cursor c = dbAdapter.fetchAllReminders();
        DatabaseUtils.dumpCursorToString(c);
        cursorAdapter.changeCursor(c);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_element_1:
                openDialog();
                return true;

            case R.id.menu_element_2:
                onDestroy();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
        dbAdapter.close();
    }

    public void openDialog(){
        DialogCustom Dialog = new DialogCustom(1, "", 0);
        Dialog.show(getSupportFragmentManager(), "Dialog");
    }

    public void openEditDialog(){
        DialogCustom Dialog = new DialogCustom(0, currentReminder.getContent(), currentReminder.getImportant());
        Dialog.show(getSupportFragmentManager(), "Dialog");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}
