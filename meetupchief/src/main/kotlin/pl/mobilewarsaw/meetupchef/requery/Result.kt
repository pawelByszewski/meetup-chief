package pl.mobilewarsaw.meetupchef.requery

import io.requery.query.Result


fun <T> Result<T>.isEmpty()
        = firstOrNull() == null

