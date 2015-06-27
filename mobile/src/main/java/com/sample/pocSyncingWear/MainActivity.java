package com.sample.pocSyncingWear;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

public class MainActivity extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private EditText mEditName;
    private EditText mEditCard;
    private Button mBtnSend;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditName = (EditText) findViewById(R.id.name_input);
        mEditCard = (EditText) findViewById(R.id.card_input);
        mBtnSend = (Button) findViewById(R.id.btn_send_data);

        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PostToDataLayerThread().start();
            }
        });
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
    }

    @Override
    protected void onStop() {
        if (null != mGoogleApiClient && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onConnectionSuspended(int cause) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    private class PostToDataLayerThread extends Thread {

        public void run() {
            PutDataMapRequest putDataMapReq = PutDataMapRequest.create(ConstantUtils.EXTRA_PATH_DATA);
            putDataMapReq.getDataMap().putString(ConstantUtils.EXTRA_NAME, mEditName.getText().toString());
            putDataMapReq.getDataMap().putString(ConstantUtils.EXTRA_CARD, mEditCard.getText().toString());
            PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
            Wearable.DataApi.putDataItem(mGoogleApiClient, putDataReq);
        }
    }
}