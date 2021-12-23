package com.company;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws ValueError
    {
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

        ArrayList<Integer> nums = new ArrayList<>();
        nums.add(10);
        nums.add(200);
        nums.add(3000);
        Series<Integer> numbers = new Series<>(nums, users);
        System.out.println(numbers);
        var present = numbers.get("User2");
        System.out.println(present);
        var notPresent = numbers.get("User20");
        System.out.println(notPresent + System.lineSeparator());
        System.out.println(numbers.apply(Main::inc));

        for (var i: numbers.items()) {
            System.out.println(i);
        }
    }

    public static int inc(int a){
        return a+10;
    }
}
