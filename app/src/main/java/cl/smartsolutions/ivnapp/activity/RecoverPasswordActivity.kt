package cl.smartsolutions.ivnapp.activity

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import cl.smartsolutions.ivnapp.R
import cl.smartsolutions.ivnapp.repository.UserRepository
import java.util.*

class RecoverPasswordActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var etEmail: EditText
    private lateinit var btnRecoverPassword: Button
    private lateinit var backButton: ImageView
    private lateinit var vibrator: Vibrator
    private lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recover_password)

        // Inicializar TextToSpeech y Vibrator
        textToSpeech = TextToSpeech(this, this)
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        // Vincular las vistas con sus IDs
        etEmail = findViewById(R.id.emailEditText)
        btnRecoverPassword = findViewById(R.id.recoverPasswordButton)
        backButton = findViewById(R.id.backButton)

        // Desactivar el botón inicialmente
        btnRecoverPassword.isEnabled = false

        // Agregar un TextWatcher para habilitar/deshabilitar el botón según el input
        etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Habilitar el botón solo si hay texto ingresado
                btnRecoverPassword.isEnabled = !s.isNullOrEmpty()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        btnRecoverPassword.setOnClickListener {
            val email = etEmail.text.toString()
            val user = UserRepository.validateUserByEmail(email)
            if (user != null) {
                Toast.makeText(this, "Contraseña enviada a: " + user.getEmail(), Toast.LENGTH_SHORT).show()
                showLoginErrorFeedback("Por favor revisa tu email")
            } else {
                showLoginErrorFeedback("No se pudo encontrar el email")
            }
        }

        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech.language = Locale("es", "ES")
            textToSpeech.setPitch(0.9f)
            textToSpeech.setSpeechRate(0.9f)

            val result = textToSpeech.setLanguage(Locale("es", "ES"))
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "El idioma no es soportado", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Error al inicializar Text to Speech", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showLoginErrorFeedback(message: String) {
        // Vibración para notificar el error
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(500)
        }

        // Reproducir mensaje de error
        textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null, null)

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        // Libera recursos de TextToSpeech
        if (textToSpeech != null) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
        super.onDestroy()
    }
}
