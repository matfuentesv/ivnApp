package cl.smartsolutions.ivnapp.activity

import android.content.Intent
import android.os.Bundle
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

        // Se recupera los valores de la vistas con sus IDs en el archivo XML
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
                Toast.makeText(this, "Usuario o contraseña incorrecta", Toast.LENGTH_SHORT).show()
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
}