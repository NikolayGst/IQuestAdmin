package zone.iquestadmin;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import zone.iquestadmin.Fragment.QuestListFragment;

public class MainActivity extends AppCompatActivity {
    private FragmentManager mFragmentManager;
    private QuestListFragment mQuestListFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragmentManager = getSupportFragmentManager();

        mQuestListFragment = new QuestListFragment();

        mFragmentManager
                .beginTransaction()
                .replace(R.id.container,mQuestListFragment)
                .addToBackStack("quest")
                .commit();
    }
}
