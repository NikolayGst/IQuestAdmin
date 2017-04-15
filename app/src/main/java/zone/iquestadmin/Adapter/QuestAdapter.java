package zone.iquestadmin.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import zone.iquestadmin.Fragment.CalendarFragment;
import zone.iquestadmin.MainActivity;
import zone.iquestadmin.R;


public class QuestAdapter extends RecyclerView.Adapter<QuestAdapter.ViewHolder> {
    private static final String ARG_QUEST = "quest";
    private static final String ARG_QUEST_ID = "quest_id";
    private List<String> mQuestList;
    private Context mContext;
    private CalendarFragment mCalendarFragment;

    public QuestAdapter(List<String> questList, Context context) {
        mQuestList = questList;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.quest_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.mTitleQuest.setText(mQuestList.get(position));

        holder.mImageQuest.setImageResource(setImage(position));

        holder.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();

                bundle.putString(ARG_QUEST, holder.mTitleQuest.getText().toString());
                int questId = position + 1;
                bundle.putInt(ARG_QUEST_ID, questId);

                mCalendarFragment = new CalendarFragment();
                mCalendarFragment.setArguments(bundle);

                ((MainActivity) mContext).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, mCalendarFragment)
                        .addToBackStack("quest")
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mQuestList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageQuest;
        TextView mTitleQuest;
        RelativeLayout mRelativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageQuest = (ImageView) itemView.findViewById(R.id.thumbnail);
            mTitleQuest = (TextView) itemView.findViewById(R.id.titleQuest);
            mRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.rltQuest);
        }
    }

    private int setImage(int pos) {
        int resId = 0;
        switch (pos) {
            case 0:
                resId = R.drawable.ds;
                break;
            case 1:
                resId = R.drawable.cd;
                break;
            case 2:
                resId = R.drawable.bc;
                break;
        }
        return resId;
    }
}
