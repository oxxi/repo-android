package com.dev.iguana.apperp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class NavigationDrawerFragment extends Fragment {
    private RecyclerView mRecyclerView;
    public static final String PREF_FILE_NAME ="testpref";
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";
    private ActionBarDrawerToggle mDrawerToggle;
    private ViewAdapter mAdapter;
    private DrawerLayout mDrawerLayout;
    private boolean mUserLearnedDrawer;
    private boolean mFrameSavedInstancestate;
    private View contentView;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }
    private static void saveToPreferences(Context context, String preferenceName, String prefrenceValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(preferenceName,prefrenceValue);
        edit.apply();

    }
    private static String readToPreferences(Context context, String preferenceName, String defaultValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        return  sharedPreferences.getString(preferenceName,defaultValue);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(readToPreferences(getActivity(),KEY_USER_LEARNED_DRAWER,"false"));

        if (savedInstanceState != null){
            mFrameSavedInstancestate = true;
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.activity_navigation_drawer_fragment, container, false);
        mRecyclerView = (RecyclerView)layout.findViewById(R.id.drawerList);
        mAdapter = new ViewAdapter(getActivity(), getData());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),mRecyclerView,new ClickListener() {
            @Override
            public void onClickListener(View view, int position) {
                Log.d("inten list view : ",""+position);

                if(position == 0){
                    startActivity(new Intent(getActivity(),ChartsActivity.class));
                }
                if(position == 1){
                    startActivity(new Intent(getActivity(),ReportsActivity.class));
                }
                if(position == 2){
                    startActivity(new Intent(getActivity(),SalesActivity.class));
                }
                if(position == 3){
                    startActivity(new Intent(getActivity(),GoalActivity.class));
                }


                /*if(position == 0){
                    //startActivity(new Intent(getActivity(),MainActivity.class));
                    // Log.d("get getActivity", getActivity().getLocalClassName());
                    if( getActivity().getLocalClassName().equals("MainActivity")){
                        mDrawerLayout.closeDrawer(contentView);
                    }else{
                        startActivity(new Intent(getActivity(),MainActivity.class));
                    }
                }
                if (position == 1){
                    Log.d("get getActivity", getActivity().getLocalClassName());
                    if( getActivity().getLocalClassName().equals("ClientesActivity")){
                        //   mDrawerLayout.closeDrawer(contentView);
                    }else{
                        // startActivity(new Intent(getActivity(),ClientesActivity.class));
                    }


                }*/
            }

            @Override
            public void onLongClickListener(View view, int position) {
                //  Log.d("onLong Click posi : ",""+position);
            }
        }));
        return layout;
    }

    public List<Information> getData(){
        List<Information> data = new ArrayList<>();
        int[] icons = {R.drawable.ic_charts,R.drawable.ic_reportes,R.drawable.ic_ventas,R.drawable.ic_metas};
        String[] titles = {"Graficos","Reportes","Resumen Ventas","Metas"};

        for (int i = 0; i < titles.length; i++) {
            Information information = new Information();
            information.title = titles[i];
            information.iconId = icons[i];
            data.add(information);
        }
        return data;
    }

    public void setUp(int frameId, DrawerLayout drawerLayout, Toolbar toolbar) {
        contentView = getActivity().findViewById(frameId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close ){

            @Override
            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);
                if (!mUserLearnedDrawer){
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity(),KEY_USER_LEARNED_DRAWER,mUserLearnedDrawer+"");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {

                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }
        };
        if(!mUserLearnedDrawer && !mFrameSavedInstancestate){
            mDrawerLayout.openDrawer(contentView);
        }
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();

            }
        });

    }




    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{
        private GestureDetector gestureDetector;
        private ClickListener mClickListener;
        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, ClickListener clickListener){
            // Log.d("Constructor ","RecyclerTouchListener");
            mClickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    //return super.onSingleTapUp(e);
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(),e.getY());
                    if(child!=null && mClickListener !=null){
                        mClickListener.onLongClickListener(child,recyclerView.getChildPosition(child));
                    }

                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            View child = recyclerView.findChildViewUnder(motionEvent.getX(),motionEvent.getY());
            if(child!=null && mClickListener !=null && gestureDetector.onTouchEvent(motionEvent)){
                mClickListener.onClickListener(child,recyclerView.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

        }
    }
    public static interface ClickListener{
        public void onClickListener(View view, int position);
        public void onLongClickListener(View view,int position);

    }
}
