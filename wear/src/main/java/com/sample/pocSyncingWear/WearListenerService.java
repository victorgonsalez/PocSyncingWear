package com.sample.pocSyncingWear;

import android.content.Intent;

import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.WearableListenerService;

public class WearListenerService extends WearableListenerService {
    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        Intent startIntent = new Intent(this, WearMainActivity.class);
        for (DataEvent event : dataEvents) {

            DataItem item = event.getDataItem();
            if (item.getUri().getPath().compareTo(ConstantUtils.EXTRA_PATH_DATA) == 0) {
                DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                startIntent.putExtra(ConstantUtils.EXTRA_NAME, dataMap.getString(ConstantUtils.EXTRA_NAME));
                startIntent.putExtra(ConstantUtils.EXTRA_CARD, dataMap.getString(ConstantUtils.EXTRA_CARD));
            }
        }
        startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(startIntent);
    }
}
