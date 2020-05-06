package tv.cloudwalker.interaction;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TvInteraction tvInteraction = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int port = getIntent().getIntExtra("port", 0);
        String ip = getIntent().getStringExtra("ip");
        Toast.makeText(this, "IP = "+ip+ "  port = "+port, Toast.LENGTH_LONG).show();
        tvInteraction = new TvInteraction(ip, port);

        ((TextView)findViewById(R.id.appList)).setMovementMethod(new ScrollingMovementMethod());
        for (Map.Entry<String,String> entry : tvInteraction.getAppList().entrySet())
            ((TextView)findViewById(R.id.appList)).append(entry.getKey()+" = "+entry.getValue()+"\n");

        for(String s : tvInteraction.getTvSourceList()){
            ((TextView)findViewById(R.id.sourceList)).append(s+"\n");
        }
    }

    public void left(View view) {
        tvInteraction.triggerKeyCode("21");
    }

    public void down(View view) {
        tvInteraction.triggerKeyCode("20");
    }

    public void up(View view) {
        tvInteraction.triggerKeyCode("19");
    }

    public void right(View view) {
        tvInteraction.triggerKeyCode("22");
    }

    public void vol_up(View view) {
        tvInteraction.triggerKeyCode("24");
    }

    public void vol_down(View view) {
        tvInteraction.triggerKeyCode("25");
    }

    public void power(View view) {
        tvInteraction.triggerKeyCode("26");
    }

    public void ok(View view) {
        tvInteraction.triggerKeyCode("23");
    }

    public void mute(View view) {
        tvInteraction.triggerKeyCode("164");
    }

    public void home(View view) {
        tvInteraction.triggerKeyCode("4");
    }

    public void back(View view) {
        tvInteraction.triggerKeyCode("3");
    }
}
