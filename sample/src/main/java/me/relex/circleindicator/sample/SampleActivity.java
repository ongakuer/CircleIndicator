package me.relex.circleindicator.sample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import me.relex.circleindicator.sample.fragment.ChangeDrawableFragment;
import me.relex.circleindicator.sample.fragment.CustomAnimationFragment;
import me.relex.circleindicator.sample.fragment.DefaultFragment;
import me.relex.circleindicator.sample.fragment.DynamicAdapterFragment;
import me.relex.circleindicator.sample.fragment.LoopRecyclerViewFragment;
import me.relex.circleindicator.sample.fragment.LoopViewPagerFragment;
import me.relex.circleindicator.sample.fragment.RecyclerViewFragment;
import me.relex.circleindicator.sample.fragment.ResetAdapterFragment;
import me.relex.circleindicator.sample.fragment.SnackbarBehaviorFragment;
import me.relex.circleindicator.sample.fragment.ViewPager2Fragment;

public class SampleActivity extends AppCompatActivity {

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sample);

        initToolbar();

        Fragment demoFragment = getSupportFragmentManager().getFragmentFactory()
                .instantiate(getClassLoader(), SampleListFragment.class.getName());
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
            adapter.add(
                    new SampleInfo("Custom Animation", CustomAnimationFragment.class.getName()));
            adapter.add(new SampleInfo("Change Drawable", ChangeDrawableFragment.class.getName()));
            adapter.add(new SampleInfo("Dynamic Adapter", DynamicAdapterFragment.class.getName()));
            adapter.add(new SampleInfo("Reset Adapter", ResetAdapterFragment.class.getName()));
            adapter.add(new SampleInfo("LoopViewPager", LoopViewPagerFragment.class.getName()));
            adapter.add(
                    new SampleInfo("Snackbar Behavior", SnackbarBehaviorFragment.class.getName()));
            adapter.add(new SampleInfo("RecyclerView (CircleIndicator2)",
                    RecyclerViewFragment.class.getName()));
            adapter.add(new SampleInfo("LoopRecyclerView (CircleIndicator2) and Manual control",
                    LoopRecyclerViewFragment.class.getName()));
            adapter.add(new SampleInfo("AndroidX ViewPager2 (CircleIndicator3)",
                    ViewPager2Fragment.class.getName()));
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
            Fragment fragment = getFragmentManager().getFragmentFactory()
                    .instantiate(getContext().getClassLoader(), fragmentName);
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
