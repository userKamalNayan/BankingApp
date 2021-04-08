package sparksfoundation.assignment.bankingapp.database;

import java.util.List;
import io.reactivex.Completable;
import io.reactivex.Flowable;

public class LocalUserDataSource implements UserDataSource {
UserDAO userDAO;

public LocalUserDataSource (UserDAO userDAO)
{
    this.userDAO = userDAO;
}

    @Override
    public Flowable<List<UserItem>> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public Completable insertUser(UserItem... userItem) {
        return userDAO.insertUser(userItem);
    }

    @Override
    public Completable updateUser(UserItem... userItem) {
        return userDAO.updateUser(userItem);
    }
}
