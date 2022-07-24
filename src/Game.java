import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
public class Game{
    final private String user_name;
    final private Scanner scnr;

    final private int min_val = 1;
    final private int max_val = 10;
    Random ran = new Random();



    private int choice=1;
    private int new_vehicles=0;
    private int chosen_caravan=1;
    private int launch_caravan=1;
    private int chosen_route=1;
    private int total_successful_trips=0;
    private int total_trips=0;


    private boolean[] stages = {false,false,false,false,false};
    private Caravan[][] all_caravans = new Caravan[3][max_val];
    private Vehicle[] all_vehicles = new Vehicle[max_val];
    private Animal[][] all_animals = new Animal[10][14];
    private Animal[][][] animal_array = new Animal[3][10][14];
    private boolean[] caravans_launched = {false,false,false};
    private boolean[] routes_taken = {false,false,false};
    private boolean[] init_caravans = {false,false,false};
    private ArrayList<String> animals_saved = new ArrayList<String>();




    Game(String user_name,Scanner scnr){
        this.user_name=user_name;
        this.scnr=scnr;
    }

    public void start(){
        while (true){
            if (stages[0]==false){
                System.out.printf("\n1. Make new caravan\n2. Add vehicle to caravan\n3. See caravans\n");
                System.out.printf("4. Launch caravan\n5. Exit game\n");
            }
            if (scnr.hasNextInt() && stages[0]==false){
                choice=scnr.nextInt();
                if (choice>5 || choice<1){
                    System.out.println("\nPlease select either option 1, 2, 3, 4, or 5.");
                    scnr.nextLine();
                    continue;
                }
                stages[0]=true;
            }
            else{
                if (stages[0]==false){
                    System.out.println("\nPlease select either option 1, 2, 3, 4, or 5.");
                    scnr.nextLine();
                    continue;
                }

            }


            if (choice==1){
                System.out.println("How many vehicles do you want to assign to the caravan (10 max)?: ");

                try{
                    if (stages[1]==false){
                        new_vehicles=scnr.nextInt();
                        if (new_vehicles>10 || new_vehicles<1){
                            System.out.println("\nPlease choose a number from 1-10");
                            scnr.nextLine();
                            new_vehicles=0;
                            continue;
                        }
                        stages[1]=true;

                    }
                }
                catch(java.util.InputMismatchException e){
                    System.out.println("\nPlease choose a number from 1-10");
                    scnr.nextLine();
                    continue;
                }


                //Now assign the caravan vehicles.


                if (stages[2]==false){
                    if (all_caravans[0][0]!=null && all_caravans[1][0]!=null &&
                            all_caravans[2][0]!=null){
                        System.out.println("\nCannot add a new caravan, all caravans have already been created!");
                        reset_progress(stages);
                        continue;
                    }
                    empty_car_array(all_vehicles);
                    for (int i=0;i<new_vehicles;i++){
                        assign_vehicles(all_vehicles);
                    }

                    for (int i=0;i<all_caravans.length;i++){
                        if (all_caravans[i][0]!=null) continue;
                        for (int j=0;j<all_caravans[i].length;j++){
                            all_caravans[i][j]=all_vehicles[j];
                            init_caravans[i]=true;
                        }
                        break;
                    }


                    int current_caravan=-1;
                    for (int i=init_caravans.length-1;i>=0;i--){
                        if (init_caravans[i]==true){current_caravan=i;break;}
                    }

                    for (int i=0;i<animal_array.length;i++){
                        if (i!=current_caravan) continue;
                        for (int j=0;j<animal_array[i].length;j++){
                            animal_array[i][j]=all_animals[j];
                        }
                        break;
                    }


                    System.out.printf("Added %d vehicles to the caravan\n\n",new_vehicles);


                    for (int i=0;i<all_vehicles.length;i++){
                        get_vehicle_string(all_vehicles[i],i,current_caravan);
                    }


                    System.out.println("\nCaravan made!");
                    stages[2]=true;


                    reset_progress(stages);
                    continue;


                }
            }
            else if (choice==2){
                if (stages[1]==false){
                    try{
                        System.out.println("Choose caravan you want to add a vehicle to (1 2 3): ");
                        chosen_caravan=scnr.nextInt();
                        if (chosen_caravan<1 || chosen_caravan>3){
                            System.out.println("Please select 1, 2, or 3.");
                            scnr.nextLine();
                            continue;
                        }
                        chosen_caravan-=1;
                        if (caravans_launched[chosen_caravan]==true){
                            System.out.println("\nCannot add vehicles to this caravan since it was launched today!\n");
                            reset_progress(stages);
                            continue;
                        }
                        all_vehicles[0]=null;
                        boolean null_found=false;
                        assign_vehicles(all_vehicles);
                        for (int i=0;i<all_caravans[0].length;i++){
                            if (all_caravans[chosen_caravan][i]==null){
                                all_caravans[chosen_caravan][i]=all_vehicles[0];
                                animal_array[chosen_caravan][i]=all_animals[0];
                                null_found=true;
                                break;
                            }
                        }
                        if (null_found==false){
                            System.out.println("\nThis caravan is already full!");
                            reset_progress(stages);
                            continue;
                        }
                        else{
                            System.out.printf("\nVehicle added: %s\n",all_vehicles[0].getVehicleName());
                            reset_progress(stages);
                            continue;
                        }
                    }catch(java.util.InputMismatchException e){
                        System.out.println("Please select 1, 2, or 3.");
                        scnr.nextLine();
                        continue;
                    }
                }
            }

            else if (choice==3){
                get_caravan_string(all_caravans);
                reset_progress(stages);
                System.out.println("");
                continue;
            }
            else if (choice==4){
                if (stages[1]==false){
                    try{
                        System.out.println("Which caravan would you like to launch? (1 2 3)");
                        launch_caravan=scnr.nextInt();
                        if (launch_caravan<1 || launch_caravan>3){
                            System.out.println("Please select 1, 2, or 3.");
                            scnr.nextLine();
                            continue;
                        }
                        launch_caravan-=1;
                        if (all_caravans[launch_caravan][0]==null){
                            System.out.println("\nThis caravan has no vehicles!");
                            reset_progress(stages);
                            continue;
                        }
                        else if (caravans_launched[launch_caravan]==true){
                            System.out.println("\nThis caravan was already launched today!");
                            reset_progress(stages);
                            continue;
                        }
                        stages[1]=true;
                    }catch(java.util.InputMismatchException e){
                        System.out.println("Please select 1, 2, or 3.");
                        scnr.nextLine();
                        continue;
                    }

                }
                if (stages[2]==false){
                    System.out.println("\n\nThere are three routes...");
                    System.out.println("Route 1 has 20% chance of making it");
                    System.out.println("Route 2 has 25% chance of making it");
                    System.out.println("Route 3 has 33% chance of making it");
                    System.out.println("\nWhich route would you like to take? (1 2 3)");
                    try{
                        chosen_route=scnr.nextInt();
                        if (chosen_route>3 || chosen_route<1){
                            System.out.println("Please choose 1 2 or 3");
                            scnr.nextLine();
                            continue;
                        }
                        chosen_route-=1;
                        if (routes_taken[chosen_route]==true){
                            System.out.printf("\nYou have already launched a caravan through route %d today!\n",
                                    chosen_route+1);
                            reset_progress(stages);
                            continue;
                        }

                        boolean all_launched=true;
                        routes_taken[chosen_route]=true;
                        for (int i=0;i<routes_taken.length;i++){
                            if (routes_taken[i]==false){
                                all_launched=false;
                                break;
                            }
                        }
                        if (all_launched==true){
                            System.out.println("\nAll caravans have launched for today!");
                            System.out.println("Get ready for tomorrow!\n");
                            for (int i=0;i<routes_taken.length;i++){
                                routes_taken[i]=false;
                                caravans_launched[i]=false;
                            }
                            for (int i=0;i<all_vehicles.length;i++){
                                all_vehicles[i]=null;
                            }


                            for (int a=0;a<animal_array.length;a++){
                                for (int b=0;b<animal_array[a].length;b++){
                                    for (int c=0;c<animal_array[a][b].length;c++){
                                        animal_array[a][b][c]=null;
                                    }
                                }
                            }

                            for (int a=0;a<all_animals.length;a++){
                                for (int k=0;k<all_animals[a].length;k++){
                                    all_animals[a][k]=null;
                                }
                            }
                            for (int i=0;i<init_caravans.length;i++){
                                init_caravans[i]=false;
                            }


                        }
                        send_to_route(launch_caravan,chosen_route+1,all_launched);

                        reset_progress(stages);
                        continue;
                    }catch(java.util.InputMismatchException e){
                        System.out.println("Please choose 1 2 or 3");
                        scnr.nextLine();
                        continue;
                    }

                }
            }
            else if (choice==5){
                System.out.println("Are you sure you want to exit?: ");
                String response=scnr.next();
                if (response.toLowerCase().equals("yes")){
                    double calculation;
                    if (total_trips==0){
                        calculation=0.0;
                    }
                    else{
                        calculation=(double)total_successful_trips/total_trips*100;
                    }

                    String general=String.format("\n\nBye %s!!!\n\n---Stats---\n\nSuccessful trips: %d\nTotal trips: %d\nPercentage of successful trips: %.2f%%\n",
                            this.user_name,total_successful_trips,total_trips,calculation);

                    System.out.println("\n\nNumber of animals saved:\n");
                    int horse_count=0;
                    int dog_count=0;
                    int cat_count=0;
                    for (int i=0;i<animals_saved.size();i++){
                        if (animals_saved.get(i)=="Horse") horse_count+=1;
                        else if (animals_saved.get(i)=="Dog") dog_count+=1;
                        else if (animals_saved.get(i)=="Cat") cat_count+=1;
                    }
                    System.out.printf("Horses saved: %d\n",horse_count);
                    System.out.printf("Dogs saved: %d\n",dog_count);
                    System.out.printf("Cats saved: %d\n",cat_count);
                    System.out.printf("Total saved: %d\n",cat_count+horse_count+dog_count);

                    if (total_successful_trips<6){
                        System.out.printf("%sLevel: %s\n",general,"Beginner");
                    }
                    else if (total_successful_trips<12){
                        System.out.printf("%sLevel: %s\n",general,"Amateur");
                    }
                    else if (total_successful_trips<18){
                        System.out.printf("%sLevel: %s\n",general,"Intermediate");
                    }
                    else if (total_successful_trips<24){
                        System.out.printf("%sLevel: %s\n",general,"Advanced");
                    }
                    else if (total_successful_trips>25){
                        System.out.printf("%sLevel: %s\n",general,"Expert");
                    }
                    break;
                }
                else{
                    scnr.nextLine();
                    reset_progress(stages);
                    continue;
                }
            }
        }
    }

    public int index_by_element(String element,ArrayList<String> arr){
        for (int i=0;i<arr.size();i++){
            if (arr.get(i)==element) return i;
        }
        return -1;
    }
    public boolean found(String one,ArrayList<String> arr){
        for (int i=0;i<arr.size();i++){
            if (arr.get(i)==one){
                return true;
            }
        }
        return false;
    }

    public void send_to_route(int caravan_number,int route_number,boolean all_launched){
        //route 1 is 20% success
        //route 2 is 25% success
        //route 3 is 33% success


        ArrayList<String> order = new ArrayList<String>();
        order.add("CompactCar");
        order.add("MidCar");
        order.add("SUV");
        order.add("Truck");


        ArrayList<String> default_list = new ArrayList<String>();

        ArrayList<String> new_arr = new ArrayList<String>();



        //here sort the caravan...
        for (int i=0;i<all_caravans.length;i++){
            if (i!=caravan_number) continue;
            for (int j=0;j<all_caravans[i].length;j++){
                if (all_caravans[i][j]==null) continue;
                default_list.add(((Vehicle)(all_caravans[i][j])).getVehicleName());
            }
        }

        int past_length=default_list.size();
        int del_index;
        while (new_arr.size()!=past_length){
            del_index=-1;
            for (int i=0;i<order.size();i++){
                if (found(order.get(i),default_list)==true){
                    new_arr.add(order.get(i));
                    del_index=index_by_element(order.get(i),default_list);
                    break;
                }
            }
            if (del_index>=0){
                default_list.remove(del_index);
            }
        }

        System.out.println("\nSorted caravan:\n");
        System.out.println(new_arr);
        System.out.println("");

        if (all_launched==true){
            for (int a=0;a<all_caravans.length;a++){
                for (int k=0;k<all_caravans[a].length;k++){
                    all_caravans[a][k]=null;
                }
            }
        }


        int up=5;
        int down=0;
        if (route_number==1) up=5;
        else if (route_number==2) up=4;
        else if (route_number==3) up=3;
        int success = ran.nextInt(up+1) + down;
        if (all_launched==false) caravans_launched[caravan_number]=true;
        total_trips+=1;
        if (success==1){
            System.out.println("\nYour caravan made it!!!\n");
            total_successful_trips+=1;

            for (int i=0;i<animal_array.length;i++){
                if (caravan_number!=i) continue;
                for (int j=0;j<animal_array[i].length;j++){
                    for (int k=0;k<animal_array[i][j].length;k++){
                        if (animal_array[i][j][k]==null) continue;
                        animals_saved.add(animal_array[i][j][k].getAnimalID());
                    }
                }
            }
        }
        else{
            System.out.println("\nYour caravan did not make it :(\n");
        }
    }

    public void empty_car_array(Vehicle my_arr[]){
        for (int i=0;i<my_arr.length;i++){
            my_arr[i]=null;
        }
    }

    public void reset_progress(boolean the_stages[]){
        for (int i=0;i<the_stages.length;i++){
            the_stages[i]=false;
        }
    }

    public void get_caravan_string(Caravan[][] custom_caravan){
        for (int i=0;i<custom_caravan.length;i++){
            if (custom_caravan[i][0]==null){
                System.out.printf("\n%35s %d\n","Caravan",i+1);
                System.out.printf("%15s","");
                System.out.print("No cars in this caravan!\n");
                continue;
            }
            System.out.printf("\n%35s %d\n","Caravan",i+1);
            for (int j=0;j<custom_caravan[i].length;j++){
                if (custom_caravan[i][j]!=null){
                    System.out.printf("%15s","");
                    get_vehicle_string((Vehicle)custom_caravan[i][j],j,i);
                }

            }
            System.out.println("");
        }


    }

    public void get_vehicle_string(Vehicle x,int i,int caravan_place){
        if (x!=null){
            String to_print;
            String animal_string="";

            for (int a=0;a<animal_array.length;a++){

                if (a==caravan_place){
                    // System.out.printf("in 1\ncaravan: %d\ncar: %d\n\n",caravan_place,i);
                    for (int b=0;b<animal_array[a].length;b++){

                        if (b==i){
                            // System.out.printf("in 2\ncaravan: %d\ncar: %d\n\n",caravan_place,i);
                            for (int c=0;c<animal_array[a][b].length;c++){
                                // System.out.println("last loop");
                                if (animal_array[a][b][c]==null) continue;
                                // System.out.println(animal_array[a][b][c].getAnimalID());
                                if (c<animal_array[a][b].length-1){
                                    if (animal_array[a][b][c+1]==null){
                                        animal_string+=animal_array[a][b][c].getAnimalID();
                                    }
                                    else{
                                        animal_string+=animal_array[a][b][c].getAnimalID()+",";
                                    }
                                }
                                else{
                                    animal_string+=animal_array[a][b][c].getAnimalID();
                                }
                            }
                        }
                    }
                }
            }

            if (x.getVehicleName().equals("SUV") ||
                    x.getVehicleName().equals("Truck")){
                String trailer_string;
                if (x.getHasTrailer()==true){
                    trailer_string=String.format("with trailer of length %.2f ft [%s]",
                            x.getTrailerLength(),animal_string);
                }
                else{
                    trailer_string=String.format("without trailer [%s]",animal_string);
                }
                to_print=String.format("%d. %s %s",i+1,
                        x.getVehicleName(),trailer_string);


            }
            else{
                to_print=String.format("%d. %s [%s]",i+1,
                        x.getVehicleName(),animal_string);
            }
            System.out.println(to_print);
        }
    }

    public Animal[] random_animals(Vehicle x){
        int total_animals=x.getCapacity();
        int trailer_cap=x.getTrailerCapacity();
        Animal[] to_return = new Animal[14];
        if (x.getHasTrailer()==true){
            for (int i=0;i<trailer_cap;i++){
                Horse new_horse = new Horse("Jeff",480.26,"Jimmy",true);
                to_return[i]=new_horse;
            }
        }

        for (int i=trailer_cap;i<total_animals+trailer_cap;i++){
            int num = ran.nextInt(2) + 1;
            switch(num){
                case 1:
                    Dog new_dog = new Dog("Bryan",84.12,"Carlos",false,"10:30AM");
                    to_return[i]=new_dog;
                    break;
                case 2:
                    Cat new_cat = new Cat("Jenny",35.50,"Maria",false,"07:45PM");
                    to_return[i]=new_cat;
                    break;
                default:
                    System.out.println("Error here...");
                    break;
            }
        }
        return to_return;

    }

    public void fill_non_empty(Vehicle x,Vehicle[] the_vehicles){
        for (int i=0;i<the_vehicles.length;i++){
            if (the_vehicles[i]==null){
                the_vehicles[i]=x;
                all_animals[i]=random_animals(x);
                break;
            }
        }
    }

    public void assign_vehicles(Vehicle[] the_vehicles){

        int x = ran.nextInt(max_val) + min_val;
        int the_trailer = ran.nextInt(2) + 0;
        int length = ran.nextInt(2) + 0;


        boolean for_trailer=false;
        int trailer_length=0;
        int trailer_capacity=0;
        if (the_trailer>0){
            for_trailer=true;
            if (length>0){
                trailer_length=40; //in ft
                trailer_capacity=4;
            }
            else{
                trailer_length=20; //in ft
                trailer_capacity=2;
            }
        }
        switch(x){
            case 1:
                CompactCar obj1 = new CompactCar();
                fill_non_empty(obj1,the_vehicles);
                break;
            case 2:
                MidCar obj2 = new MidCar();
                fill_non_empty(obj2,the_vehicles);
                break;
            case 3:
                SUV obj3 = new SUV(for_trailer,trailer_length,trailer_capacity);
                fill_non_empty(obj3,the_vehicles);
                break;
            case 4:
                Truck obj4 = new Truck(for_trailer,trailer_length,trailer_capacity);
                fill_non_empty(obj4,the_vehicles);
                break;
            case 5:
                CompactCar obj5 = new CompactCar();
                fill_non_empty(obj5,the_vehicles);
                break;
            case 6:
                MidCar obj6 = new MidCar();
                fill_non_empty(obj6,the_vehicles);
                break;
            case 7:
                SUV obj7 = new SUV(for_trailer,trailer_length,trailer_capacity);
                fill_non_empty(obj7,the_vehicles);
                break;
            case 8:
                Truck obj8 = new Truck(for_trailer,trailer_length,trailer_capacity);
                fill_non_empty(obj8,the_vehicles);
                break;
            case 9:
                SUV obj9 = new SUV(for_trailer,trailer_length,trailer_capacity);
                fill_non_empty(obj9,the_vehicles);
                break;
            case 10:
                Truck obj10 = new Truck(for_trailer,trailer_length,trailer_capacity);
                fill_non_empty(obj10,the_vehicles);
                break;
            default:
                System.out.println("There has been an error...");
                break;
        }
    }
}