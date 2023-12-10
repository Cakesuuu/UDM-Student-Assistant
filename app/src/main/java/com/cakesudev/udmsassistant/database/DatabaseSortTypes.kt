package com.cakesudev.udmsassistant.database

enum class DatabaseScheduleSortType {
    CHRONOLOGICAL,
    BY_SUBJECT_NAME,
    BY_STARTING_TIME
}

enum class DatabaseDeadlineSortType {
    CHRONOLOGICAL,
    BY_ACTIVITY_NAME,
    BY_STARTING_TIME,
    BY_STARTING_DATE
}

enum class DatabaseNoteSortType {
    CHRONOLOGICAL,
    BY_TITLE
}

enum class DatabaseAdminSortType {
    CHRONOLOGICAL,
    BY_TITLE,
    BY_ADMIN
}