package sparksfoundation.assignment.bankingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import sparksfoundation.assignment.bankingapp.common.Common;
import sparksfoundation.assignment.bankingapp.database.Database;
import sparksfoundation.assignment.bankingapp.database.LocalUserDataSource;
import sparksfoundation.assignment.bankingapp.database.UserDAO;
import sparksfoundation.assignment.bankingapp.database.UserDataSource;
import sparksfoundation.assignment.bankingapp.database.UserItem;
import sparksfoundation.assignment.bankingapp.events.OpenFragmentEvent;
import sparksfoundation.assignment.bankingapp.ui.AllCustomerFragment;

import static sparksfoundation.assignment.bankingapp.common.Common.APP_FIRST_LAUNCH;

public class MainActivity extends AppCompatActivity {

    CompositeDisposable compositeDisposable;

    Database database;
    UserDataSource userDataSource;
    SharedPreferences sharedPreferences;

    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = Database.getInstance(MainActivity.this);
        compositeDisposable = new CompositeDisposable();

        userDataSource = new LocalUserDataSource(Database.getInstance(MainActivity.this).userDAO());

        loadFragment(AllCustomerFragment.newInstance(),Common.TAG_ALL_CUSTOMER_FRAGMENT);

        sharedPreferences = getSharedPreferences("BankAppSharedPreferences",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (sharedPreferences.getBoolean(APP_FIRST_LAUNCH,true))
        {
            editor.putBoolean(APP_FIRST_LAUNCH,false);
            editor.apply();
            addUser();// we will only add users if the app is started for the first time;
        }
    }

    private void addUser() {
        System.out.println("In add User");
        List<UserItem> users = new ArrayList<>();

        UserItem userItem = new UserItem();
        userItem.setName("Kamal Nayan");
        userItem.setCurrentBalance(100000000000.0);
        userItem.setEmail("Kamalnayan.email@gmail.com");
        users.add(userItem);

         userItem = new UserItem();
        userItem.setName("Shubhangam Garg");
        userItem.setCurrentBalance(100000.0);
        userItem.setEmail("Shubhangam.email@gmail.com");
        users.add(userItem);

         userItem = new UserItem();
        userItem.setName("Abhishek Kalia");
        userItem.setCurrentBalance(100000.0);
        userItem.setEmail("Ridham.email@gmail.com");
        users.add(userItem);

         userItem = new UserItem();
        userItem.setCurrentBalance(100000.0);
        userItem.setName("Ridham Goyal");
        userItem.setEmail("Ridham.email@gmail.com");
        users.add(userItem);

         userItem = new UserItem();
        userItem.setName("Shubham Chaudhary");
        userItem.setCurrentBalance(100000.0);
        userItem.setEmail("Shubham.email@gmail.com");
        users.add(userItem);


        userItem = new UserItem();
        userItem.setName("Shivam");
        userItem.setCurrentBalance(100000.0);
        userItem.setEmail("Shivam.email@gmail.com");
        users.add(userItem);

         userItem = new UserItem();
        userItem.setName("Mehul Mehta");
        userItem.setCurrentBalance(100000.0);
        userItem.setEmail("Mehul.email@gmail.com");
        users.add(userItem);

         userItem = new UserItem();
        userItem.setName("Nikhil Chaudhary");
        userItem.setCurrentBalance(100000.0);
        userItem.setEmail("Nikhil.email@gmail.com");
        users.add(userItem);

         userItem = new UserItem();
        userItem.setName("Alok Kumar");
        userItem.setCurrentBalance(100000.0);
        userItem.setEmail("Alok.email@gmail.com");
        users.add(userItem);

         userItem = new UserItem();
        userItem.setName("Amitoj Singh Ahuja");
        userItem.setCurrentBalance(100000.0);
        userItem.setEmail("Amitoj.email@gmail.com");
        users.add(userItem);



       for (UserItem user : users)
       {
           compositeDisposable.add(userDataSource.insertUser(user)
                   .subscribeOn(Schedulers.io())
                   .observeOn(AndroidSchedulers.mainThread())
                   .subscribe(()-> {
                       Toast.makeText(MainActivity.this, "User Added", Toast.LENGTH_SHORT).show();

                   },throwable -> {
                       System.out.println("Error in inserting user");
                   }));
       }
    }




    private void loadFragment(Fragment fragment,String tag) {
        System.out.println("tag = "+fragment.getTag());
        getSupportFragmentManager().beginTransaction().
                replace(R.id.frameLayout, fragment,tag)
                .addToBackStack(tag)
                .commit();



    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void navigator(OpenFragmentEvent event)
    {
        if (event.isOpenFragment())
        {
            loadFragment(event.getFragment(),event.getTag());
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this))
        {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (EventBus.getDefault().isRegistered(this))
        {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1)
        {
            finishAffinity();
        }
        else
             super.onBackPressed();

    }
}