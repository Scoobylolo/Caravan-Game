
public class Dog extends Animal{
	final private boolean has_leash;
	final private String last_potty_break;

	public Dog(String name,double weight,String owner,boolean has_leash,
		String last_potty_break){

		super(name,weight,"Dog",owner);

		this.has_leash=has_leash;
		this.last_potty_break=last_potty_break;
	}

	public boolean getLeash(){
		return this.has_leash;
	}
	public String getPottyBreak(){
		return this.last_potty_break;
	}

}