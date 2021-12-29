package com.company;

import java.util.ArrayList;

/*Class reprezentation of index object.*/
public class Index {
    private ArrayList<String> labels = new ArrayList<>();
    private String name;

    /*
    * Index class instance init method.
    * Args:
    *   labels: Arraylist<String> of indexes
    * */
    public Index(ArrayList<String> labels, String name) throws ValueError {

        // Labels must be positive length
        if(labels.size() <= 0){
            throw new ValueError(new Exception(), "Labels must contain at least one item.");
        }

        // Labels must not contain duplicates
        var set = labels.stream().distinct();
        var original = labels.stream();
        if(set.count() != original.count()){
            throw new ValueError(new Exception(), "Labels contains duplicates.");
        }

        this.labels = labels;
        this.name = name;
    }

    /*
    * Index name defaults to ""
    * */
    public Index(ArrayList<String> labels) throws ValueError {
        this(labels, "");
    }

    /*Get name of Index*/
    public String getName() {
        return name;
    }

    /*Get labels of Index*/
    public ArrayList<String> getLabels() {
        return labels;
    }

    /*Return the size of Index*/
    public int len(){
        return labels.size();
    }

    /*
    * Returns index where key is in the index.
    * May raise ValueError if key is not present in index.
    * Args:
    *   Key: Key we search for.
    * Returns:
    *   index: Number where the key was in index.
    * */
    public int getLoc(String key) throws KeyError {
        int position =  this.labels.indexOf(key);
        if(position >= 0){
            return position;
        }
        else {
            throw new KeyError(new Exception(), "Index doesnt exist, since " + key + " is not in labels.");
        }
    }

    public String toString(String separator) {
        StringBuilder stringBuilder = new StringBuilder();
        var labels = getLabels();
        var sizeLabels = labels.size();
        for (int i = 0; i < sizeLabels; i++) {
            stringBuilder.append(labels.get(i));
            if(i==sizeLabels-1){
                break;
            }
            stringBuilder.append(separator);
        }
        stringBuilder.append(System.lineSeparator());
        return stringBuilder.toString();
    }
}
