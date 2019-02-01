package com.mcqmaster.saif_win10.mcqmaster;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface Mcq_Q_Dao
{
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void addMcq_q (Mcq_Q_entity question);

    //@Query("SELECT * FROM mcq_question")
    //List<Mcq_Q_entity> getMcq_q ();

    @Query("SELECT ID, question, option_one, option_two, option_three, option_four, answer, explanation, total_number_of_question, level_of_cards, level_of_question FROM mcq_question WHERE ID = :id LIMIT 1")
    LiveData<List<Mcq_Q_entity>> select_row (String id);

    @Query("SELECT ID, question, option_one, option_two, option_three, option_four, answer, explanation, total_number_of_question, level_of_cards, level_of_question FROM mcq_question WHERE ID = :id")
    List<Mcq_Q_entity> find_quest_option (String id);
}
