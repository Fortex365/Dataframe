package com.company;

import java.util.ArrayList;

/*
* Class reprezentation of series object.
* */
public class Series<T> {
    private ArrayList<T> values = new ArrayList<>();
    private Index index;

    /*
    * Series class init method.
    *
    * Args:
    *   values: Arraylist of values.
    *   index (optional): Index object. Defaults to Index
    * object with keys from 0 to n, where n is length of given values.
    * */
    public Series(ArrayList<T> values, Index index) throws ValueError {
        // Values must be positive length
        if(values.size() <= 0){
            throw new ValueError(new Exception(), "Values must contain at least one item.");
        }

        // Values and index must be compatible
        if(index != null && (values.size() != index.len())){
            throw new ValueError(new Exception(), "Values and index must be the the same size, " +
                    values.size() + " and " + index.len() + " was given.");
        }

        /*
        * If index wasnt given it defaults
        * to an Index object with keys from 0 to n, where n is size of given values.
        * */
        if(index == null){
            ArrayList<String> keys = new ArrayList<>();
            for(int i = 0; i < values.size(); i++){
                keys.add(String.valueOf(i));
            }

            // Update the index from null to default keys.
            index = new Index(keys);
        }

        this.values = values;
        this.index = index;
    }

    /*
    * Index object defaults to
    * instance of Index object with keys from 0 to n where n is length
    * of given values.
    * */
    public Series(ArrayList<T> values) throws ValueError {
        this(values, null);
    }

    /*Retrieve values from serie*/
    public ArrayList<T> getValues() {
        return values;
    }

    /*Retrieve the index from serie*/
    public Index getIndex() {
        return index;
    }
}
