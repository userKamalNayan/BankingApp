package sparksfoundation.assignment.bankingapp.ui;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import sparksfoundation.assignment.bankingapp.MainActivity;
import sparksfoundation.assignment.bankingapp.database.Database;
import sparksfoundation.assignment.bankingapp.database.LocalUserDataSource;
import sparksfoundation.assignment.bankingapp.database.UserDataSource;
import sparksfoundation.assignment.bankingapp.database.UserItem;

public class AllCustomerViewModel extends ViewModel {

    MutableLiveData<List<UserItem>> usersList;

    UserDataSource userDataSource;
    Context context;
    CompositeDisposable compositeDisposable;


    public void initSource(Context context)
    {
        this.context = context;

    }


    public AllCustomerViewModel() {
        if (usersList == null)
        {
             usersList = new MutableLiveData<>();
            userDataSource = new LocalUserDataSource(Database.getInstance(context).userDAO());
            compositeDisposable = new CompositeDisposable();

        }
    }

    public MutableLiveData<List<UserItem>> getUsersList() {
        getAllUsersFromDatabase();
        return usersList;
    }

    private void getAllUsersFromDatabase() {
        userDataSource.getAllUsers().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<UserItem>>() {
                    @Override
                    public void accept(List<UserItem> userItems) throws Exception {
                        usersList.setValue(userItems);
                    }
                },
                        throwable -> {
                    System.out.println("All customer fetch error "+throwable.getMessage());
                            Toast.makeText(context,"Some Error occurred", Toast.LENGTH_LONG).show();
                        });
    }

    public void setUsersList(MutableLiveData<List<UserItem>> usersList) {
        this.usersList = usersList;
    }
}