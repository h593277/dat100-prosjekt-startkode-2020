package no.hvl.dat100ptc.oppgave3;

import static java.lang.Math.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSUtils {

	public static double findMax(double[] da) {

		double max; 
		
		max = da[0];
		
		for (double d : da) {
			if (d > max) {
				max = d;
			}
		}
		
		return max;
	}

	public static double findMin(double[] da) {

		double min;

		min = da[0];
		
		for (double d : da) {
			if (d < min) {
				min = d;
			}
		}
		
		return min;
	}

	public static double[] getLatitudes(GPSPoint[] gpspoints) {

		double[] latitudes = new double[gpspoints.length];
		
		for(int i = 0; i < gpspoints.length; i++)
		{
			latitudes[i] = gpspoints[i].getLatitude();
		}
		
		return latitudes;
	}

	public static double[] getLongitudes(GPSPoint[] gpspoints) {

	double[] longitudes = new double[gpspoints.length];
		
		for(int i = 0; i < gpspoints.length; i++)
		{
			longitudes[i] = gpspoints[i].getLongitude();
		}
		
		return longitudes;

	}

	private static int R = 6371000; // jordens radius

	public static double distance(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		double d;
		double latitude1, longitude1, latitude2, longitude2;

		latitude1 = Math.toRadians(gpspoint1.getLatitude());
		latitude2 = Math.toRadians(gpspoint2.getLatitude());
		longitude1 = Math.toRadians(gpspoint1.getLongitude());
		longitude2 = Math.toRadians(gpspoint2.getLongitude());
		double q1 = latitude1;
		double q2 = latitude2;
		double q = latitude2 - latitude1;
		double lambda = longitude2 - longitude1;
		double a = (Math.pow(Math.sin(q/2), 2)) + Math.cos(q1)*Math.cos(q2)*(Math.pow(Math.sin(lambda/2), 2));
		double c = 2*Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		d = R*c;
		
		return d;
		
		

	}

	public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		int secs;
		secs = gpspoint2.getTime() - gpspoint1.getTime();
		double speed;
		double distance = distance(gpspoint1, gpspoint2);
		speed = distance/secs*3.6;
		return speed;

	}

	public static String formatTime(int secs) {

		String timestr;
		String TIMESEP = ":";
		timestr = "";
		if(secs/3600 < 10) timestr += "0";
		timestr += secs/3600 + TIMESEP;
		if(secs/60%60 < 10) timestr += "0";
		timestr += secs/60%60 + TIMESEP;
		if(secs%60 < 10) timestr += "0";
		timestr += secs%60;
		timestr = String.format("%10s", timestr);
		return timestr;


	}
	private static int TEXTWIDTH = 10;

	public static String formatDouble(double d) {

		String str;
		str = String.format("%.2f", d);
		str = String.format("%10s", str);
		
		return str;
		

	
		
	}
}
