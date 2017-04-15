package zone.iquestadmin.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import fr.tvbarthel.lib.blurdialogfragment.SupportBlurDialogFragment;
import zone.iquestadmin.Provider.VolleyRequest;
import zone.iquestadmin.R;


public class StatusDialogFragment extends SupportBlurDialogFragment implements View.OnClickListener {

    private static final String ARG_QUEST_DATE = "quest_date";
    private static final String ARG_QUEST_ID = "quest_id";
    private static final String ARG_QUEST_USER = "quest_user";
    private static final String ARG_QUEST_SEANCE = "quest_seance";
    private String mName;
    private String mDate;
    private int mSeanceId;
    private int mQuestId;

    private Button mButtonOk;
    private Button mButtonCancel;
    private VolleyRequest mVolleyRequest;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Bundle bundle = getArguments();

        mDate = bundle.getString(ARG_QUEST_DATE);
        mSeanceId = bundle.getInt(ARG_QUEST_SEANCE);
        mQuestId = bundle.getInt(ARG_QUEST_ID);
        mName = bundle.getString(ARG_QUEST_USER);

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_status, null);

        mButtonOk = (Button) view.findViewById(R.id.btnOk);
        mButtonCancel = (Button) view.findViewById(R.id.btnCancel);

        mButtonOk.setOnClickListener(this);
        mButtonCancel.setOnClickListener(this);

        mVolleyRequest = new VolleyRequest();

        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onClick(View v) {
        int status = 0;
        switch (v.getId()) {
            case R.id.btnOk:
                status = 2;
                break;
            case R.id.btnCancel:
                status = 0;
                break;
        }
        mVolleyRequest.setStatus(getContext(), this, status, mDate, mQuestId, mSeanceId, mName);

    }
}
