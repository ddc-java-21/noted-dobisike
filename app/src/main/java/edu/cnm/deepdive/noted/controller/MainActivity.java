package edu.cnm.deepdive.noted.controller;

import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.ActivityNavigator.Extras;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavGraphBuilder;
import androidx.navigation.NavGraphNavigator;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.navigation.NavigationView;
import dagger.hilt.android.AndroidEntryPoint;
import edu.cnm.deepdive.noted.R;
import edu.cnm.deepdive.noted.controller.login.PreLoginFragmentDirections;
import edu.cnm.deepdive.noted.databinding.ActivityMainBinding;
import edu.cnm.deepdive.noted.model.entity.Note;
import edu.cnm.deepdive.noted.model.pojo.NoteWithImages;
import edu.cnm.deepdive.noted.viewmodel.LoginViewModel;
import edu.cnm.deepdive.noted.viewmodel.NoteViewModel;
import java.util.List;
import javax.annotation.Nullable;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

  private static final String TAG = MainActivity.class.getSimpleName();
  private ActivityMainBinding binding;
  private AppBarConfiguration appBarConfig;
  private NavController navController;
  private LoginViewModel loginViewModel;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setupUI();
    setupNavigation();
    setupViewModel();
  }

  @Override
  public boolean onSupportNavigateUp() {
    return NavigationUI.navigateUp(navController, appBarConfig) || super.onSupportNavigateUp();
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
//    binding.navDrawer.getMenu().findItem(R.id.reminder_fragment)
//            .setOnMenuItemClickListener((item) -> )
    setContentView(binding.getRoot());
  }

  private void setupNavigation() {
    setSupportActionBar(binding.appBarMain.toolbar);
    NavigationView navigationView = binding.navDrawer;
    appBarConfig = new AppBarConfiguration.Builder(R.id.pre_login_fragment, R.id.list_fragment)
        .setOpenableLayout(binding.getRoot())
        .build();
    NavHostFragment host = binding.appBarMain.navHostFragment.getFragment();
    navController = host.getNavController();
    NavigationUI.setupActionBarWithNavController(this, navController, appBarConfig);
    NavigationUI.setupWithNavController(navigationView, navController);
  }

  private void setupViewModel() {
    ViewModelProvider provider = new ViewModelProvider(this);
    loginViewModel = provider.get(LoginViewModel.class);
    getLifecycle().addObserver(loginViewModel);
    loginViewModel.getAccount().observe(this, this::handleAccount);
    NoteViewModel noteViewModel = provider.get(NoteViewModel.class);
    getLifecycle().addObserver(noteViewModel);
    noteViewModel
        .getCurrentUser()
            .observe(this, (user) -> {

            });
    noteViewModel
        .getNotes()
        .observe(this, this::handleNotes);
  }

  private void handleNotes(List<NoteWithImages> notes) {
    Menu menu = binding.navDrawer.getMenu();
    menu.removeGroup(R.id.notes);
    notes.forEach((note) -> menu
        .add(R.id.notes, Menu.NONE, 1, note.getTitle())
        .setCheckable(true)
        .setOnMenuItemClickListener((item) -> selectNote(note, item))
    );
  }

  private boolean selectNote(Note note, MenuItem item) {
    item.setChecked(true);
    navController.navigate(PreLoginFragmentDirections.showList());
    binding.getRoot().closeDrawer(GravityCompat.START);
    return true;
  }

  /** @noinspection deprecation*/
  private void handleAccount(GoogleSignInAccount account) {
    if (account == null) {
      Extras extras = new Extras.Builder()
          .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
          .build();
      navController.navigate(PreLoginFragmentDirections.showLogin(), extras);
    }

  }

}