package com.example.nauval.dao;

import java.util.List;
import com.example.nauval.modelo.ClubNautico;

/**
 * @author Sim�n
 *Esta interface incorpora los metodos necesarios para el acceso a los datos en los diferentes
 *casos de uso de nuestra aplicaci�n
 */
public interface ClubNauticoDAO {
	/**
	 * Recupera la lista de clubes n�uticos de la aplicaci�n
	 * @return lista de clubes n�uticos
	 */
	public List<ClubNautico> recuperarClubesNauticos();
	/**
	 * Recupera la informaci�n relativa a un club nautico cuya id coincide con la que
	 * se pasa por par�metro.
	 * @param id El id del club n�utico a recuperar
	 * @return el club n�utico
	 */
	public ClubNautico recuperaClubNautico(Integer id);	
}
