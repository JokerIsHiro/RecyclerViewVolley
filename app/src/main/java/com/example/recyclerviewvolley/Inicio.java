package com.example.recyclerviewvolley;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class Inicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);

        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navView = findViewById(R.id.navView);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navHostFragment);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(navView, navController);

        final TextView textTitle = findViewById(R.id.textTitle);


        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            Fragment fragment = null;
            Class fragmentClass;
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.menuInicio:
                        fragmentClass = ListadoFragment.class;
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.menuMedidor:
                        fragmentClass = MedidorFragment.class;
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.menuConversor:
                        fragmentClass = ConversorFragment.class;
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.menuConfiguración:
                        fragmentClass = ConfiguracionFragment.class;
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    default:
                        fragmentClass = ListadoFragment.class;
                }
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(item.getItemId() == R.id.menuMapa){
                    Intent intent = new Intent(Inicio.this, MapsActivity.class);
                    startActivity(intent);
                    drawerLayout.closeDrawer(GravityCompat.START);
                }else{
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.navHostFragment, fragment).commit();
                }

                textTitle.setText(item.getTitle());

                return true;
            }
        });

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                textTitle.setText(navDestination.getLabel());
            }
        });
    }
}