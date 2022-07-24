public abstract class Vehicle extends Caravan{
	final private int animal_capacity;
	final private String vehicle_name;

	public Vehicle(int animal_capacity,String vehicle_name){
		this.animal_capacity=animal_capacity;
		this.vehicle_name=vehicle_name;
	}

	public abstract boolean getHasTrailer();
	public abstract double getTrailerLength();
	public abstract int getTrailerCapacity();

	public int getCapacity(){
		return this.animal_capacity;
	}

	public String getVehicleName(){
		return this.vehicle_name;
	}
}
