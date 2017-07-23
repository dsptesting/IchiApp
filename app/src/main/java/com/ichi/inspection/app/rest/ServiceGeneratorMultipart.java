package com.ichi.inspection.app.rest;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.ichi.inspection.app.utils.Constants;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;

import okhttp3.ConnectionSpec;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.TlsVersion;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGeneratorMultipart {

    private static ServiceGeneratorMultipart instance;

    private static ApiService mGeneralApiService;

    private static ServiceGeneratorMultipart getInstance() {
        if (instance == null) {
            instance = new ServiceGeneratorMultipart();
        }
        return instance;
    }

    public static ApiService getGeneralApiService(Context context) {
        if (mGeneralApiService == null || TextUtils.isEmpty(Constants.BASE_URL)) {
            mGeneralApiService = getInstance().createService(context, ApiService.class, Constants.BASE_URL);
        }
        return mGeneralApiService;
    }

    public ServiceGeneratorMultipart() {
        // Empty
    }

    public <S> S createService(Context context, Class<S> serviceClass, String baseUrl) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(4*60 * 1000 , TimeUnit.MILLISECONDS)
                .writeTimeout(4*60 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(4*60 * 1000, TimeUnit.MILLISECONDS)
                .connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.CLEARTEXT)/*Collections.singletonList(spec)*/)
                .addInterceptor(logging)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response response = chain.proceed(chain.request());

                        // Do anything with response here
                        return response;
                    }
                })
                .build();
/*
        // Configure the interceptor to include the cookie values in the header
        RequestInterceptor interceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                if (!TextUtils.isEmpty(Constants._COPY_COOKIE)) {
                    request.addHeader("Cookie", Constants._COPY_COOKIE);
                }

                for (HttpCookie cookie : cookieManager.getCookieStore().getCookies()) {
                    String cookieValue = cookie.getName() + "=" + cookie.getValue() + "; " + "path=" + cookie.getPath() + "; " + "domain=" + cookie.getDomain();
                    request.addHeader("Cookie", cookieValue);
                }

            }

        };

        Gson gson = new GsonBuilder().registerTypeAdapterFactory(new ItemTypeAdapterFactory()).create();

        RestAdapter.LogLevel logLevel = Constants.debugging ? RestAdapter.LogLevel.HEADERS_AND_ARGS : RestAdapter.LogLevel.NONE;

        //if(!TextUtils.isEmpty(Constants._COPY_COOKIE)) {
        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS)
                .tlsVersions(TlsVersion.TLS_1_0)
                .build();
        client.setConnectionSpecs(Collections.singletonList(spec));
        //}*/

// Set endpoint url and use OkHTTP as HTTP client
        Retrofit builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                //TODO pass something here .addCallAdapterFactory()
                .client(client)
                .build();

        return builder.create(serviceClass);
    }

    private class ItemTypeAdapterFactory implements TypeAdapterFactory {

        public <T> TypeAdapter<T> create(Gson gson, final TypeToken<T> type) {

            final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
            final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);

            return new TypeAdapter<T>() {

                public void write(JsonWriter out, T value) throws IOException {
                    delegate.write(out, value);
                }

                public T read(JsonReader in) throws IOException {
                    JsonElement jsonElement = elementAdapter.read(in);
                    if (jsonElement.isJsonObject()) {
                        JsonObject jsonObject = jsonElement.getAsJsonObject();
                        if (jsonObject.has("records")) {
                            JsonArray jsonArray = jsonObject.getAsJsonArray("records");
                            if (jsonArray != null) {
                                jsonElement = jsonArray;
                            }
                        }
                    }
                    return delegate.fromJsonTree(jsonElement);
                }
            }.nullSafe();
        }
    }
}
