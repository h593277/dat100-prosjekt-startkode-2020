package no.hvl.dat100ptc.oppgave2;
import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSData {

	private GPSPoint[] gpspoints;
	protected int antall = 0;

	public GPSData(int n) {

		for(antall = 0; antall < n; antall++)
		{
			gpspoints[antall] = new GPSPoint();
		}
	}

	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}
	
	protected boolean insertGPS(GPSPoint gpspoint) {

		boolean inserted = false;
		for(antall = 0; antall < gpspoints.length; antall++)
		{
			gpspoints[antall] = gpspoint;
			if(gpspoints[antall] == gpspoint)
			{
				inserted = true;
			}
		}
		
		return inserted;
		
	}

	public boolean insert(String time, String latitude, String longitude, String elevation) {
		
		//GPSPoint gpspoint;
		insertGPS(GPSDataConverter.convert(time, latitude, longitude, elevation));
		
		boolean inserted = true;
		
		return inserted;
		
	}

	public void print() {

		System.out.println("====== Konvertert GPS Data - START ======");

		for(antall = 0; antall < gpspoints.length; antall++)
		{
			System.out.println(antall + " (" + gpspoints.toString() + ")");
		}
		
		 System.out.println("====== Konvertert GPS Data - SLUTT ======");

	}
}
