package com.riverincloud.androidproficiencyexercise;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Di on 1/04/2015.
 *
 * The singleton class encapsulates Volley's RequestQueue and ImageLoader functionality.
 * The RequestQueue and ImageLoader would be instantiated with the Application context,
 * therefore last for the lifetime of the app.
 */
public class MySingleton {

    private static MySingleton mSingleton;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static Context mContext;

    private MySingleton(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();
        mImageLoader = new ImageLoader(mRequestQueue,
                new MyLruBitmapCache(MyLruBitmapCache.getCacheSize(context)));
    }

    public static synchronized MySingleton getMySingleton(Context context) {
        if (mSingleton == null) {
            mSingleton = new MySingleton(context);
        }
        return mSingleton;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}
