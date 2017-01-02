package aitmyhelloapp.examples.android.ait.hu.medicinereminderdemo.data;

import com.orm.SugarRecord;

import android.provider.Settings.System;

import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MedicineInfo extends SugarRecord<MedicineInfo> implements Serializable {

	public enum RepeatLength {
		first("10", 0), second("15", 1), third("30", 2), fourth("45", 3);

		private String StringValue;
		private int value;

		private RepeatLength( String  toString, int value1) {
			StringValue = toString;
			value = value1;
		}

		public static RepeatLength fromInt(int value) {
			for (RepeatLength c : RepeatLength.values()) {
				if (c.value == value) {
					return c;
				}
			}
			return first;
		}

		public int getValue() {
			return value;
		}

		public String getStringValue() {
			return StringValue;
		}
	}

	private String medicineName;
	private String dosage;
	private Calendar takeTime;
	private RepeatLength length;
	private Boolean secondReminder;
	private Boolean daily;

	// private long id;

	/* empty constructor for SugarOrm */
	public MedicineInfo() {

	}

	// constructor
	public MedicineInfo(String medicineName, String dosage, Calendar takeTime, RepeatLength length,
			Boolean secondReminder, Boolean daily) {
		this.medicineName = medicineName;
		this.dosage = dosage;
		this.takeTime = takeTime;
		this.length = length;
		this.secondReminder = secondReminder;
		this.daily = daily;

		//java.lang.System.out.println("---------------Boromore-----" + convertTime(takeTime.getTimeInMillis()));
	}

	public String convertTime(long time) {
		Date date = new Date(time);
		Format format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		return format.format(date);
	}

	// some setter and getter methods

	public RepeatLength getLength() {
		return length;
	}

	public Boolean getDaily() {
		return daily;
	}

	public void setDaily(Boolean daily) {
		this.daily = daily;
	}

	public String getDosage() {
		return dosage;
	}

	public void setDosage(String dosage) {
		this.dosage = dosage;
	}

	public String getMedicineName() {
		return medicineName;
	}

	public Calendar getTakeTime() {
		return takeTime;
	}

	public void setTakeTime(Calendar takeTime) {
		this.takeTime = takeTime;
	}

	public void setLength(RepeatLength length) {
		this.length = length;
	}

	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}

	public Boolean getSecondReminder() {
		return secondReminder;
	}

	public void setSecondReminder(Boolean secondReminder) {
		this.secondReminder = secondReminder;
	}
}
