package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;


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
            throw new ValueError(new Exception(),
                    "Values must contain at least one item.");
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

    /*Retrieves the length of serie*/
    public int len(){
        return this.values.size();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        var labels = index.getLabels();
        for (int i = 0; i < values.size(); i++) {
            stringBuilder.append(labels.get(i) + "\t" + values.get(i) + System.lineSeparator());
        }
        return stringBuilder.toString();
    }

    /*Helper method for finding the key in index*/
    private T getItem(String key) throws KeyError {
        try {
            return values.get(index.getLoc(key));
        } catch (KeyError e) {
            throw new KeyError(new Exception(), "Index doesnt exist, since " + key + " is not in labels.");
        }
    }

    /*Retrieves the corresponding value of serie by key*/
    public T get(String key){
        try{
            return this.getItem(key);
        } catch (KeyError keyError) {
            return null;
        }
    }

    /*Applies a function to a serie, returns new one*/
    public Series<T> apply(Function<T, T> func) throws ValueError {
        ArrayList<T> newValues = new ArrayList<>();

        for (var v: values) {
            newValues.add(func.apply(v));
        }
        return new Series<>(newValues, index);
    }

    /*Does a predicate on a serie and returns list of booleans*/
    public ArrayList<Boolean> predicate(Function<T, Boolean> func){
        ArrayList<Boolean> res = new ArrayList<>();

        for (var v: values) {
            res.add(func.apply(v));
        }
        return res;
    }

    /*Returns list of index:value string pairs*/
    public ArrayList<String> items(){
        var idx = index.getLabels();
        ArrayList<String> res = new ArrayList<>();

        for (int i = 0; i < idx.size(); i++) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(idx.get(i));
            stringBuilder.append(":");
            stringBuilder.append(values.get(i));
            res.add(stringBuilder.toString());
        }
        return res;
    }

    /*Since java doesnt have tuples to return (Integer, Integer)
    * User of shape should it parse on its own from string,
    * values separated by comma.
    * */
    public String shapeString(){
        return "(" + values.size() + ", 0)";
    }

    /*Returned as fixed int[2] array
    * where [0] is first dimension
    * and [1] is 2nd dimension, that can
    * be null if only 1st dimension.
    * */
    public int[] shape(){
        int[] res = new int[2];
        res[0] = values.size();
        return res;
    }

    /*
    * Saves as comma separated value format. Just give the file a name
    * and it will convert the object description into file.
    * */
    public void saveAsCsv(String fileName, String separator) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName + ".txt");
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        DataOutputStream file = new DataOutputStream(bos);

        var serieString = this.toString(separator);
        // writeBytes instead of writeUTF to avoid two spaces at start of file
        // doubt you could use utf symbols now
        file.writeBytes(serieString);
        file.flush();
        file.close();
    }

    /*
    * Overload method so you could optionally
    * pass the separator in
    * */
    public void saveAsCsv(String fileName) throws IOException {
        this.saveAsCsv(fileName, ",");
    }


    public String toString(String separator){
        var labels = index.toString(separator);

        StringBuilder stringBuilder = new StringBuilder();
        var valSize = values.size();

        for (int i = 0; i < valSize; i++) {
            stringBuilder.append(values.get(i).toString());
            if(i==valSize-1){
                break;
            }
            stringBuilder.append(separator);
        }
        stringBuilder.append(System.lineSeparator());

        return labels + stringBuilder.toString();
    }

    /*
    * Finds the maximal value of the serie, the T has to be comparable if its usermade.
    * */
    public static <T extends Comparable<? super T>> T maxValue(Collection<T> c) {
        return Collections.max(c);
    }

    /*
    * Finds the minimal value of the serie.
    * */
    public static <T extends Comparable<? super T>> T minValue(Collection<T> c) {
        return Collections.min(c);
    }

    /*Returns string of serie instance without the index labels.*/
    public String valuesString(String separator){
        StringBuilder stringBuilder = new StringBuilder();
        var valSize = values.size();

        for (int i = 0; i < valSize; i++) {
            stringBuilder.append(values.get(i).toString());
            if(i==valSize-1){
                break;
            }
            stringBuilder.append(separator);
        }
        stringBuilder.append(System.lineSeparator());
        return stringBuilder.toString();
    }

}
