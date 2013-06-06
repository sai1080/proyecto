package com.example.nauval.dao;

import java.util.List;
import com.example.nauval.modelo.ClubNautico;

/**
 * @author Simón
 *Esta interface incorpora los metodos necesarios para el acceso a los datos en los diferentes
 *casos de uso de nuestra aplicación
 */
public interface ClubNauticoDAO {
	/**
	 * Recupera la lista de clubes náuticos de la aplicación
	 * @return lista de clubes náuticos
	 */
	public List<ClubNautico> recuperarClubesNauticos();
	/**
	 * Recupera la información relativa a un club nautico cuya id coincide con la que
	 * se pasa por parámetro.
	 * @param id El id del club náutico a recuperar
	 * @return el club náutico
	 */
	public ClubNautico recuperaClubNautico(Integer id);	
}
