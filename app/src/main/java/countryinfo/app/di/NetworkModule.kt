package countryinfo.app.di

import countryinfo.app.data.ApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Provides BaseUrl as string
     */
    @Singleton
    @Provides
    fun provideBaseURL(): String {
        return "https://restcountries.com/"
    }

    /**
     * Provides LoggingInterceptor for api information
     */
    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
    }

    /**
     * Provides custom OkkHttp
     */
    @Singleton
    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClient = OkHttpClient().newBuilder()

        okHttpClient.callTimeout(40, TimeUnit.SECONDS)
        okHttpClient.connectTimeout(40, TimeUnit.SECONDS)
        okHttpClient.readTimeout(40, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(40, TimeUnit.SECONDS)
        okHttpClient.addInterceptor(loggingInterceptor)
        return okHttpClient.build()
    }

    /**
     * Provides converter factory for retrofit
     */
    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    /**
     * Provides converter factory for retrofit
     */
    @Singleton
    @Provides
    fun provideScalerConverterFactory(): ScalarsConverterFactory {
        return ScalarsConverterFactory.create()
    }

    /**
     * Provides ApiServices client for Retrofit
     */
    @Singleton
    @Provides
    fun provideRetrofitClient(
        baseUrl: String,
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory,
        scalarConverterFactory: ScalarsConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(scalarConverterFactory)
            .addConverterFactory(converterFactory)
            .build()
    }

    /**
     * Provides Api Service using retrofit
     */
    @Singleton
    @Provides
    fun provideRestApiService(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }
}