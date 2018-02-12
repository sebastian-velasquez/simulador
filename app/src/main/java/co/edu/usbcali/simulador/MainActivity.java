package co.edu.usbcali.simulador;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import co.edu.usbcali.simulador.database.user.User;
import co.edu.usbcali.simulador.products.ProductsFragment;
import co.edu.usbcali.simulador.profile.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    User loggedUser;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    ProductsFragment productsFragment;
    TransfersFragment transfersFragment;
    PaymentsFragment paymentsFragment;
    ProfileFragment profileFragment;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        this.loggedUser = intent.getParcelableExtra("loggedUser");
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        if (this.loggedUser != null) {
            fragmentManager = getSupportFragmentManager();
            transaction = fragmentManager.beginTransaction();
            productsFragment = ProductsFragment.Companion.newInstance(loggedUser);
            transaction.replace(R.id.fragmentContainer, productsFragment).commit();

            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    transaction = fragmentManager.beginTransaction();
                    switch (item.getItemId()) {
                        case R.id.products_menu_item:
                            productsFragment = ProductsFragment.Companion.newInstance(loggedUser);
                            transaction.replace(R.id.fragmentContainer, productsFragment).commit();
                            return true;
                        case R.id.transfers_menu_item:
                            transfersFragment = new TransfersFragment();
                            transaction.replace(R.id.fragmentContainer, transfersFragment).commit();
                            return true;
                        case R.id.payments_menu_item:
                            paymentsFragment = new PaymentsFragment();
                            transaction.replace(R.id.fragmentContainer, paymentsFragment).commit();
                            return true;
                        case R.id.profile_menu_item:
                            profileFragment = ProfileFragment.Companion.newInstance(loggedUser);
                            transaction.replace(R.id.fragmentContainer, profileFragment).commit();
                            return true;
                    }
                    return false;
                }
            });
        }
    }
}
