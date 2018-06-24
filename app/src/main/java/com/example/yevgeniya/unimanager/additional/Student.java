package com.example.yevgeniya.unimanager.additional;

import android.text.TextUtils;

/**
 * Created by Yevgeniya on 06.12.2017.
 */

public class Student {
    private String id,office,issue;
    public Student(){}

    public Student(String id, String office, String issue) {
        this.id = id;
        this.office = office;
        this.issue = issue;
    }

    public String getId() {
        if (TextUtils.isEmpty(id)) {
        return "No id";
        }
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOffice() {
        if (TextUtils.isEmpty(office)) {
            return "No office";
        }
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getIssue() {
        if (TextUtils.isEmpty(issue)) {
            return "No issue";
        }
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }
}
