package com.fundamentos.springboot.fundamentos.bean;

import com.fundamentos.springboot.fundamentos.FundamentosApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyBeanWithDependencyImplement implements MyBeanWithDependency{

   private MyOperation myOperation;
   private final Logger LOGGER = LoggerFactory.getLogger(FundamentosApplication.class);

    public MyBeanWithDependencyImplement(MyOperation myOperation) {
        this.myOperation = myOperation;
    }

    @Override
    public void printWithDependency() {
        LOGGER.info("Hemos ingresado al método printWithDependency");

        int number = 12;

        /* LOGGER.debug no funciona en un servidor productivo */
        LOGGER.debug("El numero enviado como parametro a la dependencia operation es: " + number);
        System.out.println("Hola desde la implementación de un bean con dependencia => " + myOperation.sum(number));
    }
}
