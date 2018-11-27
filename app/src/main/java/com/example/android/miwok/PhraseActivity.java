package com.example.android.miwok;

import android.content.Context;
import android.graphics.Color;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class PhraseActivity extends AppCompatActivity {
    private MediaPlayer mMediaPlayer;
    private ArrayList<Word> word;
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        // we created the global variable of
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };
    private AudioManager mAudioManager;
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            //Here, we modifying the way our app will interact when other activity related to sound comes.
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);     //we want our user to listen the word from begin.
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
                mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_inside_layout);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        word = new ArrayList<Word>();

        word.add(new Word("Where are you going?","minto wuksus",R.raw.phrase_where_are_you_going));
        word.add(new Word("What is your name?","tinnә oyaase'nә",R.raw.phrase_what_is_your_name));
        word.add(new Word("My name is..","oyaaset...",R.raw.phrase_my_name_is));
        word.add(new Word("How are you feeling?","michәksәs?",R.raw.phrase_how_are_you_feeling));
        word.add(new Word("I’m feeling good.","kuchi achit",R.raw.phrase_im_feeling_good));
        word.add(new Word("Are you coming?","әәnәs'aa?",R.raw.phrase_are_you_coming));
        word.add(new Word("Yes, I’m coming.","hәә’ әәnәm",R.raw.phrase_yes_im_coming));
        word.add(new Word("I’m coming.","әәnәm",R.raw.phrase_im_coming));
        word.add(new Word("Let’s go.","yoowutis",R.raw.phrase_lets_go));
        word.add(new Word("Come here","әnni'nem",R.raw.phrase_come_here));

        // Create an {@link ArrayAdapter}, whose data source is a list of Strings. The
        // adapter knows how to create layouts for each item in the list, using the
        // simple_list_item_1.xml layout resource defined in the Android framework.
        // This list item layout contains a single {@link TextView}, which the adapter will set to
        // display a single word.

        WordAdapter itemsAdapter = new WordAdapter(this, word);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // activity_numbers.xml layout file.
        ListView view = (ListView) findViewById(R.id.layout_view);
        view.setBackgroundColor(Color.parseColor("#16AFCA"));
        // Make the {@link ListView} use the {@link ArrayAdapter} we created above, so that the
        // {@link ListView} will display list items for each word in the list of words.
        // Do this by calling the setAdapter method on the {@link ListView} object and pass in
        // 1 argument, which is the {@link ArrayAdapter} with the variable name itemsAdapter.
        view.setAdapter(itemsAdapter);
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Toast.makeText(PhraseActivity.this, "Playing", Toast.LENGTH_SHORT).show();
                //making the toast object and then calling object on it.
                //If the mediaplayer object is initialised to other music file, it will free up the assignment
                //It is also imp, bcoz if the user continously clicking other song then before playing new song, 1st it will release the
                //MediaPlayer object(which is stopping last song) and so that we can start new song.

                // Request audio focus for playback
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    releaseMediaPlayer();

                    //using the position variable, we will get the position of the Word object in the Arraylist
                    Word holdView = word.get(pos);
                    mMediaPlayer = MediaPlayer.create(PhraseActivity.this, holdView.getAudioResourceId());
                    mMediaPlayer.start();
                    //using asyncronise call back, and meantime, i can go elsewhere and it tell me the work is done
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
    }
    public void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}
