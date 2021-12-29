package com.company;

import java.util.ArrayList;

public class Dataframe {
    private ArrayList<Series> values = new ArrayList<>();
    private Index columns = null;

    public Dataframe(ArrayList<Series> values) throws ValueError {
        this(values, null);
    }

    public Dataframe(ArrayList<Series> values, Index columns) throws ValueError {
        // Values must be positive length
        if(values.size() <= 0){
            throw new ValueError(new Exception(),
                    "Values must contain at least one item.");
        }
        // Values and index must be compatible
        if(columns != null && (values.size() != columns.len())){
            throw new ValueError(new Exception(), "Values and index must be the the same size, " +
                    values.size() + " and " + columns.len() + " was given.");
        }
        /*
         * If columns wasnt given it defaults
         * to an Index object with keys from 0 to n, where n is size of given values.
         * */
        if(columns == null){
            ArrayList<String> keys = new ArrayList<>();
            for(int i = 0; i < values.size(); i++){
                keys.add(String.valueOf(i));
            }

            // Update the index from null to default keys.
            columns = new Index(keys);
        }

        this.values = values;
        this.columns = columns;
    }

    public int len(){
        return columns.len();
    }

    public Series get(String key){
        try{
            return values.get(columns.getLoc(key));
        } catch (KeyError keyError) {
            return null;
        }
    }
}
