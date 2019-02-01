package com.mcqmaster.saif_win10.mcqmaster;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.firebase.database.annotations.NotNull;


@Entity (tableName = "mcq_question")
public class Mcq_Q_entity
{
    @PrimaryKey
    @NonNull
    @ColumnInfo (name = "ID")
    private String id;

    @ColumnInfo (name = "question")
    private String q;

    @ColumnInfo (name = "option_one")
    private String o1;

    @ColumnInfo (name = "option_two")
    private String o2;

    @ColumnInfo (name = "option_three")
    private String o3;

    @ColumnInfo (name = "option_four")
    private String o4;

    @ColumnInfo (name = "answer")
    private String ans;

    @ColumnInfo (name = "explanation")
    private String e;

    // Extra information
    @ColumnInfo (name = "total_number_of_question")
    private int total_N_Q;

    //Track the record by level of cards
    @ColumnInfo (name = "level_of_cards")
    private int level_cards;


    //Track the record by level of each question
    @ColumnInfo (name = "level_of_question")
    private int level_question;


    public int getTotal_N_Q() {
        return total_N_Q;
    }

    public void setTotal_N_Q(int total_N_Q) {
        this.total_N_Q = total_N_Q;
    }

    public int getLevel_cards() {
        return level_cards;
    }

    public void setLevel_cards(int level_cards) {
        this.level_cards = level_cards;
    }

    public int getLevel_question() {
        return level_question;
    }

    public void setLevel_question(int level_question) {
        this.level_question = level_question;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getO1() {
        return o1;
    }

    public void setO1(String o1) {
        this.o1 = o1;
    }

    public String getO2() {
        return o2;
    }

    public void setO2(String o2) {
        this.o2 = o2;
    }

    public String getO3() {
        return o3;
    }

    public void setO3(String o3) {
        this.o3 = o3;
    }

    public String getO4() {
        return o4;
    }

    public void setO4(String o4) {
        this.o4 = o4;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }
}
