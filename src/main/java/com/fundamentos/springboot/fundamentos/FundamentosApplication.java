package com.fundamentos.springboot.fundamentos;

import com.fundamentos.springboot.fundamentos.bean.MyBean;
import com.fundamentos.springboot.fundamentos.bean.MyBeanWithDependency;
import com.fundamentos.springboot.fundamentos.bean.MyBeanWithProperties;
import com.fundamentos.springboot.fundamentos.component.ComponentDependency;

import com.fundamentos.springboot.fundamentos.entity.User;
import com.fundamentos.springboot.fundamentos.pojo.UserPojo;
import com.fundamentos.springboot.fundamentos.repository.UserRepository;
import com.fundamentos.springboot.fundamentos.service.UserService;
import org.apache.juli.logging.Log;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class FundamentosApplication implements CommandLineRunner {

	private final Logger LOGGER = LoggerFactory.getLogger(FundamentosApplication.class);

	/* Interfaces */
	private ComponentDependency componentDependency;
	private MyBean myBean;
	private MyBeanWithDependency myBeanWithDependency;
	private MyBeanWithProperties myBeanWithProperties;
	private UserPojo userPojo;
	private UserRepository userRepository;
	private UserService userService;

	/*Constructor*/
	@Autowired
	public FundamentosApplication(@Qualifier("componentTwoImplement") ComponentDependency componentDependency, MyBean myBean, MyBeanWithDependency myBeanWithDependency, MyBeanWithProperties myBeanWithProperties, UserPojo userPojo, UserRepository userRepository, UserService userService){
		this.componentDependency = componentDependency;
		this.myBean = myBean;
		this.myBeanWithDependency = myBeanWithDependency;
		this.myBeanWithProperties = myBeanWithProperties;
		this.userPojo = userPojo;
		this.userRepository = userRepository;
		this.userService = userService;
	}


	public static void main(String[] args) {
		SpringApplication.run(FundamentosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/* ejemplosAnteriores(); */
		saveUsersInDataBase();
		/* getInformationJpqlFromUserName(); */
		saveWithErrorTransactional();
	};

	private void saveWithErrorTransactional(){
		User test1 = new User("TestTransactional1", "TestTransactional1");
		User test2 = new User("TestTransactional2", "TestTransactional2");
		/* Generamos el error */
		User test3 = new User("TestTransactional1", "TestTransactional3");

		List <User> testUserList = Arrays.asList(test1, test2, test3);

		try {
			userService.saveTransactional(testUserList);
			userService.getAllUsers()
					.stream()
					.forEach(user -> LOGGER.info("Usuario registrado con método transaccional: " + user));
		}catch (Exception e){
			LOGGER.error("Esta es una exception por error de registración y rollback por transacctional-service: " + e);
		}

	}

	private void getInformationJpqlFromUserName () {
		LOGGER.info("User encontrado con notacion @Query y JPQL" + userRepository.findByName("Jhon")
				.orElseThrow(() -> new RuntimeException("No se encontro el usuario.")
		));

		userRepository.findAndSort("Mat", Sort.by("idUser").ascending())
			.stream()
			.forEach(user -> LOGGER.info("Usuario con metodo sort " + user));

		userRepository.findByUserLastName("Pastrana")
				.stream()
				.forEach(user -> LOGGER.info("Usuario con query method " + user));

		LOGGER.info("Usuario con query method " + userRepository.findByUserNameAndUserLastName("Frank", "Ferdinand")
				.orElseThrow(() -> new RuntimeException("No se encontro el usuario.")
		));

		LOGGER.info("Usuario con query method LIKE " + userRepository.findByUserNameLike("%bert%")
				.orElseThrow(() -> new RuntimeException("No se encontro el usuario.")
		));

		LOGGER.info("Usuario con query method OR " + userRepository.findByUserNameOrUserLastName(null, "Giulian")
				.orElseThrow(() -> new RuntimeException("No se encontro el usuario.")));

		userRepository.findByUserNameLikeOrderByIdUserDesc("%Ma%")
				.stream()
				.forEach(user -> LOGGER.info("Usuario encontrado con username like order by user id  " + user));

		userRepository.findByUserNameContainingOrderByIdUserAsc("Ma")
				.stream()
				.forEach(user -> LOGGER.info("Usuario encontrado con username like order by user id  " + user));

		LOGGER.info("Usuario encontrado a partir de Named Parameter:  " + userRepository.getAllByUserNameAndLastName("Oscar", "Fernandez")
				.orElseThrow(() -> new RuntimeException("No se encontro el usuario.")));

	}


	private void saveUsersInDataBase(){
		User user1 = new User("Jhon", "Pastrana");
		User user2 = new User("Charly", "Giulian");
		User user3 = new User("Mate", "Williams");
		User user4 = new User("Mateo", "Mirland");
		User user5 = new User("Maitena", "Sarapo");
		User user6 = new User("Frank", "Ferdinand");
		User user7 = new User("Oscar", "Fernandez");
		User user8 = new User("Alberto", "Spinetta");

		List <User> userList = Arrays.asList(user1, user2, user3, user4, user5, user6, user7, user8);

		/* Usamos el repositorio para hacer persistir esta informacion */
		userList.stream()
				.peek(user -> LOGGER.info("Usuario registrado con método save " + user))
				.forEach(userRepository::save);
	}

	private void ejemplosAnteriores() {
		componentDependency.saludar();
		myBean.print();
		myBeanWithDependency.printWithDependency();
		System.out.println(myBeanWithProperties.function() + "!");
		System.out.println(userPojo.getEmail() + " - " + userPojo.getAge());
		try {
			int value = 10/0;
			LOGGER.info("El valor es: " + value);
		} catch(Exception e){
			LOGGER.error("Esto es un error al dividir por 0 " + e.getMessage());
		}
	}
}
