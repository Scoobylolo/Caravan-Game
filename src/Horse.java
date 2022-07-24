public class Horse extends Animal{
	final private boolean has_hay;

	Horse(String name,double weight,String owner,boolean has_hay){

		super(name,weight,"Horse",owner);

		this.has_hay=has_hay;
	}

	public boolean getHay(){
		return this.has_hay;
	}


}