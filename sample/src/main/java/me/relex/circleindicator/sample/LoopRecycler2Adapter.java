package me.relex.circleindicator.sample;

import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LoopRecycler2Adapter extends SampleRecyclerAdapter {

	private List<Integer> colors = new ArrayList();
	private final Random random = new Random();

	public LoopRecycler2Adapter(int count) {
		super(count);
		for (int i = 0; i < count; i++) {
			colors.add(0xff000000 | random.nextInt(0x00ffffff));
		}

		if (colors.size() > 0) {
			Integer firstColor = colors.get(0);
			Integer lastColor = colors.get(colors.size() - 1);

			colors.add(0, lastColor);
			colors.add(colors.size(), firstColor);
		}
	}

	@Override
	public void onBindViewHolder(@NonNull TextViewHolder holder, int position) {
		TextView textView = (TextView) holder.itemView;
		textView.setText(String.valueOf(getLoopPosition(position)));
		textView.setBackgroundColor(colors.get(position));
	}

	@Override
	public int getItemCount() {
		return colors.size();
	}

	private int getLoopPosition(int position) {
		if (position == 0) {
			return colors.size() - 2;
		} else if (position == colors.size() - 1) {
			return 1;
		} else {
			return position;
		}
	}
}
