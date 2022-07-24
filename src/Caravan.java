public abstract class Caravan{

	public Caravan(){

	}

	public void organizeCaravan(){

	}

	public int getTotalVehicles(){
		return 1;
	}

	public boolean caravanMadeIt(){
		return true;
	}

	public void addVehicle(Vehicle the_vehicle,Caravan[][] the_caravan,int idx1,int idx2){
		the_caravan[idx1][idx2]=the_vehicle;
		// return the_caravan;
	}

	public void seeCaravan(){

	}



}