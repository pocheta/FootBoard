package fr.michot.projet_android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import fr.michot.projet_android.view.CountryFragment;
import fr.michot.projet_android.view.FavoritesFragment;
import fr.michot.projet_android.view.SearchPlayerFragment;

public class MainActivity extends AppCompatActivity {

    private final static String FRAGMENT_STORED_KEY = "Fragment_Stored";

    public static Fragment currentFragment;
    public static SparseArray<Fragment> fragmentArray;
    public static BottomNavigationView bottomNavigationView;
    public static String currentFlag = "17";
    public static String newFlag = "17";

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if(currentFragment != null) {
            getSupportFragmentManager().putFragment(outState, FRAGMENT_STORED_KEY, currentFragment);
        }

        super.onSaveInstanceState(outState);
    }

    @SuppressLint("NonConstantResourceId")
    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentArray = new SparseArray<>(3);
        bottomNavigationView = findViewById(R.id.bottomNavBar);
        if (savedInstanceState != null) {
            currentFragment = getSupportFragmentManager().getFragment(savedInstanceState, FRAGMENT_STORED_KEY);
        } else {
            currentFragment = new SearchPlayerFragment();
        }
        replaceFragment(currentFragment);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch(item.getItemId()) {
                case R.id.players:
                    if(fragmentArray.get(0) == null || !currentFlag.equals(newFlag)) {
                        currentFragment = SearchPlayerFragment.newInstance();
                        fragmentArray.append(0, currentFragment);
                    } else {
                        currentFragment = fragmentArray.get(0);
                    }
                    bottomNavigationView.getMenu().findItem(R.id.trashFavorites).setVisible(false);
                    bottomNavigationView.getMenu().findItem(R.id.flag).setVisible(true);
                    break;

                case R.id.favorites:
                    if(fragmentArray.get(1) == null) {
                        currentFragment = FavoritesFragment.newInstance();
                        fragmentArray.append(1, currentFragment);
                    } else {
                        currentFragment = fragmentArray.get(1);
                    }
                    bottomNavigationView.getMenu().findItem(R.id.trashFavorites).setVisible(true);
                    bottomNavigationView.getMenu().findItem(R.id.flag).setVisible(false);
                    break;

                case R.id.flag:
                    if(fragmentArray.get(2) == null) {
                        currentFragment = CountryFragment.newInstance();
                        fragmentArray.append(2, currentFragment);                    } else {
                        currentFragment = fragmentArray.get(2);
                    }
                    break;
                case R.id.trashFavorites:
                    FavoritesFragment favoritesFragment = (FavoritesFragment)currentFragment;
                    favoritesFragment.clearList();
                    BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.favorites);
                    badgeDrawable.setVisible(false);
                    badgeDrawable.setNumber(0);
                    break;
            }

            if(currentFragment != null) {
                replaceFragment(currentFragment);
                return true;
            } else {
                return false;
            }
        });
    }

    private void replaceFragment(Fragment newFragment) {
        if (newFragment instanceof FavoritesFragment) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(
                            R.anim.slide_in_right,  // enter
                            R.anim.fade_out,  // exit
                            R.anim.fade_in,   // popEnter
                            R.anim.slide_out  // popExit
                    );
            fragmentTransaction.replace(R.id.fragment_container, newFragment).commit();
        }

        if (newFragment instanceof SearchPlayerFragment) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(
                            R.anim.slide_in_left,  // enter
                            R.anim.fade_out,  // exit
                            R.anim.fade_in,   // popEnter
                            R.anim.slide_out  // popExit
                    );
            fragmentTransaction.replace(R.id.fragment_container, newFragment).commit();
        }

        if (newFragment instanceof CountryFragment){
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(
                            R.anim.slide_in_top,  // enter
                            R.anim.fade_out,  // exit
                            R.anim.fade_in,   // popEnter
                            R.anim.slide_out  // popExit
                    );
            fragmentTransaction.replace(R.id.fragment_container, newFragment).commit();
        }

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    public static void setFavorites(int countFavorites){
        BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.favorites);

        if (countFavorites != 0) {
            badgeDrawable.setVisible(true);
            badgeDrawable.setNumber(countFavorites);
        }else{
            badgeDrawable.setVisible(false);
        }

    }

    public static void incrementFavorites() {
        BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.favorites);

        if (badgeDrawable.getNumber() == 0) {
            badgeDrawable.setVisible(true);
        }
        badgeDrawable.setNumber(badgeDrawable.getNumber()+1);
    }

    public static void decrementFavorites() {
        BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.favorites);

        if (badgeDrawable.getNumber() == 1 ) {
            badgeDrawable.setVisible(false);
        }
        badgeDrawable.setNumber(badgeDrawable.getNumber()-1);
    }

    public static String getCurrentFlag() {
        return currentFlag;
    }

    public static void setCurrentFlag(String flag){
        newFlag = flag;
    }

    public static void goback(Context context){
        currentFragment = SearchPlayerFragment.newInstance();
        fragmentArray.append(0, MainActivity.currentFragment);

        FragmentTransaction fragmentTransaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(
                            R.anim.slide_in_left,  // enter
                            R.anim.fade_out,  // exit
                            R.anim.fade_in,   // popEnter
                            R.anim.slide_out  // popExit
                             );
        fragmentTransaction.replace(R.id.fragment_container, currentFragment).commit();

        bottomNavigationView.getMenu().findItem(R.id.players).setChecked(true);
    }

}
