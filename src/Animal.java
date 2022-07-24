
public abstract class Animal extends Caravan{
	final private String name;
	final private double weight; //in lbs
	final private String animal_id;
	final private String owner;

	Animal(String name,double weight,String animal_id, String owner){
		this.name=name;
		this.weight=weight;
		this.animal_id=animal_id;
		this.owner=owner;
	}

	public String getName(){
		return this.name;
	}
	public double getWeight(){
		return this.weight;
	}
	public String getAnimalID(){
		return this.animal_id;
	}
	public String getOwner(){
		return this.owner;
	}

}