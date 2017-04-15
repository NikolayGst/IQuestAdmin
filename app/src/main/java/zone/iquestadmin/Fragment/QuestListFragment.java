package zone.iquestadmin.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import zone.iquestadmin.Adapter.QuestAdapter;
import zone.iquestadmin.R;


public class QuestListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private QuestAdapter mQuestAdapter;
    private List<String> mQuestList;

    public QuestListFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quest_list, container, false);

        mQuestList = new ArrayList<>();
        mQuestList.add(0,"Детские страхи");
        mQuestList.add(1,"Частный детектив");
        mQuestList.add(2,"Блок смертников");

        initView(view);

        return view;
    }

    private void initView(View view){
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mQuestAdapter = new QuestAdapter(mQuestList, getContext());
        mRecyclerView.setAdapter(mQuestAdapter);
    }

}
