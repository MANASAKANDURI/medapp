package aitmyhelloapp.examples.android.ait.hu.medicinereminderdemo;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import aitmyhelloapp.examples.android.ait.hu.medicinereminderdemo.data.MedicineInfo;

public class SetTimeActivity extends Activity {

	final Context context = this;

	/** Private members of the class */
	private TextView displayTime, displayDate;
	private Button pickTime, pickDate;
	private Calendar medicineTime;

	private int pDate;
	private int pMonth;
	private int pYear;

	private int pHour;
	private int pMinute;
	/**
	 * This integer will uniquely define the dialog to be used for displaying
	 * time picker.
	 */
	static final int TIME_DIALOG_ID = 0;
	static final int DATE_DIALOG_ID = 999;

	EditText etMedicineName, etDosage;

	private DatePicker dpResult;

	/** Callback received when the user "picks" a time in the dialog */
	private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			pHour = hourOfDay;
			pMinute = minute;
			updateDisplay();
			displayToast();
		}
	};

	/** Updates the time in the TextView */
	private void updateDisplay() {
		medicineTime.set(Calendar.MONTH, (pMonth+1));
		medicineTime.set(Calendar.DAY_OF_MONTH, pDate);
		medicineTime.set(Calendar.YEAR, pYear);		
		
		medicineTime.set(Calendar.HOUR_OF_DAY, pHour);
		medicineTime.set(Calendar.MINUTE, pMinute);
		medicineTime.set(Calendar.SECOND, 0);
		displayTime.setText(new StringBuilder().append(pad(pHour)).append(":").append(pad(pMinute)));

	} 

	/** Displays a notification when the time is updated */
	private void displayToast() {
		Toast.makeText(this, new StringBuilder().append("Time choosen is ").append(displayTime.getText()),
				Toast.LENGTH_SHORT).show();

	}

	/** Add padding to numbers less than ten */
	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_time);

		setCurrentDateOnView();
		dpResult.setVisibility(View.GONE);
		

		medicineTime = Calendar.getInstance();
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.time_array,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		etMedicineName = (EditText) findViewById(R.id.etMedicineName);
		etDosage = (EditText) findViewById(R.id.dosage);

		try {
			etMedicineName.setText(getIntent().getStringExtra("medicine_name"));

		} finally {

		}

		/** Capture our View elements */
		displayTime = (TextView) findViewById(R.id.timeDisplay);
		

		pickTime = (Button) findViewById(R.id.pickTime);

		/** Listener for click event of the button */
		pickTime.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialog(TIME_DIALOG_ID);
			}
		});

		pickDate = (Button) findViewById(R.id.pickDate);
		/** Listener for click event of the button */
		pickDate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//dpResult.setVisibility(View.VISIBLE);
				showDialog(DATE_DIALOG_ID);
			}
		});

		/** Get the current time */
		final Calendar cal = Calendar.getInstance();
		pHour = cal.get(Calendar.HOUR_OF_DAY);
		pMinute = cal.get(Calendar.MINUTE);

		/** Display the current time in the TextView */
		updateDisplay();

		/**
		 * copied from the other class
		 * //i.putExtra("medicine_name",medicineInfo.getMedicineName());
		 * //i.putExtra("medicine_time",medicineInfo.getTakeTime());
		 * //i.putExtra("medicine_length",medicineInfo.getLength().
		 * getStringValue());
		 * //i.putExtra("medicine_secondReminder",medicineInfo.getSecondReminder
		 * ());
		 **/

		try {
			displayTime.setText(getIntent().getStringExtra("medicine_time"));
		} finally {

		}

		Button btnSave = (Button) findViewById(R.id.btnSave);
		btnSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intentResult = new Intent();
				MedicineInfo newS = new MedicineInfo(etMedicineName.getText().toString(), etDosage.getText().toString(),
						medicineTime, MedicineInfo.RepeatLength.fromInt(0), true, false);
				intentResult.putExtra(getString(R.string.key_value), newS);
				setResult(RESULT_OK, intentResult);
				finish();
			}
		});

		Button btnSaveDaily = (Button) findViewById(R.id.btnSaveDaily);
		btnSaveDaily.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intentResult = new Intent();
				MedicineInfo newS = new MedicineInfo(etMedicineName.getText().toString(), etDosage.getText().toString(),
						medicineTime, MedicineInfo.RepeatLength.fromInt(0), true, true);
				intentResult.putExtra(getString(R.string.key_value), newS);
				setResult(RESULT_OK, intentResult);
				finish();
			}
		});
	}

	// display current date
	public void setCurrentDateOnView() {

		displayDate = (TextView) findViewById(R.id.dateDisplay);
		dpResult = (DatePicker) findViewById(R.id.dpResult);

		final Calendar c = Calendar.getInstance();
		pYear = c.get(Calendar.YEAR);
		pMonth = c.get(Calendar.MONTH);
		pDate = c.get(Calendar.DAY_OF_MONTH);

		// set current date into textview
		//displayDate.setText(new StringBuilder().append(pMonth + 1).append("-").append(pDate).append("-").append(pYear).append(" "));

		// set current date into datepicker
		dpResult.init(pYear, pMonth, pDate, null);

	}

	/** Create a new dialog for time picker */

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, mTimeSetListener, pHour, pMinute, false);
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, datePickerListener, pYear, pMonth, pDate);
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
			pYear = selectedYear;
			pMonth = selectedMonth;
			pDate = selectedDay;

			// set selected date into textview
			displayDate.setText(new StringBuilder().append(pMonth + 1).append("-").append(pDate).append("-")
					.append(pYear).append(" "));

			// set selected date into datepicker also
			dpResult.init(pYear, pMonth, pDate, null);

		}
	};
}
