package cl.smartsolutions.ivnapp.activity

import android.os.Bundle
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
                Toast.makeText(this, "No se pudo encontrar el email", Toast.LENGTH_SHORT).show()
            }

        }

        backButton.setOnClickListener {
            onBackPressed()
        }
    }
}