package com.spixel.SpixelFigterApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

/**
 * TODO Crear un nuevo paquete com.spixel.SpixelFigterApi.security en el que introducir todo lo referente a seguridad.
 * La intencion de este nuevo paquete es separar a nivel de paquetes la logica de negocio propia de la aplicacion de
 * la logica de seguridad
 */

@SpringBootApplication
public class SpixelFigterApiApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpixelFigterApiApplication.class, args);
	}

}
