package co.edu.usbcali.simulador;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import co.edu.usbcali.simulador.database.AppDatabase;
import co.edu.usbcali.simulador.database.user.User;
import co.edu.usbcali.simulador.database.user.UserDao;

public class LoginActivity extends AppCompatActivity {

    EditText editTextIdentification;
    EditText editTextPassword;
    Button buttonLogin;
    AppDatabase DB;
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextIdentification = findViewById(R.id.editTextIdentification);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);


        DB = AppDatabase.getAppDatabase(getApplicationContext());
        userDao = DB.userDao();
        if (userDao.count() == 0) {
            User newUser = new User(1116264525, "Juan", "Velasquez", "1234");
            userDao.insert(newUser);
        }

        final Intent intent = new Intent(this, MainActivity.class);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String identification = editTextIdentification.getText().toString().trim();
                int userIdentification = identification.equals("") ? 0 : Integer.parseInt(identification);
                String userPassword = editTextPassword.getText().toString();
                User user = userDao.findByDocumentNumber(userIdentification);
                if(userPassword != null && userIdentification != 0 && user != null) {
                    if (user.getPassword().equals(userPassword)) {
                        Toast.makeText(getApplicationContext(), "Bienvenido " + user.getFirstName(), Toast.LENGTH_SHORT).show();
                        intent.putExtra("loggedUser", user);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (user == null && userIdentification != 0) {
                        Toast.makeText(getApplicationContext(), "Usuario no registrado", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Identificacion y contraseña requeridos", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}
