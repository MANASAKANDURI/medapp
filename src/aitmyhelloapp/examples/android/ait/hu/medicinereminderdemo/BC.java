//package aitmyhelloapp.examples.android.ait.hu.medicinereminderdemo;
//
//import android.app.Activity;
//import android.app.AlarmManager;
//import android.app.PendingIntent;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.os.SystemClock;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.view.ViewPager;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.widget.Toast;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//
//import aitmyhelloapp.examples.android.ait.hu.medicinereminderdemo.Adapter.MyPagerAdapter;
//import aitmyhelloapp.examples.android.ait.hu.medicinereminderdemo.data.MedicineInfo;
//
//public class MainActivity extends FragmentActivity {
//
//	public static final String SP_DATA = "SP_DATA";
//	ArrayList<PendingIntent> intentArray;
//	ArrayList<PendingIntent> secondArray;
//	AlarmManager alarmManager;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
//		ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
//		viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
//		alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//		alarmService();
//
//	}
//
//	public void alarmService() {
//		intentArray = new ArrayList<PendingIntent>();
//		secondArray = new ArrayList<PendingIntent>();
//
//		List<MedicineInfo> infos = MedicineInfo.listAll(MedicineInfo.class);
//		for (int i = 0; i < infos.size(); i++) {
//			boolean set = false;
//			MedicineInfo currentInfo = infos.get(i);
//
//			Intent myIntent = new Intent(MainActivity.this, TimeReceiver.class);
//			// probably not going to work
//			myIntent.putExtra("medicine_name", currentInfo.getMedicineName());
//			myIntent.putExtra("dosage", currentInfo.getDosage());
//
//			boolean dailyFlag = currentInfo.getDaily();
//
//			PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this, i, myIntent,
//					PendingIntent.FLAG_UPDATE_CURRENT);
//
//			// mgrAlarm.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,SystemClock.elapsedRealtime()
//			// + 60000 * i,pendingIntent);
//			// Toast.makeText(MainActivity.this,currentInfo.getTakeTime().get(Calendar.MINUTE)+"------>"+Calendar.getInstance().get(Calendar.HOUR),
//			// Toast.LENGTH_SHORT).show();
//
//			Toast.makeText(MainActivity.this, currentInfo.getTakeTime().get(Calendar.DAY_OF_MONTH) + "-"
//					+ currentInfo.getTakeTime().get(Calendar.MONTH) + "-" + currentInfo.getTakeTime().get(Calendar.YEAR)
//					+ "----" + currentInfo.getTakeTime().get(Calendar.HOUR) + ":"
//					+ currentInfo.getTakeTime().get(Calendar.MINUTE), Toast.LENGTH_SHORT).show();
//
//			Toast.makeText(MainActivity.this, Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "-"
//					+ (Calendar.getInstance().get(Calendar.MONTH) + 1) + "-" + Calendar.getInstance().get(Calendar.YEAR)
//					+ "----" + Calendar.getInstance().get(Calendar.HOUR) + ":"
//					+ Calendar.getInstance().get(Calendar.MINUTE), Toast.LENGTH_SHORT).show();
//
//			String dateStart = Calendar.getInstance().get(Calendar.YEAR) + "/"
//					+ (Calendar.getInstance().get(Calendar.MONTH) + 1) + "/"
//					+ Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + " "
//					+ Calendar.getInstance().get(Calendar.HOUR) + ":" + Calendar.getInstance().get(Calendar.MINUTE);
//			String dateStop = currentInfo.getTakeTime().get(Calendar.YEAR) + "/"
//					+ currentInfo.getTakeTime().get(Calendar.MONTH) + "/"
//					+ Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + " "
//					+ currentInfo.getTakeTime().get(Calendar.HOUR) + ":"
//					+ currentInfo.getTakeTime().get(Calendar.MINUTE);
//
//			SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm");
//
//			Date d1 = null;
//			Date d2 = null;
//			try {
//				d1 = format.parse(dateStart);
//				d2 = format.parse(dateStop);
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//
//			// Get msec from each, and subtract.
//			long diff = d2.getTime() - d1.getTime();
//			long diffSeconds = diff / 1000 % 60;
//			long diffMinutes = diff / (60 * 1000) % 60;
//			long diffHours = diff / (60 * 60 * 1000);
//			long ddays = diff / (24 * 60 * 60 * 1000);
//			System.out.println("Time in seconds: " + diffSeconds + " seconds.");
//			System.out.println("Time in minutes: " + diffMinutes + " minutes.");
//			System.out.println("Time in hours: " + diffHours + " hours.");
//
//			Toast.makeText(MainActivity.this, "--Day Diff-- " + ddays + "---" + dailyFlag, Toast.LENGTH_SHORT).show();
//
//			if (ddays >= 0) {
//				if (dailyFlag) {
//					Log.d("---------------------", "--------Block 1--------");
//					alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, currentInfo.getTakeTime().getTimeInMillis(),
//							AlarmManager.INTERVAL_DAY, pi);
//				} else {
//
//					Log.d("---------------------", "--------Block 2--------");
//					Log.d("-------Selected---------",
//							(currentInfo.getTakeTime().get(Calendar.DAY_OF_MONTH) + "-"
//									+ currentInfo.getTakeTime().get(Calendar.MONTH) + "-"
//									+ currentInfo.getTakeTime().get(Calendar.YEAR) + "----"
//									+ currentInfo.getTakeTime().get(Calendar.HOUR) + ":"
//									+ currentInfo.getTakeTime().get(Calendar.MINUTE)));
//					Log.d("-------System------",
//							(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "-"
//									+ Calendar.getInstance().get(Calendar.MONTH+1) + "-"
//									+ Calendar.getInstance().get(Calendar.YEAR) + "----"
//									+ Calendar.getInstance().get(Calendar.HOUR) + ":"
//									+ Calendar.getInstance().get(Calendar.MINUTE)));
//
//					alarmManager.set(AlarmManager.RTC_WAKEUP, currentInfo.getTakeTime().getTimeInMillis(), pi);
//				}
//				intentArray.add(pi);
//			} else {
//				Toast.makeText(MainActivity.this, "No Date Diff...", Toast.LENGTH_SHORT).show();
//			}
//
//		}
//
//	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//
//		return true;
//	}
//
//	@Override
//	protected void onStart() {
//		super.onStart();
//		SharedPreferences sp = getSharedPreferences(SP_DATA, MODE_PRIVATE);
//		SharedPreferences.Editor editor = sp.edit();
//		String date1 = "Medicine Name: Aspirin" + "\n" + "Status: Taken in time";
//		String date2 = "Medicine Name: Chocolate" + "\n" + "+Status: Not taken in time";
//		editor.putString("20141201", date1);
//		editor.putString("20140929", date2);
//		editor.commit();
//		// editor.putString(KEY_ET_DATA,etData.getText().toString());
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}
//}
