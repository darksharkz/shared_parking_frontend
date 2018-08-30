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
    /** Base URL for the Shared Parking REST API Service */
    public static final String BASE_URL = "http://localhost:777/shared_parking";

    /** URI for signup service */
    public static final String REGISTER_URI = BASE_URL + "/user/register";
    /** URI for login service */
    public static final String LOGIN_URI = BASE_URL + "/user/login";
    /** URI for all other services */
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
    public static final String GET_PARKINGTRADESASLANDLORD_URI = BASE_URL + "/parkingtrade/getbyuseraslandlord";
    public static final String GET_PARKINGTRADESASTENANT_URI = BASE_URL + "/parkingtrade/getbyuserastenant";
    public static final String GET_CAR_URI = BASE_URL + "/car/getbyuser";
    public static final String GET_CARACTIVE_URI = BASE_URL + "/car/getactivebyuser";
    public static final String CREATE_CAR_URI = BASE_URL + "/car/create";
    public static final String EDIT_CAR_URI = BASE_URL + "/car/edit";
    public static final String DELETE_CAR_URI = BASE_URL + "/car/delete";

    /** The tag used to log to adb console. */
    private static final String TAG = "NetworkUtilities";

    private NetworkUtilities() {
    }

    public static void makeRequest(final String httpUri, HashMap<String, Object> params, Context context, final ServerCallback serverCallback){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, httpUri, new JSONObject(params), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(TAG, "JSON Response:" + response);
                        serverCallback.onSuccess(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error with volley while " + httpUri + ":" + error);
                        serverCallback.onFailure(error);
                    }
                });

        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public static void register(String mail, String firstname, String lastname, String password, Context context, final ServerCallback serverCallback){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("mail", mail);
        params.put("firstname", firstname);
        params.put("lastname", lastname);
        params.put("password", password);

        makeRequest(REGISTER_URI, params, context, serverCallback);
    }

    public static void login(String mail, String password, Context context, final ServerCallback serverCallback){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("mail", mail);
        params.put("password", password);

        makeRequest(LOGIN_URI, params, context, serverCallback);
    }

    public static void getParkingSpacebyUser(String auth_token, Context context, final ServerCallback serverCallback){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("auth_token", auth_token);

        makeRequest(GET_PARKINGSPACE_URI, params, context, serverCallback);
    }

    public static void getParkingOffersbyUser(String auth_token, Context context, final ServerCallback serverCallback){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("auth_token", auth_token);

        makeRequest(GET_PARKINGOFFERBYUSER_URI, params, context, serverCallback);
    }

    public static void getParkingOffersbyTime(String auth_token, String start_dt, String end_dt, Context context, final ServerCallback serverCallback){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("auth_token", auth_token);
        params.put("start_dt", start_dt);
        params.put("end_dt", end_dt);

        makeRequest(GET_PARKINGOFFERBYTIME_URI, params, context, serverCallback);
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

        makeRequest(CREATE_PARKINGSPACE_URI, params, context, serverCallback);
    }

    public static void createParkingOffer(String auth_token, int parkingspaceid, int price, String start_dt, String end_dt, Context context, final ServerCallback serverCallback){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("auth_token", auth_token);
        params.put("parkingspaceid", parkingspaceid);
        params.put("price", price);
        params.put("start_dt", start_dt);
        params.put("end_dt", end_dt);

        makeRequest(CREATE_PARKINGOFFER_URI, params, context, serverCallback);
    }

    public static void createParkingTrade(String auth_token, int parkingspaceid, int price, String start_dt, String end_dt, int userid, Context context, final ServerCallback serverCallback){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("auth_token", auth_token);
        params.put("parkingspaceid", parkingspaceid);
        params.put("price", price);
        params.put("start_dt", start_dt);
        params.put("end_dt", end_dt);
        params.put("userid", userid);

        makeRequest(CREATE_PARKINGTRADE_URI, params, context, serverCallback);
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

        makeRequest(EDIT_PARKINGSPACE_URI, params, context, serverCallback);
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

        makeRequest(EDIT_PARKINGOFFER_URI, params, context, serverCallback);
    }

    public static void deleteParkingSpace(int ID, String auth_token, Context context, final ServerCallback serverCallback){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("ID", ID);
        params.put("auth_token", auth_token);
        Log.e(TAG, "JSON Response:" + new JSONObject(params));

        makeRequest(DELETE_PARKINGSPACE_URI, params, context, serverCallback);
    }

    public static void deleteParkingOffer(int ID, String auth_token, Context context, final ServerCallback serverCallback){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("ID", ID);
        params.put("auth_token", auth_token);

        makeRequest(DELETE_PARKINGOFFER_URI, params, context, serverCallback);
    }

    public static void getUser(String auth_token, Context context, final ServerCallback serverCallback){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("auth_token", auth_token);

        makeRequest(GET_USER_URI, params, context, serverCallback);
    }

    public static void setBalance(int userid, int change, Context context, final ServerCallback serverCallback){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("userid", userid);
        params.put("change", change);

        makeRequest(SET_BALANCE_URI, params, context, serverCallback);
    }

    public static void getParkingTradesAsLandlord(String auth_token, Context context, final ServerCallback serverCallback){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("auth_token", auth_token);

        makeRequest(GET_PARKINGTRADESASLANDLORD_URI, params, context, serverCallback);
    }

    public static void getParkingTradesAsTenant(String auth_token, Context context, final ServerCallback serverCallback){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("auth_token", auth_token);

        makeRequest(GET_PARKINGTRADESASTENANT_URI, params, context, serverCallback);
    }

    public static void getCar(String auth_token, Context context, final ServerCallback serverCallback){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("auth_token", auth_token);

        makeRequest(GET_CAR_URI, params, context, serverCallback);
    }

    public static void getCarActive(String auth_token, Context context, final ServerCallback serverCallback){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("auth_token", auth_token);

        makeRequest(GET_CARACTIVE_URI, params, context, serverCallback);
    }

    public static void deleteCar(int ID, String auth_token, Context context, final ServerCallback serverCallback){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("ID", ID);
        params.put("auth_token", auth_token);

        makeRequest(DELETE_CAR_URI, params, context, serverCallback);
    }

    public static void editCar(int ID, String auth_token, String licenseplate, Context context, final ServerCallback serverCallback){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("ID", ID);
        params.put("auth_token", auth_token);
        params.put("licenseplate", licenseplate);

        makeRequest(EDIT_CAR_URI, params, context, serverCallback);
    }

    public static void createCar(String auth_token, String licenseplate, Context context, final ServerCallback serverCallback){
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("auth_token", auth_token);
        params.put("licenseplate", licenseplate);

        makeRequest(CREATE_CAR_URI, params, context, serverCallback);
    }

}
