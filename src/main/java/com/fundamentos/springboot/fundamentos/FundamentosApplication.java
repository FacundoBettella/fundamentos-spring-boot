package com.fundamentos.springboot.fundamentos;

import com.fundamentos.springboot.fundamentos.bean.MyBean;
import com.fundamentos.springboot.fundamentos.bean.MyBeanWithDependency;
import com.fundamentos.springboot.fundamentos.bean.MyBeanWithProperties;
import com.fundamentos.springboot.fundamentos.component.ComponentDependency;

import com.fundamentos.springboot.fundamentos.entity.User;
import com.fundamentos.springboot.fundamentos.pojo.UserPojo;
import com.fundamentos.springboot.fundamentos.repository.UserRepository;
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

	/*Constructor*/
	@Autowired
	public FundamentosApplication(@Qualifier("componentTwoImplement") ComponentDependency componentDependency, MyBean myBean, MyBeanWithDependency myBeanWithDependency, MyBeanWithProperties myBeanWithProperties, UserPojo userPojo, UserRepository userRepository){
		this.componentDependency = componentDependency;
		this.myBean = myBean;
		this.myBeanWithDependency = myBeanWithDependency;
		this.myBeanWithProperties = myBeanWithProperties;
		this.userPojo = userPojo;
		this.userRepository = userRepository;
	}


	public static void main(String[] args) {
		SpringApplication.run(FundamentosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/* ejemplosAnteriores(); */
		saveUsersInDataBase();
		getInformationJpqlFromUserName();
	}

	private void getInformationJpqlFromUserName () {
		LOGGER.info("User encontrado con findByUserName " + userRepository.findByUserName("Jhon").orElseThrow(
				() -> new RuntimeException("No se encontro el usuario.")
		));

		userRepository.findAndSort("Mat", Sort.by("idUser").ascending())
			.stream()
			.forEach(user -> LOGGER.info("Usuario con metodo sort " + user));
	}


	private void saveUsersInDataBase(){
		User user1 = new User("Jhon", "Pastrana");
		User user2 = new User("Charly", "Giulian");
		User user3 = new User("Mate", "Williams");
		User user4 = new User("Mateo", "Mirland");
		User user5 = new User("Maitena", "Sarapo");

		List <User> userList = Arrays.asList(user1, user2, user3, user4, user5);

		/* Usamos el repositorio para hacer persistir esta informacion */
		userList.stream().forEach(userRepository::save);
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
