package com.example.progressbar;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // Widgets
    private ProgressBar progressCircle;
    private ProgressBar progressBar;
    private TextView progressText;
    private Button buttonStart;

    /**
     * On create event
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the widgets
        progressCircle = findViewById(R.id.progress_circle);
        progressBar = findViewById(R.id.progress_bar);
        progressText = findViewById(R.id.progress_text);

        // Start the sample
        buttonStart = findViewById(R.id.button_start);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ProgressTask().execute(); // Start the async task (thread)
            }
        });

        // Set widgets as invisible
        setProgressVisibility(false);
    }

    /**
     * Set progress and button visibility
     * @param state
     */
    private void setProgressVisibility(boolean state) {
        progressCircle.setVisibility(state ? View.VISIBLE : View.INVISIBLE);
        progressBar.setVisibility(state ? View.VISIBLE : View.INVISIBLE);
        progressText.setVisibility(state ? View.VISIBLE : View.INVISIBLE);
        buttonStart.setVisibility(state ? View.INVISIBLE : View.VISIBLE);
    }

    /**
     * Progress task class (extended by AsyncTask)
     */
    class ProgressTask extends AsyncTask {

        // Messages to be presented according to the progress
        private String[] tasks = new String[]{
                "Connecting to server...", "Log in to system...", "Sending request...",
                "Receiving response...", "Loadind data..."
        };
        private int index;

        /**
         * On pre execute event
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            setProgressVisibility(true); // Set widgets as visible
        }

        /**
         * Do in background thread
         * @param objects
         * @return
         */
        @Override
        protected Object doInBackground(Object[] objects) {
            Random random = new Random();
            // Simple simulation
            for(index = 0; index <= 100; index += random.nextInt(10)) {
                try {
                    progressBar.setProgress(index); // Set progress
                    publishProgress(); // Update the views because here it cannot be done
                    Thread.sleep(250); // Delay in milliseconds
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        /**
         * On progress update event
         * @param values
         */
        @Override
        protected void onProgressUpdate(Object[] values) {
            progressText.setText(tasks[index / 20]); // According to progress, a message will be presented
            super.onProgressUpdate(values);
        }

        /**
         * On post execute event
         * @param o
         */
        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            setProgressVisibility(false); // Set widgets as invisible again
            Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_LONG).show(); // Show a toast message
        }
    }
}
