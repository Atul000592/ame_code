package nic.ame.app.master.service;

import java.util.Optional;

import nic.ame.app.master.dto.AmeDetailsDto;

public interface AmeDetailsService {

	Optional<AmeDetailsDto> getCandidateAmeDetailsByAmeId(String ameId);
}
