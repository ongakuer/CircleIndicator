package me.relex.circleindicator.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import java.util.ArrayList;
import java.util.List;
import me.relex.circleindicator.sample.fragment.ChangeColorFragment;
import me.relex.circleindicator.sample.fragment.CustomAnimationFragment;
import me.relex.circleindicator.sample.fragment.DefaultFragment;
import me.relex.circleindicator.sample.fragment.DynamicAdapterFragment;
import me.relex.circleindicator.sample.fragment.LoopRecyclerViewFragment;
import me.relex.circleindicator.sample.fragment.LoopViewPagerFragment;
import me.relex.circleindicator.sample.fragment.RecyclerViewFragment;
import me.relex.circleindicator.sample.fragment.ResetAdapterFragment;
import me.relex.circleindicator.sample.fragment.SnackbarBehaviorFragment;
import me.relex.circleindicator.sample.fragment.CustomLastItemFragment;

public class SampleActivity extends AppCompatActivity {

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sample);

        initToolbar();

        Fragment demoFragment = Fragment.instantiate(this, SampleListFragment.class.getName());
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, demoFragment);
        fragmentTransaction.commit();

        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            int count = getSupportFragmentManager().getBackStackEntryCount();
            ActionBar actionbar = getSupportActionBar();
            if (actionbar != null) {
                actionbar.setDisplayHomeAsUpEnabled(count > 0);
                actionbar.setDisplayShowHomeEnabled(count > 0);
            }
        });
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    public static class SampleListFragment extends Fragment {

        @Nullable @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                @Nullable Bundle savedInstanceState) {
            return new RecyclerView(getContext());
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            SampleListAdapter adapter = new SampleListAdapter();

            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);

            adapter.add(new SampleInfo("Default", DefaultFragment.class.getName()));
            adapter.add(new SampleInfo("Custom Last Item", CustomLastItemFragment.class.getName()));
            adapter.add(
                    new SampleInfo("Custom Animation", CustomAnimationFragment.class.getName()));
            adapter.add(new SampleInfo("Change Color", ChangeColorFragment.class.getName()));
            adapter.add(new SampleInfo("Dynamic Adapter", DynamicAdapterFragment.class.getName()));
            adapter.add(new SampleInfo("Reset Adapter", ResetAdapterFragment.class.getName()));
            adapter.add(new SampleInfo("LoopViewPager", LoopViewPagerFragment.class.getName()));
            adapter.add(
                    new SampleInfo("Snackbar Behavior", SnackbarBehaviorFragment.class.getName()));
            adapter.add(new SampleInfo("RecyclerView (CircleIndicator2)",
                    RecyclerViewFragment.class.getName()));
            adapter.add(new SampleInfo("LoopRecyclerView (CircleIndicator2) and Manual control",
                    LoopRecyclerViewFragment.class.getName()));
        }

        private class SampleListAdapter extends RecyclerView.Adapter<ItemViewHolder> {

            private final List<SampleInfo> mList = new ArrayList<>();

            @NonNull @Override
            public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return ItemViewHolder.create(parent);
            }

            @Override
            public void onBindViewHolder(@NonNull final ItemViewHolder holder, int position) {
                SampleInfo sample = mList.get(position);
                holder.bindView(sample.title);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        navigateToFragment(mList.get(holder.getAdapterPosition()).fragmentName);
                    }
                });
            }

            @Override public int getItemCount() {
                return mList.size();
            }

            public boolean add(SampleInfo object) {
                int lastIndex = mList.size();
                if (mList.add(object)) {
                    notifyItemInserted(lastIndex);
                    return true;
                } else {
                    return false;
                }
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

        private static class ItemViewHolder extends RecyclerView.ViewHolder {
            ItemViewHolder(View itemView) {
                super(itemView);
            }

            void bindView(String title) {
                ((TextView) itemView).setText(title);
            }

            static ItemViewHolder create(ViewGroup viewGroup) {
                return new ItemViewHolder(LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_view, viewGroup, false));
            }
        }

        private static class SampleInfo {
            public final String title;
            final String fragmentName;

            SampleInfo(String title, String fragmentName) {
                this.title = title;
                this.fragmentName = fragmentName;
            }
        }
    }
}
