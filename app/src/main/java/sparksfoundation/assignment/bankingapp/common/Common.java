package sparksfoundation.assignment.bankingapp.common;

import java.util.List;

import sparksfoundation.assignment.bankingapp.database.UserItem;

public class Common {
    public static final String APP_FIRST_LAUNCH ="APP_FIRST_LAUNCH ";
    public static final String TAG_ALL_CUSTOMER_FRAGMENT ="TAG_ALL_CUSTOMER_FRAGMENT ";
    public static final String TAG_SELECTED_CUSTOMER_FRAGMENT ="TAG_SELECTED_CUSTOMER_FRAGMENT ";


    public static List<UserItem>usersList;
    public  static UserItem selectedCustomer;
    public static UserItem userToTransferMoney;
}
