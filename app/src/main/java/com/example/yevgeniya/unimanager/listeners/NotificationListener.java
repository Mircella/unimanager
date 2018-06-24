package com.example.yevgeniya.unimanager.listeners;

import android.content.Context;
import android.widget.Toast;

import com.example.yevgeniya.unimanager.additional.Student;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

/**
 * Created by Yevgeniya on 17.12.2017.
 */

public class NotificationListener implements ChildEventListener {
    private Student student;
    private String studentID;
    private Context context;

    public NotificationListener() {
    }

    public NotificationListener(Context context,String studentID) {
        this.studentID = studentID;
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        student = dataSnapshot.getValue(Student.class);
        if(student.getId()==studentID){
            Toast.makeText(context,"Your turn",Toast.LENGTH_SHORT).show();
        }
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
