package com.example.andy.prototype.app;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.io.InputStream;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();

    private RequestQueue mRequestQueue;

    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            /* Volley is a new technique in AS that enables the dev to create a new queue of events.
             * This isn't important now but its an important optimisation technique that will
             * stop the application freezing and bottoming out.
             * TODO fine tune emulator to show phone characteristics
             */
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    /* This is the method that allows the dev to define the tag for the specific request.
        Allows for cancellation of specific tags, findable by their strings. Essential for
        logging and registration. Phone-side. Not server.
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    /* Inital method for request queue addition.
       This is for when the developer doesn't want to delete the request.
       TODO Find out if still relevant.
    */

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    // Cancels all the tags.
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }



    // TODO build cancelSpecificRequest
}
