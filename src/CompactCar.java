public class CompactCar extends Vehicle{

	public CompactCar(){
		super(2,"CompactCar");
	}

	public boolean getHasTrailer(){
		return false;
	}
	public double getTrailerLength(){
		return 0.0;
	}
	public int getTrailerCapacity(){
		return 0;
	}
	
}