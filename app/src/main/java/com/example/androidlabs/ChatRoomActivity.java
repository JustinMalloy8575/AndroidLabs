package com.example.androidlabs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {
    public ArrayList<String> elements = new ArrayList<>();
    public ArrayList<String> senders = new ArrayList<>();
    ListAdapter listAdapter = new MessageListAdapter();


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



        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message.setMessagetype("send");
                EditText text = (EditText)findViewById(R.id.messageeneter);
                Message.setText(text.getText().toString());
                elements.add(Message.getText());
                senders.add(Message.getMessagetype());
                editt.setText("");
                ((MessageListAdapter) listAdapter).notifyDataSetChanged();
            }
        });

        receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message.setMessagetype("receive");
                EditText text = (EditText)findViewById(R.id.messageeneter);
                Message.setText(text.getText().toString());
                elements.add(Message.getText());
                senders.add(Message.getMessagetype());
                editt.setText("");
                ((MessageListAdapter) listAdapter).notifyDataSetChanged();
            }
        });
        mListView.setOnItemLongClickListener( (parent, view, pos, id) -> {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            alertDialogBuilder.setTitle("Do you want to delete this?")
                    .setMessage("The Selected Row is: "+pos+"\n"+"The database id: "+ id)
                    .setPositiveButton("Yes", (click, arg) -> {elements.remove(pos); senders.remove(pos); ((MessageListAdapter) listAdapter).notifyDataSetChanged(); })
                    .setNegativeButton("No", (click, arg) -> {  })
                    .create().show();

            return true ;

        });

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
                return (long) position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                LayoutInflater inflater = getLayoutInflater();
                View newOne;


                if (senders.get(position).equals("receive")) {
                    newOne = inflater.inflate(R.layout.receivedmessages, parent, false);
                    TextView tView = newOne.findViewById(R.id.messageR);
                    tView.setText(elements.get(position));
                    ImageView i = newOne.findViewById(R.id.avatarR);
                }
                else{
                    newOne = inflater.inflate(R.layout.sentmessages, parent, false);
                    TextView tView = newOne.findViewById(R.id.messageS);
                    tView.setText(elements.get(position));
                    ImageView i = newOne.findViewById(R.id.avatarS);
                }
                return newOne;

            }
        }

    public static class Message {
        private static String messagetype= "";
        private static String text = "placeholder";

        public static String getText() {
            return text;
        }

        public static void setText(String text) {
            Message.text = text;
        }

        public static String getMessagetype() {
            return messagetype;
        }

        public static void setMessagetype(String messagetype) {
            Message.messagetype = messagetype;
        }
    }




    }