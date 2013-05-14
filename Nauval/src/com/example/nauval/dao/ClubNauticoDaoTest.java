package com.example.nauval.dao;

import java.util.ArrayList;
import java.util.List;

import com.example.nauval.modelo.ClubNautico;

public class ClubNauticoDaoTest implements ClubNauticoDAO{
	
    private List<ClubNautico> clubesNauticos=new ArrayList<ClubNautico>();
	
	
    public ClubNauticoDaoTest(){
    	ClubNautico clubNautico=new ClubNautico();
    	clubNautico.setId(1);
    	clubNautico.setNombre("Real club nautico de denia");
    	clubNautico.setDireccion("calle pepe");
    	clubNautico.setEmail("sidmf@hotamil.com");
    	clubNautico.setTelefono("6666666");
    	clubNautico.setWeb("www.clubdenia.com");
    	clubNautico.setLatitud(0.5d);
    	clubNautico.setLongitud(0.8d);
    	clubesNauticos.add(clubNautico);
    	clubNautico=new ClubNautico();
    	clubNautico.setId(2);
    	clubNautico.setNombre("Real club nautico de Gandia");
    	clubNautico.setDireccion("calle pepe2");
    	clubNautico.setEmail("sidmf@tamil.com");
    	clubNautico.setTelefono("666555555");
    	clubNautico.setWeb("www.clubjavea.com");
    	clubNautico.setLatitud(0.5d);
    	clubNautico.setLongitud(0.8d);
    	clubesNauticos.add(clubNautico);
    	clubNautico=new ClubNautico();
    	clubNautico.setId(3);
    	clubNautico.setNombre("Real club nautico de Oliva");
    	clubNautico.setDireccion("calle pepe3");
    	clubNautico.setEmail("sidmf@hotgggggl.com");
    	clubNautico.setTelefono("666644444446");
    	clubNautico.setWeb("www.clubmarina.com");
    	clubNautico.setLatitud(0.5d);
    	clubNautico.setLongitud(0.8d);
    	clubesNauticos.add(clubNautico);
    }

	@Override
	public List<ClubNautico> recuperarClubesNauticos() {
		
		return clubesNauticos;
	}

	@Override
	public ClubNautico recuperaClubNautico(Integer id) {
		for (ClubNautico  club : clubesNauticos) {
			if (club.getId() == id)
			{
				return club;
			}
		}
		return null;
	}

}
