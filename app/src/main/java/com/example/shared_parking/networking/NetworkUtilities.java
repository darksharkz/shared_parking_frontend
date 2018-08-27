package com.example.shared_parking.networking;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import java.util.HashMap;


/**
 * Provides utility methods for communicating with the server.
 */
final public class NetworkUtilities {
    /** The tag used to log to adb console. */
    private static final String TAG = "NetworkUtilities";
    /** POST parameter name for the user's account name */
    public static final String PARAM_USERNAME = "username";
    /** POST parameter name for the user's password */
    public static final String PARAM_PASSWORD = "password";
    /** POST parameter name for the user's authentication token */
    public static final String PARAM_AUTH_TOKEN = "authtoken";
    /** POST parameter name for the client's last-known sync state */
    public static final String PARAM_SYNC_STATE = "syncstate";
    /** POST parameter name for the sending client-edited contact info */
    public static final String PARAM_CONTACTS_DATA = "contacts";
    /** Timeout (in ms) we specify for each http request */
    public static final int HTTP_REQUEST_TIMEOUT_MS = 30 * 1000;
    /** Base URL for the Shared Parking REST API Service */
    public static final String BASE_URL = "http://betreffilada.de:777/shared_parking";
    /** URI for signup service */
    public static final String REGISTER_URI = BASE_URL + "/register/doregister";
    /** URI for login service */
    public static final String LOGIN_URI = BASE_URL + "/login/dologin";
    public static final String GET_PARKINGSPACE_URI = BASE_URL + "/parkingspace/getbyuser";
    public static final String CREATE_PARKINGSPACE_URI = BASE_URL + "/parkingspace/create";
    public static final String EDIT_PARKINGSPACE_URI = BASE_URL + "/parkingspace/edit";
    public static final String DELETE_PARKINGSPACE_URI = BASE_URL + "/parkingspace/delete";
    public static final String GET_USER_URI = BASE_URL + "/user/get";
    public static final String SET_BALANCE_URI = BASE_URL + "/user/setbalance";
    public static final String GET_PARKINGOFFERBYUSER_URI = BASE_URL + "/parkingoffer/getbyuser";
    public static final String GET_PARKINGOFFERBYTIME_URI = BASE_URL + "/parkingoffer/getbytime";
    public static final String CREATE_PARKINGOFFER_URI = BASE_URL + "/parkingoffer/create";
    public static final String EDIT_PARKINGOFFER_URI = BASE_URL + "/parkingoffer/edit";
    public static final String DELETE_PARKINGOFFER_URI = BASE_URL + "/parkingoffer/delete";
    public static final String CREATE_PARKINGTRADE_URI = BASE_URL + "/parkingtrade/create";

    private NetworkUtilities() {
    }

    public static void register(String mail, String firstname, String lastname, String password, Context context, final ServerCallback serverCallback){

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("mail", mail);
        params.put("firstname", firstname);
        params.put("lastname", lastname);
        params.put("password", password);
        //String url = REGISTER_URI + "?name=" + lastName + "&username=" + mail + "&password=" + password;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, REGISTER_URI, new JSONObject(params), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "JSON Response:" + response);
                        serverCallback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error with volley while registering" + error);
                        serverCallback.onFailure(error);
                    }
                });

        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public static void login(String mail, String password, Context context, final ServerCallback serverCallback){

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("mail", mail);
        params.put("password", password);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, LOGIN_URI, new JSONObject(params), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "JSON Response:" + response);
                        serverCallback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error with volley while registering" + error);
                        serverCallback.onFailure(error);
                    }
                });

        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public static void getParkingSpacebyUser(String auth_token, Context context, final ServerCallback serverCallback){

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("auth_token", auth_token);
        //String url = REGISTER_URI + "?name=" + lastName + "&username=" + mail + "&password=" + password;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, GET_PARKINGSPACE_URI, new JSONObject(params), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "JSON Response:" + response);
                        serverCallback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error with volley while registering" + error);
                        serverCallback.onFailure(error);
                    }
                });

        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public static void getParkingOffersbyUser(String auth_token, Context context, final ServerCallback serverCallback){
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("auth_token", auth_token);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, GET_PARKINGOFFERBYUSER_URI, new JSONObject(params), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "JSON Response:" + response);
                        serverCallback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error with volley while registering" + error);
                        serverCallback.onFailure(error);
                    }
                });

        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public static void getParkingOffersbyTime(String auth_token, String start_dt, String end_dt, Context context, final ServerCallback serverCallback){
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("auth_token", auth_token);
        params.put("start_dt", start_dt);
        params.put("end_dt", end_dt);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, GET_PARKINGOFFERBYTIME_URI, new JSONObject(params), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "JSON Response:" + response);
                        serverCallback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error with volley while registering" + error);
                        serverCallback.onFailure(error);
                    }
                });

        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public static void createParkingSpace(String auth_token, String city, String street, int postcode, int number, double lat, double lng, Context context, final ServerCallback serverCallback){

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("auth_token", auth_token);
        params.put("city", city);
        params.put("street", street);
        params.put("postcode", postcode);
        params.put("number", number);
        params.put("lat", lat);
        params.put("lng", lng);
        Log.e(TAG, "JSON Response:" + new JSONObject(params));
        //String url = REGISTER_URI + "?name=" + lastName + "&username=" + mail + "&password=" + password;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, CREATE_PARKINGSPACE_URI, new JSONObject(params), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "JSON Response:" + response);
                        serverCallback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error with volley while registering" + error);
                        serverCallback.onFailure(error);
                    }
                });

        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public static void createParkingOffer(String auth_token, int parkingspaceid, int price, String start_dt, String end_dt, Context context, final ServerCallback serverCallback){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("auth_token", auth_token);
        params.put("parkingspaceid", parkingspaceid);
        params.put("price", price);
        params.put("start_dt", start_dt);
        params.put("end_dt", end_dt);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, CREATE_PARKINGOFFER_URI, new JSONObject(params), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "JSON Response:" + response);
                        serverCallback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error with volley while registering" + error);
                        serverCallback.onFailure(error);
                    }
                });

        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public static void createParkingTrade(String auth_token, int parkingspaceid, int price, String start_dt, String end_dt, int userid, Context context, final ServerCallback serverCallback){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("auth_token", auth_token);
        params.put("parkingspaceid", parkingspaceid);
        params.put("price", price);
        params.put("start_dt", start_dt);
        params.put("end_dt", end_dt);
        params.put("userid", userid);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, CREATE_PARKINGTRADE_URI, new JSONObject(params), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "JSON Response:" + response);
                        serverCallback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error with volley while creating parkingtrade" + error);
                        serverCallback.onFailure(error);
                    }
                });

        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public static void editParkingSpace(int ID, String auth_token, String city, String street, int postcode, int number, double lat, double lng, Context context, final ServerCallback serverCallback){

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("ID", ID);
        params.put("auth_token", auth_token);
        params.put("city", city);
        params.put("street", street);
        params.put("postcode", postcode);
        params.put("number", number);
        params.put("lat", lat);
        params.put("lng", lng);
        Log.e(TAG, "JSON Response:" + new JSONObject(params));
        //String url = REGISTER_URI + "?name=" + lastName + "&username=" + mail + "&password=" + password;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, EDIT_PARKINGSPACE_URI, new JSONObject(params), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "JSON Response:" + response);
                        serverCallback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error with volley while editing parkingspace" + error);
                        serverCallback.onFailure(error);
                    }
                });

        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public static void editParkingOffer(int ID, String auth_token, int parkingspaceid, int price, String start_dt, String end_dt, Context context, final ServerCallback serverCallback){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("ID", ID);
        params.put("auth_token", auth_token);
        params.put("parkingspaceid", parkingspaceid);
        params.put("price", price);
        params.put("start_dt", start_dt);
        params.put("end_dt", end_dt);
        Log.e(TAG, "JSON request:" + params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, EDIT_PARKINGOFFER_URI, new JSONObject(params), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "JSON Response:" + response);
                        serverCallback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error with volley while editing parkingspace" + error);
                        serverCallback.onFailure(error);
                    }
                });

        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public static void deleteParkingSpace(int ID, String auth_token, Context context, final ServerCallback serverCallback){

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("ID", ID);
        params.put("auth_token", auth_token);
        Log.e(TAG, "JSON Response:" + new JSONObject(params));
        //String url = REGISTER_URI + "?name=" + lastName + "&username=" + mail + "&password=" + password;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, DELETE_PARKINGSPACE_URI, new JSONObject(params), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "JSON Response:" + response);
                        serverCallback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error with volley while deleting parkingspace" + error);
                        serverCallback.onFailure(error);
                    }
                });

        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public static void deleteParkingOffer(int ID, String auth_token, Context context, final ServerCallback serverCallback){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("ID", ID);
        params.put("auth_token", auth_token);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, DELETE_PARKINGOFFER_URI, new JSONObject(params), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "JSON Response:" + response);
                        serverCallback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error with volley while deleting parkingspace" + error);
                        serverCallback.onFailure(error);
                    }
                });

        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public static void getUser(String auth_token, Context context, final ServerCallback serverCallback){

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("auth_token", auth_token);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, GET_USER_URI, new JSONObject(params), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "JSON Response:" + response);
                        serverCallback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error with volley while getting user" + error);
                        serverCallback.onFailure(error);
                    }
                });

        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public static void setBalance(int userid, int change, Context context, final ServerCallback serverCallback){

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("userid", userid);
        params.put("change", change);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, SET_BALANCE_URI, new JSONObject(params), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "JSON Response:" + response);
                        serverCallback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error with volley while getting user" + error);
                        serverCallback.onFailure(error);
                    }
                });

        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

}
