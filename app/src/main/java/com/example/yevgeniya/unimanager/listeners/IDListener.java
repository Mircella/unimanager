package com.example.yevgeniya.unimanager.listeners;

import com.example.yevgeniya.unimanager.additional.Student;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Yevgeniya on 17.12.2017.
 */

public class IDListener implements ChildEventListener {
    private Student student;
    private ArrayList<String>queries=new ArrayList<>();

    public IDListener() {
    }

    public IDListener(Student student, ArrayList<String> queries) {
        this.student = student;
        this.queries = queries;
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        student = dataSnapshot.getValue(Student.class);
        queries.add(student.getOffice()+"\n"+student.getIssue());

    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    public Student getStudent() {
        if(student!=null){
            return student;
        }
        return new Student();
    }

    public ArrayList<String> getQueries() {
        if(queries==null){
            return  (ArrayList<String>) Arrays.asList(new String[]{"no queries"});
        }
        return queries;

    }
}
