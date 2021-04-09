package sparksfoundation.assignment.bankingapp.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import sparksfoundation.assignment.bankingapp.database.transaction.TransactionDAO;

@androidx.room.Database(entities = {TransactionItem.class, UserItem.class},
        exportSchema = false, version = 2)
public abstract class Database extends RoomDatabase {

    public abstract UserDAO userDAO();
    public abstract TransactionDAO transactionDAO();


    private static Database instance;

    public static Database getInstance(Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(context,
                    Database.class, "BankDatabase")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
