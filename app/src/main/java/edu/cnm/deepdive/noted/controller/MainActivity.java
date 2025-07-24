package edu.cnm.deepdive.noted.controller;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import dagger.hilt.android.AndroidEntryPoint;
import edu.cnm.deepdive.noted.R;
import edu.cnm.deepdive.noted.databinding.ActivityMainBinding;
import edu.cnm.deepdive.noted.viewmodel.LoginViewModel;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

  private static final String TAG = MainActivity.class.getSimpleName();
  private ActivityMainBinding binding;
  private AppBarConfiguration appBarConfig;
  private NavController navController;
  private LoginViewModel loginViewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setupUI();
    setupNavigation();
  }

  @Override
  public boolean onSupportNavigateUp() {
    return NavigationUI.navigateUp(navController, appBarConfig);
  }

  private void setupUI() {
    binding = ActivityMainBinding.inflate(getLayoutInflater());
    binding.navDrawer
            .getMenu()
                .findItem(R.id.sign_out)
                    .setOnMenuItemClickListener((item) -> {
                      loginViewModel.signOut();
                      return true;
                    });
    setContentView(binding.getRoot());
  }

  private void setupNavigation() {
    appBarConfig = new AppBarConfiguration.Builder(
        R.id.list_fragment, R.id.pre_login_fragment, R.id.login_fragment)
        .build();
    NavHostFragment host = binding.appBarMain.navHostFragment.getFragment();
    navController = host.getNavController();
    NavigationUI.setupActionBarWithNavController(this, navController, appBarConfig);
  }
}