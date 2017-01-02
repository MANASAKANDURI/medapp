package aitmyhelloapp.examples.android.ait.hu.medicinereminderdemo;

import java.util.Calendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class TimeReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
    	
    	String dateStart = Calendar.getInstance().get(Calendar.YEAR) + "/"
				+ (Calendar.getInstance().get(Calendar.MONTH) + 1) + "/"
				+ Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + " "
				+ Calendar.getInstance().get(Calendar.HOUR) + ":" + Calendar.getInstance().get(Calendar.MINUTE);
    	
    	//Log.d("--------Firing Event-------------", dateStart);
    	
        Intent service1 = new Intent(context, AlarmService.class);
        service1.putExtra("medicine_name",intent.getStringExtra("medicine_name"));
        service1.putExtra("dosage",intent.getStringExtra("dosage"));
        service1.putExtra("taskid",intent.getStringExtra("taskid"));
        context.startService(service1);
    }
}
