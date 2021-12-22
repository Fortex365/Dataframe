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
    }
}
