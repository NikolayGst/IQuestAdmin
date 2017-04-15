package zone.iquestadmin.Provider;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zone.iquestadmin.Fragment.CalendarFragment;
import zone.iquestadmin.Fragment.StatusDialogFragment;
import zone.iquestadmin.Fragment.StatusFragment;
import zone.iquestadmin.Model.Client;
import zone.iquestadmin.Model.Seance;
import zone.iquestadmin.Utils.AppConfig;


public class VolleyRequest {

    private static final String TAG = VolleyRequest.class.getSimpleName();
    private ProgressDialog pDialog;

    public void getDay(final Context context, final int questId, final List<HashMap<String, String>> listDate) {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Загрузка данных ...");
        pDialog.setCancelable(false);
        String str_tag = "str_tag";
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                AppConfig.urlGetDay, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArrayQuestDay = new JSONArray(response);
                    for (int i = 0; i < jsonArrayQuestDay.length(); i++) {
                        JSONObject jsonObjQuestDay = jsonArrayQuestDay.getJSONObject(i);
                        JSONObject quest = jsonObjQuestDay.getJSONObject("quest");
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("mDate", quest.getString("date"));
                        hashMap.put("mStatus", quest.getString("status"));
                        listDate.add(hashMap);
                    }
                    hideDialog();
                    CalendarFragment.getInstance().setDate();
                } catch (JSONException e) {
                    Log.d(TAG, "JSONException: " + e.getMessage());
                    hideDialog();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "" + error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("quest_id", String.valueOf(questId));
                return params;
            }
        };


        //тайм аут для запроса.
        timeout(stringRequest, 10000);

        AppController.getInstance().addToRequestQueue(stringRequest, str_tag);
    }

    public void getStatus(final Context context, final int questId,
                          final String date, final List<Seance> seanceList, final RecyclerView recyclerView) {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Загрузка данных ...");
        pDialog.setCancelable(false);
        String str_tag = "str_tag";
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                AppConfig.urlGetQuestList, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArrayQuestDay = new JSONArray(response);
                    for (int i = 0; i < jsonArrayQuestDay.length(); i++) {
                        Seance seance = new Seance();
                        JSONObject jsonObjQuestDay = jsonArrayQuestDay.getJSONObject(i);
                        JSONObject quest = jsonObjQuestDay.getJSONObject("quest");
                        int seanceId = quest.getInt("seance_id");
                        int status = quest.getInt("status");
                        seance.setSeanceId(seanceId);
                        seance.setStatus(status);
                        seanceList.set(seanceId - 1, seance);
                    }

                    hideDialog();
                    recyclerView.setVisibility(View.VISIBLE);
                } catch (JSONException e) {
                    Log.d(TAG, "JSONException: " + e.getMessage());
                    hideDialog();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "" + error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("date", date);
                params.put("quest_id", String.valueOf(questId));
                return params;
            }
        };

        //тайм аут для запроса.
        timeout(stringRequest, 10000);

        AppController.getInstance().addToRequestQueue(stringRequest, str_tag);
    }

    public void getClient(final Context context, final String date, final int questId, final int seanceId) {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Загрузка данных ...");
        pDialog.setCancelable(false);
        String str_tag = "str_tag";
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                AppConfig.urlGetClient, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Client client = new Client();
                    JSONObject jsonObjectClient = new JSONObject(response);
                    JSONObject jsonClient = jsonObjectClient.getJSONObject("client");
                    client.setName(jsonClient.getString("name"));
                    client.setEmail(jsonClient.getString("email"));
                    client.setTelephone(jsonClient.getString("telephone"));
                    hideDialog();
                    StatusFragment.getInstance().setData(client);
                } catch (JSONException e) {
                    Log.d("tag", "JSONException: " + e.getMessage());
                    hideDialog();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "" + error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("date", date);
                params.put("quest_id", String.valueOf(questId));
                params.put("seance_id", String.valueOf(seanceId));
                return params;
            }
        };


        //тайм аут для запроса.
        timeout(stringRequest, 10000);

        AppController.getInstance().addToRequestQueue(stringRequest, str_tag);
    }

    public void setStatus(final Context context, final StatusDialogFragment dialogFragment, final int status, final String date, final int questId, final int seanceId, final String name) {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Смена статуса ...");
        pDialog.setCancelable(false);
        String str_tag = "str_tag";
        showDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                AppConfig.urlSetStatus, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObjectClient = new JSONObject(response);
                    String res = jsonObjectClient.getString("status");
                    if (res.equals("Successfully")){
                        dialogFragment.dismiss();
                        Toast.makeText(context, "Статус успешно изменен", Toast.LENGTH_LONG).show();
                    }else {
                        dialogFragment.dismiss();
                        Toast.makeText(context, "Заказ уже оформлен", Toast.LENGTH_LONG).show();
                    }
                    hideDialog();
                } catch (JSONException e) {
                    Log.d("tag", "JSONException: " + e.getMessage());
                    hideDialog();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "" + error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("status", String.valueOf(status));
                params.put("date", date);
                params.put("quest_id", String.valueOf(questId));
                params.put("seance_id", String.valueOf(seanceId));
                params.put("name", name);
                return params;
            }
        };


        //тайм аут для запроса.
        timeout(stringRequest, 10000);

        AppController.getInstance().addToRequestQueue(stringRequest, str_tag);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public void timeout(Request request, final int timeOut) {
        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return timeOut;
            }

            @Override
            public int getCurrentRetryCount() {
                return timeOut;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
    }

}
