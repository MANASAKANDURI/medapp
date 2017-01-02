package aitmyhelloapp.examples.android.ait.hu.medicinereminderdemo;

import java.util.Locale;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

public class AlarmService extends Service {
    private NotificationManager mManager;
	public TextToSpeech t1;


    @Override
    public IBinder onBind(Intent arg0)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate()
    {
        // TODO Auto-generated method stub
        super.onCreate();
        
        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
			@Override
			public void onInit(int status) {
				if (status != TextToSpeech.ERROR) {
					t1.setLanguage(Locale.US);
				}
			}
		});
    }

    @SuppressWarnings("static-access")
    @Override
    public void onStart(Intent intent, int startId)
    {
        super.onStart(intent, startId);

        mManager = (NotificationManager) this.getApplicationContext().getSystemService(this.getApplicationContext().NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(this.getApplicationContext(),DialogActivity.class);
        try {
            if (intent!= null) {
                intent1.putExtra("medicine_name", intent.getStringExtra("medicine_name"));
                intent1.putExtra("dosage", intent.getStringExtra("dosage"));

                Notification notification = new Notification(R.drawable.alarm2, "Time to take "+intent.getStringExtra("medicine_name")+"!", System.currentTimeMillis());

                intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                PendingIntent pendingNotificationIntent = PendingIntent.getActivity(this.getApplicationContext(), 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
                notification.flags |= Notification.FLAG_AUTO_CANCEL;                
                notification.setLatestEventInfo(this.getApplicationContext(), "Time to take "+intent.getStringExtra("medicine_name")+"!!", "Click on the notification to set record", pendingNotificationIntent);

                // Toast.makeText(AlarmService.this,"TaskId : "+startId , Toast.LENGTH_SHORT).show();
                notification.defaults |= Notification.DEFAULT_SOUND;
                mManager.notify(startId, notification);

                Vibrator vibrator = (Vibrator) getBaseContext().getSystemService(getBaseContext().VIBRATOR_SERVICE);
                vibrator.vibrate(2000);
                
                try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
                String msg = "Take Medecine " + intent.getStringExtra("medicine_name");
        		
        		// reading out loud
        		t1.speak(msg, TextToSpeech.QUEUE_FLUSH, null);
            }
        }finally
        {

        }
    }

    @Override
    public void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
    }
}
