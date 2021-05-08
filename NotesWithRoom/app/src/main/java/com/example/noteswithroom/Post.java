package com.example.noteswithroom;


import androidx.room.Entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "posts_table")
class Post {
    @PrimaryKey(autoGenerate = true)
    private int McCode;
    private String McName;

    public Post(int mcCode, String mcName) {
        McCode = mcCode;
        McName = mcName;
    }

    public int getMcCode() {
        return McCode;
    }

    public void setMcCode(int mcCode) {
        McCode = mcCode;
    }

    public String getMcName() {
        return McName;
    }

    public void setMcName(String mcName) {
        McName = mcName;
    }
}
