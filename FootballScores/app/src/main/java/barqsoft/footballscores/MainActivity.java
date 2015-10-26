package barqsoft.footballscores;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity
{
    public static String LOG_TAG = MainActivity.class.getSimpleName();
    private final String SAVE_TAG = "Save Test";

    public static int mSelectedMatchId;
    public static int mCurrentFragment = 2;

    private PagerFragment mMainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(LOG_TAG, "Reached MainActivity onCreate");
        if (savedInstanceState == null) {
            mMainFragment = new PagerFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, mMainFragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about)
        {
            Intent start_about = new Intent(this,AboutActivity.class);
            startActivity(start_about);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        Log.v(SAVE_TAG,"will save");
        Log.v(SAVE_TAG,"fragment: "+String.valueOf(mMainFragment.mPagerHandler.getCurrentItem()));
        Log.v(SAVE_TAG,"selected id: "+ mSelectedMatchId);

        outState.putInt("Pager_Current", mMainFragment.mPagerHandler.getCurrentItem());
        outState.putInt("Selected_match", mSelectedMatchId);
        getSupportFragmentManager().putFragment(outState,"main_fragment", mMainFragment);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        Log.v(SAVE_TAG,"will retrieve");
        Log.v(SAVE_TAG,"fragment: "+String.valueOf(savedInstanceState.getInt("Pager_Current")));
        Log.v(SAVE_TAG,"selected id: "+savedInstanceState.getInt("Selected_match"));

        mCurrentFragment = savedInstanceState.getInt("Pager_Current");
        mSelectedMatchId = savedInstanceState.getInt("Selected_match");
        mMainFragment = (PagerFragment) getSupportFragmentManager().getFragment(savedInstanceState,"main_fragment");
        super.onRestoreInstanceState(savedInstanceState);
    }
}
