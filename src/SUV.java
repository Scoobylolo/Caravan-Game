public class SUV extends Vehicle{
	final private boolean has_trailer;
	final private double trailer_length;
	final private int trailer_capacity;

	public SUV(boolean has_trailer, double trailer_length, int trailer_capacity){
		super(10,"SUV");
		this.has_trailer=has_trailer;
		this.trailer_length=trailer_length;
		this.trailer_capacity=trailer_capacity;
	}

	public boolean getHasTrailer(){
		return this.has_trailer;
	}
	public double getTrailerLength(){
		return this.trailer_length;
	}
	public int getTrailerCapacity(){
		return this.trailer_capacity;
	}
}
