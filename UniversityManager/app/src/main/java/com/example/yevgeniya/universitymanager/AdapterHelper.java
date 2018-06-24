package com.example.yevgeniya.universitymanager;

import android.content.Context;
import android.widget.SimpleExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yevgeniya on 30.11.2017.
 */

public class AdapterHelper {
    private String[]offices = new String[]{"Dean office", "Ticket window", "Documentation acceptance", "International office"};
    private String[]deanItems = new String[]{"Study schedule","Exams","Get ID Card","Other questions"};
    private String[]ticketItems = new String[]{"ID Card fee","Course payment","Scolarships"};
    private String[]documentItems = new String[]{"Apply for the study","Apply for the accomodation","Send documents"};
    private String[]internationalItems = new String[]{"Erasmus Mundus","Student Trips","English courses","Ask a manager"};
    private ArrayList<Map<String,String>> groupsList;
    private ArrayList<Map<String,String>>childData;
    private ArrayList<ArrayList<Map<String,String>>>childrenList;
    private Map<String,String>attributeMap;
    private Context context;
    private SimpleExpandableListAdapter adapter;

    public AdapterHelper(Context context){
        this.context = context;
    }

    public SimpleExpandableListAdapter getAdapter(){
        for(String group:offices){
            attributeMap = new HashMap<String, String>();
            attributeMap.put("office",group);
            groupsList.add(attributeMap);
        }
        String []readGroupFrom = new String[]{"office"};
        int[]putGroupTo = new int[]{android.R.id.text1};
        childData = new ArrayList<Map<String, String>>();
        childrenList = new ArrayList<ArrayList<Map<String,String>>>();
        for(String i:deanItems){
            attributeMap = new HashMap<String, String>();
            attributeMap.put("item",i);
            childData.add(attributeMap);
        }
        childrenList.add(childData);

        childData = new ArrayList<Map<String, String>>();
        for(String i:ticketItems){
            attributeMap = new HashMap<String, String>();
            attributeMap.put("item",i);
            childData.add(attributeMap);
        }
        childrenList.add(childData);

        childData = new ArrayList<Map<String, String>>();
        for(String i:documentItems){
            attributeMap = new HashMap<String, String>();
            attributeMap.put("item",i);
            childData.add(attributeMap);
        }
        childrenList.add(childData);

        childData = new ArrayList<Map<String, String>>();
        for(String i:internationalItems){
            attributeMap = new HashMap<String, String>();
            attributeMap.put("item",i);
            childData.add(attributeMap);
        }
        childrenList.add(childData);

        String[]readChildFrom = new String[]{"item"};
        int[]putChildTo = new int[]{android.R.id.text1};

        adapter = new SimpleExpandableListAdapter(context,
                groupsList,
                android.R.layout.simple_expandable_list_item_1,
                readGroupFrom,
                putGroupTo,
                childrenList,
                android.R.layout.simple_list_item_1,
                readChildFrom,
                putChildTo);

        return adapter;
    }

    public String getOffice(int officePos){
        return ((Map<String,String>)(adapter.getGroup(officePos))).get("office");
    }

    public String getItem(int groupPos,int itemPos){
        return ((Map<String,String>)adapter.getChild(groupPos,itemPos)).get("item");
    }

    public String getOfficeAndItem(int groupPos, int itemPos){
        return getOffice(groupPos)+" "+getItem(groupPos, itemPos);
    }
}
