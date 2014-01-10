package com.paging.gridview.sample;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.paging.gridview.HeaderViewGridAdapter;
import com.paging.gridview.PagingGridView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

	private PagingGridView gridView;
	private MyPagingAdaper adapter;

	private List<String> firstList;
	private List<String> secondList;
	private List<String> thirdList;

	private int pager = 0;

	private ProgressDialog loadingDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		gridView = (PagingGridView) findViewById(R.id.paging_grid_view);
		adapter = new MyPagingAdaper();

		initData();
		createProgressDialog();

		gridView.setHasMoreItems(true);
		gridView.setPagingableListener(new PagingGridView.Pagingable() {
			@Override
			public void onLoadMoreItems() {
				if (pager < 3) {
					new CountryAsyncTask(false).execute();
				} else {
					gridView.onFinishLoading(false, null);
				}
			}
		});
	}

	public void createProgressDialog() {
		loadingDialog = new ProgressDialog(this);
		loadingDialog.setIndeterminate(true);
		loadingDialog.setMessage(getString(R.string.loading_countries));
	}

	private void clearData() {
		if(gridView.getAdapter() != null) {
			pager = 0;
			adapter = (MyPagingAdaper)((HeaderViewGridAdapter)gridView.getAdapter()).getWrappedAdapter();
			adapter.removeAllItems();
			gridView = null;
			gridView = (PagingGridView) findViewById(R.id.paging_grid_view);
			adapter = new MyPagingAdaper();

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.options_menu, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.start_demo:
				clearData();
				new CountryAsyncTask(true).execute();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}



	private void initData() {
		firstList = new ArrayList<String>();
		firstList.add("Afghanistan");
		firstList.add("Albania");
		firstList.add("Algeria");
		firstList.add("Andorra");
		firstList.add("Angola");
		firstList.add("Antigua and Barbuda");
		firstList.add("Argentina");
		firstList.add("Armenia");
		firstList.add("Aruba");
		firstList.add("Australia");
		firstList.add("Austria");
		firstList.add("Azerbaijan");

		firstList.add("Bahamas, The");
		firstList.add("Bahrain");
		firstList.add("Bangladesh");
		firstList.add("Barbados");
		firstList.add("Belarus");
		firstList.add("Belgium");
		firstList.add("Belize");
		firstList.add("Benin");
		firstList.add("Bhutan");
		firstList.add("Bolivia");
		firstList.add("Bosnia and Herzegovina");
		firstList.add("Botswana");
		firstList.add("Brazil");
		firstList.add("Brunei");
		firstList.add("Bulgaria");
		firstList.add("Burkina Faso");
		firstList.add("Burma");
		firstList.add("Burundi");

		secondList = new ArrayList<String>();
		secondList.add("Cambodia");
		secondList.add("Cameroon");
		secondList.add("Canada");
		secondList.add("Cape Verde");
		secondList.add("Central African Republic");
		secondList.add("Chad");
		secondList.add("Chile");
		secondList.add("China");
		secondList.add("Colombia");
		secondList.add("Comoros");
		secondList.add("Congo, Democratic Republic of the");
		secondList.add("Costa Rica");
		secondList.add("Cote d'Ivoire");
		secondList.add("Croatia");
		secondList.add("Cuba");
		secondList.add("Curacao");
		secondList.add("Cyprus");
		secondList.add("Czech Republic");

		thirdList = new ArrayList<String>();
		thirdList.add("Denmark");
		thirdList.add("Djibouti");
		thirdList.add("Dominica");
		thirdList.add("Dominican Republic");
	}


	private class CountryAsyncTask extends SafeAsyncTask<List<String>> {
		private boolean showLoading;
		public CountryAsyncTask(boolean showLoading) {
			this.showLoading = showLoading;
		}

		@Override
		protected void onPreExecute() throws Exception {
			super.onPreExecute();
			if(showLoading) {
				loadingDialog.show();
			}
		}

		@Override
		public List<String> call() throws Exception {
			List<String> result = null;
			switch(pager) {
				case 0:
					result = firstList;
				break;
				case 1:
					result = secondList;
				break;
				case 2:
					result = thirdList;
				break;
			}
			Thread.sleep(3000);
			return result;
		}

		@Override
		protected void onSuccess(List<String> newItems) throws Exception {
			super.onSuccess(newItems);
			pager++;
			if(gridView.getAdapter() == null) {
				gridView.setAdapter(adapter);
			}
			gridView.onFinishLoading(true, newItems);
		}

		@Override
		protected void onFinally() throws RuntimeException {
			super.onFinally();
			if(loadingDialog.isShowing()) {
				loadingDialog.dismiss();
			}
		}
	}

}
