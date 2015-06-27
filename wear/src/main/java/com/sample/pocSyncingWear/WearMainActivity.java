package com.sample.pocSyncingWear;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;

public class WearMainActivity extends Activity {

    private TextView mTextName;
    private TextView mTextCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextName = (TextView) stub.findViewById(R.id.name_txt);
                mTextCard = (TextView) stub.findViewById(R.id.card_txt);
                updateUI();
            }
        });

        IntentFilter dataFilter = new IntentFilter(Intent.ACTION_SEND);
        DataSyncingReceiver dataReceiver = new DataSyncingReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(dataReceiver, dataFilter);
    }

    public class DataSyncingReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateUI();
        }
    }

    private void updateUI() {
        mTextName.setText(getIntent().getStringExtra(ConstantUtils.EXTRA_NAME));
        mTextCard.setText(getIntent().getStringExtra(ConstantUtils.EXTRA_CARD));
    }
}
