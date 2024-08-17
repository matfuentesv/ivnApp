package cl.smartsolutions.ivnapp.activity

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cl.smartsolutions.ivnapp.R
import cl.smartsolutions.ivnapp.model.User
import cl.smartsolutions.ivnapp.repository.UserRepository

class RecoverPasswordActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var btnRecoverPassword: Button
    private lateinit var backButton: ImageView
    private val users = mutableListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recover_password)


        // Vincular las vistas con sus IDs
        etEmail = findViewById(R.id.emailEditText)
        btnRecoverPassword = findViewById(R.id.recoverPasswordButton)
        backButton = findViewById(R.id.backButton)


        btnRecoverPassword.setOnClickListener {
            val email = etEmail.text.toString()
            val user = UserRepository.validateUserByEmail(email)
            if(user!=null){
                Toast.makeText(this, "Contraseña enviada a: " + user?.getEmail(), Toast.LENGTH_SHORT).show()
            }else {
                showLoginErrorFeedback("No se pudo encontrar el email")
            }
        }

        backButton.setOnClickListener {
            onBackPressed()
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