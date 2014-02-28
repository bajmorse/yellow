package com.tddrampup.fragments;

import com.tddrampup.RobolectricTestRunnerWithInjection;

import org.junit.runner.RunWith;

/**
* Created by WX009-PC on 2/21/14.
*/
@RunWith(RobolectricTestRunnerWithInjection.class)
public class ListFragmentTest {
//    private MainActivity mActivity;
//    private ListFragment mListFragment;
//    private ListView mListView;
//
//    @Inject
//    ListingsInterface mListings;
//
//    private void addFragment() {
//        mListFragment = new ListFragment();
//        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.add(mListFragment, null);
//        fragmentTransaction.commit();
//    }
//
//    private void removeFragment() {
//        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.remove(mListFragment);
//        fragmentTransaction.commit();
//    }
//
//    @Before
//    public void setUp() throws Exception {
//        mActivity = Robolectric.buildActivity(MainActivity.class).create().start().visible().get();
//        mListings.setListings(new ArrayList<Listing>());
//        addFragment();
//        ((FakeListings) mListings).createFakeData(); // Must be after addFragment() for loading to show
//        mListView = (ListView) mListFragment.getView().findViewById(R.id.list_view);
//        ListingAdapter listingAdapter = new ListingAdapter(mActivity.getLayoutInflater(), mListings.getListings());
//        mListView.setAdapter(listingAdapter);
//    }
//
//    @Test
//    public void onAttach_shouldAttachOnItemClickedListener() throws Exception {
//        assertThat(mListFragment.mListener).isNotNull();
//    }
//
//    @Test
//    public void onDetach_shouldClearOnItemClickedListener() throws Exception {
//        removeFragment();
//        assertThat(mListFragment.mListener).isNull();
//        assertThat(mListFragment.isAdded()).isFalse();
//    }
//
//    @Test
//    public void onListViewItemClickedListener_shouldCallOnListViewItemClicked() throws Exception {
//        shadowOf(mListView).performItemClick(0);
//        //TODO: add function call assert
//    }
//
//    @Test
//    public void setupAdapter_shouldPopulateListViewWithCorrectData() throws Exception {
//        TextView titleTextView = (TextView) mListView.getAdapter().getView(0, null, mListView).findViewById(R.id.listing_title);
//        TextView addressTextView = (TextView) mListView.getAdapter().getView(0, null, mListView).findViewById(R.id.listing_address);
//        TextView cityTextView = (TextView) mListView.getAdapter().getView(0, null, mListView).findViewById(R.id.listing_city);
//
//        assertThat(titleTextView).hasText("One");
//        assertThat(addressTextView).hasText("Street");
//        assertThat(cityTextView).hasText("Toronto");
//    }
//
//    @Test
//    public void loadingLifecycle_shouldShowAndHideLoadingProgressDialog() throws Exception {
//        assertThat(mListFragment.isProgressDialogShowing()).isTrue();
//        mListFragment.new Callback().listCallbackCall(mListFragment.mListings.getListings());
//        assertThat(mListFragment.isProgressDialogShowing()).isFalse();
//    }
//
//    // TODO: recycling views test
}