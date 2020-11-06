package com.example.androidlabs;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {
    public ArrayList<Message> elements = new ArrayList<>();
    ListAdapter listAdapter = new MessageListAdapter();
    SQLiteDatabase db;
    public ChatRoomActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        final ListView mListView = (ListView) findViewById(R.id.messageList);
        final EditText editt = findViewById(R.id.messageeneter);
        Button send = findViewById(R.id.send);
        Button receive = findViewById(R.id.receive);
        mListView.setAdapter(listAdapter);
        mListView.setItemsCanFocus(false);



        send.setOnClickListener((View.OnClickListener) v -> {

            EditText text = (EditText)findViewById(R.id.messageeneter);
            ContentValues newRowValues = new ContentValues();
            newRowValues.put(DataBaseHelper.COLUMN_MESSAGES, text.getText().toString() );
            newRowValues.put(DataBaseHelper.COLUMN_SENDER, "send");
            long newID = db.insert(DataBaseHelper.MESSAGES, DataBaseHelper.COLUMN_MESSAGES, newRowValues);
            elements.add(new Message(newID, text.getText().toString(), "send"));
            ((MessageListAdapter) listAdapter).notifyDataSetChanged();
            editt.setText("");
        });

        receive.setOnClickListener((View.OnClickListener) v -> {

            EditText text = (EditText)findViewById(R.id.messageeneter);
            ContentValues newRowValues = new ContentValues();
            newRowValues.put(DataBaseHelper.COLUMN_MESSAGES, text.getText().toString() );
            newRowValues.put(DataBaseHelper.COLUMN_SENDER, "receive");
            long newID = db.insert(DataBaseHelper.MESSAGES, DataBaseHelper.COLUMN_MESSAGES, newRowValues);
            elements.add(new Message(newID, text.getText().toString(), "receive" ));
            ((MessageListAdapter) listAdapter).notifyDataSetChanged();
            editt.setText("");
        });



        mListView.setOnItemLongClickListener( (parent, view, pos, id) -> {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            alertDialogBuilder.setTitle("Do you want to delete this?")
                    .setMessage("The Selected Row is: "+pos+"\n"+"The database id: "+ getItemId(pos))
                    .setPositiveButton("Yes", (click, arg) -> {deleteMessage((Message) listAdapter.getItem(pos));elements.remove(pos); ((MessageListAdapter) listAdapter).notifyDataSetChanged();
                    })
                    .setNegativeButton("No", (click, arg) -> {  })
                    .create().show();
            return true ;

        });
        loadDataFromDatabase();


    }

    public long getItemId(int position) {
        return elements.get(position).getId();
    }



    public class MessageListAdapter extends BaseAdapter {


            @Override
            public void notifyDataSetChanged() {
                super.notifyDataSetChanged();
            }

            @Override
            public int getCount() {
                return elements.size();
            }

            @Override
            public Object getItem(int position) {
                return elements.get(position);
            }

            @Override
            public long getItemId(int position) {
                return elements.get(position).getId();
            }

           @Override
           public View getView(int position, View convertView, ViewGroup parent) {

               LayoutInflater inflater = getLayoutInflater();
               View newView;

               if (elements.get(position).getMessagetype().equals("receive")) {
                   newView = inflater.inflate(R.layout.receivedmessages, parent, false);
                   TextView tView = newView.findViewById(R.id.messageR);
                   tView.setText(elements.get(position).getText());
                   ImageView i = newView.findViewById(R.id.avatarR);
               }
               else{
                   newView = inflater.inflate(R.layout.sentmessages, parent, false);
                   TextView tView = newView.findViewById(R.id.messageS);
                   tView.setText(elements.get(position).getText());
                   ImageView i = newView.findViewById(R.id.avatarS);
               }

               return newView;

           }
       }
    public class Message {
        private String messagetype= "";
        private String text = "placeholder";

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        private long id;
        public Message(long id , String message,String sender){
            setText(message);
            setMessagetype(sender);
            this.id = id;


        }
        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getMessagetype() {
            return messagetype;
        }

        public void setMessagetype(String messagetype) {
            this.messagetype = messagetype;
        }
    }
    public void printCursor(Cursor c, int version) {
        c.moveToFirst();
        c.moveToPrevious();
        Log.d("CHAT_ROOM_ACTIVITY", "Version Number: " + version);
        Log.d("CHAT_ROOM_ACTIVITY", "Column Count: " + c.getColumnCount());
        for (String column : c.getColumnNames()) {
            Log.d("CHAT_ROOM_ACTIVITY", "Column Name: " + column);
        }
        Log.d("CHAT_ROOM_ACTIVITY", "Row Count: " + c.getCount());

        for (int x = 0; x<c.getCount(); x++) {
            c.moveToNext();
            Log.d("CHAT_ROOM_ACTIVITY", "ID: " + c.getString(0) + " TYPE: " + c.getString(1) + " MESSAGE: " + c.getString(2));
        }
        c.moveToFirst();
        c.moveToPrevious();
    }

    private void loadDataFromDatabase()
    {
        DataBaseHelper dbOpener = new DataBaseHelper(this);
        db = dbOpener.getWritableDatabase();
        String [] columns = {DataBaseHelper.SID, DataBaseHelper.COLUMN_MESSAGES, DataBaseHelper.COLUMN_SENDER};
        Cursor results = db.query(false, DataBaseHelper.MESSAGES, columns, null, null, null, null, null, null);
        int idColumnIndex = results.getColumnIndex(DataBaseHelper.SID);
        int messageColIndex = results.getColumnIndex(DataBaseHelper.COLUMN_MESSAGES);
        int senderColIndex = results.getColumnIndex(DataBaseHelper.COLUMN_SENDER);
        while(results.moveToNext())
        {
            long id = results.getLong(idColumnIndex);
            String message = results.getString(messageColIndex);
            String sender = results.getString(senderColIndex);
            elements.add(new Message(id,message,sender));
        }
        printCursor(results, db.getVersion());
    }

    private void deleteMessage(Message message){
        db.delete(DataBaseHelper.MESSAGES, DataBaseHelper.SID + "=?", new String[]{Long.toString(message.getId())});
    }



    }