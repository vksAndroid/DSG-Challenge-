package countryinfo.app.di

import android.content.Context
import android.speech.SpeechRecognizer
import countryinfo.app.utils.ConvertSpeechToTextHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun provideSpeechRecognizer(@ApplicationContext appContext: Context): SpeechRecognizer {
        return SpeechRecognizer.createSpeechRecognizer(appContext)
    }

    @Singleton
    @Provides
    fun provideConvertSpeechToTextHelper(speechRecognizer: SpeechRecognizer): ConvertSpeechToTextHelper {
        return ConvertSpeechToTextHelper(speechRecognizer)
    }

}