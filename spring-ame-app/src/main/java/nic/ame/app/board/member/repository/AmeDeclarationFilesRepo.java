package nic.ame.app.board.member.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import nic.ame.app.master.medical.model.AmeDeclarationFiles;


@Repository
public interface AmeDeclarationFilesRepo  extends JpaRepository<AmeDeclarationFiles, Integer>{

	@Query(nativeQuery = true,value="select * from ame_declaration_files where ame_id=?1")
		List<AmeDeclarationFiles> findByAmeIdFileExist(String ameId);
	
	
	 @Query(value = "select * from ame_declaration_files where ame_id=?1",nativeQuery = true)
		List<AmeDeclarationFiles> findByAmeId(String ameId);

    @Query(nativeQuery = true,value = "select * from ame_declaration_files where ame_id=?1")
    AmeDeclarationFiles findFileNameByAmeId(String ameId);
}
