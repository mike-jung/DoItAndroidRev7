package org.techtown.mission10;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

public class Fragment1 extends Fragment {
	private static final String TAG = "Fragment1";

	FragmentCallback callback;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);

		if (context instanceof FragmentCallback) {
			callback = (FragmentCallback) context;
		} else {
			Log.d(TAG, "Activity is not FragmentCallback.");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment1, container, false);
		
		Button button1 = rootView.findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				callback.onFragmentSelected(1, null);
			}
			
		});


		ViewPager pager = rootView.findViewById(R.id.pager);
		pager.setOffscreenPageLimit(3);

		CustomerPagerAdapter adapter = new CustomerPagerAdapter(getFragmentManager());

		for (int i = 0; i < 3; i++) {
			PageFragment page = createPage(i);
			adapter.addItem(page);
		}

		pager.setAdapter(adapter);


		return rootView;
	}

	public PageFragment createPage(int index) {
		String name = "화면 " + index;
		PageFragment fragment = PageFragment.newInstance(name);

		return fragment;
	}


	class CustomerPagerAdapter extends FragmentStatePagerAdapter {
		ArrayList<Fragment> items = new ArrayList<Fragment>();

		public CustomerPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		public void addItem(Fragment item) {
			items.add(item);
		}

		@Override
		public Fragment getItem(int position) {
			return items.get(position);
		}

		@Override
		public int getCount() {
			return items.size();
		}
	}



}
