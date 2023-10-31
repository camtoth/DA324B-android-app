package com.example.riskassesmentapp.db

import android.database.sqlite.SQLiteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun deleteUser(db: SQLiteDatabase, userId: Long): Int {
    // Avoid deletion of admin account
    if (userId == 1L) return 0
    val cases = getCasesByUser(db, userId)
    for (case in cases) {
        deleteCaseById(db, case.id)
    }
    return withContext(Dispatchers.IO) {
        db.delete("Users", "user_id LIKE ?", arrayOf(userId.toString()))
    }
}

suspend fun deleteAnswersByParent(db: SQLiteDatabase, parentId: Long): Int {
    return withContext(Dispatchers.IO) {
        db.delete("Answers", "parent_id LIKE ?", arrayOf(parentId.toString()))
    }
}

suspend fun deleteParentById(db: SQLiteDatabase, parentId: Long): Int {
    deleteAnswersByParent(db, parentId)
    return withContext(Dispatchers.IO) {
        db.delete("Parents", "parent_id LIKE ?", arrayOf(parentId.toString()))
    }
}

suspend fun deleteParentsByCase(db: SQLiteDatabase, caseId: Long): Int {
    val parents = getParentsByCase(db, caseId)
    var deleted = 0
    for (parent in parents) {
        deleted += deleteParentById(db, parent.id)
    }
    return deleted
}

suspend fun deleteCaseById(db: SQLiteDatabase, caseId: Long): Int {
    deleteParentsByCase(db, caseId)
    return db.delete("Cases", "case_id LIKE ?", arrayOf(caseId.toString()))
}
