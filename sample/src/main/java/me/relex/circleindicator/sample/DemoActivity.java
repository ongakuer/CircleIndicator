package me.relex.circleindicator.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.Arrays;
import java.util.List;
import me.relex.circleindicator.sample.fragment.ChangeColorFragment;
import me.relex.circleindicator.sample.fragment.CustomAnimationFragment;
import me.relex.circleindicator.sample.fragment.DefaultFragment;
import me.relex.circleindicator.sample.fragment.DynamicAdapterFragment;
import me.relex.circleindicator.sample.fragment.ResetAdapterFragment;

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

    public static class DemoListFragment extends Fragment {

        @Nullable @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                @Nullable Bundle savedInstanceState) {
            return new RecyclerView(getContext());
        }

        @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(new DemoListAdapter());
        }

        private class DemoListAdapter extends RecyclerView.Adapter<DemoViewHolder> {

            private final List<String> mFragmentList;
            private final List<String> mNameList;

            public DemoListAdapter() {
                mNameList = Arrays.asList(getResources().getStringArray(R.array.demo_name));
                mFragmentList = Arrays.asList(//
                        DefaultFragment.class.getName(),//
                        CustomAnimationFragment.class.getName(),//
                        ChangeColorFragment.class.getName(), //
                        DynamicAdapterFragment.class.getName(),//
                        ResetAdapterFragment.class.getName());
            }

            @Override public DemoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return DemoViewHolder.create(parent);
            }

            @Override public void onBindViewHolder(final DemoViewHolder holder, int position) {
                holder.bindView(mNameList.get(position));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        navigateToFragment(mFragmentList.get(holder.getAdapterPosition()));
                    }
                });
            }

            @Override public int getItemCount() {
                return mFragmentList.size();
            }
        }

        private static class DemoViewHolder extends RecyclerView.ViewHolder {
            public DemoViewHolder(View itemView) {
                super(itemView);
            }

            public void bindView(String fragmentName) {
                ((TextView) itemView).setText(fragmentName);
            }

            public static DemoViewHolder create(ViewGroup viewGroup) {
                return new DemoViewHolder(LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_demo, viewGroup, false));
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
