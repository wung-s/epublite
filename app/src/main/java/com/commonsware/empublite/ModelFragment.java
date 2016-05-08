package com.commonsware.empublite;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import de.greenrobot.event.EventBus;

/**
 * Created by Wung on 08/05/16.
 */
public class ModelFragment extends Fragment {
    private BookContents contents = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity host = getActivity();
        if(contents == null) {
            new LoadThread(host.getAssets()).start();
        }
    }

    public BookContents getBook() {
        return(contents);
    }


    private class LoadThread extends Thread{
        private  AssetManager assets = null;
        public LoadThread(AssetManager assets) {
            super();
            this.assets = assets;
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        }

        public void run() {
            Gson gson = new Gson();
            try {
                InputStream is = assets.open("book/contents.json");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                contents = gson.fromJson(reader, BookContents.class);
                EventBus.getDefault().post(new BookLoadedEvent(contents));
            }
            catch (IOException e) {
                Log.e(e.getClass().getSimpleName(), "Exception parsing JSON", e);
                Log.d("test-app", "Exception parsing JSON occured !!");
            }

        }
    }


}
