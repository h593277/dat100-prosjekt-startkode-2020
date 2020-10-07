package no.hvl.dat100ptc.oppgave2;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSDataConverter {

	// konverter tidsinformasjon i gps data punkt til antall sekunder fra midnatt
	// dvs. ignorer information om dato og omregn tidspunkt til sekunder
	// Eksempel - tidsinformasjon (som String): 2017-08-13T08:52:26.000Z
    // skal omregnes til sekunder (som int): 8 * 60 * 60 + 52 * 60 + 26 
	
	private static int TIME_STARTINDEX = 11; // startindex for tidspunkt i timestr

	public static int toSeconds(String timestr) {
		
		int secs;
		int hr, min, sec;
		String sekunder = timestr.substring(17, 18);
		String minutter = timestr.substring(14, 15);
		String timer = timestr.substring(12, 12);
		hr = Integer.parseInt(timer);
		min = Integer.parseInt(minutter);
		sec = Integer.parseInt(sekunder);
		
		secs = (hr*3600) + (min*60) + sec;
		return secs;
		
		
	}

	public static GPSPoint convert(String timeStr, String latitudeStr, String longitudeStr, String elevationStr) {

		GPSPoint gpspoint = new GPSPoint();

		gpspoint.setTime(Integer.parseInt(timeStr));
		gpspoint.setLatitude(Double.parseDouble(latitudeStr));
		gpspoint.setLongitude(Double.parseDouble(longitudeStr));
		gpspoint.setElevation(Double.parseDouble(elevationStr));
		return gpspoint;
	    
	}
	
}
