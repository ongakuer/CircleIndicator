package me.relex.circleindicator.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import me.relex.circleindicator.sample.fragment.ChangeColorFragment;
import me.relex.circleindicator.sample.fragment.CustomAnimationFragment;
import me.relex.circleindicator.sample.fragment.DefaultFragment;
import me.relex.circleindicator.sample.fragment.DynamicAdapterFragment;
import me.relex.circleindicator.sample.fragment.OnIndicatorClickFragment;

public class DemoActivity extends AppCompatActivity {

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_demo);

        initToolbar();

        Fragment demoFragment = Fragment.instantiate(this, DemoListFragment.class.getName());
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, demoFragment);
        fragmentTransaction.commit();

        getSupportFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    @Override public void onBackStackChanged() {
                        int count = getSupportFragmentManager().getBackStackEntryCount();
                        ActionBar actionbar = getSupportActionBar();
                        if (actionbar != null) {
                            actionbar.setDisplayHomeAsUpEnabled(count > 0);
                            actionbar.setDisplayShowHomeEnabled(count > 0);
                        }
                    }
                });
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public static class DemoListFragment extends Fragment implements View.OnClickListener {

        @Nullable @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_demo_list, container, false);
        }

        @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            view.findViewById(R.id.demo_default).setOnClickListener(this);
            view.findViewById(R.id.demo_custom_animation).setOnClickListener(this);
            view.findViewById(R.id.demo_change_color).setOnClickListener(this);
            view.findViewById(R.id.demo_dynamic_adapter).setOnClickListener(this);
            view.findViewById(R.id.demo_on_indicator_click).setOnClickListener(this);
        }

        @Override public void onClick(View v) {
            switch (v.getId()) {
                case R.id.demo_default:
                    navigateToFragment(DefaultFragment.class.getName());
                    break;
                case R.id.demo_custom_animation:
                    navigateToFragment(CustomAnimationFragment.class.getName());
                    break;
                case R.id.demo_change_color:
                    navigateToFragment(ChangeColorFragment.class.getName());
                    break;
                case R.id.demo_dynamic_adapter:
                    navigateToFragment(DynamicAdapterFragment.class.getName());
                    break;
                case R.id.demo_on_indicator_click:
                    navigateToFragment(OnIndicatorClickFragment.class.getName());
                    break;
            }
        }

        private void navigateToFragment(String fragmentName) {
            Fragment fragment = Fragment.instantiate(getContext(), fragmentName);
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                    android.R.anim.fade_in, android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.addToBackStack(fragmentName);
            fragmentTransaction.commit();
        }
    }
}
