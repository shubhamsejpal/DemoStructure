package com.bazmashop

import android.content.Context
import android.support.multidex.MultiDexApplication
import com.google.gson.GsonBuilder
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class BaseApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        initUniversalLoader(this)
    }

    private fun initUniversalLoader(context: Context) {
        il = ImageLoader.getInstance()
        il.init(ImageLoaderConfiguration.createDefault(context))

        options = DisplayImageOptions.Builder().considerExifParams(true)
                .showImageOnLoading(R.drawable.ic_avtar)
                .showImageForEmptyUri(R.drawable.ic_avtar)
                .showImageOnFail(R.drawable.ic_avtar).cacheInMemory(true).build()

        noPlaceHolderOptions = DisplayImageOptions.Builder().considerExifParams(true).cacheInMemory(true).build()
    }

    companion object {
        private var retrofit: Retrofit? = null
        lateinit var il: ImageLoader
        lateinit var options: DisplayImageOptions
        lateinit var noPlaceHolderOptions: DisplayImageOptions
        var TOKEN = ""

        val retrofitInstance: Retrofit?
            get() {
                if (retrofit == null) {
                    val retrofitBuilder = Retrofit.Builder()
                    retrofitBuilder.baseUrl(Constants.Api.BASE_URL)
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    retrofitBuilder.addConverterFactory(GsonConverterFactory.create(gson))
                    retrofitBuilder.client(client)
                    retrofit = retrofitBuilder.build()
                }
                return retrofit
            }

        private val client: OkHttpClient
            get() {
                val TIME_OUT_VALUE = 120
                val TIME_OUT_UNIT = TimeUnit.SECONDS

                val httpClient = OkHttpClient.Builder()
                httpClient.readTimeout(TIME_OUT_VALUE.toLong(), TIME_OUT_UNIT)
                httpClient.connectTimeout(TIME_OUT_VALUE.toLong(), TIME_OUT_UNIT)
                httpClient.addInterceptor { chain ->
                    val original = chain.request()
                    val request = original.newBuilder()
                        .method(original.method(), original.body())
                        .build()

                    chain.proceed(request)
                }

                if (BuildConfig.DEBUG) {
                    val interceptor = HttpLoggingInterceptor()
                    interceptor.level = HttpLoggingInterceptor.Level.BODY
                    httpClient.addInterceptor(interceptor)
                }

                return httpClient.build()
            }

        fun setToken(token: String?) {
            TOKEN = token!!
            resetRetrofitInstance()
        }

        fun resetRetrofitInstance() {
            retrofit = null
        }
    }

}