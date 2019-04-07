package pjl.jeans.retrofitbasicauth;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    private OkHttpClient client;
    private Call call;
ArrayList<VersionBean> versionListt;
    List<String> categoriesw2 = new ArrayList<String>();
    private Spinner coupnCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instantiateOkHttpClient();

        //spinner to be populated with coupon categories from rest service
        coupnCategories = (Spinner) findViewById(R.id.cpn_cats_spn);


    }
    @Override
    public void onStart(){
        super.onStart();

        if(client == null){
            instantiateOkHttpClient();
        }
        //get response from service and populate ui in background
        new MainActivity.OkHttpAyncTask().execute(this);
    }
    @Override
    public void onStop(){
        super.onStop();
        //cancel okhttp request call if its not complete
        call.cancel();
    }
    public void instantiateOkHttpClient(){
        //create cache object passing cache dir
        int cacheSize = 25 * 1024 * 1024;
        Cache cache = new Cache(getCacheDir(), cacheSize);

        //OkHttpClient instance with connection, read and write time out settings
        //add cache
        //add authenticator
        client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(28, TimeUnit.SECONDS)
                .cache(cache)
                .authenticator(new ZoftinoAuthenticator())
                .addInterceptor(new LoggingInterceptor())
                .build();
    }
    //okhttp authenticator
    class ZoftinoAuthenticator implements Authenticator{
        //gets called if server responds with 401 http response to retry authentication
        @Override public Request authenticate(Route route, Response response) throws IOException {
            String credential = Credentials.basic("admin", "password");

            //prevent infinite loop , if same credentials already tired, no need to try again
            if (credential.equals(response.request().header("Authorization"))) {
                return null;
            }

            return response.request().newBuilder()
                    .header("Authorization", credential)
                    .build();
        }
    }
    //okhttp intercepter logs time taken to get response from rest service using Okhttp
    class LoggingInterceptor implements Interceptor {
        @Override public Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();

            long startTime = System.nanoTime();
            Log.i("",request.url()+" start time :"+startTime);
            Log.i("" ,""+request.header("Authorization"));

            Response response = chain.proceed(request);

            long endTime = System.nanoTime();
            Log.i("",request.url()+" time taken to process :"+(endTime -startTime));

            return response;
        }
    }
    private List<Android_Version> getCouponCategories() throws JSONException {
        //get json response from service
        String jsonStr = callCouponCategoriesService();
        //convert json response to list

        JSONObject obj = new JSONObject(jsonStr);
       // String pageName = obj.getJSONObject("pageInfo").getString("pageName");

       // System.out.println(pageName);

        JSONArray arr = obj.getJSONArray("Android Version List");
        List<Android_Version> categoriess=new ArrayList<Android_Version>();
        for (int i = 0; i < arr.length(); i++) {
            Android_Version n =new Android_Version();
            String post_id = arr.getJSONObject(i).getString("Version No");
            String post_id2 = arr.getJSONObject(i).getString("Version Name");
            String post_id3 = arr.getJSONObject(i).getString("API Level");
            n.setVersion_No(post_id);
            n.setVersion_Name(post_id2);
            n.setAPI_Level(post_id3);
            //System.out.println(post_id+"-"+post_id2+"-"+post_id3);
            categoriess.add(n);
            //System.out.println();
        }

        for(Android_Version s:categoriess)
        {
          //  System.out.println(s.getVersion_No());
        }


       // List<Android_Version> categories = convertJsonStringToList(jsonStr);
        return categoriess;
    }
    public String callCouponCategoriesService(){
        String url = "http://serviceapi.skholingua.com/secured-feeds/list_multipletext_json.php";
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization" , Credentials.basic("admin", "password"))
                //.cacheControl(CacheControl.FORCE_CACHE)
                .build();

        try {
            Log.d(TAG, "okhttp request created, calling service");
            call = client.newCall(request);
            Response response = call.execute();

            //Log.i("",response.body().string());
            return response.body().string();

        } catch (IOException e) {
            Log.e(TAG, "error in getting response get request okhttp");
        }
        return null;
    }
    private List<Android_Version> convertJsonStringToList(String jsonStr){
        Log.d(TAG, "got categories from rest service");
        //instantiate Gson
        final Gson gson = new Gson();

        //pass input stream and to-type (List<String>) to fromjson
        Type listType = new TypeToken<List<String>>(){}.getType();

        List<Android_Version> categories = gson.fromJson(jsonStr, listType);
        Log.d(TAG, "json parsing is complete");
        return categories;
    }
    private class OkHttpAyncTask extends AsyncTask<Object, Void, List<Android_Version>> {

        private String TAG = MainActivity.OkHttpAyncTask.class.getSimpleName();
        private Context contx;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Android_Version> doInBackground(Object... params) {
            contx = (Context) params[0];
            Log.e(TAG, "processing http request in async task using OkHttp");
            try {
                List<Android_Version> n =getCouponCategories();
                for (Android_Version s:n)
                {
                    System.out.println("Hello"+s.getVersion_No()+"-Bela24411-");
                    categoriesw2.add(s.getVersion_Name());
                }
                return n;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Android_Version> result) {
            super.onPostExecute(result);

            if (result != null) {
                Log.e(TAG, "populate spinner in UI after response from service using OkHttp");
                //populate spinner
                List<String> categories = new ArrayList<String>();
                categories.add("Automobile");
                categories.add("Business Services");
                categories.add("Computers");
                categories.add("Education");
                categories.add("Personal");
                categories.add("Travel");
                List<String> categoriesw = new ArrayList<String>();
                ArrayAdapter<CharSequence> adapter = new ArrayAdapter(contx,
                        android.R.layout.simple_spinner_item, categoriesw2);
                adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

                coupnCategories.setAdapter(adapter);
            }
        }
    }

}

