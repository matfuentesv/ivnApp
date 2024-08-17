package cl.smartsolutions.ivnapp.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import cl.smartsolutions.ivnapp.R

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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Se recupera los valores de la vistas con sus IDs en el archivo XML
        etEmail = findViewById(R.id.emailEditText)
        etPassword = findViewById(R.id.passwordEditText)
        btnLogin = findViewById(R.id.loginButton)
        btnRegister = findViewById(R.id.registerButton)
        btnForgotPassword = findViewById(R.id.forgotPassword)


        btnLogin.setOnClickListener {
            // Lógica de autenticación, redirige a NotesActivity si es exitoso
            startActivity(Intent(this, NotesActivity::class.java))
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