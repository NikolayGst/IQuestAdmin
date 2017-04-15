package zone.iquestadmin.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import zone.iquestadmin.Fragment.StatusFragment;
import zone.iquestadmin.Model.Seance;
import zone.iquestadmin.R;


public class SeanceAdapter extends RecyclerView.Adapter<SeanceAdapter.ViewHolder> {

    private static final String ARG_QUEST_DATE = "quest_date";
    private static final String ARG_QUEST_ID = "quest_id";
    private static final String ARG_QUEST = "quest";
    private static final String ARG_QUEST_SEANCE = "quest_seance";

    private List<Seance> mSeanceList;
    private Context mContext;
    private Seance mSeance;
    private FragmentManager mFragmentManager;
    private StatusFragment mStatusFragment;
    private int mSeanceId;
    private int mStatus;
    private String mQuestName;
    private int mQuestId;
    private String mDate;

    public SeanceAdapter(List<Seance> seanceList, Context context, FragmentManager fm, String questName, int questId, String questDate) {
        this.mSeanceList = seanceList;
        this.mContext = context;
        this.mFragmentManager = fm;
        this.mQuestName = questName;
        this.mQuestId = questId;
        this.mDate = questDate;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.seance_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        mSeance = mSeanceList.get(position);
        mSeanceId = mSeance.getSeanceId();
        mStatus = mSeance.getStatus();
        holder.txtSeance.setText(mSeanceId + " сеанс");
        holder.txtStatus.setText(getStatus(mStatus));
        holder.cardView.setCardBackgroundColor(getColorStatus(mStatus, holder.txtSeance,
                holder.txtStatus));

        holder.rltSeance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSeance = mSeanceList.get(position);
                if (mSeance.getStatus() == 1 || mSeance.getStatus() == 2) {
                    Bundle bundle = new Bundle();
                    mStatusFragment = new StatusFragment();
                    bundle.putString(ARG_QUEST, mQuestName);
                    bundle.putString(ARG_QUEST_DATE, mDate);
                    bundle.putInt(ARG_QUEST_ID, mQuestId);
                    bundle.putInt(ARG_QUEST_SEANCE, mSeance.getSeanceId());
                    mStatusFragment.setArguments(bundle);
                    mFragmentManager
                            .beginTransaction()
                            .replace(R.id.container, mStatusFragment)
                            .addToBackStack("quest")
                            .commit();
                }
            }


        });
    }

    @Override
    public int getItemCount() {
        return mSeanceList.size();
    }

    private String getStatus(int status) {
        String stringStatus = "";
        switch (status) {
            case 0:
                stringStatus = "Свободно";
                break;
            case 1:
                stringStatus = "В ожидании";
                break;
            case 2:
                stringStatus = "Забронировано";
                break;
        }
        return stringStatus;
    }

    private int getColorStatus(int status, TextView txtSeance, TextView txtStatus) {
        int colorStatus = 0;
        switch (status) {
            case 0:
                colorStatus = Color.WHITE;
                break;
            case 1:
                colorStatus = Color.parseColor("#FFFFC400");
                break;
            case 2:
                colorStatus = Color.parseColor("#ff5252");
                txtSeance.setTextColor(Color.WHITE);
                txtStatus.setTextColor(Color.WHITE);
                break;
        }
        return colorStatus;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtSeance;
        TextView txtStatus;
        CardView cardView;
        RelativeLayout rltSeance;

        public ViewHolder(View itemView) {
            super(itemView);
            txtSeance = (TextView) itemView.findViewById(R.id.txtSeance);
            txtStatus = (TextView) itemView.findViewById(R.id.txtStatus);
            cardView = (CardView) itemView.findViewById(R.id.card);
            rltSeance = (RelativeLayout) itemView.findViewById(R.id.rltSeance);
        }
    }
}
