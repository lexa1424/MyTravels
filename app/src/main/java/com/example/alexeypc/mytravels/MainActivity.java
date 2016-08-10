package com.example.alexeypc.mytravels;

import android.app.Activity;


import android.database.Cursor;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;


public class MainActivity extends FragmentActivity implements LoaderCallbacks<Cursor> {

    Button btnStart;
    ListView listView;
    DataBase db;
    SimpleCursorAdapter scAdapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = (Button)findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MapActivity.start(MainActivity.this);
            }
        });

        listView = (ListView)findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LinearLayout parent = (LinearLayout) view;
                TextView textView = (TextView) parent.findViewById(R.id.tvItem);
                String date = textView.getText().toString();
                Intent intent = new Intent(MainActivity.this, TravelActivity.class);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });

        db = DataBase.getInstance(this);
        db.open();



        String [] from = new String[] {DataBase.COLUMN_DATE};
        int [] to = new int[] {R.id.tvItem};

        scAdapter = new SimpleCursorAdapter(this, R.layout.item, null, from, to, 0);
        listView.setAdapter(scAdapter);

        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        db.close();
    }


    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new MyCursorLoader(this, db);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        scAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {

    }

    static class MyCursorLoader extends CursorLoader {

        DataBase db;

        public MyCursorLoader(Context context, DataBase db) {
            super(context);
            this.db = db;
        }

        @Override
        public Cursor loadInBackground() {
            Cursor cursor = db.getAllData();

            return cursor;
        }

    }
}
