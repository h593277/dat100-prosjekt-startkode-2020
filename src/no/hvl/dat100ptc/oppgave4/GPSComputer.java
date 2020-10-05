package no.hvl.dat100ptc.oppgave4; 
import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;

public class GPSComputer {
	
	private GPSPoint[] gpspoints;
	
	public GPSComputer(String filename) {

		GPSData gpsdata = GPSDataFileReader.readGPSFile(filename);
		gpspoints = gpsdata.getGPSPoints();

	}

	public GPSComputer(GPSPoint[] gpspoints) {
		this.gpspoints = gpspoints;
	}
	
	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}
	
	// beregn total distances (i meter)
	public double totalDistance() {

		double distance = 0;

		for(int i = 1; i < gpspoints.length; i++) {
			distance += GPSUtils.distance(gpspoints[i-1], gpspoints[i]);
		}
		
		return distance;
	}

	// beregn totale hÃ¸ydemeter (i meter)
	public double totalElevation() {

		double elevation = 0;

		for(int i = 1; i < gpspoints.length; i++) {
			if(gpspoints[i].getElevation() > gpspoints[i-1].getElevation()) {
				elevation += gpspoints[i].getElevation() - gpspoints[i-1].getElevation();
			}
		}
		
		return elevation;
	}

	// beregn total tiden for hele turen (i sekunder)
	public int totalTime() {

		return gpspoints[gpspoints.length-1].getTime() - gpspoints[0].getTime();

	}
		
	// beregn gjennomsnitshastighets mellom hver av gps punktene

	public double[] speeds() {
		
		double[] speeds = new double[gpspoints.length-1];
		for(int i = 0; i < speeds.length; i++) {
			speeds[i] = GPSUtils.speed(gpspoints[i], gpspoints[i+1]);
		}
		
		return speeds;
	}
	
	public double maxSpeed() {
		
		return  GPSUtils.findMax(speeds());
	}

	public double averageSpeed() { //Average speed in km/h

		return totalDistance()/totalTime() *3.6;
	}

	/*
	 * bicycling, <10 mph, leisure, to work or for pleasure 4.0 bicycling,
	 * general 8.0 bicycling, 10-11.9 mph, leisure, slow, light effort 6.0
	 * bicycling, 12-13.9 mph, leisure, moderate effort 8.0 bicycling, 14-15.9
	 * mph, racing or leisure, fast, vigorous effort 10.0 bicycling, 16-19 mph,
	 * racing/not drafting or >19 mph drafting, very fast, racing general 12.0
	 * bicycling, >20 mph, racing, not drafting 16.0
	 */

	// conversion factor m/s to miles per hour
	public static double MS = 2.236936;

	//Alt etter denne komentaren må fiskes
	
	// beregn kcal gitt weight og tid der kjÃ¸res med en gitt hastighet
	public double kcal(double weight, int secs, double speed) {

		double kcal;

		// MET: Metabolic equivalent of task angir (kcal x kg-1 x h-1)
		double met = 0;		
		double speedmph = speed * MS;
		
		if(speedmph < 10) met = 4.0;
		if(speedmph >= 10) met = 6.0;
		if(speedmph >= 12) met = 8.0;
		if(speedmph >= 14) met = 10.0;
		if(speedmph >= 16) met = 12.0;
		if(speedmph > 20) met = 16.0;

		kcal = met * weight * secs / 3600.0;
		
		return kcal;
	}

	public double totalKcal(double weight) {

		double totalkcal = 0;

		double[] speeds = speeds();
		
		for(int i = 0; i < gpspoints.length-1; i++) {
			totalkcal += kcal(weight, gpspoints[i+1].getTime()-gpspoints[i].getTime(), speeds[i]);
		}
		
		return totalkcal;
	}
	
	private static double WEIGHT = 80.0;
	
	public void displayStatistics() {

		System.out.println("==============================================");

		System.out.format("%-15s:%12s\n", "Total Time", GPSUtils.formatTime(totalTime()));
		System.out.format("%-15s:%12.2f km\n", "Total distance", totalDistance()/1000);
		System.out.format("%-15s:%12.2f m\n", "Total elevation", totalElevation());
		System.out.format("%-15s:%12.2f km/t\n", "Max speed", maxSpeed());
		System.out.format("%-15s:%12.2f km/t\n", "Average speed", averageSpeed());
		System.out.format("%-15s:%12.2f kcal\n", "Energy", totalKcal(2)); //Trenger å putte inn en ordentlig verdi her!!!!
		
		System.out.println("==============================================");
	}

}
