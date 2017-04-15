package zone.iquestadmin.Fragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import zone.iquestadmin.Model.Client;
import zone.iquestadmin.Provider.VolleyRequest;
import zone.iquestadmin.R;
import zone.iquestadmin.Utils.FormatDate;
import zone.iquestadmin.Utils.FormatSeance;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatusFragment extends Fragment {

    private static final String ARG_QUEST_DATE = "quest_date";
    private static final String ARG_QUEST_ID = "quest_id";
    private static final String ARG_QUEST = "quest";
    private static final String ARG_QUEST_USER = "quest_user";
    private static final String ARG_QUEST_SEANCE = "quest_seance";
    private static final String TAG = StatusFragment.class.getSimpleName();

    private TextView mTxtDate;
    private TextView mTxtName;
    private TextView mTxtEmail;
    private TextView mTxtSeance;
    private TextView mTxtTel;
    private ImageView mImgTel;
    private Button mButtonStatus;
    private String mName;
    private String mUsername;
    private String mEmail;
    private String mTelephone;
    private String mDate;
    private int mSeanceId;
    private int mQuestId;
    private VolleyRequest mVolleyRequest;
    private static StatusFragment instance;

    public StatusFragment() {

    }

    public static StatusFragment getInstance() {
        return instance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();

        instance = this;

        mDate = bundle.getString(ARG_QUEST_DATE);
        mName = bundle.getString(ARG_QUEST);
        mSeanceId = bundle.getInt(ARG_QUEST_SEANCE);
        mQuestId = bundle.getInt(ARG_QUEST_ID);

        View view = inflater.inflate(R.layout.fragment_status, container, false);

        initView(view);

        mTxtDate.setText(FormatDate.format(mDate));

        mTxtSeance.setText(mSeanceId
                + " сеанс на "
                + FormatSeance.format(mSeanceId, mQuestId)
                + "\n" + mName);

        mVolleyRequest = new VolleyRequest();

        mVolleyRequest.getClient(getContext(), mDate, mQuestId, mSeanceId);

        mImgTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tel = mTxtTel.getText().toString();
                telByNumberPhone(tel);
            }
        });

        mButtonStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        return view;
    }

    private void initView(View view) {
        mTxtDate = (TextView) view.findViewById(R.id.txtDate);
        mTxtName = (TextView) view.findViewById(R.id.nameClient);
        mTxtEmail = (TextView) view.findViewById(R.id.email);
        mTxtTel = (TextView) view.findViewById(R.id.telephone);
        mImgTel = (ImageView) view.findViewById(R.id.imgCall);
        mTxtSeance = (TextView) view.findViewById(R.id.txtSeance);
        mButtonStatus = (Button) view.findViewById(R.id.btnStatus);
    }


    public void setData(Client client) {
        mUsername = client.getName();
        mEmail = client.getEmail();
        mTelephone = client.getTelephone();
        mTxtName.setText("ФИО: " + mUsername);
        mTxtEmail.setText("Email: " + mEmail);
        mTxtTel.setText("Телефон: " + mTelephone);
    }

    public void telByNumberPhone(String telephone) {
        Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + telephone));
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(dialIntent);
    }

    /*@Override
    public void onStop() {
        super.onStop();
        instance = null;
        Log.d(TAG, "onStop: ");
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        instance = null;
        Log.d(TAG, "onDestroyView: ");
    }

    private void showDialog() {
        Bundle bundle = new Bundle();
        final StatusDialogFragment statusDialogFragment = new StatusDialogFragment();
        bundle.putInt(ARG_QUEST_SEANCE, mSeanceId);
        bundle.putInt(ARG_QUEST_ID, mQuestId);
        bundle.putString(ARG_QUEST_DATE, mDate);
        bundle.putString(ARG_QUEST_USER, mUsername);
        statusDialogFragment.setArguments(bundle);
        statusDialogFragment.show(getActivity().getSupportFragmentManager(), "fragment_status");
    }
}
