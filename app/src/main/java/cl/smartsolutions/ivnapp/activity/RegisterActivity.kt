package cl.smartsolutions.ivnapp.activity

import android.content.Intent
import android.os.Bundle
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
import cl.smartsolutions.ivnapp.model.User
import cl.smartsolutions.ivnapp.repository.UserRepository
import java.util.Locale

class RegisterActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var etName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etAge: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var backButton: ImageView
    private lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        // Inicializar TextToSpeech
        textToSpeech = TextToSpeech(this, this)

        // Vincular las vistas con sus IDs
        etName = findViewById(R.id.nameEditText)
        etLastName = findViewById(R.id.lastNameEditText)
        etEmail = findViewById(R.id.emailEditText)
        etAge = findViewById(R.id.ageEditText)
        etPassword = findViewById(R.id.passwordEditText)
        btnRegister = findViewById(R.id.registerButton)
        backButton = findViewById(R.id.backButton)

        // Desactivar el botón de registro inicialmente
        btnRegister.isEnabled = false

        // Agregar un TextWatcher para verificar si todos los campos están llenos
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                btnRegister.isEnabled = areAllFieldsFilled()
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        etName.addTextChangedListener(textWatcher)
        etLastName.addTextChangedListener(textWatcher)
        etEmail.addTextChangedListener(textWatcher)
        etAge.addTextChangedListener(textWatcher)
        etPassword.addTextChangedListener(textWatcher)

        btnRegister.setOnClickListener {
            if (UserRepository.getUsers().size < 5) {
                val user = User(
                    etName.text.toString(),
                    etLastName.text.toString(),
                    etEmail.text.toString(),
                    etPassword.text.toString(),
                    etAge.text.toString().toInt()
                )

                UserRepository.createUser(user)

                // Mensaje de voz al registrarse
                val message = "Usuario registrado exitosamente"
                textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null, null)

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "No se pueden crear más usuarios", Toast.LENGTH_SHORT).show()
            }
        }

        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun areAllFieldsFilled(): Boolean {
        return etName.text.isNotEmpty() &&
                etLastName.text.isNotEmpty() &&
                etEmail.text.isNotEmpty() &&
                etAge.text.isNotEmpty() &&
                etPassword.text.isNotEmpty()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech.language = Locale.getDefault()
        }
    }

    override fun onDestroy() {
        // Liberar recursos de TextToSpeech
        if (textToSpeech != null) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
        super.onDestroy()
    }
}
