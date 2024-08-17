package cl.smartsolutions.ivnapp.repository

import cl.smartsolutions.ivnapp.model.User

object UserRepository {


    private val users = mutableListOf(
        User("Matias", "Fuentes", "matias.fuentes.vasquez@gmail.com", "1234", 29),
        User("Constanza", "Mundaca", "cmundaca@gmail.com", "admin123", 24),
        User("Catalina", "Arriagada", "carriagada@gmail.com", "password2", 30),
        User("Pedro", "Martinez", "pmartinez@gmail.com", "password3", 28)
    )


    fun validateUser(email: String, password: String): Boolean {
        return users.any { it.getEmail() == email && it.getPassword() == password }
    }
    fun createUser(user: User){
        users.add(user)
    }
}