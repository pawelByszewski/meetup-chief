package pl.mobilewarsaw.meetupchef.database


interface Table {

    val TABLE_NAME: String
    val CREATE_STATEMENT: String

}