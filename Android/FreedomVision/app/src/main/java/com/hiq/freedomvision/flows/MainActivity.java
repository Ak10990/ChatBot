package com.hiq.freedomvision.flows;

import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.hiq.freedomvision.R;
import com.hiq.freedomvision.constants.GenericConstants;
import com.hiq.freedomvision.helpers.QueryEngine;
import com.hiq.freedomvision.models.Chat;
import com.hiq.freedomvision.models.QueParse;
import com.hiq.freedomvision.utils.UIUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimerTask;

/**
 * Created by akanksha on 20/10/16.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener, QueryEngine.ActivityEngineListener,
        OptionsFragment.OptionDialogCallback {

    private List<Chat> chatList = new ArrayList<>();
    private FrameLayout fragContainer;
    private RecyclerView recyclerView;
    private EditText etEnterChat;
    private Button closeBtn;
    private QueryEngine queryEngine;
    private MessagesRecyclerAdapter adapter;
    private TextToSpeech tts;
    private boolean goodBye = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initTts();
        initReceiver();
    }

    private void initTts() {
        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.UK);
                }
            }
        });
    }

    private void initReceiver() {
        queryEngine = new QueryEngine(MainActivity.this, MainActivity.this);
        if (chatList == null || chatList.size() == 0) {
            sendChat(null, null);
        }
       /* IntentFilter filter = new IntentFilter("com.example.Broadcast");

        MyReceiver receiver = new MyReceiver();
        registerReceiver(receiver, filter);*/
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.chat_listview);
        if (chatList != null && recyclerView != null) {
            adapter = new MessagesRecyclerAdapter(this, chatList);
            recyclerView.setAdapter(adapter);
        }
        etEnterChat = (EditText) findViewById(R.id.et_enter_chat);
        fragContainer = (FrameLayout) findViewById(R.id.frag_container);
        FloatingActionButton sendFabBtn = (FloatingActionButton) findViewById(R.id.send_fab_btn);
        sendFabBtn.setOnClickListener(this);
        closeBtn = (Button) findViewById(R.id.close_btn);
        closeBtn.setOnClickListener(this);
    }

    private void switchPopUpFragment(QueParse queParse) {
        switch (queParse.getOptionsType()) {
//            case OptionsFragment.ALL_DIALOGS.OPTION_BOOK:
//            case OptionsFragment.ALL_DIALOGS.OPTION_TYPE:
            case OptionsFragment.ALL_DIALOGS.OPTION_YN:
                OptionsFragment optionsFragment = OptionsFragment.getInstance(queParse);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frag_container, optionsFragment, OptionsFragment.class.getSimpleName())
                        .commit();
//                optionsFragment.show(getSupportFragmentManager(), OptionsFragment.class.getSimpleName());
                break;
            case OptionsFragment.ALL_DIALOGS.OPTION_NONE:
                fragContainer.setVisibility(View.GONE);
                closeBtn.setVisibility(View.GONE);
                break;
            default:
                throw new IllegalArgumentException("Invalid type");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_bye:
                if (!goodBye && chatList.size() > 4) {
                    queryEngine.sendQuery("", "RATING", goodBye);
                    goodBye = true;
                } else {
                    queryEngine.sendQuery("", "BYE", goodBye);
                    Handler handler = new Handler();
                    handler.postDelayed(new TimerTask() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 1200);
                }
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_fab_btn:
                if (!TextUtils.isEmpty(etEnterChat.getText().toString())) {
                    sendChat(chatList.get(chatList.size() - 1).getBody(), etEnterChat.getText().toString());
                    etEnterChat.setText("");
                    UIUtils.hideSoftKeyboard(MainActivity.this);
                }
                break;
            case R.id.close_btn:
                sendOption("-1", "-1");
                break;
        }
    }

   /* private void prepareList() {
       *//* chatList.add(new Chat(1, new Date(), Constants.CHAT_BOT, ChatConstants.aQue.get(0)));
        chatList.add(new Chat(1, new Date(), Constants.ME, "How are you"));
        chatList.add(new Chat(1, new Date(), Constants.CHAT_BOT, "Hi"));
        chatList.add(new Chat(1, new Date(), Constants.ME, "Hi"));
        chatList.add(new Chat(1, new Date(), Constants.CHAT_BOT, "CCCs'njsj"));
        chatList.add(new Chat(1, new Date(), Constants.ME, "Hi"));*//*
    }*/

    private void sendChat(String ques, String ans) {
       /* Intent intent = new Intent();
        intent.setAction("com.hiq.freedomvision");
        intent.putExtra("HighScore", 1000);
        sendBroadcast(intent);*/
        String result = queryEngine.sendQuery(ques, ans, goodBye);
        if (ques != null && result != null) {
            chatList.add(new Chat(chatList.size(), new Date(), GenericConstants.ME, result));
            adapter.notifyDataSetChanged();
            recyclerView.smoothScrollToPosition(adapter.getItemCount());
        }

    }

    @Override
    public void onReceiveQuery(QueParse queParse) {
        chatList.add(new Chat(chatList.size(), new Date(), GenericConstants.CHAT_BOT, queParse.getQuestion()));
        adapter.notifyDataSetChanged();
        recyclerView.smoothScrollToPosition(adapter.getItemCount());
        fragContainer.setVisibility(View.VISIBLE);
        closeBtn.setVisibility(View.VISIBLE);
        switchPopUpFragment(queParse);
        tts.speak(queParse.getQuestion(), TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void sendOption(String ques, String ans) {
        if (!ans.equals("-1")) {
            sendChat(ques, ans);
        }
        fragContainer.setVisibility(View.GONE);
        closeBtn.setVisibility(View.GONE);
    }
}
