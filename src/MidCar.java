public class MidCar extends Vehicle{

	public MidCar(){
		super(5,"MidCar");
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