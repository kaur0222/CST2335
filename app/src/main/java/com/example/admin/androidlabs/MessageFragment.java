package com.example.admin.androidlabs;

/**
 * Created by Admin on 12/13/2017.
 */

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class MessageFragment extends Fragment {
    TextView msgView;
    TextView idView;
    Button deleteBtn;
    String myMsg;
    int myId;
    long dbID;
    ChatWindow chatWindow;

    public MessageFragment() {
        // Required empty public constructor
    }

    public void setChatWindow(ChatWindow chatWindow){
        this.chatWindow = chatWindow;
    }

    public static MessageFragment newInstance()
    {
        MessageFragment myFragment = new MessageFragment();

        return myFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            myMsg = bundle.getString("chatMsg");
            myId = bundle.getInt("Id");
            Log.i("MessageFragment", myMsg);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_msg, container, false);
        msgView = (TextView) view.findViewById(R.id.messageView);
        msgView.setText(myMsg);

        idView = (TextView) view.findViewById(R.id.msgId);
        idView.setText(Integer.toString(myId));

        deleteBtn = (Button) view.findViewById(R.id.deleteMsg);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chatWindow != null){
                    //tablet
                    chatWindow.deleteMessage(myId);
                    // once deleted the fragment is disappeared
                    getActivity().getFragmentManager().popBackStack();

                }
                else{
                    Log.i("tag","hello");
                    Intent intent = new Intent();
                    intent.putExtra("deleteMsgId", myId);
                    //intent.putExtra("deleteDBMsgId", dbID);
                    getActivity().setResult(10, intent);
                    getActivity().finish();
                }
            }
        });
        return view;
    }
}
