package cl.smartsolutions.ivnapp.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import cl.smartsolutions.ivnapp.R
import cl.smartsolutions.ivnapp.repository.UserRepository
import java.util.*

class LoginActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: TextView
    private lateinit var btnForgotPassword: TextView
    private lateinit var textToSpeech: TextToSpeech
    private lateinit var vibrator: Vibrator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inicializar TextToSpeech y Vibrator
        textToSpeech = TextToSpeech(this, this)
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        // Inicializar vistas
        etEmail = findViewById(R.id.emailEditText)
        etPassword = findViewById(R.id.passwordEditText)
        btnLogin = findViewById(R.id.loginButton)
        btnRegister = findViewById(R.id.registerButton)
        btnForgotPassword = findViewById(R.id.forgotPassword)

        // Desactivar botón de login inicialmente
        btnLogin.isEnabled = false

        // Activar el botón de login solo cuando ambos campos estén llenos
        etEmail.addTextChangedListener { validateInputs() }
        etPassword.addTextChangedListener { validateInputs() }

        btnLogin.setOnClickListener {
            // Lógica de autenticación
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            if (UserRepository.validateUser(email, password)) {
                val intent = Intent(this, NotesActivity::class.java)
                startActivity(intent)
            } else {
                // Retroalimentación de error
                showLoginErrorFeedback("Usuario y/o contraseña incorrecta")
            }
        }

        btnRegister.setOnClickListener {
            // Redirigir a la pantalla de registro
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        btnForgotPassword.setOnClickListener {
            // Redirigir a la pantalla de recuperación de contraseña
            startActivity(Intent(this, RecoverPasswordActivity::class.java))
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

    private fun validateInputs() {
        btnLogin.isEnabled = etEmail.text.isNotEmpty() && etPassword.text.isNotEmpty()
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
