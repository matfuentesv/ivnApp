package cl.smartsolutions.ivnapp.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import cl.smartsolutions.ivnapp.R
import cl.smartsolutions.ivnapp.model.User

class RegisterActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etLasName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etAge: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var backButton: ImageView
    private val users = mutableListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        // Vincular las vistas con sus IDs
        etName = findViewById(R.id.nameEditText)
        etLasName = findViewById(R.id.lastNameEditText)
        etEmail = findViewById(R.id.emailEditText)
        etAge = findViewById(R.id.ageEditText)
        etPassword = findViewById(R.id.passwordEditText)
        btnRegister = findViewById(R.id.registerButton)
        backButton = findViewById(R.id.backButton)


        btnRegister.setOnClickListener {
            if (users.size < 5) {
                val user = User(
                    etName.text.toString(),
                    etLasName.text.toString(),
                    etEmail.text.toString(),
                    etAge.text.toString(),
                    Integer.parseInt(etPassword.text.toString())
                )
                users.add(user)
                finish() // Regresa a la pantalla de login
            } else {
                Toast.makeText(this, "No se pueden crear mas usuarios", Toast.LENGTH_SHORT).show()
            }
        }

        backButton.setOnClickListener {
            onBackPressed()
        }


    }
}