package aitmyhelloapp.examples.android.ait.hu.medicinereminderdemo;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import aitmyhelloapp.examples.android.ait.hu.medicinereminderdemo.Adapter.MyPagerAdapter;
import aitmyhelloapp.examples.android.ait.hu.medicinereminderdemo.data.MedicineInfo;

public class MainActivity extends FragmentActivity {

	public static final String SP_DATA = "SP_DATA";
	AlarmManager alarmManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
		alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmService();

	}

	public void alarmService() {
		List<MedicineInfo> infos = MedicineInfo.listAll(MedicineInfo.class);
		for (int i = 0; i < infos.size(); i++) {
			
				
			MedicineInfo currentInfo = infos.get(i);

			if (currentInfo.getSecondReminder()) {
				Intent myIntent = new Intent(MainActivity.this, TimeReceiver.class);
				// probably not going to work
				myIntent.putExtra("medicine_name", currentInfo.getMedicineName());
				myIntent.putExtra("dosage", currentInfo.getDosage());

				boolean dailyFlag = currentInfo.getDaily();

				PendingIntent pi = null;
				if (dailyFlag) {
					pi = PendingIntent.getBroadcast(MainActivity.this, i, myIntent, PendingIntent.FLAG_CANCEL_CURRENT);
				} else {
					pi = PendingIntent.getBroadcast(MainActivity.this, i, myIntent, PendingIntent.FLAG_CANCEL_CURRENT);
				}

				String dateStart = Calendar.getInstance().get(Calendar.YEAR) + "/"
						+ (Calendar.getInstance().get(Calendar.MONTH) + 1) + "/"
						+ Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + " "
						+ Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + ":"
						+ Calendar.getInstance().get(Calendar.MINUTE);
				String dateStop = currentInfo.getTakeTime().get(Calendar.YEAR) + "/"
						+ currentInfo.getTakeTime().get(Calendar.MONTH) + "/"
						+ currentInfo.getTakeTime().get(Calendar.DAY_OF_MONTH) + " "
						+ currentInfo.getTakeTime().get(Calendar.HOUR_OF_DAY) + ":"
						+ currentInfo.getTakeTime().get(Calendar.MINUTE);

				System.out.println(dateStart + "----====----" + dateStop);

				SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");

				Date d1 = null;
				Date d2 = null;
				try {
					d1 = format.parse(dateStart);
					d2 = format.parse(dateStop);
				} catch (ParseException e) {
					e.printStackTrace();
				}

				// Get msec from each, and subtract.
				long diff = d2.getTime() - d1.getTime();
				long diffSeconds = diff / 1000 % 60;
				long diffMinutes = diff / (60 * 1000) % 60;
				long diffHours = diff / (60 * 60 * 1000);
				long ddays = diff / (24 * 60 * 60 * 1000);
				System.out.println("Time in seconds: " + diffSeconds + " seconds.");
				System.out.println("Time in minutes: " + diffMinutes + " minutes.");
				System.out.println("Time in hours: " + diffHours + " hours.");
				System.out.println("Days: " + ddays + " days.");

				// Toast.makeText(MainActivity.this, "--Day Diff-- " + ddays +
				// "---" + dailyFlag, Toast.LENGTH_SHORT).show();

				// System.out.println(d2.getTime()+"-----------"+currentInfo.getTakeTime()+"-----"
				// + currentInfo.getTakeTime().getTimeInMillis());

				// alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
				// currentInfo.getTakeTime().getTimeInMillis(),2*60*60,pi);
				// alarmManager.set(AlarmManager.RTC_WAKEUP,
				// currentInfo.getTakeTime().getTimeInMillis(),pi);
				// alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
				// SystemClock.elapsedRealtime(),3*60*60,pi);

				// Toast.makeText(MainActivity.this,currentInfo.getMedicineName()+","+diffSeconds+","+diffMinutes+","+diffHours
				// , Toast.LENGTH_SHORT).show();
				// Toast.makeText(MainActivity.this,d1.getTime()+"---"+d2.getTime(),
				// Toast.LENGTH_SHORT).show();

				// Log.d("--->",d1.getTime() + "---" + d2.getTime() + "----" +
				// convertTime(d1.getTime())+"----"+convertTime(d2.getTime())+"---"+convertTime(System.currentTimeMillis()));

				if (dailyFlag) {
					alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, d2.getTime(), AlarmManager.INTERVAL_DAY, pi);
				} else {
					alarmManager.set(AlarmManager.RTC_WAKEUP, d2.getTime(), pi);
				}

				currentInfo.setSecondReminder(false);
				currentInfo.save();

			}
			else {
				if (!currentInfo.getDaily()) {
					Intent myIntent = new Intent(MainActivity.this, TimeReceiver.class);
					PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this, i, myIntent,
							PendingIntent.FLAG_CANCEL_CURRENT);

					String dateStart = Calendar.getInstance().get(Calendar.YEAR) + "/"
							+ (Calendar.getInstance().get(Calendar.MONTH) + 1) + "/"
							+ Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + " "
							+ Calendar.getInstance().get(Calendar.HOUR) + ":"
							+ Calendar.getInstance().get(Calendar.MINUTE);
					String dateStop = currentInfo.getTakeTime().get(Calendar.YEAR) + "/"
							+ currentInfo.getTakeTime().get(Calendar.MONTH) + "/"
							+ currentInfo.getTakeTime().get(Calendar.DAY_OF_MONTH) + " "
							+ currentInfo.getTakeTime().get(Calendar.HOUR) + ":"
							+ currentInfo.getTakeTime().get(Calendar.MINUTE);

					System.out.println(dateStart + "----====----" + dateStop);

					SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm");
					format.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));

					Date d1 = null;
					Date d2 = null;
					try {
						d1 = format.parse(dateStart);
						d2 = format.parse(dateStop);
					} catch (ParseException e) {
						e.printStackTrace();
					}

					// Get msec from each, and subtract.
					long diff = d2.getTime() - d1.getTime();
					long diffSeconds = diff / 1000 % 60;
					long diffMinutes = diff / (60 * 1000) % 60;
					long diffHours = diff / (60 * 60 * 1000);
					long ddays = diff / (24 * 60 * 60 * 1000);
					System.out.println("Time in seconds: " + diffSeconds + " seconds.");
					System.out.println("Time in minutes: " + diffMinutes + " minutes.");
					System.out.println("Time in hours: " + diffHours + " hours.");
					System.out.println("Days: " + ddays + " days.");
					boolean flag = checkIfPendingIntentIsRegistered(i);
					//Log.d("PI:", i + "---" + flag);
					if (diffMinutes < 0 && diffHours < 0 && flag) {
						//pi.cancel();
						//alarmManager.cancel(pi);
						//Toast.makeText(MainActivity.this, "Cancelling : " + currentInfo.getMedicineName(),Toast.LENGTH_SHORT).show();
					}
				}
			}
		}

	}

	public String convertTime(long time) {
		Date date = new Date(time);
		Format format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		return format.format(date);
	}

	private boolean checkIfPendingIntentIsRegistered(int id) {
		Intent intent = new Intent(MainActivity.this, TimeReceiver.class);
		// Build the exact same pending intent you want to check.
		// Everything has to match except extras.
		return (PendingIntent.getBroadcast(MainActivity.this, id, intent, PendingIntent.FLAG_NO_CREATE) != null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	@Override
	protected void onStart() {
		super.onStart();
		SharedPreferences sp = getSharedPreferences(SP_DATA, MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		String date1 = "Medicine Name: Aspirin" + "\n" + "Status: Taken in time";
		String date2 = "Medicine Name: Alerid" + "\n" + "+Status: Not taken in time";
		editor.putString("20141201", date1);
		editor.putString("20140929", date2);
		editor.commit();
		// editor.putString(KEY_ET_DATA,etData.getText().toString());
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
