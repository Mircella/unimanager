package com.example.yevgeniya.universitymanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Yevgeniya on 30.11.2017.
 */

public class MyTestClass {
    private static String[]offices = new String[]{"Dean office", "Ticket window", "Documentation acceptance", "International office"};
    private static String[]deanItems = new String[]{"Study schedule","Exams","Get ID Card","Other questions"};
    private static String[]ticketItems = new String[]{"ID Card fee","Course payment","Scolarships"};
    private static String[]documentItems = new String[]{"Apply for the study","Apply for the accomodation","Send documents"};
    private static String[]internationalItems = new String[]{"Erasmus Mundus","Student Trips","English courses","Ask a manager"};
    private static ArrayList<Map<String,String>> groupsList;
    private static ArrayList<Map<String,String>>childData;
    private static ArrayList<ArrayList<Map<String,String>>>childrenList;
    private static Map<String,String>attributeMap;
    public static void main(String[] args) {

        groupsList = new ArrayList<Map<String, String>>();
        for(String group:offices){
            attributeMap = new HashMap<String, String>();
            attributeMap.put("group",group);
            groupsList.add(attributeMap);
        }

        childData = new ArrayList<Map<String, String>>();
        childrenList = new ArrayList<ArrayList<Map<String,String>>>();
        for(String i:deanItems){
            attributeMap.put("item",i);
            childData.add(attributeMap);
        }
        childrenList.add(childData);

        childData = new ArrayList<Map<String, String>>();
        for(String i:ticketItems){
            attributeMap.put("item",i);
            childData.add(attributeMap);
        }
        childrenList.add(childData);

        childData = new ArrayList<Map<String, String>>();
        for(String i:documentItems){
            attributeMap.put("item",i);
            childData.add(attributeMap);
        }
        childrenList.add(childData);

        childData = new ArrayList<Map<String, String>>();
        for(String i:internationalItems){
            attributeMap.put("item",i);
            childData.add(attributeMap);
        }
        childrenList.add(childData);
        Iterator<HashMap.Entry<String,String>>iterator;
        System.out.println("Group values");
    for(Map<String,String>map:groupsList){
        iterator = map.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String,String>pair= iterator.next();
            String value = pair.getValue();
            System.out.println("value1 "+value);
        }

    }
    }


}
