package pl.mobilewarsaw.meetupchief.dagger.module;


import android.content.Context;

import pl.mobilewarsaw.meetupchief.database.Database;

public class DatabaseModule {

    private Context context;

    public DatabaseModule(Context context) {
        this.context = context;
    }

    Database provideDataBase() {
        return Database.getInstance(context);
    }
}
