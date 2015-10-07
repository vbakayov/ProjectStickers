package pedometar;


import com.starboardland.pedometer.R;

import android.app.Activity;
import android.content.Context;
import android.hardware.*;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CounterActivity extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private boolean firstTime;
    private int initialStep;
    private TextView count;
    private Button refresh;
    boolean activityRunning;
    private OnClickListener myhandler1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        count = (TextView) findViewById(R.id.count);
        refresh= (Button) findViewById(R.id.buttonrefresh);
        refresh.setOnClickListener(myhandler1);
        initialStep = 0;
        firstTime= true;
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        
        
        
    }
    
   

    @Override
    protected void onResume() {
        super.onResume();
        activityRunning = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Count sensor not available!", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        activityRunning = false;
        // if you unregister the last listener, the hardware will stop detecting step events
//        sensorManager.unregisterListener(this); 
    }

    @Override
    public void onSensorChanged(final SensorEvent event) {
        if (activityRunning) {
        	if(firstTime){
        		initialStep = (int) event.values[0];
        		count.setText(String.valueOf(event.values[0]- initialStep));
        		firstTime= false;
        	}else{
        		count.setText(String.valueOf(event.values[0]- initialStep));
        	}	
           
            myhandler1 = new View.OnClickListener() {
                public void onClick(View v) {
                	initialStep =  (int) event.values[0];
                	count.setText(String.valueOf(event.values[0] - initialStep));
                }
              };
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
    
    
  
}
