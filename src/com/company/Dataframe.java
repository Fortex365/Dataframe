package com.company;

import java.io.*;
import java.util.ArrayList;

/*
* Class representation of Dataframe.
* */
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

    /*
    * Returns length of the dataframe.
    * */
    public int len(){
        return columns.len();
    }

    /*
    * By given key tries to access the corresponding serie.
    * */
    public Series get(String key){
        try{
            return values.get(columns.getLoc(key));
        } catch (KeyError keyError) {
            return null;
        }
    }

    /*
    * Calculates the shape of the dataframe.
    * */
    public int[] shape(){
        int[] res = new int[2];
        res[0] = values.size();
        res[1] = columns.len();
        return res;
    }

    @Override
    public String toString(){
        return "Dataframe("+ this.shape()[0] + ", " + this.shape()[1] + ")";
    }

    /*
    * Returns the iterator over Dataframe
    * */
    public ArrayList<String> iterator(){
        return columns.getLabels();
    }

    /*
    * Returns Index object of Dataframe values
    * */
    public Index index(){
        return values.get(0).getIndex();
    }

    /*
    * Saves the dataframe class instance into csv format file.
    * */
    public void saveAsCsv(String fileName, String separator) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName + ".txt");
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        DataOutputStream file = new DataOutputStream(bos);

        var columnString = columns.toString(separator);
        file.writeBytes(columnString);
        file.flush();
        for (var s: values) {
            file.writeBytes(s.valuesString(separator));
        }
        file.flush();
        file.close();
    }

    /*
    * Overload method to provide default separator.
    * */
    public void saveAsCsv(String fileName) throws IOException {
        saveAsCsv(fileName, ",");
    }

    /*
    * Returns iterator over pairs (column, Series) as another iterator.
    * */
    public ArrayList<Object> items(){
        ArrayList<Object> res = new ArrayList<>();
        var column = columns.getLabels();

        for (int i = 0; i < columns.len(); i++) {
            ArrayList<Object> item = new ArrayList<>();
            item.add(column.get(i));
            item.add(values.get(i));

            res.add(item);
        }
        return res;
    }
}
