package sparksfoundation.assignment.bankingapp.ui.selectedcustomer;

import android.content.Context;
import android.graphics.fonts.Font;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import sparksfoundation.assignment.bankingapp.common.Common;
import sparksfoundation.assignment.bankingapp.database.Database;
import sparksfoundation.assignment.bankingapp.database.LocalUserDataSource;
import sparksfoundation.assignment.bankingapp.database.TransactionItem;
import sparksfoundation.assignment.bankingapp.database.UserDataSource;
import sparksfoundation.assignment.bankingapp.database.UserItem;
import sparksfoundation.assignment.bankingapp.database.transaction.LocalTransactionDataSource;
import sparksfoundation.assignment.bankingapp.database.transaction.TransactionDataSource;

public class SelectedCustomerViewModel extends ViewModel {
    MutableLiveData<UserItem> user;
    MutableLiveData<List<TransactionItem>> transactionsList;

    Context context;
    TransactionDataSource transactionDataSource;
    CompositeDisposable compositeDisposable;
    UserDataSource userDataSource;
    int userId;
    public MutableLiveData<UserItem> getUser() {

        compositeDisposable.add(userDataSource.getAllUsers().subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<List<UserItem>>() {
            @Override
            public void accept(List<UserItem> userItems) throws Exception {
                for (UserItem tempUser :  userItems)
                {
                    if ((tempUser.getUserId() == Common.selectedCustomer.getUserId()))
                    {
                        user.setValue(tempUser);
                    }
                }
            }
        }));
        return user;
    }




    public void initDataSource(Context context,int userId) {
        this.context = context;
        this.userId = userId;
    }

    public void setUser(MutableLiveData<UserItem> user) {
        this.user = user;
    }

    public SelectedCustomerViewModel() {
        if (user == null) {
            user = new MutableLiveData<>();
            if (Common.selectedCustomer != null) {
                transactionsList = new MutableLiveData<>();
                userDataSource = new LocalUserDataSource(Database.getInstance(context).userDAO());
                transactionDataSource = new LocalTransactionDataSource(Database.getInstance(context).transactionDAO());
                compositeDisposable = new CompositeDisposable();
            }
        }
    }

    public MutableLiveData<List<TransactionItem>> getTransactionsList() {
        compositeDisposable.add(transactionDataSource.getUserTransactions(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<TransactionItem>>() {
                    @Override
                    public void accept(List<TransactionItem> transactionItems) throws Exception {
                        transactionsList.setValue(transactionItems);
                        for (TransactionItem transactionItem: transactionItems)
                        {
                            System.out.println("in model id = "+transactionItem.getUserId()+
                                    " sender name = "+transactionItem.getSenderName()+"\n");
                        }
                    }
                }));
        return transactionsList;
    }

    public void setTransactionsList(MutableLiveData<List<TransactionItem>> transactionsList) {
        this.transactionsList = transactionsList;
    }
}