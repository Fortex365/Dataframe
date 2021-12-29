package com.company;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    /*
    * Code tests:
    * */
    public static void main(String[] args) throws ValueError, IOException, KeyError {
	    ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add("User1");
        stringArrayList.add("User2");
        stringArrayList.add("User3");
        Index users = new Index(stringArrayList, "Uzivatele");
        assert users.len() == 3;
        assert users.getName() == "Uzivatele";
        assert users.getLoc("User2") == 1;
        Index usersNoName = new Index(stringArrayList);
        assert usersNoName.getName() == "";
        System.out.println("Index 'Users' to string: "+System.lineSeparator()+users.toString(","));

        ArrayList<Integer> nums = new ArrayList<>();
        nums.add(10);
        nums.add(200);
        nums.add(3000);
        Series<Integer> userSalary = new Series<>(nums, users);
        // Formated output
        System.out.println("Serie 'usersSalary' to string: "+System.lineSeparator()+userSalary);
        // Value in serie
        var present = userSalary.get("User2");
        System.out.println("Present value 'User2' in serie: "+present);
        // Value not in serie
        var notPresent = userSalary.get("User20");
        System.out.println("Nonpresent value of 'User20' in serie: "+notPresent + System.lineSeparator());
        // Apply inc by 10 to current serie returns new one
        System.out.println("Applied inc by 10 to serie 'usersSalary':"+System.lineSeparator()+userSalary.apply(Main::inc));
        // Returns iterator over pairs in serie
        System.out.println("Iterator over 'userSalary' serie:");
        for (var i: userSalary.items()) {
            System.out.println(i);
        }
        // See the shape as string user has to parse it manually
        System.out.println(System.lineSeparator()+"Shape is " + userSalary.shapeString());
        // Get shape as array where 0-nth index is size of serie 1-nth empty (still not a Dataframe)
        var shape = userSalary.shape();
        System.out.println(System.lineSeparator()+"Shape is " + shape[0] + " x " + shape[1]);
        // toString with separator
        System.out.println(System.lineSeparator()+"'userSalary' serie to string:"+System.lineSeparator()+userSalary.toString(","));
        // save serie as csv
        userSalary.saveAsCsv("users_salary");
        // print max of salary of userSalary
        System.out.println(Series.maxValue(userSalary.getValues()));
        // print min
        System.out.println(Series.minValue(userSalary.getValues()));

        // create additional series
        var phoneNumbers = new Series<String>(new ArrayList<String>(Arrays.asList("604574148","745894274","798854621")), users);
        var address = new Series<String>(new ArrayList<String>(Arrays.asList("Riegrova","Olomoucká","Kovárenská")), users);
        // combine them together
        var values = new ArrayList<Series>(Arrays.asList(userSalary,phoneNumbers,address));
        // create index for them
        var columns = new Index(new ArrayList<String>(Arrays.asList("salary","phoneNumbers","addresses")));
        // put them into dataframe
        var dataframe = new Dataframe(values, columns);
        // retrieve from dataframe
        var g = dataframe.get("phoneNumbers");
        System.out.println(System.lineSeparator()+"Dataframe:"+System.lineSeparator()+g);
        // save dataframe as csv
        dataframe.saveAsCsv("dataframe_users");
    }

    public static int inc(int a){
        return a+10;
    }
}
