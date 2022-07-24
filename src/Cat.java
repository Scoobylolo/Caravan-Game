public class Cat extends Animal{
	final private boolean has_litter_box;
	final private String last_time_litter_cleaned;

	Cat(String name,double weight,String owner,boolean has_litter_box,
		String last_time_litter_cleaned){

		super(name,weight,"Cat",owner);
		this.has_litter_box=has_litter_box;
		this.last_time_litter_cleaned=last_time_litter_cleaned;
	}

	public boolean getLitterBox(){
		return this.has_litter_box;
	}
	public String getLastTimeCleaned(){
		return this.last_time_litter_cleaned;
	}


}