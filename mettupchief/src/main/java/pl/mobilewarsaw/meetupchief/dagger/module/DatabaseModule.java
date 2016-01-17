package pl.mobilewarsaw.meetupchief.dagger.module;


import android.content.Context;
import android.provider.ContactsContract;

import dagger.Module;
import dagger.Provides;
import pl.mobilewarsaw.meetupchief.database.Database;

@Module
public class DatabaseModule {

    private Context context;

    public DatabaseModule(Context context) {
        this.context = context;
    }

    @Provides
    Database provideDataBase() {
        return Database.getInstance(context);
    }
}
