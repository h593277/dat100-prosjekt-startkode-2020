package no.hvl.dat100ptc.oppgave5;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

public class ShowRoute extends EasyGraphics {

	private static int MARGIN = 50;
	private static int MAPXSIZE = 800;
	private static int MAPYSIZE = 800;

	private GPSPoint[] gpspoints;
	private GPSComputer gpscomputer;
	
	public ShowRoute() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		gpscomputer = new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		makeWindow("Route", MAPXSIZE + 2 * MARGIN, MAPYSIZE + 2 * MARGIN);

		showRouteMap(MARGIN + MAPYSIZE);
		
		showStatistics();
	}

	// antall x-pixels per lengdegrad
	public double xstep() {

		double maxlon = GPSUtils.findMax(GPSUtils.getLongitudes(gpspoints));
		double minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));

		double xstep = MAPXSIZE / (Math.abs(maxlon - minlon)); 

		return xstep;
	}

	// antall y-pixels per breddegrad
	public double ystep() {
	
		double ystep;
		
		double maxLat = GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints));
		double minLat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));
		
		ystep = MAPYSIZE / (Math.abs(maxLat - minLat));
		
		return ystep;
		
	}

	public void showRouteMap(int ybase) {

		setColor(0,128,0);
		int x,y;
		for(int i = 0; i < gpspoints.length; i++)
		{	
			x = (int)gpspoints[i].getLongitude()*(int)xstep();
			y = ybase - (int)gpspoints[i].getLatitude()*(int)ystep();
			fillCircle(x,y,5);
		}
	}

	public void showStatistics() {

		int TEXTDISTANCE = 20;

		setColor(0,0,0);
		setFont("Courier",12);
		int ybase = 20;
		
		drawString("Time: " + (gpscomputer.totalTime()/3600) + ":" + (gpscomputer.totalTime()/60%60) + ":" + (gpscomputer.totalTime()%60), TEXTDISTANCE, ybase);
		drawString(String.format("Average Speed: %.2f", (gpscomputer.averageSpeed())) + " km/t", TEXTDISTANCE, ybase+20);
		drawString(String.format("Total Distance: %.2f", (gpscomputer.totalDistance())) + " km", TEXTDISTANCE, ybase+40);
		drawString(String.format("Total Elevation: %.2f", (gpscomputer.totalElevation())) + " elevation", TEXTDISTANCE, ybase+60);
		drawString(String.format("Total Kcal: %.2f", (gpscomputer.totalKcal(80))) + " kcal", TEXTDISTANCE, ybase+80);
		
		
		
	
	}

}
