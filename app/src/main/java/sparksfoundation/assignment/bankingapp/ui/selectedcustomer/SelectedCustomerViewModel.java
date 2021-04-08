package sparksfoundation.assignment.bankingapp.ui.selectedcustomer;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import sparksfoundation.assignment.bankingapp.common.Common;
import sparksfoundation.assignment.bankingapp.database.UserItem;

public class SelectedCustomerViewModel extends ViewModel {
   MutableLiveData<UserItem> user ;

    public MutableLiveData<UserItem> getUser() {
        return user;
    }

    public void setUser(MutableLiveData<UserItem> user) {
        this.user = user;
    }

    public SelectedCustomerViewModel() {
        if (user == null)
        {
            user = new MutableLiveData<>();
            if (Common.selectedCustomer != null)
            {
                user.setValue(Common.selectedCustomer);
            }
        }
    }
}