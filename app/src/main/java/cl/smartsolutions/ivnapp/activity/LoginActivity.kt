package cl.smartsolutions.ivnapp.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import cl.smartsolutions.ivnapp.R
import cl.smartsolutions.ivnapp.repository.UserRepository

class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: TextView
    private lateinit var btnForgotPassword: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        // Se recupera los valores de las vistas con sus IDs en el archivo XML
        etEmail = findViewById(R.id.emailEditText)
        etPassword = findViewById(R.id.passwordEditText)
        btnLogin = findViewById(R.id.loginButton)
        btnRegister = findViewById(R.id.registerButton)
        btnForgotPassword = findViewById(R.id.forgotPassword)

        btnLogin.setOnClickListener {
            // Lógica de autenticación
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            if (UserRepository.validateUser(email, password)) {
                val intent = Intent(this, NotesActivity::class.java)
                startActivity(intent)
            } else {
                // Notificación visual y vibración para indicar que hubo un error en la autenticación
                showLoginErrorFeedback("Usuario o contraseña incorrecta")
            }
        }

        btnRegister.setOnClickListener {
            // Redirige a la pantalla de registro
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        btnForgotPassword.setOnClickListener {
            // Redirige a la pantalla de recuperación de contraseña
            startActivity(Intent(this, RecoverPasswordActivity::class.java))
        }
    }

    private fun showLoginErrorFeedback(message: String) {
        // Vibración para notificar el error
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(500)
        }

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
