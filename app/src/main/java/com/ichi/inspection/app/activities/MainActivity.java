package com.ichi.inspection.app.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ichi.inspection.app.R;
import com.ichi.inspection.app.fragments.BuyersAgentFragment;
import com.ichi.inspection.app.fragments.ClientInfoFragment;
import com.ichi.inspection.app.fragments.FeesFragment;
import com.ichi.inspection.app.fragments.ForgetPasswordFragment;
import com.ichi.inspection.app.fragments.InspectionDetailsFragment;
import com.ichi.inspection.app.fragments.InspectionListFragment;
import com.ichi.inspection.app.fragments.InspectionOrderFragment;
import com.ichi.inspection.app.fragments.InspectionSelectionFragment;
import com.ichi.inspection.app.fragments.ListingAgentFragment;
import com.ichi.inspection.app.fragments.LoginFragment;
import com.ichi.inspection.app.fragments.PaymentFragment;
import com.ichi.inspection.app.fragments.PropertyInfoFragment;
import com.ichi.inspection.app.fragments.SchedulerFragment;
import com.ichi.inspection.app.fragments.SpecialInstructionFragment;
import com.ichi.inspection.app.fragments.SplashFragment;
import com.ichi.inspection.app.service.UploadPhotoService;
import com.ichi.inspection.app.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    private static final String TAG = StartActivity.class.getSimpleName();

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        navigateToScreen(Constants.INSPECTIONLIST, null, false);

        startUploadService();
    }

    /**
     * replace new fragment on current fragment
     *
     * @param containerId     containerId
     * @param fragment        fragment
     * @param keepOnBackState keepOnBackState
     */
    public void replaceFragment(int containerId, Fragment fragment, boolean keepOnBackState) {
        try {
            FragmentManager manager = getSupportFragmentManager();
            //manager.enableDebugLogging(true);
            FragmentTransaction transaction = manager.beginTransaction();

            //based on the keepOnBackState flag manager will manage the back stack fragment
            if (!keepOnBackState) {
                int count = manager.getBackStackEntryCount();
                for (int i = 0; i < count; ++i) {
                    manager.popBackStack();
                }
            }
            //transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            transaction.replace(containerId, fragment, fragment.getClass().getName());
            if (keepOnBackState) {
                transaction.addToBackStack(fragment.getClass().getName());
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    *  Handles back stack for the fragment manager
    * */
    @Override
    public void onBackPressed(){

        if (getSupportFragmentManager().getBackStackEntryCount() == 0){
            Log.v(TAG,"onBackPressed : " + getSupportFragmentManager().getBackStackEntryCount() );
            Fragment fragment=getSupportFragmentManager().findFragmentById(R.id.fl_home_container);
            if(fragment instanceof InspectionListFragment){
                finish();  // To prevent back from the activity
            }else{
                navigateToScreen(0, null, true);
            }
        }
        else {
            Log.v(TAG,"super onBackPressed : ");
            super.onBackPressed();
        }
    }
    /**
     * Navigate to different screen
     * @param position
     */
    public void navigateToScreen(int position, Bundle bundle, boolean keepOnBackState) {

        Fragment fragment = null;

        switch (position){
            case Constants.INSPECTIONLIST:
                fragment = new InspectionListFragment();
                break;
            case Constants.INSPECTION_NAVIGATION:
                fragment = new InspectionSelectionFragment();
                break;
            case Constants.INSPECTION_ORDER:
                fragment = new InspectionOrderFragment();
                break;
            case Constants.INSPECTION_DETAIL:
                fragment = new InspectionDetailsFragment();
                break;
            case Constants.INSPECTION_CLIENT_INFO:
                fragment = new ClientInfoFragment();
                break;
            case Constants.INSPECTION_BUYERS_AGENT:
                fragment = new BuyersAgentFragment();
                break;
            case Constants.INSPECTION_LINKING_AGENT:
                fragment = new ListingAgentFragment();
                break;
            case Constants.INSPECTION_PROPERTY_INFO:
                fragment = new PropertyInfoFragment();
                break;
            case Constants.INSPECTION_SCHEDULER:
                fragment = new SchedulerFragment();
                break;
            case Constants.INSPECTION_SPECIAL_INSTRUCTION:
                fragment = new SpecialInstructionFragment();
                break;
            case Constants.INSPECTION_FEES:
                fragment = new FeesFragment();
                break;
            case Constants.INSPECTION_PAYMENT:
                fragment = new PaymentFragment();
                break;
        }

        if(fragment == null){
            fragment = new SplashFragment();
        }
        if(bundle != null) fragment.setArguments(bundle);

        replaceFragment(R.id.fl_home_container, fragment, keepOnBackState);

    }


    public void logout(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MainActivity.this, StartActivity.class);
                startActivity(intent);
                finish();
                prefs.clearSavedToken();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();


    }

    public void openPendingUploads(){
        Intent intent = new Intent(MainActivity.this,UploadProgressActivity.class);
        startActivity(intent);
    }


}
