package sparksfoundation.assignment.bankingapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface UserDAO {

    @Query("Select * FROM Users")
    Flowable<List<UserItem>> getAllUsers();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertUser(UserItem... userItem);

     @Update(onConflict = OnConflictStrategy.REPLACE)
    Completable updateUser(UserItem... userItem);

}
