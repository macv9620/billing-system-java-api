package com.macv.billing.web.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

//Anotación para poder inyectar la clase dentro del ciclo de vida de Spring
@Component
public class JwtUtil {

    //Clave secreta que irá en la firma del JWT
    private static final String SECRET_KEY = System.getenv().get("BILLING_SECRET_JWT_KEY");

    //Algoritmo de encriptación que se usará en la firma
    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY);

    //Método que dado un usuario creará y retormará un JWT utilizando la libreria Auth0
    public String create(String username){

        return JWT
                //Crear un JWT
                .create()
                //Añadir info al payload en este caso el nombre de usuario
                .withSubject(username)
                //Quien crea el JWS
                .withIssuer("spring-billing-api")
                //Cuando fue creado
                .withIssuedAt(new Date())
                //Cuando expira el JWT en milisegundos en este caso se parametriza para que sea 1 día a partir
                //de la fecha de creación
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1)))
                //Firma privada del JWT, este recibe una clave secreta y un algoritmo
                .sign(ALGORITHM);

    }

    //Método para revisar si un JWT es válido retorna verdadero si es válido o falso si no lo es
    public boolean validateToken(String jwt) {
        //Construye un objeto JWT que al pasarle el algoritmo permite validar un JWT
        //Si es correcto el método se ejecuta
        //Si no es válido el método lanza una excepción JWTVerificationException por eso se implementa try/catch
        try {
            JWT.require(ALGORITHM)
                    .build()
                    .verify(jwt);
            return true;
        } catch (JWTVerificationException e){
            return false;
        }

    }

    public String getUsername(String jwt){
        //Este método, tras validar el JWT retorna un objeto
        //el método getSubject del objeto retornado permite obtener el subject del JWT
        //que en este caso es el username como se determinó en el método create() -> .withSubject(username)
        return JWT.require(ALGORITHM)
                .build()
                .verify(jwt)
                .getSubject();
    }
}
