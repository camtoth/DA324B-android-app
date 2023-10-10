package com.example.riskassesmentapp.db

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
            "title_en VARCHAR(50) NOT NULL, " +
            "text_en VARCHAR(1000) NOT NULL, " +
            "title_se VARCHAR(50) NOT NULL, " +
            "text_se VARCHAR(1000) NOT NULL, " +
            "r_neglect FLOAT(3), " +
            "r_pca FLOAT(3), " +
            "weight_yes_neglect FLOAT(3), " +
            "weight_middle_neglect FLOAT(3), " +
            "weight_no_neglect FLOAT(3), " +
            "weight_yes_pca FLOAT(3), " +
            "weight_middle_pca FLOAT(3), " +
            "weight_no_pca FLOAT(3));"

    const val SQL_CREATE_CASES = "CREATE TABLE IF NOT EXISTS Cases (" +
            "case_id INTEGER PRIMARY KEY NOT NULL, " +
            "personnr VARCHAR(13) NOT NULL UNIQUE, " +
            "case_nr VARCHAR(15) NOT NULL UNIQUE, " +
            "email VARCHAR(100) NOT NULL, " +
            "gender VARCHAR(1) NOT NULL, " +
            "given_names VARCHAR(150) NOT NULL, " +
            "last_name VARCHAR(100) NOT NULL, " +
            "high_risk BOOL, " +
            "user_id INTEGER NOT NULL, " +
            "FOREIGN KEY(user_id) REFERENCES Users(user_id));"

    const val SQL_CREATE_PARENTS = "CREATE TABLE IF NOT EXISTS Parents (" +
            "parent_id INTEGER PRIMARY KEY NOT NULL, " +
            "personnr VARCHAR(13) NOT NULL UNIQUE, " +
            "given_names VARCHAR(150) NOT NULL, " +
            "last_name VARCHAR(100) NOT NULL, " +
            "gender VARCHAR(1) NOT NULL, " +
            "high_risk_pca BOOL, " +
            "high_risk_neglect BOOL, " +
            "est_high_risk_pca BOOL, " +
            "est_high_risk_neglect BOOL, " +
            "last_changed VARCHAR(10), " +
            "case_id INTEGER NOT NULL, " +
            "FOREIGN KEY(case_id) REFERENCES Cases(case_id));"

    const val SQL_CREATE_ANSWERS = "CREATE TABLE IF NOT EXISTS Answers (" +
            "answer_id INTEGER PRIMARY KEY NOT NULL, " +
            "opt_yes BOOL NOT NULL, " +
            "opt_middle BOOL NOT NULL, " +
            "opt_no BOOL NOT NULL, " +
            "parent_id INTEGER NOT NULL, " +
            "question_id INTEGER NOT NULL, " +
            "FOREIGN KEY(parent_id) REFERENCES Parents(parent_id), " +
            "FOREIGN KEY(question_id) REFERENCES Questions(question_id));"
}