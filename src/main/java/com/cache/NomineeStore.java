package com.cache;

import com.model.Nominee;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by yangliu on 20/12/2016.
 */
public class NomineeStore {
    private static Map<String, Nominee> nomineeMap = new LinkedHashMap<>();

    public static void createNomineeMap(Map<String, Nominee> newNomineeMap) {
        if(nomineeMap.size() == 0) {
            nomineeMap.putAll(newNomineeMap);
        }
    }

    public static Collection<Nominee> getAllNominees() {
        return nomineeMap.values();
    }

    public static Nominee getNominee(String key) {
        return nomineeMap.get(key);
    }

    public static boolean addNominee(String key, Nominee value) {
        if(!nomineeMap.containsKey(key)) {
            nomineeMap.put(key, value);
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean updateNominee(String key, Nominee value) {
        if(nomineeMap.containsKey(key)) {
            nomineeMap.put(key, value);
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean deleteNominee(String key) {
        if(nomineeMap.containsKey(key)) {
            nomineeMap.remove(key);
            return true;
        }
        return false;
    }
}
