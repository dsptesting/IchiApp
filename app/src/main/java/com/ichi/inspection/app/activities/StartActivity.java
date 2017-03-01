package com.ichi.inspection.app.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.ichi.inspection.app.R;
import com.ichi.inspection.app.fragments.SplashFragment;
import com.ichi.inspection.app.interfaces.OnApiCallbackListener;
import com.ichi.inspection.app.models.BaseResponse;
import com.ichi.inspection.app.utils.Constants;
import com.ichi.inspection.app.utils.Utils;

import butterknife.BindView;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

//@RuntimePermissions
public class StartActivity extends BaseActivity implements OnApiCallbackListener {

    private static final String TAG = StartActivity.class.getSimpleName();

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    //private LoginAsyncTask loginAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO uncomment this code once having successful login flow with user model
        /*if(prefs != null && prefs.getLoggedInUser(this) != null){
            startActivity(new Intent(SplashActivity.this,MainActivity.class));
            finish();
            return;
        }*/

        setContentView(R.layout.activity_main);
        navigateToScreen(Constants.SPLASH, null, false);

        //SplashActivityPermissionsDispatcher.showContactsWithCheck(this);
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
            if(fragment instanceof SplashFragment){
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
            case Constants.SPLASH:
                fragment = new SplashFragment();
                break;
            /*case Constants.SIGN_UP:
                fragment = new SignUpFragment();
                break;*/
        }

        if(fragment == null){
            fragment = new SplashFragment();
        }
        if(bundle != null) fragment.setArguments(bundle);

        replaceFragment(R.id.fl_home_container, fragment, keepOnBackState);

    }


    //TODO dont delete this code, its permission code. will need this soon
    /*@NeedsPermission({Manifest.permission.READ_CONTACTS})
    void showContacts(){
        Log.v(TAG, "Permission accessed below ");
        try{
            //TODO write code to fetch and sync contacts

            RegisterResponse registerResponse= (RegisterResponse) prefs.getObject(Constants.PREF_REGISTER_OBJECT, RegisterResponse.class);
            GetTokenResponse getTokenRes = (GetTokenResponse) prefs.getObject(Constants.PREF_TOKEN_OBJECT, GetTokenResponse.class);

            if(getTokenRes!= null){
                if(getTokenRes.accessToken != null){
                    loginAsyncTask = new LoginAsyncTask(this,this);
                    loginAsyncTask.execute();
                }
            }

        }
        catch (SecurityException e){
            e.printStackTrace();
            Utils.showSnackBar(coordinatorLayout,getString(R.string.lbl_permission_denied));
        }
        catch (Exception e){
            e.printStackTrace();
            Utils.showSnackBar(coordinatorLayout,getString(R.string.lbl_permission_denied));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        SplashActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.READ_CONTACTS})
    void showRationaleForContacts(PermissionRequest request) {
        showRationaleDialog(R.string.permission_contacts_rationale, request);
    }

    @OnPermissionDenied({Manifest.permission.READ_CONTACTS})
    void showDeniedForContacts() {
        Utils.showSnackBar(coordinatorLayout,getString(R.string.permission_contacts_denied));
    }

    @OnNeverAskAgain({Manifest.permission.READ_CONTACTS})
    void showNeverAskForContacts() {
        Utils.showSnackBar(coordinatorLayout,getString(R.string.permission_contacts_never_askagain));
    }

    private void showRationaleDialog(@StringRes int messageResId, final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setPositiveButton(R.string.button_allow, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        *//*if(isAdded()) *//*request.proceed();
                    }
                })
                .setNegativeButton(R.string.button_deny, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        *//*if(isAdded())*//* request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage(messageResId)
                .show();
    }
*/

    @Override
    public void onApiPreExecute(AsyncTask asyncTask) {
        showProgressDialog("" , "Loading data...");
    }

    @Override
    public void onApiPostExecute(BaseResponse baseResponse, AsyncTask asyncTask) {
        cancelProgressDialog();
        goToHomeActivity();
    }

    private void goToHomeActivity(){
        Intent intent = new Intent(this , MainActivity.class);
        startActivity(intent);
        finish();
    }
}
