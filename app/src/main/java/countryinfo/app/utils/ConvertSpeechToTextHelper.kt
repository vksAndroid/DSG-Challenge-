package countryinfo.app.utils

import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import javax.inject.Inject

class ConvertSpeechToTextHelper @Inject constructor(val speechRecognizer: SpeechRecognizer){

    fun speechToTextConverter(
        resultText: (String) -> Unit,
        finished: () -> Unit
    ) {

        val speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        speechRecognizerIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM,
        )

        // Optionally I have added my mother language
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "el_GR")

        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(bundle: Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(v: Float) {}
            override fun onBufferReceived(bytes: ByteArray?) {}
            override fun onEndOfSpeech() {
                finished()
                // changing the color of your mic icon to
                // gray to indicate it is not listening or do something you want
            }

            override fun onError(i: Int) {}

            override fun onResults(bundle: Bundle) {
                val result = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (result != null) {
                    resultText(result[0])
                }
            }

            override fun onPartialResults(bundle: Bundle) {}
            override fun onEvent(i: Int, bundle: Bundle?) {}

        })
        speechRecognizer.startListening(speechRecognizerIntent)
    }

}