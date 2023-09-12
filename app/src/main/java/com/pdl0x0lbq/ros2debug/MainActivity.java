package com.pdl0x0lbq.ros2debug;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.ros2.rcljava.RCLJava;
public class MainActivity extends ros_activity {
    private static final String IS_WORKING_TALKER = "isWorkingTalker";
    private static final String IS_WROKING_LISTENER = "isWorkingListener";
    private listener_node listenerNode;
    private talker_node talkerNode;
    private TextView listenerView;
    private static String logtag = MainActivity.class.getName();
    private boolean isWorkingListener;
    private boolean isWorkingTalker;
    /** Called when the activity is first created. */
    @Override
    public final void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            isWorkingListener = savedInstanceState.getBoolean(IS_WROKING_LISTENER);
            isWorkingTalker = savedInstanceState.getBoolean(IS_WORKING_TALKER);
        }

        Button listenerStartBtn = (Button)findViewById(R.id.lstart);
        listenerStartBtn.setOnClickListener(startListenerListener);
        Button listenerStopBtn = (Button)findViewById(R.id.lstop);
        listenerStopBtn.setOnClickListener(stopListenerListener);

        Button talkerStartBtn = (Button)findViewById(R.id.tstart);
        talkerStartBtn.setOnClickListener(startTalkerListener);
        Button talkerStopBtn = (Button)findViewById(R.id.tstop);
        talkerStopBtn.setOnClickListener(stopTalkerListener);

        listenerView = (TextView)findViewById(R.id.ltext);
        listenerView.setMovementMethod(new ScrollingMovementMethod());

        RCLJava.rclJavaInit();

        listenerNode = new listener_node("ros2galacticnode_listener", "/chatter", listenerView);

        talkerNode = new talker_node("ros2galacticnode_talker", "/chatter");

        changeListenerState(false);
        changeTalkerState(false);
    }
    private View.OnClickListener startListenerListener = new View.OnClickListener() {
        public void onClick(final View view) {
            changeListenerState(true);
        }
    };
    private View.OnClickListener stopListenerListener = new View.OnClickListener() {
        public void onClick(final View view) {
            Log.d(logtag, "onClick() called - stop listener button");
            changeListenerState(false);
        }
    };
    private View.OnClickListener startTalkerListener = new View.OnClickListener() {
        public void onClick(final View view) {
            Log.d(logtag, "onClick() called - start talker button");
            changeTalkerState(true);
        }
    };

    private View.OnClickListener stopTalkerListener = new View.OnClickListener() {
        public void onClick(final View view) {
            Log.d(logtag, "onClick() called - stop talker button");
            changeTalkerState(false);
        }
    };
    private void changeListenerState(boolean isWorking) {
        this.isWorkingListener = isWorking;
        Button buttonStart = (Button)findViewById(R.id.lstart);
        Button buttonStop = (Button)findViewById(R.id.lstop);
        buttonStart.setEnabled(!isWorking);
        buttonStop.setEnabled(isWorking);
        if (isWorking){
            getExecutor().addNode(listenerNode);
        } else {
            getExecutor().removeNode(listenerNode);
        }
    }
    private void changeTalkerState(boolean isWorking) {
        this.isWorkingTalker = isWorking;
        Button buttonStart = (Button)findViewById(R.id.tstart);
        Button buttonStop = (Button)findViewById(R.id.lstop);
        buttonStart.setEnabled(!isWorking);
        buttonStop.setEnabled(isWorking);
        if (isWorking){
            getExecutor().addNode(talkerNode);
            talkerNode.start();

            std_msgs.msg.String msg = new std_msgs.msg.String();
            talkerNode.publisher.publish(msg);
        } else {
            talkerNode.stop();
            getExecutor().removeNode(talkerNode);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putBoolean(IS_WROKING_LISTENER, isWorkingListener);
            outState.putBoolean(IS_WORKING_TALKER, isWorkingTalker);
        }
        super.onSaveInstanceState(outState);
    }
}