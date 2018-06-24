package com.example.yevgeniya.unimanager.listeners;

import com.example.yevgeniya.unimanager.additional.Student;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

/**
 * Created by Yevgeniya on 17.12.2017.
 */

public class StudentListener implements ChildEventListener {
    private Student student;

    public StudentListener() {
    }

    public StudentListener(Student student) {
        this.student = student;
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        student = dataSnapshot.getValue(Student.class);
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
}
