package com.company;

import java.util.ArrayList;
import java.util.function.BiFunction;
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

    /*
    * WIP
    * */
    private Series<T> applyOperator(Series<T> other, BiFunction<T, T, T> operator) throws ValueError {
        if(this.len() != other.len()){
            throw new ValueError(new Exception(), "Length must be compatible.");
        }

        ArrayList<T> newValues = new ArrayList<>();
        var otherValues = other.getValues();

        for (int i = 0; i < values.size(); i++) {
            var newValue = operator.apply(values.get(i), otherValues.get(i));
            newValues.add(newValue);
        }

        return new Series<>(newValues, index);
    }

    /*Helper method for finding the key in index*/
    private T getItem(String key) throws KeyError {
        try {
            return values.get(index.getLoc(key));
        } catch (ValueError e) {
            throw new KeyError(new Exception());
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

    public void saveAsCsv(String fileName, String separator){

    }

    public void saveAsCsv(String fileName){
        this.saveAsCsv(fileName, ",");
    }
}
