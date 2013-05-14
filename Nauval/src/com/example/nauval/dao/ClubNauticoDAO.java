package com.example.nauval.dao;

import java.util.List;

import com.example.nauval.modelo.ClubNautico;

public interface ClubNauticoDAO {
	public List<ClubNautico> recuperarClubesNauticos();
	public ClubNautico recuperaClubNautico(Integer id);
	
}
