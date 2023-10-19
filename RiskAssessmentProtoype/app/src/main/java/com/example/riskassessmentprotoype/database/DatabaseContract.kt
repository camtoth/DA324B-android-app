package com.example.riskassessmentprotoype.database

object DatabaseContract {
    const val SQL_CREATE_USERS = "CREATE TABLE IF NOT EXISTS Users (" +
            "user_id INTEGER PRIMARY KEY NOT NULL, " +
            "username VARCHAR(50) UNIQUE NOT NULL, " +
            "pw VARCHAR(256) NOT NULL, " +
            "given_names VARCHAR(150) NOT NULL, " +
            "last_name VARCHAR(100) NOT NULL, " +
            "is_admin BOOL NOT NULL);"

    const val SQL_CREATE_QUESTIONS = "CREATE TABLE IF NOT EXISTS Questions (" +
            "question_id INTEGER PRIMARY KEY NOT NULL, " +
            "text_en VARCHAR(1000) NOT NULL, " +
            "text_se VARCHAR(1000) NOT NULL, " +
            "r_neglect FLOAT(3), " +
            "r_pca FLOAT(3), " +
            "weight_yes_neglect FLOAT(3), " +
            "weight_middle_neglect FLOAT(3), " +
            "weight_no_neglect FLOAT(3), " +
            "weight_yes_pca FLOAT(3), " +
            "weight_middle_pca FLOAT(3), " +
            "weight_no_pca FLOAT(3));"

    const val SQL_CREATE_PARENTS = "CREATE TABLE IF NOT EXISTS Parents (" +
            "parent_id INTEGER PRIMARY KEY NOT NULL, " +
            "given_names VARCHAR(150) NOT NULL, " +
            "last_name VARCHAR(100) NOT NULL, " +
            "gender VARCHAR(10) NOT NULL);"

    const val SQL_CREATE_CASES = "CREATE TABLE IF NOT EXISTS Cases (" +
            "case_id INTEGER PRIMARY KEY NOT NULL, " +
            "personnr VARCHAR(13) NOT NULL UNIQUE, " +
            "email VARCHAR(100) NOT NULL, " +
            "gender VARCHAR(10) NOT NULL, " +
            "given_names VARCHAR(150) NOT NULL, " +
            "last_name VARCHAR(100) NOT NULL, " +
            "last_changed VARCHAR(10), " +
            "neglect_risk BOOL, " +
            "neglect_score FLOAT(3), " +
            "neglect_estimation FLOAT(3), " +
            "pca_risk BOOL, " +
            "pca_score FLOAT(3), " +
            "pca_estimation FLOAT(3), " +
            "user_id INTEGER NOT NULL, " +
            "FOREIGN KEY(user_id) REFERENCES Users(user_id));"

    const val SQL_CREATE_ANSWERS = "CREATE TABLE IF NOT EXISTS Answers (" +
            "answer_id INTEGER PRIMARY KEY NOT NULL, " +
            "opt_yes BOOL NOT NULL, " +
            "opt_middle BOOL NOT NULL, " +
            "opt_no BOOL NOT NULL, " +
            "parent_no INTEGER NOT NULL, " +
            "case_id INTEGER NOT NULL, " +
            "question_id INTEGER NOT NULL, " +
            "FOREIGN KEY(case_id) REFERENCES Cases(case_id), " +
            "FOREIGN KEY(question_id) REFERENCES Questions(question_id));"

    const val SQL_CREATE_PARENT_TO_CASE = "CREATE TABLE IF NOT EXISTS Parent_to_Case (" +
            "parent_to_case_id INTEGER PRIMARY KEY NOT NULL, " +
            "parent_id INTEGER NOT NULL, " +
            "case_id INTEGER NOT NULL, " +
            "FOREIGN KEY(case_id) REFERENCES Cases(case_id), " +
            "FOREIGN KEY(parent_id) REFERENCES Parents(parent_id));"

    const val SQL_SETUP_SCHEMA = SQL_CREATE_USERS + "\n\n" +
            SQL_CREATE_QUESTIONS + "\n\n" +
            SQL_CREATE_PARENTS  + "\n\n" +
            SQL_CREATE_CASES + "\n\n" +
            SQL_CREATE_ANSWERS  + "\n\n" +
            SQL_CREATE_PARENT_TO_CASE
}