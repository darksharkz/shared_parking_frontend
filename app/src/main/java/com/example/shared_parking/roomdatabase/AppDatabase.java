package com.example.shared_parking.roomdatabase;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

@Database(entities = {User.class, ParkingSpace.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;
    public abstract UserDao userDao();
    public abstract ParkingSpaceDao parkingSpaceDao();

    public synchronized static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = buildDatabase(context);
        }
        return INSTANCE;
    }

    private static AppDatabase buildDatabase(final Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "database-name").allowMainThreadQueries().addCallback(rdc).build();
    }

    static RoomDatabase.Callback rdc = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            db.execSQL("INSERT INTO user (uid, first_name, last_name) VALUES (1, 'Simon', 'Englert')");
            db.execSQL("INSERT INTO user (uid, first_name, last_name) VALUES (2, 'Max', 'Mustermann')");
            db.execSQL("INSERT INTO user (uid, first_name, last_name) VALUES (3, 'Mr', 'X')");
            db.execSQL("INSERT INTO parkingspace (id, post_code, city, street, number, lat, lng, user_id) VALUES (1, 97070, 'Würzburg', 'Hackstetterstraße', 3, 49.782150, 9.961362, 1)");
            db.execSQL("INSERT INTO parkingspace (id, post_code, city, street, number, lat, lng, user_id) VALUES (2, 97070, 'Würzburg', 'Hackstetterstraße', 11, 49.782801, 9.961384, 2)");
            db.execSQL("INSERT INTO parkingspace (id, post_code, city, street, number, lat, lng, user_id) VALUES (3, 97070, 'Würzburg', 'Am Hubland', 16, 49.780116, 9.966458, 3)");
            db.execSQL("INSERT INTO parkingspace (id, post_code, city, street, number, lat, lng, user_id) VALUES (4, 97070, 'Würzburg', 'Am Hubland', 16, 49.780134, 9.965745, 3)");
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };
}

