package edu.buffalo.cse.irf14;

import java.util.Comparator;
import java.util.Map;

/**
 * Code referenced from Stack Overflow.
 * @author Pravin
 *
 */
class ValueComparator implements Comparator<String> {

    Map<String, Double> base;
    public ValueComparator(Map<String, Double> base) {
        this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with equals.    
    public int compare(String a, String b) {
        if (base.get(a) > base.get(b)) {
            return -1;
        }
        else
        	if(base.get(a).equals(base.get(b)))
        {
        	return 0 ;
        }
        else {
            return 1;
        } // returning 0 would merge keys
    }
}