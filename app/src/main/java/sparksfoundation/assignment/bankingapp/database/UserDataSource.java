package sparksfoundation.assignment.bankingapp.database;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public interface UserDataSource {

     Flowable<List<UserItem>> getAllUsers();

     Completable insertUser(UserItem... userItem);

    Completable updateUser(UserItem... userItem);

}
