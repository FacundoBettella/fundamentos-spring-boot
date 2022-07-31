package com.fundamentos.springboot.fundamentos.service;

import com.fundamentos.springboot.fundamentos.entity.User;
import com.fundamentos.springboot.fundamentos.repository.UserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {

    /* Creo LOG */
    private final Log LOG = LogFactory.getLog(UserService.class);

    /* Inyectamos dependencia UserRepository en el constructor UserService */
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /* Permite hacer un RollBack de todos los "insert" en db que se hicieron en caso de que ocurra un error en algun registro*/
    @Transactional
    public void saveTransactional(List<User> users){
        users.stream()
                /* peek para mostrar en pantalla lo que se este registrando */
                .peek(user -> LOG.info("Usuario insertado con saveTransactional: " + user))
                .forEach(userRepository::save);
    }
    /**
     * Las características de una transacción tienen el acrónimo ACID:

     * Atomicidad: Las actividades de un método se consideran como una unidad de trabajo. Esto se conoce como Atomicidad. Este concepto asegura que todas las operaciones en una transacción se ejecuta todo o nada.
     * Si todas las instrucciones o líneas de código de un método transaccional son ejecutadas con éxito, entonces al finalizar se realiza un commit, es decir, guardado de la información.
     * Si alguna de las instrucciones falla se realiza un rollback, es decir, ninguna de la información es guardada en la base de datos o el repositorio donde ser persiste dicha información…

     * Consistente: Una vez que termina una transacción (sin importar si ha sido exitosa o no) la información queda en estado consistente, ya que se realizó todo o nada, y por lo tanto los datos no deben estar corruptos en ningún aspecto.

     * Aislado: Múltiples usuarios pueden utilizar los métodos transaccionales, sin afectar el acceso de otros usuarios. Sin embargo debemos prevenir errores por accesos múltiples, aislando en la medida de lo posible nuestros métodos transaccionales. El aislamiento normalmente involucra el bloqueo de registros o tablas de base de datos, esto se conoce como locking…

     * Durable: Sin importar si hay una caída del servidor, una transacción exitosa debe guardarse y perdurar posterior al termino de una transacción.
     **/

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getAllPageableUsers(int page, int size){
        return userRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    public User save(User newUser) {
        return userRepository.save(newUser);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public User update(User userToUpdate, Long id) {
        return userRepository.findById(id)
                .map(
                        user -> {
                            user.setUserName(userToUpdate.getUserName());
                            user.setUserLastName(userToUpdate.getUserLastName());
                            return userRepository.save(user);
                        }
                ).orElse(null);
    }
}
